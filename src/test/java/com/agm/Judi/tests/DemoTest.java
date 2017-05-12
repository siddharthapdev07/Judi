package com.agm.Judi.tests;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.ldap.ExtendedRequest;

import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.agm.framework.FunctionLibraries.ApplicationFunctions;
import com.agm.framework.FunctionLibraries.CommonFunctions;
import com.agm.framework.FunctionLibraries.DB;
import com.agm.framework.helpers.Initializer;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class DemoTest {

	String strLogFileName;
	public ExtentReports extent;
	public ExtentTest test;
	public String strSQLQuery;
	public String strField;

	@BeforeMethod
	public void beforeMethod() {
		// Setting the Log file path in system variables
		strLogFileName = this.getClass().getSimpleName()
				+ "_"
				+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar
						.getInstance().getTime());
		System.setProperty("logFileName", strLogFileName);
		CommonFunctions.getInstance().funStartTestCase(
				this.getClass().getSimpleName());

		// new instance for Extent Reports
		extent = new ExtentReports(Initializer.getInstance().GetValue(
				"java.results.path")
				+ strLogFileName + ".html", true);
		// starting test
		test = extent.startTest("DEMO test ", "Execution Started - Demo Test");
	}

	@Test
	public void Test() {
		// to initialize Extent reports object for Libraries
		CommonFunctions commonFunctions = CommonFunctions.getInstance();
		commonFunctions.init(test);
		ApplicationFunctions applicationFunctions = ApplicationFunctions
				.getInstance();
		applicationFunctions.init(test);

		// Actual test functionality starts from here
		//Launch Application
		 commonFunctions.funLaunchURL(Initializer.getInstance().GetValue(
		 "app.test.test05"));
		 
		 //Login Application
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
