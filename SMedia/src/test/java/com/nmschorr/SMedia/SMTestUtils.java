/*
 * SMTestUtil.java by Nancy M. Schorr, 2015
 * 
 * This is a set of Utilities used by SMTest.java.
 *
 * Please see Readme.md or Readme.txt for more information.
 * 
*/

package com.nmschorr.SMedia;

//import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.AWTException;
//import java.awt.Robot;

import static java.lang.System.out;
import static org.junit.Assert.fail;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Point; 
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
 //	
 	public static boolean showAlertBugMode = false;  // change to true to show FireFox alert bug
 										   // false is the normal default setting
 	
	class AlertThread extends Thread {           // Dismiss the Firefox crash alert
		public AlertThread (String tname) {
			super(tname);
		}
		
		@Override
		public void run(){  
			System.out.println("Inside run() of AlertThread method.");  
			try {		
				Thread.currentThread();
				Thread.sleep(6000);
							
				dismissFirefoxCrashAlert();  // closes Firefox error alerts
				Thread.currentThread();
				Thread.sleep(2000);
					//	following is alternate method for dismissing alert
					//				Robot robot = new Robot();
					//				robot.delay(1000);		
					//				robot.mouseMove(855, 351);    
					//				robot.mousePress(InputEvent.BUTTON1_MASK);
					//				robot.mouseRelease(InputEvent.BUTTON1_MASK);
			} catch (Exception e)      {   System.out.println(e); }
		}  
	}

	
	protected static void setWindowSize() {
		Dimension winSize = new Dimension(964, 590 );	
		zDriver.manage().window().setPosition(new Point(0,0));
		zDriver.manage().window().setSize(winSize);
	}

	
	protected WebDriver createDriver(Logger tLogger) throws AWTException, InterruptedException {
		tLogger.info("Just entered createDriver() and Starting new FirefoxDriver.");
		
		if ( showAlertBugMode == false ) {
			System.out.println("Starting new thread to handle windows alerts");
			// the following 2 lines will dismiss the Windows crash alert dialog
			AlertThread tAlertThread = new AlertThread("tAlertThread");  
			tAlertThread.start();  
		}
		WebDriver localDriver = new FirefoxDriver();	 // using this to see if bug goes away
		localDriver.manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.SECONDS); //for the entire test run
		tLogger.info("Done creating FirefoxDriver!");
		if ( showAlertBugMode == false ) {
			// make wait time long so we can play with the Windows alert 
			localDriver.manage().timeouts().implicitlyWait(9000, TimeUnit.SECONDS); //for the entire test run
		}
		return localDriver;
	}

	
	public static Logger createLogger()  {
		out.println("\n" + "Inside createLogger - Logger is being set up. New test setup beginning.");
		Logger aLogger = LogManager.getLogger("smtrace");
		verificationErrors = new StringBuffer();
		aLogger.info("Logger has been set up.");
		return aLogger;
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
			System.out.println(e);
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

	protected static void createLogFile (FirefoxProfile fp) throws Exception {
		File outfile = new File(outfileName);
		if (!outfile.exists())  
			outfile.createNewFile();

		fp.setPreference("webdriver.log.driver", "DEBUG");
		fp.setPreference("webdriver.log.file", outfileName);
	}
		
	protected static void dismissFirefoxCrashAlert() {
		bLogger.info("Inside dismissFirefoxCrashAlert");
		HWND hwnd = User32.INSTANCE.FindWindow (null, "Firefox"); // window title
		String containerStr = "Plugin Container for Firefox";
		HWND hwndcontainer= User32.INSTANCE.FindWindow (null, containerStr);  

		if (hwnd == null) {
			bLogger.info("Firefoxdialog is not showing");
		}
		else {
			bLogger.info("Firefoxdialog IS showing. Closing it now.");
			User32.INSTANCE.PostMessage(hwnd, WinUser.WM_CLOSE, null, null); 			
		}

		if (hwndcontainer == null) {
			bLogger.info("FirefoxContainerDialog is not showing");
		}
		else {
			bLogger.info("FirefoxContainerDialog IS showing. Closing it now.");
			User32.INSTANCE.SetForegroundWindow(hwndcontainer);   // bring to front
			User32.INSTANCE.PostMessage(hwndcontainer, WinUser.WM_CLOSE, null, null); 			
		}
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


