package com.yhj.APDP;

import java.net.*;
import java.util.*;
import java.io.*;
import java.sql.*;


public class SIBB_DEL {

	String mysqlURL;

	public SIBB_DEL(String mysqlURL) {

		this.mysqlURL=mysqlURL;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
    		} catch (Exception e) {
        		System.out.println("BBSQL: JDBC exception");
        		System.exit(1);
    		}


	}


	public String connectPeer(String senderDomain,String dstDomain,Socket[] peerBB,String[] statusList,int sla,int rar,String src,String dst)  {




		String output="";
		int bbID=-1;
		System.out.println(dstDomain);

		try{
			Connection conn;
            		conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB","root","111111");
            		Statement stmt1 = conn.createStatement();

	    		ResultSet res = stmt1.executeQuery("select BB_ID from BBPeer where Domain='" + dstDomain + "'");


			while(res.next()) {
				bbID=res.getInt("BB_ID");
			}


		} catch (SQLException e) {

		}

		if (statusList[(bbID - 1)].equals("OFF")) {
			return "Request Failed. Peer BB for " + dstDomain + " is not online.";
		}

		try {
			System.out.println("Getting I/O Stream from BB : " + bbID);
			System.out.println("Status of BB : " + bbID + " = " + statusList[(bbID-1)]);
			//System.out.println(peerBB.length);
			//System.out.println(peerBB[(bbID-1)].getInetAddress());
			PrintWriter out = new PrintWriter(peerBB[(bbID-1)].getOutputStream(), true);
			//System.out.println("Outstream found");
			BufferedReader in = new BufferedReader(new InputStreamReader(peerBB[(bbID-1)].getInputStream()));
			//out.println("0:BBPeer");
			//in.readLine();
			out.println("delete RAR;"+sla+";"+rar);
			output = in.readLine();
		} catch(IOException e) {

			return "Request Failed. I/O Error";

		}
		//System.out.println("THE OUTPUT : " + output);
		return output;


	}


}
