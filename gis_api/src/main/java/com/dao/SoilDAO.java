package com.dao;

import java.util.ArrayList;

import java.sql.*;

import com.model.Coordinates;

public class SoilDAO {

    String user = "postgres";
    String password = "ivar31";
    String url = "jdbc:postgresql://127.0.0.1:5432/sdb";

    public ArrayList<Coordinates> getSoil(String rootableDepth,String soilDrainage,String surfaceDrainage,double minimumSoilPH,double maximumSoilPH){
        Queries query = new Queries();
        String sql = query.soilQuery;
        
        ArrayList<Coordinates> coordinates = new ArrayList<Coordinates>();

        Coordinates newCoordinate = null;

        //load driver
        try{
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException c){
            System.err.println("Load the driver!");
            c.printStackTrace();
        }

        //Execute statement
        try(
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql)
        ){

            statement.setString(1, surfaceDrainage);
            statement.setString(2, soilDrainage);
            statement.setString(3, rootableDepth);
            statement.setDouble(4, minimumSoilPH);
            statement.setDouble(5, maximumSoilPH);

            ResultSet rs = statement.executeQuery();
            while(rs.next()){

                double latitude = rs.getDouble("lat");
                double longitude = rs.getDouble("long");

                newCoordinate = new Coordinates(latitude,longitude);
                coordinates.add(newCoordinate);
            }
        }catch(SQLException e){
            System.err.format("SQL state : \n%s\n%s\n",e.getSQLState(),e.getMessage());
        }

        return coordinates;
    }
    
}
