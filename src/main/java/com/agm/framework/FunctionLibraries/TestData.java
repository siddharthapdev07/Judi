package com.agm.framework.FunctionLibraries;

import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;
import com.relevantcodes.extentreports.ExtentTest;

public class TestData {

	private static TestData objTd = null;
	public ExtentTest test = null;

	public static TestData getInstance() {
		if (objTd == null)
			objTd = new TestData();
		return objTd;
	}

	int iCaseID;
	int iTestID;
	int iRunID;

	public void init(ExtentTest test) {
		this.test = test;
	}
	
	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funLoadTestDetailsFromTestRail() Description : This
	 * function will Load the TestID,Case ID and Run ID for the test case Author
	 * : Suresh Kumar,Mylam Date : 17 May 2017 Parameter : strTestCaseName :
	 * Provide the test case name
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funLoadTestData(String strTestCaseName) {
		try {
			switch (strTestCaseName.toUpperCase()) {
			case "DEMOTEST":
				iCaseID = 541421;
				iTestID = 1079709;
				iRunID = 2081;				
				break;
			case "DEMOTEST2":
				iCaseID = 541421;
				iTestID = 1079709;
				iRunID = 2081;
				break;
			case "DEMOTEST3":
				iCaseID = 541421;
				iTestID = 1079709;
				iRunID = 2081;
				break;
			default:
				CommonFunctions.getInstance()
						.funLog("Issue on identifying the test case - Please add New Case for the running Test");

			}
			Stage.getInstance().setCaseID(iCaseID);
			Stage.getInstance().setTestID(iTestID);
			Stage.getInstance().setRunID(iRunID);

		} catch (Exception e) {
			CommonFunctions.getInstance()
					.funLog("Exception occured while setting test details. Exception : "
							+ e.getMessage());
		}
	}

}
