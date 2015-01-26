package com.yhj.PDP;

import java.io.*;
import java.sql.*;
import java.net.*;
import java.util.*;
import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.pr.*;
import com.yhj.PDP.cops.messages.*;
import com.yhj.PDP.cops.messages.pr.*;
import com.yhj.PDP.cops.utils.*;
import com.yhj.PDP.cops.objects.*;
import com.yhj.PDP.cops.objects.pr.*;
import com.yhj.PDP.pib.*;
import com.yhj.PDP.pib.test.*;

/* This class handles modification of resource requests. 
   It caters for intra and inter-domain requests
   
   NOTE : Modifying a RAR does not entitle the user to modify
   the source/destination of the traffic flow. In the case where the 
   source/destination needs to be modified, a new request is required.
   
*/

public class RARmod {

	String mysqlURL = "dot.cse.unsw.edu.au";
        RARcops rc;

	public RARmod(String mysqlURL) {
		this.mysqlURL = mysqlURL;
		RARcops rc = new RARcops(mysqlURL);
		this.rc=rc;
	}

	public String modRAR(String params,String currentDomain,Socket[] peerBB,int[] peerID,int bbID,String[] statusList) {

		updateDB udb = new updateDB(mysqlURL);
		int i=0;

		// Extracting parameters from client request string

		String [] paramsA = new String[10];
		StringTokenizer token = new StringTokenizer(params,";");

		while (token.hasMoreTokens()) {

			paramsA[i]=token.nextToken();
			i++;
		}

	
		int slaID = udb.CheckNumberFormat(paramsA[1]);
		if (slaID < 0 ) return "SLA ID INPUT ERROR";

		String rar_ID = paramsA[2];
	

		int rarID = udb.CheckNumberFormat(rar_ID);
		if (rarID < 0 ) return "RAR ID INPUT ERROR";

		String rar_sd = paramsA[3];
		String rar_st = paramsA[4];
		String rar_ed = paramsA[5];
		String rar_et = paramsA[6];
		String rar_bw = paramsA[7];

	
		int rar_newbw = udb.CheckNumberFormat(rar_bw);
		if (rar_newbw < 0 ) return "NEW BW INPUT ERROR";

		String sla_sd="";
		String sla_st="";
		String sla_ed="";
		String sla_et="";
		int sla_availbw=0;
		int rar_oldbw=0;
		int sla_newbw=0;

		String cd = "";
		String dst = "";
		String src = "";
		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB");
			Statement stmt1 = conn.createStatement();
			Statement stmt2 = conn.createStatement();

			ResultSet res_RAR = stmt1.executeQuery ("select * from RAR where rar_id="+rarID);

			while (res_RAR.next()) {

				rar_oldbw=res_RAR.getInt("givenBW");
				src = res_RAR.getString("source");
				dst = res_RAR.getString("destination");

			}


			ResultSet res_SLA = stmt2.executeQuery ("select *,current_date() from SLA where sla_id="+slaID);

			while (res_SLA.next()) {
				sla_availbw = res_SLA.getInt("AvailBW");
				sla_sd=res_SLA.getString("startdate");
				sla_ed=res_SLA.getString("enddate");
				sla_st=res_SLA.getString("starttime");
				sla_et=res_SLA.getString("endtime");
				cd=res_SLA.getString("current_date()");
			}

			if (rar_newbw > (sla_availbw + rar_oldbw)) {

			try {
				String now = new java.util.Date().toString();
				PrintWriter log = new PrintWriter(new FileWriter("./logs/BBLog.txt",true));
				log.println(now + " - SLA : " + slaID + " failed to modify RAR: " + rarID + ". Insufficient Bandwidth Allocation");
				log.close();

			} catch(IOException ex) {
            			System.err.println("IOException: " + ex.getMessage());
   			}

        		return "Requested Rate of " + rar_newbw + " is above the limit. A maximum bandwidth request of " + (sla_availbw + rar_oldbw) + " is allowable. Request Rejected.";
			}


			mysqlDate msd = new mysqlDate();


			if(msd.compareDate(rar_ed,rar_sd,rar_et,rar_st)) {

				return "Input ERROR. RAR cannot end before RAR start date.";

			}


			if(msd.compareDate(rar_sd,sla_sd,rar_st,sla_st)) {

				return "Invalid modification. RAR cannot begin before SLA start date (" + sla_sd + " " + sla_st + ")";

			}


			if(msd.compareDate(sla_ed,rar_ed,sla_et,rar_et)) {

				return "Invalid modification. RAR cannot end after SLA end date (" + sla_ed + " " + sla_et + ")";

			}

			if(msd.compareDate(sla_ed,rar_sd,sla_et,rar_st)) {

				return "Invalid modification. RAR cannot start after SLA end date (" + sla_ed + " " + sla_et + ")";

			}

			sla_newbw = (sla_availbw + rar_oldbw) - rar_newbw;

			matchNetwork mN = new matchNetwork(mysqlURL);
			String checkDestination = mN.findDomain(dst,currentDomain);
			String theSRC = mN.srcDomain(src,currentDomain);

			if (checkDestination.equals("DST Domain") && theSRC.equals("SRC Domain")) {


				ResultSet cap = stmt2.executeQuery("select * from Capacity where Domain ='"+currentDomain+"'");
				int availBW=0;
				while(cap.next()){
					availBW = cap.getInt("availCapacity");
				}

				if ( (availBW + rar_oldbw - rar_newbw) < 0 ) {
					return "Request failed.";
				}

				stmt1.executeUpdate("update Capacity set availCapacity=availCapacity + "+rar_oldbw+" - " +rar_newbw+  " where Domain='"+currentDomain+"'");

				stmt1.executeUpdate("update Flows set bandwidth="+rar_newbw+",starttime='"+rar_st+"',startdate='"+rar_sd+"',endtime='"+rar_et+"',enddate='"+rar_ed+"' where Domain='"+currentDomain+"'");
				udb.updateSLA(slaID,sla_availbw,sla_newbw);
				udb.updateRAR(rarID,rar_sd,rar_st,rar_ed,rar_et,rar_newbw);
				return "The request is successful. RAR ID  : " + rarID + " has been changed. It is valid from " + rar_st + " " + rar_sd + " until " + rar_et + " " + rar_ed + " with an allocated bandwidth of " + rar_newbw;



			}
			
			if (checkDestination.equals("DST Domain") && !theSRC.equals("SRC Domain")) {


				ResultSet cap = stmt2.executeQuery("select * from Capacity where Domain ='"+currentDomain+"'");
				int availBW=0;
				while(cap.next()){
					availBW = cap.getInt("availCapacity");
				}

				if ( (availBW + rar_oldbw - rar_newbw) < 0 ) {
					return "Request failed.";
				}

				stmt1.executeUpdate("update Capacity set availCapacity=availCapacity + "+rar_oldbw+" - " +rar_newbw+  " where Domain='"+currentDomain+"'");

				stmt1.executeUpdate("update Flows set bandwidth="+rar_newbw+",starttime='"+rar_st+"',startdate='"+rar_sd+"',endtime='"+rar_et+"',enddate='"+rar_ed+"' where Domain='"+currentDomain+"'");

				return "Request Successful";

			}

			String transitRet = "";
			if (checkDestination.substring(0,7).equals("interBB")) {
				System.out.println("Contacting Peer BB");
				SIBB_REQ sibbreq = new SIBB_REQ(mysqlURL);

				transitRet = sibbreq.modRAR(currentDomain,checkDestination.substring(8),peerBB,peerID,bbID,statusList,slaID,rarID,rar_sd,rar_st,rar_ed,rar_et,rar_newbw,src,dst);
				if (transitRet.substring(0,18).equalsIgnoreCase("Request Successful")) {

					ResultSet cap = stmt2.executeQuery("select * from Capacity where Domain ='"+currentDomain+"'");
					int availBW=0;
					while(cap.next()){
						availBW = cap.getInt("availCapacity");
					}

					if ( (availBW + rar_oldbw - rar_newbw) < 0 ) {
						return "Request failed.";
					}

					stmt1.executeUpdate("update Capacity set availCapacity=availCapacity + "+rar_oldbw+" - " +rar_newbw+  " where Domain='"+currentDomain+"'");

					stmt1.executeUpdate("update Flows set bandwidth="+rar_newbw+",starttime='"+rar_st+"',startdate='"+rar_sd+"',endtime='"+rar_et+"',enddate='"+rar_ed+"' where Domain='"+currentDomain+"'");

				}

			}
			System.out.println(transitRet);
			
			if (theSRC.equals("SRC Domain") && transitRet.substring(0,18).equals("Request Successful")) {

				udb.updateSLA(slaID,sla_availbw,sla_newbw);
				udb.updateRAR(rarID,rar_sd,rar_st,rar_ed,rar_et,rar_newbw);
				return "The request is successful. RAR ID  : " + rarID + " has been changed. It is valid from " + rar_st + " " + rar_sd + " until " + rar_et + " " + rar_ed + " with an allocated bandwidth of " + rar_newbw;


			}
			
			else if(theSRC.equals("SRC Domain") && transitRet.equals("Request Failed")) {
				return "Request Failed";
			}

			return transitRet;

			
		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			return "Action not performed. Database error : " + ex;
		}






	}


}