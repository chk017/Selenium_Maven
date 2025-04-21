package com.Diyar.Selenium.Framework.Lib;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.Diyar.Selenium.Reporting.Reporting;

public class SimpleLib { 

	protected FrameLib frameLib = new FrameLib();
	Reporting reporting;
	
	
	public enum Type{
		ID,
		XPATH,
		NAME,
		CSS
	}


	/** Waits for the presence of element for few seconds and throws error
	 * @param by - Selenium by
	 * @param ElementName - Name of the element
	 * @author (chk017) Kaja ChennnakesavaRao Bachu
	 */
	public void waitForElementBy(By by, String sElementName) {
		frameLib.waitForElementBy(by, sElementName);
		
	}

	/**
	 * It verifies the presence of element and returns boolean
	 * @param by - Selenium by
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public boolean isElementPresentby(By by) {
		return frameLib.isElementPresent(by);
	}


	/**
	 * It verifies the absence of element and returns boolean
	 * @param by - Selenium by
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public boolean isElementAbsentby(By by) {
//		return !isElementPresentby(by);
		return frameLib.isElementAbsent(by);
	}

	
	public WebDriver getDriver() {
		return	frameLib.getDriver();
		
	}


	/**
	 * This method takes the input and returns the web element
	 * @param element - 
	 * @return - It returns driver.findelement(By.xpath(xpath));
	 */
	/*public static WebElement getWebElement(String element) {
		return BaseTest.driver.findElement(By.xpath(element));
	*/	
	public WebElement getWebElem(String sXPath) {
		return frameLib.getDriver().findElement(By.xpath(sXPath));
		
	}

	
}
