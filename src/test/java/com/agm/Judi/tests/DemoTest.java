package com.agm.Judi.tests;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.ldap.ExtendedRequest;

import org.testng.ITestResult;
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

public class DemoTest {

	public String strLogFileName;
	public ExtentReports extent;
	public ExtentTest test;
	public String strSQLQuery;
	public String strField;
	public AutoItX objAutoIT = null;
	@BeforeMethod
	public void beforeMethod() {
		// ********************************* INITIAL SETUP    *****************************************	
		strLogFileName = this.getClass().getSimpleName()
				+ "_"
				+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar
						.getInstance().getTime());									// Setting the Log file path in system variables
		System.setProperty("logFileName", strLogFileName);
		CommonFunctions.getInstance().funStartTestCase(
				this.getClass().getSimpleName());
		
		// ********************************* R-E-P-O-R-T-S     ***************************************		
		extent = new ExtentReports(Initializer.getInstance().GetValue(
				"java.results.path")
				+ strLogFileName + ".html", true);									// new instance for Extent Reports
		
		extent.loadConfig(new File(
				"src/main/resources/Config-ExtentReports.xml"));
		
		test = extent.startTest("Demo_test ", "Demo Test");     // starting test
	
		test.assignAuthor("Suresh Kumar Mylam");									// Set Category and author to report
		
		test.assignCategory("Regression");
		
		// ********************************* AUTOIT SETUP   *****************************************	
		
		File file = new File(Initializer.getInstance().GetValue("java.autoit.jacob"));		
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
		objAutoIT = new AutoItX();
	}

	@Test
	public void Test() {
		//*********************************************   Config Set Up - START ************************************* 
		// to initialize Extent reports object for Libraries
		CommonFunctions commonFunctions = CommonFunctions.getInstance();
		commonFunctions.init(test);
		commonFunctions.init(objAutoIT);
		ApplicationFunctions applicationFunctions = ApplicationFunctions			//This block should exist for all the test cases
				.getInstance();
		applicationFunctions.init(test);		
		//*********************************************   Config Set Up - END ************************************* 
		
		// Actual test functionality starts from here
		// Launch Application
		commonFunctions.funLaunchURL(Initializer.getInstance().GetValue(
				"app.test.test05"));

		// Login Application
		applicationFunctions.funLoginApplication();
	}

	@AfterMethod
	public void afterMethod() {
		try {
			CommonFunctions.getInstance().funQuitBrowser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonFunctions.getInstance().funLog(
					"Issue in terminating the browser");
		}
		// ending test
		extent.endTest(test);
		// writing everything to document
		extent.flush();
		CommonFunctions.getInstance().funEndTestCase(
				this.getClass().getSimpleName());

	}
}
