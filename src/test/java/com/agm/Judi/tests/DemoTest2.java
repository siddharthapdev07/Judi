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
//	static {
//	    System.loadLibrary("jacob-1.18-M3-x86");
//	}
	@Test
	public void Demo() {
		
		CommonFunctions.getInstance().funLoadTestDetailsFromTestRail(this.getClass().getSimpleName());
		CommonFunctions.getInstance().funUpdateResultsToTestRail("FAIL");
		
		
		
		// DB.getInstance().funConnectDB(Initializer.getInstance().GetValue("db.env"),
		// Initializer.getInstance().GetValue("db.test05.dbname.portal"));
		// strSQLQuery = "Select id from public.trialversion";
		// strField = DB.getInstance().funExecuteQuery(strSQLQuery, "id");
		// System.out.println(strField);
		
		
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
