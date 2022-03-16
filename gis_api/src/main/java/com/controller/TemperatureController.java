package com.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.TemperatureDAO;
import com.model.Coordinates;

@WebServlet("/temperature")
public class TemperatureController extends HttpServlet{

    protected void service(HttpServletRequest request,HttpServletResponse response) throws IOException{

        TemperatureDAO temperatureDao = new TemperatureDAO();
        Utility utility = new Utility();

        ArrayList<Coordinates> temperatureValues = temperatureDao.getTemperature(16, 24);

        utility.sendCoordinatesAsJson(response, temperatureValues);
    }
    
}
