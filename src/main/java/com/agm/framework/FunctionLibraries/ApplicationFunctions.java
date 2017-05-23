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
			CommonFunctions.getInstance().getElement(driver, "judi.test.login.username").sendKeys(Initializer.getInstance().GetValue("app.test.test05.username"));
			test.log(LogStatus.PASS, "Userid Entered successfully","");
		}catch(Exception e){
			CommonFunctions.getInstance().funLog("Issue identifying the object - UserName" + e.getMessage());
			test.log(LogStatus.FAIL, "Exception in identifying the UserName field","");
		}
		try{		
			CommonFunctions.getInstance().getElement(driver, "judi.test.login.password").sendKeys(Initializer.getInstance().GetValue("app.test.test05.password"));
			test.log(LogStatus.PASS, "Password field verification","");
		}catch(Exception e){
			CommonFunctions.getInstance().funLog("Issue identifying the object - Password" + e.getMessage());
			test.log(LogStatus.FAIL, "Exception in identifying the password field","");
		}
		try{		
			CommonFunctions.getInstance().getElement(driver, "judi.test.login.login").click();
			test.log(LogStatus.PASS, "Login Button is clicked successfully","");
			CommonFunctions.getInstance().funWait(15);
		}catch(Exception e){
			CommonFunctions.getInstance().funLog("Issue identifying the object - LoginButton" + e.getMessage());
			test.log(LogStatus.FAIL, "Exception in clicking the Login button",test.addScreenCapture(CommonFunctions.getInstance().funTakeScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName())));
		}
		//Login Validation
//		try{		
//			if(CommonFunctions.getInstance().getElement(driver, "judi.test.home.logout").isDisplayed()==true){
//				test.log(LogStatus.PASS, "Login to application is successful",test.addScreenCapture(CommonFunctions.getInstance().funTakeScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName())));
//			}else{
//				test.log(LogStatus.FAIL, "Login to application is Failed","");
//			}
//		}catch(Exception e){
//			CommonFunctions.getInstance().funLog("Issue identifying the object - LogOut" + e.getMessage());
//			test.log(LogStatus.FAIL, "Issue identifying the object - LogOut",test.addScreenCapture(CommonFunctions.getInstance().funTakeScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName())));
//		}
		CommonFunctions.getInstance().funElementValidate(CommonFunctions.getInstance().getElement(driver, "judi.test.home.logout"),"ISPRESENT", "Validating LogOut element : ", true, true);
		
	}
	

}
