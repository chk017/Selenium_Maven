package com.Diyar.Selenium.Framework.Lib;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.Diyar.Selenium.Basetest.BaseTest;
import com.Diyar.Selenium.Reporting.Reporting;

public class FrameLib extends BaseTest {

//	private WebDriver driver = getDriver();

	// public WebDriver driver = BaseTest.driver;
	// Logger logger;
	// private static String formatDate = new
	// SimpleDateFormat("dd-MM-yyyy").format(new Date());
	// private static String formatTime = new
	// SimpleDateFormat("hh.mm.ss").format(new Date());
	// Actions action = new Actions(driver);

	/**
	 * This method will return the name of the method by passing new object. Example
	 * getmethodname(new Object() {})
	 * 
	 * @param new Object() {}
	 * @return It returns the name of method currently running.
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public String getmethodname(Object Obj) {
		return Obj.getClass().getEnclosingMethod().getName();
	}

	/**
	 * it will get the text by using js
	 * 
	 * @param sXpath
	 * @return - text
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public String getTextByXpath(String sXpath) {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
	
		return (String) executor.executeScript("return arguments[0].value;", getDriver().findElement(By.xpath(sXpath)));

	}

	/**
	 * This method will verify the element is present or not.
	 * 
	 * @param by - Selenium by Element
	 * @return - It will return false, if element does not present. Similarly return
	 *         true, if element present.
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */

	public boolean isElementPresent(By by) {
		try {
			getDriver().findElement(by);
			highlightElement(getDriver().findElement(by));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Waits for the presence of element for Timeout number of seconds and throws error
	 * 
	 * @param by          - Selenium by
	 * @param ElementName - Name of the element
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void waitForElementBy(By by, String ElementName) { 
	
		for (int second = 1; second <= BaseTest.iTimeout + 5; second = second + 5) {

			if (second >= BaseTest.iTimeout) {
				try {
					Reporting.info("TIMED OUT waiting for the element : " + ElementName, true);
//					throw new Exception("Exception Timedout");

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (isElementPresent(by)) {
				BaseTest.logger.info("Element :\"" + ElementName + "\" found within the Time");
				break;
			} else {
				BaseTest.logger
						.info("System is waiting for the Element :\"" + ElementName + "\" from " + second + " seconds");
				Util.sleepforseconds(5);
			}
		}
	}


	/**
	 * System waits for page to load until the page Title appears and validates the Title.
	 * If the waiting time reaches Timedout number of seconds, throws error
	 * 
	 * @param sTitle
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void waitfortitleandvalidate(String sTitle) {
		for (int second = 0;; second++) {

			if (second >= BaseTest.iTimeout) {
				try {
					BaseTest.logger.error("Timedout Exception");
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// If results have been returned.
			if ((!getDriver().getTitle().isEmpty()) && getDriver().getTitle().equals(sTitle)) {
				BaseTest.logger.info("Title displayed successfully : \"" + sTitle + "\"");
				Reporting.pass("Page \"" + sTitle + "\" is displayed successfully");
				break;
			} else {
				try {
//					 Util.sleepforseconds(1);
					Thread.sleep(1000);
					
					BaseTest.logger.info(
							"System is waiting for the Expected Title :\"" + sTitle + "\" from " + second + "seconds");
				} catch (InterruptedException e1) {
					e1.printStackTrace();

					Reporting.fail("Title seems to be changed, Actual Title : " + getDriver().getTitle()
							+ " \n Expected Title : " + sTitle);
					BaseTest.logger.error("Title seems to be changed, Actual Title : " + getDriver().getTitle()
							+ " \n Expected Title : " + sTitle);
				}

			}
		}
	}

	/**
	 * Waits for Text field to appear --> Clears the TextField --> Enters given text
	 * "TextValue" in the Text field --> Get the value from Text field and validate
	 * the same with the entered text.
	 * 
	 * @param by          - Selenium by
	 * @param ElementName - Name of the Element
	 * @param TextValue   - Value to pass in the TextField
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void TextFieldTypeNValidate(By by, String ElementName, String TextValue) {
		waitForElementBy(by, ElementName);
		getDriver().findElement(by).sendKeys(TextValue);

		if (getDriver().findElement(by).getDomProperty("value").contains(TextValue)) {

			BaseTest.logger.info(
					"Value = \"" + TextValue + "\" is entered successfully in Textfield : \"" + ElementName + "\"");
			Reporting.pass(
					"Value = \"" + TextValue + "\" is entered successfully in Textfield : \"" + ElementName + "\"");
		} else {

			Reporting.fail(
					"Value = \"" + TextValue + "\" is not entered properly in Textfield : \"" + ElementName + " \"");
			BaseTest.logger.info(
					"Value = \"" + TextValue + "\" is not entered properly in Textfield : \"" + ElementName + " \"");
		}
	}

	/**
	 * This method will maximize the browser window handled by driver
	 * 
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void maximizethewindow() {
		getDriver().manage().window().maximize();
		BaseTest.logger.info("Maximizing the window");
	}

	/**
	 * This method will delete the browser cookies
	 * 
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void deletecookies() {
		getDriver().manage().deleteAllCookies();
		BaseTest.logger.info("Deleting cookies");
	}

	/**
	 * This method will move into two frames one by one.
	 * 
	 * @param OuterFrame - This is the first frame
	 * @param InnerFrame - This is the second frame
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void focustoframes(String OuterFrame, String InnerFrame) {
		BaseTest.logger.info("Focussing on two frames...");
		getDriver().switchTo().frame(OuterFrame).switchTo().frame(InnerFrame);
	}

	/**
	 * This method will move into the frame provided in the parameter.
	 * 
	 * @param Frame - First frame
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void focustoframes(String Frame) {
		BaseTest.logger.info("Focussing on a frame...");
		getDriver().switchTo().frame(Frame);
	}

	/**
	 * To click left mouse button
	 * 
	 * @param noofleftclicks - It should be 1 (for Single Click) or 2 (for Double
	 *                       Click)
	 * @throws AWTException
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void mouseleftclick(int noofleftclicks) throws AWTException {
		for (int i = 0; i < noofleftclicks; i++) {
			new Robot().mousePress(InputEvent.BUTTON1_DOWN_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}
	}

	/**
	 * This method moves the cursor to the coordinates provided in parameters
	 * 
	 * @param X_coord
	 * @param Y_coord
	 * @throws AWTException
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void MouseMove(int X_coord, int Y_coord) throws AWTException {
		new Robot().mouseMove(X_coord, Y_coord);
	}

	
	public void crossingMultipleFrames(String sFirstFrame) {
		BaseTest.logger.info("Focussing on Multiple Frames...");
		outofframes();
		focustoframes(sFirstFrame);

		List<WebElement> ele = getDriver().findElements(By.tagName("iframe"));
		BaseTest.logger.info("Number of frames in a page :" + ele.size());

		int elementIndex = ele.size();
		WebElement matchedElement = null;
		for (int i = 1; i <= ele.size(); i++) {
			// Returns the Id of a frame.
			WebElement el = ele.get(i - 1);
			BaseTest.logger.info("Frame Id :" + el.getDomProperty("id"));
			if (elementIndex == i) {
				matchedElement = el;
			}
		}

		BaseTest.logger.info("Matched Element :"+matchedElement);

		getDriver().switchTo().frame(matchedElement);

		focustoframes("webiViewFrame");

	}

	/**
	 * This method will Refresh the page
	 */
	public void refreshPage() {
		getDriver().navigate().refresh();
	}

	/**
	 * This method will highlight the element provided in the parameter.
	 * 
	 * @param by - Selenium by
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void highlightElement(WebElement webElement) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();

		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", webElement,
				"background: yellow; border: 2px solid blue;");
		Util.sleepforseconds(1);
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", webElement, "");

	}


	/**
	 * To double click on an element provided in the parameter
	 * @param Element - Web element
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void doubleClick(WebElement Element) {
		Actions action = new Actions(getDriver());
		action.doubleClick(Element);
		action.perform();
	}

	/**
	 * To hover an element by providing xpath of element
	 * @param sXPathofElement
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void hover(String sXPathofElement){
		Actions action = new Actions(getDriver());
		action.moveToElement(getDriver().findElement(By.xpath(sXPathofElement))).build().perform();
	}

	/**
	 * To hover on element and clicking on it	  
	 * @param hoverelement
	 * @param clickelement
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void hovernclick(String hoverelement, By clickelement) {
		Actions action = new Actions(getDriver());
		hover(hoverelement);
		action.moveToElement(getDriver().findElement(clickelement)).click().build().
		perform();

	}

	/**
	 * This method will maximize the window through Keys
	 * 
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	protected void Maximizethewindow() throws AWTException {
		Robot rb = new Robot();
		rb.keyPress(java.awt.event.KeyEvent.VK_ALT);
		rb.keyPress(java.awt.event.KeyEvent.VK_SPACE);
		rb.keyPress(java.awt.event.KeyEvent.VK_X);

		rb.keyRelease(java.awt.event.KeyEvent.VK_X);
		rb.keyRelease(java.awt.event.KeyEvent.VK_SPACE);
		rb.keyRelease(java.awt.event.KeyEvent.VK_ALT);
	}

	/**
	 * This method will switch the driver from one browser to other browser. 
	 * @throws AWTException
	 */
	public void windowhandle() throws AWTException {
		BaseTest.logger.info("Count of windows : " + getDriver().getWindowHandles().size());
		String master = getDriver().getWindowHandle();
		BaseTest.logger.info("Master value : " + master);
		Set<String> strlist = getDriver().getWindowHandles();

		for (String handle : strlist) {
			System.out.println("handle :" + handle);
			if (!handle.equals(master)) {
				getDriver().switchTo().window(handle);
				maximizethewindow();
			}
		}
	}

	/**
	 * To move out of all the frames
	 * 
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void outofframes() {
		BaseTest.logger.info("Moving out of Frames...");
		getDriver().switchTo().defaultContent();
	}

	/**
	 * This method is used to get the count of multiple elements that matches the
	 * xpath
	 * 
	 * @param sXpath - xpath as String
	 * @return - It will return the count of elements that match xpath
	 * @author chk017 Kaja ChennakesavaRao bachu
	 */
	public int getXpathsCount(String sXpath) {

		List<WebElement> xpaths = null;
		try {
			xpaths = getDriver().findElements(By.xpath(sXpath));
		} catch (NoSuchElementException e) {
			BaseTest.logger.warn("No xPaths are found with the provided xpath : \"" + sXpath + "\"");
		}

		return xpaths.size();
	}

	/**
	 * This method is used to click on an element. Here it uses Javascript executor
	 * to identify and click on element using xpath.
	 * 
	 * @param xpath - xpath as string
	 * @author chk017 Kaja ChennakesavaRao bachu
	 */
	public void clickon(String sXpath, String sElementName) {
		
		try {
		WebElement element = getDriver().findElement(By.xpath(sXpath));
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript("arguments[0].click();", element);
		Reporting.pass("System successfully clicked on Element : " + sElementName);
		BaseTest.logger.info("System successfully clicked on Element : \"" + sElementName + "\"");
		} catch (NoSuchElementException e) {
			BaseTest.logger.error("System failed to click on the element ", e.getMessage());
			// TODO: handle exception
		}
	}

	/**
	 * This method is used to click on browse button and upload a file
	 * 
	 * @param sButtonXpath - this is the xpath with input tag
	 * @param sFilepath    - path of the file to upload
	 * @author chk017 Kaja ChennakesavaRao Bachu
	 */
	public void clickButtonAndUploadFile(String sButtonXpath, String sFilepath) {
		if (booleanBrowserStack)
			((RemoteWebDriver) getDriver()).setFileDetector(new LocalFileDetector());
		getDriver().findElement(By.xpath(sButtonXpath)).sendKeys(sFilepath);
		BaseTest.logger.info("Uploading file \"" + sFilepath + "\" for the button \"" + sButtonXpath + "\"");

	}

	/**
	 * It verifies the absence of element and returns boolean
	 * 
	 * @param by - Selenium by
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public boolean isElementAbsent(By by) {
		List<WebElement> xpaths = getDriver().findElements(by);
		if (xpaths.size() >= 1)
			return false;
		else
			return true;
	}

	/**
	 * This method verifies the absence of element by taking the xpath and writes in the logs
	 * 
	 * @param sXpath - xpath as String
	 * @return - returns boolean as true or false
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	
	public boolean verifyAbsence(String sXpath) { 

		if(getXpathsCount(sXpath) >= 1) {
			BaseTest.logger.info("system failed to hide the Xpath \""+sXpath+"\"");
			return false; 
		}else {
			BaseTest.logger.info("Xpath \""+sXpath+"\" not found successfully");
			return true; } }

	

	/**
	 * This method verifies the presence of element by taking the xpath
	 * 
	 * @param sXpath - xpath as String
	 * @return - returns boolean as true or false
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public boolean verifyPresence(String sXpath) {
		if (getXpathsCount(sXpath) >= 1) {
			BaseTest.logger.info("Xpath \"" + sXpath + "\" found successfully");
			return true;
		} else {
			BaseTest.logger.info("system failed to find the Xpath \"" + sXpath + "\"");
			return false;
		}
	}

	/**
	 * This method is to update the attribute value
	 * 
	 * @param sXpath          - Xpath of the element
	 * @param sAttributeName  - Attribute name
	 * @param sAttributeValue - Attribute value to update
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void setAttibute(String sXpath, String sAttributeName, String sAttributeValue) {

		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("document.getElementByXpath(" + sXpath + ").setAttribute(" + sAttributeName,
				sAttributeValue + ")");

	}

	/**
	 * This method is to delete the attribute value by passing xPath as parameter
	 * 
	 * @param sXpath         - Xpath of the element
	 * @param sAttributeName - Name of the attribute Note: this works only for the
	 *                       xpath
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void deleteAttribute(String sXpath, String sAttributeName) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("document.getElementByXpath(" + sXpath + ").removeAttribute(" + sAttributeName + ")");

	}

	public void verifyDomComplete() {

		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		boolean a = executor.executeScript("return document.readyState").equals("complete");
		System.out.println("Output : " + executor.executeScript("return document.readyState"));
		System.out.println(" Dom loading Complete :" + a);
	}
	
	public void waitForDomCompletion() {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		while(!executor.executeScript("return document.readyState").equals("complete")) {
			System.out.println("Dom status : " +executor.executeScript("return document.readyState"));
			System.out.println("Waiting for Dom completion ");
			Util.sleepforseconds(1);
		}
		System.out.println("Dom completed");
	}
	

	/**
	 * This method is to click OK on the alert.
	 * 
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void alertAccept() {
		getDriver().switchTo().alert().accept();

	}

}
