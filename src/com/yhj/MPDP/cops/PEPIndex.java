package com.yhj.MPDP.cops;

import java.io.*;
import java.sql.*;
import java.util.*;



public class PEPIndex { 

	String mysqlURL="localhost";

	public PEPIndex() {
	
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println("BBSQL: JDBC exception");
			System.exit(1);
		}
	
	}

	int Mstatus(String pdpID,String mysqlURL) {
		
		int sindex = -1;
	
		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB","root","111111");
			Statement stmt1 = conn.createStatement();

			//stmt1.executeUpdate("insert into PEP values('" + pepID+ "','" + pepAddy + "'," + pepPort + ")");
			ResultSet res = stmt1.executeQuery("select sindex from LPDP where LPDPID='" + pdpID + "'");
			
			while(res.next()) {
				sindex=res.getInt("sindex");
			}		
				

        	} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());

        	}


		return sindex;

	}

	int status(String pepID,String mysqlURL) {
	
		int sindex = -1;
	
		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB","root","111111");
			Statement stmt1 = conn.createStatement();

			//stmt1.executeUpdate("insert into PEP values('" + pepID+ "','" + pepAddy + "'," + pepPort + ")");
			ResultSet res = stmt1.executeQuery("select sindex from PEP where pepID='" + pepID + "'");
			
			while(res.next()) {
				sindex=res.getInt("sindex");
			}	

        	} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());

        	}


		return sindex;

	}

}
