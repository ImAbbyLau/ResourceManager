/**
 * @(#) CopsPdpImpl.java 1.00 00/09/23
 *
 * Copyright (c) 2000
 *
 * This software is the translation of COPS implementation
 * from Telia Research AB to java.
 * For non-commercial purpose. This may not be redistributed.
 */

package com.yhj.MPDP.cops;

import java.net.*;
import java.io.*;
import java.lang.String;
import java.util.*;

import com.yhj.MPDP.cops.messages.*;
import com.yhj.MPDP.cops.objects.Decision;
import com.yhj.MPDP.cops.utils.*;

public class CopsPdpImpl implements CopsPdp, CopsConstants {
	static int maxCon;
	// contains mapping < PEP address, RecvThread >
	// to keep track of all threads initiated by PEP
	protected Hashtable connections; // 未指定Hashtable的Key和Value类型
	/**
	 * the message handler. currently only support one message handler.
	 */
	protected CopsMsgHandler cmh;
	static ServerThread st;
	static ServRecvThread rt;
	public Socket[] mpepSockets;

	public CopsPdpImpl(CopsMsgHandler cmh, Socket[] mpepSockets) {
		connections = new Hashtable();
		maxCon = CopsConstants.COPS_PDP_MAX_CON;
		this.mpepSockets = mpepSockets;
		this.cmh = cmh;

	}

	/*
	 * Init function, this *must* be called before starting to use the library.
	 * Returns false if and only if initialisation fails. A failure probably
	 * means that the computer is out of resources. Some resources may be leaked
	 * on a failure, therefore the process should be terminated.
	 * 
	 * port Port to listen for incoming connections, for example COPS_PORT
	 * (3288). client_types array of supported client types. n_types the length
	 * of the above array max_connections maximum number of PEP connections
	 * allowed. One connection is needed as a buffer (for telling PEPs that it
	 * cannot connect), so this value should not be lower than 2
	 */

	public Socket[] getArray() {
		return mpepSockets;
	}

	public boolean init(short port, int maxConnections, Socket[] mpepSockets)
			throws CopsPdpException {
		/*
		 * Some resources may be leaked if return value is false. This is no
		 * problem since the process cannot continue anyway.
		 */
		maxCon = maxConnections;
		try {
			ServerSocket ss = new ServerSocket(port);

			st = new ServerThread(ss, cmh, mpepSockets);
			st.start();
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		return true;
	}

	public void shutdown() throws CopsPdpException {
		// TODO: closing all connections to the registered PEP
		st.close();
		rt.close();
	}

	public void putConnection(String pepAddr, Socket s) {
		connections.put(pepAddr, s);
	}

	// public RecvThread getConnection(String pepAddr) {
	// return (RecvThread) connections.get(pepAddr);
	// }

	public Enumeration getConnections() {
		return connections.elements();
	}

	protected CopsMsgParser getMsgParser(Socket s) {
		return new CopsMsgParser(s);
	}

	public void sendMessage(Socket sock, CopsMessage msg)
			throws CopsPdpException {
		byte[] b;
		System.out.println("Sending " + msg);

		try {
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			if ((b = msg.toBytes()) == null) {
				System.err
						.println("CopsPdp.sendMessage CopsMessage.toBytes failed");
				throw new CopsPdpException("Invalid message", ERR_PARAMS);
			}
			// System.out.println("sending bytes : " + b + " " + b.length); //
			// 报文的byte形式
			out.write(b, 0, b.length); // 将msg发给sock
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new CopsPdpException(e, ERR_CONTACT);
		}
	}

	public void accept(Socket s, short clientType, short kaTimer,
			String mpepID, String mysqlURL) throws CopsPdpException {
		if (s == null) {
			throw new CopsPdpException("Invalid socket: " + s, ERR_PARAMS);
		}

		PEPIndex mpepIndex = new PEPIndex();
		// int mindex = mpepIndex.Mstatus(pepID, mysqlURL); //检查MPEP的状态
		int mindex = 1;
		if (mindex == -1) {
			System.out
					.println("PEP ERROR. PEP is NOT AUTHORISED. TERMINATING...");
			close(s, clientType, (short) 14, (short) 11);
			return;
		}

		System.out.println("MPEP ID : " + mpepID + " maps to array index "
				+ mindex);

		mpepSockets[mindex] = s;

		CopsCAT cat = new CopsCAT(clientType, kaTimer);
		sendMessage(s, cat);
	}

	public void accept(Socket s, short clientType, short kaTimer)
			throws CopsPdpException {
		if (s == null) {
			throw new CopsPdpException("Invalid socket: " + s, ERR_PARAMS);
		}
		System.out.println("accept length : " + mpepSockets.length);

		CopsCAT cat = new CopsCAT(clientType, kaTimer);
		sendMessage(s, cat);
	}

	public void close(Socket s, short clientType, short errorCode,
			short errorSubcode) throws CopsPdpException {
		CopsCC cc = new CopsCC(clientType, errorCode, errorSubcode);
		sendMessage(s, cc);
	}

	public void keepAlive(Socket s, short clientType) throws CopsPdpException {
		if (s == null) {
			throw new CopsPdpException("Invalid socket: " + s, ERR_PARAMS);
		}
		CopsKA ka = new CopsKA(clientType);
		sendMessage(s, ka);
	}

	public void decision(Socket s, short clientType, int chandle,
			Decision[] decs) throws CopsPdpException {
		if (s == null) {
			throw new CopsPdpException("Invalid socket: " + s, ERR_PARAMS);
		}
		CopsDEC dec = new CopsDEC(clientType, chandle, decs);
		sendMessage(s, dec);
	}

	// A server thread that listens for incoming calls on certain port
	class ServerThread extends Thread {
		ServerSocket ss;
		CopsMsgHandler cmh;
		Socket[] mpepSockets;
		private volatile Thread t;

		ServerThread(ServerSocket ss, CopsMsgHandler cmh, Socket[] mpepSockets) {
			this.ss = ss;
			this.cmh = cmh;
			this.mpepSockets = mpepSockets;
		}

		public void close() {
			st = null;
		}

		public void run() {
			// System.out.println("Listening to port: " + ss.getLocalPort());
			System.out.println("Listening to port: " + ss.getLocalPort()
					+ " for MPEPs' connection");

			Socket sServer = null;
			Thread thisThread = Thread.currentThread();
			try {
				while (st != null) {
					sServer = ss.accept();
					System.out.println();
					System.out.println("About to go to ServRecvThread");
					rt = new ServRecvThread(sServer, cmh,
							getMsgParser(sServer), mpepSockets);
					rt.start();
					connections.put(sServer.getInetAddress().getHostAddress()
							+ sServer.getLocalPort(), rt);
				}
				sServer.close();
				System.out.println("Socket server closed");
			} catch (IOException ioe) {
				System.err.println(ioe.getMessage());
			}
		}
	}
}
