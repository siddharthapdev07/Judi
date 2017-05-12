package com.agm.Judi.tests;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.agm.framework.FunctionLibraries.CommonFunctions;
import com.agm.framework.FunctionLibraries.DB;
import com.agm.framework.helpers.Initializer;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class DemoTest2 {
	Connection conn = null;
	String strSQLQuery;
	String strField;
	@Test
	public void Demo() {
		DB.getInstance().funConnectDB(Initializer.getInstance().GetValue("db.env"), Initializer.getInstance().GetValue("db.test05.dbname.portal"));
		strSQLQuery = "Select id from public.trialversion";
		strField = DB.getInstance().funExecuteQuery(strSQLQuery, "id");
		System.out.println(strField);
		
//		DB.getInstance().funConnectDB(
//		Initializer.getInstance().GetValue("db.env"),
//		Initializer.getInstance().GetValue("db.test05.dbname.portal"));		
//strSQLQuery = "Select id from public.trialversion";
//commonFunctions.funLog("Query used for retrieve id from trialversion table : " + strSQLQuery);
//strField = DB.getInstance().funExecuteQuery(strSQLQuery, "id");
	}

}
