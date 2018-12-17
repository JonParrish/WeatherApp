/* Copyright 2018 Harold Fortuin of
   Fortuitous Consulting Services, Inc.

   You are free to use or modify this software and source code
   as long as you include this Copyright notice.

   No warranty is provided or implied. Use at your own risk.
*/
package com.fortuitous.weather.servlet;

import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

public class InitWeatherDataServlet extends HttpServlet {

    public static final String LIST_US_LOCATIONS = "LIST_US_LOCATIONS";
    
    /*	now in the web.xml...
    public static final String BOSTON = "BOSTON";
    public static final String ADAK = "ADAK";
    public static final String LOOKOUT_SAIPAN = "LOOKOUT_SAIPAN";
    */

    public static final long serialVersionUID = 5862956;

    public void init() throws ServletException {
        ServletConfig servletConfig = getServletConfig();

        // set its init parameters into applicaion Scope object ServletContext
    /* UPDATE TO DO: check the web-app_4_0.xsd - conformant way to set in app scope hopefully via web.xml;
	   else add Spring Boot to load app-scope variables
     */
        String latLong = "";
        String nameLocation = "";

        Enumeration<String> enumLocations = servletConfig.getInitParameterNames();
        
        ServletContext servletContext = getServletContext();
        
        while (enumLocations.hasMoreElements() ) {
            nameLocation = enumLocations.nextElement();
            latLong = servletConfig.getInitParameter( nameLocation );
            servletContext.setAttribute(nameLocation, latLong);
        }
    } // end init()

} // end class
