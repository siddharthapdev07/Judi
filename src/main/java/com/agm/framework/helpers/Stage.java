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
		private int iTestID ;
		private int iRunID;
		private int iCaseID;
		private boolean iStatus;
		private String strSite;
		private String strTrial;
		private String strTestName;
		
		public String getTestName()
		{
			return strTestName;
		}
		
		public void setTestName(String strTestName)
		{
			this.strTestName = strTestName;
		}
		
		public String getTrial()
		{
			return strTrial;
		}
		
		public void setTrial(String strTrial)
		{
			this.strTrial = strTrial;
		}
		
		public String getSite()
		{
			return strSite;
		}
		
		public void setSite(String strSite)
		{
			this.strSite = strSite;
		}
		
		public boolean getStatus()
		{
			return iStatus;
		}
		
		public void setStatus(boolean iStatus)
		{
			this.iStatus = iStatus;
		}
		/**
		 * @return the IntegerValue
		 */
		public int getTestID()
		{
			return iTestID;
		}
		/**
		 * @Set the IntegerValue
		 */
		public void setTestID(int iTestID)
		{
			this.iTestID = iTestID;
		}
		
		/**
		 * @return the IntegerValue
		 */
		public int getCaseID()
		{
			return iCaseID;
		}
		/**
		 * @Set the IntegerValue
		 */
		public void setCaseID(int iCaseID)
		{
			this.iCaseID = iCaseID;
		}
		/**
		 * @return the IntegerValue
		 */
		public int getRunID()
		{
			return iRunID;
		}
		/**
		 * @Set the IntegerValue
		 */
		public void setRunID(int iRunID)
		{
			this.iRunID = iRunID;
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
