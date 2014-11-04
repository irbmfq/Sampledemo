package com.test.example;

import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class YellowBookTest {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	@Test
	public void testYell() throws InterruptedException {
		// TODO Auto-generated method stub

		DesiredCapabilities capabillities = DesiredCapabilities.htmlUnit();  
        capabillities.setCapability("version", "8");  
        capabillities.setCapability("platform", Platform.WINDOWS);

		WebDriver driver = new FirefoxDriver(capabillities);

		

		// And now use it
		driver.get("http://www.google.com");

		driver.quit();

	}

}