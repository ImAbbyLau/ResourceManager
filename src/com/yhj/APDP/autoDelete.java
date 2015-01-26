package com.yhj.APDP;

import java.io.*;
import java.sql.*;
import java.net.*;
import com.yhj.APDP.cops.*;
import com.yhj.APDP.cops.pr.*;
import com.yhj.APDP.cops.messages.*;
import com.yhj.APDP.cops.messages.pr.*;
import com.yhj.APDP.cops.utils.*;
import com.yhj.APDP.cops.objects.*;
import com.yhj.APDP.cops.objects.pr.*;
import com.yhj.APDP.pib.*;
import com.yhj.APDP.pib.test.*;


/* A class that starts a timed automatic thread to periodically check 
   if RAR's are expired and consequently delete them
*/

public class autoDelete extends Thread {


	private String mysqlURL="localhost";
	CopsprPdpImpl pdp;
	Socket[] pepSockets;
	Socket[] peerBB;
	int[] peerID;
	int bbID;
	String[] statusList;
	String currentDomain;
	
	public autoDelete(CopsprPdpImpl pdp,Socket[] pepSockets,String mysqlURL,Socket[] peerBB,int[] peerID,int bbID,String[] statusList,String currentDomain) {
		
		this.mysqlURL=mysqlURL;
		this.pdp=pdp;
		this.statusList=statusList;
		this.peerBB=peerBB;
		this.peerID=peerID;
		this.currentDomain=currentDomain;
		this.bbID=bbID;
		this.pepSockets=pepSockets;
	}


	public void run() {
		
		PurgeDB pdb = new PurgeDB(pdp,pepSockets,mysqlURL,peerBB,peerID,bbID,statusList,currentDomain);
		
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
