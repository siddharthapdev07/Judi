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

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import autoitx4java.AutoItX;
import bsh.This;

import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;
import com.agm.testrail.APIClient;
import com.agm.testrail.APIException;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.jacob.com.LibraryLoader;

public class CommonFunctions {

	// Creating singleton
	private static CommonFunctions objCmf = null;

	public WebDriver driver = null;
	public String strLogFileName = null;
	public boolean iStatus = true;
	private AutoItX objAutoIT = null;
	public ExtentReports extent;
	public ExtentTest test;
	public String strTestCaseName = null;

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
//			strFileName = new File(Initializer.getInstance().GetValue(
//					"java.error.path")
//					+ strFileName).getAbsolutePath();
			strFileName = "../Images/"+strFileName;

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
		// JSONObject r;
		List<Integer> testCases = new ArrayList<Integer>();
		Map<Integer, Integer> testCasesResults = new HashMap<Integer, Integer>();
		Map<String, Integer> data = new HashMap<String, Integer>();

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
				// r = (JSONObject) client.sendPost("add_result_for_case/"
				// + Stage.getInstance().getRunID() + "/"
				// + Stage.getInstance().getCaseID(), data);
				client.sendPost("add_result_for_case/"
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
						test.log(Status.PASS, MarkupHelper.createLabel(
								strDescription + " : Actual : " + strActual
										+ " and Expected is : " + strExpected,
								ExtentColor.GREEN));
						try {
							test.addScreenCaptureFromPath(CommonFunctions
									.getInstance().funTakeScreenshot(
											Thread.currentThread()
													.getStackTrace()[1]
													.getMethodName()));
						} catch (IOException e) {
							CommonFunctions.getInstance().funLog(
									"Issue in recording snapshot error is : "
											+ e.getMessage());
						}

					} else {
						test.log(Status.PASS, MarkupHelper.createLabel(
								strDescription + " : Actual : " + strActual
										+ " and Expected is : " + strExpected,
								ExtentColor.GREEN));
					}
					funLog(strDescription + " : Actual : " + strActual
							+ " and Expected is : " + strExpected);
				} else {
					Stage.getInstance().setStatus(false);
					if (bTakeScreenShot) {
						test.log(Status.FAIL, MarkupHelper.createLabel(
								strDescription + " : Actual : " + strActual
										+ " and Expected is : " + strExpected,
								ExtentColor.RED));
						try {
							test.addScreenCaptureFromPath(CommonFunctions
									.getInstance().funTakeScreenshot(
											Thread.currentThread()
													.getStackTrace()[1]
													.getMethodName()));
						} catch (IOException e) {
							CommonFunctions.getInstance().funLog(
									"Issue in recording snapshot error is : "
											+ e.getMessage());
						}
					} else {
						test.log(Status.FAIL, MarkupHelper.createLabel(
								strDescription + " : Actual : " + strActual
										+ " and Expected is : " + strExpected,
								ExtentColor.RED));
					}
					funLog(strDescription);
					if (exitHandler) {
						funFinalizeResults();
					}
				}
				break;
			case "INTEGER":
				int strActualValue = Integer.parseInt(strActual);
				int strExpectedValue = Integer.parseInt(strExpected);
				if (strActualValue - strExpectedValue == 0) {
					if (bTakeScreenShot) {
						test.log(Status.PASS, MarkupHelper.createLabel(
								strDescription + " : Actual : " + strActual
										+ " and Expected is : " + strExpected,
								ExtentColor.GREEN));
						try {
							test.addScreenCaptureFromPath(CommonFunctions
									.getInstance().funTakeScreenshot(
											Thread.currentThread()
													.getStackTrace()[1]
													.getMethodName()));
						} catch (IOException e) {
							CommonFunctions.getInstance().funLog(
									"Issue in recording snapshot error is : "
											+ e.getMessage());
						}
					} else {
						test.log(Status.PASS, MarkupHelper.createLabel(
								strDescription + " : Actual : " + strActual
										+ " and Expected is : " + strExpected,
								ExtentColor.GREEN));
					}
					funLog(strDescription + " : Actual : " + strActual
							+ " and Expected is : " + strExpected);
				} else {
					Stage.getInstance().setStatus(false);
					if (bTakeScreenShot) {
						test.log(Status.FAIL, MarkupHelper.createLabel(
								strDescription + " : Actual : " + strActual
										+ " and Expected is : " + strExpected,
								ExtentColor.RED));
						try {
							test.addScreenCaptureFromPath(CommonFunctions
									.getInstance().funTakeScreenshot(
											Thread.currentThread()
													.getStackTrace()[1]
													.getMethodName()));
						} catch (IOException e) {
							CommonFunctions.getInstance().funLog(
									"Issue in recording snapshot error is : "
											+ e.getMessage());
						}
					} else {
						test.log(Status.FAIL, MarkupHelper.createLabel(
								strDescription + " : Actual : " + strActual
										+ " and Expected is : " + strExpected,
								ExtentColor.RED));
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
						test.log(Status.PASS, MarkupHelper.createLabel(
								strElementDescription, ExtentColor.GREEN));
						try {
							test.addScreenCaptureFromPath(CommonFunctions
									.getInstance().funTakeScreenshot(
											Thread.currentThread()
													.getStackTrace()[1]
													.getMethodName()));
						} catch (IOException e) {
							CommonFunctions.getInstance().funLog(
									"Issue in recording snapshot error is : "
											+ e.getMessage());
						}
					} else {
						test.log(Status.PASS, MarkupHelper.createLabel(
								strElementDescription, ExtentColor.GREEN));
					}
					funLog(strElementDescription + " is present ");
				} else {
					test.log(
							Status.FAIL,
							MarkupHelper.createLabel(strElementDescription
									+ " : is NOT present ", ExtentColor.RED));
					try {
						test.addScreenCaptureFromPath(CommonFunctions
								.getInstance()
								.funTakeScreenshot(
										Thread.currentThread().getStackTrace()[1]
												.getMethodName()));
					} catch (IOException e) {
						CommonFunctions.getInstance().funLog(
								"Issue in recording snapshot error is : "
										+ e.getMessage());
					}
				}
				break;
			// case "ISVISIBLE":
			// if (oElement.isDisplayed()) {
			// if (bTakeScreenShot) {
			// test.log(LogStatus.PASS, strElementDescription
			// + " : is Displayed ", test
			// .addScreenCapture(CommonFunctions.getInstance()
			// .funTakeScreenshot(
			// Thread.currentThread()
			// .getStackTrace()[1]
			// .getMethodName())));
			// } else {
			// test.log(LogStatus.PASS, strElementDescription
			// + " : is Displayed ", "");
			// }
			// funLog(strElementDescription + " is present ");
			// } else {
			// test.log(LogStatus.FAIL, strElementDescription
			// + " : is NOT Displayed ", test
			// .addScreenCapture(CommonFunctions.getInstance()
			// .funTakeScreenshot(
			// Thread.currentThread()
			// .getStackTrace()[1]
			// .getMethodName())));
			// }
			// break;
			// case "ISENABLE":
			// if (oElement.isEnabled()) {
			// if (bTakeScreenShot) {
			// test.log(LogStatus.PASS, strElementDescription
			// + " : is Enabled ", test
			// .addScreenCapture(CommonFunctions.getInstance()
			// .funTakeScreenshot(
			// Thread.currentThread()
			// .getStackTrace()[1]
			// .getMethodName())));
			// } else {
			// test.log(LogStatus.PASS, strElementDescription
			// + " : is Enabled ", "");
			// }
			// funLog(strElementDescription + " is present ");
			// } else {
			// test.log(LogStatus.FAIL, strElementDescription
			// + " : is NOT Enabled ", test
			// .addScreenCapture(CommonFunctions.getInstance()
			// .funTakeScreenshot(
			// Thread.currentThread()
			// .getStackTrace()[1]
			// .getMethodName())));
			// }
			// break;
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
	// public void funElementsValidate(List<WebElement> oElement,
	// String strValidation, String strElementDescription,
	// boolean bTakeScreenShot, boolean exitHandler) {
	// try {
	// switch (strValidation.toUpperCase().trim()) {
	// case "ISPRESENT":
	// if (oElement.size() != 0) {
	// if (bTakeScreenShot) {
	// test.log(LogStatus.PASS, strElementDescription
	// + " : WebElements list is present ", test
	// .addScreenCapture(CommonFunctions.getInstance()
	// .funTakeScreenshot(
	// Thread.currentThread()
	// .getStackTrace()[1]
	// .getMethodName())));
	// } else {
	// test.log(LogStatus.PASS, strElementDescription
	// + " : WebElements list is present ", "");
	// }
	// funLog(strElementDescription
	// + " WebElements list is present ");
	// } else {
	// test.log(LogStatus.FAIL, strElementDescription
	// + " : WebElements list is NOT present ", test
	// .addScreenCapture(CommonFunctions.getInstance()
	// .funTakeScreenshot(
	// Thread.currentThread()
	// .getStackTrace()[1]
	// .getMethodName())));
	// }
	// break;
	// default:
	// funLog("No case statement defined for the given validation : "
	// + strValidation);
	// break;
	// }
	// } catch (Exception e) {
	// funLog("Issue on validating element : " + strElementDescription
	// + ", Exception : " + e.getMessage());
	// }
	// }

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
				ApplicationFunctions.getInstance().funSuccessCall(
						strElementDescription + " is present ");
			}

		} catch (Exception e) {
			ApplicationFunctions.getInstance()
					.funFailureCall(
							"Issue on Identifying drop down : "
									+ strElementDescription, e);

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
		Stage.getInstance().setStatus(iStatus);
		// strTestCaseName = sun.reflect.Reflection.getCallerClass(2)
		// .getSimpleName();

		strTestCaseName = Thread.currentThread().getStackTrace()[2]
				.getMethodName();
		Stage.getInstance().setTestName(strTestCaseName);
		CommonFunctions.getInstance().funStartTestCase(strTestCaseName);

		// ********************************* AUTOIT SETUP
		// ************************************************
		File file = new File(Initializer.getInstance().GetValue(
				"java.autoit.jacob"));
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
		objAutoIT = new AutoItX();
	}

	// public void funBeforeSuite(){
	// // ********************************* R-E-P-O-R-T-S
	// // **********************************************
	// // new instance for Extent Reports
	// extent = new ExtentReports(Initializer.getInstance().GetValue(
	// "java.results.path")
	// + strLogFileName + ".html", true);
	// extent.loadConfig(new File(
	// "src/main/resources/Config-ExtentReports.xml"));
	// // Set Category and author to report
	// test.assignAuthor("Suresh Kumar Mylam");
	// test.assignCategory("Regression");
	//
	// // Initialize test object in other Libraries
	// ApplicationFunctions.getInstance().init(test);
	// DB.getInstance().init(test);
	// TestScripts.getInstance().init(test);
	// }

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
			ApplicationFunctions.getInstance().funQuitBrowser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			funLog("Issue in terminating the browser");
		}
		funEndTestCase(strTestCaseName);
	}

	// public void funAfterSuite(){
	// // ending test
	// extent.endTest(test);
	// // writing everything to document
	// extent.flush();
	// }

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funGetTestData() Date : June 16, 2017 Author : Suresh
	 * Kumar,Mylam Description : This function will retrive data from
	 * TestData.xml Parameters : strInput : Key name
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public String funGetTestData(String TestName, String strParam) {
		String testDataParam = null;
		DocumentBuilder dBuilder = null;
		Document doc = null;
		Node node = null;
		File fXmlFile = new File(System.getProperty("user.dir")
				+ Initializer.getInstance().GetValue("file.testDataFilePath"));
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			doc = dBuilder.parse(fXmlFile);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		XPath xPath = XPathFactory.newInstance().newXPath();
		String expression = String.format("/TestCases/Test[@Name='" + TestName
				+ "']/" + strParam);
		try {
			node = (Node) xPath.compile(expression).evaluate(doc,
					XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if (node != null) {
			testDataParam = node.getTextContent();
		} else {
			ApplicationFunctions.getInstance().funInfoCall(
					"cannot read the test Data xml file");
			test.log(Status.FAIL, MarkupHelper.createLabel(
					"cannot read the test Data xml file", ExtentColor.RED));
			Stage.getInstance().setStatus(false);
			funFinalizeResults();
		}
		return testDataParam;
	}

}
