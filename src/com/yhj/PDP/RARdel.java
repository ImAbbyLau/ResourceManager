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

/* This class handles deletion of resource requests. 
   It caters for intra and inter-domain requests
*/

public class RARdel {

	String mysqlURL = "localhost";
        RARcops rc;
        
        
	public RARdel(String mysqlURL) {
		this.mysqlURL = mysqlURL;
		RARcops rc = new RARcops(mysqlURL);
		this.rc=rc;
	}
	
	
	public String deleteRAR(CopsprPdpImpl pdp,Socket[] pepSockets,int rarID,int sla,Socket[] peerBB,int[] peerID,int bbID,String[] statusList,String currentDomain) {

		int freeBW=0;
		int availBW=0;
		int newAvail=0;
		String dest = "";
		String src = "";
		matchNetwork mN = new matchNetwork(mysqlURL);

		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB");
			Statement stmt1 = conn.createStatement();
			Statement stmt2 = conn.createStatement();
			Statement stmt3 = conn.createStatement();
			Statement stmt1a = conn.createStatement();
			Statement stmt1x = conn.createStatement();

			ResultSet res1x = stmt1x.executeQuery("select count(*) from RAR where rar_id="+rarID+" AND sla_id="+sla);


			while (res1x.next()) {
				int counter = res1x.getInt("count(*)");
				if (counter==0) {
					try {
						String now = new java.util.Date().toString();
						PrintWriter log = new PrintWriter(new FileWriter("./logs/BBLog.txt",true));
						log.println(now + " - SLA : " + sla + " attempted to delete non-existent RAR ID : " + rarID);
						log.close();
					} catch(IOException ex) {
            				System.err.println("IOException: " + ex.getMessage());
				}

				return "REQUEST FAILED : RAR Does Not Exist. Type 'SLA Info' to find out current RAR's in use.";
			}
		}

		ResultSet res1 = stmt1.executeQuery("select givenBW,destination,source from RAR where rar_id="+rarID);

		while (res1.next()) {
			freeBW = res1.getInt("givenBW");
			dest = res1.getString("destination");
			src = res1.getString("source");
		}

		ResultSet res1a = stmt1a.executeQuery("select availBW from SLA where sla_id="+sla);

			while (res1a.next()) {
			availBW=res1a.getInt("availBW");
		}

		newAvail = freeBW + availBW;


		String checkDestination = mN.findDomain(dest,currentDomain);
		String theSRC = mN.srcDomain(src,currentDomain);
	
		if (checkDestination.equals("DST Domain") && !theSRC.equals("SRC Domain")) {

			stmt1x.executeUpdate("update Capacity set availCapacity=availCapacity+"+freeBW+" where Domain='"+currentDomain+"'");


			stmt1x.executeUpdate("delete from Flows where RAR="+rarID+" AND Domain='"+currentDomain+"'");

			rc.sendcops(pdp,pepSockets,currentDomain,(short) 2,src,dest);
			System.out.println("RAR " + rarID + " removed from destination domain.");
			return "RAR deleted";
		}
		
		if (checkDestination.equals("NO_NEIGHBOUR")) {
			return "Delete Failed. No peer BB found for inter-domain request";
		}
		
		if (checkDestination.equals("GATEWAY_OFF")) {
			return "Delete Failed. Routing Gateway is offline";
		}

		if (checkDestination.substring(0,7).equals("interBB")) {
			System.out.println("Contacting Peer BB");
			SIBB_DEL sibbdel = new SIBB_DEL(mysqlURL);
			sibbdel.connectPeer(currentDomain,checkDestination.substring(8),peerBB,statusList,sla,rarID,src,dest);
			
			System.out.println("Adding : " + freeBW + " to " + currentDomain);
			stmt1x.executeUpdate("update Capacity set availCapacity=availCapacity+"+freeBW+" where Domain='"+currentDomain+"'");
			stmt1x.executeUpdate("delete from Flows where RAR="+rarID+" AND Domain='"+currentDomain+"'");
			if (theSRC.equals("SRC Domain")) {
				System.out.println("RAR " + rarID + " removed from source domain : " + currentDomain);
			}
			else {			
				System.out.println("RAR " + rarID + " removed from transit domain : " + currentDomain);
			}
			rc.sendcops(pdp,pepSockets,currentDomain,(short) 2,src,dest);
		}

		if (theSRC.equals("SRC Domain")) {

			
			//stmt1x.executeUpdate("update Capacity set availCapacity=availCapacity+"+freeBW+" where Domain='"+currentDomain+"'");
			//stmt1x.executeUpdate("delete from Flows where RAR="+rarID+" AND Domain='"+currentDomain+"'");
			System.out.println("RAR " + rarID + " removed from the source domain.");
			
			rc.sendcops(pdp,pepSockets,currentDomain,(short) 2,src,dest);

			stmt1x.executeUpdate("delete from RAR where rar_id="+rarID);
			stmt1x.executeUpdate("update SLA set availBW="+newAvail+" where sla_id="+sla);

			System.out.println("Updated SLA ID = " + sla + ", " + freeBW + " added to available BW.");
			System.out.println("New available BW = " + newAvail);
			System.out.println("RAR ID: "+rarID +" successfully deleted.");

			return "RAR ID "+rarID+" deleted successfully. New available bandwidth is now " + newAvail;
		}

		else return "UNABLE TO DELETE!";

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			return "Action not performed. Database error : " + ex;
		}



	}


}
