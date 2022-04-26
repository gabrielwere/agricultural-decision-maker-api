package com.model;

public class CropModel {
    private String cropName;
    private double maximumRainfall;
    private double minimumRainfall;
    private String soilDrainage;
    private String soilSurfaceDrainage;
    private String soilRootableDepth;
    private double maximumSoilPH;
    private double minimumSoilPH;
    private double minimumTemperature;
    private double maximumTemperature;


    public CropModel(String cropName,
    double maxRainfallAmount,double minRainfallAmount,
    String soilDrainage,String surfaceDrainage,String rootableDepth,
    double maximumSoilPH,double minimumSoilPH,
    double minTemperature,double maxTemperature){

        this.cropName = cropName;
        this.maximumRainfall = maxRainfallAmount;
        this.minimumRainfall = minRainfallAmount;
        this.soilDrainage = soilDrainage;
        this.soilSurfaceDrainage = surfaceDrainage;
        this.soilRootableDepth = rootableDepth;
        this.maximumSoilPH = maximumSoilPH;
        this.minimumSoilPH = minimumSoilPH;
        this.minimumTemperature = minTemperature;
        this.maximumTemperature = maxTemperature;

    }
    public String getCropName(){
        return this.cropName;
    }

    public double getMaxRainfallAmount(){
        return this.maximumRainfall;
    }

    public double getMinRainfallAmount(){
        return this.minimumRainfall;
    }

    public String getSoilDrainage(){
        return this.soilDrainage;
    }

    public String getSurfaceDrainage(){
        return this.soilSurfaceDrainage;
    }

    public String getRootableDepth(){
        return this.soilRootableDepth;
    }

    public double getMaximumSoilPH(){
        return this.maximumSoilPH;
    }

    public double getMinimumSoilPH(){
        return this.minimumSoilPH;
    }

    public double getMinTemperature(){
        return this.minimumTemperature;
    }

    public double getMaxTemperature(){
        return this.maximumTemperature;
    }



    
}
