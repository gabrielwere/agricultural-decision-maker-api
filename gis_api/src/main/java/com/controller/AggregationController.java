package com.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.AggregationDAO;
import com.model.Coordinates;

@WebServlet("/aggregation")

public class AggregationController extends HttpServlet{

    protected void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
       AggregationDAO aggregationDao = new AggregationDAO();
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
        

        utility.sendCoordinatesAsJson(response,aggregationValues);
    }
    
}
