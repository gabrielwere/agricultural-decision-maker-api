package com.dao;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Queries {

    public String rainfallQuery = "SELECT DISTINCT TRUNC(ST_Y(ST_CENTROID(geom))::NUMERIC,2) AS lat,TRUNC(ST_X(ST_CENTROID(geom))::NUMERIC,2) AS long FROM environmental.rainfall_vector WHERE rainfall_amount BETWEEN ? AND ?;";
    public String soilQuery = "SELECT DISTINCT TRUNC(ST_Y(ST_CENTROID(geom))::NUMERIC,2) AS lat,TRUNC(ST_X(ST_CENTROID(geom))::NUMERIC,2) AS long FROM environmental.soils WHERE sdra_descr = ? AND drai_descr = ? AND rdep_descr = ?;";
    public String temperatureQuery = "SELECT DISTINCT TRUNC(ST_Y(ST_CENTROID(geom))::NUMERIC,2) AS lat,TRUNC(ST_X(ST_CENTROID(geom))::NUMERIC,2) AS long FROM environmental.temp_vector WHERE temp_value BETWEEN ? AND ?;";
    public String aggregationQuery = finalString();

    //read long query from file
    public String loadFromFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get("C:/Users/Were/Documents/GIS DataBase/gis_api/src/main/java/com/dao/aggregation.sql")));
    }

    //call the method within a try catch bloack
    private String finalString(){
        try {
            String query = loadFromFile();
            return query;
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }
   
}
