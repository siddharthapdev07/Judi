package com.agm.framework.FunctionLibraries;

import com.relevantcodes.extentreports.ExtentTest;

public class TestScripts {
	
	private static TestScripts objTs = null;
	public ExtentTest test = null;
	public static TestScripts getInstance() {
		if (objTs == null)
			objTs = new TestScripts();
		return objTs;
	}
	public void init(ExtentTest test) {
		this.test = test;
	}

	public void DemoTest() {
		
		// Login Application
		ApplicationFunctions.getInstance().funLoginApplication();		
		//Select Test Trail
		ApplicationFunctions.getInstance().funSelectTestTrial("AutomationTestTrial");	
		//Invite Users
		ApplicationFunctions.getInstance().funInviteUser("autotest_freetodelete3@mx-intr.agmednet.net", "Event Coordinator");
		//Register users
		ApplicationFunctions.getInstance().funRegistration("autotest_freetodelete3@mx-intr.agmednet.net");
		
	}
}
