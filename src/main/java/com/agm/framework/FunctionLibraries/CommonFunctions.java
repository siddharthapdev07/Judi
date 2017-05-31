package com.agm.framework.FunctionLibraries;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.imageio.ImageIO;


//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;

import autoitx4java.AutoItX;

import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;
import com.agm.simple.JSONObject;
import com.agm.testrail.APIClient;
import com.agm.testrail.APIException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CommonFunctions {

	// Creating singleton
	private static CommonFunctions objCmf = null;

	public WebDriver driver = null;
	public ExtentTest test = null;
	private AutoItX objAutoIT = null;
	int iCaseID;
	int iTestID;
	int iRunID;

	private CommonFunctions() {

	}

	public static CommonFunctions getInstance() {
		if (objCmf == null)
			objCmf = new CommonFunctions();
		return objCmf;
	}

	public void init(ExtentTest test) {
		this.test = test;
	}

	public void init(AutoItX objAutoIT) {
		this.objAutoIT = objAutoIT;
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funLoadTestDetailsFromTestRail() Description : This
	 * function will Load the TestID,Case ID and Run ID for the test case Author
	 * : Suresh Kumar,Mylam Date : 17 May 2017 Parameter : strTestCaseName :
	 * Provide the test case name
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funLoadTestDetailsFromTestRail(String strTestCaseName) {
		try {
			switch (strTestCaseName.toUpperCase()) {
			case "DEMOTEST":
				iCaseID = 541421;
				iTestID = 1079709;
				iRunID = 2081;
				break;
			case "DEMOTEST2":
				iCaseID = 541421;
				iTestID = 1079709;
				iRunID = 2081;
				break;
			case "DEMOTEST3":
				iCaseID = 541421;
				iTestID = 1079709;
				iRunID = 2081;
				break;
			default:
				funLog("Issue on identifying the test case - Please add New Case for the running Test");

			}
			Stage.getInstance().setCaseID(iCaseID);
			Stage.getInstance().setTestID(iTestID);
			Stage.getInstance().setRunID(iRunID);
		} catch (Exception e) {
			funLog("Exception occured while setting test details. Exception : "
					+ e.getMessage());
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funLaunchURL() Description : This function will launch
	 * URL Author : Suresh Kumar,Mylam Date : 03 May 2017 Parameter : strBrowser
	 * = iexplore.exe/chrome, strURL = URL
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funLaunchURL(String strURL) {
		String strBrowser = Initializer.getInstance().GetValue("gui.browser");

		try {
			// Killing opened browser by process
			funKillbyProcess(strBrowser);
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
			funStepValidate("TEXT", driver.getTitle().toString(),
					"AG Mednet Portal", "validate the Browser Title", true,
					false);
		} catch (Exception e) {
			funLog("Issue on launching URL. Exception : " + e.getMessage());
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funQuitBrowser() Description : This function will quit
	 * browser Author : Suresh Kumar,Mylam Date : 03 May 2017 Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funQuitBrowser() throws Exception {
		if (driver != null)
			driver.quit();
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funKillbyProcess() Description : This function will kill
	 * the process by its name Author : Suresh Kumar,Mylam Date : 03 May 2017
	 * Parameter : strProcessName : process name
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funKillbyProcess(String strProcessName) {
		String strProcess = null;

		try {
			switch (strProcessName.toUpperCase()) {
			case "IE":
				strProcess = "IEDriverServer.exe";
				break;
			case "FIREFOX":
				strProcess = "firefox.exe";
				break;
			}

			Runtime.getRuntime().exec("taskkill /f /IM " + strProcess);
			funWait(5);

		} catch (Exception e) {
			funLog("Issue on killing the process :" + strProcessName
					+ ". Exception : " + e.getMessage());
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funWait() Description : This function will make the
	 * script wait for given time Author : Suresh Kumar,Mylam Date : 3 May 2017
	 * Parameter : Time in seconds
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funWait(int intSeconds) {
		intSeconds = intSeconds * 1000;
		long waittime = 0;
		waittime = System.currentTimeMillis() + intSeconds;
		while (waittime > System.currentTimeMillis()) {
			// this loop will wait for 5 secs
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : getElement() Description : This function will return web
	 * element Author : Suresh Kumar,Mylam Date : 03 May 2017 Parameter : Driver
	 * : IE Driver, propertyName : Object Property Name from props loader
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public WebElement getElement(WebDriver driver, String propertyName) {
		WebElement element = null;

		String propertyValue = Initializer.getInstance().GetValue(propertyName)
				.trim();
		String propertyType = Initializer.getInstance().GetType(propertyName)
				.trim();

		try {
			switch (propertyType.toUpperCase()) {
			case "NAME":
				element = driver.findElement(By.name(propertyValue));
				break;
			case "ID":
				element = driver.findElement(By.id(propertyValue));
				break;
			case "CSS":
				element = driver.findElement(By.cssSelector(propertyValue));
				break;
			case "CLASSNAME":
				element = driver.findElement(By.className(propertyValue));
				break;
			case "XPATH":
				element = driver.findElement(By.xpath(propertyValue));
				break;
			case "LINKTEXT":
				 element = driver.findElement(By.linkText(propertyValue));
				break;
			}
		} catch (Exception e) {
			funLog("Issue on getting element : " + propertyValue
					+ " Exception : " + e.getMessage());
		}
		return element;
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : getElements() Description : This function will return
	 * list <WebElements> Author : Suresh Kumar,Mylam Date : 3 May 2017
	 * Parameter : Driver : IE Driver, propertyName : Object Property Name from
	 * props loader
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public List<WebElement> getElements(WebDriver driver, String propertyName) {
		List<WebElement> element = null;

		String propertyValue = Initializer.getInstance().GetValue(propertyName)
				.trim();
		String propertyType = Initializer.getInstance().GetType(propertyName)
				.trim();

		try {
			switch (propertyType.toUpperCase()) {
			case "NAME":
				element = driver.findElements(By.name(propertyValue));
				break;
			case "ID":
				element = driver.findElements(By.id(propertyValue));
				break;
			case "CSS":
				element = driver.findElements(By.cssSelector(propertyValue));
				break;
			case "CLASSNAME":
				element = driver.findElements(By.className(propertyValue));
				break;
			case "XPATH":
				element = driver.findElements(By.xpath(propertyValue));
				break;
			case "LINKTEXT":
	
				break;
			}
		} catch (Exception e) {
			funLog("Issue on getting element : " + propertyValue
					+ " Exception : " + e.getMessage());
		}

		return element;
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funLog() Description : This function write logs in log
	 * file which is placed in logs folder Author : Suresh Kumar,Mylam Date : 4
	 * May 2017 Parameter : strMessage : strMessage
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funLog(String strMessage) {
		// Getting function name calling from
		String strMethodCalling = Thread.currentThread().getStackTrace()[2]
				.getMethodName();
		if (strMessage.toUpperCase().contains("EXCEPTION")) {
			if (strMessage.substring(strMessage.indexOf("Exception"),
					strMessage.length()).contains("Exception")) {
				strMessage = strMessage + "NULL";
			}
		}

		if (strMessage.toUpperCase().contains("ISSUE")) {
			Stage.getInstance().getLog()
					.error(strMethodCalling + " : " + strMessage);
		} else {
			Stage.getInstance().getLog()
					.info(strMethodCalling + " : " + strMessage);
		}

		System.out.println(strMethodCalling + " : " + strMessage);
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funSendEmail() Description : This function will send
	 * email Author : Suresh Kumar,Mylam Date : 3 May 2017 Parameter : strStatus
	 * : Passed/Failed, strApplicationName : Application Name, strContent : Body
	 * of the email
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	// public void funSendEmail(String strStatus, String strApplicationName,
	// String strContent) {
	// String to = Initializer.getInstance().GetValue("email.to");
	// String cc = Initializer.getInstance().GetValue("email.cc");
	// String from = Initializer.getInstance().GetValue("email.from");
	//
	// Properties prop = System.getProperties();
	// prop.setProperty("mail.smtp.host",
	// Initializer.getInstance().GetValue("email.hostname"));
	// prop.setProperty("mail.smtp.port",
	// Initializer.getInstance().GetValue("email.port"));
	//
	// Session sess = Session.getDefaultInstance(prop);
	//
	// try {
	// String strFontColor;
	// if (strStatus.toUpperCase().contains("PASS")) {
	// strFontColor = "Green";
	// } else {
	// strFontColor = "Red";
	// }
	// MimeMessage message = new MimeMessage(sess);
	// message.setFrom(new InternetAddress(from));
	// message.addRecipients(Message.RecipientType.TO, to);
	// message.addRecipients(Message.RecipientType.CC, cc);
	// message.setSubject("JUDI - " + strApplicationName);
	// message.setContent("HTML Construction", "text/html"); // HTML
	// // structure
	// // need to
	// // construct
	// System.out.println(message);
	// Transport.send(message);
	// funLog(strApplicationName + " : " + strStatus + ". " + strContent
	// + ". Email send successfully");
	// } catch (Exception e) {
	// funLog("Issue on sending email. Exception : " + e.getMessage());
	// }
	//
	// }

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funTakeScreenshot() Description : This function will take
	 * screen shot on error Author : Suresh Kumar,Mylam Date : 5 May 2017
	 * Parameter : strImgName : file name
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public String funTakeScreenshot(String strImgName) {
		String strFileName = null;

		try {
			Robot robot = new Robot();

			SimpleDateFormat formatter = new SimpleDateFormat(
					"MMddyyyy hh mm ss");
			Calendar now = Calendar.getInstance();

			BufferedImage image = robot.createScreenCapture(new Rectangle(
					Toolkit.getDefaultToolkit().getScreenSize()));
			strFileName = strImgName + formatter.format(now.getTime()) + ".jpg";
			strFileName = strFileName.replace(" ", "");
			ImageIO.write(image, "jpg", new File(Initializer.getInstance()
					.GetValue("java.error.path") + strFileName));
			strFileName = new File(Initializer.getInstance().GetValue(
					"java.error.path")
					+ strFileName).getAbsolutePath();

		} catch (Exception e) {
			funLog("Issue on taking snapshot. Exception : " + e.getMessage());
		}
		return strFileName;
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funStartTestCase() Description : This is to print log for
	 * the Starting of the test case Author : Suresh Kumar,Mylam Date : 8 May
	 * 2017 Parameter : sTestCaseName : Test Case name
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funStartTestCase(String sTestCaseName) {

		funLog("****************************************************************************************");
		funLog("------------------  " + sTestCaseName
				+ " Execution is STARTED   ------------------------");
		funLog("****************************************************************************************");

	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funEndTestCase() Description : This is to print log for
	 * the ending of the test case Author : Suresh Kumar,Mylam Date : 8 May 2017
	 * Parameter : sTestCaseName : Test Case name
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funEndTestCase(String sTestCaseName) {

		funLog("------------------  " + sTestCaseName
				+ " Execution is END   ---------------------------");

	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funWaitAndAction() Description : This function will wait
	 * for few seconds and perform action once the object is loaded. It works
	 * only for Auto IT. Author : Suresh Kumar,Mylam Date : 12 May 2017
	 * Parameter : strInput : Give input text for SetText, Give text to identify
	 * object for Click
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funWaitAndAction(String objTitle, String objID,
			String strAction, String strInput) {
		boolean blnObject = false;
		// Activate the window based on title
		if (objTitle.trim().length() != 0) {
			objAutoIT.winActivate(objTitle);
			blnObject = true;
		}
		try {
			// Action
			if (blnObject == true) {
				if (strAction.trim().toUpperCase().contains("CLICK")) {
					objAutoIT.controlClick(objTitle, strInput.trim(),
							objID.trim());
				} else if (strAction.trim().toUpperCase().contains("SETTEXT")) {
					objAutoIT.ControlSetText(objTitle.trim(), "", objID.trim(),
							strInput);
				}
			}
			funWait(2);

		} catch (Exception e) {
			CommonFunctions.getInstance().funLog(
					"Issue on identifying object :" + objID + "Exception : "
							+ e.getMessage());
		}

	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funUpdateResultsToTestRail() Description : This function
	 * will update the Result statuus to TestRail Author : Suresh Kumar,Mylam
	 * Date : 17 May 2017 Parameter : strResultStatus : Provide the result
	 * status
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */

	public void funUpdateResultsToTestRail(String strResultStatus) {
		JSONObject r;
		List testCases = new ArrayList();
		Map testCasesResults = new HashMap();
		Map data = new HashMap();

		// strResultStatus = "PASS"; //Need to pass from test case

		APIClient client = new APIClient(Initializer.getInstance().GetValue(
				"app.test.testRailURL"));
		client.setUser(Initializer.getInstance().GetValue(
				"app.test.testRailUsername"));
		client.setPassword(Initializer.getInstance().GetValue(
				"app.test.testRailUserPassword"));

		testCases.add(Stage.getInstance().getCaseID());
		switch (strResultStatus) {
		case "PASS":
			testCasesResults.put(Stage.getInstance().getTestID(), 1);
			break;
		case "FAIL":
			testCasesResults.put(Stage.getInstance().getTestID(), 5);
			break;
		default:
			testCasesResults.put(Stage.getInstance().getTestID(), 4);
			break;
		}

		for (int i = 0; i < testCasesResults.size(); i++) {
			// iTestID = (int) testCases.get(i);
			data.put("status_id",
					testCasesResults.get(Stage.getInstance().getTestID()));
			try {
				r = (JSONObject) client.sendPost("add_result_for_case/"
						+ Stage.getInstance().getRunID() + "/"
						+ Stage.getInstance().getCaseID(), data);
			} catch (MalformedURLException e) {
				CommonFunctions.getInstance().funLog(
						"Issue on forming the API. Exception : "
								+ e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				CommonFunctions.getInstance().funLog(
						"Issue with Test data used in API. Exception : "
								+ e.getMessage());
				e.printStackTrace();
			} catch (APIException e) {
				CommonFunctions.getInstance().funLog(
						"Issue on sending API. Exception : " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funFinalizeResults() Description : This function finalize
	 * the results on failure step and terminate the execution Author : Suresh
	 * Kumar,Mylam Date : 19 May 2017 Parameter : none
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funFinalizeResults() {
		boolean bExpected = true;
		Assert.assertEquals(Stage.getInstance().getStatus(), bExpected);
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funStepValidate() Description : This function will
	 * compare the actual with expected values and returns result Author :
	 * Suresh Kumar,Mylam Date : 22 May 2017 Parameter : strType :
	 * TEXT/ELEMENT,bTakeScreenShot : true/false, exitHandler : true/false
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funStepValidate(String strType, String strActual,
			String strExpected, String strDescription, boolean bTakeScreenShot,
			boolean exitHandler) {
		try {
			switch (strType.toUpperCase().trim()) {
			case "TEXT":
				if (strActual.toLowerCase().trim()
						.contains(strExpected.toLowerCase().trim())) {
					if (bTakeScreenShot) {
						test.log(LogStatus.PASS, strDescription
								+ " : Actual : " + strActual
								+ " and Expected is : " + strExpected, test
								.addScreenCapture(CommonFunctions.getInstance()
										.funTakeScreenshot(
												Thread.currentThread()
														.getStackTrace()[1]
														.getMethodName())));
					} else {
						test.log(LogStatus.PASS, strDescription
								+ " : Actual : " + strActual
								+ " and Expected is : " + strExpected, "");
					}
					funLog(strDescription);
				} else {
					Stage.getInstance().setStatus(false);
					if (bTakeScreenShot) {
						test.log(LogStatus.FAIL, strDescription
								+ " : Actual : " + strActual
								+ " and Expected is : " + strExpected, test
								.addScreenCapture(CommonFunctions.getInstance()
										.funTakeScreenshot(
												Thread.currentThread()
														.getStackTrace()[1]
														.getMethodName())));
					} else {
						test.log(LogStatus.FAIL, strDescription
								+ " : Actual : " + strActual
								+ " and Expected is : " + strExpected, "");
					}
					funLog(strDescription);
					if (exitHandler) {
						funFinalizeResults();
					}
				}
				break;
			default:
				funLog("Exception Occured in validating : " + strDescription);
				break;

			}
		} catch (Exception e) {
			funLog("Issue on validating Text phrase : " + strDescription
					+ " : Actual : " + strActual + " and Expected is : "
					+ strExpected + " Exception is : " + e.getMessage());
		}

	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funElementValidate() Description : This function will
	 * compare the actual with expected values and returns result Author :
	 * Suresh Kumar,Mylam Date : 22 May 2017 Parameter : strType :
	 * TEXT/ELEMENT,bTakeScreenShot : true/false, exitHandler : true/false
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funElementValidate(WebElement oElement, String strValidation,
			String strElementDescription, boolean bTakeScreenShot,
			boolean exitHandler) {
		try {
			switch (strValidation.toUpperCase().trim()) {
			case "ISPRESENT":
				if (oElement != null) {
					if (bTakeScreenShot) {
						test.log(LogStatus.PASS, strElementDescription
								+ " : is present ", test
								.addScreenCapture(CommonFunctions.getInstance()
										.funTakeScreenshot(
												Thread.currentThread()
														.getStackTrace()[1]
														.getMethodName())));
					} else {
						test.log(LogStatus.PASS, strElementDescription
								+ " : is present ", "");
					}
					funLog(strElementDescription + " is present ");
				} else {
					test.log(LogStatus.FAIL, strElementDescription
							+ " : is NOT present ", test
							.addScreenCapture(CommonFunctions.getInstance()
									.funTakeScreenshot(
											Thread.currentThread()
													.getStackTrace()[1]
													.getMethodName())));
				}
				break;
			case "ISVISIBLE":
				if (oElement.isDisplayed()) {
					if (bTakeScreenShot) {
						test.log(LogStatus.PASS, strElementDescription
								+ " : is Displayed ", test
								.addScreenCapture(CommonFunctions.getInstance()
										.funTakeScreenshot(
												Thread.currentThread()
														.getStackTrace()[1]
														.getMethodName())));
					} else {
						test.log(LogStatus.PASS, strElementDescription
								+ " : is Displayed ", "");
					}
					funLog(strElementDescription + " is present ");
				} else {
					test.log(LogStatus.FAIL, strElementDescription
							+ " : is NOT Displayed ", test
							.addScreenCapture(CommonFunctions.getInstance()
									.funTakeScreenshot(
											Thread.currentThread()
													.getStackTrace()[1]
													.getMethodName())));
				}
				break;
			case "ISENABLE":
				if (oElement.isEnabled()) {
					if (bTakeScreenShot) {
						test.log(LogStatus.PASS, strElementDescription
								+ " : is Enabled ", test
								.addScreenCapture(CommonFunctions.getInstance()
										.funTakeScreenshot(
												Thread.currentThread()
														.getStackTrace()[1]
														.getMethodName())));
					} else {
						test.log(LogStatus.PASS, strElementDescription
								+ " : is Enabled ", "");
					}
					funLog(strElementDescription + " is present ");
				} else {
					test.log(LogStatus.FAIL, strElementDescription
							+ " : is NOT Enabled ", test
							.addScreenCapture(CommonFunctions.getInstance()
									.funTakeScreenshot(
											Thread.currentThread()
													.getStackTrace()[1]
													.getMethodName())));
				}
				break;
			default:
				funLog("No case statement defined for the given validation : "
						+ strValidation);
				break;
			}
		} catch (Exception e) {
			funLog("Issue on validating element : " + strElementDescription
					+ ", Exception : " + e.getMessage());
		}

	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funElementsValidate() Description : This function will
	 * compare the actual with expected values and returns result Author :
	 * Suresh Kumar,Mylam Date : 23 May 2017 Parameter : strType :
	 * TEXT/ELEMENT,bTakeScreenShot : true/false, exitHandler : true/false
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funElementsValidate(List<WebElement> oElement,
			String strValidation, String strElementDescription,
			boolean bTakeScreenShot, boolean exitHandler) {
		try {
			switch (strValidation.toUpperCase().trim()) {
			case "ISPRESENT":
				if (oElement.size() != 0) {
					if (bTakeScreenShot) {
						test.log(LogStatus.PASS, strElementDescription
								+ " : WebElements list is present ", test
								.addScreenCapture(CommonFunctions.getInstance()
										.funTakeScreenshot(
												Thread.currentThread()
														.getStackTrace()[1]
														.getMethodName())));
					} else {
						test.log(LogStatus.PASS, strElementDescription
								+ " : WebElements list is present ", "");
					}
					funLog(strElementDescription
							+ " WebElements list is present ");
				} else {
					test.log(LogStatus.FAIL, strElementDescription
							+ " : WebElements list is NOT present ", test
							.addScreenCapture(CommonFunctions.getInstance()
									.funTakeScreenshot(
											Thread.currentThread()
													.getStackTrace()[1]
													.getMethodName())));
				}
				break;
			default:
				funLog("No case statement defined for the given validation : "
						+ strValidation);
				break;
			}
		} catch (Exception e) {
			funLog("Issue on validating element : " + strElementDescription
					+ ", Exception : " + e.getMessage());
		}
	}
	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funElementsValidate() Description : This function will
	 * compare the actual with expected values and returns result Author :
	 * Suresh Kumar,Mylam Date : 23 May 2017 Parameter : strType :
	 * TEXT/ELEMENT,bTakeScreenShot : true/false, exitHandler : true/false
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funSelectValidate(List<WebElement> list,
			String strValue, String strElementDescription,
			boolean bTakeScreenShot, boolean exitHandler) {
		boolean found= false;
		try {
			Select oE = new Select((WebElement) list);
			List<WebElement> allOptions = oE.getOptions();
		    for (WebElement element : allOptions) {
		        if(element.getText().toLowerCase().trim().contains(strValue));
		        element.click();
		        found = true;
		    }
			
			if(found) {
		        funLog(strElementDescription + " is present ");
				test.log(LogStatus.PASS, strValue
						+ " is NOT present in the drop down , field : "+ strElementDescription, test
						.addScreenCapture(CommonFunctions.getInstance()
								.funTakeScreenshot(
										Thread.currentThread()
												.getStackTrace()[1]
												.getMethodName())));
			}			

		} catch (Exception e) {
			funLog("Issue on Identifying drop down : " + strElementDescription
					+ ", Exception : " + e.getMessage());
		}
	}
}
