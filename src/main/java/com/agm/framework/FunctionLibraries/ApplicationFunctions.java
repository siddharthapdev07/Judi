package com.agm.framework.FunctionLibraries;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;
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

	CommonFunctions commonFunctions = CommonFunctions.getInstance();
	TestScripts testScripts = TestScripts.getInstance();
	TestData testData = TestData.getInstance();
	DB db = DB.getInstance();
	Initializer initializer = Initializer.getInstance();
	Stage stage = Stage.getInstance();
	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funLaunchURL() Description : This function will launch
	 * URL Author : Suresh Kumar,Mylam Date : 03 May 2017 Parameter : strBrowser
	 * = iexplore.exe/chrome, strURL = URL
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funLaunchURL(String strURL) {
		String strBrowser = initializer.GetValue("gui.browser");

		try {
			// Killing opened browser by process
			commonFunctions.funKillbyProcess(strBrowser);
			// FirefoxProfile prof;
			switch (strBrowser.toUpperCase()) {
			case "FIREFOX":
				System.setProperty("webdriver.gecko.driver", Initializer
						.getInstance().GetValue("java.firefox.path"));
				driver = new FirefoxDriver();
				break;
			case "IE":
				System.setProperty("webdriver.ie.driver", Initializer
						.getInstance().GetValue("java.ie.path"));
				driver = new InternetExplorerDriver();
				break;
			case "CHROME":
				System.setProperty("webdriver.chrome.driver", Initializer
						.getInstance().GetValue("java.chrome.path"));
				driver = new ChromeDriver();
				break;
			default:
				System.setProperty("webdriver.gecko.driver", Initializer
						.getInstance().GetValue("java.firefox.path"));
				driver = new FirefoxDriver();
			}
			driver.manage().window().maximize();
			ApplicationFunctions applicationFunctions = ApplicationFunctions
					.getInstance();
			applicationFunctions.init(driver);
			driver.get(strURL);
			commonFunctions.funStepValidate("TEXT", driver.getTitle()
					.toString(), "AG Mednet Portal",
					"validate the Browser Title", true, false);
		} catch (Exception e) {
			commonFunctions.funLog("Issue on launching URL. Exception : "
					+ e.getMessage());
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funLoginApplication() Description : This function Login
	 * to the application Author : Suresh Kumar,Mylam Date : 23 May 2017
	 * Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funLoginApplication() {
		// Launch URL
		funLaunchURL(initializer.GetValue("app.test.test05"));
		try {
			commonFunctions.getElement(driver, "judi.test.login.username")
					.sendKeys(
							initializer.GetValue(
									"app.test.test05.username"));
			test.log(LogStatus.PASS, "Userid Entered successfully", "");
		} catch (Exception e) {
			commonFunctions.funLog("Issue identifying the object - UserName"
					+ e.getMessage());
			test.log(LogStatus.FAIL,
					"Exception in identifying the UserName field", "");
		}
		try {
			commonFunctions.getElement(driver, "judi.test.login.password")
					.sendKeys(
							initializer.GetValue(
									"app.test.test05.password"));
			test.log(LogStatus.PASS, "Password field verification", "");
		} catch (Exception e) {
			commonFunctions.funLog("Issue identifying the object - Password"
					+ e.getMessage());
			test.log(LogStatus.FAIL,
					"Exception in identifying the password field", "");
		}
		try {
			commonFunctions.getElement(driver, "judi.test.login.login").click();
			test.log(LogStatus.PASS, "Login Button is clicked successfully", "");
			commonFunctions.funWait(15);
		} catch (Exception e) {
			commonFunctions.funLog("Issue identifying the object - LoginButton"
					+ e.getMessage());
			test.log(LogStatus.FAIL, "Exception in clicking the Login button",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		commonFunctions.funElementValidate(
				commonFunctions.getElement(driver, "judi.test.home.logout"),
				"ISPRESENT", "Validating LogOut element : ", true, true);

	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funLogOutApplication() Description : This function LogOut
	 * to the application Author : Suresh Kumar,Mylam Date : 30 May 2017
	 * Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funLogOutApplication() {
		try {
			commonFunctions.getElement(driver, "judi.test.home.logout").click();

			test.log(LogStatus.PASS, "LogOut button is clicked", "");
		} catch (Exception e) {
			commonFunctions.funLog("Issue identifying the object - LogOut"
					+ e.getMessage());
			test.log(LogStatus.FAIL,
					"Exception in identifying the LogOut button",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		commonFunctions.funWait(3);
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
			commonFunctions.getElement(driver, "judi.test.home.trialAdmin")
					.click();
			commonFunctions.funWait(12);
			test.log(LogStatus.PASS,
					"Trial Administration is clicked successfully", "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Trial Administration "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Exception in identifying the Trial Administration element",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		commonFunctions.funWait(3);
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funSelectTestTrial() Description : This function will
	 * select the test Trial in Trail drop down Author : Suresh Kumar,Mylam Date
	 * : 23 May 2017 Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funSelectTestTrial(String strValue) {
		// Navigate to Trial Admin page
		funNavigate_TrialAdmin();
		driver.switchTo().frame("asdfasdfasdfsdfa");
		// Navigate to Users page
		try {
			commonFunctions.getElement(driver, "judi.test.trialAdmin.Users")
					.click();
		} catch (Exception e) {
			commonFunctions.funLog("Issue identifying the object - Users "
					+ e.getMessage());
		}
		commonFunctions.funWait(4);
		try {
			// Select Value in drop down
			commonFunctions.getElement(driver, "judi.test.trialAdmin.Trail")
					.sendKeys("AutomationTestTrial");
			commonFunctions.getElement(driver, "judi.test.trialAdmin.Trail")
					.click();
			Select oE = new Select(commonFunctions.getElement(driver,
					"judi.test.trialAdmin.Trail"));
			WebElement oSelected = oE.getFirstSelectedOption();
			String strSelected = oSelected.getText();
			if (strSelected.trim().equalsIgnoreCase(strValue.trim())) {
				commonFunctions.funLog("Trial is selected in the drop down : "
						+ strValue);
				test.log(LogStatus.PASS, strValue
						+ " is selected in the drop down ", "");
			} else {
				commonFunctions
						.funLog("Expected Trial is NOT selected in the drop down : Actual value is - "
								+ strSelected
								+ " and Expected value is - "
								+ strValue);
				test.log(LogStatus.FAIL,
						"Expected Trial is NOT selected in the drop down : Actual value is - "
								+ strSelected + " and Expected value is - "
								+ strValue, test
								.addScreenCapture(commonFunctions
										.funTakeScreenshot(Thread
												.currentThread()
												.getStackTrace()[1]
												.getMethodName())));
			}
			// JavascriptExecutor executor = (JavascriptExecutor) driver;
			// executor.executeScript("arguments[0].click()", obj);
			// obj.click();
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// String jsCmd =
			// "document.getElementsByName('selecttrial')[0].value='" + strValue
			// + "'";
			// js.executeScript(jsCmd);
		} catch (Exception e) {
			commonFunctions.funLog("Issue on Identifying drop down : "
					+ strValue + ", Exception : " + e.getMessage());
			test.log(LogStatus.FAIL, strValue
					+ " is NOT present in the drop down ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		driver.switchTo().defaultContent();
		commonFunctions.funWait(1);
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funSelectTestTrial() Description : This function will
	 * select the test Trial in Trail drop down Author : Suresh Kumar,Mylam Date
	 * : 25 May 2017 Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funInviteUser(String strEmail, String strUserRole) {
		driver.switchTo().frame("asdfasdfasdfsdfa");
		// Navigate to Users page
		try {
			commonFunctions.getElement(driver, "judi.test.trialAdmin.Email")
					.clear();
			commonFunctions.getElement(driver, "judi.test.trialAdmin.Email")
					.sendKeys(strEmail);
			commonFunctions.funLog("Email entered successfully on Users page");
		} catch (Exception e) {
			commonFunctions.funLog("Issue identifying the object - Email "
					+ e.getMessage());
		}
		commonFunctions.funWait(3);
		try {
			commonFunctions.getElement(driver, "judi.test.trialAdmin.Role")
					.click();
			commonFunctions.funWait(3);
			// Select oE = new Select(commonFunctions.getElement(
			// driver, "judi.test.trialAdmin.Role"));
			// List<WebElement> allOptions = oE.getOptions();
			// for (WebElement oelement : allOptions) {
			// if (oelement.getText().toLowerCase().trim()
			// .equalsIgnoreCase(strUserRole.toLowerCase().trim())) {
			// oelement.click();
			// commonFunctions.funLog(
			// strUserRole + " is present and selected");
			// break;
			// }
			// }
			commonFunctions.getElement(driver, "judi.test.trialAdmin.Role")
					.sendKeys(strUserRole);

		} catch (Exception e) {
			commonFunctions.funLog("Issue on Identifying drop down : "
					+ strUserRole + ", Exception : " + e.getMessage());
			commonFunctions.getElement(driver, "judi.test.trialAdmin.Email")
					.click();
			test.log(LogStatus.FAIL, strUserRole
					+ " is NOT present in the drop down ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		try {
			commonFunctions.getElement(driver, "judi.test.trialAdmin.Invite")
					.click();
			commonFunctions
					.funLog("Invite button is clicked successfully on Users page");
		} catch (Exception e) {
			commonFunctions.funLog("Issue identifying the object - Invite "
					+ e.getMessage());
		}
		driver.switchTo().defaultContent();
		commonFunctions.funWait(1);
		// LogOut from Applications
		funLogOutApplication();
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funRegistration() Description : This function register
	 * the user to the application Author : Suresh Kumar,Mylam Date : 30 May
	 * 2017 Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funRegistration(String strEmail) {
		// Launch application
		driver.navigate().to(
				initializer.GetValue("app.test.test05"));
		commonFunctions.funWait(8);
		try {
			commonFunctions.getElement(driver, "judi.test.login.register")
					.click();
			commonFunctions.funWait(3);
			test.log(LogStatus.PASS, "Register button is clicked", "");
		} catch (Exception e) {
			commonFunctions.funLog("Issue identifying the object - Register"
					+ e.getMessage());
			test.log(LogStatus.FAIL,
					"Exception in identifying the Register button",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		try {
			commonFunctions.getElement(driver, "judi.test.register.email")
					.sendKeys(strEmail);
			commonFunctions.getElement(driver, "judi.test.register.email")
					.sendKeys(Keys.TAB);
			commonFunctions.funWait(1);
			test.log(LogStatus.PASS,
					"Register page - Email entered successfully", "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register--> Email"
							+ e.getMessage());
			test.log(LogStatus.FAIL,
					"Exception in identifying the object - Register--> Email",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
			commonFunctions.funWait(5);
		}

		try {
			commonFunctions.getElement(driver, "judi.test.register.continue")
					.click();
			commonFunctions.funWait(3);
			test.log(LogStatus.PASS, "Continue button is clicked successfully",
					"");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register--> Continue"
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Exception in identifying the object - Register--> Continue",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		commonFunctions.funWait(2);
		// Step 1 in Registration
		funRegistration_Step1("AutoTest1");
		// Step 2 in Registration
		funRegistration_Step2("United States");
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funRegistration_Step1() Description : This function
	 * complete the step 1 in registration process Author : Suresh Kumar,Mylam
	 * Date : 30 May 2017 Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funRegistration_Step1(String strUserName) {
		try {
			commonFunctions.getElement(driver,
					"judi.test.register.step1.userName").sendKeys(strUserName);
			commonFunctions.funWait(1);
			test.log(LogStatus.PASS, "User Name is entered successfully"
					+ strUserName, "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register-->Step1--> UserName "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register-->Step1--> UserName ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		try {
			commonFunctions.getElement(driver,
					"judi.test.register.step1.password").sendKeys(strUserName);
			test.log(LogStatus.PASS, "password is entered successfully", "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register-->Step1--> password "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register-->Step1--> password ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		try {
			CommonFunctions
					.getInstance()
					.getElement(driver,
							"judi.test.register.step1.confirmPassword")
					.sendKeys(strUserName);
			test.log(LogStatus.PASS, "confirmPassword is entered successfully",
					"");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register-->Step1--> confirmPassword "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register-->Step1--> confirmPassword ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		try {
			commonFunctions.getElement(driver,
					"judi.test.register.step1.firstName").sendKeys(strUserName);
			test.log(LogStatus.PASS, "firstName is entered successfully", "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register-->Step1--> firstName "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register-->Step1--> firstName ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		try {
			commonFunctions.getElement(driver,
					"judi.test.register.step1.lastName").sendKeys(strUserName);
			test.log(LogStatus.PASS, "lastName is entered successfully", "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register-->Step1--> lastName "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register-->Step1--> lastName ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		try {
			commonFunctions.getElement(driver,
					"judi.test.register.step1.phoneNo").sendKeys(strUserName);
			commonFunctions.getElement(driver,
					"judi.test.register.step1.phoneNo").sendKeys(Keys.TAB);
			commonFunctions.funWait(1);
			test.log(LogStatus.PASS, "phoneNo is entered successfully", "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register-->Step1-->phoneNo "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register-->Step1--> phoneNo ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		commonFunctions.funWait(1);
		try {
			commonFunctions.getElement(driver,
					"judi.test.register.step1.continue").click();
			test.log(LogStatus.PASS,
					"Continue button is clicked successfully on Step1 page", "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register--> Step1-->Continue "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register--> Step1-->Continue ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funRegistration_Step2() Description : This function
	 * complete the step 2 in registration process Author : Suresh Kumar,Mylam
	 * Date : 30 May 2017 Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funRegistration_Step2(String strValue) {
		try {
			commonFunctions.getElement(driver, "judi.test.register.step2.org")
					.sendKeys(strValue);
			test.log(LogStatus.PASS, "Organization is entered successfully"
					+ strValue, "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register-->Step2--> Organization "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register--> Step2--> Organization ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		try {
			commonFunctions.getElement(driver,
					"judi.test.register.step2.address1").sendKeys(strValue);
			test.log(LogStatus.PASS, "address1 is entered successfully", "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register-->Step2--> address1 "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register-->Step2--> address1 ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		try {
			commonFunctions.getElement(driver,
					"judi.test.register.step2.address2").sendKeys(strValue);
			test.log(LogStatus.PASS, "address2 is entered successfully", "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register--> Step2--> address2 "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register--> Step2--> address2 ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		try {
			commonFunctions.getElement(driver, "judi.test.register.step2.city")
					.sendKeys(strValue);
			test.log(LogStatus.PASS, "City is entered successfully", "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register-->Step2--> City "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register--> Step2--> City ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		try {
			commonFunctions
					.getElement(driver, "judi.test.register.step2.state")
					.sendKeys(strValue);
			test.log(LogStatus.PASS, "State is entered successfully", "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register--> Step2-->State "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register--> Step2-->State ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		try {
			commonFunctions.getElement(driver,
					"judi.test.register.step2.postal").sendKeys(strValue);
			commonFunctions.getElement(driver,
					"judi.test.register.step2.postal").sendKeys(Keys.TAB);
			test.log(LogStatus.PASS, "Postal code is entered successfully", "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register-->Step2-->Postal code "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register-->Step2-->Postal code",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
		try {
			commonFunctions.getElement(driver,
					"judi.test.register.step2.country").sendKeys(strValue);
			commonFunctions.getElement(driver,
					"judi.test.register.step2.country").sendKeys(Keys.TAB);
			test.log(LogStatus.PASS, "Country is entered successfully", "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register-->Step2-->Country"
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register-->Step2-->Country",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}

		commonFunctions.funWait(1);
		try {
			commonFunctions.getElement(driver,
					"judi.test.register.step2.continue").click();
			test.log(LogStatus.PASS,
					"Continue button is clicked successfully on Step2 page", "");
		} catch (Exception e) {
			commonFunctions
					.funLog("Issue identifying the object - Register--> Step2-->Continue "
							+ e.getMessage());
			test.log(
					LogStatus.FAIL,
					"Issue identifying the object - Register--> Step2-->Continue ",
					test.addScreenCapture(commonFunctions
							.funTakeScreenshot(Thread.currentThread()
									.getStackTrace()[1].getMethodName())));
		}
	}

}
