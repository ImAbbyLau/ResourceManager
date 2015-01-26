package com.yhj.MPDP;

import java.net.*;
import java.io.*;
import java.rmi.*;
import java.util.*;

import com.yhj.MPDP.cops.*;
import com.yhj.MPDP.cops.pr.*;
import com.yhj.MPDP.cops.messages.*;
import com.yhj.MPDP.cops.messages.pr.*;
import com.yhj.MPDP.cops.objects.*;
import com.yhj.MPDP.cops.objects.pr.*;
import com.yhj.MPDP.pib.*;
import com.yhj.MPDP.pib.test.*;
import com.yhj.MPDP.pib.PIBImpl.*;

/* This class starts the  MPDP Server (One per Domain)
 // It also starts the PDP component for COPS-PR operation
 // the PDP are multithreaded and can receive multiple connections from clients
 //   depending on the specified Port 
 // 
 */
public class MPDP {

	CopsprPdpImpl Mpdp;
	int max = 5;
	short clientType = 0;
	short reportType = 0;
	int KACounter = 0;
	int cHandle = 1;
	pdpSQL pdpP;
	PIB pib;
	Socket[] MpepSockets;
	// String mysqlURL = "dot.cse.unsw.edu.au";
	String mysqlURL = "";
	String UserName = "";
	String Password = "";
	int decflag = 0;

	public static void main(String[] args) throws IOException {

		// String hostname = "127.0.0.1";
		String mysqlURL = "localhost";
		String UserName = "";
		String Password = "";

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("[MPDP] Enter Location of MPDP's Database : ");
		// MPDP数据库的地址，多台电脑间通信的时候填写数据库所在ip地址，单机测试时可设为"localhost"；
		mysqlURL = in.readLine();
		// mysqlURL = "localhost";
		System.out.print("[MPDP] Input MPDP's Database UserName : ");
		// UserName = in.readLine();
		UserName = "root";
		System.out.print("[MPDP] Input MPDP's Database Password : ");
		// Password = in.readLine();
		Password = "111111";
		System.out.println();

		try {
			MPDP mpdp = new MPDP(mysqlURL, UserName, Password);
			mpdp.run(); // 启动mpdp
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MPDP(String mysqlURL, String UserName, String Password)
			throws RemoteException {

		this.mysqlURL = mysqlURL;
		this.UserName = UserName;
		this.Password = Password;

		pdpSQL pdpP = new pdpSQL(mysqlURL, UserName, Password); // 创建数据库实例
		this.pdpP = pdpP;
		int CpepCount = pdpP.noPEPs();
		if (CpepCount == -1) {
			System.out.println("Number of PDPs = 0. Aborting");
			System.exit(1);
		}
		MpepSockets = new Socket[CpepCount];// 在数据库中查找此MPDP所管理MPEP的个数，根据个数创建socket线程数用于与每个MPEP的通信

		// To execute PDP processes
		// 创建Mpdp实例，注意参数“new
		// EchoMsgHandler(mysqlURL)”的作用为当线程收到报文信息后，返回主程序，进行对收到不同报文的分别处理
		Mpdp = new CopsprPdpImpl(new EchoMsgHandler(mysqlURL), MpepSockets);
	}

	public void run() throws IOException {

		int PDPID;
		ServerSocket serverSocket = null;
		boolean listening = true;

		// Simple logging procedure to document system usage

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter MPDP Number : ");
		PDPID = Integer.parseInt(in.readLine());
		int mpdpPort = pdpP.getPort(PDPID); // MPEP与MPDP建立以COPS协议传输的端口

		// String controlDomain = pdpP.getCurrentDomain(PDPID);
		// SIBBS_CONNECT sibbs = new SIBBS_CONNECT(mysqlURL);

		// PrintWriter log = new PrintWriter(new FileWriter("./logs/BBLog.txt",
		// true));
		// String now = new java.util.Date().toString();
		// log.println("MPDPServer(JRRMServer) Server #" + PDPID + " started : "
		// + now);
		// log.close();

		// //////////////MPDP CONNECTION///////////////////////////
		try {
			// open connection to MPEP
			Mpdp.init((short) mpdpPort, max, MpepSockets);
		} catch (CopsPdpException e) {
			e.printStackTrace();
		}
		Socket[] mpeps = Mpdp.getArray();

		pib = new PIBImpl();
		initIPFilterTable(pib); // pib库为COPS-PR中的应用
		
		//Wait for PEP connection finished
		boolean Decsend = false;
		while (Decsend == false) {
			if (decflag == 1)
				Decsend = true;
		}
		
		int port = 5555; // MPDP连接RRMServer的TCP连接端口
		try {
			serverSocket = new ServerSocket(port); // 在Port端口进行监听
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port);
			System.exit(-1);
		}
		System.out.println();
		System.out.println("MPDPServer(JRRMServer) #" + PDPID
				+ " ready to receive RRMServers' requests on port : " + port);

		while (listening) {

			Socket socket = serverSocket.accept(); // MPEP与MPDP建立TCP/IP连接
			new BBMultiServerThread(Mpdp, mpeps, socket, PDPID, mysqlURL)
					.start();
			// new BBMultiServerThread(pdp, mpep, peps, socket, PDPID,
			// mysqlURL).start();

		}
	}

	// 以下为线程返回的根据收到的不同信息所做不同处理的处理函数集合
	class EchoMsgHandler implements CopsMsgHandler {

		String mysqlURL = "localhost";
		Socket[] pepSockets;

		public EchoMsgHandler(String mysqlURL) {
			this.mysqlURL = mysqlURL;
		}

		// How to handle an incoming PEP connection
		public void handleOPN(CopsOPN opn, Socket s) {

			String pepAddy = s.getInetAddress().toString().substring(1);
			int pepPort = s.getPort();
			String pdpID = opn.pepid.pepid;
			System.out.println("Received OPN msg: " + opn);
			System.out.println("Incoming MPEP Addy : " + pepAddy);
			System.out.println("Incoming MPEP Port : " + pepPort);
			System.out.println("Incoming MPEP ID == " + pdpID);
			System.out.println();

			// Accept the incoming PEP connection
			try {
				Mpdp.accept(s, clientType, (short) 3, pdpID, mysqlURL);
			} catch (CopsPdpException e) {
				e.printStackTrace();
			}
		}

		// how to handle a REQ message from MPEP
		public void handleREQ(CopsprREQ req, Socket s, Socket[] pepSockets) {
			System.out.println();
			System.out.println("Received REQ msg: " + req);
			try {
				byte[] prid; // = {1, 3, 6, 1, 2, 3, 1, 1};
				processREQ(req, s);
			} catch (CopsPdpException e) {
				e.printStackTrace();
			}
		}

		public void handleREQ(CopsREQ req, Socket s) {
			System.out.println();
			System.out.println("Received REQ msg : " + req);
		}

		public void handleDEC(CopsDEC dec, Socket s) {
			System.out.println("Received DEC msg: " + dec);
		}

		public void handleRPT(CopsRPT rpt, Socket s) {
			System.out.println();
			System.out.println("Received RPT msg: " + rpt);
			reportType = rpt.getReportType().reportType;
			if (reportType == 1) {
				System.out.println("Decision was successful at the PEP");
			}
			if (reportType == 2) {
				System.out.println("Decision could not be completed by PEP");
			}
			if (reportType == 3) {
				System.out.println("Accounting update for an installed state");
			}
		}

		public void handleDRQ(CopsDRQ drq, Socket s) {
			System.out.println("Received DRQ msg: " + drq);
		}

		public void handleSSQ(CopsSSQ ssq, Socket s) {
			System.out.println("Received SSQ msg: " + ssq);
		}

		public void handleCAT(CopsCAT cat, Socket s) {
			System.out.println("Received CAT msg: " + cat);
			// create a new thread that handles sending KA messages
			// KeepAliveThread kat = new KeepAliveThread(s,
			// cat.cokat.katimervalue);
		}

		public void handleCC(CopsCC cc, Socket s) {
			System.out.println("Received CC msg: " + cc);
		}

		public void handleKA(CopsKA ka, Socket s) {
			System.out.println("Received KA msg: " + ka);
			try {
				Mpdp.sendMessage(s, ka);
				KACounter++;
				if (KACounter == 3) {
					KACounter = 0;
					Mpdp.sendMessage(s, new CopsCC((short) 0,
							COError.SHUTTING_DOWN, (short) 0));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void handleSSC(CopsSSC ssc, Socket s) {
			System.out.println("Received SSC msg: " + ssc);
		}

		public void handleUnknown(CopsMessage msg, Socket s) {
			System.out.println("Unknown message: " + msg);
		}

		public void handleException(CopsException e, Socket s) {
			e.printStackTrace();
			try {
				shutdown();
			} catch (RemoteException re) {
				re.printStackTrace();
			}

		}

	}

	// Below is COPS related Methods

	// Process request from PEP，将pib中的策略信息封装为DEC报文

	protected void processREQ(CopsprREQ req, Socket s) throws CopsPdpException {

		Enumeration clientSIs = req.getClientSIs();
//		System.out.println(clientSIs);

		while (clientSIs.hasMoreElements()) {
			NamedClientSIReq clientSI = (NamedClientSIReq) clientSIs
					.nextElement();
			Enumeration bindings = clientSI.getData();
			// System.out.println(bindings);

			while (bindings.hasMoreElements()) {
				Binding b = (Binding) bindings.nextElement();
				// System.out.println(b);
				byte[] prid = b.prid.getPRID();
				// System.out.println(new String(prid));

				PRI pri = pib.getPRI(prid);
				if (pri != null) {
					// send back the value of attributes represented by the
					// given PRID
					Binding[] decs = { new Binding(new COPRPRID(prid),
							new COPREPD(pri.toBytes())) };

					Decisionpr[] decpr = { new Decisionpr(req.getContext(),
							new CODecFlag(CODecFlag.INSTALL, (short) 0),
							new NamedDecData(decs)) };
					CopsprDEC dec = new CopsprDEC((short) 0, req.getHandle()
							.getHandle(), decpr);
					System.out.println("4444");
					Mpdp.sendMessage(s, dec);
					decflag = 1;
				}
				// 实际使用为如下方式，与pib建库的方式相对应
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
					// Decisionpr[] decpr = {new Decisionpr(req.getContext(),
					// new CODecFlag(CODecFlag.INSTALL, (short) 0), new
					// NamedDecData(decs))};
					Decisionpr[] decpr = { new Decisionpr(
							COContext.CONFIGURATION, (short) 0, (short) 1,
							(short) 0, new NamedDecData(decs)) };

					CopsprDEC dec = new CopsprDEC((short) 0, req.getHandle()
							.getHandle(), decpr);
					Mpdp.sendMessage(s, dec);
					System.out.println("Seng to MPEP's Addr : "
							+ s.getInetAddress());
					System.out.println("Seng to MPEP's Addr : " + s.getPort());// MPEP的IP地址加端口号
					decflag = 1;
				} else {
					// error. PRID or PRC not found.
				}
			}
		}
	}

	public void initIPFilterTable(PIB pib) {
		try {
			PRC prc = new PRCImpl(IpFilterEntry.class, IpFilterEntry.prcIndex); // create
																				// Hashtable
			pib.putPRC(prc.getIndex(), prc); // put preIndex in this Hashtable

			byte[] dstAddr = ObjectID.parseFrom("192.168.0.1");
			byte[] srcAddr = ObjectID.parseFrom("192.168.0.30");
			int dscp = 0x2e;
			byte[] mask = ObjectID.parseFrom("255.255.255.255");
			// byte prid = (byte) 256;
			byte prid = (byte) 255;

			IpFilterEntry filterEntry = new IpFilterEntry(prid, dstAddr, mask,
					srcAddr, mask, dscp);
			prc.putPRI(prid, filterEntry); // put filterEntry 'value with pird
											// in Hashtable

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Shutting down of PDP

	public void shutdown() throws RemoteException {

		System.out.println("shutting down...");

		try {
			// closing connection
			Mpdp.shutdown();

		} catch (CopsPdpException e) {
			e.printStackTrace();
		}
	}
}
