package com.yhj.MPDP;

import java.io.*;
import java.sql.*;
import java.util.*;

public class pdpSQL {

	String mysqlURL = "localhost";
	String UserName = "";
	String Password = "";

	public pdpSQL(String mysqlURL, String username, String password) {

		this.mysqlURL = mysqlURL;
		this.UserName = username;
		this.Password = password;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

		} catch (Exception e) {
			System.out.println("BBSQL: JDBC exception" + e.getMessage());
			System.out.println("1111111111111111111111111111111111");
			System.exit(1);
		}

	}

	public int noPEPs() {

		int count = -1;

		try {
			Connection conn;
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/CPR", UserName, Password);
			Statement stmt1 = conn.createStatement();

			// stmt1.executeUpdate("insert into PEP values('" + pepID+ "','" +
			// pepAddy + "'," + pepPort + ")");
			ResultSet res = stmt1.executeQuery("select count(*) from CPEP");

			while (res.next()) {
				count = res.getInt("count(*)");
			}

		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

		return count;

	}

	public int getPort(int bbID) {

		int port = 0;

		try {

			Connection conn;
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/CPR", UserName, Password);
			Statement stmt1 = conn.createStatement();

			// stmt1.executeUpdate("insert into PEP values('" + pepID+ "','" +
			// pepAddy + "'," + pepPort + ")");
			ResultSet res = stmt1
					.executeQuery("select pdpPort from CPDP where PDP_ID="
							+ bbID);

			while (res.next()) {
				port = res.getInt("pdpPort");
			}

		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			System.out.println("3333333");

		}

		return port;

	}

	String getCurrentDomain(int bbID) {

		String dom = "";

		try {

			Connection conn;
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/CPR", UserName, Password);
			Statement stmt1 = conn.createStatement();
			ResultSet res = stmt1
					.executeQuery("select Domain from CPDP where PDP_ID="
							+ bbID);
			// System.out.println();
			System.out.print("PDP#" + bbID + " controls ");
			while (res.next()) {
				dom = res.getString("Domain");
				System.out.print(dom + ",");
			}
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
		System.out.println();

		return dom;

	}

}
