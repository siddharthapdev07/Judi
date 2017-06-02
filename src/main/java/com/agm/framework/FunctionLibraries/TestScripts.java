package com.agm.framework.FunctionLibraries;

import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;
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

//	ApplicationFunctions.getInstance() ApplicationFunctions.getInstance() = ApplicationFunctions.getInstance().getInstance();
//	CommonFunctions.getInstance() CommonFunctions.getInstance() = CommonFunctions.getInstance().getInstance();
//	TestData testData = TestData.getInstance();
//	DB db = DB.getInstance();
//	Initializer initializer = Initializer.getInstance();
//	Stage stage = Stage.getInstance();
	
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
