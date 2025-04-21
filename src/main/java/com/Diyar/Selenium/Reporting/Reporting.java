package com.Diyar.Selenium.Reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import com.Diyar.Selenium.Basetest.BaseTest;
import com.Diyar.Selenium.Framework.Lib.Util;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Reporting extends BaseTest{


//	private static WebDriver driver;
	static String sCapturePath ;
	private static ExtentSparkReporter reporterSpark;
	private static FileInputStream fileInputStreamObj;
	private static ExtentReports extentReportsObj;
	private static ExtentTest extentTestObj;
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>() ;
	

	/**
	 * This is the start of the test case and this will continue to record the steps until it calls the endtest. 
	 * @param testname - This is the name of the testcase
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public static void startTest(String testname) {
		/*
		 * extentTestObj = extentReportsObj.startTest(testname); // Assigning ExtentTest
		 * object to the ThreadLocal object of ExtentTest extentTest.set(extentTestObj);
		 */
		
		extentTestObj = extentReportsObj.createTest(testname).assignAuthor("Diyar_United_Company");
		//		Assigning ExtentTest object to the ThreadLocal object of ExtentTest
		extentTest.set(extentTestObj);
	}

	/**
	 * This method is the first step in the reporting. It will create report with name provided as parameter appends with date and time to avoid the override of report
	 * @param sReportname - This is the name of the report
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public static void startReporting(String sReportname) {
		reporterSpark = new ExtentSparkReporter(BaseTest.sReportsPath + sReportname +"_"+Util.getCurrentDatenTime(Util.getformat())+".html");
		
		try {
			reporterSpark.loadXMLConfig(sProjectDirectory + "/Installs/setup/Extent-Config.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		  extentReportsObj = new ExtentReports();
	      extentReportsObj.attachReporter(reporterSpark);
//	        System.out.println("logger obj " + logger);
//		Logger.getLogger(sReportname);
	}

	/**
	 * This method will end the test case that is currently running which was started with method startTest.
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	/*
	 * public static void endTest() { extentReportsObj.endTest(extentTest.get()); }
	 */

	/**
	 * This method will print the report. this should be run after all the reporting steps are generated.
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public static void reportflush() {
		extentReportsObj.flush();
	}

	
	public static void pass(String sDescription) {
		extentTest.get().log(Status.PASS, sDescription);
	}

	
	/**
	 * This is the statement that generates as Pass in the report that prints the Description 
	 * @param stepName - enter some text
	 * @param Screencapture - If this parameter is true, it will provide screenshot of application.
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public static void pass(String sDescription, boolean bScreenCapture) {
		if(bScreenCapture) {
			extentTest.get().pass(sDescription, MediaEntityBuilder.createScreenCaptureFromBase64String(formatBase64Binary(capture())).build());
		}else {
			extentTest.get().log(Status.PASS, sDescription);
		}
		
	}


	/**
	 * This is the statement that generates as Fail in the report that prints the Description 
	 * @param sDescription - This text will print in the report
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public static void fail(String sDescription) {
		extentTest.get().log(Status.FAIL, sDescription);
	}


	/**
	 * This is the statement that generates as Fail in the report that prints the Description 
	 * @param Description - This text will print in the report
	 * @param Screencapture - If this parameter is true, it will provide screenshot of application.
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public static void fail(String sDescription, boolean bScreenCapture) {
		if(bScreenCapture) {
			extentTest.get().fail(sDescription, MediaEntityBuilder.createScreenCaptureFromBase64String(formatBase64Binary(capture())).build());

		}else
			extentTest.get().log(Status.FAIL, sDescription);
	}


	/**
	 * This is the statement that generates Information in the report
	 * @param sInformation
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public static void info(String sDescription) {
		extentTest.get().log(Status.INFO, sDescription);
	}

	/**
	 * This is the statement that generates as Information in the report that prints the Description 
	 * @param Description - This text will print in the report
	 * @param Screencapture - If this parameter is true, it will provide screenshot of application.
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	public static void info(String sDescription, boolean bScreenCapture) {
		if(bScreenCapture) {
			extentTest.get().info(sDescription, MediaEntityBuilder.createScreenCaptureFromBase64String(formatBase64Binary(capture())).build());
			
		}else
			extentTest.get().log(Status.FAIL, sDescription);
	}


	/**
	 * This method will capture the screenshot of the page and saves in the Report folder
	 * @return path of the screenshot with file name as current time
	 * @author (chk017) kaja ChennnakesavaRao Bachu
	 */
	private static String capture() { 
		Util.createFolder(sReportsPath + "/Images/");
		File scrFile = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
		
		long Imagename = System.currentTimeMillis();
		File Dest = new File(sReportsPath + "/Images/" + Imagename + ".png");
		String errflpath = sReportsPath + "/Images/" + Imagename + ".png";

		try {
			FileHandler.copy(scrFile, Dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return errflpath;
	}


	/**
	 * This method is used to convert the image to base64 string and format the base64 string with png.
	 * @param capturePath - Image path
	 * @return - Returns the formatted base64String like "data:image/png;base64," + <base64String>
	 */
	private static String formatBase64Binary(String sCapturePath) {

		String base64str = encodeFileToBase64(new File(sCapturePath));
		String imgFormat = "data:image/png;base64," + base64str;
		return imgFormat;

	}


	/**
	 * This method converts an image to base64 String.
	 * 
	 * @param file
	 * @return - Base64String
	 */
	private static String encodeFileToBase64(File file){
		String encodedfile = null;
		try {
			fileInputStreamObj = new FileInputStream(file);
			byte[] bytes = new byte[(int)file.length()];
			fileInputStreamObj.read(bytes);

			encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return encodedfile;
	}

	


}
