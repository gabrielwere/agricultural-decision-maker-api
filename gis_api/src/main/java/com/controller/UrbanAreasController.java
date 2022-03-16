package com.controller;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UrbanAreasDAO;

import java.io.IOException;
import java.util.ArrayList;

import com.model.Coordinates;


@WebServlet("/urban")
public class UrbanAreasController extends HttpServlet{
    protected void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
        UrbanAreasDAO urbanAreasDao = new UrbanAreasDAO();
        Utility utility = new Utility();

        ArrayList<Coordinates> urbanAreasValues = urbanAreasDao.getUrbanAreas();
        

        utility.sendCoordinatesAsJson(response,urbanAreasValues);
    }
}
