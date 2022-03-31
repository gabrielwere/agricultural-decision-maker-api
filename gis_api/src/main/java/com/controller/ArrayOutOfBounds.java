package com.controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ArrayOutOfBounds")
public class ArrayOutOfBounds extends HttpServlet{

    protected void service(HttpServletRequest request,HttpServletResponse response) throws IOException{
        response.sendRedirect("error.jsp");
    }
    
}
