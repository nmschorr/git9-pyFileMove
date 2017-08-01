GuruTests Readme

GuruTests logs onto guru99.com - a learning website for programming languages. The username and password are stored in a properties file.  In order to run these tests, you need to create your own free account at guru99.com and replace the values in the properties file with your own login and password. The properties file is named "localprops.properties" in the "PropertiesFiles" project directory.  

The tests are run with Junit, so each test stands on its own and can be run independently of the others.  Each test launches a fresh instance of Firefox with Selenim Webdriver.  The tests use Maven and a Maven pom.xml file to specify dependencies.  The entire suite was written in and runs with Eclipse on a Windows 7 pc. 

The suite is comprised of two main java files - GuruUtils.java and TestGuruLogin.java.  The main tests are in TestGuruLogin.java and the supporting code is in GuruUtils.java.  The other supporting files are for interacting with the native interface - in this case - Windows 7.  

Several Java packages are made use of.  Log4j is in use to enhance the logging messages about the tests.  The Java Reflection api is used to return method names for reporting.  The Selenium Surefire reporting mechanism is used for reporting.  


