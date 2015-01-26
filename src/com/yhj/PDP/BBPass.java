package com.yhj.PDP;

import java.io.*;
import java.util.*;
import java.sql.*;

/* Simple class for authenticating incoming clients
*/

public class BBPass {

	String mysqlURL="";


	public BBPass(String mysqlURL) {
 
		this.mysqlURL=mysqlURL;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
    		} catch (Exception e) {
        		System.out.println("BBSQL: JDBC exception");
        		System.exit(1);
    		}
   	}


	public String checkPass(String loginString) {

		String [] logA = new String[2];
		int i=0;

		StringTokenizer st = new StringTokenizer(loginString,":");

		while (st.hasMoreTokens()) {

			logA[i]=st.nextToken();
			i++;

		}

		String user = logA[0];
		String pass = logA[1];


		String verified = checkDB(user,pass);
		return verified;


	}


	public String checkDB(String user, String pass) {



    		try {

            		Connection conn;
            		conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB","root","111111");
            		Statement stmt1 = conn.createStatement();

//            		ResultSet res1 = stmt1.executeQuery("select count(*) from passwords where sla_id="+user+" AND password='"+pass+"'");
			ResultSet res1 = stmt1.executeQuery("select password from passwords where sla_id="+user);
			String thePass="";

            		while (res1.next()) {
            			thePass = res1.getString("password");


				}
				
			if (pass.equals(thePass)) return "Login success.";

            		else return "Login failed.Restart client.";

        	} catch(SQLException ex) {
            		System.err.println("SQLException: " + ex.getMessage());
        	}

    		return "Should not get to this point!";

	}


}
