package com.agm.framework.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.testng.annotations.BeforeSuite;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.agm.framework.FunctionLibraries.ApplicationFunctions;
import com.agm.framework.FunctionLibraries.CommonFunctions;
import com.relevantcodes.extentreports.LogStatus;

public class Initializer {

	private static Initializer objInit = null;

	private Initializer() {
	}
	// Creating singleton
	public static Initializer getInstance() {
		if (objInit == null)
			objInit = new Initializer();
		return objInit;
	}

	public Properties configProps = null;

	@BeforeSuite
	public void funIntialize() {
//		System.out.println("before suite");
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
