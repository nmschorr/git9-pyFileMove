/***
 * TestGuruLogin.java by Nancy M. Schorr, 2015
 *
 * Please see README.md for more information.
 * 
 */
package com.nmschorr.gurutests;
// Note:  Surefire requires test classes be named starting or ending w/Test
//        or ending w/Testcase
// 
// junit has the main() method in org.junit.runner.JUnitCore

import org.junit.*;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import static org.hamcrest.CoreMatchers.containsString;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.UnhandledAlertException;
//import org.openqa.selenium.support.ui.ExpectedConditions;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGuruLogin extends GuruUtils {
	WebDriver myDriver;
	public WebDriverWait wait;
	public Logger gLogger;
	//RemoteWebDriver rd = new RemoteWebDriver();
	
	@Before
	public void setUp() throws Exception {
		verErrors = new StringBuffer();
		gLogger = createNewLogger();
		Properties gProperties = newProps(gLogger);  // load the properties file
		setUpStrings(gProperties);
		gLogger.info("baseurl is: " + baseUrl);
		}
	
	@Test
	public void TestGurulogin()  throws Exception {
		myDriver = setUpDriver();
		gLogger.info("String for myDriver: " + myDriver.toString());
		
		
		try {
			myDriver.manage().timeouts().implicitlyWait(70, TimeUnit.SECONDS);
			wait = new WebDriverWait(myDriver, 7);
			myDriver.get(baseUrl);  // main login page
			myWait(2,gLogger);
			assertEquals("Guru99 Bank Home Page", myDriver.getTitle());
			myDriver.findElement(By.name("uid")).clear();
			myDriver.findElement(By.name("uid")).sendKeys(userNameVal);
			myDriver.findElement(By.name("password")).clear();
			myDriver.findElement(By.name("password")).sendKeys(pWordVal);
			myDriver.findElement(By.name("btnLogin")).click();
			myWait(2,gLogger);

			try {
				Alert alert = myDriver.switchTo().alert();
				String alertText = alert.getText();
				if (alertText.equals("User or Password is not valid"))
				{
					System.out.println("Yes the alert text is there");
				}
				System.out.println("!!! Alert is: "+alert.toString());
				System.out.println("!!! Alert says: "+alertText);
				gLogger.info("Alert present. Test Failed! ");
				alert.accept();

			} catch (NoAlertPresentException e) {
				gLogger.info("No alert present. Success! ");
			}			
				myWait(2,gLogger);
			
			assertEquals("asserting Homepage", "Guru99 Bank Manager HomePage", myDriver.getTitle());
			//assertThat(myDriver.getTitle(), containsString("Guru"));
			assertThat("asserting part of the string",myDriver.getTitle(), containsString("Guru"));
			gLogger.error("Done with Tests!!");
			assertEquals("Manger Id : mngr12715", myDriver.findElement(By.cssSelector("tr.heading3 > td")).getText());
			System.out.println("waiting now");
		} catch (UnhandledAlertException e) {
			e.printStackTrace();
		}
	}		
		
	@After
	public void tearDown() throws Exception {
		gLogger.exit(false);
		myDriver.quit();
		String verErrorString = verErrors.toString();
		if (!"".equals(verErrorString)) {
			fail(verErrorString);
		}
	}

}


