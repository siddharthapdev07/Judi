package com.agm.Judi.tests;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.ldap.ExtendedRequest;

import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.agm.framework.FunctionLibraries.CommonFunctions;
import com.comcast.framework.helpers.Initializer;
import com.relevantcodes.extentreports.ExtentReports;


public class DemoTest {

  ExtentReports report;
  ExtendedRequest xLogger;
  ITestResult result;
  String strLogFileName;
  
  @BeforeMethod
  public void beforeMethod() {
	  strLogFileName = this.getClass().getSimpleName() + "_" +new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	  System.out.println(strLogFileName);
	  System.setProperty("logFileName", strLogFileName);
	  //Pre-Conditions	  
  }
  
  @Test
  public void Demo() {
	  CommonFunctions.getInstance().funStartTestCase(this.getClass().getSimpleName());
	  CommonFunctions.getInstance().funLaunchURL(Initializer.getInstance().GetValue("app.test.test05"));
//	  CommonFunctions.getInstance().funSendEmail("PASS", "JUDI", "SUCCESS");
	  CommonFunctions.getInstance().funEndTestCase(this.getClass().getSimpleName());
	  
  }

  @AfterMethod
  public void afterMethod() {
	  
  }

}
