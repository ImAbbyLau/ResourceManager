package com.yhj.PDP;

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
			System.out.println("BBSQL: JDBC exception");
			System.exit(1);
		}

	}

	public int noPEPs() {

		int count = -1;

		try {

			Connection conn;

			// conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL +
			// "/test_BB");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test_BB", UserName, Password);

			Statement stmt1 = conn.createStatement();

			// stmt1.executeUpdate("insert into PEP values('" + pepID+ "','" +
			// pepAddy + "'," + pepPort + ")");
			ResultSet res = stmt1.executeQuery("select count(*) from PEP");

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
			conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL
					+ "/PR", UserName, Password);
			Statement stmt1 = conn.createStatement();

			// stmt1.executeUpdate("insert into PEP values('" + pepID+ "','" +
			// pepAddy + "'," + pepPort + ")");
			ResultSet res = stmt1
					.executeQuery("select pdpPort from LPDP where PDP_ID="
							+ bbID);

			while (res.next()) {
				port = res.getInt("pdpPort");
			}

		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());

		}

		return port;

	}

}
