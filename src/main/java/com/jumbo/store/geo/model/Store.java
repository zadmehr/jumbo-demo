package com.jumbo.store.geo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

/**
 * Represents a store with various attributes such as location, address, and operational details.
 * This class is mapped to the "stores" collection in MongoDB.
 * 
 * Attributes:
 * - uuid: Unique identifier for the store.
 * - city: City where the store is located.
 * - postalCode: Postal code of the store's location.
 * - street: Street address of the store.
 * - street2: Additional street address information.
 * - street3: Additional street address information.
 * - addressName: Name of the address.
 * - longitude: Longitude coordinate of the store's location.
 * - latitude: Latitude coordinate of the store's location.
 * - complexNumber: Complex number associated with the store.
 * - showWarningMessage: Flag indicating whether to show a warning message.
 * - todayOpen: Opening time of the store for the current day.
 * - locationType: Type of the store's location.
 * - collectionPoint: Flag indicating whether the store is a collection point.
 * - sapStoreID: SAP store identifier.
 * - todayClose: Closing time of the store for the current day.
 * - location: GeoJsonPoint representing the geographical location of the store.
 * 
 * Methods:
 * - setLongitude(double longitude): Sets the longitude and updates the location.
 * - setLatitude(double latitude): Sets the latitude and updates the location.
 * - getLongitude(): Returns the longitude of the store.
 * - getLatitude(): Returns the latitude of the store.
 * - getUuid(): Returns the unique identifier of the store.
 */
@Data
@Getter
@Setter
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
