package edu.umd.cs.semesterproject.model;

// A rule that is triggered by location changes.
// Has a longitude, latitude, and radius that it checks.
public class LocationRule extends Rule {

    private double mLatitude;
    private double mLongitude;
    private double mRadius;
    private String placeName;

    public LocationRule() {
        super();
    }

    @Override
    public String getConditions() {
        return placeName;
    }

    public LocationRule(String name, boolean isEnabled, double latitude, double longitude, double radius) {
        super(name, isEnabled);

        this.setRuleType(RuleType.LOCATION);
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

    public String getPlaceName(){
        return placeName;
    }

    public void setPlaceName(String n){
        placeName = n;
    }

    public void setRadius(double rad){
        mRadius = rad;
    }

    public double getRadius(){
        return mRadius;
    }
}
