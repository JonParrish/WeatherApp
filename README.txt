The WeatherDataApp is an example JavaEE Servlet 4.0/JSP/HTML 5 web application that calls a US National Weather Service
web service API to return up to 7 days of forecast high and low temperatures starting from tomorrow for a selected set of locations in the USA.
The locations' latitudes and longitudes are set with the web.xml along with other initialization data that is read in when the application starts for optimal performance.

From the default InputValues.jsp page, the user can select one of 3 locations widely scattered around US states and territories: Boston, Mass., Adak, Alaska,
and the Bird Lookout on the US territory of Saipan in the South Pacific. Upon submission of the choice location, the application's WeatherDataServlet manages
the call to the National Weather Service API, parses the resulting XML, and passes resulting data objects for display into a human-readable table in the Results.jsp final page.

The app was developed in the Eclipse Photon version IDE, using Java 10, several Apache Commons libraries, log4j, and JDOM2. 
Its JSP pages make use of the Standard Tag Libraries. It was only tested in Tomcat 9, but since it is strictly conformant to JavaEE 8 it should work with any conformant
JavaEE application server.

Deploy the app in your JavaEE application server by dropping it into the relevant directory of your application server.
If that does not work, try zipping the .war directory and then dropping it into the same directory (after removing the unzipped version).

Note that the "BAD_" XML files in the WAR's WEB-INF directory are for testing it with the validateConfigurationAgainstBuild,
which is setup to test the web.xml and beans.xml bundled files against the .class files under the WEB-INF/classes.

Along with some screen shots and this README.txt, source code is also available in a subdirectory. 
You are free to use or modify this application in accordance with the indicated license.


