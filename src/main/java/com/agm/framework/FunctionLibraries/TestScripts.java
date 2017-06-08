package com.agm.framework.FunctionLibraries;

import com.agm.framework.helpers.Initializer;
import com.relevantcodes.extentreports.ExtentTest;

public class TestScripts {
	
	private static TestScripts objTs = null;
	public ExtentTest test = null;
	public String strTrial= null;
	public static TestScripts getInstance() {
		if (objTs == null)
			objTs = new TestScripts();
		return objTs;
	}
	public void init(ExtentTest test) {
		this.test = test;
	}

	public void DemoTest() {
		strTrial  = "Judi_Demo";
		String strSiteID = "AutoSite2";
		// Login Application
		ApplicationFunctions.getInstance().funLoginApplication();	
		// Navigate to Trial Administration page and Select Trial
		ApplicationFunctions.getInstance().funSelectTrial(strTrial);	
		//Select Test Trail
		ApplicationFunctions.getInstance().funTrialAdmin_Trials(strTrial);
		//add multiple sites
		ApplicationFunctions.getInstance().funTrialAdmin_Sites("ADDSITE", strSiteID);
		
//		//Invite Users
//		ApplicationFunctions.getInstance().funInviteUser("autotest_freetodelete3@mx-intr.agmednet.net", "Event Coordinator");
//		//Register users
//		ApplicationFunctions.getInstance().funRegistration("autotest_freetodelete3@mx-intr.agmednet.net");
		
	}
}
