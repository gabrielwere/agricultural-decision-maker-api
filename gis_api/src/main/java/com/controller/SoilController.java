package com.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.SoilDAO;
import com.model.Coordinates;


@WebServlet("/soil")
public class SoilController extends HttpServlet{

    protected void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
        SoilDAO soilDao = new SoilDAO();
        Utility utility = new Utility();

        ArrayList<Coordinates> soilValues = soilDao.getSoil("deep", "well drained", "well");

        utility.sendCoordinatesAsJson(response, soilValues);
    }
    
}
