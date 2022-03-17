package com.model;

public class Coordinates {

    private double latitude;
    private double longitude;
    private double shortestRoadDistance;
    private double shortestAirfieldDistance;
    private double shortestUrbanAreasDistance;

    public Coordinates(double latitude,double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public double getShortestRoadDistance(){
        return this.shortestRoadDistance;
    }

    public double getShortestAirfieldDistance(){
        return this.shortestAirfieldDistance;
    }

    public double getShortestUrbanAreasDistance(){
        return this.shortestUrbanAreasDistance;
    }

    public void setShortestRoadDistance(double shortestRoadDistance){
        this.shortestRoadDistance = shortestRoadDistance;
    }

    public void setShortestAirfieldDistance(double shortestAirfieldDistance){
        this.shortestAirfieldDistance = shortestAirfieldDistance;
    }

    public void setShortestUrbanAreasDistance(double shortestUrbanAreasDistance){
        this.shortestUrbanAreasDistance = shortestUrbanAreasDistance;
    }

    //formula to calculate distance between two points
    public double Haversine(double lat2,double long2){
        double distance;
        double radius = 6371e3;
        

        double changeInLatitude = Math.toRadians(lat2-this.latitude);
        double changeInLongitude = Math.toRadians(long2-this.longitude);

        double lat1 = Math.toRadians(this.latitude);
        lat2 = Math.toRadians(lat2);

        double a = 
        (Math.sin(changeInLatitude/2) * Math.sin(changeInLatitude/2)) +
        Math.cos(lat1) * Math.cos(lat2) *
        (Math.sin(changeInLongitude/2) * Math.sin(changeInLongitude/2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        //distance in kilometers
        distance = (radius * c)/1000;

        return distance;
    }
    
}
