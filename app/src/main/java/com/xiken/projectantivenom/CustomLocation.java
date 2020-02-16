package com.xiken.projectantivenom;

public class CustomLocation {
    private double latiutude;
    private double longitude;
    public CustomLocation(double latiutude,double longitude){
        this.latiutude = latiutude;
        this.longitude = longitude;
    }

    public CustomLocation() {
    }

    public double getLatiutude() {
        return latiutude;
    }

    public void setLatiutude(double latiutude) {
        this.latiutude = latiutude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
