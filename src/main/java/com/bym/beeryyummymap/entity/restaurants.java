package com.bym.beeryyummymap.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "restaurants")
public class restaurants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id ;
    private String resCode ;
    private String name;
    private String description ;
    private String status;
    private UUID locationId;
    private Timestamp createdAt;
    private Timestamp updatedAt;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}
