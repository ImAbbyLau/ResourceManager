package com.yhj.APDP;

import java.util.*;
import java.sql.*;
import java.io.*;

/* A simple route discovery class that depends on the
   network topology being stored in the MySQL database.
   Obviously this class can be modified or replaced if a 
   more advanced route discovery mechanism is so desired.
*/

public class matchNetwork {

	String mysqlURL="";

	public matchNetwork(String mysqlURL) {

		this.mysqlURL=mysqlURL;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
    		} catch (Exception e) {
        		System.out.println("BBSQL: JDBC exception");
        		System.exit(1);
    		}
	}

	// Method to find the next hop to a destination address from the current domain.

	String findDomain(String target,String thisDomain) {


		int numNeighbours=-1;

		try {

			Connection conn;
            		conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB","root","111111");
            		Statement stmt1 = conn.createStatement();

	    		ResultSet res = stmt1.executeQuery("select count(*) from Domains where Domain='" + thisDomain + "'");

	    		while (res.next()) {

            			numNeighbours = res.getInt("count(*)");
            			System.out.println("Current Neighbouring Servers : " + numNeighbours);
            		}

		} catch(SQLException ex) {
            		System.err.println("SQLException: " + ex.getMessage());
        	}

		
		String[] Neighbours = new String[numNeighbours];
		int[] correctMatch = new int[numNeighbours];

		try {
			Connection conn;
            		conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB","root","111111");
            		Statement stmt1 = conn.createStatement();

	    		ResultSet res = stmt1.executeQuery("select Neighbour from Domains where Domain='" + thisDomain + "'");
			int i = 0;
	    	
	    		// Retrieve this domain's neighbours
	    	
	    		while (res.next()) {

            			Neighbours[i] = res.getString("Neighbour");
            			i++;
            		}

		

			int matchCount = compareDomain(target,thisDomain);
			System.out.println("matchCount : " + matchCount);
			
			// If there is a A.B.C.x match, then the destination address is in this domain
			
			if (matchCount==3) {
				return "DST Domain";
			}

			for (int j=0;j<numNeighbours;j++) {

				correctMatch[j] = compareDomain(target,Neighbours[j]);

			}

			int match=0;
			int index=-1;

			for (int j=0;j<numNeighbours;j++) {
				if (correctMatch[j] > match) {
					match = correctMatch[j];
					index = j;
				}
			}


			// Neighbour is destination domain
			
			if (match==3) {
				return "interBB;"+Neighbours[index];
			}
			
			// Neighbour could be in same A.B.x.x domain segment
			
			if (match==2 && Neighbours.length>1) {
				
				int targ = intAddy(target);
				int dom = intAddy(thisDomain);
				
				int test=0;
				if (targ > dom) test=1;
				
				if (test==1) {
					for (int j=0;j<Neighbours.length;j++) {
						int temp = intAddy(Neighbours[j]);
						if (temp > dom) return "interBB;"+Neighbours[j];
					}
				}
				
				else {
					for (int j=0;j<Neighbours.length;j++) {
						int temp = intAddy(Neighbours[j]);
						if (temp < dom) return "interBB;"+Neighbours[j];
					}
				}
							
			}
			
			// If there is obvious domain matching, then send request to the default
			// gateway which will pass the request onto the logical neighbour
			
			if (match==0 || match==1) {
				
				String pepID="";
				String neigh="";
				String pepStat="";
				res = stmt1.executeQuery("select GatewayPEPID from BBPeer where Domain='"+thisDomain+"'");
				while (res.next()) {
					pepID=res.getString("GatewayPEPID");
				}
				res = stmt1.executeQuery("select Neighbour,status from PEP where pepID='"+pepID+"'");
				while (res.next()) {
					neigh=res.getString("Neighbour");
					pepStat=res.getString("status");
				}
				
				if (pepStat.equals("OFF")) { 
					return "GATEWAY_OFF";
				}
				
				else return "interBB;"+neigh;
				
			}

			if (index==-1) {
	
				return "NO_NEIGHBOUR";

			}
	
			else {
				return "interBB;" + Neighbours[index];
			}
		
		} catch(SQLException ex) {
            		System.err.println("SQLException: " + ex.getMessage());
        		return "ERROR";
        	}


	}

	int intAddy(String addy) {
	
		String modAddy="";
	
		for (int j=0;j<addy.length();j++) {
			if (addy.charAt(j)!='.') {
				modAddy+=addy.charAt(j);
			}
		}
		
		int ret = Integer.parseInt(modAddy);
		return ret;
		
	}

	String srcDomain(String src,String thisDomain) {


		//System.out.println("The source : " + src);
		//System.out.println("This domain : " + thisDomain);
		int matchCount = compareDomain(src,thisDomain);

		if (matchCount==3) {
			return "SRC Domain";
		}

		if (matchCount==2) {
			return "SUB Domain";
		}
		
		else return "NOT SRC";


	}

	String slaDomain(int sla,String currentDomain) {
		
		int numAllowed=-1;
		String[] allowedDomains=null;
		try {

			Connection conn;
            		conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB","root","111111");
            		Statement stmt1 = conn.createStatement();

	    		ResultSet res = stmt1.executeQuery("select count(*) from Allow where SLA=" + sla);

	    		while (res.next()) {

            			numAllowed=res.getInt("count(*)");
            			if(numAllowed<=0) {
            				return "SLA0";
            			}
            		}
        		
			allowedDomains = new String[numAllowed];
            		int counter=0;

            		res = stmt1.executeQuery("select * from Allow where SLA=" + sla);

			while (res.next()) {
            			String temp = res.getString("Domain");
            			if (currentDomain.equals(temp)) {
            				return "SLA1";
            			}
            			counter++;
            		}
            		
            		
            		

		} catch(SQLException ex) {
            		System.err.println("SQLException: " + ex.getMessage());
            		return "Database Error";
        	}
		return "SLA0";
	}

	int compareDomain(String target,String potentialDomain) {

		// A.B.C.D
		// match A = 1, A.B=2, A.B.C=3

		int match = 0;
		String [] domain = new String[4];
		String [] targetA = new String[4];
    		StringTokenizer token1 = new StringTokenizer(potentialDomain,".");
    		StringTokenizer token2 = new StringTokenizer(target,".");

		int i =0;

    		while (token2.hasMoreTokens()) {

			targetA[i]=token2.nextToken();

			i++;


		}

		i = 0 ;

		while (token1.hasMoreTokens()) {

			domain[i]=token1.nextToken();

			i++;


		}


		//System.out.println(targetA.length);

		for(int j=0;j<4;j++) {
			if(targetA[j].equals(domain[j])) {

				match++;
			}
		}

		//System.out.println(match);
 		return match;

	}

	// Ignore, only used for testing purposes.

	public static void main(String args[]) throws IOException {

		matchNetwork mN = new matchNetwork("localhost");
		System.out.println(mN.findDomain("129.94.232.22","67.167.1.0"));
	


	}

}
