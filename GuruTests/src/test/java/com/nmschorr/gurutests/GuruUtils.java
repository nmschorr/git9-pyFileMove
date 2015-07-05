/***
 * GuruUtils.java by Nancy M. Schorr, 2015
 * 
 * This is a set of Utilities used by TestGuruLogin.java.
 *
 * Please see README.md for more information.
 * 
 */
package com.nmschorr.gurutests;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.fail;

import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.*;


// Note: my have a properties directory

public class GuruUtils {
	final protected static String propsFileName = "./PropertiesFiles/localprops.properties" ;
	protected static Logger gLogger;
	protected FileInputStream fiStream;
	protected String browserLogName;
	public StringBuffer	verErrors;
	public static FirefoxProfile ffoxProfile;
	WebDriver myDriver;
	FirefoxBinary ffBinary;
	protected File binaryFile;
	public String baseUrl;
	public String userNameVal;
	public String pWordVal;
	
	public static void myWait(int timewait, Logger localLogger) throws Exception {
		localLogger.info("Inside myWait() " + "and waiting " + timewait);	
		Thread.sleep(timewait * 1000);	//sleep is in milliseconds
	}

	protected Logger createNewLogger () {
		Logger localLogger;
		localLogger = LogManager.getLogger(this.getClass().getName());
		localLogger.entry();
		localLogger.info("Inside createNewLogger");
		localLogger.info("Working Dir= " + System.getProperty("user.dir"));
		return localLogger;
	}

	public void setUpStrings(Properties localProperties) {
		System.out.println("in setupstrings & theProperties= " +localProperties.toString());
		try {
			userNameVal = getMyProp("usernameval", localProperties);
			pWordVal = getMyProp("pwordval", localProperties);
			// following line wrong pwordval
			//pWordVal = getMyProp("wrongpwordval", localProperties);
	
			baseUrl = getMyProp("baseurl", localProperties);
			System.out.println("new baseurl is: "+baseUrl);
			System.out.println("new pwordval is: "+pWordVal);
			System.out.println("new userNameVal is: "+userNameVal);
		}	 catch (NullPointerException e) {
			e.printStackTrace(System.out); 
		}			
	}

	public String getMyProp(String theVal, Properties aProp) {
		String newestVal = aProp.getProperty(theVal, "ERROR - No value");
		System.out.println("Inside getMyProp-Value for "+theVal+ ": " + newestVal );  
		return newestVal;	
	}
	
	public WebDriver setUpDriver() {
		URL gridHubUrl = null;
		String fString = "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe";
		WebDriver localDriver;
		ffoxProfile =  new FirefoxProfile(); 	
		//ffoxProfile.setPreference("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		binaryFile = new File(fString);
		ffBinary = new FirefoxBinary(binaryFile);


		// new remote driver code for Grid	
		DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();

		try {
			gridHubUrl = new URL("http://192.168.1.69:4444/wd/hub");
		} catch (Exception e)  {
			System.err.println(e);
		}
		// use the following for special Remote Web Driver testing
		//localDriver = new RemoteWebDriver(gridHubUrl,firefoxCapabilities);
		localDriver = new FirefoxDriver(ffBinary, ffoxProfile);	

		return localDriver;
	}

	public void createLogFile (FirefoxProfile fp, Properties aProp) throws Exception {
		browserLogName = aProp.getProperty("browserlogfile", "ERROR - No value");
		File outfile = new File(browserLogName);
		if (!outfile.exists())  
			outfile.createNewFile();
		fp.setPreference("webdriver.log.driver", "DEBUG");
		fp.setPreference("webdriver.log.file", browserLogName);
	}

	public Properties newProps(Logger myLogger) {  // create new properties file
		java.util.Properties localProps = null;
		try {
			localProps = new Properties();
		} catch (NullPointerException e) {
			e.printStackTrace(System.out);
		}
	    	    
		myLogger.info("Inside newProps() reading new properties file." );	

		try {
			fiStream = new FileInputStream(propsFileName);
			System.out.println (fiStream.toString());
			FileDescriptor myFD= fiStream.getFD();
			System.out.println(myFD.toString());
			System.out.println("propsFileName is: " + propsFileName);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		try {
			localProps.load(fiStream);
		} catch (Exception e) {
			fail("failure in setUp");
			e.printStackTrace(System.out);
		} finally {}

		try {
			fiStream.close();
		} catch (java.io.IOException e) {
			e.printStackTrace(System.out);
		}
		
		System.out.println("inside newProps() and props= "+localProps.toString());
		return localProps;
	}

	public void deleteCookies(Logger logger, WebDriver aDriver) throws Exception {
		class lc {};	    
		String myLocalMethod = lc.class.getEnclosingMethod().toString(); 
		logger.info("Running Method: " + returnMethodName(myLocalMethod));	    		

		Set<Cookie> cookies = aDriver.manage().getCookies();
		Iterator<Cookie> itr = cookies.iterator();

		while (itr.hasNext()){
			Cookie c = itr.next();
			logger.info("Cookie Name: " + c.getName() + " --- " + "Cookie Domain: " + c.getDomain() + " --- " + "Cookie Value: " + c.getValue());
			logger.info("Deleting cookie: "  +  c.getName());
			aDriver.manage().deleteCookieNamed(c.toString());
		} 	
	}
	
	public String returnMethodName (String aMethod) {
		//	    System.out.println("passed in string is: " + aMethod);
		String[] methodAreaArray = aMethod.split("\\("); 
		String firstPart = methodAreaArray[0];  // take 1st part before first (
		//	    System.out.println("firstPart in string is: " + firstPart);

		String[] methodStringArray = firstPart.split("\\."); 
		String methodStringShortened = methodStringArray[(methodStringArray.length)-1];
		//		System.out.println("method string shortened is : " + methodStringShortened);
		return methodStringShortened;
	}

}
