package com.jumbo.store.geo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Data
@Document(collection = "stores")
public class Store {

    @Id
    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("city")
    private String city;

    @JsonProperty("postalCode")
    private String postalCode;

    @JsonProperty("street")
    private String street;

    @JsonProperty("street2")
    private String street2;

    @JsonProperty("street3")
    private String street3;

    @JsonProperty("addressName")
    private String addressName;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("complexNumber")
    private String complexNumber;

    @JsonProperty("showWarningMessage")
    private boolean showWarningMessage;

    @JsonProperty("todayOpen")
    private String todayOpen;

    @JsonProperty("locationType")
    private String locationType;

    @JsonProperty("collectionPoint")
    private boolean collectionPoint;

    @JsonProperty("sapStoreID")
    private String sapStoreID;

    @JsonProperty("todayClose")
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
}
