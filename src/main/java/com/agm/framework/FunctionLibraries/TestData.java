package com.agm.framework.FunctionLibraries;

import com.agm.framework.helpers.Stage;

public class TestData {
	
	private static TestData objTd = null;

	public static TestData getInstance() {
		if (objTd == null)
			objTd = new TestData();
		return objTd;
	}
	
	int iCaseID;
	int iTestID;
	int iRunID;

	ApplicationFunctions applicationFunctions = ApplicationFunctions
			.getInstance();
	CommonFunctions commonFunctions = CommonFunctions.getInstance();	
	
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
				commonFunctions.funLog("Issue on identifying the test case - Please add New Case for the running Test");

			}
			Stage.getInstance().setCaseID(iCaseID);
			Stage.getInstance().setTestID(iTestID);
			Stage.getInstance().setRunID(iRunID);
			
		} catch (Exception e) {
			commonFunctions.funLog("Exception occured while setting test details. Exception : "
					+ e.getMessage());
		}
	}
	
	
	
	
	
	
}
