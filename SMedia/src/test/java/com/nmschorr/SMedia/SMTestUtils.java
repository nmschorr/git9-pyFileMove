/*
 * SMTestUtil.java by Nancy M. Schorr, 2015
 * 
 * This is a set of Utilities used by SMTest.java.
 *
 * Please see Readme.md or Readme.txt for more information.
 * 
*/

package com.nmschorr.SMedia;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.AWTException;

import static java.lang.System.out;
import static org.junit.Assert.fail;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Point; 
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriver.Timeouts.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;


// @SuppressWarnings("unused")
public class SMTestUtils {
	protected static WebDriver zDriver;
 	public static Logger gLogger;
	protected final static String baseUrl = "http://jetgalaxy.com/wordpress/";
	private final static String dirname = "C:\\Users\\user\\git2\\SMedia\\PropertyFiles\\" ;
	private final static String pname = "SMedia.properties" ;
	private final static String propFileName = dirname + pname ;
	private final static String outfileName = "E:\\Workspace\\firefox.log";	
	protected final static Integer WAIT_TIME = 12;
	static Properties theProperties;
	static String signinval;
	static StringBuffer verificationErrors;
	static FileInputStream fiStream;
 	public static Logger bLogger =  LogManager.getLogger("smtrace");
 	public static boolean showAlertBugMode = false;  // change to true to show FireFox alert bug
 										   // false is the normal default setting

 	
 //----11111---first need this -------
 		protected static void dismissFirefoxCrashAlert() {
 			bLogger.info("Inside dismissFirefoxCrashAlert");
 			HWND hwnd = User32.INSTANCE.FindWindow (null, "Firefox"); // window title
 		
 			if (hwnd == null) {
 				bLogger.info("Firefoxdialog is not showing");
 			}
 			else {
 				bLogger.info("Firefoxdialog IS showing. Closing it now.");
 				User32.INSTANCE.PostMessage(hwnd, WinUser.WM_CLOSE, null, null); 			
 			}
 		}
 //----222222------need class to create 2nd thread----
	class AlertThread extends Thread {           // Dismiss the Firefox crash alert
		public AlertThread (String tname) {
			super(tname);
		}
		
		@Override
		public void run(){  
			System.out.println("Inside run() of AlertThread method.");  
			try {		
				Thread.sleep(9000);   // a wait to give time for the alert to appear
							          // when you call this whatever thread is current will grab it
				dismissFirefoxCrashAlert();  // closes Firefox error alerts
				Thread.sleep(2000);   // give time for alert to go away before continuing other thread
				} catch (Exception e)      {   System.out.println(e); }
		}  
	}

	
//---33333----need place to run second thread-------		
	protected WebDriver createDriver(Logger myLogger) throws AWTException, InterruptedException {
		myLogger.debug("Just entered createDriver() and Starting new FirefoxDriver.");
		
		if ( showAlertBugMode == false ) {
			myLogger.debug("Starting new thread to handle windows alerts");
					// the following 2 lines will dismiss the Windows crash alert dialog
			AlertThread tAlertThread = new AlertThread("tAlertThread");  
			tAlertThread.start();  
		}
		FirefoxProfile p = new FirefoxProfile();
		p.setPreference("webdriver.log.file", "C:\\tmp\\firefox_console.log");
		WebDriver localDriver = new FirefoxDriver(p);	 // using this to see if bug goes away
		localDriver.manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.SECONDS); //for the entire test run
	    myLogger.debug("Done creating FirefoxDriver!");
		return localDriver;
	}

		
	
	protected static void setWindowSize() {
		Dimension winSize = new Dimension(964, 590 );	
		zDriver.manage().window().setPosition(new Point(0,0));
		zDriver.manage().window().setSize(winSize);
	}


	public static Logger createLogger()  {
		Logger aLogger = LogManager.getRootLogger();
		aLogger.debug("\n" + "Inside createLogger - Logger is being set up. New test setup beginning.");
	 
		verificationErrors = new StringBuffer();
		aLogger.info("Logger has been set up.");
		return aLogger;
	}

	protected static void createLogFile (FirefoxProfile fp) throws Exception {
		File outfile = new File(outfileName);
		if (!outfile.exists())  
			outfile.createNewFile();

		fp.setPreference("webdriver.log.driver", "DEBUG");
		fp.setPreference("webdriver.log.file", outfileName);
	}

	


	protected static void mySleep(int timewait) {
		try {
			//gLogger.info("Inside mySleep() " + "and waiting " + timewait);	
			Thread.sleep(timewait * 300);	//sleep is in milliseconds
		} catch (Exception e) {
			System.out.println(e);
		} 
	} 
	
	
	protected static void createProperties()  {
		System.out.println("PROPNAME name is: " + propFileName);
		try {	 fiStream = new FileInputStream(propFileName);
		} catch (FileNotFoundException e) {  
			out.println(e);
			fail("failure in createProperties()");
		}
		try {
			theProperties = new Properties();
			theProperties.load(fiStream);
			fiStream.close();
		} catch (Exception e) {
			System.out.println(e);
			fail("failure in createProperties()");
		} 
	}

	public static void checkRunValue()  {
		String newVal =  theProperties.getProperty("showAlertBugMode", "error");
		System.out.println("The property value is " + newVal ); 
		if (newVal.equals("1"))
			showAlertBugMode = true;
		else showAlertBugMode = false;
	}

		
	
	public static void printMe(String toPrt) {
		out.println("Running this next: " + toPrt);
	}
}

		//	protected static void initStringProps()  {
		//		//String errorString = "ERROR - no value present";
		//		String newVal="";
		//		//String myval = String.getString("myval");
		//		//gLogger.info("The property value for " + val + " is " + intVal );    	
		//	}

//protected static void dismissFirefoxCrashAlert() {
//	bLogger.info("Inside dismissFirefoxCrashAlert");
//	HWND hwnd = User32.INSTANCE.FindWindow (null, "Firefox"); // window title
//	String containerStr = "Plugin Container for Firefox";
//	HWND hwndcontainer= User32.INSTANCE.FindWindow (null, containerStr);  
//
//	if (hwnd == null) {
//		bLogger.info("Firefoxdialog is not showing");
//	}
//	else {
//		bLogger.info("Firefoxdialog IS showing. Closing it now.");
//		User32.INSTANCE.PostMessage(hwnd, WinUser.WM_CLOSE, null, null); 			
//	}
//
//	if (hwndcontainer == null) {
//		bLogger.info("FirefoxContainerDialog is not showing");
//	}
//	else {
//		bLogger.info("FirefoxContainerDialog IS showing. Closing it now.");
//		User32.INSTANCE.SetForegroundWindow(hwndcontainer);   // bring to front
//		User32.INSTANCE.PostMessage(hwndcontainer, WinUser.WM_CLOSE, null, null); 			
//	}
//}
//	following is alternate method for dismissing alert
//				Robot robot = new Robot(); robot.delay(1000);		
//				robot.mouseMove(855, 351);  robot.mousePress(InputEvent.BUTTON1_MASK);
//				robot.mouseRelease(InputEvent.BUTTON1_MASK);


