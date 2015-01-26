package com.yhj.PDP;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.net.*;

import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.pr.*;
import com.yhj.PDP.cops.messages.*;
import com.yhj.PDP.cops.messages.pr.*;
import com.yhj.PDP.cops.utils.*;
import com.yhj.PDP.cops.objects.*;
import com.yhj.PDP.cops.objects.pr.*;

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

	CopsprREQ req;

	public BBProtocol(String mysqlURL) {
		this.mysqlURL = mysqlURL;
	}

	// Method essentially remembers the sla ID for the rest of the session

	public void setSLA(String loginString) {

		String[] logA = new String[2];
		int i = 0;

		StringTokenizer st = new StringTokenizer(loginString, ":");

		while (st.hasMoreTokens()) {

			logA[i] = st.nextToken();
			i++;

		}

		String slaS = logA[0];

		try {
			sla_id = Integer.parseInt(slaS);
		} catch (NumberFormatException e) {
		}

	}

	public String processInput(CopsprPdpImpl pdp, Socket[] pepSockets,
			String theInput, Socket[] peerBB, int[] peerID, int bbID,
			String[] statusList, String currentDomain) throws IOException {

		int inputLength = theInput.length();

		// Handle resource requests

		if (inputLength > 10
				&& theInput.substring(0, 10).equalsIgnoreCase("request BW")) {

			String params = theInput.trim();

			RARreq reqRAR = new RARreq(mysqlURL);
			return reqRAR.reqBW(pdp, pepSockets, params, peerBB, peerID, bbID,
					statusList, currentDomain);

		}

		// Handle requests for SLA details

		else if (inputLength > 7
				&& theInput.substring(0, 8).equalsIgnoreCase("SLA info")) {

			updateDB si = new updateDB(mysqlURL);

			try {
				sla_id = Integer.parseInt(theInput.substring(9).trim());
			} catch (NumberFormatException e) {
			}

			return si.slaInfo(sla_id) + ". " + si.showRAR(sla_id);

		}

		// Handle requests for RAR details

		else if (inputLength > 7
				&& theInput.substring(0, 8).equalsIgnoreCase("RAR info")) {

			int i = 0;
			String[] paramsA = new String[7];
			StringTokenizer token = new StringTokenizer(theInput, ";");

			while (token.hasMoreTokens()) {

				paramsA[i] = token.nextToken();
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
			return ri.rarInfo(rarid, sla_id);
		}

		// Handle request for RAR deletion

		else if (inputLength > 10
				&& theInput.substring(0, 10).equalsIgnoreCase("delete RAR")) {

			int i = 0;
			String[] paramsA = new String[7];
			StringTokenizer token = new StringTokenizer(theInput, ";");

			while (token.hasMoreTokens()) {

				paramsA[i] = token.nextToken();
				i++;

			}
			int delRAR = Integer.parseInt(paramsA[2]);
			sla_id = Integer.parseInt(paramsA[1]);
			RARdel deleteRAR = new RARdel(mysqlURL);
			return deleteRAR.deleteRAR(pdp, pepSockets, delRAR, sla_id, peerBB,
					peerID, bbID, statusList, currentDomain);

		}

		// Handle request for SLA modification. (Admin Only)

		else if (inputLength > 10
				&& theInput.substring(0, 10).equalsIgnoreCase("modify SLA")) {

			updateDB mod = new updateDB(mysqlURL);
			return mod.modSLA(theInput);

		}

		// Handle a request to modify a RAR

		else if (inputLength > 10
				&& theInput.substring(0, 10).equalsIgnoreCase("modify RAR")) {

			RARmod modR = new RARmod(mysqlURL);
			return modR.modRAR(theInput, currentDomain, peerBB, peerID, bbID,
					statusList);

		}

		// The unlikely event where the client sends an invalid request command.

		else {
			System.out.println("Invalid Input : " + theInput);
			return "Invalid Input";
		}

	}

	public String processInput(CopsprPdpImpl pdp, MPEPClient mpep,
			Socket[] pepSockets, String theInput, Socket[] peerBB,
			int[] peerID, int bbID, String[] statusList, String currentDomain)
			throws IOException {

		int inputLength = theInput.length();
		// Socket s = getSocket("localhost","localhost");
		// Handle resource requests

		if (inputLength > 10
				&& theInput.substring(0, 10).equalsIgnoreCase("request BW")) {

			String params = theInput.trim(); // BBClient's Request String
			try {
				System.out.println("Send BBClient's REQ To MPDP...");
				int echo = 0;
				mpep.sendConfigREQ(); // PDPServer以MPEP的角色将Bandwidth
										// Request发给MPDP，
				while (echo == 0) {
					echo = mpep.Decflag; // 等待CPDP返回DEC
				}
				if (echo == 1) {
					CopsDEC decs = mpep.DEC;
					mpep.Decflag = 0;
					System.out.println("MPEP received DEC From MPDP : " + decs);
					/*
					 * PDP should send message to PEP about the resource
					 * allocation strategy PDP should also send message to
					 * BBClient whether it can get the resource
					 */
					CopsCAT cat = new CopsCAT((short) 1, (short) 3);
					// send a CAT message instead to check the connection
					// between PDP and PEP
					pdp.sendMessage(mpep.DEC); // 转发来自MPDP的DEC
					// Socket ss = pdp.getSocket();
					// mpep.processDEC(decs, ss);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			RARreq reqRAR = new RARreq(mysqlURL);
			return reqRAR.reqBW(pdp, pepSockets, params, peerBB, peerID, bbID,
					statusList, currentDomain);

		}

		// Handle requests for SLA details

		else if (inputLength > 7
				&& theInput.substring(0, 8).equalsIgnoreCase("SLA info")) {

			updateDB si = new updateDB(mysqlURL);

			try {
				sla_id = Integer.parseInt(theInput.substring(9).trim());
			} catch (NumberFormatException e) {
			}

			return si.slaInfo(sla_id) + ". " + si.showRAR(sla_id);

		}

		// Handle requests for RAR details

		else if (inputLength > 7
				&& theInput.substring(0, 8).equalsIgnoreCase("RAR info")) {

			int i = 0;
			String[] paramsA = new String[7];
			StringTokenizer token = new StringTokenizer(theInput, ";");

			while (token.hasMoreTokens()) {

				paramsA[i] = token.nextToken();
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
			return ri.rarInfo(rarid, sla_id);
		}

		// Handle request for RAR deletion

		else if (inputLength > 10
				&& theInput.substring(0, 10).equalsIgnoreCase("delete RAR")) {

			int i = 0;
			String[] paramsA = new String[7];
			StringTokenizer token = new StringTokenizer(theInput, ";");

			while (token.hasMoreTokens()) {

				paramsA[i] = token.nextToken();
				i++;

			}
			int delRAR = Integer.parseInt(paramsA[2]);
			sla_id = Integer.parseInt(paramsA[1]);
			RARdel deleteRAR = new RARdel(mysqlURL);
			return deleteRAR.deleteRAR(pdp, pepSockets, delRAR, sla_id, peerBB,
					peerID, bbID, statusList, currentDomain);

		}

		// Handle request for SLA modification. (Admin Only)

		else if (inputLength > 10
				&& theInput.substring(0, 10).equalsIgnoreCase("modify SLA")) {

			updateDB mod = new updateDB(mysqlURL);
			return mod.modSLA(theInput);

		}

		// Handle a request to modify a RAR

		else if (inputLength > 10
				&& theInput.substring(0, 10).equalsIgnoreCase("modify RAR")) {

			RARmod modR = new RARmod(mysqlURL);
			return modR.modRAR(theInput, currentDomain, peerBB, peerID, bbID,
					statusList);

		}

		// The unlikely event where the client sends an invalid request command.

		else {
			System.out.println("Invalid Input : " + theInput);
			return "Invalid Input";
		}

	}

}
