package com.r1792.service.onshape;

//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.r1792.model.onshape.OnshapeBomItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class OnshapeBomService {

    @Value("${onshape.accessKey}")
    private String accessKey;

    @Value("${onshape.secretKey}")
    private String secretKey;

    @Value("${onshape.baseUrl:https://cad.onshape.com}")
    private String baseUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * Fetch a flattened BOM and aggregate quantities by partNumber / name.
     */
    public List<OnshapeBomItem> getAggregatedBom(String did, String wid, String eid) throws Exception {
        String url = String.format(
                "%s/api/assemblies/d/%s/w/%s/e/%s/bom?indented=false&multiLevel=true&generateIfAbsent=true",
                baseUrl, did, wid, eid);

        String basicAuth = Base64.getEncoder()
                .encodeToString((accessKey + ":" + secretKey).getBytes(StandardCharsets.UTF_8));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .header("Authorization", "Basic " + basicAuth)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Onshape BOM call failed: " +
                    response.statusCode() + " " + response.body());
        }

        JsonNode root = objectMapper.readTree(response.body());
        JsonNode bomTable = root.path("bomTable");
        JsonNode itemsNode = bomTable.isMissingNode() ? root.path("items") : bomTable.path("items");

        if (!itemsNode.isArray()) {
            throw new RuntimeException("Unexpected BOM JSON shape: " + response.body());
        }

        Map<String, OnshapeBomItem> summary = new LinkedHashMap<>();

        for (JsonNode itemNode : itemsNode) {
            String partNumber = itemNode.path("partNumber").asText("");
            String name = itemNode.path("name").asText("");

            double qty = itemNode.path("quantity").asDouble(1.0);

            // build key like in JS: prefer partNumber, fallback to name / partIdentity / item
            String key;
            if (!partNumber.isBlank()) {
                key = "PN:" + partNumber;
            } else if (!name.isBlank()) {
                key = "NM:" + name;
            } else {
                String partIdentity = itemNode.path("itemSource").path("partIdentity").asText("");
                String rawItem = itemNode.path("item").asText("");
                key = "ID:" + (!partIdentity.isBlank() ? partIdentity : rawItem);
            }

            String displayName;
            if (!partNumber.isBlank()) {
                displayName = partNumber + " — " + name;
            } else if (!name.isBlank()) {
                displayName = name;
            } else {
                displayName = key;
            }

            OnshapeBomItem existing = summary.get(key);
            if (existing == null) {
                summary.put(key, new OnshapeBomItem(partNumber, displayName, qty));
            } else {
                double newTotal = existing.getTotalQty() + qty;
                summary.put(key, new OnshapeBomItem(
                        existing.getPartNumber(),
                        existing.getName(),
                        newTotal
                ));
            }
        }

        return new ArrayList<>(summary.values());
    }
}
