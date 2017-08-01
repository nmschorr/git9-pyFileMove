SMedia Readme

SMedia uses Selenium to test a website called schorrmedia.com .

The tests are run with JUnit, so each test stands on its own and can be run independently of the others.  Each test launches a fresh instance of Firefox with Selenim Webdriver.  The tests use Maven and a Maven pom.xml file to specify dependencies.  The entire suite was written in and runs with Eclipse on a Windows 7 pc. 

The suite is comprised of two main java files. The main tests are in SMTest.java and the supporting code is in SMTestUtils.java.  The other supporting files are for interacting with the native interface - in this case - Windows 7. 

Several Java packages are made use of.  Log4j2 is in use to enhance the logging messages about the tests. The Selenium Surefire reporting mechanism is used for reporting.  

Firefox and Selenium have had a long series of bugs where at the launch of Firefox by Selenium an alert pops up stating that Firefox has crashed.  Manually dismissing that alert allow Selenium to continue and Firefox actually still launches.  Unfortunately, the alert pops up before Selenium has taken control of Firefox, so it is unable to dismiss that alert.  See: Bug 1165955 - FF38 - Running Selenium tests causes Firefox to "quit unexpectantly" https://bugzilla.mozilla.org/show_bug.cgi?id=1165955 

The thread that launches Firefox becomes stuck waiting for the alert to go away. This test suite handles those alerts by launching a separate thread, and that second thread uses the java jna libraries to find the alert and dismiss it. 
  


