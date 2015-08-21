/***
 * SMTestUtil.java by Nancy M. Schorr, 2015
 * 
 * This is a set of Utilities used by SMTest.java.
 *
 * Please see Readme.rtf or Readme.txt for more information.
 * 
 */

package com.nmschorr.SMedia;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

import static java.lang.System.out;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By; 
import org.openqa.selenium.Point; 
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.Select;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef.HWND;


// note: you need to have a Zacks.com acct & be logged in at zacks.com


@SuppressWarnings("unused")
public class SMTestUtil {
	protected static WebDriver zDriver;
	public static Logger gLogger;
	protected final static String fString = "E:\\FirefoxTesting\\firefox.exe";
	protected final static String portfolioTitle = "Stock Portfolio Management - Zacks Investment Research";    
	protected final static String theTitle = "Zacks Investment Research: Stock Research, Analysis, & Recommendations";
	protected final static String portfolioUrl = "http://www.zacks.com/portfolios/my-stock-portfolio/";
	protected final static String zacksMainUrl = "http://208.65.116.3";
	//protected final static String zacksMainUrl = "http://www.zacks.com/";
	private final static String dirname = "C:\\Users\\user\\git2\\ZacksProject\\PropertyFiles\\" ;
	private final static String pname = "Zacksprops.properties" ;
	private final static String propname = dirname + pname ;

	private final static String outfileName = "E:\\Workspace\\firefox.log";	
	protected final static String PF_NAME = "testportfolio";	
	private final static String errorState = "DEBUG";
	protected final static Integer WAIT_TIME = 22;
	private static boolean acceptNextAlert = true;
	private File binaryFile;
	static Properties theProperties;
	static FirefoxProfile ffoxProfile;
	static String signinval;
	static String usernameval;
	static String pwordval;
	static FirefoxBinary ffBinary;
	static StringBuffer verificationErrors;
	static FileInputStream fiStream;

	class AlertThread extends Thread {  
		// This inner class will dismiss the Firefox crash alert
		@Override
		public void run(){  
			System.out.println("2nd thread is running...");  
			try {		
				Robot robot = new Robot();
				robot.delay(1000);		
				Thread.currentThread();
				Thread.sleep(6000);

				dismissFirefoxCrashAlert();  // closes Firefox error alerts

				Thread.currentThread();
				Thread.sleep(2000);
				//		following is alternate method
				//				robot.mouseMove(855, 351);    
				//				robot.mousePress(InputEvent.BUTTON1_MASK);
				//				robot.mouseRelease(InputEvent.BUTTON1_MASK);
			} catch (Exception e)      {   System.out.println(e); }
		}  
	}

	protected static void setWindowSize() {
		//	replaces: zDriver.manage().window().maximize();
		int screen_height;
		int screen_width;
		Toolkit localToolkit = Toolkit.getDefaultToolkit();

		screen_width = (int) localToolkit.getScreenSize().getWidth()-72;
		screen_height = (int) localToolkit.getScreenSize().getHeight()-392;  // make it shorter so we have some room

		Dimension screenResolution = new Dimension(screen_width, screen_height );	
		zDriver.manage().window().setPosition(new Point(0,0));
		zDriver.manage().window().setSize(screenResolution);
		//mySleep(1, Thread.currentThread());
	}

	protected WebDriver createDriver() throws AWTException, InterruptedException {
		System.out.println("Just entered createDriver()");
		String profPath = "C:\\Users\\user\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\a817ys2n.default";
		 
	     
		 File ff = new File(profPath);
		 FirefoxProfile ffprofil22 = new FirefoxProfile(ff);

			//myProfile.setPreference("signon.rememberSignons", false);
			//ffBinary = new FirefoxBinary(binaryFile);
			//ffoxProfile.setPreference("webdriver.firefox.bin", "E:\\FirefoxTesting\\firefox.exe");
		
		
		//AlertThread thread2=new AlertThread();  
		//thread2.start();  
		System.out.println("! Starting new FirefoxDriver !");
			//WebDriver localDriver = new FirefoxDriver(ffBinary,myProfile);	 // using this to see if bug goes away
			//WebDriver localDriver = new FirefoxDriver();	 // using this to see if bug goes away
		WebDriver localDriver = new FirefoxDriver(ffprofil22);
		
		System.out.println("Done creating FirefoxDriver!");
		//localDriver.manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.SECONDS); //for the entire test run
		return localDriver;
	}

	Logger createLogger()  {
		Logger aLogger = LogManager.getLogger(ZacksTest.class.getName());
		verificationErrors = new StringBuffer();
		aLogger.entry();
		aLogger.info("Logger has been set up.");
		return aLogger;
	}

	protected static  void  initStrings()  {
		signinval = initValue("signinval");
		usernameval = initValue("usernameval");
		pwordval = initValue("pwordval");
	}


	protected static String initValue(String val)  {
		String errorString = "ERROR - no value present";
		String newVal =  theProperties.getProperty(val, errorString);
		gLogger.info("The property value for " + val + " is " + newVal );    	
		return newVal;
	}


	protected static void printMethodName (Method aMethod) {
		gLogger.info("Running Method: " + aMethod.getName());	    		
	}	


	protected static void mySleep(int timewait, Thread tThread) {
		try {
			gLogger.info("Inside mySleep() " + "and waiting " + timewait);	
			tThread.sleep(timewait * 1000);	//sleep is in milliseconds
		} catch (Exception e) {
			System.out.println(e);
		} 
	} 

	
	protected static void createProperties()  {
		System.out.println("PROPNAME name is" + propname);
		try {	 fiStream = new FileInputStream(propname);
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


	protected static String closeAlertAndGetItsText() {
		try {
			Alert alert = zDriver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	} 


	protected void logoutZacks() throws Exception {  
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());
		// deleteCookies();		
		zDriver.findElement(By.id("logout")).click();
		assertEquals("Testing Log Out", "Log Out - Zacks.com", zDriver.getTitle());
	}


	protected void deleteCookies() throws Exception {
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());
		gLogger.info("Deleting Zacks Cookies");

		Set<Cookie> cookies = zDriver.manage().getCookies();
		Iterator<Cookie> itr = cookies.iterator();

		while (itr.hasNext()){
			Cookie c = itr.next();
			gLogger.info("Cookie Name: " + c.getName() + " --- " + "Cookie Domain: " + c.getDomain() + " --- " + "Cookie Value: " + c.getValue());
			gLogger.info("Deleting cookie: "  +  c.getName());
			zDriver.manage().deleteCookieNamed(c.toString());
			gLogger.info("Done with cookies.");
		} 	
	}


	protected static void createLogFile (FirefoxProfile fp) throws Exception {
		File outfile = new File(outfileName);
		if (!outfile.exists())  
			outfile.createNewFile();

		fp.setPreference("webdriver.log.driver", "DEBUG");
		fp.setPreference("webdriver.log.file", outfileName);
	}


	protected static void dismissFirefoxCrashAlert() {
		HWND hwnd = User32.INSTANCE.FindWindow
				(null, "Firefox"); // window title
		HWND hwndcontainer= User32.INSTANCE.FindWindow
				(null, "Plugin Container for Firefox"); // window title

		if (hwnd == null) {
			System.out.println("Firefoxdialog is not showing");
		}
		else{
			System.out.println("Firefoxdialog IS showing. Closing it now.");
			User32.INSTANCE.PostMessage(hwnd, WinUser.WM_CLOSE, null, null); 			
		}

		if (hwndcontainer == null) {
			System.out.println("FirefoxContainerDialog is not showing");
		}
		else{
			System.out.println("Firefoxdialog IS showing. Closing it now.");
			User32.INSTANCE.SetForegroundWindow(hwndcontainer);   // bring to front
			User32.INSTANCE.PostMessage(hwndcontainer, WinUser.WM_CLOSE, null, null); 			
			//User32.INSTANCE.ShowWindow(hwndcontainer, 9 );        // SW_RESTORE
		}
	}

	
	static void zfindAndType (String s, String t) {
		out.println("Finding element: " + s + " and typing: " + t);
		zDriver.findElement(By.name(s)).sendKeys(t);
	}

	
	boolean chklink() {
		zDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS); //for the entire test run
		try {
			zDriver.findElement(By.linkText("Delete this Portfolio"));
			zDriver.manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.SECONDS); //for the entire test run
			return true;
		}
		catch (Exception  e)
		{
			out.println("No portfolio found. " + e);
			zDriver.manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.SECONDS); //for the entire test run
			return false;
		}
	}
	public static void printMe(String toPrt) {
		out.println("Running this next: " + toPrt);
	}
}


