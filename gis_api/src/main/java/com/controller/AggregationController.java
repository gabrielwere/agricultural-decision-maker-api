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

        //Analytic Hierarchy Process
        double[][] pairwiseMatrix = new double[3][3];

        //The pairwise comparison matrix should appear as follows
        //|             | URBAN AREAS |  ROADS | AIRFIELDS |
        //| URBAN AREAS |     1       |        |           |
        //| ROADS       |             |    1   |           |
        //| AIRFIELDS   |             |        |      1    |

        
        String option = "urban";

        double sumOfColumn1 = 0;
        // double sumOfColumn2 = 0;
        // double sumOfColumn3 = 0;

        //7 means strong importance
        //1 means equal importance
        switch(option){

            case "urban":
                //first row
                pairwiseMatrix[0][0] = 1;
                pairwiseMatrix[0][1] = 7;
                pairwiseMatrix[0][2] = 7;

                //second row
                pairwiseMatrix[1][0] = 1/7;
                pairwiseMatrix[1][1] = 1;
                pairwiseMatrix[1][2] = 1;

                //third row
                pairwiseMatrix[2][0] = 1/7;
                pairwiseMatrix[2][1] = 1;
                pairwiseMatrix[2][2] = 1;

                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){ 
                        sumOfColumn1 += pairwiseMatrix[j][i];
                    }
                    for(int x=0;x<3;x++){
                        for(int k=0;k<3;k++){ 
                            pairwiseMatrix[k][x] /= sumOfColumn1; 
                        }
                    }
                    
                }

                System.out.println(option);
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        System.out.printf("%f\t",pairwiseMatrix[i][j]);
                    }
                    System.out.println();
                }

        }
        
        utility.sendCoordinatesAsJson(response,aggregationValues);
    }
    
}
