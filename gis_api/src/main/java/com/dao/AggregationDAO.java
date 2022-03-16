package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.model.Coordinates;

public class AggregationDAO {

    String user = "postgres";
    String password = "ivar31";
    String url = "jdbc:postgresql://127.0.0.1:5432/sdb";

    public ArrayList<Coordinates> getAggregation(
        double minRainfallAmount,double maxRainfallAmount,
        String soilDrainage,String surfaceDrainage,String rootableDepth,
        double minTemperature,double maxTemperature
    ){
        Queries query = new Queries();
        String sql = query.aggregationQuery;

        ArrayList<Coordinates> coordinates = new ArrayList<Coordinates>();
        
        Coordinates newCoordinate = null;

        //load Driver
        try{
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException c){
            System.err.println("Load the driver!");
            c.printStackTrace();
        }

        //Establish connection and execute statement
        try(
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql)
        ){
            statement.setDouble(1, minRainfallAmount);
            statement.setDouble(2, maxRainfallAmount);
            statement.setDouble(3, minRainfallAmount);
            statement.setDouble(4, maxRainfallAmount);
            statement.setString(5, soilDrainage);
            statement.setString(6, surfaceDrainage);
            statement.setString(7, rootableDepth);
            statement.setString(8, soilDrainage);
            statement.setString(9, surfaceDrainage);
            statement.setString(10, rootableDepth);
            statement.setDouble(11, minTemperature);
            statement.setDouble(12, maxTemperature);

            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                double latitude = rs.getDouble("lat");
                double longitude = rs.getDouble("long");

                newCoordinate= new Coordinates(latitude, longitude);
                coordinates.add(newCoordinate);
            }
            connection.close();

        }catch(SQLException e){
            System.err.format("SQL state : \n%s\n%s\n",e.getSQLState(),e.getMessage());
        }
        return coordinates;


    }
    
}
