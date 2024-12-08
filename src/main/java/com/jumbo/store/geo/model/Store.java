package com.jumbo.store.geo.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

@Data
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

    @GeoSpatialIndexed(name = "location", type = GeoSpatialIndexType.GEO_2DSPHERE)
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
