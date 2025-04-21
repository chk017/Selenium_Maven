package com.Diyar.Selenium.Browser;

import java.net.MalformedURLException;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.Diyar.Selenium.Basetest.BaseTest;

public class Browserstack extends BaseTest{

	public static String sUsername = null;
	public static String sAccesskey = null; // we get the value from system properties and pass values from mvn command.
	public static String browserStackurl = "https://"+sUsername+":"+sAccesskey+"@hub.browserstack.com/wd/hub";
	
	

	public void bstackCaps() throws MalformedURLException {
		
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setPlatform(Platform.MAC);
		desiredCapabilities.setBrowserName("firefox");
		desiredCapabilities.setVersion("57");
		
//		URL browserstackURL = null;
		/*
		try {
			browserstackURL = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
//		driver = new RemoteWebDriver(new URL(browserStackurl), desiredCapabilities);
		driver.set(new RemoteWebDriver(new URL(browserStackurl), desiredCapabilities));
		
	}
}
