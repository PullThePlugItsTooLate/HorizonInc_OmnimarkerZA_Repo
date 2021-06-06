package com.sm19003564.omnimarkerza;

public class FavouriteLocationClass {

    private String location_name;
    private double latitude;
    private double longitude;


    public FavouriteLocationClass() {
    }

    @Override
    public String toString() {
        return "FavouriteLocationClass{" +
                "location_name='" + location_name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }



    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
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



    public FavouriteLocationClass(String location_name, double latitude, double longitude) {
        this.location_name = location_name;
        this.latitude = latitude;
        this.longitude = longitude;
    }


}
