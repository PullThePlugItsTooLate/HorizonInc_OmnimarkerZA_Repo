package com.sm19003564.omnimarkerza;

public class FavouritePlace {

    // Attributes
    private String text;
    private String placeName;
    private String address;
    private String id;
    private double latitude;
    private double longitude;

    // Constructor
    public FavouritePlace(String text, String placeName, String address, String id, double latitude, double longitude) {
        this.text = text;
        this.placeName = placeName;
        this.address = address;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Default Constructor
    public FavouritePlace() {

    }

    // Gets

    public String getText() {
        return text;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // Sets

    public void setText(String text) {
        this.text = text;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // toString Override

    @Override
    public String toString() {
        return text;
    }
}