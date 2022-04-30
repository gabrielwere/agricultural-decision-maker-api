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
        String surfaceDrainage,
        double minTemperature,double maxTemperature,
        double minimumSoilPH,double maximumSoilPH
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
            statement.setDouble(1, minTemperature);
            statement.setDouble(2, maxTemperature);
            statement.setDouble(3, minimumSoilPH);
            statement.setDouble(4, maximumSoilPH);
            statement.setString(5, surfaceDrainage);
            statement.setDouble(6, minRainfallAmount);
            statement.setDouble(7, maxRainfallAmount);
            statement.setDouble(8, minimumSoilPH);
            statement.setDouble(9, maximumSoilPH);
            statement.setString(10, surfaceDrainage);
            statement.setDouble(11, minRainfallAmount);
            statement.setDouble(12, maxRainfallAmount);
           
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");

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
