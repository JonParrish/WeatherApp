<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
Copyright 2018 Harold Fortuin of
   Fortuitous Consulting Services, Inc.

   You are free to use or modify this software and source code
   as long as you include this Copyright notice.

   No warranty is provided or implied. Use at your own risk.
 --%>


<html lang="en">
<head>
    <meta charset="UTF-8"/>
 
    <title>Upcoming High and Low Temperatures for ${requestScope.readableLocation}</title>
</head>
<body>
<div style="text-align: center;">

<h1>Upcoming High and Low Temperatures <br/>for<br/> 
<c:out value="${requestScope.readableLocation}" /></h1>



Its time zone is <c:out value="${requestScope.weatherData.get(0).timezoneOnly}" /> (hh:mm) from <br/>
Coordinated Universal Time <p/>



<table border="1" align="center">
<tr>
  <th>Date</th>
  <th>Maximum</th>
  <th>Minimum</th>
</tr>
<c:forEach items="${requestScope.weatherData}" var="wDatum">
    <tr>
        <td ><c:out value="${wDatum.dateOnly}" /></td>
        <td align="center"><c:out value="${wDatum.maximumTemperature}" /></td>
        <td align="center"><c:out value="${wDatum.minimumTemperature}" /></td>
    </tr>
</c:forEach>
</table>

<p/>
Underlying XML web service data provided by the US National Weather Service.

</div>
</body>
</html>