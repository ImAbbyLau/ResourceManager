package com.yhj.APDP;

import java.io.*;
import java.sql.*;
import java.net.*;
import java.util.*;
import com.yhj.APDP.cops.*;
import com.yhj.APDP.cops.pr.*;
import com.yhj.APDP.cops.messages.*;
import com.yhj.APDP.cops.messages.pr.*;
import com.yhj.APDP.cops.utils.*;
import com.yhj.APDP.cops.objects.*;
import com.yhj.APDP.cops.objects.pr.*;
import com.yhj.APDP.pib.*;
import com.yhj.APDP.pib.test.*;

/* This class handles requests for resources. 
   It caters for intra and inter-domain requests
*/

public class RARreq {

	String mysqlURL = "dot.cse.unsw.edu.au";
        RARcops rc;

	public RARreq(String mysqlURL) {
		this.mysqlURL = mysqlURL;
		RARcops rc = new RARcops(mysqlURL);
		this.rc=rc;
	}



	public String reqBW(CopsprPdpImpl pdp,Socket[] pepSockets,String params,Socket[] peerBB,int[] peerID,int bbID,String[] statusList,String currentDomain) throws IOException {


		int bwOld = 0;
		int sla=-1;
		int i=0;
		updateDB udb = new updateDB(mysqlURL);
		String [] reqArray = new String[11];

		// Following code retrieves the paramets for the request

		StringTokenizer st = new StringTokenizer(params,";");

		while (st.hasMoreTokens()) {
			reqArray[i]=st.nextToken();
			i++;
		}

		String currentdate = "";

		sla = udb.CheckNumberFormat(reqArray[1]);
		if (sla < 0 ) return "SLA INPUT ERROR";

		String startdate = reqArray[2];

		String req_starttime = reqArray[3];

		String enddate = reqArray[4];

		String req_endtime = reqArray[5];
		String bw = reqArray[6];
		String src = reqArray[7];
		String dst = reqArray[8];


		int requestedBW = udb.CheckNumberFormat(bw);
		if (requestedBW < 0 ) return "REQUESTED BW INPUT ERROR";

		String slaStart="";
		String slaEnd="";
		String sla_stime="";
		String sla_etime="";
		String service_type="";
		String dscp="";

		System.out.println(src);


		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB","root","111111");
			Statement stmt1 = conn.createStatement();
			Statement stmt2 = conn.createStatement();

			ResultSet res_SLA = stmt2.executeQuery ("select *,current_date() from SLA where sla_id="+sla);

			while (res_SLA.next()) {
				bwOld = res_SLA.getInt("AvailBW");
				slaStart=res_SLA.getString("startdate");
				slaEnd=res_SLA.getString("enddate");
				sla_stime=res_SLA.getString("starttime");
				sla_etime=res_SLA.getString("endtime");
				service_type=res_SLA.getString("service_type");
				currentdate=res_SLA.getString("current_date()");
			}

			// Check if the SLA is entitled to the amount of bandwidth requested 

			if (requestedBW > bwOld) {
				try {
					String now = new java.util.Date().toString();
					PrintWriter log = new PrintWriter(new FileWriter("./logs/BBLog.txt",true));
					log.println(now + " - SLA : " + sla + " attempted to request more bandwidth that it is allocated.");
					log.close();
				} catch(IOException ex) {
					System.err.println("IOException: " + ex.getMessage());
				}
				return "Requested Rate of " + requestedBW + " is above the limit. A maximum bandwidth request of " + bwOld + " is allowable. Request Rejected.";
			}

			// Check if the current domain can handle the bandwidth to be requested

			ResultSet checkDomain = stmt2.executeQuery ("select * from Capacity where Domain='" + currentDomain + "'");

			int availCapacity=0;
			int totalCapacity=0;
			while (checkDomain.next()) {
				totalCapacity = checkDomain.getInt("Capacity");
				availCapacity = checkDomain.getInt("availCapacity");
			}

			if (requestedBW > availCapacity) {
				try {
					String now = new java.util.Date().toString();
					PrintWriter log = new PrintWriter(new FileWriter("./logs/BBLog.txt",true));
					log.println(now + " - SLA : " + sla + " attempted to request more bandwidth than the " + currentDomain + " can handle.");
					log.close();
				} catch(IOException ex) {
					System.err.println("IOException: " + ex.getMessage());
				}
				return "Request Failed     . Requested Rate of " + requestedBW + " is above the Domain's capacity. Maximum available capacity is " + availCapacity;
			}


			ResultSet res_codepoint = stmt1.executeQuery ("select dscp from codepoint where service_type='" + service_type + "'");
			while (res_codepoint.next()) {
				dscp = res_codepoint.getString("dscp");
			}

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			return "Action not performed. Database error : " + ex;
		}


		System.out.println("The current domain : " + currentDomain + " can handle the requested rate of " + requestedBW);



        // Following lines of code determines if requested RAR falls within SLA time limits


		mysqlDate msd = new mysqlDate();

        // rar start < sla start

		if(!msd.compareDate(slaStart,startdate,sla_stime,req_starttime)) {
			return "Invalid Request. RAR cannot start before SLA start date (" + slaStart + ").";
		}


        // rar start > sla end



		if(msd.compareDate(slaEnd,startdate,sla_etime,req_starttime)) {
			return "Invalid Request. RAR cannot start after SLA end date (" + slaEnd +").";
		}

        // rar end > sla end


		if(msd.compareDate(slaEnd,enddate,sla_etime,req_endtime)) {
			return "Invalid Request. RAR cannot end after SLA end date (" + slaEnd + ").";
		}

		if (msd.compareDate(enddate,startdate,req_endtime,req_starttime)) {

			return "Input ERROR. RAR cannot finish before request starts!";

		}
		
		/* Following code determines network information to find out where
		   requests are to be sent. The matchNetwork class is a basic class
		   which can be improved to incorporate a route discovery protocol.
		*/

		matchNetwork mN = new matchNetwork(mysqlURL);

		String checkSource = mN.slaDomain(sla,currentDomain);
		
		if (checkSource.equals("SLA0")) {
			return "Request Failed. SLA is NOT authorised for this domain";
		}
		if (checkSource.equals("Database Error")) {
			return "Request failed. Database Error";
		}

		// Find out where the destination of the requested flow is

		String checkDestination = mN.findDomain(dst,currentDomain);
		String theSRC = mN.srcDomain(src,currentDomain);
		
		if (checkDestination.equals("SQL_ERROR")) {
			return "Request Failed. Inter-Domain SQL Error.";
		}
		
		if (checkDestination.equals("DST Domain")) {
			System.out.println("Destination node is in this Domain");
			int newRAR=-1;
			try {
				Connection conn;
				conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB","root","111111");
				Statement stmt1 = conn.createStatement();
				int availCap = -1;
				ResultSet res = stmt1.executeQuery("select * from Capacity where Domain='"+currentDomain+"'");
				while (res.next()){
					availCap=res.getInt("availCapacity");
				}

				int newAvail = availCap - requestedBW;

				stmt1.executeUpdate("update Capacity set availCapacity="+ newAvail + " where Domain='"+currentDomain+"'");

				System.out.println("Capacity for " + currentDomain + " is now " + newAvail);
								
				System.out.println();
				
				
				int newBW = bwOld - requestedBW;
				newRAR = udb.insertRAR(sla,startdate,req_starttime,enddate,req_endtime,requestedBW,src,dst);
				udb.updateSLA(sla,bwOld,newBW);



				if (newRAR==-1) {
					return "Request failed. Database Error";
				}
				
				stmt1.executeUpdate("insert Flows values("+newRAR+",'"+currentDomain+"',"+requestedBW+",'"+req_starttime+"','"+startdate+"','"+req_endtime+"','"+enddate+"')");
			
							
				

			} catch(SQLException e) {
				System.out.println(e);
			}
			// Send new configuration data to relevent PEP in the current domain.
			rc.sendcops(pdp,pepSockets,currentDomain,(short) 1, src,dst);
			
			// For the case where the source & destination of the request are in the same domain
			
			if (theSRC.equals("SRC Domain")) {
				return "The Request is Successful. The new RAR has ID : " + newRAR + " and is valid from " + req_starttime + " "+ startdate + " until " + req_endtime + " " + enddate;
			}
			
			else return "Request Successful:"+newRAR;
		}

		if (checkDestination.equals("NO_NEIGHBOUR")) {
			return "Request Failed. No peer BB found for inter-domain request";
		}
		
		if (checkDestination.equals("GATEWAY_OFF")) {
			return "Request Failed. Routing Gateway is offline";
		}
		
		

		String transitRet ="";

		/* The following code implies that an inter-domain request has been made.
		   Therefore connections to peer BB's are required
		*/		

		if (checkDestination.substring(0,7).equals("interBB")) {
			System.out.println("Contacting Peer BB");
			SIBB_REQ sibbreq = new SIBB_REQ(mysqlURL);
			// Connect to peer BB
			transitRet = sibbreq.connectPeer(currentDomain,checkDestination.substring(8),peerBB,statusList,sla,startdate,req_starttime,enddate,req_endtime,requestedBW,src,dst);

			// If the peer BB responds that the inter-domain request has been successful

			if (transitRet.substring(0,18).equalsIgnoreCase("Request Successful")) {
				try {
					Connection conn;
					conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB","root","111111");
					Statement stmt1 = conn.createStatement();
					int availCap = -1;
					ResultSet res = stmt1.executeQuery("select * from Capacity where Domain='"+currentDomain+"'");
					while (res.next()){
						availCap=res.getInt("availCapacity");
					}

					int newAvail = availCap - requestedBW;

					stmt1.executeUpdate("update Capacity set availCapacity="+ newAvail + " where Domain='"+currentDomain+"'");

					System.out.println("Capacity for " + currentDomain + " is now " + newAvail);

					stmt1.executeUpdate("insert Flows values("+transitRet.substring(19)+",'"+currentDomain+"',"+requestedBW+",'"+req_starttime+"','"+startdate+"','"+req_endtime+"','"+enddate+"')");
					System.out.println("Traffic flow belonging to RAR : " + transitRet.substring(19) + " has been added to " + currentDomain);
					

				} catch(SQLException e) {
					System.out.println(e);
				}
				
				rc.sendcops(pdp,pepSockets,currentDomain,(short) 1,src,dst);
			}
			
		}




		
		
		//System.out.println(transitRet);
		if (theSRC.equals("SRC Domain") && transitRet.substring(0,18).equals("Request Successful")) {

			rc.sendcops(pdp,pepSockets,currentDomain,(short) 1,src,dst);
			return "The request is successful. The RAR ID for the new inter-domain flow is : " + transitRet.substring(19) + ". It is valid from " + req_starttime + " " + startdate + " until " + req_endtime + " " + enddate + ".";


		}

		else if(theSRC.equals("SRC Domain") && transitRet.equals("Request Failed")) {
			return "Request Failed";
		}

		else if(transitRet.length() > 14 && transitRet.substring(0,14).equals("Request Failed")) {
			return transitRet;
		}

		//return "TRANSIT";
		//else return "Request Successful";
		return transitRet;

	}
}


