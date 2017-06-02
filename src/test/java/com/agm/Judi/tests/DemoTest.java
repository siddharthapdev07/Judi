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
import com.agm.framework.FunctionLibraries.TestScripts;
import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;
import com.jacob.com.LibraryLoader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class DemoTest {

	public ITestResult result;
	
	CommonFunctions commonFunctions = CommonFunctions.getInstance();
	TestScripts testScripts = TestScripts.getInstance();
	
	@BeforeMethod
	public void beforeMethod() {		
		commonFunctions.funBeforeTest();		
	}

	@Test
	public void Test() {	
		//Test Script
		testScripts.DemoTest();	
	
		//Finalizing the reports
		commonFunctions.funFinalizeResults();
		
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {		
		commonFunctions.funAfterTest(result);		
	}
}
