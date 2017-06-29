package com.agm.smoke;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import autoitx4java.AutoItX;

import com.agm.framework.FunctionLibraries.ApplicationFunctions;
import com.agm.framework.FunctionLibraries.DB;
import com.agm.framework.FunctionLibraries.TestScripts;
import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.jacob.com.LibraryLoader;

public class BaseTest {
	public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;
    public boolean iStatus = true;
    public String strTestCaseName = null;
    public String strLogFileName = null;
    private AutoItX objAutoIT = null;
    @BeforeSuite
    public void setUp(String className)
    {
    	
    	System.out.println(Thread.currentThread().getStackTrace()[2].getClassName());

        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/MyOwnReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("AutomationTesting");
        htmlReporter.config().setReportName("Smoke Test Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.DARK);
    }
    
 
//    public void funTestSetUp(){
//		// ********************************* INITIAL SETUP
//		// ************************************************
//		Stage.getInstance().setStatus(iStatus);
////		strTestCaseName = sun.reflect.Reflection.getCallerClass(2)
////				.getSimpleName();
//		strTestCaseName = Thread.currentThread().getStackTrace()[2].getMethodName();
//		Stage.getInstance().setTestName(strTestCaseName);
//		strLogFileName = strTestCaseName
//				+ "_"
//				+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar
//						.getInstance().getTime());
//		// Setting the Log file path in system variables
//		System.setProperty("logFileName", strLogFileName);
////		funStartTestCase(strLogFileName);
//		
//		// ********************************* AUTOIT SETUP
//		// ************************************************
//		File file = new File(Initializer.getInstance().GetValue(
//				"java.autoit.jacob"));
//		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
//		objAutoIT = new AutoItX();
//
//		// Initialize test object in other Libraries
//		ApplicationFunctions.getInstance().init(test);
//		DB.getInstance().init(test);
//		TestScripts.getInstance().init(test);
//
//    } 
    
     
    @AfterMethod
    public void getResult(ITestResult result)
    {
        if(result.getStatus() == ITestResult.FAILURE)
        {
//            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));
            test.fail(result.getThrowable());
        }
        else if(result.getStatus() == ITestResult.SUCCESS)
        {
//            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
        }
        else
        {
//            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" Test Case SKIPPED", ExtentColor.ORANGE));
//            test.skip(result.getThrowable());
        }
    }
     
    @AfterSuite
    public void tearDown()
    {
        extent.flush();
    }
}
