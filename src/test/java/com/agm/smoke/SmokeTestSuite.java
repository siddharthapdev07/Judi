package com.agm.smoke;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import autoitx4java.AutoItX;

import com.agm.framework.FunctionLibraries.ApplicationFunctions;
import com.agm.framework.FunctionLibraries.CommonFunctions;
import com.agm.framework.FunctionLibraries.DB;
import com.agm.framework.FunctionLibraries.TestScripts;
import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class SmokeTestSuite {
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
	public boolean iStatus = true;
	public String strTestCaseName = null;
	public String strLogFileName = null;
	public String strTestSuiteName = null;

	@BeforeSuite
	public void setUp() {
		String strFullClassName = Thread.currentThread().getStackTrace()[1]
				.getClassName();
		strTestCaseName = strFullClassName.substring(strFullClassName
				.lastIndexOf('.') + 1);
		Stage.getInstance().setTestName(strTestCaseName);
		strLogFileName = strTestCaseName
				+ "_"
				+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar
						.getInstance().getTime());
		htmlReporter = new ExtentHtmlReporter(Initializer.getInstance()
				.GetValue("java.results.path") + strLogFileName + ".html");

		strLogFileName = strTestCaseName
				+ "_"
				+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar
						.getInstance().getTime());
		// Setting the Log file path in system variables
		System.setProperty("logFileName", strLogFileName);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("AutomationTesting");
		htmlReporter.config().setReportName("Smoke Test Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);
	}

	@Test
	public void Validate_TrialAdmin_TrialsSitesSubjectsUsers() {
		CommonFunctions.getInstance().funBeforeTest();
		test = extent.createTest(Stage.getInstance().getTestName());
		CommonFunctions.getInstance().init(test);
		ApplicationFunctions.getInstance().init(test);
		DB.getInstance().init(test);
		TestScripts.getInstance().init(test);

		// TestScript
		TestScripts.getInstance()
				.Validate_TrialAdmin_TrialsSitesSubjectsUsers();

		// Finalize Results
		CommonFunctions.getInstance().funFinalizeResults();
	}

	
	@Test
	public void functionality1Test2() {
		CommonFunctions.getInstance().funBeforeTest();

		test = extent.createTest(Stage.getInstance().getTestName());
		test.log(Status.INFO, MarkupHelper.createLabel(" *****************",
				ExtentColor.ORANGE));
		test.log(Status.PASS, MarkupHelper.createLabel(" *****************",
				ExtentColor.GREEN));
		try {
			test.addScreenCaptureFromPath(CommonFunctions.getInstance()
					.funTakeScreenshot(
							Thread.currentThread().getStackTrace()[1]
									.getMethodName()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(1 > 0);
	}

	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL,
					MarkupHelper.createLabel(result.getName()
							+ " Test case FAILED due to below issues:",
							ExtentColor.RED));
			test.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(
					Status.PASS,
					MarkupHelper.createLabel(result.getName()
							+ " Test Case PASSED", ExtentColor.GREEN));
		} else {
			test.log(
					Status.SKIP,
					MarkupHelper.createLabel(result.getName()
							+ " Test Case SKIPPED", ExtentColor.ORANGE));
			test.skip(result.getThrowable());
		}
	}

	@AfterSuite
	public void tearDown() {
		extent.flush();
	}
}
