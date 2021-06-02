package com.sm19003564.omnimarkerza;

public class Settings {

    private boolean metric;
    private boolean imperial;

    public Settings() {

    }

    public Settings(boolean metric, boolean imperial) {
        this.metric = metric;
        this.imperial = imperial;
    }

    public boolean isImperial() {
        return imperial;
    }

    public void setImperial(boolean imperial) {
        this.imperial = imperial;
    }

    public boolean isMetric() {
        return metric;
    }

    public void setMetric(boolean metric) {
        this.metric = metric;
    }
}
