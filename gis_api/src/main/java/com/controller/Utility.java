package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.model.Coordinates;


public class Utility {

    //Utility method to send coordinates data as json
    public void sendCoordinatesAsJson(HttpServletResponse response,ArrayList<Coordinates> list) throws IOException{

        response.setContentType("application/json");
        Gson gson = new Gson();

        String res = gson.toJson(list);
        PrintWriter out = response.getWriter();

        out.println(res);
        out.flush();
    }

   
    
}
