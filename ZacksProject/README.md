ZacksTests Readme

ZacksTests uses Selenium to test a user experience at Zacks.com - a financial website that tracks stocks and financial data. The username and password are stored in a properties file.  In order to run these tests, you need to create your own free account at Zacks.com and replace the values in the properties file with your own login and password. The properties file is named "Zacksprops.properties" in the "PropertiesFiles" project directory.  

The tests are run with Junit, so each test stands on its own and can be run independently of the others.  Each test launches a fresh instance of Firefox with Selenim Webdriver.  The tests use Maven and a Maven pom.xml file to specify dependencies.  The entire suite was written in and runs with Eclipse on a Windows 7 pc. 

The suite is comprised of two main java files - TestZacks.java and ZacksUtils.java.  The main tests are in TestZacks.java and the supporting code is in ZacksUtils.java.  The other supporting files are for interacting with the native interface - in this case - Windows 7. 

Several Java packages are made use of.  Log4j is in use to enhance the logging messages about the tests.  The Java Reflection api is used to return method names for reporting.  The Selenium Surefire reporting mechanism is used for reporting.  

Firefox and Selenium have had a long series of bugs where at the launch of Firefox by Selenium an alert pops up stating that Firefox has crashed.  Manually dismissing that alert allow Selenium to continue and Firefox actually still launches.  Unfortunately, the alert pops up before Selenium has taken control of Firefox, so it is unable to dismiss that alert.  See: Bug 1165955 - FF38 - Running Selenium tests causes Firefox to "quit unexpectantly" https://bugzilla.mozilla.org/show_bug.cgi?id=1165955 

The thread that launches Firefox becomes stuck waiting for the alert to go away. This test suite handles those alerts by launching a separate thread, and that second thread uses the java jna libraries to find the alert and dismiss it. 
  
To do: include:  list of files and directories.


