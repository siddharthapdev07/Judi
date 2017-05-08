package com.comcast.framework.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeSuite;

public class Initializer {

	private static Initializer objInit = null;

	private Initializer() {
	}

	public static Initializer getInstance() {
		if (objInit == null)
			objInit = new Initializer();
		return objInit;
	}

	public Properties configProps = null;

	@BeforeSuite
	public void funIntialize() {
		System.out.println("before suite");
		funLoadProps();
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 *  Function Name : funLoadProps Created 
	 *  Date 		  : May 02, 2017
	 *  Author 		  : Suresh Kumar,Mylam
	 *  Description   : This function will load all property files in test/resources/properties. 
	 *  Parameters    : NA
	 * '''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public void funLoadProps() {
		String configPropsPath = System.getProperty("user.dir")
				+ "//src//test//resources//properties//config.properties";
		String orPropsPath = System.getProperty("user.dir")
				+ "//src//test//resources//properties//OR.properties";

		try {
			configProps = new Properties();
			FileInputStream propFile = new FileInputStream(configPropsPath);
			configProps.load(propFile);

			propFile = new FileInputStream(orPropsPath);
			configProps.load(propFile);
		} catch (IOException e) {
//			CommonFunctions.getInstance().funLog(
//					"Issue on launching URL. Exception : " + e.getMessage());
		}
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name  : GetValue Created 
	 * Date 		  : May 02, 2017
	 * Author 		  : Suresh Kumar,Mylam
	 * Description    : This function will return value on the given key. 
	 * Parameters     : strInput : Key name
	 * '''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public String GetValue(String strInput) {
		String strOutput = null;
		String strMethodCalling = Thread.currentThread().getStackTrace()[2]
				.getMethodName();

		if (configProps == null) {
			funLoadProps();
		}

		strOutput = configProps.getProperty(strInput);

		if (strOutput.contains("::")) {
			if (!strMethodCalling.equalsIgnoreCase("GetType")) {
				strOutput = strOutput.split("::")[0].toString();
			}
		}

		if (strOutput == null) {
			System.out.println("Property '" + strInput
					+ "' is not avaiable in the property file");
		}

		return strOutput;
	}

	/*
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 * Function Name  : GetType() 
	 * Date 		  : May 02, 2017
	 * Author 		  : Suresh Kumar,Mylam
	 * Description 	  : This function will get the type
	 * Parameters     : strInput : Key name
	 * ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	 */
	public String GetType(String strInput) {
		String strOutput = null;
		strOutput = GetValue(strInput);
		int index = strOutput.lastIndexOf("::");
		strOutput = strOutput.substring(index + 2, strOutput.length());
		return strOutput;
	}	

}
