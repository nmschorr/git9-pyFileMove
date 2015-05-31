/***
 * ZacksTest.java by Nancy M. Schorr, 2015
 * 
 * This test suite executes four tests against Zacks.com
 * 
 * Note:  You must create your own account at Zacks.com and change the login parameters 
 * in the parameters file in order to test it.
 *
 * The tests are run with Junit, so each test stands on its own and 
 * can be run independently of the others.  Each test launches a fresh 
 * instance of Firefox with Selenim Webdriver.  The tests use Maven and a 
 * Maven pom.xml file to specify dependencies.  The entire suite was written 
 * in and runs with Eclipse on a Windows 7 PC. 
 *
 * Please see Readme.rtf or Readme.txt for more information.
 * 
 */

package com.nmschorr.zackstest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

// note: you need to have an account and password at zacks.com

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZacksTest extends ZacksUtil {
	ZacksUtil zutil = new ZacksUtil();
	@Before  //run only once before all tests
	public void setUp() throws Exception {		
		createProperties();
		gLogger = zutil.createLogger();
		zDriver = createDriver();
		initStrings();
	}

	//@Test
	public void Test1LoginZacks() throws Exception {
		// eClass is an empty class there just for returning the local method name
		// test logging in only	    
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());
		loginZacks(zacksMainUrl);
		logoutZacks();
	}

	//@Test
	public void Test2ZacksAddPortfolio() throws Exception {
		// login, add portfolio
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());
		loginZacks(zacksMainUrl);
		zacksAddPortfolio();	    
		logoutZacks();
	}

	@Test
	public void Test3ZacksModifyPortfolio() throws Exception {
		// test modifying portfolio
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());
		loginZacks(zacksMainUrl);
		modifyPort();
		logoutZacks();
	} 

	@Test
	public void Test4ZacksDeletePortfolio() throws Exception {
		// test modifying portfolio
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());
		
		loginZacks(zacksMainUrl);
		deletePortfolio();
		logoutZacks();
	} 
	/// end of tests
	/// beginning of methods to support tests
	
	public static void loginZacks(String localUrl) {  
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());

		zDriver.get(localUrl);  // main Zacks page
		setWindowSize();

		zDriver.findElement(By.linkText("Sign In")).click();
		zDriver.findElement(By.id("username")).clear();
		zDriver.findElement(By.id("username")).sendKeys(usernameval);
		zDriver.findElement(By.id("password")).clear();
		zDriver.findElement(By.id("password")).sendKeys(pwordval);	 
		zDriver.findElement(By.xpath(signinval)).click();
		assertEquals("Testing Signin",theTitle, zDriver.getTitle());	
		//assertThat(zDriver.getTitle(), containsString("Zacks Investment Research:"));
	            //  <title>Zacks Investment Research: Stock Research, Analysis, &amp; Recommendations</title>
	}
	
	public static void zacksAddPortfolio() throws Exception {
		// method to add portfolio, must be logged in first
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod()); 		
  	
		zDriver.get(portfolioUrl);
		assertEquals("Testing Portfolio Url Title", portfolioTitle, zDriver.getTitle());    
		zDriver.findElement(By.linkText("Create a New Portfolio")).click(); 
		
		String expectedTitle = "Stock Portfolio Management - Zacks Investment Research";
		assertEquals(expectedTitle, zDriver.getTitle());
		
		// add new portfolio entries
		zDriver.findElement(By.name("port_name")).clear();
		zDriver.findElement(By.name("port_name")).sendKeys(PF_NAME);
		zDriver.findElement(By.name("port_tck_name")).clear();
		zDriver.findElement(By.id("port_tck_name")).sendKeys("aapl, msft, t, csco, irbt");
		zDriver.findElement(By.id("submit")).click();
		zDriver.findElement(By.xpath("//input[@value='Modify Portfolio Buys']")).click();  
	}
	
	public static void modifyPort() throws Exception {
		// modify portfolio - add shares
		class eClass {};	    
		printMethodName(eClass.class.getEnclosingMethod());	

		zDriver.get(portfolioUrl);  // main Zacks page
		String expectedTitle = "Stock Portfolio Management - Zacks Investment Research";
		System.out.println("Expected title is: " + zDriver.getTitle());
		assertEquals("Testing Portfolio Url Title", expectedTitle, zDriver.getTitle());    
				
		zDriver.findElement(By.linkText("Modify Previous Buys")).click();
		zDriver.findElement(By.name("position[0][shares]")).clear();
		zDriver.findElement(By.name("position[0][shares]")).sendKeys("22");
		zDriver.findElement(By.name("position[1][shares]")).clear();
		zDriver.findElement(By.name("position[1][shares]")).sendKeys("33");
		zDriver.findElement(By.name("position[2][shares]")).clear();
		zDriver.findElement(By.name("position[2][shares]")).sendKeys("44");
		zDriver.findElement(By.name("position[3][shares]")).clear();
		zDriver.findElement(By.name("position[3][shares]")).sendKeys("55");
		zDriver.findElement(By.name("position[4][shares]")).clear();
		zDriver.findElement(By.name("position[4][shares]")).sendKeys("66");
		zDriver.findElement(By.id("modify_btn")).click();
	}

	public void deletePortfolio ()  {
		// make sure it's there before we delete it
		zDriver.get(portfolioUrl);
		new Select(zDriver.findElement(By.id("port_id"))).selectByVisibleText(PF_NAME);;
		// need to add check to sure the portfolio is there to delete
		// assertEquals("testportfolio", zDriver.findElement(By.cssSelector("option[value=\"1222826\"]")).getText());
		//	assertThat(zDriver.getTitle(), containsString("xxx"));			
		mySleep(2);
		zDriver.findElement(By.linkText("Delete this Portfolio")).click();
		zDriver.findElement(By.id("chk")).click();
		zDriver.findElement(By.name("btn_del")).click();
		mySleep(2);
		System.out.println("title is: " + zDriver.getTitle());
		assertThat(zDriver.getTitle(), containsString("Stock Portfolio Management"));
		// page : <title>Stock Portfolio Management - Zacks Investment Research</title>
	}

	@After
	public void tearDown() throws Exception {
		gLogger.info("All done with tests. Quitting Webdriver and shutting down");
		gLogger.exit(false);
		AlertThread thread3=new AlertThread();  
		thread3.start();  
		zDriver.quit();
		mySleep(1);
		//  String verificationErrorString = verificationErrors.toString();
		//	  if (!"".equals(verificationErrorString)) {
		//		  fail(verificationErrorString);
  }
}
