package com.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.AggregationDAO;
import com.dao.AirfieldsDAO;
import com.dao.RoadsDAO;
import com.dao.UrbanAreasDAO;
import com.model.Coordinates;

@WebServlet("/aggregation")

public class AggregationController extends HttpServlet{

    protected void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
        AggregationDAO aggregationDao = new AggregationDAO();
        RoadsDAO roadsDAO = new RoadsDAO();
        AirfieldsDAO airfieldsDAO = new AirfieldsDAO();
        UrbanAreasDAO urbanAreasDAO = new UrbanAreasDAO();
        
        Utility utility = new Utility();
        
        ArrayList<Coordinates> aggregationValues = aggregationDao.getAggregation(
            1000,
            1200,
            "well drained",
            "well",
            "deep",
            16,
            25
        );
        
        ArrayList<Coordinates> roadValues = roadsDAO.getRoads();
        ArrayList<Coordinates> airfieldValues = airfieldsDAO.getAirfields();
        ArrayList<Coordinates> urbanAreasValues = urbanAreasDAO.getUrbanAreas();
        
        for(int i=0;i<aggregationValues.size();i++){
            
            //Shortest road distance calculation
            for(int j=0;j<roadValues.size();j++){
                
                double distance = aggregationValues.get(i).Haversine(roadValues.get(j).getLatitude(), roadValues.get(j).getLongitude());

                //special computation for first iteration
                if(j==0){
                    aggregationValues.get(i).setShortestRoadDistance(distance);
                }else{
                    if(distance <= aggregationValues.get(i).getShortestRoadDistance()){
                        aggregationValues.get(i).setShortestRoadDistance(distance);
                    }
                }
            }
            
            //Shortest distance to urban areas
            for(int k=0;k<urbanAreasValues.size();k++){
                
                double distance = aggregationValues.get(i).Haversine(urbanAreasValues.get(k).getLatitude(), urbanAreasValues.get(k).getLongitude());

                //special computation for first iteration
                if(k==0){
                    aggregationValues.get(i).setShortestUrbanAreasDistance(distance);
                }else{
                    if(distance <= aggregationValues.get(i).getShortestUrbanAreasDistance()){
                        aggregationValues.get(i).setShortestUrbanAreasDistance(distance);
                    }
                }
            }
            
            //Shortest distance to airfields
            for(int x=0;x<airfieldValues.size();x++){
                
                double distance = aggregationValues.get(i).Haversine(airfieldValues.get(x).getLatitude(), airfieldValues.get(x).getLongitude());

                //special computation for first iteration
                if(x==0){
                    aggregationValues.get(i).setShortestAirfieldDistance(distance);
                }else{
                    if(distance <= aggregationValues.get(i).getShortestAirfieldDistance()){
                        aggregationValues.get(i).setShortestAirfieldDistance(distance);
                    }
                }
            }

        }

        
        
        
        utility.sendCoordinatesAsJson(response,aggregationValues);
    }
    
}
