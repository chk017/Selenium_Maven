package com.Diyar.Selenium.Framework.Lib;

import org.openqa.selenium.By;
//import org.openqa.selenium.ElementClickInterceptedException;
//import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.Diyar.Selenium.Basetest.BaseTest;
import com.Diyar.Selenium.Reporting.Reporting;

/**
 * this class provides methods used for Links those are supported by selenium.
 * You just have to create an object for your element like public static Link
 * elem1 = new Link(<xpath>, Type.xpath) All the supporting methods can be
 * called like elem1.click, elem1.waitForElement, elem1.getElement,
 * elem1.setElement
 * 
 * @author Kaja ChennnakesavaRao Bachu
 *
 */

public class Link extends SimpleLib {
	// private WebDriver driver;
	private Type type;
	private String sElement;

	/**
	 * This method is a constructor of class Label that takes input parameters as
	 * Element, Type. Object created with this method can use methods like
	 * getText(), waitforelement(), getElement()
	 * 
	 * @param element - element
	 * @param type    - This is the enum to select element type like Xpath, Id,
	 *                name, CSS
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public Link(String sXPathofElement, Type type) {
		this.type = type;
		this.sElement = sXPathofElement;
	}

	
	/**
	 * This will get the text from the Label
	 * @return - String which is the Label
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public String getText(){

		String sValue = null;
		if(isElementPresent("")) {
		switch(type){
		case ID: sValue = getDriver().findElement(By.id(sElement)).getText(); break;
		case NAME: sValue = getDriver().findElement(By.name(sElement)).getText(); break;
		case XPATH: sValue = getDriver().findElement(By.xpath(sElement)).getText(); break;
		case CSS: sValue = getDriver().findElement(By.cssSelector(sElement)).getText(); break;
		}
		}else {
			BaseTest.logger.info("seems element does not exist with Xpath : "+sElement);
		}
		return sValue;

	}	
	
	
	
	
	/**
	 * This method is a parameterized constructor of class Link that takes input
	 * parameters as element. Here type defaults to Xpath
	 * 
	 * @param element - name of the element.
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public Link(String sXPathofElement) {
		this.type = Type.XPATH;
		this.sElement = sXPathofElement;
	}

	/**
	 * This method is to click on the element
	 * 
	 * @param sElementName - Name of the element
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */

	public void click(String sElementName) {

		try {
			switch (type) {
			case ID:
				waitForElementBy(By.id(sElement), sElementName);
				getDriver().findElement(By.id(sElement)).click();
				Reporting.pass("System successfully clicked on the element \"" + sElementName + "\"");
				BaseTest.logger.info("System successfully clicked on the element \"" + sElementName + "\"");
				break;
			case NAME:
				waitForElementBy(By.name(sElement), sElementName);
				getDriver().findElement(By.name(sElement)).click();
				Reporting.pass("System successfully clicked on the element \"" + sElementName + "\"");
				BaseTest.logger.info("System successfully clicked on the element \"" + sElementName + "\"");
				break;
			case XPATH:
				waitForElementBy(By.xpath(sElement), sElementName);
				try {
					getDriver().findElement(By.xpath(sElement)).click();
					Reporting.pass("System successfully clicked on the element \"" + sElementName + "\"");
					BaseTest.logger.info("System successfully clicked on the element \"" + sElementName + "\"");
					/*
					 * }catch(ElementClickInterceptedException e){ lib.clickon(element,
					 * sElementName);
					 */
				} catch (org.openqa.selenium.WebDriverException e) {
					frameLib.clickon(sElement, sElementName);
				}
				break;
			case CSS:
				waitForElementBy(By.cssSelector(sElement), sElementName);
				getDriver().findElement(By.cssSelector(sElement)).click();
				Reporting.pass("System successfully clicked on the element \"" + sElementName + "\"");
				BaseTest.logger.info("System successfully clicked on the element \"" + sElementName + "\"");
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method will check for presence of element, waits for the element in
	 * regular intervals and fails after 50 seconds
	 * 
	 * @param sElementName - Name of the Element
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void waitForElement(String sElementName) {

		try {
			switch (type) {
			case ID:
				waitForElementBy(By.id(sElement), sElementName);
				break;
			case NAME:
				waitForElementBy(By.name(sElement), sElementName);
				break;
			case XPATH:
				waitForElementBy(By.xpath(sElement), sElementName);
				break;
			case CSS:
				waitForElementBy(By.cssSelector(sElement), sElementName);
				break;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is to get the XPath of element that was declared Button usernameButton =
	 * new Button("//[@name='username']");
	 * 
	 * @return - Here it returns the xpath like //[@name='username']
	 */
	public String getElement() {
		return sElement;
	}

	public void setElement(String sElem) {
		this.sElement = sElem;
	}

	/**
	 * To verify the presence of element and returns boolean
	 * 
	 * @param ElementName - Name of the element
	 * @return - true or false
	 */
	public boolean isElementPresent(String sElementName) {
		boolean trueORfalse = false;

		try {
			switch (type) {
			case ID:
				waitForElement(sElementName);
				trueORfalse = isElementPresentby(By.id(sElement));
				break;
			case NAME:
				waitForElement(sElementName);
				trueORfalse = isElementPresentby(By.name(sElement));
				break;
			case XPATH:
				waitForElement(sElementName);
				trueORfalse = isElementPresentby(By.xpath(sElement));
				break;
			case CSS:
				waitForElement(sElementName);
				trueORfalse = isElementPresentby(By.cssSelector(sElement));
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (trueORfalse) {
			BaseTest.logger.info(sElementName + " is present in the page");
		} else {
			BaseTest.logger.error("System failed to find the Element " + sElementName + " in the page");
		}
		return trueORfalse;
	}

	/**
	 * This method verifies the absence of element
	 * 
	 * @param sElementName - name of the element
	 * @return - returns boolean
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public boolean isElementAbsent(String sElementName) {
		boolean trueORfalse = false;

		try {
			switch (type) {
			case ID:
				trueORfalse = isElementAbsentby(By.id(sElement));
				break;
			case NAME:
				trueORfalse = isElementAbsentby(By.name(sElement));
				break;
			case XPATH:
				trueORfalse = isElementAbsentby(By.xpath(sElement));
				break;
			case CSS:
				trueORfalse = isElementAbsentby(By.cssSelector(sElement));
				break;
			}
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			trueORfalse = false;
		}
		if (trueORfalse) {
			BaseTest.logger.info("System successfully not displayed the element " + sElementName);
		} else {
			BaseTest.logger.error("System failed hide the Element " + sElementName + " in the page");
		}
		return trueORfalse;
	}




	/**
	 * This method verifies the presence of element and prints in the report
	 * 
	 * @param sElementName - name of the element This method generates the report
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */

	public void verifyElementPresent(String sElementName) {

		if(isElementPresent(sElementName)) {
			Reporting.pass("Link " + sElementName+" is present in the page"); }else {
				Reporting.fail("System failed to find the Element "
						+sElementName+" in the page"); } }

	/**
	 * This method verifies the absence of element and prints in the report
	 * 
	 * @param sElementName - name of the element This method generates the report
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */

	public void verifyElementAbsent(String sElementName) {
		if(isElementAbsent(sElementName)) {
			Reporting.pass("System successfully not displayed the element "+sElementName); 
		}else {
			Reporting.fail("System failed hide the Element "+sElementName+" in the page",true); 
		} 
	}




	/**
	 * This method is to get the attribute value by passing the attribute name as
	 * parameter.
	 * 
	 * @param sAttributeName - attribute name that needs value.
	 * @return - returns attribute value
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public String getattribute(String sAttributeName) {

		String sAttributeValue = null;
		switch (type) {

		case ID:
			sAttributeValue = getDriver().findElement(By.id(sElement)).getDomProperty(sAttributeName);
			break;
		case NAME:
			sAttributeValue = getDriver().findElement(By.name(sElement)).getDomProperty(sAttributeName);
			break;
		case XPATH:
			waitForElement(sAttributeName);
			sAttributeValue = getDriver().findElement(By.xpath(sElement)).getDomProperty(sAttributeName);
			break;
		case CSS:
			sAttributeValue = getDriver().findElement(By.cssSelector(sElement)).getDomProperty(sAttributeName);
			break;
		}
		return sAttributeValue;
	}

	/**
	 * This method is used to update the attribute value
	 * 
	 * @param sAttributeName  - name of the attribute
	 * @param sAttributeValue - new value of the attribute Note : This works only
	 *                        for the xpath
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void setAttributeValue(String sAttributeName, String sAttributeValue) {
		waitForElement(sAttributeName);
		frameLib.setAttibute(sElement, sAttributeName, sAttributeValue);
	}

	/**
	 * This method is used to delete an attribute
	 * 
	 * @param sAttributeName - Name of the attribute Note : This works only for the
	 *                       xpath
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void deleteAttribute(String sAttributeName) {
		waitForElement(sAttributeName);
		frameLib.deleteAttribute(sElement, sAttributeName);

	}
	
	/**
	 * This method will return the Web Element.
	 * 
	 * @return driver.findElement(By.xpath(element))
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public WebElement getWebElement() {
		return getWebElem(sElement);
	}

}
