package com.dao;

import java.util.ArrayList;

import com.model.Coordinates;

import java.sql.*;

public class AirfieldsDAO {

    String user = "postgres";
    String password = "ivar31";
    String url = "jdbc:postgresql://127.0.0.1:5432/sdb";

    public ArrayList<Coordinates> getAirfields(){
        Queries query = new Queries();
        String sql = query.airfieldsQuery;

        ArrayList<Coordinates> coordinates = new ArrayList<Coordinates>();

        Coordinates newCoordinate = null;

        try{
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException c){
            System.err.println("Load the Driver");
            c.printStackTrace();
        }

        try(
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql);
        ){

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
