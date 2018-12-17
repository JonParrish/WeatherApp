/* Copyright 2018 Harold Fortuin of
   Fortuitous Consulting Services, Inc.

   You are free to use or modify this software and source code
   as long as you include this Copyright notice.

   No warranty is provided or implied. Use at your own risk.
*/
package com.fortuitous.weather.servlet;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import com.fortuitous.weather.USWeatherServiceClient;
import com.fortuitous.weather.WeatherDatum;

public class WeatherDataServlet extends HttpServlet {
	static final long serialVersionUID = 264050332L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // from the InputValues.jsp (the default page)
    	String[] locationCode;
    	locationCode = req.getParameterValues("listUSLocations[]");
        
        // get the lat/long from the ServletContext
        ServletContext servletContext = getServletContext();
        
        String latLong = (String)servletContext.getAttribute(locationCode[0]);
        
        // parse readable location, latitude and longitude
        String locationThenLatThenLong[] = latLong.split(";");
        
        // res left in for debugging
        ArrayList<WeatherDatum> weatherData = 
        	getSevenDayForecast(locationThenLatThenLong[1], locationThenLatThenLong[2]);
        
        req.setAttribute("weatherData", weatherData);
        req.setAttribute("readableLocation", locationThenLatThenLong[0]);
        
        RequestDispatcher reqDispatcher = req.getRequestDispatcher("/WEB-INF/Results.jsp");
        reqDispatcher.forward(req, res);
    }

/**
 * 
 * @param latitude
 * @param longitude
 * @param res
 * @return String of results in XML format
 * @throws IOException
 */
    private ArrayList<WeatherDatum> getSevenDayForecast(String latitude, String longitude) 
    								     throws IOException{
    	
        float fLat = Float.valueOf(latitude);
        float fLong = Float.valueOf(longitude);
        
        ArrayList<WeatherDatum> weatherData = null;
        
        USWeatherServiceClient uswcClient = new USWeatherServiceClient(fLat, fLong);
        weatherData = uswcClient.call();      

        return weatherData;
    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        doGet(req, res);

    }

} // end class
