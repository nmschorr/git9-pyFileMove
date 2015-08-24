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
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.*;
import static java.lang.System.out;
import static org.hamcrest.CoreMatchers.containsString;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SMTest extends SMTestUtils {
	//SMTestUtils zutil = new SMTestUtils();
	String homeMenuID = "li[id=menu-item-400]";
	String websiteMenuID = "#menu-item-143 > a";
	String videoMenuID = "#menu-item-141 > a";
	String socialMenuID = "#menu-item-142 > a";
	String databaseMenuID = "#menu-item-418 > a";
	String aboutMenuID = "#menu-item-419 > a";

	@Before  //run only once before all tests
	public void setUp() throws Exception {		
		//createProperties();
		gLogger = createLogger();
		zDriver = createDriver();
	}

	//@Test
	public void Test1Menus() throws Exception {
		// eClass is an empty class there just for returning the local method name
		// test logging and menubar     
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());
		
		checkMainMenubar();

	}

	@Test
	public void Test2Links() throws Exception {
		// test adding a portfolio
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());

		checkLinks();
	}

	//@Test
	public void Test3Search() throws Exception {
		// test modifying a portfolio
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());

		checkMainMenubar();

	} 

	//@Test
	public void Test4Guestbook() throws Exception {
		// test deleting a portfolio
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());

		checkMainMenubar();
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

	 //asertThat, assertEquals


	void checkMainMenubar() throws Exception {
		// checks to see that all items in main menubar are functioning ok
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod()); 	
		zDriver.get(baseUrl);
		mySleep(2);


		String homeXpath = "html/body/div/div[2]/div[1]/div/ul/li[1]/a";
		String websiteXpath = "html/body/div/div[2]/div[1]/div/ul/li[2]/a]";
		String videoXpath = "html/body/div/div[2]/div[1]/div/ul/li[3]/a";
		String socialXpath = "html/body/div/div[2]/div[1]/div/ul/li[4]/a";
		String databaseXpath = "/html/body/div/div[2]/div[1]/div/ul/li[5]/a";
		String aboutXpath = "html/body/div/div[2]/div[1]/div/ul/li[6]/a";

		//Home menuitem	    
		zDriver.findElement( By.xpath(homeXpath)   ).click();	
		mySleep(2);
		out.println("just clicked Home"+"\n"+"title found is: " + zDriver.getTitle());
		assert(zDriver.getTitle().contains("Schorr Media provides internet"));

		//Websites menuitem
		zDriver.findElement( By.cssSelector(websiteMenuID)).click();    	    
		mySleep(2);
		out.println("just clicked Websites"+"\n"+"title found is: " + zDriver.getTitle());
		assert(zDriver.getTitle().contains("Websites"));

		zDriver.findElement( By.cssSelector(videoMenuID)   ).click();	
		mySleep(2);
		out.println("just clicked Video"+"\n"+"title found is: " + zDriver.getTitle());
		assert(zDriver.getTitle().contains("YouTube"));

		zDriver.findElement( By.cssSelector(socialMenuID)   ).click();	
		mySleep(2);
		out.println("just clicked Social Media"+"\n"+"title found is: " + zDriver.getTitle());
		assert(zDriver.getTitle().contains("Social"));

		zDriver.findElement( By.cssSelector(databaseMenuID)   ).click();	
		mySleep(2);
		out.println("just clicked Database"+"\n"+"title found is: " + zDriver.getTitle());
		assert(zDriver.getTitle().contains("Database"));

		zDriver.findElement( By.cssSelector(aboutMenuID)   ).click();	
		mySleep(2);
		out.println("just clicked About Us"+"\n"+"title found is: " + zDriver.getTitle());
		assert(zDriver.getTitle().contains("About"));
	}
	
	void checkLinks() {
		// checks to see that all links functioning ok
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod()); 	
		zDriver.get(baseUrl);
		mySleep(2);
		
		zDriver.findElement( By.cssSelector(websiteMenuID)).click();    	    
		mySleep(2);
		out.println("just clicked Websites"+"\n"+"title found is: " + zDriver.getTitle());

		zDriver.findElement(By.cssSelector(".entry>pre>span>a>span")).click();
		mySleep(2);
		out.println("just clicked Vitamin Center"+"\n"+"title found is: " + zDriver.getTitle());
	    assertThat( zDriver.getTitle(), containsString("Vitamin Center Agoura Hills"))  ;
	    // assertEquals("Vitamin Center Agoura Hills | Hereâ€™s To Your Health", zDriver.getTitle());
	    zDriver.navigate().back();
	    
		//zDriver.findElement(By.cssSelector(".entry>pre>span>a>span")).click();
	  	zDriver.findElement(By.xpath("//div[@id='post-139']/div/pre/span[2]/a/span")).click();
		mySleep(2);
		out.println("just clicked NCGR LA"+"\n"+"title found is: " + zDriver.getTitle());
	    assertEquals("NCGR LA", zDriver.getTitle());
	    zDriver.navigate().back();
    
	    zDriver.findElement(By.xpath("//div[@id='post-139']/div/pre/span[3]/a/span")).click();
	    mySleep(2);
	    out.println("just clicked Bukkwyd"+"\n"+"title found is: " + zDriver.getTitle());
	    assertThat( zDriver.getTitle(), containsString("Frank Bukkwyd"))  ;
	    zDriver.navigate().back();
    
	    zDriver.findElement(By.linkText("Deja Vu Designs")).click();
	    mySleep(2);
	    out.println("just clicked Deja Vu Designs"+"\n"+"title found is: " + zDriver.getTitle());
	    assertThat( zDriver.getTitle(), containsString("Welcome to DJV Design"))  ;
	    zDriver.navigate().back();

		zDriver.findElement( By.cssSelector(videoMenuID)   ).click();	
		mySleep(2);
		out.println("just clicked Video"+"\n"+"title found is: " + zDriver.getTitle());
		assert(zDriver.getTitle().contains("YouTube"));

	    zDriver.findElement(By.cssSelector(".entry>p>a")).click();
	    mySleep(2);
	    out.println("just clicked Precision"+"\n"+"title found is: " + zDriver.getTitle());
	    assertThat( zDriver.getTitle(), containsString("Home Page"))  ;
	    zDriver.navigate().back();
	

	    assertEquals("The Wine Dude", zDriver.findElement(By.linkText("The Wine Dude")).getText());
	    zDriver.findElement(By.linkText("The Wine Dude")).click();
	    mySleep(2);
	    out.println("just clicked The Wine Dude"+"\n"+"title found is: " + zDriver.getTitle());
	    assertThat( zDriver.getTitle(), containsString("The Wine Dude"))  ;
	    zDriver.navigate().back();
	
	    assertTrue(isElementPresent(By.xpath("//img[@title='ID-10038519-camera']")));
	    out.println("just checked if Camera image is present"+"\n"+"title found is: " + zDriver.getTitle());

	    assertTrue(isElementPresent(By.cssSelector("//img[alt=Facebook]")));
	    assertTrue(isElementPresent(By.cssSelector("//img[alt=Twitter]")));
	    
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
