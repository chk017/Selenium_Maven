package com.Diyar.Selenium.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.util.Hashtable;

import com.Diyar.Selenium.Basetest.BaseTest;

/**
 * This class is used to get the data from Excel, 
 * For this we have to create an object for the class ReadExcel and call the method readexceldatawithheadings
 * @author chk017(kaja ChennakesavaRao Bachu)
 */

public class ReadExcel {

	int colnum; 
	int rownum;
	Workbook wb = null;
	private Hashtable<String, String> privateMapdata;

	
	/**
	 *  This method gets the data by passing the column name, row name, which means we get the value where the column and row intersect 
	 *  a new folder "testFiles" should be created in ckFramework and excel sheet with test data should be placed in testFiles folder. 
	 *  sTestdata_Path = sproject_path + //testFiles//test_data.xlsx 
	 *  Here test_data.xlsx is the sheet you created in testFiles folder. where as sproject_path will consider the current project path.
	 *  
	 * @param path - Path of excel Workbook from which data to retrieve
	 * @param sSheetName - Name of the sheet
	 * @param sRowHeading - Heading of the row to get the data from that row
	 * @param columnHeading - Heading of the column to get the data from that column
	 * @return returns the value from excel based on the parameters provided as Row and Column
	 * @author chk017(kaja ChennakesavaRao Bachu)
	 */
	public String getdata (String sPath, String sSheetName, String sRowHeading, String sColumnHeading){

		BaseTest.logger.info("System is retrieving test data...");

		getWBObj_from_Path(sPath);


		Sheet sheet = wb.getSheet(sSheetName);
		int rowmax = sheet.getLastRowNum() +1;
		int colmax = sheet.getRow(0).getLastCellNum();


		for(int rowtemp=0; rowtemp<rowmax;rowtemp++){
			if(sheet.getRow(rowtemp).getCell(0).toString().contentEquals(sRowHeading)){
				this.rownum = rowtemp;
				break;
			}	}

		for(int coltemp=0; coltemp<colmax;coltemp++){

			if(sheet.getRow(0).getCell(coltemp).toString().contentEquals(sColumnHeading)){
				this.colnum = coltemp;
				break;
			}	}

		String Value = sheet.getRow(rownum).getCell(colnum).getStringCellValue();
		BaseTest.logger.info(" Test Data : "+Value);

		return Value;

	}


	/***
	 * This method is to get the data into the maps
	 * @param path
	 * @param sheetname
	 * @param rowheading
	 * @return Map
	 */
	public Hashtable<String, String> getDataWOSheet (String sPath, String rowheading){

		Sheet sheet = null;
		int numbOfSheets; 
		int rowmax;
		wb = getWBObj_from_Path(sPath);

		numbOfSheets = wb.getNumberOfSheets();

		checkLabel:
			for (int iSheetIter=0;iSheetIter<numbOfSheets;iSheetIter++) {
				sheet = wb.getSheetAt(iSheetIter);
				rowmax = sheet.getPhysicalNumberOfRows();

				/**
				 * Here we are checking the test method name and finding the corresponding row from the list of Testnames and assigning to rownum
				 */
				for(int rowtemp=1; rowtemp<rowmax;rowtemp++){
					String temprowHeading = sheet.getRow(rowtemp).getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim();
					
					if(temprowHeading.isBlank()) {
						System.out.println("Please fix the white spaces after the physical rows in the sheet : "+ sheet.getSheetName()+" at the rows : "+ (rowtemp+1));
					}
					else if(temprowHeading.contentEquals(rowheading)){
						this.rownum = rowtemp;

						break checkLabel;
					}	
				}
			}

		return getExcelData(sPath, sheet.getSheetName(), rowheading);

	}


	private int getRequiredRowNumber(Sheet sheet, int iCountOfAllRows, String rowheading) {

		checkLabel:
			for(int rowtemp=1; rowtemp<iCountOfAllRows;rowtemp++){
				String temprowHeading = sheet.getRow(rowtemp).getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim();
				

				if(temprowHeading.isBlank()) {
					System.out.println("Please fix the white spaces after the physical rows in the sheet : "+ sheet.getSheetName()+" at the rows : "+ rowtemp);
				}
				else if(temprowHeading.contentEquals(rowheading)){
					this.rownum = rowtemp;
					break checkLabel;
				}	
			}
	return rownum;
	}



	/**
	 * This method will return the workbook Object by taking the path of the excel file
	 * @param sPath
	 * @return
	 */
	private Workbook getWBObj_from_Path(String sPath) {

		try {
			wb = WorkbookFactory.create(new FileInputStream(new File(sPath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return wb;
	}


	/**
	 * This method will search for Test case name(rowheading) in the sPath, sheetname and returns the keys and values in the Map 
	 * @param sPath - Test data excel path
	 * @param sheetname - Sheetname in the Excel workbook
	 * @param rowheading - Test case name we are searching for to retrieve the data
	 * @return - returns the map with Keys and Values 
	 */
	public Hashtable<String, String> getExcelData (String sPath, String sheetname, String rowheading){

		BaseTest.logger.info("System is retrieving test data...");

		privateMapdata = new Hashtable<String, String>();
		
		wb = getWBObj_from_Path(sPath);	
		
		Sheet sheet = wb.getSheet(sheetname);
		int rowmax = sheet.getLastRowNum() +1;
		int colmax = sheet.getRow(0).getLastCellNum();

		/**
		 * Here we are checking the test method name and finding the corresponding row from the list of Testnames and assigning to rownum
		 */
		rownum = getRequiredRowNumber(sheet, rowmax, rowheading);

		for(int coltemp=0; coltemp<colmax;coltemp++)
		{
			if(!sheet.getRow(rownum).getCell(coltemp,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().isEmpty()) 
			{
				privateMapdata.put(sheet.getRow(0).getCell(coltemp).getStringCellValue(), (sheet.getRow(rownum).getCell(coltemp).getStringCellValue()));
			}				
		}

		return privateMapdata;
	}

}