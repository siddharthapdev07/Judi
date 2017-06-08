package com.agm.Judi.tests;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import junit.framework.TestResult;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import autoitx4java.AutoItX;

import com.agm.framework.FunctionLibraries.ApplicationFunctions;
import com.agm.framework.FunctionLibraries.CommonFunctions;
import com.agm.framework.FunctionLibraries.DB;
import com.agm.framework.helpers.Initializer;
import com.jacob.com.LibraryLoader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class DemoTest2 {
	Connection conn = null;
	public String strSQLQuery;
	public String strField;
	public WebDriver driver;
	public AutoItX objAutoIT = null;
    public Object[][] tabArray=null;
//	static {
//	    System.loadLibrary("jacob-1.18-M3-x86");
//	}
	@Test
	public void Demo(){
		
		DB.getInstance().funConnectDB(
				Initializer.getInstance().GetValue("db.env"),
				Initializer.getInstance().GetValue("db.test1g.dbname.portal"));
		String strQuery = "SELECT count(*) from trialsite WHERE trial_id IN (SELECT id FROM trial where name = 'Judi_Demo')";

		CommonFunctions.getInstance().funLog("SQL Query used to fetch sites is : " + strQuery);
		String iSites_DB = DB.getInstance().funExecuteQuery(strQuery, "count");	
		System.out.println(iSites_DB);
//	public Object[][] Demo() {

//		      File inputWorkbook = new File(System.getProperty("user.dir")
//						+ Initializer.getInstance().GetValue(
//								"file.csvSitesFilePath"));
//		        Workbook w;
//		        int startRow,startCol, endRow, endCol,ci,cj,ck;
//		        try {
//		            //w = Workbook
//		            w = Workbook.getWorkbook(inputWorkbook);
//		            // Get the first sheet
//		            Sheet sheet = w.getSheet("sheet1");
//		            // Loop over first 10 column and lines
//		            endRow = sheet.getRows();
//		            endCol = sheet.getColumns();
//		            tabArray=new String[endRow-1][endCol-1];
//
//		            ci=0;
//
//		            for (int i=1;i<endRow;i++,ci++){
//		                cj=0;
//
//		                for (int j=1;j<endCol;j++,cj++){
//
//		                  Cell cell = sheet.getCell(j, i);
//		                tabArray[ci][cj] = cell.getContents(); 
//
//		                }
//		            //    System.out.println("");
//		            }
//		            //file.close();
//		        }
//		        catch (Exception e)
//		        {
//		            e.printStackTrace();
//		        }
//
//
//
//		       return(tabArray);  /// Here Getting the error **Type mismatch: cannot convert from Object[][] to Object[][][]**
//		    }
		
		
		
		
		
//		CommonFunctions.getInstance().funLoadTestDetailsFromTestRail(this.getClass().getSimpleName());
//		CommonFunctions.getInstance().funUpdateResultsToTestRail("FAIL");
//		
		
		
//		 DB.getInstance().funConnectDB(Initializer.getInstance().GetValue("db.env"),
//		 Initializer.getInstance().GetValue("db.test05.dbname.portal"));
//		 strSQLQuery = "select trial_id from pendingtrialuser where emailaddress = 'autotest_freetodelete1@mx-intr.agmednet.net'";
//		 strField = DB.getInstance().funExecuteQuery(strSQLQuery, "trial_id");
//		 System.out.println(strField);
		
		
//		File file = new File(Initializer.getInstance().GetValue("java.autoit.jacob"));		
//		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
//		objAutoIT = new AutoItX();
//		CommonFunctions commonFunctions = CommonFunctions.getInstance();
//		commonFunctions.init(objAutoIT);
//		System.setProperty("webdriver.gecko.driver", Initializer
//				.getInstance().GetValue("java.firefox.path"));
//		driver = new FirefoxDriver();
//		driver.manage().window().maximize();
//		ApplicationFunctions applicationFunctions = ApplicationFunctions
//				.getInstance();
//		applicationFunctions.init(driver);
//		driver.get("file:///C://Users//Suresh%20Mylam//Desktop//upload.html");        //This needs to be parameterized 
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		driver.findElement(By.id("file")).click();
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		CommonFunctions.getInstance().funWaitAndAction("File Upload", "Edit1", "SETTEXT", "C:\\Users\\Suresh Mylam\\Desktop\\Selenium-Framework.pptx");
//		CommonFunctions.getInstance().funWaitAndAction("File Upload", "Button1", "CLICK", "");
	}

}
