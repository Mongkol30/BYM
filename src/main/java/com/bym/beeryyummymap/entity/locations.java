package com.bym.beeryyummymap.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "locations")
public class locations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id ;
    private String locCode ;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String loc_code) {
        this.locCode = loc_code;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
