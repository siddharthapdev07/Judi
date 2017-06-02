package com.agm.framework.FunctionLibraries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.agm.framework.helpers.Initializer;
import com.agm.framework.helpers.Stage;

public class DB {

	public Connection objConnDB = null;
	public Statement st;
	public String strURL;
	public ResultSet rs;
	private static DB objDB = null;
	private DB() {
	}

	// Creating singleton
	public static DB getInstance() {
		if (objDB == null)
			objDB = new DB();
		return objDB;
	}

	Initializer initializer = Initializer.getInstance();
	Stage stage = Stage.getInstance();
	
	public Connection funConnectDB(String strEnv, String dbName) {

		try {
			switch (strEnv.toLowerCase()) {
			case "test05":
				strURL = "jdbc:postgresql://db.test05.agmednet.net:5432/" + dbName;
				break;
			case "tes-1g":
				strURL = "jdbc:postgresql://db.test-1g.agmednet.net:5432" + dbName;
				break;
			default:
				strURL = "jdbc:postgresql://db.test05.agmednet.net:5432/portal_db";
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
							+ e.getMessage() + " Query executed is : "+ strSQLQuery);
		}
		try {
			while (rs.next())
			{
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

}
