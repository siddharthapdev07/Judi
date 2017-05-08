package com.agm.framework.FunctionLibraries;

import java.sql.Connection;

public class DB {
	
public Connection objConnDB = null; 
	
	private static DB objDB = null;
	private DB(){}
	public static DB getInstance(){
		if(objDB==null)
			objDB = new DB();
		return objDB;
	}	
	
	public void fun1(){
		
	}
	public void fun2(){
		
	}
	public void fun3(){
		
	}
}
