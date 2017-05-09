package com.agm.framework.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/** 
* ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
* Class Name		: Stage.java 
* Created Date		: May 02, 2017
* Author 			: Suresh Kumar,Mylam
* Description 		: This file can be used as a temporary storage
* ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
**/
public class Stage {

	private static Stage objStage = null;
	private Stage(){}
	public static Stage getInstance(){
		if(objStage==null)
			objStage = new Stage();
		return objStage;
	}
	
		//Log
		private Logger log = null;		
		private String abcd = null;
		
		/**
		 * @return the StringValue
		 */
		public String getStringValueByName()
		{
			return abcd;
		}
		/**
		 * @Set the StringValue
		 */
		public void setStringValueByName(String xyz)
		{
			this.abcd = xyz;
		}

		/**
		 * @return the log
		 */
		public Logger getLog() 
		{
			if(log == null)
			{
				log = LogManager.getRootLogger();
			}
			return  log;
		}
		/**
		 * @param log the log to set
		 */
		public void setLog(Logger log) 
		{
			this.log = log;
		}
}
