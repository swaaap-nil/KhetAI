package com.example.khetai.model;

public class Land {

    public int getLandNumber() {
        return landNumber;
    }

    public void setLandNumber(int landNumber) {
        this.landNumber = landNumber;
    }

    private int landNumber;
    private double area;
    private double latitude;
    private double longitude;

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Land(int landNumber,float area, float latitude, float longitude) {
        this.area = area;
        this.latitude = latitude;
        this.longitude = longitude;
        this.landNumber=landNumber;
    }
}
