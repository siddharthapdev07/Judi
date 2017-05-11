package com.agm.framework.FunctionLibraries;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.agm.framework.helpers.Initializer;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ApplicationFunctions {
	// Creating singleton
		private static ApplicationFunctions objApf = null;

		public WebDriver driver = null;
		public ExtentTest test = null;
		WebElement element ;
		private ApplicationFunctions() {
			
		}

		public static ApplicationFunctions getInstance() {
			if (objApf == null)
				objApf = new ApplicationFunctions();
			return objApf;
		}

		public void init(ExtentTest test) {
			this.test = test;
		}
	
		public void init(WebDriver driver) {
			this.driver = driver;
		}
	
	public void funLoginApplication()
	{
		try{
			CommonFunctions.getInstance().getElement(driver, Initializer.getInstance().GetValue("judi.test.login.username")).sendKeys(Initializer.getInstance().GetValue("app.test.test05.username"));
//			element.sendKeys(Initializer.getInstance().GetValue("app.test.test05.username"));
			test.log(LogStatus.PASS, "Userid Field verification",test.addScreenCapture(CommonFunctions.getInstance().funTakeScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName())));
		}catch(Exception e){
			CommonFunctions.getInstance().funLog("Issue identifying the object - UserName" + e.getMessage());
			test.log(LogStatus.FAIL, "Userid Field verification",test.addScreenCapture(CommonFunctions.getInstance().funTakeScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName())));
		}
		try{		
			CommonFunctions.getInstance().getElement(driver, Initializer.getInstance().GetValue("judi.test.login.password")).sendKeys(Initializer.getInstance().GetValue("app.test.test05.password"));
//			element.sendKeys(Initializer.getInstance().GetValue("app.test.test05.password"));
			test.log(LogStatus.PASS, "Password field verification",test.addScreenCapture(CommonFunctions.getInstance().funTakeScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName())));
		}catch(Exception e){
			CommonFunctions.getInstance().funLog("Issue identifying the object - UserName" + e.getMessage());
			test.log(LogStatus.FAIL, "Password field verification",test.addScreenCapture(CommonFunctions.getInstance().funTakeScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName())));
		}
	}

}
