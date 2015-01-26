package com.yhj.APDP;

import java.net.*;
import java.util.*;
import java.io.*;
import com.yhj.APDP.cops.*;
import com.yhj.APDP.cops.pr.*;
import com.yhj.APDP.cops.messages.*;
import com.yhj.APDP.cops.objects.*;
import com.yhj.APDP.cops.messages.pr.*;
import com.yhj.APDP.cops.objects.pr.*;
import com.yhj.APDP.cops.utils.*;
import com.yhj.APDP.pib.*;
import com.yhj.APDP.pib.test.*;

public class PEPClient {

	String mysqlURL = "localhost";
	updateDB udb;
	CopsPep pep;
	String pdpAddr="localhost";
	short pdpPort = 3288;
	short clientType = 1;
	KeepAliveThread kat;
	int cHandle = 1;
	short rType = 2;
	short mType = 3;
	Decision[] dec = {new Decision((short) 11, (short) 22, (short) 33, (short) 44)};
	String pepID=null;
	LinuxRoute lr;
	short pepPort =4288;
	Socket[] pepSockets;
	int pdpCount = 1  ;
	int max = 5;
	
     public static void main(String[] args) throws IOException {
		String hostname = "localhost";
		//String hostname="121.49.83.220";
		String mysqlURL = "localhost";
		if (args.length > 0 ) mysqlURL = args[0];

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Enter location of database : ");
		mysqlURL = in.readLine();

		System.out.print("Enter PEP Name : ");
		String pepName = in.readLine();
		

		PEPClient test = new PEPClient(mysqlURL,hostname,pepName);
		test.run();
		
	}


	public PEPClient(String mysqlURL,String hostname,String pepID) {
		pep = new CopsprPepImpl(new EchoMsgHandler());
		pdpAddr = hostname;
		updateDB udb = new updateDB(mysqlURL);
		pdpPort = udb.getPDPport(pepID);
		LinuxRoute lr = new LinuxRoute();
		lr.setupDiff();
		pepSockets = new Socket[pdpCount];
		this.lr=lr;
		this.mysqlURL=mysqlURL;
		this.pdpPort=pdpPort;
		this.pepID=pepID;
		this.udb=udb;
	}

	public void run() {
		try {
			pep.connect(pdpAddr,pdpPort);
			pep.open(pdpAddr,pdpPort,clientType,pepID);
			//pep.init((short)pepPort, max,pepSockets);
			try {
		        Thread.sleep(1000);//鎷彿閲岄潰鐨�000浠ｈ〃1000姣锛屼篃灏辨槸1绉掞紝鍙互璇ユ垚浣犻渶瑕佺殑鏃堕棿
		} catch (InterruptedException e) {
		        e.printStackTrace();
		}
			
		    //pep.sendREQ(pdpAddr,pdpPort);
			 
		} catch (CopsPepException ce) {
			System.err.println(ce);
			//e.printStackTrace();
		} 
	}

	public void shutdown() {
		System.out.println("shutting down...");
	}
	
// Create byte[] from string = "1.3.6.1.2.2.1.3.2". use in /pib/IpfilterEntry.java
// Use to get prid, use in sendConfigREQ.
	public static byte[] parseFrom(String oidStr) {
		System.out.println("22");
		StringTokenizer tok = new StringTokenizer(oidStr, ".");
		byte[] result = new byte[tok.countTokens()];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) Integer.parseInt(tok.nextToken());
		}
		return result;
		
	}
	    
	// Send this after receiving CAT from PDP
	private void sendConfigREQ(Socket s) throws CopsPepException {
		
		udb.statusPEP(pepID,"ON");
		
		CopsprREQ req = new CopsprREQ(clientType, pepID,cHandle, COContext.CONFIGURATION, (short) 0);
		Binding[] configs = new Binding[1];
		byte[] prid = IpFilterEntry.prcIndex;
		//byte[] prid = parseFrom("1.3.6.1.2.2.1.3.2");
		configs[0] = new Binding(new COPRPRID(prid), new COPREPD(BERUtil.encode(null, BERUtil.NULL)));
		req.addClientSI(new NamedClientSIReq(configs));

		pep.sendMessage(s, req);
	}
	// process decision send by PDP.
	private void processDEC(CopsDEC dec, Socket s) {
		
		String dst = "";
		String src = "";
		
		Enumeration e = dec.getDecisions();
		while (e.hasMoreElements()) {
			Decision decision = (Decision) e.nextElement();
			NamedDecData named = (NamedDecData) decision.getNamed();
			
			Enumeration myenum = named.getInstallDecisions();
			while (myenum.hasMoreElements()) {
				Binding b = (Binding) myenum.nextElement();
				IpFilterEntry ipfe = new IpFilterEntry(b.epd.getEncodedPRIValue());
				dst="";
				src="";
				
				for (int i = 0; i < ipfe.srcAddr.length; i++) {
					if (i<ipfe.srcAddr.length - 1) {
						
						src = src + (ipfe.srcAddr[i] & 255) + ".";
					}
					else {
						
						src = src + (ipfe.srcAddr[i] & 255);
					}
				}
				System.out.println();
				System.out.println("The Source is : " + src);
				
				
				for (int i = 0; i < ipfe.dstAddr.length; i++) {
					if (i<ipfe.dstAddr.length - 1) {
						
						dst = dst + (ipfe.dstAddr[i] & 255) + ".";
					}
					else {
						
						dst = dst + (ipfe.dstAddr[i] & 255);
					}
				}
				System.out.println();
				System.out.println("The Destination is : " + dst);
				if (ipfe.dscp != -1) {
					//System.out.println("DSCP is " + ipfe.dscp);
				}
				
				short commandCode = decision.getFlag().getCommandCode();
								
				if (commandCode == 0) {
					
					
					System.out.println("PEP is connected to PDP");


					//This can be used here for future additions with respect
					// to receiving a DEC after REQ to PDP upon startup.
				
				}
				if (commandCode == 1) {
					System.out.println("Incoming DEC is an 'INSTALL' DEC");
					lr.addRoute(dst,src);
				}
				
				if (commandCode ==2) {
					System.out.println("Incoming DEC is a 'REMOVE' DEC");
					lr.delRoute(dst,src);
				} 
				
				
				
			}
		}
	}

////////////////////////////////////////////////////
   /**
   * This class handles the message.
   * This is basically the behaviour of PEP/router.
   */

  class EchoMsgHandler implements CopsMsgHandler {

    public void handleREQ(CopsREQ req, Socket s){
      System.out.println("Received REQ msg : " + req); // not valid.
    }
    
    public void handleREQ(CopsprREQ req, Socket s, Socket[] jackcrap) { 
    	System.out.println("Received REQ msg : " + req) ; //not valid.
    }

    public void handleDEC(CopsDEC dec, Socket s){
      System.out.println("Received DEC msg from LPDP: " + dec);
      // TODO process the Decision
      processDEC(dec, s);
    }

    public void handleRPT(CopsRPT rpt, Socket s){
      System.out.println("Received RPT msg: " + rpt); // not valid.
    }

    public void handleDRQ(CopsDRQ drq, Socket s){
      System.out.println("Received DRQ msg: " + drq); // not valid.
    }

    public void handleSSQ(CopsSSQ ssq, Socket s){
      System.out.println("Received SSQ msg: " + ssq);
    }

    public void handleOPN(CopsOPN opn, Socket s){
      System.out.println("Received OPN msg: " + opn); // not valid.
     
    }

    public void handleCAT(CopsCAT cat, Socket s){
      System.out.println("Received CAT msg: " + cat);

      // create a new thread that handles sending KA messages
      //kat = new KeepAliveThread(s, cat.getKATimer());
      //kat.start();
      try {
        sendConfigREQ(s);
      } catch (Exception e) {e.printStackTrace();}
    }

    public void handleCC(CopsCC cc, Socket s){
      System.out.println("Received CC msg: " + cc);
      udb.statusPEP(pepID,"OFF");
      try {
      		s.close();
      		System.exit(1);
      } catch(IOException io) {}
      
      // TODO: shutdown the system
    }

    public void handleKA(CopsKA ka, Socket s){
      System.out.println("Received KA msg: " + ka);
    }

    public void handleSSC(CopsSSC ssc, Socket s){
      System.out.println("Received SSC msg: " + ssc); // not valid.
    }

    public void handleUnknown(CopsMessage msg, Socket s) {
      System.out.println("Unknown message: " + msg);
    }

    public void handleException(CopsException e, Socket s) {
      shutdown();
    }
  }

  class KeepAliveThread extends Thread {
    Socket s;
    int interval; // interval in milli second

    KeepAliveThread(Socket s, short interval) {
      this.s = s;
      this.interval = interval * 1000;
    }

    public void run() {
      // sleep for a period of time and then send KA message
      Thread thread = Thread.currentThread();
      while (kat == thread) {
        try {
          Thread.sleep(interval);
        } catch (InterruptedException e) {

        } finally {
          // send KA
          try{
            //System.out.println("Sending keep alive to "+pdpAddr);
            pep.keepAlive(pdpAddr, CopsConstants.COPS_PDP_PORT, clientType);
          } catch (CopsPepException e) {
            // possibly lost connection
            e.printStackTrace();
            break;
          }
        }
      }
    }
  }


////////////////////////////

/*
  private void processDEC(CopsDEC dec, Socket s) {
    // TODO process the decision
    Enumeration e = dec.getDecisions();
    while (e.hasMoreElements()) {
      Decision decision = (Decision) e.nextElement();
      NamedDecData named = (NamedDecData) decision.getNamed();
      Enumeration enum = named.getInstallDecisions();
      while (enum.hasMoreElements()) {
        Binding b = (Binding) enum.nextElement();
        System.out.println("install: " + new IpFilterEntry(b.epd.getEncodedPRIValue()));
      }
    }
  }

  private void sendConfigREQ(Socket s) throws CopsPepException {
    CopsprREQ req = new CopsprREQ(clientType, cHandle, COContext.CONFIGURATION, (short) 0);
    Binding[] configs = new Binding[1];
    byte[] prid = IpFilterEntry.prcIndex;
    configs[0] = new Binding(new COPRPRID(prid), new COPREPD(BERUtil.encode(null, BERUtil.NULL)));
    req.addClientSI(new NamedClientSIReq(configs));

    pep.sendMessage(s, req);
  }
*/
}
