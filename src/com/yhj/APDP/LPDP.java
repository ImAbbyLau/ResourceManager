package com.yhj.APDP;

import java.net.*;
import java.io.*;
import java.rmi.*;
import java.util.*;

import com.yhj.APDP.pib.PIBImpl;
import com.yhj.APDP.cops.*;
import com.yhj.APDP.cops.pr.*;
import com.yhj.APDP.cops.messages.*;
import com.yhj.APDP.cops.messages.pr.*;
import com.yhj.APDP.cops.utils.*;
import com.yhj.APDP.cops.objects.*;
import com.yhj.APDP.cops.objects.pr.*;
import com.yhj.APDP.pib.*;
import com.yhj.APDP.pib.test.*;

/* This class starts the LPDP Server (One per Domain)
// It also starts the PDP component for COPS-PR operation
// the PDP are multithreaded and can receive multiple connections from clients
//   depending on the specified Port 
*/

public class LPDP {

	CopsprPdpImpl Lpdp;
	int max = 5;
	short clientType = 0;
	short reportType = 0;
	int KACounter = 0;
	int cHandle = 1;
	pdpSQL pdpP;
	PIB pib;
	Socket[] pepSockets;
	//String mysqlURL = "dot.cse.unsw.edu.au";
	String mysqlURL = "localhost";
	//Extension String pdpAddr;
	String mysqlURL2 = "localhost";
	public volatile String pdpAddr = "0";
	CPEPClient cpep;
	public short pdpPort= 0;
	String hostname = "localhost";
	String pepName="0";
	CopsprREQ REQ;
	
	public static void main(String[] args) throws IOException {

		//String hostname = "127.0.0.1";
		String mysqlURL = "localhost";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println();
		System.out.print("Enter Location of Database : ");
		mysqlURL = in.readLine();
		mysqlURL = "localhost";
		try {
			LPDP lpdp = new LPDP(mysqlURL);
			lpdp.run();
		} catch (Exception e){e.printStackTrace();}
	}

	public LPDP(String mysqlURL) throws RemoteException,IOException {

		this.mysqlURL=mysqlURL;
		pdpSQL pdpP = new pdpSQL(mysqlURL);
		this.pdpP=pdpP;
		System.out.print("Enter APDP Number : ");
		int CpepCount = pdpP.noPEPs();
		if (CpepCount==-1) {
			System.out.println("Number of PDPs = 0. Aborting");
			System.exit(1);
		}
		pepSockets = new Socket[CpepCount];

		// To execute PDP processes
		Lpdp = new CopsprPdpImpl(new EchoMsgHandler(mysqlURL),pepSockets);
	}

	  public void run() throws IOException{

		//Simple logging procedure to document system usage

		PrintWriter log = new PrintWriter(new FileWriter("./logs/BBLog.txt",true));

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int PDPID;	
		PDPID = Integer.parseInt(in.readLine());
		
		int pdpPort = pdpP.getPort(PDPID);
		String controlDomain=pdpP.getCurrentDomain(PDPID);
		
		//SIBBS_CONNECT sibbs = new SIBBS_CONNECT(mysqlURL);
		String now = new java.util.Date().toString();
		log.println("PDP Server #" + PDPID + " started : " + now);
		log.close();

		System.out.println("Server #" + PDPID + " ready to receive requests.");

		////////////////PDP CONNECTION///////////////////////////
		try {
			// open connection
			Lpdp.init((short)pdpPort, max,pepSockets);

		} catch (CopsPdpException e) {
			e.printStackTrace();}
	        pib = new PIBImpl();
	        initIPFilterTable(pib);  
   }
	  
	class EchoMsgHandler implements CopsMsgHandler {

        String mysqlURL="localhost";
		Socket[] pepSockets;
		public EchoMsgHandler(String mysqlURL) {
			this.mysqlURL=mysqlURL;
		}
		// How to handle an incoming PEP connection
        public void handleOPN(CopsOPN opn, Socket s){

		    String pepAddy =s.getInetAddress().toString().substring(1);
			int pepPort = s.getPort();
			String pepID = opn.pepid.pepid;
			System.out.println("Incoming PEP Addy  : " + pepAddy);
			System.out.println("Incoming PEP Port  : " + pepPort);

			System.out.println("Incoming PEP ID ==  " + pepID);
			System.out.println();
		
            System.out.println("Received OPN msg: " + opn);
			
         // Accept the incoming PEP connection
            try {
				Lpdp.accept(s,clientType,(short) 3,pepID,mysqlURL);
			} catch (CopsPdpException e) {
				e.printStackTrace();
			}
		}

        
        //how to handle a REQ message from MPEP
		public void handleREQ(CopsprREQ req, Socket s,Socket[] pepSockets){
			REQ = req;
			System.out.println();
			System.out.println("Received REQ msg: " + req);
			KACounter = 0;
			System.out.println("I have no policy for this request,now I will hand in it to CPDP");
			//
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println();
			System.out.print("Enter Location of Database : ");
			try {
				mysqlURL2 = in.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.print("Enter CPEP Number : ");
			try {
				pepName = in.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cpep = new CPEPClient(mysqlURL,hostname,pepName);
			cpep.run();
			//sending req
			/*
			try {
		        Thread.sleep(1000);//鎷彿閲岄潰鐨�000浠ｈ〃1000姣锛屼篃灏辨槸1绉�		        cpep.sendreq(req);
		        } catch (InterruptedException e) {e.printStackTrace();}
			
			*/
		        while(true)
        		{
		        	//System.out.println(decflag);
		        	if(cpep.Catflag == 1)
		        	{
				     cpep.sendreq(req);
					 break;
				    }
		        	else System.out.print("");
		        		
		        }
			/*
			//waiting for dec
        	try {
        		Thread.sleep(1000);
				Lpdp.sendMessage(s,cpep.DEC);
				} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				} catch (CopsPdpException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();} 
			*/
		        //try {
			      //  Thread.sleep(1000);
		          // }catch (InterruptedException e) {e.printStackTrace();}
		        
		        while(true)
        		{
		        	//System.out.println(decflag);
		        	if(cpep.Decflag == 1)
		        	{
				   try {
					    //processDEC(cpep.DEC, s); 
					    System.out.println();
					    System.out.println("Sending this DECISION to PEP ");
						Lpdp.sendMessage(s,cpep.DEC);
					} catch (CopsPdpException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();}
					break;
				  }
		        	else System.out.print("");
		        		
		        }
		}
			
		public void handleREQ(CopsREQ req, Socket s) {
			
			System.out.println();
			System.out.println("Received REQ msg : " + req);
				
		}

		public void handleDEC(CopsDEC dec, Socket s){
			
 		   System.out.println("Received DEC msg: " + dec);
			       
		}

		public void handleRPT(CopsRPT rpt, Socket s){
			System.out.println();
			System.out.println("Received RPT msg: " + rpt);
			reportType = rpt.getReportType().reportType;
			if(reportType == 1){
				  System.out.println("Decision was successful at the PEP");
				  //processDEC(cpep.DEC, s); 
				  System.out.println("Now,I put this POLICY in my DATABASE");
				  cpep.sendrpt(rpt);
				//Lpdp.sendMessage(s,cpep.DEC);
			  }
			if(reportType == 2){
				  System.out.println("Decision could not be completed by PEP");
			  }
			if(reportType == 3){
				  System.out.println("Accounting update for an installed state");
			  }
			
		}

		public void handleDRQ(CopsDRQ drq, Socket s){
			System.out.println("Received DRQ msg: " + drq);
		}

		public void handleSSQ(CopsSSQ ssq, Socket s){
			System.out.println("Received SSQ msg: " + ssq);
		}

		public void handleCAT(CopsCAT cat, Socket s){
			System.out.println("Received CAT msg: " + cat);
            //create a new thread that handles sending KA messages
			//KeepAliveThread kat = new KeepAliveThread(s, cat.cokat.katimervalue);
		}

		public void handleCC(CopsCC cc, Socket s){
			System.out.println("Received CC msg: " + cc);
			try {
	      		s.close();
	      		System.out.println("close the connection");
	      		System.exit(1);
	      		
	      } catch(IOException io) {}
	      
		}

		public void handleKA(CopsKA ka, Socket s){
			
			System.out.println("Received KA msg: " + ka);
			///*
			try {
				Lpdp.sendMessage(s, ka);
				KACounter++;
				if (KACounter == 3) {
					KACounter = 0;
					Lpdp.sendMessage(s, new CopsCC((short) 0, COError.SHUTTING_DOWN, (short) 0));
				}
			}
			catch (Exception e) {e.printStackTrace();}
		   }
			
		public void handleSSC(CopsSSC ssc, Socket s){
			System.out.println("Received SSC msg: " + ssc);
		}

		public void handleUnknown(CopsMessage msg, Socket s) {
			System.out.println("Unknown message: " + msg);
		}
		
		public void handleException(CopsException e, Socket s) {
			e.printStackTrace();
			try {
				shutdown();
			}
			catch (RemoteException re) {re.printStackTrace();}
	
	   }
		
	}
	
	
// Below is COPS related Methods
	
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
				System.out.println("The Destination is : " + dst);
				if (ipfe.dscp != -1) {
				System.out.println("DSCP is " + ipfe.dscp);
				}
				
				short commandCode = decision.getFlag().getCommandCode();
								
				if (commandCode == 0) {
					
					
					System.out.println("PEP is connected to PDP");

					//This can be used here for future additions with respect
					// to receiving a DEC after REQ to PDP upon startup.
				
				}
				if (commandCode == 1) {
					System.out.println("Incoming DEC is an 'INSTALL' DEC");
					//System.out.println("Now,I put this POLICY in my DATABASE");
					//lr.addRoute(dst,src);
				}
				
				if (commandCode ==2) {
					System.out.println("Incoming DEC is a 'REMOVE' DEC");
					//lr.delRoute(dst,src);
				} 
			}
		}
		//short reportType = 1;
		//try {
			//pep.ReportState( s, clientType,cHandle,reportType);
		//} catch (CopsPepException ce) {
			//System.err.println(ce);
		//}
	}
	
// Process request from PEP
	
	protected void processREQ(CopsprREQ req, Socket s) throws CopsPdpException {
		
		//System.out.println();
		System.out.println("----------------------------------------");
		Enumeration clientSIs = req.getClientSIs();
		//System.out.println(clientSIs);
		
		while (clientSIs.hasMoreElements()) {
			NamedClientSIReq clientSI = (NamedClientSIReq) clientSIs.nextElement();
			Enumeration bindings = clientSI.getData();
			//System.out.println(bindings);
			
			while (bindings.hasMoreElements()) {
				Binding b = (Binding) bindings.nextElement();
				//System.out.println(b);
				byte[] prid = b.prid.getPRID();
				//System.out.println(new String(prid));
				
				PRI pri = pib.getPRI(prid);
				if (pri != null) {
					// send back the value of attributes represented by the given PRID
					Binding[] decs = {new Binding(new COPRPRID(prid), new COPREPD(pri.toBytes()))};
				    
					Decisionpr[] decpr = {new Decisionpr(req.getContext(), new CODecFlag(CODecFlag.INSTALL, (short) 0), new NamedDecData(decs))};
					CopsprDEC dec = new CopsprDEC((short) 0, req.getHandle().getHandle(), decpr);
					System.out.println("4444");
					Lpdp.sendMessage(s, dec);
					
				}
				else if (pib.hasPRC(prid)) {
					
					// send all instances of this PRC
					PRC prc = pib.getPRC(prid);
					Binding[] decs = new Binding[prc.countPRIs()];
					int i = 0;
					Enumeration pris = prc.getPRIs();
					while (pris.hasMoreElements()) {
						pri = (PRI) pris.nextElement();
						decs[i] = new Binding(pri.getPRID(), pri.toBytes());
						i++;
					}
					//Decisionpr[] decpr = {new Decisionpr(req.getContext(), new CODecFlag(CODecFlag.INSTALL, (short) 0), new NamedDecData(decs))};
					Decisionpr[] decpr = {new Decisionpr(COContext.CONFIGURATION, (short) 0,(short) 0,(short) 0, new NamedDecData(decs))};

					CopsprDEC dec = new CopsprDEC((short) 0, req.pepid.pepid, req.getHandle().getHandle(), decpr);
					Lpdp.sendMessage(s, dec);
				}
				else {
					// error. PRID or PRC not found.
				}
			}
		}
	}

	public void initIPFilterTable(PIB pib) {
		try {
			PRC prc = new PRCImpl(IpFilterEntry.class, IpFilterEntry.prcIndex);
			pib.putPRC(prc.getIndex(), prc);
		
			byte[] dstAddr = ObjectID.parseFrom("127.0.0.1");
			byte[] srcAddr = ObjectID.parseFrom("127.0.0.1");
			int dscp = 8;
			byte[] mask = ObjectID.parseFrom("255.255.255.255");
			//byte prid = (byte) 256;
			byte prid = (byte) 255;

			IpFilterEntry filterEntry = new IpFilterEntry(prid, dstAddr, mask, srcAddr, mask,dscp);
			prc.putPRI(prid, filterEntry);

		   } catch (Exception e) {e.printStackTrace();}
	}
	       
	// Shutting down of PDP
	
	public void shutdown() throws RemoteException {

		System.out.println("shutting down...");

		try {
			// closing connection
			Lpdp.shutdown();

		} catch (CopsPdpException e) {
			e.printStackTrace();
		}
	}

	
}
