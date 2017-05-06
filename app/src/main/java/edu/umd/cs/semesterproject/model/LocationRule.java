package edu.umd.cs.semesterproject.model;

import java.util.List;

public class LocationRule extends Rule {

    private double mLatitude;
    private double mLongitude;
    private double mRadius;

    public LocationRule() {
        super();
    }

    @Override
    public String getConditions() {
        return mLatitude + "," + mLongitude;
    }

    public LocationRule(String name, boolean isEnabled, double latitude, double longitude, double radius) {
        super(name, Rule.TYPE_LOCATION, isEnabled);

        mLatitude = latitude;
        mLongitude = longitude;
        mRadius = radius;
    }

    public void setLatitude(double lat){
        mLatitude = lat;
    }

    public double getLatitude(){
        return mLatitude;
    }

    public void setLongitude(double lon){
        mLongitude = lon;
    }

    public double getLongitude(){
        return mLongitude;
    }

    public void setRadius(double rad){
        mRadius = rad;
    }

    public double getRadius(){
        return mRadius;
    }
}
