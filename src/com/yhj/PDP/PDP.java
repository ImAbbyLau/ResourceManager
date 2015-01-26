package com.yhj.PDP;

import java.net.*;
import java.io.*;
import java.rmi.*;
import java.util.*;
import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.pr.*;
import com.yhj.PDP.cops.messages.*;
import com.yhj.PDP.cops.messages.pr.*;
import com.yhj.PDP.cops.utils.*;
import com.yhj.PDP.cops.objects.*;
import com.yhj.PDP.cops.objects.pr.*;
import com.yhj.PDP.pib.*;
import com.yhj.PDP.pib.test.*;

/* This class starts the BB Server (One per Domain)
 // It also starts the PDP component for COPS-PR operation
 // Both the BB and PDP are multithreaded and can receive multiple connections from clients
 //   depending on the specified Port 
 // It also need to connect to MPDP which is JRRMServer
 */

public class PDP {

	CopsprPdpImpl pdp;
	int max = 5; // 该PDP的最大PEP连接数目
	short clientType = 0;
	short reportType = 0;
	int cHandle = 1;
	int KACounter = 0;
	pdpSQL pdpP;
	PIB pib;
	Socket[] pepSockets;
	// String mysqlURL = "dot.cse.unsw.edu.au";
	String mysqlURL = "localhost"; // PDP Database Address
	String mysqlURL2 = "localhost"; // MPDP's Database Address
	String hostname2 = ""; // MPDP's IP Address
	String pepName = ""; // MPEP Name
	String UserName = "";
	String Password = "";
	MPEPClient mpep;
	CopsprREQ REQ;
	Socket rrmclient = null;
	int decflag = 0;

	public static void main(String[] args) throws IOException {

		// String hostname = "127.0.0.1";
		String mysqlURL = "";
		String UserName = "";
		String Password = "";

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Enter Location of PDP's Database : ");
		mysqlURL = in.readLine();
		// mysqlURL = "localhost";
		System.out.print("Input Database UserName : ");
		// UserName = in.readLine();
		UserName = "root";
		System.out.print("Input Database Password : ");
		// Password = in.readLine();
		Password = "111111";
		System.out.println();

		try {
			PDP pdp = new PDP(mysqlURL, UserName, Password); // Start up a PDP
			pdp.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PDP(String mysqlURL, String username, String password)
			throws RemoteException {

		this.mysqlURL = mysqlURL;
		this.UserName = username;
		this.Password = password;

		pdpSQL pdpP = new pdpSQL(mysqlURL, UserName, Password); // 注册JDBC

		this.pdpP = pdpP;
		int pepCount = pdpP.noPEPs(); // 获得PDP的PEP数量
		if (pepCount == -1) {
			System.out.println("Number of PEPs = 0. Aborting");
			System.exit(1);
		}
		pepSockets = new Socket[pepCount]; // 创建PEP的Socket连接数组

		// To execute PDP processes
		pdp = new CopsprPdpImpl(new EchoMsgHandler(mysqlURL), pepSockets); // 实例化PDP
	}

	public void run() throws IOException {

		String[] statusList;
		Socket[] sockets;
		int[] peerID;
		String currentDomain = "0.0.0.0";
		ServerSocket serverSocket = null;
		int PDPID;
		boolean listening = true;

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int port;
		System.out.print("Enter PDP Number : ");
		PDPID = Integer.parseInt(in.readLine());

		SIBBS_CONNECT sibbs = new SIBBS_CONNECT(mysqlURL); // BBpeer间的连接
		port = sibbs.getCurrentPort(PDPID); //
		// System.out.println("BBServer(RRMServer) #" + PDPID +
		// " runs on port : "
		// + port);
		System.out.println();
		int pdpPort = pdpP.getPort(PDPID); // 获得PEP连接PDP的端口
		// currentDomain = sibbs.getCurrentDomain(PDPID);

		// Simple logging procedure to document system usage
		// File FilePath = new File(this.getClass().getResource("").getPath());
		// System.out.println(FilePath);
		// File file = new File(
		// "E:/eclipse/workspace/ResourceManager/log_yhj/BBLog.txt");
		// if (!file.exists()) {
		// try {
		// file.createNewFile();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// PrintWriter log = new PrintWriter(new FileWriter(
		// "./log_yhj/BBLog.txt", true));
		// String now = new java.util.Date().toString();
		// log.println("(PDP)BB Server #" + PDPID + " started : " + now);
		// log.close();

		// PDP Connect to MPDP First
		System.out.print("Enter Localtion of Database : ");
		// mysqlURL2 = in.readLine();
		mysqlURL2 = "localhost"; //
		System.out.print("Enter MPDP's Hostname : ");
		hostname2 = in.readLine();
		// hostname2 = "localhost"; // MPDP的IP地址
		System.out.print("Enter MPEP's Name : ");
		pepName = in.readLine();
		// pepName = "LA"; // PDPServer作为MPEP连接MPDP的name

		mpep = new MPEPClient(mysqlURL2, hostname2, pepName); // 实例化MPEP
		mpep.run();

		// wait for MPEP connection established
		boolean Catrecv = false;
		while (Catrecv == false) {
			if (mpep.Catflag == 1)
				Catrecv = true;
		}

		

		// The following 3 lines sets up inter-domain communication by
		// connectiong to the peer BB's that are currently online at the
		// time that the current BB is started.

		// sockets = sibbs.connectOnline(PDPID);
		// statusList = sibbs.statusList(PDPID);
		// peerID = sibbs.getPeerID(PDPID);

		// //////////PDP CONNECTION//////////////
		try {
			// open connection
			pdp.init((short) pdpPort, max, pepSockets); // PEP通过pdpPort端口与PDP相连
		} catch (CopsPdpException e) {
			e.printStackTrace();
		}
		System.out.println();
		// /////////////////////////////////////////////////////

		pib = new PIBImpl();
		initIPFilterTable(pib);

		// PEP's NEED TO CONNECT TO PDP BEFORE CLIENTS CONNECT TO BB

		Socket[] peps = pdp.getArray();

		// //////////////START TIMER THREAD TO AUTO-DELETE EXPIRED RARs//////

		// autoDelete ad = new autoDelete(pdp, peps, mysqlURL, sockets, peerID,
		// PDPID, statusList, currentDomain);
		// autoDelete ad = new autoDelete(pdp, peps, mysqlURL,
		// PDPID);
		// ad.start();

		//Wait for PEP connection finished
		boolean Decsend = false;
		while (Decsend == false) {
			if (decflag == 1)
				Decsend = true;
		}

		
		// Listen for incoming client connections and fork off to new thread to
		// process client requests
		
		// RRMClient to MPDPServer(JRRMserver)
				System.out.println();
				System.out.print("Enter Address of JRRMServer : ");
				String addy = in.readLine();
				// String addy = "localhost";
				System.out.print("Enter Port for JRRMServer : ");
				System.out.println();
				// int bbport = Integer.parseInt(in.readLine());
				int bbport = 5555;

				try {
					rrmclient = new Socket(addy, bbport); // 产生一个MBBClient与MBBServer的Socket连接
				} catch (Exception e) {
					System.err.println("Don't know about JRRMServer host: " + addy);
					System.exit(1);
				}

				// Creating the BB Server Socket to accept connections from Clients

				try {
					serverSocket = new ServerSocket(port); // 在Port端口进行监听
					// System.out.println("PDPServer #" + PDPID
					// + " ready to receive requests.");
					System.out
							.println("PDPServer(RRMServer) #"
									+ PDPID
									+ " ready to receive BBClients' requests on port : "
									+ port);
				} catch (IOException e) {
					System.err.println("Could not listen on port: " + port);
					System.exit(-1);
				}

		while (listening) {

			Socket socket = serverSocket.accept();

			// new BBMultiServerThread(pdp, peps, socket, PDPID,
			// mysqlURL).start();
			new BBMultiServerThread(pdp, mpep, rrmclient, peps, socket, PDPID,
					mysqlURL).start();
		}
		serverSocket.close();
	}

	/**
	 * This class handles the COPS messages. Modified to suit our needs.
	 * 
	 */

	class EchoMsgHandler implements CopsMsgHandler {

		// String mysqlURL = "dot.cse.unsw.edu.au";
		String mysqlURL = "localhost";
		Socket[] pepSockets;

		public EchoMsgHandler(String mysqlURL) {
			this.mysqlURL = mysqlURL;
		}

		public void handleREQ(CopsREQ req, Socket s) {
			System.out.println("Received REQ msg : " + req);
		}

		/*
		 * handleREQ PDP收到来自PEP的请求PDP作为MPEP将请求转发至MDPD
		 */
		public void handleREQ(CopsprREQ req, Socket s, Socket[] pepSockets) {
			int reqflag = 1;
			System.out.println("Received REQ msg : " + req);
			System.out.println();
			// try {
			// byte[] prid; // = {1, 3, 6, 1, 2, 3, 1, 1};
			// processREQ(req, s);
			//
			// } catch (CopsPdpException e) {
			// e.printStackTrace();
			// }
			// System.out.println("I have no policy for this request,now I will hand in it to CPDP");

			// BufferedReader in = new BufferedReader(new InputStreamReader(
			// System.in));
			// System.out.println();
			// System.out.print("Enter Location of Database : ");
			// try {
			// mysqlURL2 = in.readLine(); //MPDP
			// } catch (IOException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			// System.out.print("Enter CPEP Number : ");
			// try {
			// pepName = in.readLine(); //MPEP
			// } catch (IOException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }

			// pepName = "LA";
			// mysqlURL2 = "localhost" ;
			// //鐠囥儴顔曠純顔诲瘜鐟曚椒璐熼崷銊ょ瑝閸氬瞼鏁搁懘鎴︽？闁矮淇婄拋鍓х枂閿涘苯鍙炬稉鐠祔sqlURL2娑撶瘖PDP閹碉拷顕惔鏃�殶閹诡喖绨遍崷鏉挎絻閿涘苯宓嗘潻鎰攽CPDP缁嬪绨惃鍕瘜閺堝搫婀撮崸锟界幢
			// String hostname2 = "localhost" ;
			// //hostname2娑旂喍璐烠PDP閸︽澘娼冮敍灞炬Ц娑撹桨绨￠崥鎱峆DP瀵よ櫣鐝泂ocket鏉╃偞甯撮幍锟芥付閿涘苯宓唒ep.connect(pdpAddr,pdpPort)娑擃厾娈憄dpAddr;
			// //閸︺劋绔撮崣鎵暩閼存垳绗傚ù瀣槸閸掑棗鐪伴弮璁圭礉閸欘亪娓剁亸鍡楁勾閸э拷鍏橀弨閫涜礋閳ユ笓ocalhost閳ユ繂宓嗛張顒佹簚閸︽澘娼冮崡鍐插讲閿涳拷
			// cpep = new MPEPClient(mysqlURL2,hostname2,pepName);
			// cpep.run();
			// sending req
			/*
			 * try { Thread.sleep(1000); cpep.sendreq(req); } catch
			 * (InterruptedException e) {e.printStackTrace();}
			 */
			while (true) {
				// System.out.println(decflag);
				// if(mpep.Catflag == 1)
				if (reqflag == 1) {
					mpep.sendreq(req);
					// mpep.Catflag = 0;
					reqflag = 0;
					break;
				} else
					System.out.print("");
			}
			/*
			 * //waiting for dec try { Thread.sleep(1000);
			 * Lpdp.sendMessage(s,cpep.DEC); } catch (InterruptedException e) {
			 * // TODO Auto-generated catch block e.printStackTrace(); } catch
			 * (CopsPdpException e) { // TODO Auto-generated catch block
			 * e.printStackTrace();}
			 */
			// try {
			// Thread.sleep(1000);
			// }catch (InterruptedException e) {e.printStackTrace();}

			while (true) {
				// System.out.println(decflag);
				if (mpep.Decflag == 1) {
					try {
						processDEC(mpep.DEC, s);
						System.out.println("Sending this DECISION to PEP ");
						pdp.sendMessage(s, mpep.DEC);
						mpep.Decflag = 0;
						decflag = 1;
					} catch (CopsPdpException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				} else
					System.out.print("");

			}
		}

		public void handleDEC(CopsDEC dec, Socket s) {
			System.out.println("Received DEC msg: " + dec);
		}

		public void handleRPT(CopsRPT rpt, Socket s) {
			System.out.println("Received RPT msg: " + rpt);
		}

		public void handleDRQ(CopsDRQ drq, Socket s) {
			System.out.println("Received DRQ msg: " + drq);
		}

		public void handleSSQ(CopsSSQ ssq, Socket s) {
			System.out.println("Received SSQ msg: " + ssq);
		}

		// How to handle an incoming PEP connection

		public void handleOPN(CopsOPN opn, Socket s) {

			String pepAddy = s.getInetAddress().toString().substring(1);
			int pepPort = s.getPort();
			String pepID = opn.pepid.pepid;
			System.out.println("Received OPN msg: " + opn);
			System.out.println("Incoming PEP Addy : " + pepAddy);
			System.out.println("Incoming PEP Port : " + pepPort);
			System.out.println("Incoming PEP ID == " + pepID);
			// Accept the incoming PEP connection
			try {
				pdp.accept(s, clientType, (short) 3, pepID, mysqlURL);
			} catch (CopsPdpException e) {
				e.printStackTrace();
			}
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
				pdp.sendMessage(s, ka);
				KACounter++;
				if (KACounter == 3) {
					KACounter = 0;
					pdp.sendMessage(s, new CopsCC((short) 0,
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

	// Process request from PEP

	protected void processREQ(CopsREQ req, Socket s) throws CopsPdpException {

		System.out.println();
		System.out.println("----------------------------------------");

		Enumeration clientSIs = req.getClientSIs();
		while (clientSIs.hasMoreElements()) {
			NamedClientSIReq clientSI = (NamedClientSIReq) clientSIs
					.nextElement();
			Enumeration bindings = clientSI.getData();
			while (bindings.hasMoreElements()) {
				Binding b = (Binding) bindings.nextElement();
				byte[] prid = b.prid.getPRID();
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
					pdp.sendMessage(s, dec);

				} else if (pib.hasPRC(prid)) {

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
							COContext.CONFIGURATION, (short) 0, (short) 0,
							(short) 0, new NamedDecData(decs)) };

					CopsprDEC dec = new CopsprDEC((short) 0, req.getHandle()
							.getHandle(), decpr);
					pdp.sendMessage(s, dec);
				} else {
					// error. PRID or PRC not found.
				}
			}
		}
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
					// lr.addRoute(dst,src);
				}

				if (commandCode == 2) {
					System.out.println("Incoming DEC is a 'REMOVE' DEC");
					// lr.delRoute(dst,src);
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
			// byte prid = (byte) 256;
			byte prid = (byte) 255;

			IpFilterEntry filterEntry = new IpFilterEntry(prid, dstAddr, mask,
					srcAddr, mask, dscp);
			prc.putPRI(prid, filterEntry);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Shutting down of PDP

	public void shutdown() throws RemoteException {

		System.out.println("shutting down...");

		try {
			// closing connection
			pdp.shutdown();

		} catch (CopsPdpException e) {
			e.printStackTrace();
		}
	}
}
