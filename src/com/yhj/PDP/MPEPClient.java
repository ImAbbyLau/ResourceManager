package com.yhj.PDP;

import java.net.*;
import java.util.*;
import java.io.*;

import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.pr.*;
import com.yhj.PDP.cops.messages.*;
import com.yhj.PDP.cops.objects.*;
import com.yhj.PDP.cops.messages.pr.*;
import com.yhj.PDP.cops.objects.pr.*;
import com.yhj.PDP.cops.utils.*;
import com.yhj.PDP.pib.*;
import com.yhj.PDP.pib.test.*;
import com.yhj.PDP.LinuxRoute;

public class MPEPClient {

	CopsPep pep;
	String pdpAddr;
	updateDB udb;
	short pdpPort;
	LinuxRoute lr;
	String pepID = null;
	String mysqlURL = "localhost";
	short clientType = 1;
	int cHandle = 1;
	KeepAliveThread kat;
	int Decflag;
	PDP lpdp;
	CopsDEC DEC;
	int Catflag = 0;
	public static Socket socket = null;

	public MPEPClient(String mysqlURL, String hostname, String pepID) {
		pep = new CopsprPepImpl(new EchoMsgHandler());
		pdpAddr = hostname;
		updateDB udb = new updateDB(mysqlURL);
		// pdpPort = udb.getPDPport(pepID); //获得MPDP用于连接MPEP的监听端口
		pdpPort = 3288;
		// LinuxRoute lr = new LinuxRoute();
		// lr.setupDiff();
		this.lr = lr;
		this.mysqlURL = mysqlURL;
		this.pdpPort = pdpPort;
		this.pepID = pepID;
		this.udb = udb;
		Decflag = 0;
	}

	public void run() {
		try {
			System.out.println();
			System.out.println("MPEP is trying to connect to MPDP...");
			pep.connect(pdpAddr, pdpPort); // MPDP与MPEP建立TCP连接
			pep.open(pdpAddr, pdpPort, clientType, pepID); // send OPN message
		} catch (CopsPepException ce) {
			System.err.println(ce);
			ce.printStackTrace();
		}
	}

	public void sendrpt(CopsRPT rpt) {
		try {
			pep.CpepsendRPT(pdpAddr, pdpPort, rpt);
		} catch (CopsPepException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void gotMessage() throws InterruptedException {
		Catflag = 1;
		notify();
	}


	public void sendreq(CopsprREQ req) {
		try {
			System.out.println("Sending PEP's Request to MPDP...");
			System.out.println("MPDP's Addr : " + pdpAddr + "; MPDP's Port : " + pdpPort);
			pep.MpepsendREQ(pdpAddr, pdpPort, req);
		} catch (CopsPepException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class EchoMsgHandler implements CopsMsgHandler {

		// how to handle a CAT message from PDP
		public void handleCAT(CopsCAT cat, Socket s) {
			System.out.println("Received CAT msg from MPDP : " + cat);
			System.out
					.println("The connection to MPDP is established---------");
			Catflag = 1;
			socket = s; // Get the Socket between MPDP and MPEP
			// LPDP.EchoMsgHandler.setnotify();
			// try {
			// gotMessage();
			// } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// Catflag=1;
			// create a new thread that handles sending KA messages
			// kat = new KeepAliveThread(s, cat.getKATimer());
			// kat.start();

			// try {
			// sendConfigREQ(s);
			// } catch (Exception e) {e.printStackTrace();}
		}

		// how to handle a DEC message from MPDP
		public void handleDEC(CopsDEC dec, Socket s) {
			System.out.println("MPEP Received DEC msg from MPDP : ");
			System.out.println(dec);
//			System.out.println(s.getInetAddress() + "+" + s.getPort());
			System.out.println();
			DEC = dec;    //MPDP get dec via DEC
			// TODO process the Decision
			Decflag = 1;    //MPDP can know whether MPDP received dec from MPDP
			// processDEC(dec, s); //Implement The Decision From MPDP
		}

		public void handleREQ(CopsREQ req, Socket s) {
			System.out.println("Received REQ msg: " + req); // not valid.
		}

		public void handleREQ(CopsREQ req, Socket s, Socket[] jackcrap) {
			System.out.println("Received REQ msg : " + req); // not valid.
		}

		public void handleRPT(CopsRPT rpt, Socket s) {
			System.out.println("Received RPT msg: " + rpt); // not valid.
		}

		public void handleDRQ(CopsDRQ drq, Socket s) {
			System.out.println("Received DRQ msg: " + drq); // not valid.
		}

		public void handleSSQ(CopsSSQ ssq, Socket s) {
			System.out.println("Received SSQ msg: " + ssq);
		}

		public void handleOPN(CopsOPN opn, Socket s) {
			System.out.println("Received OPN msg: " + opn); // not valid.
		}

		public void handleCC(CopsCC cc, Socket s) {
			System.out.println("Received CC msg: " + cc);
			udb.statusPEP(pepID, "OFF");
			try {
				s.close();
				System.exit(1);
			} catch (IOException io) {
			}

			// TODO: shutdown the system
		}

		public void handleKA(CopsKA ka, Socket s) {
			System.out.println("Received KA msg: " + ka);
		}

		public void handleSSC(CopsSSC ssc, Socket s) {
			System.out.println("Received SSC msg: " + ssc); // not valid.
		}

		public void handleUnknown(CopsMessage msg, Socket s) {
			System.out.println("Unknown message: " + msg);
		}

		public void handleException(CopsException e, Socket s) {
			shutdown();
		}

		// @Override
		public void handleREQ(CopsprREQ req, Socket s, Socket[] pepSockets) {
			// TODO Auto-generated method stub

		}
	}

	public void shutdown() {
		System.out.println("shutting down...");
	}

	// Create byte[] from string = "1.3.6.1.2.2.1.3.2". use in
	// /pib/IpfilterEntry.java
	// Use to get prid, use in sendConfigREQ.
	public static byte[] parseFrom(String oidStr) {
		StringTokenizer tok = new StringTokenizer(oidStr, ".");
		byte[] result = new byte[tok.countTokens()];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) Integer.parseInt(tok.nextToken());
		}
		return result;
	}

	// Send this after receiving CAT from MPDP
	public void sendConfigREQ(Socket s) throws CopsPepException {
		pep.sendMessage(s, lpdp.REQ);
	}

	public void sendConfigREQ() throws CopsPepException {
		// pep.MpepsendREQ(addr, port, req);
		// Socket s = pep.
		// pep.sendMessage(lpdp.REQ);
		udb.statusPEP(pepID, "ON");
		Socket s = socket;    //MPEP与MPDP之间的Socket连接
		CopsprREQ req = new CopsprREQ(clientType, cHandle,
				COContext.CONFIGURATION, (short) 0);
		Binding[] configs = new Binding[1];
		byte[] prid = IpFilterEntry.prcIndex;
		// byte[] prid = parseFrom("1.3.6.1.2.2.1.3.2");
		configs[0] = new Binding(new COPRPRID(prid), new COPREPD(
				BERUtil.encode(null, BERUtil.NULL)));
		req.addClientSI(new NamedClientSIReq(configs));
		System.out.println("mpep.send: " + req);
		pep.sendMessage(s, req);

	} // Add By Myself

	// Send this after receiving CAT from PDP

	/*
	 * private void sendConfigREQ(Socket s) throws CopsPepException {
	 * 
	 * udb.statusPEP(pepID,"ON");
	 * 
	 * CopsprREQ req = new CopsprREQ(clientType,pepID,cHandle,
	 * COContext.CONFIGURATION, (short) 0); Binding[] configs = new Binding[1];
	 * byte[] prid = IpFilterEntry.prcIndex; //byte[] prid =
	 * parseFrom("1.3.6.1.2.2.1.3.2"); configs[0] = new Binding(new
	 * COPRPRID(prid), new COPREPD(BERUtil.encode(null, BERUtil.NULL)));
	 * req.addClientSI(new NamedClientSIReq(configs));
	 * 
	 * pep.sendMessage(s, req); }
	 */

	// process decision send by PDP.
	public void processDEC(CopsDEC dec, Socket s) {

		String dst = "";
		String src = "";

		Enumeration e = dec.getDecisions();
		while (e.hasMoreElements()) {
			Decision decision = (Decision) e.nextElement();
			NamedDecData named = (NamedDecData) decision.getNamed();

			Enumeration myenum = named.getInstallDecisions();
			while (myenum.hasMoreElements()) {
				Binding b = (Binding) myenum.nextElement();
				IpFilterEntry ipfe = new IpFilterEntry(
						b.epd.getEncodedPRIValue());
				dst = "";
				src = "";

				for (int i = 0; i < ipfe.srcAddr.length; i++) {
					if (i < ipfe.srcAddr.length - 1) {

						src = src + (ipfe.srcAddr[i] & 255) + ".";
					} else {

						src = src + (ipfe.srcAddr[i] & 255);
					}
				}
				System.out.println();
				System.out.println("The Source is : " + src);

				for (int i = 0; i < ipfe.dstAddr.length; i++) {
					if (i < ipfe.dstAddr.length - 1) {

						dst = dst + (ipfe.dstAddr[i] & 255) + ".";
					} else {

						dst = dst + (ipfe.dstAddr[i] & 255);
					}
				}
				System.out.println();
				System.out.println("The Destination is : " + dst);
				if (ipfe.dscp != -1) {
					// System.out.println("DSCP is " + ipfe.dscp);
				}

				short commandCode = decision.getFlag().getCommandCode();

				if (commandCode == 0) {

					System.out.println("PEP is connected to PDP");

					// This can be used here for future additions with respect
					// to receiving a DEC after REQ to PDP upon startup.

				}
				if (commandCode == 1) {
					System.out.println("Incoming DEC is an 'INSTALL' DEC");
					lr.addRoute(dst, src);
				}

				if (commandCode == 2) {
					System.out.println("Incoming DEC is a 'REMOVE' DEC");
					lr.delRoute(dst, src);
				}
			}
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
					try {
						// System.out.println("Sending keep alive to "+pdpAddr);
						pep.keepAlive(pdpAddr, CopsConstants.COPS_PDP_PORT,
								clientType);
					} catch (CopsPepException e) {
						// possibly lost connection
						e.printStackTrace();
						break;
					}
				}
			}
		}
	}
}
