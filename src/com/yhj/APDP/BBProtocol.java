package com.yhj.APDP;


import java.io.*;
import java.sql.*;
import java.util.*;
import java.net.*;
import com.yhj.APDP.cops.*;
import com.yhj.APDP.cops.pr.*;
import com.yhj.APDP.cops.messages.*;
import com.yhj.APDP.cops.messages.pr.*;
import com.yhj.APDP.cops.utils.*;
import com.yhj.APDP.cops.objects.*;
import com.yhj.APDP.cops.objects.pr.*;

/* Class which examines incoming client messages and sends them 
    away to be processed.
*/


public class BBProtocol {

	private static final int WELCOME = 0;
	private static final int SENTSLA = 1;
	private static final int MAIN = 2;
	private static final int RESTART = 3;


	private int sla_id = 0;


	private int state = MAIN;
	private String mysqlURL = "";



	public BBProtocol (String mysqlURL) {
		this.mysqlURL = mysqlURL;
	}

	// Method essentially remembers the sla ID for the rest of the session

	public void setSLA(String loginString) {

		String [] logA = new String[2];
		int i=0;

		StringTokenizer st = new StringTokenizer(loginString,":");

		while (st.hasMoreTokens()) {

			logA[i]=st.nextToken();
			i++;

		}
	
		String slaS = logA[0];

		try {
			sla_id = Integer.parseInt(slaS);
		} catch (NumberFormatException e) {}


	}


	public String processInput(CopsprPdpImpl pdp,Socket[] pepSockets,String theInput,Socket[] peerBB,int[] peerID,int bbID,String[] statusList,String currentDomain) throws IOException {

    		int inputLength = theInput.length();

		// Handle resource requests

    		if (inputLength > 10 && theInput.substring(0,10).equalsIgnoreCase("request BW")) {

       			String params = theInput.trim();
        		 	
        		RARreq reqRAR = new RARreq(mysqlURL);
        		return reqRAR.reqBW(pdp,pepSockets,params,peerBB,peerID,bbID,statusList,currentDomain);


       		}

		// Handle requests for SLA details

    		else if (inputLength > 7 && theInput.substring(0,8).equalsIgnoreCase("SLA info")) {

              		updateDB si = new updateDB(mysqlURL);
			
			try {
				sla_id = Integer.parseInt(theInput.substring(9).trim());
			}
			catch (NumberFormatException e) {}

			return si.slaInfo(sla_id) + ". " + si.showRAR(sla_id);


       		}

		// Handle requests for RAR details

		else if (inputLength > 7 && theInput.substring(0,8).equalsIgnoreCase("RAR info")) {

			int i=0;
    			String [] paramsA = new String[7];
    			StringTokenizer token = new StringTokenizer(theInput,";");

    			while (token.hasMoreTokens()) {


				paramsA[i]=token.nextToken();
				i++;


			}
			int rarid = 0;
			try {
				sla_id = Integer.parseInt(paramsA[1]);
				rarid = Integer.parseInt(paramsA[2]);
			} catch (NumberFormatException e) {
				return "RAR INPUT ERROR";
			}

        		updateDB ri = new updateDB(mysqlURL);
        		return ri.rarInfo(rarid,sla_id);
      		}

		// Handle request for RAR deletion

    		else if (inputLength > 10 && theInput.substring(0,10).equalsIgnoreCase("delete RAR")) {

			int i=0;
    			String [] paramsA = new String[7];
    			StringTokenizer token = new StringTokenizer(theInput,";");

    			while (token.hasMoreTokens()) {


				paramsA[i]=token.nextToken();
				i++;


			}
        		int delRAR = Integer.parseInt(paramsA[2]);
			sla_id = Integer.parseInt(paramsA[1]);
        		RARdel deleteRAR = new RARdel(mysqlURL);
        		return deleteRAR.deleteRAR(pdp,pepSockets,delRAR,sla_id,peerBB,peerID,bbID,statusList,currentDomain);

       		}
       	
       		// Handle request for SLA modification. (Admin Only)

       		else if (inputLength> 10 && theInput.substring(0,10).equalsIgnoreCase("modify SLA")) {


       			updateDB mod = new updateDB(mysqlURL);
       			return mod.modSLA(theInput);

       		}

		// Handle a request to modify a RAR

       		else if (inputLength> 10 && theInput.substring(0,10).equalsIgnoreCase("modify RAR")) {

       			
			RARmod modR = new RARmod(mysqlURL);
			return modR.modRAR(theInput,currentDomain,peerBB,peerID,bbID,statusList);

    	   	}


		// The unlikely event where the client sends an invalid request command. 
	
    		else {
    			System.out.println("Invalid Input : " + theInput);
    			return "Invalid Input";
		}


    }






}



