package com.jumbo.store.geo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Data
@Getter
@Setter
@Document(collection = "stores")
public class Store {

    @Id
    private String uuid;
    private String city;
    private String postalCode;
    private String street;
    private String street2;
    private String street3;
    private String addressName;
    private double longitude;
    private double latitude;
    private String complexNumber;
    private boolean showWarningMessage;
    private String todayOpen;
    private String locationType;
    private boolean collectionPoint;
    private String sapStoreID;
    private String todayClose;

    @Field("location")
    private GeoJsonPoint location;

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        setLocation();
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
        setLocation();
    }

    private void setLocation() {
        this.location = new GeoJsonPoint(this.longitude, this.latitude);
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getUuid() {
        return uuid;
    }
}
