package com.agm.framework.FunctionLibraries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.testng.annotations.Parameters;

import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;
import com.relevantcodes.extentreports.ExtentTest;

public class TestScripts {

	private static TestScripts objTs = null;
	public ExtentTest test = null;
	public String strTrial = null;

	public static TestScripts getInstance() {
		if (objTs == null)
			objTs = new TestScripts();
		return objTs;
	}

	public void init(ExtentTest test) {
		this.test = test;
	}

	public void Validate_TrialAdmin_TrialsSitesSubjectsUsers() {
		strTrial = CommonFunctions.getInstance().funGetTestData(
				Stage.getInstance().getTestName(), "TrialName");
		Stage.getInstance().setTrial(strTrial);
		Random rand = new Random();
		int selected = rand.nextInt(1000);
		String strSiteID = "AutoSite"
				+ selected
				+ new SimpleDateFormat("yyMMdd_HHmmss").format(Calendar
						.getInstance().getTime());
		// Creating subjects csv file
		ApplicationFunctions.getInstance().funCreateSubjects(strSiteID);
		// Login Application
		ApplicationFunctions.getInstance().funLoginApplication();
		// Navigate to Trial Administration page and Select Trial
		ApplicationFunctions.getInstance().funSelectTrial(strTrial);
		// Select Test Trail
		ApplicationFunctions.getInstance().funTrialAdmin_Trials(strTrial);
		// add site
		ApplicationFunctions.getInstance().funTrialAdmin_Sites("ADDSITE",
				strSiteID);
		// add subject
		ApplicationFunctions.getInstance().funTrialAdmin_Subjects("ADDSUBJECT",
				"");
		// add multiple sites
		ApplicationFunctions.getInstance().funTrialAdmin_Subjects(
				"ADDMULTIPLESUBJECTS", "");
		// add multiple sites
		ApplicationFunctions.getInstance().funTrialAdmin_Sites(
				"ADDMULTIPLESITES", "");
		// //Invite Users
		ApplicationFunctions.getInstance().funInviteUser(
				"autotest_freetodelete3@mx-intr.agmednet.net",
				"Event Coordinator");
//		// Register users
//		ApplicationFunctions.getInstance().funRegistration(
//				"autotest_freetodelete3@mx-intr.agmednet.net");

	}

	public void Test_DemoTest2(String strTrial) {
		System.out.println(strTrial);

	}
}
