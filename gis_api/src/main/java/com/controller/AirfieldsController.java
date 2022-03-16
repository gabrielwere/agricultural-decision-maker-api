package com.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.AirfieldsDAO;

import java.io.IOException;
import java.util.ArrayList;

import com.model.Coordinates;


@WebServlet("/airfields")
public class AirfieldsController extends HttpServlet {
    
    protected void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
        AirfieldsDAO airfieldsDao = new AirfieldsDAO();
        Utility utility = new Utility();

        ArrayList<Coordinates> airfieldValues = airfieldsDao.getAirfields();
        

        utility.sendCoordinatesAsJson(response,airfieldValues);
    }
    
}
