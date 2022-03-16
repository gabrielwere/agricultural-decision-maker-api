package com.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.RoadsDAO;

import java.io.IOException;
import java.util.ArrayList;

import com.model.Coordinates;

@WebServlet("/roads")
public class RoadsController extends HttpServlet{

    protected void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
        RoadsDAO roadsDao = new RoadsDAO();
        Utility utility = new Utility();

        ArrayList<Coordinates> roadValues = roadsDao.getRoads();
        

        utility.sendCoordinatesAsJson(response,roadValues);
    }
    
}
