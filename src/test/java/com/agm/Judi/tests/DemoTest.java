package com.agm.Judi.tests;

import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.agm.framework.FunctionLibraries.CommonFunctions;
import com.agm.framework.FunctionLibraries.TestScripts;

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
