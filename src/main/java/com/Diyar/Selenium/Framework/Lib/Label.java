package com.Diyar.Selenium.Framework.Lib;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.Diyar.Selenium.Basetest.BaseTest;
import com.Diyar.Selenium.Reporting.Reporting;

/**
 * this class provides methods used for Labels those are supported by selenium. 
 * You just have to create an object for your element like public static Label elem1 = new Label(<xpath>, Type.xpath)
 *All the supporting methods can be called like elem1.getText, elem1.waitForElement, elem1.getElement, elem1.setElement
 *@author Kaja ChennnakesavaRao Bachu
 */

public class Label extends SimpleLib{
	private Type type;
	
	private String sElement = null;
	

	/**
	 * This method is a constructor of class Label that takes input parameters as Element, Type. Object created with this method can use methods like getText(), waitforelement(), getElement()
	 * @param element - element
	 * @param type - This is the enum to select element type like Xpath, Id, name, CSS
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public Label(String sXPathofElement, Type type){
		this.type = type;
		this.sElement = sXPathofElement;
	}

	/**
	 * This method is a parameterized constructor of class label that takes input parameters as element. Here type defaults to Xpath
	 * @param element - name of the element. 
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public Label(String sXPathofElement){
		this.type = Type.XPATH;
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
	 * This method will check for presence of element, waits for the element in regular intervals and fails after 50 seconds
	 * @param ElementName - Name of the Element
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void waitForElement(String ElementName) {

		try {
			switch(type){
			case ID:  waitForElementBy(By.id(sElement), ElementName);break;
			case NAME: waitForElementBy(By.name(sElement), ElementName);; break;
			case XPATH: waitForElementBy(By.xpath(sElement), ElementName); break;
			case CSS: waitForElementBy(By.cssSelector(sElement), ElementName);; break;
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	/**
	 * This method is to get the XPath of element that was declared 
	 * Button usernameButton = new Button("//[@name='username']");
	 * @return - Here it returns the xpath like //[@name='username']
	 */
	public String getElement() {
		return sElement;
	}

	public void setElement(String sElem) {
		this.sElement = sElem;
	}


	/**
	 * To verify the presence of element and prints in the report
	 * @param ElementName - Name of the element
	 * @return - boolean
	 */
	public boolean isElementPresent(String sElementname) {
		boolean trueORfalse = false;

		try {
			switch(type) {
			case ID: 
				waitForElement(sElementname);
				trueORfalse = isElementPresentby(By.id(sElement)); break;
			case NAME:
				waitForElement(sElementname);
				trueORfalse = isElementPresentby(By.name(sElement)); break;
			case XPATH:
				waitForElement(sElementname);
				trueORfalse = isElementPresentby(By.xpath(sElement)); break;
			case CSS:
				waitForElement(sElementname);
				trueORfalse = isElementPresentby(By.cssSelector(sElement)); break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return trueORfalse;
	}

	/**
	 * This method verifies the absence of element and prints in the report
	 * @param Elementname - name of the element
	 * @return - returns boolean
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public boolean isElementAbsent(String sElementname) {
		boolean trueORfalse = false;

		try {
			switch(type) {
			case ID: 
				trueORfalse = isElementAbsentby(By.id(sElement)); break;
			case NAME:	
				trueORfalse = isElementAbsentby(By.name(sElement)); break;
			case XPATH:
				trueORfalse = isElementAbsentby(By.xpath(sElement)); break;
			case CSS:
				trueORfalse = isElementAbsentby(By.cssSelector(sElement)); break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(trueORfalse) {
			BaseTest.logger.info("System successfully not displayed the element "+sElementname);
		}else {
			BaseTest.logger.error("System failed hide the Element "+sElementname+" in the page");
		}

		return trueORfalse;
	}


	/**
	 * This method verifies the presence of element and prints in the report
	 * @param Elementname - name of the element
	 * This method generates the report
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void verifyElementPresent(String sElementname) {

		if(isElementPresent(sElementname)) {
			Reporting.pass("Label " + sElementname+" is present in the page");
		}else {
			Reporting.fail("System failed to find the Element "+sElementname+" in the page");
		}
	}

	/**
	 * This method verifies the absence of element and prints in the report
	 * @param Elementname - name of the element
	 * This method generates the report
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void verifyElementAbsent(String sElementname) {
		if(isElementAbsent(sElementname)) {
			Reporting.pass("System successfully not displayed the element "+sElementname);
		}else {
			Reporting.fail("System failed hide the Element "+sElementname+" in the page");
		}
	}


	/**
	 * This method is to get the attribute value by passing the attribute name as parameter.
	 * @param sAttributeName - attribute name that needs value.
	 * @return - returns attribute value
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public String getattribute(String sAttributeName) {

		String sAttributeValue = null;
		switch(type) {

		case ID:
			sAttributeValue = getDriver().findElement(By.id(sElement)).getDomProperty(sAttributeName); break;
		case NAME:
			sAttributeValue = getDriver().findElement(By.name(sElement)).getDomProperty(sAttributeName); break;
		case XPATH:
			waitForElement(sAttributeValue);
			sAttributeValue = getDriver().findElement(By.xpath(sElement)).getDomProperty(sAttributeName); break;
		case CSS:
			sAttributeValue = getDriver().findElement(By.cssSelector(sElement)).getDomProperty(sAttributeName); break;
		}
		return sAttributeValue;
	}


	/**
	 * This method is used to update the attribute value
	 * @param sAttributeName - name of the attribute
	 * @param sAttributeValue - new value of the attribute
	 * Note : This works only for the xpath
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void setAttributeValue(String sAttributeName, String sAttributeValue) {
		waitForElement(sAttributeName);
		frameLib.setAttibute(sElement, sAttributeName, sAttributeValue);
	}


	/**
	 * This method is used to delete an attribute
	 * @param sAttributeName - Name of the attribute
	 * Note : This works only for the xpath
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void deleteAttribute(String sAttributeName) {
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
