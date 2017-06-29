package com.agm.framework.FunctionLibraries;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.util.ArrayList;

//import com.relevantcodes.extentreports.ExtentTest;
//import com.relevantcodes.extentreports.LogStatus;

public class DB {

	public Connection objConnDB = null;
	public Statement st;
	public String strURL;
	public ResultSet rs;
	public ExtentTest test = null;
	public String strQuery = null;
	public String strField = null;

	private static DB objDB = null;

	private DB() {
	}

	// Creating singleton
	public static DB getInstance() {
		if (objDB == null)
			objDB = new DB();
		return objDB;
	}

	public void init(ExtentTest test) {
		this.test = test;
	}

	ApplicationFunctions applicationFunctions = ApplicationFunctions
			.getInstance();
	CommonFunctions commonFunctions = CommonFunctions.getInstance();
	Initializer initializer = Initializer.getInstance();
	Stage stage = Stage.getInstance();

	public Connection funConnectDB(String strEnv, String dbName) {

		try {
			switch (strEnv) {
			case "test05":
				strURL = "jdbc:postgresql://db.test05.agmednet.net:5432/"
						+ dbName;
				break;
			case "test-1g":
				strURL = "jdbc:postgresql://db.test-1g.agmednet.net:5432"
						+ dbName;
				break;
			default:
				strURL = "jdbc:postgresql://db.test-1g.agmednet.net:5432/portal_db";
			}
			Class.forName("org.postgresql.Driver");
			try {
				objConnDB = DriverManager.getConnection(strURL, "readonlyuser",
						"readonly");
			} catch (SQLException e) {
				CommonFunctions.getInstance().funLog(
						"SQL Exception occured in DB Connection :  Exception is - "
								+ e.getMessage());
			}
		} catch (ClassNotFoundException e) {
			CommonFunctions.getInstance().funLog(
					"Exception occured in DB Connection :  Exception is - "
							+ e.getMessage());
		}
		return objConnDB;
	}

	public String funExecuteQuery(String strSQLQuery, String strField) {
		String strFieldValue = null;
		try {
			st = objConnDB.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			CommonFunctions.getInstance().funLog(
					"Exception in Creating statement :  Exception is - "
							+ e.getMessage());
		}
		try {
			rs = st.executeQuery(strSQLQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			CommonFunctions.getInstance().funLog(
					"Exception in Executing Query :  Exception is - "
							+ e.getMessage() + " Query executed is : "
							+ strSQLQuery);
		}
		try {
			while (rs.next()) {
				strFieldValue = rs.getString(strField);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strFieldValue;
	}

	public void funValidateInviteUser(String strEmail, String strRole) {

		funConnectDB(initializer.GetValue("db.env"),
				initializer.GetValue("db.test05.dbname.portal"));
		strQuery = "select trial_id from pendingtrialuser where emailaddress = '"
				+ strEmail + "' and roleenum = '" + strRole + "'";
		strField = funExecuteQuery(strQuery, "trial_id");
		if (strField != null) {

		} else {
			
			CommonFunctions.getInstance().funLog("User is not invited successfully, please check the invite user functionlaity");
			test.log(Status.FAIL, MarkupHelper.createLabel("User is not invited successfully, please check the invite user functionlaity", ExtentColor.RED));
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
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name : funGetDBValuesArr() Description : This function will run
	 * query and return the result in array format for the total rows Author :
	 * Suresh Kumar Mylam Date : 09 Jun 2017 Parameter : strQuery = SQL query,
	 * strField = Field name in the array If field name has :: then array first
	 * position has the first field name value and second position has second
	 * field name value etc..
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public ArrayList<String> funGetDBValuesArr(String strDBName,
			String strQuery, String strField) {
		// Declaring variable
		Statement oState = null;
		ResultSet oRS = null;
		String strFieldFirst = null;
		String strFieldSecond = null;

		// Connecting DB
		if (strDBName.trim().length() != 0) {
			funConnectDB(Initializer.getInstance().GetValue("db.env"),
					strDBName);
		}

		if (strField.contains("::")) {
			String[] strFields = strField.split("::");
			strFieldFirst = strFields[0];
			strFieldSecond = strFields[1];

		}

		ArrayList<String> arrDBValues = new ArrayList<String>();

		try {
			oState = objConnDB.createStatement();
			oRS = oState.executeQuery(strQuery);

			while (oRS.next()) {
				if (strField.contains("::")) {
					arrDBValues.add(oRS.getString(strFieldFirst));
					arrDBValues.add(oRS.getString(strFieldSecond));
				} else {
					arrDBValues.add(oRS.getString(strField));
				}
			}
		} catch (Exception e) {
			CommonFunctions.getInstance().funLog(
					"Error on querying the field " + strField + "\n Query : "
							+ strQuery + ". Exception : " + e.getMessage());

		}

		return arrDBValues;
	}

	public String funCheckSiteID(String strSiteID) {
		strQuery = "select clinicaltrialsiteid from trialsite where trial_id = (SELECT id FROM trial where name = '"
				+ Stage.getInstance().getTrial()
				+ "') and clinicaltrialsiteid = '"
				+ strSiteID
				+ "' and ctmdeleted = 'f'";
		DB.getInstance().funConnectDB(
				Initializer.getInstance().GetValue("db.env"),
				Initializer.getInstance().GetValue("db.dbname.portal"));
		CommonFunctions.getInstance().funLog(
				"SQL Query used to fetch verify site exist or not is : "
						+ strQuery);
		String strValue = DB.getInstance().funExecuteQuery(strQuery,
				"clinicaltrialsiteid");

		return strValue;
	}

}
