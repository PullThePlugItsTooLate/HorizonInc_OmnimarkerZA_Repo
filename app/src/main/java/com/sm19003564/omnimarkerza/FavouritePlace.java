package com.sm19003564.omnimarkerza;

public class FavouritePlace {

    private String text;
    private String placeName;
    private String address;
    private String id;
    private double latitude;
    private double longitude;

    public FavouritePlace(String text, String placeName, String address, String id, double latitude, double longitude) {
        this.text = text;
        this.placeName = placeName;
        this.address = address;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public FavouritePlace() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
