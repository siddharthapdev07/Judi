package com.agm.Judi.tests;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.ldap.ExtendedRequest;

import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.agm.framework.FunctionLibraries.CommonFunctions;
import com.agm.framework.helpers.Initializer;


public class DemoTest {

	String strLogFileName;
	public String filePath = "./extentreport.html";
  @BeforeMethod
  public void beforeMethod() {
	  //Setting the Logfile path in system variables
	  CommonFunctions.getInstance().funStartTestCase(this.getClass().getSimpleName());
	  strLogFileName = this.getClass().getSimpleName() + "_" +new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	  System.setProperty("logFileName", strLogFileName);  

 }

  
  @Test
  public void Demo() {

	  CommonFunctions.getInstance().funLaunchURL(Initializer.getInstance().GetValue("app.test.test05"));
	  
  }

  @AfterMethod
  public void afterMethod() {
	  try {
		CommonFunctions.getInstance().funQuitBrowser();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		CommonFunctions.getInstance().funLog("Issue in terminating the browser");
	}

	  CommonFunctions.getInstance().funEndTestCase(this.getClass().getSimpleName()); 
  }

}
