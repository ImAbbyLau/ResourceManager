package com.yhj.APDP;

import java.net.*;
import java.io.*;
import java.util.*;
import com.yhj.APDP.cops.*;
import com.yhj.APDP.cops.pr.*;
import com.yhj.APDP.cops.messages.*;
import com.yhj.APDP.cops.messages.pr.*;
import com.yhj.APDP.cops.utils.*;
import com.yhj.APDP.cops.objects.*;
import com.yhj.APDP.cops.objects.pr.*;

public class BBMultiServerThread extends Thread {

	private Socket socket = null;
	private String routerIP = "";
	private String mysqlURL = "";
	private int bbID;
	private Socket[] peerBB;
	private String[] statusList;
	private int[] peerID;
	PrintWriter log;
	String now;
	private int clientPort;
	private String clientAddy;
	private String currentDomain;
	private Socket[] pepSockets;
	SIBBS_CONNECT sibbs;
	CopsprPdpImpl pdp;

    	public BBMultiServerThread(CopsprPdpImpl pdp,Socket[] pepSockets,Socket socket,int serverID,Socket[] peerBB,int[] peerID,String[] status,String mysqlURL,String currentDomain) {
		super("BBMultiServerThread");
		this.socket = socket;
		bbID=serverID;
		this.peerBB=peerBB;
		this.peerID=peerID;
		statusList = status;
		this.mysqlURL = mysqlURL;
		this.currentDomain=currentDomain;
    		this.pdp=pdp;
    		this.pepSockets=pepSockets;
	}



    public void run() {

	try {

	    // Set up output and input streams for the server.

	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

	    BufferedReader in = new BufferedReader(
	    	new InputStreamReader(socket.getInputStream()));

	    String inputLine=null;
	    String outputLogin;
	    InetAddress iNetS = socket.getInetAddress();
	    int clientPort = socket.getPort();
	    String clientAddy = iNetS.toString();

	    boolean ACTIVE=true;

	    System.out.println("Establishing Connection From : " + clientAddy.substring(1) + ":" + clientPort);
	    System.out.println();

	    log = new PrintWriter(new FileWriter("./logs/BBLog.txt",true));
	    now = new Date().toString();
	    log.println(now + " - Connection established from " + clientAddy.substring(1));
	    log.close();


	    //System.out.println("This server knows about : " + peerBB.length + " other servers.");

	    BBPass bbpass = new BBPass(mysqlURL);
	    BBProtocol bbp = new BBProtocol(mysqlURL);

	    // Authenticate the user

	    String loginS = in.readLine();

	    outputLogin = bbpass.checkPass(loginS);

	    if(outputLogin.equalsIgnoreCase("Login failed.Restart client.")) {
	    	ACTIVE=false;
	    	System.out.println("Incorrect Login from : " + clientAddy.substring(1));
		now = new Date().toString();
		log = new PrintWriter(new FileWriter("./logs/BBLog.txt",true));
		log.println("Incorrect Login from : " + clientAddy.substring(1) + " at : " + now);
		log.close();
	    }


	
	    out.println(outputLogin);
	  
	    // Update the status of peer BB's, if more are online, then connect to them

	    SIBBS_CONNECT sibbs = new SIBBS_CONNECT(mysqlURL);

	    peerBB = sibbs.updatePeers(peerBB,bbID,peerID,statusList);
	    statusList = sibbs.statusList(bbID);
	    


	   // Stay in this loop as long as transactions between client and server still needed.

	   while(ACTIVE) {

		try {
	    		inputLine=in.readLine();
		} catch (SocketException e) { 
			
			ACTIVE=false;
		}
		
		try {
	    		if(inputLine.equalsIgnoreCase("exit")) {
	    			ACTIVE=false;
	    			break;
	    		}

			// The following cleanly shutdowns the BB

		    	if(inputLine.equalsIgnoreCase("shutdown")) {
		    		System.out.println("About to shutdown");
		    		out.close();
	    			in.close();
				System.out.println("Shutting down BB......");
	    			updateDB off = new updateDB(mysqlURL);
	    			
	    			
	    			for (int i=0; i<peerBB.length;i++){
	    				
	    				try {
	    					peerBB[i].close();
	    					System.out.println("Closing peer # " + (i+1));
	    				} catch (Exception e) {
	    					//System.out.println(e);
	    				}
	    			}
	    			System.out.println();
	    			System.out.println("Connections to peer BB's successfully closed.");
	    			off.dropPEP(currentDomain,pepSockets,pdp);
	    			System.out.println("PEP's successfully shutdown.");
	    			off.shutdownServer(bbID);
				socket.close();
	    			System.exit(0);
	    		}
			//peerBB = sibbs.updatePeers(peerBB,bbID,peerID,statusList);
	    		statusList = sibbs.statusList(bbID);
			
			// Process the incoming request in the BBProtocol class
			
		    	String output = bbp.processInput(pdp,pepSockets,inputLine,peerBB,peerID,bbID,statusList,currentDomain);


	   		out.println(output);

		} catch(NullPointerException e) {
			ACTIVE=false;
		}

	   }


	    out.close();
	    in.close();
	    socket.close();

	   System.out.println("Connection closed : " + clientAddy.substring(1));
	   System.out.println();

	} catch (IOException e) {
	    e.printStackTrace();

    	}
    }
}
