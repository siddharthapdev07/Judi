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
import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;
import com.jacob.com.LibraryLoader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class DemoTest {

	public String strSQLQuery;
	public String strField;
	public ITestResult result;
	
	ApplicationFunctions applicationFunctions = ApplicationFunctions.getInstance();
	CommonFunctions commonFunctions = CommonFunctions.getInstance();
	
	@BeforeMethod
	public void beforeMethod() {		
		commonFunctions.funBeforeTest();		
	}

	@Test
	public void Test() {		
		// Login Application
		applicationFunctions.funLoginApplication();		
		//Select Test Trail
		applicationFunctions.funSelectTestTrial("AutomationTestTrial");	
		//Invite Users
		applicationFunctions.funInviteUser("autotest_freetodelete3@mx-intr.agmednet.net", "Event Coordinator");
		//Register users
		applicationFunctions.funRegistration("autotest_freetodelete3@mx-intr.agmednet.net");
		
		//Finalizing the reports
		commonFunctions.funFinalizeResults();
		
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {		
		commonFunctions.funAfterTest(result);		
	}
}
