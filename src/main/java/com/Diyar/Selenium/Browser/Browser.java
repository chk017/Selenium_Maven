package com.Diyar.Selenium.Browser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Diyar.Selenium.Basetest.BaseTest;
import com.Diyar.Selenium.Framework.Lib.Util;


public class Browser extends BaseTest{

	public WebDriverWait waiter;
	public static String browserStackurl = "https://"+sBrowserStackUsername+":"+sBrowserStackAccesskey+"@hub.browserstack.com/wd/hub";
	public static String sauceLabsURL = "https://"+sSauceLabsUsername+":"+sSauceLabsAccesskey+"@ondemand.apac-southeast-1.saucelabs.com:443/wd/hub";

	private boolean BoolBrowserExtensionRequired = false;

	/**
	 * This method will open the url in the browser
	 * @param sURL - url is passed as a parameter. If the url is declared in config, it will pull the same. we can also pass other URL as string
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void getURL(String sURL) {
		getDriver().get(sURL);
//		Util.sleepforseconds(5);
		

	}

	/**
	 * This method will open the url in the browser
	 * @param sURL - url is passed as a parameter. If the url is declared in config, it will pull the same
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void getURL() {
		getDriver().get(sURL);

	}

	/**
	 * Opens the browser provided in the properties file like Chrome, ff, IE. it will pick up the Chrome, if no value provided
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public void openBrowser() { 
		System.out.println("Execution in BrowserStack : "+ booleanBrowserStack);

		
		BoolBrowserExtensionRequired = Boolean.parseBoolean(getproperty("BrowserExtensionRequired"));
		System.out.println(" BoolBrowserExtensionRequired : " + BoolBrowserExtensionRequired);
		

		if(sBrowser == null) {
			sBrowser = getproperty("browser");
		}else { System.out.println("Choosen Browser : " + sBrowser); }

		if(sBrowser.equalsIgnoreCase("Chrome") || sBrowser.equalsIgnoreCase("")) {
			chromeSetup();
		}else if(sBrowser.equalsIgnoreCase("FF") || sBrowser.equalsIgnoreCase("firefox")){
			firefoxSetup();
		}else if(sBrowser.equalsIgnoreCase("EDGE")) {
			edgeSetup();
		}else if(sBrowser.equalsIgnoreCase("SAFARI")) {
			safariSetup();
		}else if(sBrowser.equalsIgnoreCase("BrowserStack")) {
			browserStackCapabilities();
		}else if(sBrowser.equalsIgnoreCase("SauceLabs")) {
			sauceLabsCapabilities();
		}else {	

			System.out.println("Choosing the default Browser as : Chrome " );
			chromeSetup();
		}
		getDriver().manage().timeouts().pageLoadTimeout(Duration.ofMinutes(iPageLoadTimeout));

	}




	/**
	 * Opens the browser by providing the parameter which is enum Select_Browser
	 * @param browser - Example Select_Browser.chrome, Select_Browser.FF, Select_Browser.IE
	 * @author (chk017) kaja ChennnakesavaRao Bachu 
	 */
	public void openBrowser(SelectBrowser SelectBrowser) { 
		sBrowser = SelectBrowser.toString();
		openBrowser();
	}

	public void openBrowser(String browsername, boolean boolBrowserStack) {
		sBrowser = browsername;
		booleanBrowserStack = boolBrowserStack;
		openBrowser();
	}


	public enum SelectBrowser {
		FIREFOX, CHROME, EDGE, OPERA, BrowserStack, SauceLabs, FROMJSON
	}



	@SuppressWarnings("unused")
	private void firefoxSetup2() {

	
		Map<String, String> vals = Util.getJSONtoMap(sProjectDirectory + "/drivers/Browser_Configs.json", "firefox");

		//		FirefoxOptions firefoxOptions = new FirefoxOptions();
		//		desiredCapabilities.setCapability(firefoxOptions.CAPABILITY, firefoxOptions);
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

		System.out.println("------------------------------------------");
		for (Entry<String, String> entry : vals.entrySet()) {
			//			firefoxOptions.addArguments(entry.getValue());

			desiredCapabilities.setCapability(entry.getKey(), entry.getValue());
			System.out.println("value added to options : " + entry.getValue());
		}
		System.out.println("------------------------------------------");

		localBrowserStackConfig(desiredCapabilities);

		if(booleanBrowserStack){
			openBrowserStack(desiredCapabilities);
		}else{
//			WebDriverManager.firefoxdriver().setup();
			driver.set(new FirefoxDriver());
		}

	}

	//this is the latest method
	private void firefoxSetup() {

		
		Map<String, String> vals = Util.getJSONtoMap(sProjectDirectory + "/drivers/Browser_Configs.json", "firefox");

				FirefoxOptions firefoxOptions = new FirefoxOptions();

		System.out.println("------------------------------------------");
		for (Entry<String, String> entry : vals.entrySet()) {

			firefoxOptions.setCapability(entry.getKey(), entry.getValue());
			System.out.println("value added to options : " + entry.getValue());
		}
		System.out.println("------------------------------------------");

		localBrowserStackConfig(firefoxOptions);

		if(booleanBrowserStack){
			openBrowserStack(firefoxOptions);
		}else{
			driver.set(new FirefoxDriver(firefoxOptions));
		}

	}
	
	

	private void chromeSetup() {

//		WebDriverManager.chromedriver().clearDriverCache().setup();
//		WebDriverManager.chromedriver().clearResolutionCache().setup();
		
		
		ChromeOptions options = new ChromeOptions();
		
		if(BoolBrowserExtensionRequired)
		options.addExtensions(new File(sProjectDirectory + "/drivers/extensions/" + "ChromeExt1.crx"));
		
		
		Map<String, String> vals = Util.getJSONtoMap(sProjectDirectory + "/drivers/Browser_Configs.json", "chrome");

		System.out.println("------------------------------------------");
//		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

		for (Entry<String, String> entry : vals.entrySet()) {
			options.setCapability(entry.getKey(), entry.getValue());

			System.out.println("value added to options : " + entry.getKey() +"  : "+ entry.getValue());
		}

//		options.merge(desiredCapabilities);
		System.out.println("------------------------------------------");

//		localBrowserStackConfig(desiredCapabilities);
		localBrowserStackConfig(options);
		
		

		if(booleanBrowserStack){
//			openBrowserStack(desiredCapabilities);
			openBrowserStack(options);
		}else{
//			WebDriverManager.chromedriver().setup();
//			driver.set(new ChromeDriver());
			driver.set(new ChromeDriver(options));
			
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(iTimeout));
		}
				
//		driver.get("chrome-extension://idgpnmonknjnojddfkpgkljpfnnfcklj/popup.html");
		
		
	}





	private void edgeSetup() {

		EdgeOptions options = new EdgeOptions();
		
		if(BoolBrowserExtensionRequired)
		options.addExtensions(new File(sProjectDirectory + "/drivers/extensions/" + "EdgeExt1.crx"));
		

		Map<String, String> vals = Util.getJSONtoMap(sProjectDirectory + "/drivers/Browser_Configs.json", "edge");


//		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		for (Entry<String, String> entry : vals.entrySet()) {
			options.setCapability(entry.getKey(), entry.getValue());

			System.out.println("value added to options : " + entry.getKey() +"  : "+ entry.getValue());
		}
		System.out.println("===================================================");


		localBrowserStackConfig(options);

		if(booleanBrowserStack){
			openBrowserStack(options);
		}else{
//			WebDriverManager.edgedriver().setup();
			driver.set(new EdgeDriver(options));
			
			waiter = new WebDriverWait(getDriver(), Duration.ofSeconds(iTimeout));
//			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(iTimeout));
		}
		

	}
	
	private void localBrowserStackConfig(DesiredCapabilities desiredCapabilities) { 
		try {
		if(booleanBrowserStack) {
			desiredCapabilities.setCapability("browserstack.local", true); 
			
		} } catch
		(Exception e) { 
			} 
	}


	private void localBrowserStackConfig(FirefoxOptions options) {

		if(booleanBrowserStack) {
			options.setCapability("browserstack.local", true);
		}
	}
	
	
	private void localBrowserStackConfig(ChromeOptions options) {

		if(booleanBrowserStack) {
			options.setCapability("browserstack.local", true);
		}
	}

	private void localBrowserStackConfig(EdgeOptions options) {

		if(booleanBrowserStack) {
			options.setCapability("browserstack.local", true);
		}
	}


	private void browserStackCapabilities() { 

		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setPlatform(Platform.WINDOWS);
		desiredCapabilities.setBrowserName("chrome");
		desiredCapabilities.setVersion("93");


		try {
			driver.set(new RemoteWebDriver(new URL(browserStackurl), desiredCapabilities));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	public void openBrowserStack(Capabilities desiredCapabilities) {

		try {
			driver.set(new RemoteWebDriver(new URL(browserStackurl), desiredCapabilities));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}


	private void safariSetup() {


		Map<String, String> vals = Util.getJSONtoMap(sProjectDirectory + "/drivers/Browser_Configs.json", "safari");

		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		for (Entry<String, String> entry : vals.entrySet()) {
			desiredCapabilities.setCapability(entry.getKey(), entry.getValue());

			System.out.println("value added to options : " + entry.getKey() +"  : "+ entry.getValue());
		}
		System.out.println("===================================================");

		localBrowserStackConfig(desiredCapabilities);
		if(booleanBrowserStack){
			openBrowserStack(desiredCapabilities);
		}else{
			driver.set(new SafariDriver());
		}


	}



	private void sauceLabsCapabilities() {

		URL url = null;

		MutableCapabilities sauceOptions = new MutableCapabilities();
		sauceOptions.setCapability("username", sSauceLabsUsername);
		sauceOptions.setCapability("access_key", sSauceLabsAccesskey);
		sauceOptions.setCapability("browserVersion", "latest");

		ChromeOptions options = new ChromeOptions();
		options.setCapability("sauce:options", sauceOptions);

		try {
			url = new URL("https://ondemand.apac-southeast-1.saucelabs.com/wd/hub");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		driver.set(new RemoteWebDriver(url, options));

	}	

	@SuppressWarnings("unused")
	private void edgeSetup_Local() {

		System.setProperty("webdriver.edge.driver", sProjectDirectory + "/drivers/msedgedriver.exe");
		driver.set(new EdgeDriver());

	}

	@SuppressWarnings("unused")
	private void androidSetup() {

		Map<String, String> vals = Util.getJSONtoMap(sProjectDirectory + "/drivers/Browser_Configs.json", "firefox");

		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

		for (Entry<String, String> entry : vals.entrySet()) {
			desiredCapabilities.setCapability(entry.getKey(), entry.getValue());
			System.out.println("value added to options : " + entry.getValue());
		}

	}


}
