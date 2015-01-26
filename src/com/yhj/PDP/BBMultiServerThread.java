package com.yhj.PDP;

import java.net.*;
import java.io.*;
import java.util.*;
import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.pr.*;
import com.yhj.PDP.cops.messages.*;
import com.yhj.PDP.cops.messages.pr.*;
import com.yhj.PDP.cops.utils.*;
import com.yhj.PDP.cops.objects.*;
import com.yhj.PDP.cops.objects.pr.*;

public class BBMultiServerThread extends Thread {

	private Socket socket = null;
	private Socket msocket = null;
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
	MPEPClient mpep;

	public BBMultiServerThread(CopsprPdpImpl pdp, MPEPClient mpep,
			Socket msocket, Socket[] pepSockets, Socket socket, int serverID,
			String mysqlURL) {
		super("BBMultiServerThread");
		this.socket = socket; // BBClient's Socket
		this.msocket = msocket;
		bbID = serverID;
		this.peerBB = peerBB;
		this.peerID = peerID;
		// statusList = status;
		this.mysqlURL = mysqlURL; // PDP的数据库地址
		this.currentDomain = currentDomain;
		this.pdp = pdp;
		this.pepSockets = pepSockets;
		this.mpep = mpep;
	}

	public void run() {

		try {

			// Set up output and input streams for the server.

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
			// Output to BBClient
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream())); // Input from BBClient
			
			PrintWriter outbb = new PrintWriter(msocket.getOutputStream(), true); 
			// Output to MBBClient
			BufferedReader inbb = new BufferedReader(new InputStreamReader(
					msocket.getInputStream())); // Input from MBBClient

			String inputLine = null;
			String outputLogin;
			InetAddress iNetS = socket.getInetAddress(); // 获得BBClient的IP地址
			int clientPort = socket.getPort(); // 获得BBClient的连接Port
			String clientAddy = iNetS.toString();

			boolean ACTIVE = true;

			System.out.println();
			System.out.println("Establishing BBClient Connection From : "
					+ clientAddy.substring(1) + ":" + clientPort);
			System.out.println();

			// log = new PrintWriter(new
			// FileWriter("E:/eclipse/workspace/ResourceManager/log_yhj/BBLog.txt",true));
			// now = new Date().toString();
			// log.println(now + " - Connection established from " +
			// clientAddy.substring(1));
			// log.close();

			// System.out.println("This server knows about : " + peerBB.length +
			// " other servers.");

			BBPass bbpass = new BBPass(mysqlURL);
			BBProtocol bbp = new BBProtocol(mysqlURL); // 处理BBClient的请求

			// Authenticate the user

			String loginS = in.readLine();

			outputLogin = bbpass.checkPass(loginS);

			if (outputLogin.equalsIgnoreCase("Login failed.Restart client.")) {
				ACTIVE = false;
				System.out.println("Incorrect Login from : "
						+ clientAddy.substring(1));
//				now = new Date().toString();
//				log = new PrintWriter(new FileWriter("./logs/BBLog.txt", true));
//				log.println("Incorrect Login from : " + clientAddy.substring(1)
//						+ " at : " + now);
//				log.close();
			}

			out.println(outputLogin); // BBClient登录信息
			// Update the status of peer BB's, if more are online, then connect
			// to them

			// SIBBS_CONNECT sibbs = new SIBBS_CONNECT(mysqlURL);

			// peerBB = sibbs.updatePeers(peerBB,bbID,peerID,statusList);
			// statusList = sibbs.statusList(bbID);

			// Stay in this loop as long as transactions between client and
			// server still needed.

			while (ACTIVE) {

				try {
					inputLine = in.readLine(); // BBClient传来的Option信息
				} catch (SocketException e) {

					ACTIVE = false;
				}

				try {
					if (inputLine.equalsIgnoreCase("exit")) {
						ACTIVE = false;
						break;
					}
					// The following cleanly shutdowns the BB

					if (inputLine.equalsIgnoreCase("shutdown")) {
						System.out.println("About to shutdown");
						out.close();
						in.close();
						System.out.println("Shutting down BB......");
						updateDB off = new updateDB(mysqlURL);

						for (int i = 0; i < peerBB.length; i++) {

							try {
								peerBB[i].close();
								System.out.println("Closing peer # " + (i + 1));
							} catch (Exception e) {
								// System.out.println(e);
							}
						}
						System.out.println();
						System.out
								.println("Connections to peer BB's successfully closed.");
						off.dropPEP(currentDomain, pepSockets, pdp);
						System.out.println("PEP's successfully shutdown.");
						off.shutdownServer(bbID);
						socket.close();
						System.exit(0);
					}
					// peerBB =
					// sibbs.updatePeers(peerBB,bbID,peerID,statusList);
					// statusList = sibbs.statusList(bbID);

					// Process the incoming request in the BBProtocol class

					// String output = bbp.processInput(pdp, pepSockets,
					// inputLine, peerBB, peerID, bbID, statusList,
					// currentDomain);
					System.out.println();
					System.out.println("Input From BBClient:" + inputLine);
					outbb.println(inputLine);    //将BBCLient的请求发给MPDP
					System.out.println();
					System.out.println("OKKKKKK");
					String output = bbp.processInput(pdp, mpep, pepSockets,
							inputLine, peerBB, peerID, bbID, statusList,
							currentDomain);
					// (1)processInput用于处理BBClient的请求；（2）将对Opting的操作信息返还BBClient
					out.println(output);

				} catch (NullPointerException e) {
					ACTIVE = false;
				}

			}

			out.close();
			in.close();
			socket.close();

			System.out
					.println("Connection closed : " + clientAddy.substring(1));
			System.out.println();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
