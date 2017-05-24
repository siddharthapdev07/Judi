package com.agm.framework.FunctionLibraries;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.agm.framework.helpers.Initializer;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ApplicationFunctions {
	// Creating singleton
	private static ApplicationFunctions objApf = null;

	public WebDriver driver = null;
	public ExtentTest test = null;
	WebElement element;

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

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funLoginApplication() Description : This function Login
	 * to the application Author : Suresh Kumar,Mylam Date : 23 May 2017
	 * Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funLoginApplication() {
		try {
			CommonFunctions
					.getInstance()
					.getElement(driver, "judi.test.login.username")
					.sendKeys(
							Initializer.getInstance().GetValue(
									"app.test.test05.username"));
			test.log(LogStatus.PASS, "Userid Entered successfully", "");
		} catch (Exception e) {
			CommonFunctions.getInstance().funLog(
					"Issue identifying the object - UserName" + e.getMessage());
			test.log(LogStatus.FAIL,
					"Exception in identifying the UserName field", "");
		}
		try {
			CommonFunctions
					.getInstance()
					.getElement(driver, "judi.test.login.password")
					.sendKeys(
							Initializer.getInstance().GetValue(
									"app.test.test05.password"));
			test.log(LogStatus.PASS, "Password field verification", "");
		} catch (Exception e) {
			CommonFunctions.getInstance().funLog(
					"Issue identifying the object - Password" + e.getMessage());
			test.log(LogStatus.FAIL,
					"Exception in identifying the password field", "");
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, "judi.test.login.login").click();
			test.log(LogStatus.PASS, "Login Button is clicked successfully", "");
			CommonFunctions.getInstance().funWait(15);
		} catch (Exception e) {
			CommonFunctions.getInstance().funLog(
					"Issue identifying the object - LoginButton"
							+ e.getMessage());
			test.log(LogStatus.FAIL, "Exception in clicking the Login button",
					test.addScreenCapture(CommonFunctions.getInstance()
							.funTakeScreenshot(
									Thread.currentThread().getStackTrace()[1]
											.getMethodName())));
		}
		// Login Validation
		// try{
		// if(CommonFunctions.getInstance().getElement(driver,
		// "judi.test.home.logout").isDisplayed()==true){
		// test.log(LogStatus.PASS,
		// "Login to application is successful",test.addScreenCapture(CommonFunctions.getInstance().funTakeScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName())));
		// }else{
		// test.log(LogStatus.FAIL, "Login to application is Failed","");
		// }
		// }catch(Exception e){
		// CommonFunctions.getInstance().funLog("Issue identifying the object - LogOut"
		// + e.getMessage());
		// test.log(LogStatus.FAIL,
		// "Issue identifying the object - LogOut",test.addScreenCapture(CommonFunctions.getInstance().funTakeScreenshot(Thread.currentThread().getStackTrace()[1].getMethodName())));
		// }
		CommonFunctions.getInstance().funElementValidate(
				CommonFunctions.getInstance().getElement(driver,
						"judi.test.home.logout"), "ISPRESENT",
				"Validating LogOut element : ", true, true);

	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funNavigate_TrialAdmin() Description : This function
	 * Navigate to Trail Administration Author : Suresh Kumar,Mylam Date : 23
	 * May 2017 Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funNavigate_TrialAdmin() {
		try {
			CommonFunctions.getInstance()
					.getElement(driver, "judi.test.home.trialAdmin").click();
			CommonFunctions.getInstance().funWait(12);
			test.log(LogStatus.PASS,
					"Trial Administration is clicked successfully", "");
		} catch (Exception e) {
			CommonFunctions.getInstance().funLog(
					"Issue identifying the object - Trial Administration "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Exception in identifying the Trial Administration element",
					test.addScreenCapture(CommonFunctions.getInstance()
							.funTakeScreenshot(
									Thread.currentThread().getStackTrace()[1]
											.getMethodName())));
		}
		CommonFunctions.getInstance().funWait(3);
	}
	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funSelectTestTrial() Description : This function
	 * will select the test Trial in Trail drop down Author : Suresh Kumar,Mylam Date : 23
	 * May 2017 Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funSelectTestTrial(String strValue) {
		try {
			driver.switchTo().frame("asdfasdfasdfsdfa");
			WebElement obj = CommonFunctions.getInstance().getElement(driver,
					"judi.test.trialAdmin.Trail");
	        JavascriptExecutor js = (JavascriptExecutor) driver;	        
	         String jsCmd = "document.getElementsByName('selecttrial')[0].value='" + strValue + "'";
	         js.executeScript(jsCmd);
	         
	         
//			Select oE = new Select(CommonFunctions.getInstance().getElement(driver,
//					"judi.test.trialAdmin.Trail"));
//
//			List<WebElement> allOptions = oE.getOptions();
//		    for (WebElement oelement : allOptions) {
//		        if(oelement.getText().toLowerCase().trim().equalsIgnoreCase(strValue)){ 
//		        	oelement.click();
//		        System.out.println(oelement.getText());
//		        CommonFunctions.getInstance().funLog(strValue + " is present and selected");
//		        }
//		    }
		} catch (Exception e) {
			CommonFunctions.getInstance().funLog("Issue on Identifying drop down : " + strValue
					+ ", Exception : " + e.getMessage());
			test.log(LogStatus.FAIL, strValue
					+ " is NOT present in the drop down ", test
					.addScreenCapture(CommonFunctions.getInstance()
							.funTakeScreenshot(
									Thread.currentThread()
											.getStackTrace()[1]
											.getMethodName())));
		}	
		driver.switchTo().defaultContent();
		CommonFunctions.getInstance().funWait(1);		
	}
}
