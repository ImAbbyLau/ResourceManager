package com.yhj.PDP;

import java.io.*;
import java.sql.*;
import java.net.*;
import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.pr.*;
import com.yhj.PDP.cops.messages.*;
import com.yhj.PDP.cops.messages.pr.*;
import com.yhj.PDP.cops.utils.*;
import com.yhj.PDP.cops.objects.*;
import com.yhj.PDP.cops.objects.pr.*;
import com.yhj.PDP.pib.*;
import com.yhj.PDP.pib.test.*;

/* A class that starts a timed automatic thread to periodically check 
 if RAR's are expired and consequently delete them
 */

public class autoDelete extends Thread {

	private String mysqlURL = "localhost";
	CopsprPdpImpl pdp;
	Socket[] pepSockets;
	Socket[] peerBB;
	int[] peerID;
	int bbID;
	String[] statusList;
	String currentDomain;

	public autoDelete(CopsprPdpImpl pdp, Socket[] pepSockets, String mysqlURL,
			int bbID) {

		this.mysqlURL = mysqlURL;
		this.pdp = pdp;
		this.statusList = statusList;
		this.peerBB = peerBB;
		this.peerID = peerID;
		this.currentDomain = currentDomain;
		this.bbID = bbID;
		this.pepSockets = pepSockets;
	}

	public void run() {

		PurgeDB pdb = new PurgeDB(pdp, pepSockets, mysqlURL, peerBB, peerID,
				bbID, statusList, currentDomain);

		while (true) {
			try {
				// This interval can be changed to suit user requirements
				sleep(10000);

				pdb.purgeRAR();

			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}

}
