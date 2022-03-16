package com.dao;

import java.util.ArrayList;

import java.sql.*;

import com.model.Coordinates;


public class TemperatureDAO {

    String user = "postgres";
    String password = "ivar31";
    String url = "jdbc:postgresql://127.0.0.1:5432/sdb";

    public ArrayList<Coordinates> getTemperature(double minTemperature,double maxTemperature){
        Queries queries = new Queries();
        String sql = queries.temperatureQuery;

        ArrayList<Coordinates> coordinates = new ArrayList<Coordinates>();

        Coordinates newCoordinate = null;

        //Load Driver
        try{
            Class.forName("org.postgresql.Driver");
         }catch(ClassNotFoundException c){
            System.err.println("Load the driver!");
             c.printStackTrace();
         }

         try(
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql)
        ){
            statement.setDouble(1, minTemperature);
            statement.setDouble(2, maxTemperature);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                double latitude = rs.getDouble("lat");
                double longitude = rs.getDouble("long");

                newCoordinate = new Coordinates(latitude,longitude);
                coordinates.add(newCoordinate);
            }
            connection.close();

        }catch(SQLException e){
            System.err.format("SQL state : \n%s\n%s\n",e.getSQLState(),e.getMessage());
        }


        return coordinates;
    }
    
}
