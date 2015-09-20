package com.nmschorr.SMedia;

/*
 * SMTest.java by Nancy M. Schorr, 2015
 * 
 * This test suite executes four tests against schorrmedia.com (jetgalaxy.com/wordpress)
 * 
 * Note: 
 *
 * The tests are run with JUnit, so each test stands on its own and 
 * can be run independently of the others.  Each test launches a fresh 
 * instance of FireFox with Selenium WebDriver.  The tests use Maven and a 
 * Maven pom.xml file to specify dependencies.  The entire suite was written 
 * in and runs with Eclipse on a Windows 7 PC. 
 *
 * Please see README.md for more information.
 * 
*/ 


import java.sql.Timestamp;
import java.util.Date;

import org.junit.runners.MethodSorters;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;
 

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SMTest extends SMTestUtils {
	String websiteMenuID = "#menu-item-143 > a";
	String videoMenuID = "#menu-item-141 > a";
	String automationMenuID = "#menu-item-418 > a";
	String aboutMenuID = "#menu-item-419 > a";
	String guestBookLink = "http://jetgalaxy.com/wordpress/guestbook/";
	static final int SLEEPTIME=1;
	static final int LONGSLEEPTIME=5;
	 
 
	@Before  //run once before each test
	public void setUp() throws Exception {		
		createProperties();  // if we need properties this is where they would get initialized
		checkRunValue();
		System.out.println ("value of useThreads is: " + Boolean.toString(useThreads) );
		gLogger = createLogger();
		zDriver = createDriver(gLogger);
		gLogger.info("Logger and Driver setup. Done with setUp().");
	}		

	
	@Test
	public void test1Menus() throws Exception {
		// eClass is an empty class there just for returning the local method name
		// test logging and menubar     
 		gLogger.info("Starting the actual new test1.");
		checkMainMenubar();
	}
	

	@Test
	public void test2Links() throws Exception {
		// test adding a portfolio
 		gLogger.info("Starting the actual new test2.");
		checkLinks();
	}


	@Test
	public void test3Search() throws Exception {
		// test modifying a portfolio
 		gLogger.info("Starting the actual new test3.");
		checkLinksAndSearch();
	} 


	@Test
	public void test4Guestbook() throws Exception {
		// test deleting a portfolio
 		gLogger.info("Starting the actual new test4.");
		checkGuestBook();
	} 
	// end of tests

	
	// beginning of methods to support tests
	static boolean isElementPresent(By by) {
		try {
			zDriver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	
	void checkGuestBook() throws Exception {
		// checks to see that links and search box work
		zDriver.get(baseUrl);
		mySleep(SLEEPTIME);
		setWindowSize();
		zDriver.get(guestBookLink);  // get the guestbook page
		gLogger.info("Requested Guestbook page");
		mySleep(SLEEPTIME);

		String plsSignText = "Please sign our Guestbook";
		WebElement ele = zDriver.findElement(By.cssSelector("body"));	
		String elementTxt = ele.getText();
		assertTrue(elementTxt.contains(plsSignText));
		gLogger.info("Checked for Guestbook page. ok so far.");

		gLogger.info("Should be inside the GuestBook");
		gLogger.info("Entering GuestBook info for an entry");
		mySleep(SLEEPTIME);
		zDriver.findElement( By.cssSelector("#gwolle_gb_write_button>input")).click();	
		zDriver.findElement( By.cssSelector("#gwolle_gb_author_name")).clear();
		zDriver.findElement( By.cssSelector("#gwolle_gb_author_name")).sendKeys("Joe");
		mySleep(SLEEPTIME);

		zDriver.findElement( By.cssSelector("#gwolle_gb_author_origin")).clear();
		zDriver.findElement( By.cssSelector("#gwolle_gb_author_origin")).sendKeys("Somewhere, USA");
		gLogger.info("Part way through Guestbook entries");
		mySleep(SLEEPTIME);

		zDriver.findElement( By.cssSelector("#gwolle_gb_author_email")).clear();
		zDriver.findElement( By.cssSelector("#gwolle_gb_author_email")).sendKeys("joe@xyz.com");
		mySleep(SLEEPTIME);

		zDriver.findElement( By.cssSelector("#gwolle_gb_content")).clear();

		Date date= new Date();  //getTime() returns current time in milliseconds
		long time = date.getTime();   //Passed the milliseconds to constructor of Timestamp class 
		Timestamp ts = new Timestamp(time);
		gLogger.info("Current Time Stamp: "+ts); 

		String inputString = "Nice Website, thanks! Date time now: "+ ts;
		gLogger.info("Going to enter: "+inputString); 
		zDriver.findElement( By.cssSelector("#gwolle_gb_content")).sendKeys(inputString);
		mySleep(SLEEPTIME);
		gLogger.info("About to click ");

		zDriver.findElement( By.cssSelector("input[type=submit]")).click();
		gLogger.info("Clicked submit ");
		mySleep(SLEEPTIME);

		boolean feedBack = zDriver.findElement(By.cssSelector("body")).getText().contains("Thank you for your entry");	
		gLogger.info("Should be true: value of feedback is "+ feedBack);
		assertTrue(feedBack);
		gLogger.info("If we got this far, everything is ok");		 
		mySleep(SLEEPTIME);
	}

	
	void checkLinksAndSearch() throws Exception {
		// checks to see that links and search box work
		zDriver.get(baseUrl);
		mySleep(SLEEPTIME);
		setWindowSize();
		gLogger.info("About to test social media page links");

		zDriver.findElement( By.cssSelector(websiteMenuID)   ).click();	

		// not going to these pages - not sure they allow it
		gLogger.info("Checking for presense of links on page");
		assertTrue(isElementPresent(By.partialLinkText("Arthyr Chadbourne"))); //facebook
		assertTrue(isElementPresent(By.partialLinkText("Frank Bukkwyd"))); //facebook
		assertTrue(isElementPresent(By.id("7557"))); // Bukkwyd link-title won't work because of single quote
		gLogger.info("links all there");

		checkSocialIcons();

		//new page
		gLogger.info("Testing About Us page");
		zDriver.findElement( By.cssSelector(aboutMenuID)   ).click();	
		mySleep(SLEEPTIME);
		gLogger.info("just clicked About Us. Title found is: " + zDriver.getTitle());

		gLogger.info("Testing About Us elements, images, links, etc.");

		assertTrue(isElementPresent(By.className("thermometer"))); // Thermometer image
		checkSocialIcons();

		assertTrue(isElementPresent(By.linkText("LinkedIn profile"))); 
		zDriver.findElement(By.partialLinkText("resume")).click();  //resume, first link

		mySleep(SLEEPTIME);
		String pageText = zDriver.findElement(By.cssSelector("body")).getText();	
		//assertTrue(pageText.contains("Nancy Schorr"));  //uncomment to try this method instead of below line
		assertThat( pageText, containsString("Nancy Schorr"))  ;
	
		zDriver.navigate().back();
		mySleep(SLEEPTIME);

		gLogger.info("About to test search box");
		zDriver.findElement(By.id("s")).clear();  // search box
		zDriver.findElement(By.id("s")).sendKeys("office");
		zDriver.findElement(By.id("searchsubmit")).click();
		mySleep(SLEEPTIME);
		assertTrue(isElementPresent(By.partialLinkText("About Us"))); 
		gLogger.info("Search box ok");		
		gLogger.info("End of tests. All tests complete.");		
	}


	void checkMainMenubar() throws Exception {
		// checks to see that all items in main menubar are functioning ok
		zDriver.get(baseUrl);
		mySleep(SLEEPTIME);
		setWindowSize();

		//Websites menuitem
		zDriver.findElement( By.cssSelector(websiteMenuID)).click();    	    
		mySleep(SLEEPTIME);
		gLogger.info("just clicked Websites. Title found is: " + zDriver.getTitle());
		assertTrue(zDriver.getTitle().contains("Websites"));

		zDriver.findElement( By.cssSelector(videoMenuID)   ).click();	
		mySleep(SLEEPTIME);
		gLogger.info("just clicked Video. Title found is: " + zDriver.getTitle());
		assertTrue(zDriver.getTitle().contains("YouTube"));

		zDriver.findElement( By.cssSelector(automationMenuID)   ).click();	
		mySleep(SLEEPTIME);
		gLogger.info("just clicked Automation. Title found is: " + zDriver.getTitle());
		assertTrue(zDriver.getTitle().contains("Automat"));

		zDriver.findElement( By.cssSelector(aboutMenuID)   ).click();	
		mySleep(SLEEPTIME);
		gLogger.info("just clicked About Us. Title found is: " + zDriver.getTitle());
		assertTrue(zDriver.getTitle().contains("About"));
	}

	
	void checkLinks() {
		// checks to see that all links functioning ok
		zDriver.get(baseUrl);
		mySleep(SLEEPTIME);
		setWindowSize();

		zDriver.findElement( By.cssSelector(websiteMenuID)).click();    	    
		mySleep(SLEEPTIME);
		gLogger.info("just clicked Websites. Title found is: " + zDriver.getTitle());

		zDriver.findElement(By.linkText("Vitamin Center")).click();
		mySleep(SLEEPTIME);
		gLogger.info("just clicked Vitamin Center. Title found is: " + zDriver.getTitle());
		assertThat( zDriver.getTitle(), containsString("Vitamin Center"))  ;
		zDriver.navigate().back();

		zDriver.findElement(By.linkText("NCGR-LA")).click();
		mySleep(SLEEPTIME);
		gLogger.info("just clicked NCGR LA. Title found is: " + zDriver.getTitle());
		assertThat( zDriver.getTitle(), containsString("NCGR"))  ;
		zDriver.navigate().back();

		zDriver.findElement(By.linkText("Frank Bukkwyd")).click();
		mySleep(SLEEPTIME);
		gLogger.info("just clicked Bukkwyd. Title found is: " + zDriver.getTitle());
		assertThat( zDriver.getTitle(), containsString("Frank Bukkwyd"))  ;
		zDriver.navigate().back();

		zDriver.findElement(By.linkText("Deja Vu Designs")).click();
		mySleep(SLEEPTIME);
		gLogger.info("just clicked Deja Vu Designs. Title found is: " + zDriver.getTitle());
		assertThat( zDriver.getTitle(), containsString("Welcome to DJV Design"))  ;
		zDriver.navigate().back();

		// new page
		zDriver.findElement( By.cssSelector(videoMenuID)   ).click();	
		mySleep(SLEEPTIME);
		gLogger.info("just clicked Video. Title found is: " + zDriver.getTitle());
		assertTrue(zDriver.getTitle().contains("YouTube"));

		assertEquals("Precision Audiosonics", zDriver.findElement(By.linkText("Precision Audiosonics")).getText());
		zDriver.findElement(By.linkText("Precision Audiosonics")).click();
		mySleep(SLEEPTIME);
		gLogger.info("just clicked Precision. Title found is: " + zDriver.getTitle());
		assertThat( zDriver.getTitle(), containsString("Home Page"))  ;
		zDriver.navigate().back();

		assertEquals("The Wine Dude", zDriver.findElement(By.linkText("The Wine Dude")).getText());
		zDriver.findElement(By.linkText("The Wine Dude")).click();
		mySleep(SLEEPTIME);
		gLogger.info("just clicked The Wine Dude. Title found is: " + zDriver.getTitle());
		assertThat( zDriver.getTitle(), containsString("The Wine Dude"))  ;
		zDriver.navigate().back();

		assertTrue(isElementPresent(By.xpath("//img[@title='ID-10038519-camera']")));
		gLogger.info("just checked if Camera image is present. Title found is: " + zDriver.getTitle());

		checkSocialIcons();
	}

	void checkSocialIcons() {
		gLogger.info("Checking if Social Icon images are present");
		assertTrue(isElementPresent(By.cssSelector("img[alt=\"Facebook\"]")));
		assertTrue(isElementPresent(By.cssSelector("img[alt=\"Twitter\"]")));
		assertTrue(isElementPresent(By.cssSelector("img[alt=\"Youtube\"]")));
		assertTrue(isElementPresent(By.cssSelector("img[alt=\"LinkedIn\"]")));
		gLogger.info("Finished checking if Social Icon images are present");

		assertEquals("TechCrunch", zDriver.findElement(By.linkText("TechCrunch")).getText());
		gLogger.info("Finished checking if TechCrunch link is present");
	}	


	@After
	public void tearDown() throws Exception {
		System.out.println("Starting new thread to handle windows alerts");
		AlertThread thread3=new AlertThread("thread3");  
		thread3.start();  
		gLogger.info("Quitting Webdriver and shutting down");
		zDriver.quit();
		mySleep(1);
		gLogger.info("All done with this test.");
	}
	
	
	@AfterClass
	public static void afterClass() {
		System.out.println("Done with Test Suite. Goodbye.");
	}
}
