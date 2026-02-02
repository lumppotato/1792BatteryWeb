package com.r1792.model.inventory;


import jakarta.persistence.*;

@Entity
@Table(name = "inv_locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 64)
    private String code; // e.g. SHELF-A1

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 1000)
    private String description;

    public Location() {
    }

    public Location(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // getters / setters

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}