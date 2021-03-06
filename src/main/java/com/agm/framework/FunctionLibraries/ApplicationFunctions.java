package com.agm.framework.FunctionLibraries;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
//import com.relevantcodes.extentreports.ExtentTest;
//import com.relevantcodes.extentreports.LogStatus;

public class ApplicationFunctions {
	// Creating singleton
	private static ApplicationFunctions objApf = null;

	public WebDriver driver = null;
	public ExtentTest test = null;
	WebElement element;
	public String strQuery = null;
	public String strField = null;
	// public String[] str= null;
	public String strActual = null;
	public String strExpected = null;
	public String strObjPrefix = null;

	private ApplicationFunctions() {
		if(Initializer.getInstance().GetValue("app.env").equalsIgnoreCase("test05")){
			strObjPrefix = "judi.test05.";
		}else {
			strObjPrefix = "judi.test1g.";
		}
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
	 * Function Name : funLaunchURL() Description : This function will launch
	 * URL Author : Suresh Kumar,Mylam Date : 03 May 2017 Parameter : strBrowser
	 * = iexplore.exe/chrome, strURL = URL
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funLaunchURL(String strURL) {
		String strBrowser = Initializer.getInstance().GetValue("gui.browser");

		try {
			// Killing opened browser by process
			CommonFunctions.getInstance().funKillbyProcess(strBrowser);
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
			CommonFunctions.getInstance().funStepValidate("TEXT",
					driver.getTitle().toString(), "AG Mednet Portal",
					"validate the Browser Title", true, false);
		} catch (Exception e) {
			CommonFunctions.getInstance().funLog(
					"Issue on launching URL. Exception : " + e.getMessage());
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
	 * Function Name : funLoginApplication() Description : This function Login
	 * to the application Author : Suresh Kumar,Mylam Date : 23 May 2017
	 * Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funLoginApplication() {
		// Launch URL
		funLaunchURL(Initializer.getInstance().GetValue("app.test"));
		try {
			CommonFunctions
					.getInstance()
					.getElement(driver, strObjPrefix+"login.username")
					.sendKeys(
							Initializer.getInstance().GetValue(
									"app.test.username"));
		} catch (Exception e) {
			funFailureCall("Issue identifying the object - UserName", e);
			// CommonFunctions.getInstance().funLog(
			// "Issue identifying the object - UserName" + e.getMessage());
			// test.log(LogStatus.FAIL,
			// "Exception in identifying the UserName field", "");
		}
		try {
			CommonFunctions
					.getInstance()
					.getElement(driver, strObjPrefix+"login.password")
					.sendKeys(
							Initializer.getInstance().GetValue(
									"app.test.password"));
		} catch (Exception e) {
			funFailureCall("Issue identifying the object - Password", e);
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"login.login").click();
			funSuccessCall("Login Button is clicked successfully");
			CommonFunctions.getInstance().funWait(8);
		} catch (Exception e) {
			funFailureCall("Issue identifying the object - Login Button", e);
			// CommonFunctions.getInstance().funLog(
			// "Issue identifying the object - Login Button"
			// + e.getMessage());
			// test.log(LogStatus.FAIL,
			// "Exception in clicking the Login button",
			// test.addScreenCapture(CommonFunctions.getInstance()
			// .funTakeScreenshot(
			// Thread.currentThread().getStackTrace()[1]
			// .getMethodName())));
		}
		CommonFunctions.getInstance().funElementValidate(
				CommonFunctions.getInstance().getElement(driver,
						strObjPrefix+"home.logout"), "ISPRESENT",
				"Validating LogIn status : ", true, true);
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
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"home.logout").click();
		} catch (Exception e) {
			funFailureCall("Issue identifying the object - LogOut", e);
		}
		CommonFunctions.getInstance().funWait(3);
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funNavigate_TrialAdmin() Description : This function
	 * Navigate to Trail Administration Author : Suresh Kumar,Mylam Date : 23
	 * May 2017 Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funSelectTrial(String strTrial) {
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"home.trialAdmin").click();
			CommonFunctions.getInstance().funWait(5);
			funSuccessCall("Trial Administration is clicked successfully");
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Trial Administration ", e);
		}
		CommonFunctions.getInstance().funWait(2);
		try {
			Select oE = new Select(CommonFunctions.getInstance().getElement(
					driver, strObjPrefix+"trialAdmin.Trail"));
			List<WebElement> allOpt = oE.getOptions();
			for (int i = 0; i < allOpt.size(); i++) {
				String optionName = allOpt.get(i).getText();
				if (optionName.equalsIgnoreCase(strTrial)) {
					allOpt.get(i).click();
					break;
				}
			}
			CommonFunctions.getInstance().funWait(1);
			oE = new Select(CommonFunctions.getInstance().getElement(driver,
					strObjPrefix+"trialAdmin.Trail"));
			WebElement oSelected = oE.getFirstSelectedOption();
			String strSelected = oSelected.getText();
			CommonFunctions.getInstance().funStepValidate("TEXT",
					strSelected.trim().toLowerCase().trim(),
					strTrial.toLowerCase().trim(),
					"Trial selection in the Trial drop down", true, true);
		} catch (Exception e) {
			funFailureCall("Issue on Identifying drop down : " + strTrial
					+ ", Exception : ", e);
			CommonFunctions.getInstance().funFinalizeResults();
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funTrialAdmin_Trials() Description : This function will
	 * validate Trial tab Author : Suresh Kumar,Mylam Date :06 Jun 2017
	 * Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funTrialAdmin_Trials(String strTrial) {
		CommonFunctions.getInstance().funWait(1);
		// Trial NAme Validation
		String TrialName = CommonFunctions.getInstance()
				.getElement(driver, strObjPrefix+"trialAdmin.Trials.TrialName")
				.getText();
		CommonFunctions.getInstance().funStepValidate("TEXT", TrialName,
				strTrial, "Trial Name validation", false, false);
		// Description Validation
		String strDescription = CommonFunctions.getInstance()
				.getElement(driver, strObjPrefix+"trialAdmin.Trials.Description")
				.getAttribute("value");
		DB.getInstance().funConnectDB(
				Initializer.getInstance().GetValue("db.env"),
				Initializer.getInstance().GetValue("db.dbname.portal"));
		strQuery = "select description from trial where name = '" + strTrial
				+ "'";
		String strTrialDesc_DB = DB.getInstance().funExecuteQuery(strQuery,
				"description");
		CommonFunctions.getInstance().funStepValidate("TEXT",
				strDescription.toLowerCase().trim(),
				strTrialDesc_DB.toLowerCase().trim(),
				"Trial Description validation", false, false);
		if (strDescription.contains("_End")) {
			strDescription = strDescription.replace("_End", "");
		} else {
			strDescription = strDescription + "_End";
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.Trials.Description")
					.clear();
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.Trials.Description")
					.sendKeys(strDescription);
			// Join Code validation
			String strJoinCode = CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.Trials.joinCode")
					.getText();
			strJoinCode = strJoinCode.split(":")[1].trim();
			strQuery = "select joincode from trial where name = '" + strTrial
					+ "'";
			String strJoinCode_DB = DB.getInstance().funExecuteQuery(strQuery,
					"joincode");
			CommonFunctions.getInstance().funStepValidate("TEXT",
					strJoinCode.toLowerCase().trim(),
					strJoinCode_DB.toLowerCase().trim(),
					"Trial join code validation", false, false);
			// Number of Sites Validation
			String iSites = CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.Trials.sites")
					.getText();
			iSites = iSites.split(":")[1].trim();
			strQuery = "SELECT count(*) as COUNT from trialsite WHERE trial_id IN (SELECT id FROM trial where name = '"
					+ strTrial + "') group by trial_id";
			CommonFunctions.getInstance().funLog(
					"SQL Query used to fetch sites is : " + strQuery);
			String iSites_DB = DB.getInstance().funExecuteQuery(strQuery,
					"count");
			CommonFunctions.getInstance().funStepValidate("INTEGER", iSites,
					iSites_DB, "Validating sites count from GUI to DB", false,
					false);
			CommonFunctions.getInstance().funWait(1);
			// No of subjects Validation
			String iSubjects = CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.Trials.subjects")
					.getText();
			iSubjects = iSubjects.split(":")[1].trim();
			strQuery = "SELECT count(*) as COUNT from trialsubject WHERE trial_id IN (SELECT id FROM trial where name = '"
					+ strTrial + "') group by trial_id";
			CommonFunctions.getInstance().funLog(
					"SQL Query used to fetch subjects is : " + strQuery);
			String iSubjects_DB = DB.getInstance().funExecuteQuery(strQuery,
					"count");
			CommonFunctions.getInstance().funStepValidate("INTEGER", iSubjects,
					iSubjects_DB, "Validating Subjects count from GUI to DB",
					false, false);
			// Save changes button
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.Trials.saveChanges")
					.click();
			CommonFunctions.getInstance().funWait(4);
			strActual = CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"result").getText();
			strExpected = "The trial " + strTrial + " has been updated.";
			CommonFunctions
					.getInstance()
					.funStepValidate(
							"TEXT",
							strActual,
							strExpected,
							"Validating the trial description Update message after update",
							true, false);

			// Verify the description after update
			strQuery = "select description from trial where name = '"
					+ strTrial + "'";
			strTrialDesc_DB = DB.getInstance().funExecuteQuery(strQuery,
					"description");
			CommonFunctions.getInstance().funStepValidate("TEXT",
					strDescription.toLowerCase().trim(),
					strTrialDesc_DB.toLowerCase().trim(),
					"Trial Description validation", false, false);
		} catch (Exception e) {
			funFailureCall(
					"Issue in validating the Trial details under Trial tab", e);
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funDeleteSiteIfExist() Description : This function will
	 * delete the site if exist Author : Suresh Kumar,Mylam Date :08 Jun 2017
	 * Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funDeleteSiteIfExist(String strSiteID) {
		boolean temp = false;
		String strSiteStatus = DB.getInstance().funCheckSiteID(strSiteID);

		if (strSiteStatus != null) {
			funSuccessCall("Checking for any subjects associated to this site.... ");
			// Delete Subject if it exist on Site
			funDeleteSubjectOnSite(strSiteID);
			// check for the site existence and delete if exist , so that we can
			// re-create same data
			// Verify the sites added successfully
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.sites").click();
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.sites.viewSites")
					.click();
			CommonFunctions.getInstance().funWait(2);
			WebElement oViewSitesTable = CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.sites.viewSitesTable");
			List<WebElement> allRows = oViewSitesTable.findElements(By
					.tagName("tr"));
			// Below loop finds the record and delete
			for (WebElement row : allRows) {
				List<WebElement> cells = row.findElements(By.tagName("td"));
				int iCol = cells.size();
				String strSiteID_GUI = cells.get(0).getText().trim();
				if (strSiteID_GUI.equalsIgnoreCase(strSiteID.trim())) {
					funSuccessCall("This site is already present and deleting.... ");
					temp = true;
					WebElement cell = cells.get(iCol - 1);
					try {
						cell.findElement(
								By.cssSelector("a[ng-click='edit(site)']"))
								.click();
						CommonFunctions.getInstance().funWait(2);
					} catch (Exception e) {
						funFailureCall(
								"Issue on Identifying Edit objects in view Sites tab : ",
								e);
						CommonFunctions.getInstance().funFinalizeResults();
					}
					try {
						CommonFunctions
								.getInstance()
								.getElement(driver,
										strObjPrefix+"trialAdmin.sites.delete").click();
						CommonFunctions.getInstance().funWait(1);
						CommonFunctions
								.getInstance()
								.getElement(driver,
										strObjPrefix+"trialAdmin.sites.deleteSite")
								.click();
						CommonFunctions.getInstance().funWait(1);
						break;
					} catch (Exception e) {
						funFailureCall("Issue on Deleting the site ", e);
						CommonFunctions.getInstance().funFinalizeResults();
					}
				}

			}
			if (!temp) {
				funSuccessCall("No Record found for this site id : "
						+ strSiteID + " and it is free to create");
			}
			if (temp) {
				CommonFunctions.getInstance().funWait(3);
				// Below loop verifies the deleted record
				allRows = oViewSitesTable.findElements(By.tagName("tr"));
				boolean flag = true;
				for (WebElement row : allRows) {
					List<WebElement> cells = row.findElements(By.tagName("td"));
					String strSiteID_GUI = cells.get(0).getText().trim();

					// CommonFunctions.getInstance().funStepValidate("TEXT",
					// strSiteID_GUI, strSiteID.trim(),
					// "Deleted record still exist, Deleted id is : "
					// + strSiteID_GUI, true, true);

					if (strSiteID_GUI.equalsIgnoreCase(strSiteID.trim())) {

						CommonFunctions.getInstance().funLog(
								"Deleted record still exist, Deleted id is : "
										+ strSiteID_GUI);
						test.log(Status.FAIL, MarkupHelper.createLabel(
								"Deleted record still exist, Deleted id is : "
										+ strSiteID_GUI, ExtentColor.RED));
						try {
							test.addScreenCaptureFromPath(CommonFunctions
									.getInstance().funTakeScreenshot(
											Thread.currentThread()
													.getStackTrace()[1]
													.getMethodName()));
						} catch (IOException e1) {
							CommonFunctions.getInstance().funLog(
									"Issue in recording snapshot error is : "
											+ e1.getMessage());
						}
						Stage.getInstance().setStatus(false);
						flag = false;
						break;
					}
				}
				if (flag) {
					funSuccessCall("Record deleted Successfully : " + strSiteID);
				}
			}
		} else {
			funInfoCall("No Site exist with name : " + strSiteID);
		}
	}

	public void funDeleteSubjectOnSite(String strSiteID) {
		strQuery = "select clinicaltrialsubjectid from trialsubject where id in "
				+ "(select subject_id from subjectsitexref where site_id = "
				+ "(select id from trialsite where clinicaltrialsiteid = '"
				+ strSiteID
				+ "' "
				+ "and trial_id = (select id from trial where name = '"
				+ Stage.getInstance().getTrial() + "')))";
		ArrayList<String> Array1 = DB.getInstance().funGetDBValuesArr(
				Initializer.getInstance().GetValue("db.dbname.portal"),
				strQuery, "clinicaltrialsubjectid");
		if (!Array1.isEmpty()) {
			for (int i = 0; i < Array1.size(); i++) {
				funDeleteSubjectIfExist(Array1.get(i));
				CommonFunctions.getInstance().funWait(2);
			}
		} else {
			CommonFunctions.getInstance().funLog(
					"No subject found for this Site ID: " + strSiteID);
		}

	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funTrialAdmin_Trials() Description : This function will
	 * select the test Trial in Trail drop down and validate Trial tab Author :
	 * Suresh Kumar,Mylam Date :06 Jun 2017 Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funTrialAdmin_Sites(String strFun, String strSiteID) {
		CommonFunctions.getInstance().funWait(1);
		// Select Sites Tab
		CommonFunctions.getInstance()
				.getElement(driver, strObjPrefix+"trialAdmin.sites").click();
		CommonFunctions.getInstance().funWait(2);
		try {
			switch (strFun.toUpperCase().trim()) {
			case "ADDSITE":
				// Check and Delete Site Id if exist
				funDeleteSiteIfExist(strSiteID);
				// Navigate to Add Sites tab
				CommonFunctions.getInstance()
						.getElement(driver, strObjPrefix+"trialAdmin.sites.addSites")
						.click();
				try {
					CommonFunctions.getInstance().funWait(2);
					CommonFunctions.getInstance()
							.getElement(driver, strObjPrefix+"trialAdmin.sites.siteId")
							.sendKeys(strSiteID);
					CommonFunctions.getInstance()
							.getElement(driver, strObjPrefix+"trialAdmin.sites.siteId")
							.sendKeys(Keys.TAB);
					CommonFunctions.getInstance().funWait(1);
					CommonFunctions
							.getInstance()
							.getElement(driver,
									strObjPrefix+"trialAdmin.sites.siteName")
							.sendKeys(strSiteID);
					CommonFunctions
							.getInstance()
							.getElement(driver,
									strObjPrefix+"trialAdmin.sites.siteName")
							.sendKeys(Keys.TAB);
					CommonFunctions.getInstance().funWait(1);
					Select oE = new Select(
							CommonFunctions.getInstance().getElement(driver,
									strObjPrefix+"trialAdmin.sites.country"));
					List<WebElement> allOpt = oE.getOptions();
					for (int i = 0; i < allOpt.size(); i++) {
						String optionName = allOpt.get(i).getText();
						if (optionName.equalsIgnoreCase("United States")) {
							allOpt.get(i).click();
							break;
						}
					}
					CommonFunctions.getInstance().funWait(1);
					CommonFunctions
							.getInstance()
							.getElement(driver, strObjPrefix+"trialAdmin.sites.country")
							.sendKeys(Keys.TAB);
					CommonFunctions.getInstance().funWait(1);
					try {
						if (CommonFunctions
								.getInstance()
								.getElement(driver,
										strObjPrefix+"trialAdmin.sites.addSite")
								.getAttribute("disabled") != null) {
							CommonFunctions
									.getInstance()
									.funLog("Issue in adding new site, please check 'Add Site' button is enabled or NOT");
							test.log(
									Status.FAIL,
									MarkupHelper
											.createLabel(
													"Issue in adding new site, please check 'Add Site' button is enabled or NOT",
													ExtentColor.RED));
							try {
								test.addScreenCaptureFromPath(CommonFunctions
										.getInstance().funTakeScreenshot(
												Thread.currentThread()
														.getStackTrace()[1]
														.getMethodName()));
							} catch (IOException e1) {
								CommonFunctions.getInstance().funLog(
										"Issue in recording snapshot error is : "
												+ e1.getMessage());
							}
							Stage.getInstance().setStatus(false);
						} else {
							CommonFunctions
									.getInstance()
									.getElement(driver,
											strObjPrefix+"trialAdmin.sites.addSite")
									.click();
							funSuccessCall("New site is added and Site is : "
									+ strSiteID);
						}
					} catch (Exception e) {
						funFailureCall(
								"Issue in adding new site, please check 'Add Site' button is enabled or NOT",
								e);
						CommonFunctions.getInstance().funFinalizeResults();
					}

					CommonFunctions.getInstance().funWait(1);
					// Add this siteid as primary site to validate Sites tab
					Stage.getInstance().setSite(strSiteID + "-" + strSiteID);
				} catch (Exception e) {
					funFailureCall(
							"Issue on Identifying objects in Sites tab : "
									+ ", Exception : ", e);
					CommonFunctions.getInstance().funFinalizeResults();
				}
				break;
			case "ADDMULTIPLESITES":
				CSVReader reader = new CSVReader(new FileReader(
						System.getProperty("user.dir")
								+ Initializer.getInstance().GetValue(
										"file.csvSitesFilePath")));
				List<String[]> list = reader.readAll();
				Iterator<String[]> ite = list.iterator();
				while (ite.hasNext()) {
					String[] str = ite.next();
					System.out.println(str[0].toString());
					funDeleteSiteIfExist(str[0].toString());
					Stage.getInstance().setSite(
							str[0].toString() + "-" + str[0].toString());
					CommonFunctions.getInstance().funWait(1);
				}

				// Navigate to Add Sites tab
				CommonFunctions.getInstance()
						.getElement(driver, strObjPrefix+"trialAdmin.sites.addSites")
						.click();

				try {
					CommonFunctions.getInstance()
							.getElement(driver, strObjPrefix+"trialAdmin.sites.add")
							.click();
					CommonFunctions.getInstance().funWait(2);
					CommonFunctions.getInstance().funWaitAndAction(
							"File Upload",
							"Edit1",
							"SETTEXT",
							System.getProperty("user.dir")
									+ Initializer.getInstance().GetValue(
											"file.csvSitesFilePath"));
					CommonFunctions.getInstance().funWait(2);
					CommonFunctions.getInstance().funWaitAndAction(
							"File Upload", "Button1", "CLICK", "");
					CommonFunctions.getInstance().funWait(2);
					try {
						if (CommonFunctions
								.getInstance()
								.getElement(driver,
										strObjPrefix+"trialAdmin.sites.confirm")
								.getAttribute("disabled") != null) {

							CommonFunctions
									.getInstance()
									.funLog("Issue in adding multiple sites, please check 'Confirm sites' button is enabled or NOT");
							test.log(
									Status.FAIL,
									MarkupHelper
											.createLabel(
													"Issue in adding multiple sites, please check 'Confirm sites' button is enabled or NOT",
													ExtentColor.RED));
							try {
								test.addScreenCaptureFromPath(CommonFunctions
										.getInstance().funTakeScreenshot(
												Thread.currentThread()
														.getStackTrace()[1]
														.getMethodName()));
							} catch (IOException e1) {
								CommonFunctions.getInstance().funLog(
										"Issue in recording snapshot error is : "
												+ e1.getMessage());
							}
							Stage.getInstance().setStatus(false);
						} else {
							CommonFunctions
									.getInstance()
									.getElement(driver,
											strObjPrefix+"trialAdmin.sites.confirm")
									.click();
							CommonFunctions.getInstance().funWait(1);
							funSuccessCall("Multiple sites are added successfully"
									+ strSiteID);
						}
					} catch (Exception e) {
						funFailureCall(
								"Issue in adding multiple sites, please check 'Confirm sites' button is enabled or NOT",
								e);
					}

					// Verify the sites added successfully
					CommonFunctions
							.getInstance()
							.getElement(driver,
									strObjPrefix+"trialAdmin.sites.viewSites").click();
					CommonFunctions.getInstance().funWait(1);
					// for(int i=0;i<str.length;i++){
					// // String[] str = i1.next();
					// WebElement oViewSitesTable = CommonFunctions
					// .getInstance()
					// .getElement(driver,
					// strObjPrefix+"trialAdmin.sites.viewSitesTable");
					// List<WebElement> allRows = oViewSitesTable
					// .findElements(By.tagName("tr"));
					// CommonFunctions.getInstance().funWait(1);
					// for (WebElement row : allRows) {
					// List<WebElement> cells = row.findElements(By
					// .tagName("td"));
					// String strSiteID_GUI = cells.get(0).getText()
					// .trim();
					// if (strSiteID_GUI.equalsIgnoreCase(str[i]
					// .toString().trim())) {
					// CommonFunctions.getInstance().funLog(
					// "Site added successfully and Site id is : "
					// + strSiteID_GUI);
					// test.log(LogStatus.FAIL,
					// "Site added successfully and Site id is : "
					// + strSiteID_GUI, "");
					// Stage.getInstance().setStatus(false);
					// break;
					// }
					// }
					// }
					// }

				} catch (Exception e) {
					funFailureCall(
							"Issue on Adding multiple sites in Sites tab : ", e);
					CommonFunctions.getInstance().funFinalizeResults();
				}
				break;
			default:
				System.out.println("vtgchdasbf");
			}
		} catch (Exception e) {
			funFailureCall("Issue on Adding sites in Sites tab : ", e);
			CommonFunctions.getInstance().funFinalizeResults();
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funDeleteSiteIfExist() Description : This function will
	 * delete the site if exist Author : Suresh Kumar,Mylam Date :08 Jun 2017
	 * Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funDeleteSubjectIfExist(String strSite) { // provided site name
															// and subject id
															// are same
		boolean temp = false;
		// check for the site existence and delete if exist , so that we can
		// re-create same data
		// Verify the sites added successfully
		CommonFunctions.getInstance().getElement(driver, strObjPrefix+"trialAdmin.sub")
				.click();
		CommonFunctions.getInstance().funWait(2);
		CommonFunctions.getInstance()
				.getElement(driver, strObjPrefix+"trialAdmin.sub.viewSub").click();
		CommonFunctions.getInstance().funWait(2);
		WebElement oViewSitesTable = CommonFunctions.getInstance().getElement(
				driver, strObjPrefix+"trialAdmin.sub.viewSubTable");
		List<WebElement> allRows = oViewSitesTable.findElements(By
				.tagName("tr"));
		// Below loop finds the record and delete
		for (WebElement row : allRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			int iCol = cells.size();
			String strSub_GUI = cells.get(0).getText().trim();
			if (strSub_GUI.equalsIgnoreCase(strSite.trim())) {
				funSuccessCall("This subject is already present and deleting.... ");
				temp = true;
				WebElement cell = cells.get(iCol - 1);
				try {
					cell.findElement(
							By.cssSelector("a[ng-click='edit(subject)']"))
							.click();
					CommonFunctions.getInstance().funWait(2);
				} catch (Exception e) {
					funFailureCall(
							"Issue on Identifying Edit objects in view subjects tab : ",
							e);
					CommonFunctions.getInstance().funFinalizeResults();
				}
				try {
					CommonFunctions.getInstance()
							.getElement(driver, strObjPrefix+"trialAdmin.sub.delete")
							.click();
					CommonFunctions.getInstance().funWait(1);
					CommonFunctions
							.getInstance()
							.getElement(driver, strObjPrefix+"trialAdmin.sub.deleteSub")
							.click();
					CommonFunctions.getInstance().funWait(1);
					break;
				} catch (Exception e) {
					funFailureCall("Issue on Deleting the subject ", e);
					CommonFunctions.getInstance().funFinalizeResults();
				}
			}

		}
		if (!temp) {
			funSuccessCall("No Record found for this Subject id : " + strSite
					+ " and it is free to create");
		}
		if (temp) {
			CommonFunctions.getInstance().funWait(3);
			// Below loop verifies the deleted record
			allRows = oViewSitesTable.findElements(By.tagName("tr"));
			boolean flag = true;
			for (WebElement row : allRows) {
				List<WebElement> cells = row.findElements(By.tagName("td"));
				String strSiteID_GUI = cells.get(0).getText().trim();
				if (strSiteID_GUI.equalsIgnoreCase(strSite.trim())) {

					CommonFunctions.getInstance().funLog(
							"Deleted record still exist, Deleted id is : "
									+ strSiteID_GUI);
					test.log(Status.FAIL, MarkupHelper.createLabel(
							"Deleted record still exist, Deleted id is : "
									+ strSiteID_GUI, ExtentColor.RED));
					try {
						test.addScreenCaptureFromPath(CommonFunctions
								.getInstance()
								.funTakeScreenshot(
										Thread.currentThread().getStackTrace()[1]
												.getMethodName()));
					} catch (IOException e1) {
						CommonFunctions.getInstance().funLog(
								"Issue in recording snapshot error is : "
										+ e1.getMessage());
					}
					Stage.getInstance().setStatus(false);
					flag = false;
					break;
				}
			}
			if (flag) {
				funSuccessCall("Subject Record deleted Successfully : "
						+ strSite);
			}
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funTrialAdmin_Subjects() Description : This function will
	 * validate Subjects tab Author : Suresh Kumar,Mylam Date :09 Jun 2017
	 * Parameter : strFun,strSite
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funTrialAdmin_Subjects(String strFun, String strSite) {
		CommonFunctions.getInstance().funWait(1);
		// Select Subjects Tab
		CommonFunctions.getInstance().getElement(driver, strObjPrefix+"trialAdmin.sub")
				.click();
		CommonFunctions.getInstance().funWait(2);
		try {
			switch (strFun.toUpperCase().trim()) {
			case "ADDSUBJECT":
				// if (strSite.isEmpty()){
				strSite = Stage.getInstance().getSite();
				// }
				// Check and Delete Site Id if exist
				funDeleteSubjectIfExist(strSite);
				// Navigate to Add Subjects tab
				CommonFunctions.getInstance()
						.getElement(driver, strObjPrefix+"trialAdmin.sub.addSub")
						.click();
				try {
					CommonFunctions.getInstance().funWait(2);
					Select oE = new Select(CommonFunctions.getInstance()
							.getElement(driver, strObjPrefix+"trialAdmin.sub.site"));
					List<WebElement> allOpt = oE.getOptions();
					for (int i = 0; i < allOpt.size(); i++) {
						String optionName = allOpt.get(i).getText();
						if (optionName.equalsIgnoreCase(strSite)) {
							allOpt.get(i).click();
							break;
						}
					}
					CommonFunctions.getInstance().funWait(1);
					CommonFunctions.getInstance()
							.getElement(driver, strObjPrefix+"trialAdmin.sub.subject")
							.sendKeys(strSite);
					CommonFunctions.getInstance()
							.getElement(driver, strObjPrefix+"trialAdmin.sub.subject")
							.sendKeys(Keys.TAB);
					CommonFunctions.getInstance().funWait(1);
					CommonFunctions.getInstance()
							.getElement(driver, strObjPrefix+"trialAdmin.sub.subDesc")
							.sendKeys(strSite);
					CommonFunctions.getInstance()
							.getElement(driver, strObjPrefix+"trialAdmin.sub.subDesc")
							.sendKeys(Keys.TAB);
					CommonFunctions.getInstance().funWait(1);
					// CommonFunctions
					// .getInstance()
					// .getElement(driver,
					// strObjPrefix+"trialAdmin.sub.addSubject")
					// .click();
					// CommonFunctions.getInstance().funWait(1);

					try {
						if (CommonFunctions
								.getInstance()
								.getElement(driver,
										strObjPrefix+"trialAdmin.sub.addSubject")
								.getAttribute("disabled") != null) {

							CommonFunctions
									.getInstance()
									.funLog("Issue in adding new subject, please check 'Add Subject' button is enabled or NOT");
							test.log(
									Status.FAIL,
									MarkupHelper
											.createLabel(
													"Issue in adding new subject, please check 'Add Subject' button is enabled or NOT",
													ExtentColor.RED));
							try {
								test.addScreenCaptureFromPath(CommonFunctions
										.getInstance().funTakeScreenshot(
												Thread.currentThread()
														.getStackTrace()[1]
														.getMethodName()));
							} catch (IOException e1) {
								CommonFunctions.getInstance().funLog(
										"Issue in recording snapshot error is : "
												+ e1.getMessage());
							}
							Stage.getInstance().setStatus(false);
						} else {
							CommonFunctions
									.getInstance()
									.getElement(driver,
											strObjPrefix+"trialAdmin.sub.addSubject")
									.click();
							CommonFunctions.getInstance().funWait(1);
							funSuccessCall("New subject is added and subject is : "
									+ strSite);
						}
					} catch (Exception e) {
						funFailureCall(
								"Issue in adding new site, please check 'Add Site' button is enabled or NOT",
								e);
					}
				} catch (Exception e) {

					funFailureCall(
							"Issue on Identifying objects in Subjects tab : ",
							e);
					CommonFunctions.getInstance().funFinalizeResults();
				}
				break;
			case "ADDMULTIPLESUBJECTS":

				// Navigate to Add Sites tab
				CommonFunctions.getInstance()
						.getElement(driver, strObjPrefix+"trialAdmin.sub.addSub")
						.click();
				try {
					CommonFunctions.getInstance()
							.getElement(driver, strObjPrefix+"trialAdmin.sites.add")
							.click();
					CommonFunctions.getInstance().funWait(3);
					CommonFunctions.getInstance().funWaitAndAction(
							"File Upload",
							"Edit1",
							"SETTEXT",
							System.getProperty("user.dir")
									+ Initializer.getInstance().GetValue(
											"file.csvSubjectsFilePath"));
					CommonFunctions.getInstance().funWait(2);
					CommonFunctions.getInstance().funWaitAndAction(
							"File Upload", "Button1", "CLICK", "");
					CommonFunctions.getInstance().funWait(2);
					CommonFunctions.getInstance()
							.getElement(driver, strObjPrefix+"trialAdmin.sub.confirm")
							.click();
					CommonFunctions.getInstance().funWait(2);
					funSuccessCall("Multiple subjects are added successfully");
					// // Verify the Subject added successfully
					// CommonFunctions
					// .getInstance()
					// .getElement(driver,
					// strObjPrefix+"trialAdmin.sites.viewSites")
					// .click();
					// CommonFunctions.getInstance().funWait(2);

				} catch (Exception e) {
					funFailureCall(
							"Issue on Adding multiple Subjects in subjects tab : ",
							e);
					CommonFunctions.getInstance().funFinalizeResults();
				}
				break;
			default:
				CommonFunctions.getInstance().funLog(
						"No case is identified for the given input ");
				test.log(Status.FAIL, MarkupHelper.createLabel(
						"No case is identified for the given input ",
						ExtentColor.RED));
				try {
					test.addScreenCaptureFromPath(CommonFunctions.getInstance()
							.funTakeScreenshot(
									Thread.currentThread().getStackTrace()[1]
											.getMethodName()));
				} catch (IOException e1) {
					CommonFunctions.getInstance().funLog(
							"Issue in recording snapshot error is : "
									+ e1.getMessage());
				}
				Stage.getInstance().setStatus(false);
				CommonFunctions.getInstance().funFinalizeResults();
			}
		} catch (Exception e) {
			funFailureCall("Issue on Adding sites in Sites tab : ", e);
			CommonFunctions.getInstance().funFinalizeResults();
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funSelectTestTrial() Description : This function will
	 * select the test Trial in Trail drop down Author : Suresh Kumar,Mylam Date
	 * : 23 May 2017 Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funSelectTestTrial(String strValue) {
		driver.switchTo().frame("asdfasdfasdfsdfa");
		// Navigate to Users page
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.Users").click();
		} catch (Exception e) {
			CommonFunctions.getInstance().funLog(
					"Issue identifying the object - Users " + e.getMessage());
		}
		CommonFunctions.getInstance().funWait(4);
		try {
			// Select Value in drop down
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.Trail")
					.sendKeys("AutomationTestTrial");
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.Trail").click();
			Select oE = new Select(CommonFunctions.getInstance().getElement(
					driver, strObjPrefix+"trialAdmin.Trail"));
			WebElement oSelected = oE.getFirstSelectedOption();
			String strSelected = oSelected.getText();
			if (strSelected.trim().equalsIgnoreCase(strValue.trim())) {
				funSuccessCall("Trial is selected in the drop down : "
						+ strValue);
			} else {
				CommonFunctions.getInstance().funLog(
						"Expected Trial is NOT selected in the drop down : Actual value is - "
								+ strSelected + " and Expected value is - "
								+ strValue);
				test.log(Status.FAIL, MarkupHelper.createLabel(
						"Expected Trial is NOT selected in the drop down : Actual value is - "
								+ strSelected + " and Expected value is - "
								+ strValue, ExtentColor.RED));
				try {
					test.addScreenCaptureFromPath(CommonFunctions.getInstance()
							.funTakeScreenshot(
									Thread.currentThread().getStackTrace()[1]
											.getMethodName()));
				} catch (IOException e1) {
					CommonFunctions.getInstance().funLog(
							"Issue in recording snapshot error is : "
									+ e1.getMessage());
				}
				Stage.getInstance().setStatus(false);
			}

		} catch (Exception e) {
			funFailureCall("Issue on Identifying drop down : " + strValue, e);
		}
		driver.switchTo().defaultContent();
		CommonFunctions.getInstance().funWait(1);
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funSelectTestTrial() Description : This function will
	 * select the test Trial in Trail drop down Author : Suresh Kumar,Mylam Date
	 * : 25 May 2017 Parameter : NA
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funInviteUser(String strEmail, String strUserRole) {
		// Navigate to Users page
		CommonFunctions.getInstance()
				.getElement(driver, strObjPrefix+"trialAdmin.users").click();
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.users.email").clear();
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.users.email")
					.sendKeys(strEmail);
			CommonFunctions.getInstance().funLog(
					"Email entered successfully on Users page");
		} catch (Exception e) {
			CommonFunctions.getInstance().funLog(
					"Issue identifying the object - Email " + e.getMessage());
		}
		CommonFunctions.getInstance().funWait(1);
		try {
			Select oE = new Select(CommonFunctions.getInstance().getElement(
					driver, strObjPrefix+"trialAdmin.users.role"));
			List<WebElement> allOpt = oE.getOptions();
			for (int i = 0; i < allOpt.size(); i++) {
				String optionName = allOpt.get(i).getText();
				if (optionName.equalsIgnoreCase(strUserRole)) {
					allOpt.get(i).click();
					break;
				}
			}

		} catch (Exception e) {
			funFailureCall("Issue on Identifying drop down : " + strUserRole, e);
		}
		try {
			if (CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"trialAdmin.users.addUser")
					.getAttribute("disabled") != null) {
				CommonFunctions
						.getInstance()
						.funLog("Issue in adding new User, please check 'Add User' button is enabled or NOT");
				test.log(
						Status.FAIL,
						MarkupHelper
								.createLabel(
										"Issue in adding new User, please check 'Add User' button is enabled or NOT",
										ExtentColor.RED));
				try {
					test.addScreenCaptureFromPath(CommonFunctions.getInstance()
							.funTakeScreenshot(
									Thread.currentThread().getStackTrace()[1]
											.getMethodName()));
				} catch (IOException e1) {
					CommonFunctions.getInstance().funLog(
							"Issue in recording snapshot error is : "
									+ e1.getMessage());
				}
				Stage.getInstance().setStatus(false);
			} else {
				CommonFunctions.getInstance()
						.getElement(driver, strObjPrefix+"trialAdmin.users.addUser")
						.click();
				CommonFunctions.getInstance().funWait(3);
				funSuccessCall("New User is added and User is : " + strEmail);
			}
		} catch (Exception e) {
			funFailureCall(
					"Issue in adding new User, please check 'Add User' button is enabled or NOT",
					e);
			CommonFunctions.getInstance().funFinalizeResults();
		}

		CommonFunctions.getInstance().funWait(1);
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
		driver.navigate().to(Initializer.getInstance().GetValue("app.test"));
		CommonFunctions.getInstance().funWait(8);
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"login.register").click();
			CommonFunctions.getInstance().funWait(3);
			funInfoCall("Register button is clicked");
		} catch (Exception e) {
			funFailureCall("Issue identifying the object - Register", e);
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.email")
					.sendKeys(strEmail);
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.email")
					.sendKeys(Keys.TAB);
			CommonFunctions.getInstance().funWait(1);
			funInfoCall("Email Address entered successfully");
		} catch (Exception e) {
			funFailureCall("Issue identifying the object - Register--> Email",
					e);
			CommonFunctions.getInstance().funWait(5);
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.continue").click();
			CommonFunctions.getInstance().funWait(3);
			funInfoCall("Continue button is clisked");
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register--> Continue", e);
		}
		CommonFunctions.getInstance().funWait(2);
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
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step1.userName")
					.sendKeys(strUserName);
			CommonFunctions.getInstance().funWait(1);
			CommonFunctions.getInstance().funLog("Register button is clicked");
			test.log(Status.INFO, MarkupHelper.createLabel(
					"User Name is entered successfully" + strUserName,
					ExtentColor.ORANGE));
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register-->Step1--> UserName ",
					e);
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step1.password")
					.sendKeys(strUserName);
			CommonFunctions.getInstance().funLog("Register button is clicked");
			test.log(Status.INFO, MarkupHelper.createLabel(
					"User Name is entered successfully" + strUserName,
					ExtentColor.ORANGE));
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register-->Step1--> password ",
					e);
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step1.confirmPassword")
					.sendKeys(strUserName);
			funInfoCall("confirmPassword is entered successfully");
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register-->Step1--> confirmPassword ",
					e);
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step1.firstName")
					.sendKeys(strUserName);
			funInfoCall("FirstName is entered successfully");
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register-->Step1--> firstName ",
					e);
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step1.lastName")
					.sendKeys(strUserName);
			funInfoCall("LastName is entered successfully");
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register-->Step1--> lastName ",
					e);
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step1.phoneNo")
					.sendKeys(strUserName);
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step1.phoneNo")
					.sendKeys(Keys.TAB);
			CommonFunctions.getInstance().funWait(1);
			funInfoCall("phoneNo is entered successfully");
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register-->Step1-->phoneNo ",
					e);
		}
		CommonFunctions.getInstance().funWait(1);
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step1.continue").click();
			funInfoCall("Continue button is clicked successfully on Step1 page");
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register--> Step1-->Continue ",
					e);
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
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step2.org")
					.sendKeys(strValue);
			funInfoCall("Organization is entered successfully" + strValue);
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register-->Step2--> Organization ",
					e);
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step2.address1")
					.sendKeys(strValue);
			funInfoCall("address1 is entered successfully");
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register-->Step2--> address1 ",
					e);
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step2.address2")
					.sendKeys(strValue);
			funInfoCall("address2 is entered successfully");
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register--> Step2--> address2 ",
					e);
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step2.city")
					.sendKeys(strValue);
			funInfoCall("City is entered successfully");
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register-->Step2--> City ",
					e);
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step2.state")
					.sendKeys(strValue);
			funInfoCall("State is entered successfully");
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register--> Step2-->State ",
					e);
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step2.postal")
					.sendKeys(strValue);
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step2.postal")
					.sendKeys(Keys.TAB);
			funInfoCall("Postal code is entered successfully");
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register-->Step2-->Postal code ",
					e);
		}
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step2.country")
					.sendKeys(strValue);
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step2.country")
					.sendKeys(Keys.TAB);
			funInfoCall("Country is entered successfully");
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register-->Step2-->Country",
					e);
		}

		CommonFunctions.getInstance().funWait(1);
		try {
			CommonFunctions.getInstance()
					.getElement(driver, strObjPrefix+"register.step2.continue").click();
			funInfoCall("Continue button is clicked successfully on Step2 page");
		} catch (Exception e) {
			funFailureCall(
					"Issue identifying the object - Register--> Step2-->Continue ",
					e);
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funSuccessCall() Description : This function will update
	 * results in success call Author : Suresh Kumar,Mylam Date : 08 jun 2017
	 * Parameter : strDescription
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	// public void funSuccessCall(String strDescription) {
	// CommonFunctions.getInstance().funLog(strDescription);
	// test.log(LogStatus.PASS, strDescription, test
	// .addScreenCapture(CommonFunctions.getInstance()
	// .funTakeScreenshot(
	// Thread.currentThread().getStackTrace()[1]
	// .getMethodName())));
	// }
	public void funSuccessCall(String strDescription) {
		CommonFunctions.getInstance().funLog(strDescription);
		test.log(Status.PASS,
				MarkupHelper.createLabel(strDescription, ExtentColor.GREEN));
		try {
			test.addScreenCaptureFromPath(CommonFunctions.getInstance()
					.funTakeScreenshot(
							Thread.currentThread().getStackTrace()[1]
									.getMethodName()));
		} catch (IOException e) {
			CommonFunctions.getInstance().funLog(
					"Issue in recording snapshot error is : " + e.getMessage());
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funFailureCall() Description : This function will update
	 * results in success call Author : Suresh Kumar,Mylam Date : 08 jun 2017
	 * Parameter : strDescription,Exception
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	// public void funFailureCall(String strDescription, Exception e) {
	// CommonFunctions.getInstance().funLog(strDescription + e.getMessage());
	// test.log(LogStatus.FAIL, strDescription, test
	// .addScreenCapture(CommonFunctions.getInstance()
	// .funTakeScreenshot(
	// Thread.currentThread().getStackTrace()[1]
	// .getMethodName())));
	// Stage.getInstance().setStatus(false);
	// }

	public void funFailureCall(String strDescription, Exception e) {
		CommonFunctions.getInstance().funLog(strDescription);
		test.log(Status.FAIL, MarkupHelper.createLabel(strDescription
				+ ". Error is: " + e.getMessage(), ExtentColor.RED));
		try {
			test.addScreenCaptureFromPath(CommonFunctions.getInstance()
					.funTakeScreenshot(
							Thread.currentThread().getStackTrace()[1]
									.getMethodName()));
		} catch (IOException e1) {
			CommonFunctions.getInstance()
					.funLog("Issue in recording snapshot error is : "
							+ e1.getMessage());
		}
		Stage.getInstance().setStatus(false);
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funInfoCall() Description : This function will update
	 * results in success call Author : Suresh Kumar,Mylam Date : 08 jun 2017
	 * Parameter : strDescription,Exception
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funInfoCall(String strDescription) {
		CommonFunctions.getInstance().funLog(strDescription);
		test.log(Status.INFO,
				MarkupHelper.createLabel(strDescription, ExtentColor.ORANGE));
		try {
			test.addScreenCaptureFromPath(CommonFunctions.getInstance()
					.funTakeScreenshot(
							Thread.currentThread().getStackTrace()[1]
									.getMethodName()));
		} catch (IOException e) {
			CommonFunctions.getInstance().funLog(
					"Issue in recording snapshot error is : " + e.getMessage());
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funCreateSubjects() Description : This function will
	 * write data in Subjects sheet Author : Suresh Kumar,Mylam Date : 13 jun
	 * 2017 Parameter : strDescription,Exception
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funCreateSubjects(String strSite) {
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(
					System.getProperty("user.dir")
							+ Initializer.getInstance().GetValue(
									"file.csvSubjectsFilePath")));
			for (int j = 0; j < 3; j++) {
				String str1 = strSite;
				String str2 = strSite
						+ new SimpleDateFormat("yyyyMMdd_HHmmss")
								.format(Calendar.getInstance().getTime());
				CommonFunctions.getInstance().funWait(1);
				String str3 = strSite
						+ new SimpleDateFormat("yyyyMMdd_HHmmss")
								.format(Calendar.getInstance().getTime());
				String[] subjects = (str1 + "#" + str2 + j + "#" + str3 + j)
						.toString().split("#");
				writer.writeNext(subjects);
			}
			writer.close();
		} catch (IOException e) {
			ApplicationFunctions.getInstance().funFailureCall(
					"Issue in creating subjects data", e);

		}

	}
}
