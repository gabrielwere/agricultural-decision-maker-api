package com.controller;

import com.dao.RainfallDAO;
import com.model.Coordinates;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/rainfall")
public class RainfallController extends HttpServlet{

    protected void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
        RainfallDAO rainfallDao = new RainfallDAO();
        Utility utility = new Utility();

        ArrayList<Coordinates> rainfallValues = rainfallDao.getRainfall(1000,1200);
        

        utility.sendCoordinatesAsJson(response,rainfallValues);
    }
    
}
