package com.yhj.APDP;

import java.net.*;
import java.util.*;
import java.io.*;
import java.sql.*;


public class SIBB_REQ {

	String mysqlURL;

	public SIBB_REQ(String mysqlURL) {

		this.mysqlURL=mysqlURL;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
    		} catch (Exception e) {
        		System.out.println("BBSQL: JDBC exception");
        		System.exit(1);
    		}


	}


	/* Following method is used to connect to the necessary peer BB and make an
	   inter-domain request. 
	*/

	public String connectPeer(String senderDomain,String dstDomain,Socket[] peerBB,String[] statusList,int sla,String startdate,String starttime,String enddate,String endtime,int requestedBW,String src,String dst)  {

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

		// Check if the required peer BB is actually online. 

		if (statusList[(bbID - 1)].equals("OFF")) {
			return "Request Failed. Peer BB for " + dstDomain + " is not online.";
		}

		try {
			System.out.println("Getting I/O Stream from BB : " + bbID);
			System.out.println("Status of BB : " + bbID + " = " + statusList[(bbID-1)]);

			PrintWriter out = new PrintWriter(peerBB[(bbID-1)].getOutputStream(), true);
			System.out.println("output made");
			BufferedReader in = new BufferedReader(new InputStreamReader(peerBB[(bbID-1)].getInputStream()));
			System.out.println("input made");
			out.println("request bw;"+sla+";"+startdate+";"+starttime+";"+enddate+";"+endtime+";"+requestedBW+";"+src+";"+dst);
			output = in.readLine();
			System.out.println("string sent");
		} catch(IOException e) {

			return "Request Failed. I/O Error";

		}
		return output;

	}

	// Similar to the connectPeer method, but this is tailored specifically for RAR modification

	public String modRAR(String senderDomain,String dstDomain,Socket[] peerBB,int[] peerID,int bbID,String[] statusList,int sla,int rar,String startdate,String starttime,String enddate,String endtime,int requestedBW,String src,String dst)  {

		

		String output="";

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
			PrintWriter out = new PrintWriter(peerBB[(bbID-1)].getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(peerBB[(bbID-1)].getInputStream()));
			out.println("modify RAR;"+sla+";"+rar+";"+startdate+";"+starttime+";"+enddate+";"+endtime+";"+requestedBW);
			output = in.readLine();
		} catch(IOException e) {

			return "Request Failed. I/O Error";

		}
		return output;


	}


}
