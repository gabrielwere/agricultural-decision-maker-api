package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

        
        String option = "roads";
        int numberOfPoints = 100;

        double sumOfColumn;
        int i;
        int j;

        //7 means strong importance
        //1 means equal importance
        switch(option){

            case "urban":
                //first row
                pairwiseMatrix[0][0] = 1;
                pairwiseMatrix[0][1] = 7;
                pairwiseMatrix[0][2] = 7;

                //second row
                pairwiseMatrix[1][0] = 1/7.0;
                pairwiseMatrix[1][1] = 1;
                pairwiseMatrix[1][2] = 1;

                //third row
                pairwiseMatrix[2][0] = 1/7.0;
                pairwiseMatrix[2][1] = 1;
                pairwiseMatrix[2][2] = 1;


                for(i=0;i<3;i++){

                    sumOfColumn = 0.0;

                    for(j=0;j<3;j++){ 
                        sumOfColumn += pairwiseMatrix[j][i];
                    }

                    for(j=0;j<3;j++){
                        pairwiseMatrix[j][i] /= sumOfColumn;
                    } 
                }
                break;

            case "roads":
                //first row
                pairwiseMatrix[0][0] = 1;
                pairwiseMatrix[0][1] = 1/7.0;
                pairwiseMatrix[0][2] = 1;
 
                //second row
                pairwiseMatrix[1][0] = 7;
                pairwiseMatrix[1][1] = 1;
                pairwiseMatrix[1][2] = 7;
 
                //third row
                pairwiseMatrix[2][0] = 1;
                pairwiseMatrix[2][1] = 1/7.0;
                pairwiseMatrix[2][2] = 1;
 
                for(i=0;i<3;i++){
 
                    sumOfColumn = 0.0;
 
                    for(j=0;j<3;j++){ 
                        sumOfColumn += pairwiseMatrix[j][i];
                    }
 
                    for(j=0;j<3;j++){
                        pairwiseMatrix[j][i] /= sumOfColumn;
                    } 
                }
                break;
            
            case "airfields":
                //first row
                pairwiseMatrix[0][0] = 1;
                pairwiseMatrix[0][1] = 1;
                pairwiseMatrix[0][2] = 1/7.0;
 
                //second row
                pairwiseMatrix[1][0] = 1;
                pairwiseMatrix[1][1] = 1;
                pairwiseMatrix[1][2] = 1/7.0;
 
                //third row
                pairwiseMatrix[2][0] = 7;
                pairwiseMatrix[2][1] = 7;
                pairwiseMatrix[2][2] = 1;
 
                for(i=0;i<3;i++){
 
                    sumOfColumn = 0.0;
 
                    for(j=0;j<3;j++){ 
                        sumOfColumn += pairwiseMatrix[j][i];
                    }
 
                    for(j=0;j<3;j++){
                        pairwiseMatrix[j][i] /= sumOfColumn;
                    } 
                }
                break;
            
            default:
                //first row
                pairwiseMatrix[0][0] = 1;
                pairwiseMatrix[0][1] = 1;
                pairwiseMatrix[0][2] = 1;
 
                //second row
                pairwiseMatrix[1][0] = 1;
                pairwiseMatrix[1][1] = 1;
                pairwiseMatrix[1][2] = 1;
 
                //third row
                pairwiseMatrix[2][0] = 1;
                pairwiseMatrix[2][1] = 1;
                pairwiseMatrix[2][2] = 1;
        }

        double weightOfUrban;
        double weightOfRoads;
        double weightOfAirfields;

        double sumOfRow1 = 0.0;
        double sumOfRow2 = 0.0;
        double sumOfRow3 = 0.0;


        for(i=0;i<3;i++){
            sumOfRow1 += pairwiseMatrix[0][i];
        }
        weightOfUrban = sumOfRow1/3;

        for(i=0;i<3;i++){
            sumOfRow2 += pairwiseMatrix[1][i];
        }
        weightOfRoads = sumOfRow2/3;

        for(i=0;i<3;i++){
            sumOfRow3 += pairwiseMatrix[2][i];
        }
        weightOfAirfields = sumOfRow3/3;

        System.out.println(weightOfUrban);
        System.out.println(weightOfRoads);
        System.out.println(weightOfAirfields);

        //normalize the values
        normalizeValues(aggregationValues);
        

        //calcuate preferemce score for each coordinate
        //using the weighted sum model
        double preferenceScore;

        for(i=0;i<aggregationValues.size();i++){
            double weightedRoadPerformanceValue = aggregationValues.get(i).getShortestRoadDistance() * weightOfRoads;
            double weightedAirfieldPerformanceValue = aggregationValues.get(i).getShortestAirfieldDistance() * weightOfAirfields;
            double weightedUrbanPerformanceValue = aggregationValues.get(i).getShortestUrbanAreasDistance() * weightOfUrban;

            preferenceScore = weightedRoadPerformanceValue + weightedAirfieldPerformanceValue + weightedUrbanPerformanceValue;
            aggregationValues.get(i).setPreferenceScore(preferenceScore);
        }

        Collections.sort(aggregationValues,new Comparator<Coordinates>(){
            public int compare(Coordinates cood1,Coordinates cood2){
                return Double.compare(cood2.getPreferenceScore(), cood1.getPreferenceScore());
            }
        });

        ArrayList<Coordinates> bestCoordinates = new ArrayList<Coordinates>();


        if(numberOfPoints > aggregationValues.size()){
            throw new ArrayIndexOutOfBoundsException();
        }else{ 
            for(i=0;i<numberOfPoints;i++){
                bestCoordinates.add(aggregationValues.get(i));
            }
        }
        
        //send normalised values as json
        utility.sendCoordinatesAsJson(response,bestCoordinates);
    }

    protected void normalizeValues(ArrayList<Coordinates> coordinates){
        double smallestRoadDistance = getSmallestRoadDistance(coordinates);
        double smallestUrbanDistance = getSmallestUrbanDistance(coordinates);
        double smallestAirfieldDistance = getSmallestAirfieldDistance(coordinates);

        int i;
        double current;
        double normalized;
        for(i=0;i<coordinates.size();i++){
            current = coordinates.get(i).getShortestRoadDistance();
            normalized = smallestRoadDistance/current;
            coordinates.get(i).setShortestRoadDistance(normalized);
        }

        for(i=0;i<coordinates.size();i++){
            current = coordinates.get(i).getShortestUrbanAreasDistance();
            normalized = smallestUrbanDistance/current;
            coordinates.get(i).setShortestUrbanAreasDistance(normalized);
        }

        for(i=0;i<coordinates.size();i++){
            current = coordinates.get(i).getShortestAirfieldDistance();
            normalized = smallestAirfieldDistance/current;
            coordinates.get(i).setShortestAirfieldDistance(normalized);
        }

    }

    protected double getSmallestRoadDistance(ArrayList<Coordinates> coordinates){
        double smallest = coordinates.get(1).getShortestRoadDistance();

        int i;
        for(i=0;i<coordinates.size();i++){
            if(coordinates.get(i).getShortestRoadDistance() < smallest){
                smallest = coordinates.get(i).getShortestRoadDistance();
            }
        }

        return smallest;
    }

    protected double getSmallestUrbanDistance(ArrayList<Coordinates> coordinates){
        double smallest = coordinates.get(1).getShortestUrbanAreasDistance();

        int i;
        for(i=0;i<coordinates.size();i++){
            if(coordinates.get(i).getShortestUrbanAreasDistance() < smallest){
                smallest = coordinates.get(i).getShortestUrbanAreasDistance();
            }
        }

        return smallest;
    }

    protected double getSmallestAirfieldDistance(ArrayList<Coordinates> coordinates){
        double smallest = coordinates.get(1).getShortestAirfieldDistance();

        int i;
        for(i=0;i<coordinates.size();i++){
            if(coordinates.get(i).getShortestAirfieldDistance() < smallest){
                smallest = coordinates.get(i).getShortestAirfieldDistance();
            }
        }

        return smallest;
    }
    
}
