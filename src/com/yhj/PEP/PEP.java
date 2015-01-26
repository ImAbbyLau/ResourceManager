package com.yhj.PEP;

import java.net.*;
import java.util.*;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//import LPEP.KATimer.KeepAliveThread;
//import TimerTest.TimerTestTask;

//import TimerTest.TimerTestTask;
import com.yhj.PEP.cops.*;
import com.yhj.PEP.cops.pr.*;
import com.yhj.PEP.cops.messages.*;
import com.yhj.PEP.cops.objects.*;
import com.yhj.PEP.cops.messages.pr.*;
import com.yhj.PEP.cops.objects.pr.*;
import com.yhj.PEP.cops.utils.*;
import com.yhj.PEP.pib.*;
import com.yhj.PEP.pib.test.*;

public class PEP {

	String mysqlURL = "localhost";
	updateDB udb;
	CopsPep pep;
	String pdpAddr;
	short pdpPort = 3288; // The connection port to PDP
	short clientType = 1;
	KeepAliveThread kat;
	TimeoutThread t;
	int cHandle = 1;
	short rType = 2;
	short mType = 3;
	Decision[] dec = { new Decision((short) 11, (short) 22, (short) 33,
			(short) 44) };
	String pepID = null;
	LinuxRoute lr;
	int kaflag = 0;
	int decflag = 0;
	long timeOne = 0;
	int REQCounter = 0;
	int timei = 0;
	int count = 1;
	long[] Times = new long[count + 1];

	public static void main(String[] args) throws IOException {

		String hostname = "localhost";
		String mysqlURL = "localhost";
		if (args.length > 0)
			mysqlURL = args[0];

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter location of database : "); //
		// mysqlURL = in.readLine();
		mysqlURL = "localhost";
		System.out.print("Enter PDP's hostname : "); // PDP's Address
		hostname = in.readLine();
		// hostname = "localhost";
		System.out.print("Enter PEP Name : ");
		String pepName = in.readLine();
		// String pepName = "1a";
		System.out.println();

		PEP pep = new PEP(mysqlURL, hostname, pepName);
		pep.run();
	}

	public PEP(String mysqlURL, String hostname, String pepID) {
		pep = new CopsprPepImpl(new EchoMsgHandler());
		// pdpAddr = udb.getpdpAddr(pepID);
		pdpAddr = hostname;
		updateDB udb = new updateDB(mysqlURL);
		pdpPort = udb.getPDPport(pepID);
		// LinuxRoute lr = new LinuxRoute();
		// lr.setupDiff();
		this.lr = lr;
		this.mysqlURL = mysqlURL;
		this.pdpPort = pdpPort;
		this.pepID = pepID;
		this.udb = udb;
	}

	public void run() {
		try {

			pep.connect(pdpAddr, pdpPort);
			pep.open(pdpAddr, pdpPort, clientType, pepID);

		} catch (CopsPepException ce) {
			System.err.println(ce);
			ce.printStackTrace();
		}
	}

	// //////////////////////////////////////////////////
	/**
	 * This class handles the message. This is basically the behaviour of
	 * PEP/router.
	 */

	class EchoMsgHandler implements CopsMsgHandler {

		// how to handle a CAT message from PDP
		public void handleCAT(CopsCAT cat, Socket s) {
			System.out.println("Received CAT msg: " + cat);
			System.out.println("The connection is established---------");
			System.out.println("PEP is Waiting");
			System.out.println();
			// create a new thread that handles sending KA messages
			// kat = new KeepAliveThread(s, cat.getKATimer());
			// kat.start();
			try {
				sendConfigREQ(s);
				Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
				c.setTime(new Date());
				timeOne = c.getTimeInMillis();
				System.out.println("Sending REQ at " + timeOne);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// dec 超时

		// try{
		// new DecTimer(s, 3);

		// }catch (Exception e ) {

		// }

		/*
		 * t = new TimeoutThread(5000,new TimeoutException("超时")); try{
		 * t.start(); }catch (TimeoutException e) { e.printStackTrace(); }
		 */

		// how to handle a DEC message from PDP
		public void handleDEC(CopsDEC dec, Socket s) {
			decflag = 1;
			System.out.println("Received DEC msg: " + dec);
			Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
			c.setTime(new Date());
			long timeTwo = c.getTimeInMillis();
			System.out.println("Receive DEC at " + timeTwo);
			long time = (timeTwo - timeOne);
			System.out.println("The time delay is " + time + "ms");
//			Times[timei++] = time;
			// process the Decision
			// Try to send REQ to test time delay\
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			if (REQCounter < count) {
//				if (decflag == 1) {
//					try {
//						sendConfigREQ(s);
//						c = Calendar.getInstance(); // 可以对每个时间域单独修改
//						c.setTime(new Date());
//						timeOne = c.getTimeInMillis();
//						System.out.println("Sending REQ at " + timeOne);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					REQCounter++;
//					decflag = 0;
//				}
//				if (decflag == 0) {
//					;
//				}
//			} else {
//				// for (int i = 0; i < Times.length; i++)
//				// System.out.print(Times[i] + " ");
//				System.out.println();
//			}
			// processDEC(dec, s);
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
			kaflag = 1;
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

	// Send this after receiving CAT from PDP
	private void sendConfigREQ(Socket s) throws CopsPepException {

		udb.statusPEP(pepID, "ON");

		CopsprREQ req = new CopsprREQ(clientType, cHandle,
				COContext.CONFIGURATION, (short) 0);
		Binding[] configs = new Binding[1];
		byte[] prid = IpFilterEntry.prcIndex;
		// byte[] prid = parseFrom("1.3.6.1.2.2.1.3.2");
		configs[0] = new Binding(new COPRPRID(prid), new COPREPD(
				BERUtil.encode(null, BERUtil.NULL)));
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
				System.out.println("The Source is : " + src);

				for (int i = 0; i < ipfe.dstAddr.length; i++) {
					if (i < ipfe.dstAddr.length - 1) {

						dst = dst + (ipfe.dstAddr[i] & 255) + ".";
					} else {

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

					// This can be used here for future additions with respect
					// to receiving a DEC after REQ to PDP upon startup.

				}
				if (commandCode == 1) {
					System.out.println("Incoming DEC is an 'INSTALL' DEC");
					// lr.addRoute(dst,src);
				}

				if (commandCode == 2) {
					System.out.println("Incoming DEC is a 'REMOVE' DEC");
					lr.delRoute(dst, src);
				}
			}
		}

		short reportType = 1;
		try {
			pep.ReportState(s, clientType, cHandle, reportType);
		} catch (CopsPepException ce) {
			System.err.println(ce);
		}
	}

	class DecTimer {
		Timer timer;
		Socket s;

		public DecTimer(Socket s, int interval) {
			this.s = s;
			// this.interval =interval;
			timer = new Timer();
			timer.schedule(new KATimerTask(), interval * 1000);
		}

		class KATimerTask extends TimerTask {
			public void run() {
				if (decflag == 1) {
					decflag = 0;
					timer.cancel();
				} else {
					System.out.println("DEC超时!");
				}
			}
		}
	}

	class KATimer {
		Timer timer;
		Socket s;

		public KATimer(Socket s, int interval) {
			this.s = s;
			// this.interval =interval;
			timer = new Timer();
			timer.schedule(new KATimerTask(), interval * 1000);

		}

		class KATimerTask extends TimerTask {
			public void run() {
				// int kaflag = 1;
				while (kaflag == 1) {
					try {
						pep.keepAlive(s);
						timer.cancel();
					} catch (CopsPepException e) {
						// possibly lost connection
						e.printStackTrace();
						break;
					}
					try {
						s.setSoTimeout(5000);
						s.getInputStream().available();

					} catch (SocketTimeoutException e) {
						System.out.println("读取数据超时!");
						try {
							pep.close(pdpAddr, CopsConstants.COPS_PDP_PORT,
									clientType, (short) 1, (short) 2);
							;
							break;
						} finally {
							break;
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
	}

	public class TimeoutThread extends Thread {
		/**
		 * 计时器超时时间
		 */
		private long timeout;
		private boolean isCanceled = false;
		/**
		 * 　　* 当计时器超时时抛出的异常 　　
		 */
		private TimeoutException timeoutException;

		/**
		 * 　　* 构造器 　　* @param timeout 指定超时的时间 　　
		 */
		public TimeoutThread(long timeout, TimeoutException timeoutErr) {
			super();
			this.timeout = timeout;
			this.timeoutException = timeoutErr;
			// 设置本线程为守护线程
			this.setDaemon(true);
		}

		public synchronized void cancel() {
			isCanceled = true;
		}

		/**
		 * 　　* 启动超时计时器 　　
		 */
		public void run() {
			try {
				Thread.sleep(timeout);
				if (decflag == 1)
					t.cancel();
				if (!isCanceled)
					throw timeoutException;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public class TimeoutException extends RuntimeException {

		private static final long serialVersionUID = -8078853655388692688L;

		public TimeoutException(String errMessage) {
			super(errMessage);
		}
	}

	//
	class KeepAliveThread extends Thread {
		Socket s;
		int interval; // interval in milli second
		DataInputStream diska;
		CopsCommonHeader cchka = null;

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
						// pep.keepAlive(pdpAddr, CopsConstants.COPS_PDP_PORT);
						try {
							pep.keepAlive(s);
						} catch (CopsPepException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						synchronized (kat) {
							try {
								kat.wait(5000);
								if (kaflag == 0) {
									System.out.println("读取数据超时!");
									try {
										pep.close(pdpAddr,
												CopsConstants.COPS_PDP_PORT,
												clientType, (short) 1,
												(short) 2);
										break;
									} catch (CopsPepException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								if (kaflag == 1) {
									kaflag = 0;
								}

							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
						}
					} finally {
					}
				}
			}
		}
	}
}

/*
 * try // { // s.setSoTimeout(interval); //s.getInputStream().available();
 * //s.getInputStream().read(); // } // catch (SocketTimeoutException e)
 * 
 * 
 * //if (!s.isClosed()&& s.isConnected()) //{ System.out.println("读取数据超时!"); try
 * { pep.close(pdpAddr, CopsConstants.COPS_PDP_PORT, clientType, (short)1,
 * (short)2);; break;}
 */

// //////////////////////////

/*
 * private void processDEC(CopsDEC dec, Socket s) { // TODO process the decision
 * Enumeration e = dec.getDecisions(); while (e.hasMoreElements()) { Decision
 * decision = (Decision) e.nextElement(); NamedDecData named = (NamedDecData)
 * decision.getNamed(); Enumeration enum = named.getInstallDecisions(); while
 * (enum.hasMoreElements()) { Binding b = (Binding) enum.nextElement();
 * System.out.println("install: " + new
 * IpFilterEntry(b.epd.getEncodedPRIValue())); } } }
 * 
 * private void sendConfigREQ(Socket s) throws CopsPepException { CopsprREQ req
 * = new CopsprREQ(clientType, cHandle, COContext.CONFIGURATION, (short) 0);
 * Binding[] configs = new Binding[1]; byte[] prid = IpFilterEntry.prcIndex;
 * configs[0] = new Binding(new COPRPRID(prid), new COPREPD(BERUtil.encode(null,
 * BERUtil.NULL))); req.addClientSI(new NamedClientSIReq(configs));
 * 
 * pep.sendMessage(s, req); }
 */

