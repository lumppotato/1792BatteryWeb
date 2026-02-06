package com.r1792.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppInfo {
    @Value("${app.version}")
    private String version;

    public String getVersion() {
        return version;
    }
}
