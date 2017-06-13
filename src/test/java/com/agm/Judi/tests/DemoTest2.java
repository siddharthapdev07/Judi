package com.agm.Judi.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import junit.framework.TestResult;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import autoitx4java.AutoItX;

import com.agm.framework.FunctionLibraries.ApplicationFunctions;
import com.agm.framework.FunctionLibraries.CommonFunctions;
import com.agm.framework.FunctionLibraries.DB;
import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;
import com.jacob.com.LibraryLoader;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
public class DemoTest2 {
	Connection conn = null;
	public String strSQLQuery;
	public String strField;
	public WebDriver driver;
	public AutoItX objAutoIT = null;
    public Object[][] tabArray=null;
	@Test
	public void Demo() {	
		int j=0;
		for (j = 0; j < 10; j++) {
			Random rand = new Random();
			int selected = rand.nextInt(100);
			System.out.println(selected);
		}

		
		
//		CSVWriter writer;
//		try {
//			writer = new CSVWriter(new FileWriter(
//					System.getProperty("user.dir")
//							+ Initializer.getInstance().GetValue(
//									"file.csvSubjectsFilePath")));
//			for (int j = 0; j < 3; j++) {
//				String str1 = "AutoSite8";
//				String str2 = "AutoSite8"
//						+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar
//								.getInstance().getTime());
//				CommonFunctions.getInstance().funWait(1);
//				String str3 = "AutoSite8"
//						+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar
//								.getInstance().getTime());
//				String[] country = (str1 + "#" + str2 + j + "#" + str3 + j)
//						.toString().split("#");
//				writer.writeNext(country);
//			}
//			writer.close();
//		} catch (IOException e) {
//			ApplicationFunctions.getInstance().funFailureCall("Issue in creating subjects data", e);
//			
//		}
		
	}
		
		
		
		
		
//		CSVReader reader = new CSVReader(new FileReader(
//				System.getProperty("user.dir")
//						+ Initializer.getInstance().GetValue(
//								"file.csvSubjectsFilePath")));
//		
//		List<String[]> li = reader.readAll();
//		Iterator<String[]> i1 = li.iterator();
//
//		while (i1.hasNext()) {
//			String[] str = i1.next();
//			System.out.println(str[0].toString());
//			Stage.getInstance().setSite(
//					str[0].toString() + "-" + str[0].toString());
//			CommonFunctions.getInstance().funWait(1);
//		}
	}
		
		

		
//		String strQuery = "select clinicaltrialsiteid from trialsite where trial_id = (SELECT id FROM trial where name = 'Judi_Demo') and clinicaltrialsiteid = 'AutoSite3' and ctmdeleted = 'f'";		
//		DB.getInstance().funConnectDB(
//		Initializer.getInstance().GetValue("db.env"),
//		Initializer.getInstance().GetValue("db.test1g.dbname.portal"));
//		CommonFunctions.getInstance().funLog("SQL Query used to fetch verify site exist or not is : " + strQuery);
//		String strValue = DB.getInstance().funExecuteQuery(strQuery, "clinicaltrialsiteid");
//		if(strValue != null){
//			System.out.println("success");
//		}else{
//			System.out.println("fail");
//		}
		
		
		
		
		
//		String strQuery= "select clinicaltrialsubjectid from trialsubject where id in "
//				+ "(select subject_id from subjectsitexref where site_id = "
//				+ "(select id from trialsite where clinicaltrialsiteid = 'AutoSite1' "
//				+ "and trial_id = (select id from trial where name = 'Judi_Demo')))";
//		ArrayList<String> Array1 = DB.getInstance().funGetDBValuesArr(Initializer.getInstance().GetValue("db.test1g.dbname.portal"), strQuery, "clinicaltrialsubjectid");
//		System.out.println(Array1.isEmpty());
//		for(int i=0;i< Array1.size();i++){
//			
//		}
		
		
//	}
		
		
		
		


//		CSVReader reader = new CSVReader(new FileReader(
//				System.getProperty("user.dir")
//						+ Initializer.getInstance().GetValue(
//								"file.csvSitesFilePath")));
//		List<String[]> li = reader.readAll();
//		Iterator<String[]> i1 = li.iterator();
//		
//		while (i1.hasNext()) {
//			String[] str = i1.next();
//			for (int i = 0; i < str.length; i++) {
//				System.out.print(" " + str[i]);
//			}
//			System.out.println("   ");
//		}
//	}
//	
	
//		DB.getInstance().funConnectDB(
//				Initializer.getInstance().GetValue("db.env"),
//				Initializer.getInstance().GetValue("db.test1g.dbname.portal"));
//		String strQuery = "SELECT count(*) from trialsite WHERE trial_id IN (SELECT id FROM trial where name = 'Judi_Demo')";
//
//		CommonFunctions.getInstance().funLog("SQL Query used to fetch sites is : " + strQuery);
//		String iSites_DB = DB.getInstance().funExecuteQuery(strQuery, "count");	
//		System.out.println(iSites_DB);
//	public Object[][] Demo() {

//		      File inputWorkbook = new File(System.getProperty("user.dir")
//						+ Initializer.getInstance().GetValue(
//								"file.csvSitesFilePath"));
//		        Workbook w;
//		        int startRow,startCol, endRow, endCol,ci,cj,ck;
//		        try {
//		            //w = Workbook
//		            w = Workbook.getWorkbook(inputWorkbook);
//		            // Get the first sheet
//		            Sheet sheet = w.getSheet("sheet1");
//		            // Loop over first 10 column and lines
//		            endRow = sheet.getRows();
//		            endCol = sheet.getColumns();
//		            tabArray=new String[endRow-1][endCol-1];
//
//		            ci=0;
//
//		            for (int i=1;i<endRow;i++,ci++){
//		                cj=0;
//
//		                for (int j=1;j<endCol;j++,cj++){
//
//		                  Cell cell = sheet.getCell(j, i);
//		                tabArray[ci][cj] = cell.getContents(); 
//
//		                }
//		            //    System.out.println("");
//		            }
//		            //file.close();
//		        }
//		        catch (Exception e)
//		        {
//		            e.printStackTrace();
//		        }
//
//
//
//		       return(tabArray);  /// Here Getting the error **Type mismatch: cannot convert from Object[][] to Object[][][]**
//		    }
		
		
		
		
		
//		CommonFunctions.getInstance().funLoadTestDetailsFromTestRail(this.getClass().getSimpleName());
//		CommonFunctions.getInstance().funUpdateResultsToTestRail("FAIL");
//		
		
		
//		 DB.getInstance().funConnectDB(Initializer.getInstance().GetValue("db.env"),
//		 Initializer.getInstance().GetValue("db.test05.dbname.portal"));
//		 strSQLQuery = "select trial_id from pendingtrialuser where emailaddress = 'autotest_freetodelete1@mx-intr.agmednet.net'";
//		 strField = DB.getInstance().funExecuteQuery(strSQLQuery, "trial_id");
//		 System.out.println(strField);
		
		
//		File file = new File(Initializer.getInstance().GetValue("java.autoit.jacob"));		
//		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
//		objAutoIT = new AutoItX();
//		CommonFunctions commonFunctions = CommonFunctions.getInstance();
//		commonFunctions.init(objAutoIT);
//		System.setProperty("webdriver.gecko.driver", Initializer
//				.getInstance().GetValue("java.firefox.path"));
//		driver = new FirefoxDriver();
//		driver.manage().window().maximize();
//		ApplicationFunctions applicationFunctions = ApplicationFunctions
//				.getInstance();
//		applicationFunctions.init(driver);
//		driver.get("file:///C://Users//Suresh%20Mylam//Desktop//upload.html");        //This needs to be parameterized 
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		driver.findElement(By.id("file")).click();
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		CommonFunctions.getInstance().funWaitAndAction("File Upload", "Edit1", "SETTEXT", "C:\\Users\\Suresh Mylam\\Desktop\\Selenium-Framework.pptx");
//		CommonFunctions.getInstance().funWaitAndAction("File Upload", "Button1", "CLICK", "");
//	}

//}
