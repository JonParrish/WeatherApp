/* Copyright 2018 Harold Fortuin of
   Fortuitous Consulting Services, Inc.

   You are free to use or modify this software and source code
   as long as you include this Copyright notice.

   No warranty is provided or implied. Use at your own risk.
*/
package com.fortuitous.weather;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.IllegalDataException;

// testing
import org.jdom2.input.SAXBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class WeatherDataProcessor {
	
	org.jdom2.Document jdomDocument = null;
	
	public WeatherDataProcessor(org.jdom2.Document aJDOMDocument)  { 
		this.jdomDocument = aJDOMDocument;
	}

	/** main() is a test client that can parse NWSExample.xml from command line from file system
	 * UPDATE TO DO: make a JUnit test class with its own main
	 * @param args[0] command line-passed filename
	*/
	public static void main(String[] args) {
         
        Document jdomDoc;
        WeatherDataProcessor wDataProcessor;
        ArrayList<WeatherDatum> weatherData;
 
        
        SAXBuilder builder = new SAXBuilder();

        WeatherDatum wDatum = null;
        
        try {
        	jdomDoc = builder.build(new File(args[0]));
        	wDataProcessor = new WeatherDataProcessor(jdomDoc);
        	weatherData = wDataProcessor.fromXmlToData();
        	
        	wDatum = weatherData.get(0);
        	// test conversion to JSP display fields for date vs. time zone
        	wDatum.convertZonedDateTimeToDateAndZone();
 
            // get the document type
            System.out.println(jdomDoc.getDocType());       	      	
        } catch (Exception e) {
        	e.printStackTrace();
		}
	} 
	
	
	public ArrayList<WeatherDatum> fromXmlToData() {
		
		Element dwmlRoot = null;
		Element elemData = null;
		Element elemTimeLayout = null;
		Element elemParameters = null;
		Element elemTemperature = null;;
		
		WeatherDatum wDatum = null;
		
		
		ArrayList<WeatherDatum> returnWeatherData = new ArrayList<WeatherDatum>();
		
		try {
			dwmlRoot = jdomDocument.getRootElement();
			
			// first, get date-time data
			elemData = dwmlRoot.getChild("data");
			// only the first one for now
			elemTimeLayout = elemData.getChild("time-layout");

	        /* get pair of "start-valid-time" children
	         * end times are not required for displayed result
	         * puts each start time in a WeatherDatum
	         * first set of elements 7 am - 7 pm local time ok for my purposes
	         */			
			List<Element> listStartValidTimes = elemTimeLayout.getChildren("start-valid-time");
			
			Element elemStartValidTime;
			
			// returned XML has 1:1 start-valid-time to end-valid-time elements
			ListIterator<Element> itStartTimesList = listStartValidTimes.listIterator();
			
			// convenience for debugging
			boolean bAddedToList = false;
			
			String strStartTimeText = "";
			
	        while (itStartTimesList.hasNext() ) {
	        	elemStartValidTime = itStartTimesList.next();

	        	strStartTimeText = elemStartValidTime.getTextTrim();
	        	
	            wDatum = new WeatherDatum();
	            // get the data+time as given by NWS API
	            wDatum.setYyyyMmDd(strStartTimeText);
	        	// create display items
	        	wDatum.convertZonedDateTimeToDateAndZone();
	        
	            bAddedToList = returnWeatherData.add(wDatum);
	        }
	        
	        // now all dates are in WeatherDatum's in the returnWeatherData
	        
	        // now get matching temperatures 

	        elemParameters = elemData.getChild("parameters");
	        List<Element> listElemTemperatures = elemParameters.getChildren("temperature");  
	        ListIterator<Element> itTemperatures = listElemTemperatures.listIterator();

	        String strTempType = "";
	        boolean bIsMaximum = false, bIsMinimum = false;
	        
	        while (itTemperatures.hasNext() ) {
	        	elemTemperature = itTemperatures.next();
	        	strTempType = elemTemperature.getAttributeValue("type");
	        	bIsMaximum = strTempType.equals("maximum");
	        	bIsMinimum = strTempType.equals("minimum");
	        	
	        	if ((!bIsMinimum) && (!bIsMaximum) )
	        		throw new IllegalDataException("bad temperature XML node: " + strTempType);
	        	else
	        		returnWeatherData = setTemperatureWeatherData(elemTemperature, bIsMaximum, returnWeatherData);		
	        } 
	        
		} catch (Exception e) {
            e.printStackTrace();
        } // end catch
		
		
		// all data filled into each WeatherDatum and ready to pass to JSP for display
		return returnWeatherData;		
	}
	
	private ArrayList<WeatherDatum> setTemperatureWeatherData(Element aElemTemperature, boolean bIsMaximum, ArrayList<WeatherDatum> aListWeatherDatum) 
		throws Exception {
        List<Element> listElemValues = aElemTemperature.getChildren("value");  
        int nSize = listElemValues.size();
        
        Element elemValue;
        String tempValue = "";
        int nWeatherDatumIdx = -1;
        WeatherDatum weatherDatum;
        
        for (nWeatherDatumIdx = 0; nWeatherDatumIdx < nSize; nWeatherDatumIdx++ ) {
        	// elemValue = itValues.next();
        	elemValue = listElemValues.get(nWeatherDatumIdx);
        	tempValue  = elemValue.getTextTrim();
        	int nTempValue = Integer.parseInt(tempValue);
        	
        	weatherDatum = aListWeatherDatum.get(nWeatherDatumIdx);
        	if (bIsMaximum)
        		weatherDatum.setMaximumTemperature(nTempValue);
        	else
        		weatherDatum.setMinimumTemperature(nTempValue);
        }
		
		return aListWeatherDatum;
	}
}
