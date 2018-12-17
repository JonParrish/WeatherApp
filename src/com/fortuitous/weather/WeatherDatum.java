
/* Copyright 2018 Harold Fortuin of
   Fortuitous Consulting Services, Inc.

   You are free to use or modify this software and source code
   as long as you include this Copyright notice.

   No warranty is provided or implied. Use at your own risk.
*/
package com.fortuitous.weather;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherDatum {

    private ZonedDateTime yyyyMmDd;
    private String dateOnly;
    private String timezoneOnly;
    
    private Integer maximumTemperature = 9999;
    private Integer minimumTemperature = -9999;

    /** multi-argument constructor not needed for web service hydration of instances
     * @param ayyyyMmDd         year-month-dayThh:mm+/-timezone as from the web service
     * @param aMaxTemperature   maximum daily temperature
     * @param aMinTemperature   minimum daily temperature
     */
    public WeatherDatum (ZonedDateTime aZonedDateTime, Integer aMaxTemperature, Integer aMinTemperature) {
        yyyyMmDd = aZonedDateTime;
        maximumTemperature = aMaxTemperature;
        minimumTemperature = aMinTemperature;
    }
    
    public WeatherDatum() {}

    public ZonedDateTime getYyyyMmDd() {
        return yyyyMmDd;
    }

    public void setYyyyMmDd(ZonedDateTime aYyyyMmDd) {
        yyyyMmDd = aYyyyMmDd;
    }
    
    // expects the specified format
    public void setYyyyMmDd(String aYyyyMmDdString) {
        yyyyMmDd = ZonedDateTime.parse(aYyyyMmDdString, DateTimeFormatter.ISO_OFFSET_DATE_TIME );         
    }    

    /** call only AFTER setYyyyMmDd() called 
     */
    public void convertZonedDateTimeToDateAndZone() {
    	
    	String temp = yyyyMmDd.toString();
    	/* sample strings:
    	 * 2018-12-06T07:00+10:00 (Bird Island)
    	 * 2018-12-05T07:00-05:00 (Boston)
    	 * 2018-12-06T07:00-09:00 (Adak, AK)
    	 */
    	
    	String[] stringArray = temp.split("T");
    	dateOnly = stringArray[0];
    	
    	// last 6 chars are time zone
    	timezoneOnly = stringArray[1].substring(5, stringArray[1].length() );
    }
    
    
    public String getDateOnly() {
		return dateOnly;
	}

	public void setDateOnly(String dateOnly) {
		this.dateOnly = dateOnly;
	}

	public String getTimezoneOnly() {
		return timezoneOnly;
	}

	public void setTimezoneOnly(String timezoneOnly) {
		this.timezoneOnly = timezoneOnly;
	}

	public Integer getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(Integer maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public Integer getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(Integer minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }
    
    /** support debugging as needed
     * 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
    	sb.append("Date=");
        sb.append(yyyyMmDd);
        
        sb.append("MaximumTemperature=");
        sb.append(maximumTemperature);
        
        sb.append("MinimumTemperature=");
        sb.append(minimumTemperature);
	
    	return sb.toString();
    }
    
}