package com.yhj.PDP;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.net.*;

/* This class interconnects peer BBs from different domains.
   At the moment it makes a connection to all online peer BB's
   regardless of the domain that they located. It can be modified to 
   only connect to neighbouring peer BBs. However, a BB will only
   communicate with a neighbouring BB whilst making inter-domain
   requests (as defined by SIBBs protocol).
*/

public class SIBBS_CONNECT {

	
	String mysqlURL = "localhost";
	
	public SIBBS_CONNECT(String mysqlURL) {

		PrintWriter out = null;
		BufferedReader in = null;
		this.mysqlURL = mysqlURL;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println("BBSQL: JDBC exception");
			System.exit(1);
		}

	}

	// Used to connect to all online peer BB when current BB is started.
	// Each element in the array corresponds to an establish connection from the
	// current BB to the peer BB. The index is the ((BB ID number) - 1) 
	// [As array index starts at 0]. For example, the socket to BB#5 will be located
	// at position 4 in the array. If the peer BB is not online, then that entry in
	// the array will simply be a null socket.

	Socket[] connectOnline (int bbID) throws IOException {

		PrintWriter out = null;
        	BufferedReader in = null;

		Socket[] sockets = null;
		String[] addyList = null;
		String[] status = null;
		int[] portList = null;
		int port=0;
		int numServers=0;
		int onlineServers=0;
		String BBLogin = "0:BBPeer";

		try {

            		Connection conn;
            		conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB");
            		Statement stmt1 = conn.createStatement();
		
	    		ResultSet res = stmt1.executeQuery("select count(*) from BBPeer");

	    		while (res.next()) {

            			numServers = res.getInt("count(*)");
            			
            		}

            		res = stmt1.executeQuery("select count(*) from BBPeer where status='ON' ORDER BY BB_ID ASC");
            		
            		while (res.next()) {
            			
            			onlineServers = res.getInt("count(*)");
            			
            		}

			
			System.out.println("Server knows about : " + numServers + " servers. Current Online Servers : " + onlineServers);
	    		System.out.println();
	    		addyList = new String[numServers];
	    		portList = new int[numServers];
			status = new String[numServers];
			
	    		res = stmt1.executeQuery("select * from BBPeer ORDER BY BB_ID ASC");

	    		int counter=0;

	    		while (res.next()) {
	    			int bb_id = res.getInt("BB_ID");
	    			String stat = res.getString("status");
				addyList[counter]=res.getString("Address");
	    			portList[counter]=res.getInt("Port");
	    			
	    			if (stat.equals("ON")) {
	    				status[counter] = "ON";
	    			}
	    			
	    			else {
	    				status[counter] = "OFF";
	    			}
	    			
				counter++;
	    		}
	    		
	    		System.out.println("Changing Status of Server #" + bbID + " to 'ON'");
	    		System.out.println();
	    		stmt1.executeUpdate("update BBPeer set status='ON' where BB_ID=" + bbID);

	    		ResultSet res2 = stmt1.executeQuery("select port from BBPeer where BB_ID=" + bbID);
	    		while (res2.next()) {
	    			port=res2.getInt("port");

            		}

		} catch(SQLException ex) {
            		System.err.println("SQLException: " + ex.getMessage());
            		System.out.println("Unable to carry out Action. Aborting.... Please restart");
            		System.exit(0);
              	}
        	

        	sockets = new Socket[numServers];
		

		for (int i=0;i<numServers;i++) {
				
			try {
        							
				if (status[i].equals("ON") && (bbID != i + 1)) {
					
					String tempAddy = addyList[i];
					int tempPort = portList[i];
					System.out.println("Connecting to Server : " + (i + 1));
				
           	 			sockets[i] = new Socket(tempAddy,tempPort);
           	 			sockets[i].setKeepAlive(true);
           	 		
           	 			out = new PrintWriter(sockets[i].getOutputStream(), true);
           	 			in = new BufferedReader(new InputStreamReader(sockets[i].getInputStream()));
           	 			out.println(BBLogin);
           	 			in.readLine();
           	 			//in.close();
           	 			//out.close();
           	 		
       				}
       			
       			} catch (ConnectException e) {
       				System.err.println("Cannot Connect to peer BB : " + e);
       			}

        	}
        	

		return sockets;

	}

	int[] getPeerID(int bbID) {
		
		int onlineServers=0;
		int[] peers=null;
		
		try {

            		Connection conn;
            		conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB");
            		Statement stmt1 = conn.createStatement();
		
	    		ResultSet res = stmt1.executeQuery("select count(*) from BBPeer where status='ON'");

	    		while (res.next()) {

            			onlineServers = res.getInt("count(*)");
            			
            		}
            		
            		peers= new int[onlineServers];
            		
            		res = stmt1.executeQuery("select * from BBPeer where status='ON'");

	    		int counter=0;

	    		while (res.next()) {
				peers[counter]=res.getInt("BB_ID");
				counter++;
	    		}
            		
            		} catch(SQLException ex) {
            		System.err.println("SQLException: " + ex.getMessage());
        		}
            		
			return peers;
		
	}

	
	// An array that stores information about the status of peer BBs. 
	// Used in conjunction with connectOnline and updatePeers to determine
	// which BB have come online since last status check.

	String[] statusList(int bbID) {
		
		String[] status = null;
		int numServers = 0 ;
		int onlineServers = 0;
		
		
		try {

            		Connection conn;
            		conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB");
            		Statement stmt1 = conn.createStatement();
		
	    		ResultSet res = stmt1.executeQuery("select count(*) from BBPeer");

	    		while (res.next()) {

            			numServers = res.getInt("count(*)");
            			
            		}
            		
            		res = stmt1.executeQuery("select count(*) from BBPeer where status='ON' ORDER BY BB_ID ASC");
            		
            		while (res.next()) {
            			
            			onlineServers = res.getInt("count(*)");
            			
            		}


			status = new String[numServers];

	    		res = stmt1.executeQuery("select * from BBPeer ORDER BY BB_ID ASC");

	    		int counter=0;

	    		while (res.next()) {
	    			int bb_id = res.getInt("BB_ID");
	    			String stat = res.getString("status");


	    			if (stat.equals("ON")) {
	    				status[counter] = "ON";
	    			}

	    			else {
	    				status[counter] = "OFF";
	    			}
	    			
				counter++;
	    		}


		} catch(SQLException ex) {
            		System.err.println("SQLException: " + ex.getMessage());
        	}

        	return status;


	}

	// Updates the connections to peer bandwidth brokers
	// If more servers have come online since the last check
	// then make a new connection to them


	Socket[] updatePeers(Socket[] knownPeers,int bbID,int[] peerID,String[] statusList) throws IOException {

		PrintWriter out = null;
        	BufferedReader in = null;
		String BBLogin = "0:BBPeer";

		try {

            		Connection conn;
            		conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB");
            		Statement stmt1 = conn.createStatement();

            		ResultSet res = stmt1.executeQuery("select * from BBPeer ORDER BY BB_ID ASC");
            		int counter=0;

            		while(res.next()) {

            			int bb_ID = res.getInt("BB_ID");
            			String stat = res.getString("status");
            			String addy = res.getString("Address");
            			int port = res.getInt("port");

            			
            			if(statusList[counter].equals("OFF") && stat.equals("ON") && bb_ID != bbID) {



            					
					try {
						knownPeers[counter] = new Socket(addy,port);
            					knownPeers[counter].setKeepAlive(true);
					
            					out = new PrintWriter(knownPeers[counter].getOutputStream(), true);
           	 				in = new BufferedReader(new InputStreamReader(knownPeers[counter].getInputStream()));
           	 				
           	 				out.println(BBLogin);
           	 				in.readLine();
           	 				
						System.out.println();
						//in.close();
						//out.close();
					}catch (ConnectException e) {
						System.out.println("Error : " + e.getMessage());
						System.out.println("Addr : " + addy);
						System.out.println("port : " + port);
						System.out.println("counter : " + counter);
						System.out.println("Stat[counter] : " + statusList[counter]);
						System.out.println("Stat : " + stat);
					}

            					            			}
            			
            			counter++;
            			
            		}
            	
            	     		
            	
            		
            	} catch(SQLException ex) {
            		System.err.println("SQLException: " + ex.getMessage());
        	}


		return knownPeers;
		
	}

	int getCurrentPort(int bbID) {

		int port=0;

		try {

            		Connection conn;
            		conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB","root","111111");
            		Statement stmt1 = conn.createStatement();
            		ResultSet res = stmt1.executeQuery("select port from BBPeer where BB_ID=" + bbID);
	    		while (res.next()) {
	    			port=res.getInt("port");

            		}

            	} catch(SQLException ex) {
            		System.err.println("SQLException: " + ex.getMessage());
        	}

//        	System.out.println("BB #" + bbID + " runs on port : " + port);

        	return port;


	}


	String getCurrentDomain(int bbID) {

		String dom="";

		try {

            		Connection conn;
            		conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB");
            		Statement stmt1 = conn.createStatement();
            		ResultSet res = stmt1.executeQuery("select Domain from BBPeer where BB_ID=" + bbID);
	    		while (res.next()) {
	    			dom=res.getString("Domain");

            		}

            	} catch(SQLException ex) {
            		System.err.println("SQLException: " + ex.getMessage());
        	}

        	System.out.println("BB #" + bbID + " controls " + dom);

        	return dom;


	}


}
