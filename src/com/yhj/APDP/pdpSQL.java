package com.yhj.APDP;

import java.io.*;
import java.sql.*;
import java.util.*;



public class pdpSQL { 

	String mysqlURL="localhost";
	Connection conn=null;


	public pdpSQL(String mysqlURL) {

	
		this.mysqlURL=mysqlURL;
		
	
		try {
			Class.forName("org.gjt.mm.mysql.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://"+mysqlURL+"/PR","root","111111");
		} catch (Exception e) {
			System.out.println("BBSQL: JDBC exception" + e.getMessage());
			System.out.println("1111111111111111111111111111111111");
			System.exit(1);
		}
	
	}
 
	
	public int noPEPs () {
		
		int count=-1;
		
		try {

			
			Statement stmt1 = conn.createStatement();

			//stmt1.executeUpdate("insert into PEP values('" + pepID+ "','" + pepAddy + "'," + pepPort + ")");
			ResultSet res = stmt1.executeQuery("select count(*) from LAPEP");
			
			while (res.next()) {
				count=res.getInt("count(*)");
			}

        	} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			//System.out.println(mysqlURL);
			System.out.println("2222222222");

        	}	
        	
        	return count;
		
	}


	public int getPort(int bbID) {
	
		int port = 0;
		
		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://"+mysqlURL+"/PR","root","111111");
			Statement stmt1 = conn.createStatement();

			//stmt1.executeUpdate("insert into PEP values('" + pepID+ "','" + pepAddy + "'," + pepPort + ")");
			ResultSet res = stmt1.executeQuery("select pdpPort from LPDP where PDP_ID="+bbID);
			
			while(res.next()) {
				port=res.getInt("pdpPort");
			}		
				

        	} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());System.out.println("3333333");

        	}


		return port;

	}
	
	String getCurrentDomain(int bbID) {

		
		String dom = "";

		try {

            		Connection conn;
            		conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/PR","root","111111");
            		Statement stmt1 = conn.createStatement();
            		ResultSet res = stmt1.executeQuery("select Domain from LPDP where PDP_ID=" + bbID);
	    		while (res.next()) {
	    			dom=res.getString("Domain");
	    			System.out.println("PDP#" + bbID + " controls " + dom);
            		}

            	} catch(SQLException ex) {
            		System.err.println("SQLException: " + ex.getMessage());
        	}

        	
        	return dom;


	}


}