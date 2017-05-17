package com.agm.testrail;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.agm.framework.FunctionLibraries.CommonFunctions;

import au.com.bytecode.opencsv.CSVReader;

public class TestResults {
	@Test
	public void funUpdateResults() {
		String testRailURL = "https://agmednet.testrail.com";
		String testRailUser = "smylam@agmednet.com";
		String testRailUserPassword = "TestRail123";
		int i;

		JSONObject r;
		List testCases = new ArrayList();
		Map testCasesResults = new HashMap();
		Map data = new HashMap();
		
		int iCaseID = 541421;
		int iTestID = 1079709;
		int iRunId = 2081;
		String strResultStatus = "PASS";								//Need to pass from test case
		
		APIClient client = new APIClient(testRailURL);
		client.setUser(testRailUser);
		client.setPassword(testRailUserPassword);
	
		testCases.add(iTestID);
		switch (strResultStatus) {
		case "PASS":
			testCasesResults.put(iTestID, 1);
			break;
		case "FAIL":
			testCasesResults.put(iTestID, 5);
			break;
		default:
			testCasesResults.put(iTestID, 4);
			break;
		}
		
		for (i = 0; i < testCasesResults.size(); i++) {

			iTestID = (int) testCases.get(i);
			data.put("status_id", testCasesResults.get(iTestID));
			try {
				r = (JSONObject) client.sendPost("add_result_for_case/"+ iRunId +"/"+ iCaseID, data);
			} catch (MalformedURLException e) {
				CommonFunctions.getInstance().funLog("Issue on forming the API. Exception : " + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				CommonFunctions.getInstance().funLog("Issue with Test data used in API. Exception : " + e.getMessage());
				e.printStackTrace();
			} catch (APIException e) {
				CommonFunctions.getInstance().funLog("Issue on sending API. Exception : " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
