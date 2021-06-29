package com.sm19003564.omnimarkerza;

public class Settings {

    // Attributes
    private boolean metric;
    private boolean imperial;

    // Default constructor
    public Settings() {

    }

    // Constructor
    public Settings(boolean metric, boolean imperial) {
        this.metric = metric;
        this.imperial = imperial;
    }

    // Gets
    public boolean isImperial() {
        return imperial;
    }

    public boolean isMetric() {
        return metric;
    }

    // Sets
    public void setImperial(boolean imperial) {
        this.imperial = imperial;
    }

    public void setMetric(boolean metric) {
        this.metric = metric;
    }
}
