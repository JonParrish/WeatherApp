/* Copyright 2018 Harold Fortuin of
   Fortuitous Consulting Services, Inc.

   You are free to use or modify this software and source code
   as long as you include this Copyright notice.

   No warranty is provided or implied. Use at your own risk.
*/
package com.fortuitous.weather;

import java.util.ArrayList;
import java.time.LocalDate;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;


// @Stateless
public class USWeatherServiceClient {
	private static final String BASE_WEATHER_SERVICE_URL = 
			"https://graphical.weather.gov/xml/sample_products/browser_interface/ndfdXMLclient.php";
	
	// set bogus initial values
	private float fLat = 400.0f;
	private float fLong = 400.0f;
	
	public USWeatherServiceClient(float lati, float longi) {
		this.fLat = lati;
		this.fLong = longi;
	}
	
	// JAX-WS approach - decided against that - XML to object mapping not so straightforward
	
	public ArrayList<WeatherDatum> call() {
	       
		/* useful when returning raw XML instead of parsed data
		StringBuilder strBuilder = new StringBuilder();
		*/
	        
	    HttpURLConnection conn=null;
	    BufferedReader reader=null;
	    String urlWithQueryParameters = addQueryParameters();
	        
	    SAXBuilder saxBuilder = null;
	    org.jdom2.Document jdomDoc = null;
	    
	    WeatherDataProcessor weatherDataProcessor = null;
	    ArrayList<WeatherDatum> returnWeatherData = null;
	        
	        try{  
	            URL url = new URL(urlWithQueryParameters);  
	            conn = (HttpURLConnection)url.openConnection();  
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Accept", "application/xml");
	            
	            if (conn.getResponseCode() != 200) {
	                throw new RuntimeException("HTTP GET Request Failed with Error code : "
	                              + conn.getResponseCode());
	            }
	            
	            /* Read the content from the defined connection
	               Using IO Stream with Buffer raise highly the efficiency of IO
	            */
		        reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
	            
		        saxBuilder = new SAXBuilder();
		        jdomDoc = saxBuilder.build(reader);
		        
		        weatherDataProcessor = new WeatherDataProcessor(jdomDoc);
		        returnWeatherData = weatherDataProcessor.fromXmlToData();
		        
		        /* to print out raw XML
		        String output = null;  
	            while ((output = reader.readLine()) != null)  
	                strBuilder.append(output);
	                */  
	        }  catch(JDOMException e) {  
	            e.printStackTrace();   
	        } catch(MalformedURLException e) {  
	            e.printStackTrace();   
	        } catch(IOException e){  
	            e.printStackTrace();   
	        }
	        finally
	        {
	            if(reader!=null)
	            {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            if(conn!=null)
	            {
	                conn.disconnect();
	            }
	        }

	        // return strBuilder.toString();  
	        return returnWeatherData;
		
	}
	
	
	
/**  build a request returning up to 1 week of data - starting tomorrow for 7 days, but sometimes fewer days

https://graphical.weather.gov/xml/sample_products/browser_interface/ndfdXMLclient.php?
lat=42.31&
lon=-71.025&
product=time-series&
begin=2018-08-14T00:00:00&
end=2018-08-20T00:00:00&
maxt=maxt&mint=mint
*/
	private String addQueryParameters() {
		StringBuilder sbURL = new StringBuilder(BASE_WEATHER_SERVICE_URL);
		sbURL.append('?');
		sbURL.append("lat=" + fLat + '&');
		sbURL.append("lon=" + fLong + '&');
		sbURL.append("product=time-series&");
				
		LocalDate tomorrowsDate = java.time.LocalDate.now();
		tomorrowsDate = tomorrowsDate.plusDays(1);
		
		LocalDate aWeekLaterDate = tomorrowsDate.plusDays(7);
		final String midnightTime = "T00:00:00&";
		
		sbURL.append("begin=" + tomorrowsDate.toString() + midnightTime);
		sbURL.append("end=" + aWeekLaterDate.toString() + midnightTime);
		sbURL.append("maxt=maxt&mint=mint&");
		
		return sbURL.toString();
	}
} // end class
