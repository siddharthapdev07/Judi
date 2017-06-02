package com.agm.Judi.tests;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.ldap.ExtendedRequest;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import autoitx4java.AutoItX;

import com.agm.framework.FunctionLibraries.ApplicationFunctions;
import com.agm.framework.FunctionLibraries.CommonFunctions;
import com.agm.framework.FunctionLibraries.DB;
import com.agm.framework.FunctionLibraries.TestData;
import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;
import com.jacob.com.LibraryLoader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class DemoTest123 {

	public String strLogFileName;
	public ExtentReports extent;
	public ExtentTest test;
	public String strSQLQuery;
	public String strField;
	public AutoItX objAutoIT = null;
	public ITestResult result;
	public boolean iStatus = true;

	@BeforeMethod
	public void beforeMethod() {
		// ********************************* INITIAL SETUP    *****************************************	
		Stage.getInstance().setStatus(iStatus);
		strLogFileName = this.getClass().getSimpleName()
				+ "_"
				+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar
						.getInstance().getTime());														// Setting the Log file path in system variables
		System.setProperty("logFileName", strLogFileName);
		CommonFunctions.getInstance().funStartTestCase(
				this.getClass().getSimpleName());		
		// ********************************* R-E-P-O-R-T-S     ***************************************		
		extent = new ExtentReports(Initializer.getInstance().GetValue(
				"java.results.path")
				+ strLogFileName + ".html", true);														// new instance for Extent Reports		
		extent.loadConfig(new File(
				"src/main/resources/Config-ExtentReports.xml"));		
		test = extent.startTest(this.getClass().getSimpleName(), this.getClass().getSimpleName());     	// starting test	
		test.assignAuthor("Suresh Kumar Mylam");														// Set Category and author to report		
		test.assignCategory("Regression");		
		// ********************************* AUTOIT SETUP   *****************************************			
		File file = new File(Initializer.getInstance().GetValue("java.autoit.jacob"));		
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
		objAutoIT = new AutoItX();		
		// ********************************* TestRail details -Static Data   *****************************************
		TestData.getInstance().funLoadTestData(this.getClass().getSimpleName());
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
//		applicationFunctions.init(test);	
		
		//*********************************************  TEST FUNCTIONALITY START ************************************* 		
		// Launch Application
		applicationFunctions.funLaunchURL(Initializer.getInstance().GetValue(
				"app.test.test05"));
		// Login Application
		applicationFunctions.funLoginApplication();		
		//Navigate to Trial Admin page
		applicationFunctions.funNavigate_TrialAdmin();
		//Select Test Trail
		applicationFunctions.funSelectTestTrial("AutomationTestTrial");	
		//Invite Users
		applicationFunctions.funInviteUser("autotest_freetodelete3@mx-intr.agmednet.net", "Event Coordinator");
		//Register users
		applicationFunctions.funRegistration("autotest_freetodelete3@mx-intr.agmednet.net");
		
		
		//*********************************************  TEST FUNCTIONALITY END ************************************* 
		//Finalizing the reports
		commonFunctions.funFinalizeResults();
		
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {		
//		try {
//			CommonFunctions.getInstance().funQuitBrowser();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			CommonFunctions.getInstance().funLog(
//					"Issue in terminating the browser");
//		}
		// ending test
		extent.endTest(test);
		// writing everything to document
		extent.flush();
		try{
		    switch (result.getStatus()) {
		    case ITestResult.SUCCESS:
		    	CommonFunctions.getInstance().funUpdateResultsToTestRail("PASS");
		        break;
		    case ITestResult.FAILURE:
		    	CommonFunctions.getInstance().funUpdateResultsToTestRail("FAIL");
		        break;
		    default:
		        throw new RuntimeException("Invalid status");
		    }
			}catch(Exception e){
				CommonFunctions.getInstance().funLog("Invalid Result status");
			}
		CommonFunctions.getInstance().funEndTestCase(this.getClass().getSimpleName());

	}
}
