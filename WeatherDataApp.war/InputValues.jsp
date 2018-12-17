<%@page contentType="text/html; charset=UTF-8" import="java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
Copyright 2018 Harold Fortuin of
   Fortuitous Consulting Services, Inc.

   You are free to use or modify this software and source code
   as long as you include this Copyright notice.

   No warranty is provided or implied. Use at your own risk.
 --%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Upcoming High and Low Temperatures</title>
</head>
<body>
<div style="text-align: center;">
<h1>Upcoming High and Low Temperatures</h1>

<c:set var="bostonWithLatLong" value="${applicationScope.BOSTON}" />
<c:set var="displayBoston" value="${fn:split(bostonWithLatLong, ';')}" />

<c:set var="adakWithLatLong" value="${applicationScope.ADAK}" />
<c:set var="displayAdak" value="${fn:split(adakWithLatLong, ';')}" />

<c:set var="saipanWithLatLong" value="${applicationScope.LOOKOUT_SAIPAN}" />
<c:set var="displaySaipan" value="${fn:split(saipanWithLatLong, ';')}" />

<form action = "WeatherDataServlet" method = "GET">
    <label>Choose a location within the USA:</label>
    <select name = "${applicationScope.LIST_US_LOCATIONS}">
        <option value = "BOSTON" selected><c:out value="${displayBoston[0]}" /></option>
        <option value = "ADAK"><c:out value="${displayAdak[0]}" /></option>
        <option value = "LOOKOUT_SAIPAN"><c:out value="${displaySaipan[0]}" /></option>
    </select>        
        
    <input type="submit"></input>
</form>

</div>
</body>
</html>
