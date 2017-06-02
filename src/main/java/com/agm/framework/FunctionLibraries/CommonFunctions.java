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
import com.jacob.com.LibraryLoader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CommonFunctions {

	// Creating singleton
	private static CommonFunctions objCmf = null;

	public WebDriver driver = null;
	public String strLogFileName;
	public boolean iStatus = true;
	private AutoItX objAutoIT = null;
	public ExtentReports extent;
	public ExtentTest test;
	public String strTestCaseName;

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

	TestScripts testScripts = TestScripts.getInstance();
	TestData testData = TestData.getInstance();
	ApplicationFunctions applicationFunctions = ApplicationFunctions
			.getInstance();
	DB db = DB.getInstance();
	Initializer initializer = Initializer.getInstance();
	Stage stage = Stage.getInstance();
	
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

		String propertyValue = initializer.GetValue(propertyName)
				.trim();
		String propertyType = initializer.GetType(propertyName)
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

		String propertyValue = initializer.GetValue(propertyName)
				.trim();
		String propertyType = initializer.GetType(propertyName)
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
			stage.getLog()
					.error(strMethodCalling + " : " + strMessage);
		} else {
			stage.getLog()
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
	// String to = initializer.GetValue("email.to");
	// String cc = initializer.GetValue("email.cc");
	// String from = initializer.GetValue("email.from");
	//
	// Properties prop = System.getProperties();
	// prop.setProperty("mail.smtp.host",
	// initializer.GetValue("email.hostname"));
	// prop.setProperty("mail.smtp.port",
	// initializer.GetValue("email.port"));
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
			ImageIO.write(image, "jpg", new File(initializer
					.GetValue("java.error.path") + strFileName));
			strFileName = new File(initializer.GetValue(
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

		APIClient client = new APIClient(initializer.GetValue(
				"app.test.testRailURL"));
		client.setUser(initializer.GetValue(
				"app.test.testRailUsername"));
		client.setPassword(initializer.GetValue(
				"app.test.testRailUserPassword"));

		testCases.add(stage.getCaseID());
		switch (strResultStatus) {
		case "PASS":
			testCasesResults.put(stage.getTestID(), 1);
			break;
		case "FAIL":
			testCasesResults.put(stage.getTestID(), 5);
			break;
		default:
			testCasesResults.put(stage.getTestID(), 4);
			break;
		}

		for (int i = 0; i < testCasesResults.size(); i++) {
			// iTestID = (int) testCases.get(i);
			data.put("status_id",
					testCasesResults.get(stage.getTestID()));
			try {
				r = (JSONObject) client.sendPost("add_result_for_case/"
						+ stage.getRunID() + "/"
						+ stage.getCaseID(), data);
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
		Assert.assertEquals(stage.getStatus(), bExpected);
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
					stage.setStatus(false);
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
	public void funSelectValidate(List<WebElement> list, String strValue,
			String strElementDescription, boolean bTakeScreenShot,
			boolean exitHandler) {
		boolean found = false;
		try {
			Select oE = new Select((WebElement) list);
			List<WebElement> allOptions = oE.getOptions();
			for (WebElement element : allOptions) {
				if (element.getText().toLowerCase().trim().contains(strValue))
					;
				element.click();
				found = true;
			}

			if (found) {
				funLog(strElementDescription + " is present ");
				test.log(
						LogStatus.PASS,
						strValue
								+ " is NOT present in the drop down , field : "
								+ strElementDescription,
						test.addScreenCapture(CommonFunctions
								.getInstance()
								.funTakeScreenshot(
										Thread.currentThread().getStackTrace()[1]
												.getMethodName())));
			}

		} catch (Exception e) {
			funLog("Issue on Identifying drop down : " + strElementDescription
					+ ", Exception : " + e.getMessage());
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funBeforeTest() Description : This function will setup
	 * the required configurations and this needs to be executed before every
	 * test Author : Suresh Kumar,Mylam Date : 01 Jun 2017 Parameter : strType :
	 * TEXT/ELEMENT,bTakeScreenShot : true/false, exitHandler : true/false
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */

	public void funBeforeTest() {
		// ********************************* INITIAL SETUP
		// ************************************************
		stage.setStatus(iStatus);
		strTestCaseName = sun.reflect.Reflection.getCallerClass(2)
				.getSimpleName();
		strLogFileName = strTestCaseName
				+ "_"
				+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar
						.getInstance().getTime());
		// Setting the Log file path in system variables
		System.setProperty("logFileName", strLogFileName);
		funStartTestCase(strLogFileName);

		// ********************************* R-E-P-O-R-T-S
		// **********************************************
		// new instance for Extent Reports
		extent = new ExtentReports(initializer.GetValue(
				"java.results.path")
				+ strLogFileName + ".html", true);
		extent.loadConfig(new File(
				"src/main/resources/Config-ExtentReports.xml"));
		// starting test
		test = extent.startTest(strTestCaseName, strTestCaseName);
		// Set Category and author to report
		test.assignAuthor("Suresh Kumar Mylam");
		test.assignCategory("Regression");

		// ********************************* AUTOIT SETUP
		// ************************************************
		File file = new File(initializer.GetValue(
				"java.autoit.jacob"));
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
		objAutoIT = new AutoItX();
		// ********************************* TestRail details -Static Data
		// ***************************
		testData.funLoadTestData(strTestCaseName);

		// Initialize test object in ApplicationFunctions
		// ApplicationFunctions applicationFunctions = ApplicationFunctions
		// .getInstance();
		applicationFunctions.init(test);
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funAfterTest() Description : This function will close the
	 * all opened connections and terminate the script Author : Suresh
	 * Kumar,Mylam Date : 01 Jun 2017 Parameter : strType :
	 * TEXT/ELEMENT,bTakeScreenShot : true/false, exitHandler : true/false
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */

	public void funAfterTest(ITestResult result) {
		try {
			funQuitBrowser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			funLog("Issue in terminating the browser");
		}
		// ending test
		extent.endTest(test);
		// writing everything to document
		extent.flush();
		try {
			switch (result.getStatus()) {
			case ITestResult.SUCCESS:
				funUpdateResultsToTestRail("PASS");
				break;
			case ITestResult.FAILURE:
				funUpdateResultsToTestRail("FAIL");
				break;
			default:
				throw new RuntimeException("Invalid status");
			}
		} catch (Exception e) {
			funLog("Invalid Result status");
		}
		funEndTestCase(strTestCaseName);

	}
}
