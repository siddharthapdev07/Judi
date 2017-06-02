package com.agm.framework.FunctionLibraries;

import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;

public class TestScripts {
	
	private static TestScripts objTs = null;
	
	public static TestScripts getInstance() {
		if (objTs == null)
			objTs = new TestScripts();
		return objTs;
	}
	

	ApplicationFunctions applicationFunctions = ApplicationFunctions
			.getInstance();
	CommonFunctions commonFunctions = CommonFunctions.getInstance();
	TestData testData = TestData.getInstance();
	DB db = DB.getInstance();
	Initializer initializer = Initializer.getInstance();
	Stage stage = Stage.getInstance();
	
	public void DemoTest() {
		// Login Application
		applicationFunctions.funLoginApplication();		
		//Select Test Trail
		applicationFunctions.funSelectTestTrial("AutomationTestTrial");	
		//Invite Users
		applicationFunctions.funInviteUser("autotest_freetodelete3@mx-intr.agmednet.net", "Event Coordinator");
		//Register users
		applicationFunctions.funRegistration("autotest_freetodelete3@mx-intr.agmednet.net");
	}
}
