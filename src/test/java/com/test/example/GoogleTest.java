package com.test.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import junit.framework.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

public class GoogleTest extends TestCase {

	 private RemoteWebDriver driver;
	    private SessionId sessionId;
	    
	    /**
	     * @return the driver
	     */
	    public WebDriver getDriver() {
	        return driver;
	    }

	    /**
	     * @return the sessionId
	     */
	    public SessionId getSessionId() {
	        return sessionId;
	    }

	    /**
	     * @param sessionId the sessionId to set
	     */
	    public void setSessionId(SessionId sessionId) {
	        this.sessionId = sessionId;
	    }
	    
	    class MyListener implements TestListener {
	        private TestCase test;
	        private ArrayList<String> errors = new ArrayList<String>();
	        
	        public MyListener(TestCase test) {
	            this.test = test;
	        }

	        @Override
	        public void addError(Test test, Throwable t) {
	            this.errors.add(t.getMessage());
	        }

	        @Override
	        public void addFailure(Test test, AssertionFailedError t) {
	            this.errors.add(t.getMessage());
	        }

	        @Override
	        public void endTest(Test test) {
	            System.out.println("End a test: " + ((GoogleTest) test).getSessionId().toString());
	            String success = (this.errors.size() == 0) ? "1" : "0";
	            String data = "success=" + success + "&name=" + this.test.getName();
	            if (this.errors.size() > 0)
	            {
	                data += "&status_message=" + this.errors.toString();
	            }
	            this._sendApi(data);
	        }

	        @Override
	        public void startTest(Test test) {
	            System.out.println("Start a test.");
	        }
	        
	        private void _sendApi(String data) {
	            try {
	             // Send data
	            URL url = new URL("http://testingbot.com/hq");
	            data += "&client_key=050512a4d2e83bf24d1c25c15a0eb7d0&client_secret=bf316f9e0bb6a66e8ad4d4608003c23a";
	            data += "&session_id=" + ((GoogleTest) test).getSessionId().toString();
	            URLConnection conn = null;
	            try {
	                conn = url.openConnection();
	            } catch (IOException ex) {
	            }
	            
	            conn.setDoOutput(true);
	            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	            try {
	                wr.write(data);
	            } catch (IOException ex) {
	            }
	                
	            wr.flush();
	            
	            // Get the response
	            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

	            wr.close();
	            rd.close();
	            } catch (Exception e) {}
	        }
	    }

	    @Override
	    public void setUp() throws Exception {
	        DesiredCapabilities capabillities = DesiredCapabilities.firefox();
	        capabillities.setCapability("version", "11");
	        capabillities.setCapability("platform", Platform.WINDOWS);
	        capabillities.setCapability("name", "Testing Selenium 2");

	        this.driver = new RemoteWebDriver(
	        		 new URL("http://050512a4d2e83bf24d1c25c15a0eb7d0:bf316f9e0bb6a66e8ad4d4608003c23a@hub.testingbot.com:80/wd/hub"),
	                 capabillities);
	        this.setSessionId(this.driver.getSessionId());
	        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    }
	    
	    @Override
	    public void run(TestResult tr) {
	        MyListener l = new MyListener(this);
	        tr.addListener(l);
	        super.run(tr);
	    }

	    public void testSimple() throws Exception {
	       this.getDriver().get("http://www.google.com");
	        assertEquals("Google", this.getDriver().getTitle());
	        driver.findElement(By.name("q")).isDisplayed();
	        driver.findElement(By.name("q")).sendKeys("Selenium");
//driver.close();
	//driver.getTitle();
//	Thread.sleep(2000);
	    }

	    @Override
	    public void tearDown() throws Exception {
	        this.getDriver().quit();
	    }
	}
