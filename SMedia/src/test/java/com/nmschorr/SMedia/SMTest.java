package com.nmschorr.SMedia;

/***
 * SMTest.java by Nancy M. Schorr, 2015
 * 
 * This test suite executes four tests against schorrmedia.com
 * 
 * Note: 
 *
 * The tests are run with Junit, so each test stands on its own and 
 * can be run independently of the others.  Each test launches a fresh 
 * instance of Firefox with Selenium WebDriver.  The tests use Maven and a 
 * Maven pom.xml file to specify dependencies.  The entire suite was written 
 * in and runs with Eclipse on a Windows 7 PC. 
 *
 * Please see README.md for more information.
 * 
 */



import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.runners.MethodSorters;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.WebElement;

import static java.lang.System.out;

import com.nmschorr.SMedia.SMTestUtils;

// note: you need to have an account and password at SM.com

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SMTest extends SMTestUtils {
	SMTestUtils zutil = new SMTestUtils();

	@Before  //run only once before all tests
	public void setUp() throws Exception {		
		//createProperties();
		gLogger = zutil.createLogger();
		zDriver = createDriver();
	}

	@Test
	public void Test1Menus() throws Exception {
		// eClass is an empty class there just for returning the local method name
		// test logging in only	    
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());
		
		CheckMenus();

	}

	//@Test
	public void Test2Links() throws Exception {
		// test adding a portfolio
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());

		CheckMenus();
	}

	//@Test
	public void Test3Search() throws Exception {
		// test modifying a portfolio
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());

		CheckMenus();

	} 

	//@Test
	public void Test4Guestbook() throws Exception {
		// test deleting a portfolio
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());

		CheckMenus();
	} 
	// end  

	// beginning of methods to support tests
	static boolean isElementPresent(By by) {
		    try {
		      zDriver.findElement(by);
		      return true;
		    } catch (NoSuchElementException e) {
		      return false;
		    }
		  }



	public static void CheckMenus() throws Exception {
		// method to add portfolio, must be logged in first
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod()); 	
		zDriver.get(baseUrl);
	    mySleep(2);
			
	    String homeMenuID = "li[id=menu-item-140]";
	    String websiteMenuID = "li[id=menu-item-143]";
	    String videoMenuID = "li[id=menu-item-141]";
	    String socialMenuID = "li[id=menu-item-142]";
	    String databaseMenuID = "li[id=menu-item-418]";
	    String aboutMenuID = "li[id=menu-item-419]";
	    
	    zDriver.findElement(By.cssSelector(homeMenuID));
	    assertEquals("Schorr Media provides internet and social media services to businesses", zDriver.getTitle());
	    out.println("just clicked Home");
	    
	    zDriver.findElement( By.cssSelector(websiteMenuID)   ).click();	
	    assert(zDriver.getTitle().contains("Websites"));
	    assert(zDriver.getTitle().contains("| Schorr Media"));
	    out.println("just clicked Websites");
	    
//	    List<WebElement> myarry2 = zDriver.findElements( By.cssSelector("li[id^=menu-item]"));  //works!!!!
//	    for (WebElement ww :   myarry2) {
//	    	System.out.println("attribute line: " + ww.getAttribute("id"));
//	    }
//	    By byobj = By.cssSelector("li[id=menu-item-141]" ) ;

	    
	    
	    zDriver.findElement( By.cssSelector(videoMenuID)   ).click();	
	    assert(zDriver.getTitle().contains("Video"));
	    out.println("just clicked Video");
	    
	    zDriver.findElement( By.cssSelector(socialMenuID)   ).click();	
	    assert(zDriver.getTitle().contains("Social"));
	    out.println("just clicked Social Media");
	    
	    zDriver.findElement( By.cssSelector(databaseMenuID)   ).click();	
	    assert(zDriver.getTitle().contains("Database"));
	    out.println("just clicked Database");
    
	    zDriver.findElement( By.cssSelector(aboutMenuID)   ).click();	
	    assert(zDriver.getTitle().contains("About"));
	    out.println("just clicked About Us");

		
		
	}

	public static void modifyPort() throws Exception {
		// modify portfolio - add shares
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());	

		printMe("zDriver.get(portfolioUrl)");
	}


	public void deletePortfolio ()  {
		// need to add check to sure the portfolio is there to delete
		out.println("Deleting test portfolio only if it exists already.");
		zDriver.get(baseUrl);
		zDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //for the entire test run
	}


	@After
	public void tearDown() throws Exception {
		gLogger.exit(false);
		AlertThread thread3=new AlertThread();  
		thread3.start();  
		gLogger.info("Quitting Webdriver and shutting down");
		zDriver.quit();
		mySleep(1);
		gLogger.info("All done with tests and exiting. Goodbye.");
	}

}
