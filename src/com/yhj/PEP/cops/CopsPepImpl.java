/**
 * @(#) CopsPepImpl.java 1.00 00/09/23
 *
 * Copyright (c) 2000
 *
 * This software is the translation of COPS implementation
 * from Telia Research AB to java.
 * For non-commercial purpose. This may not be redistributed.
 */

package com.yhj.PEP.cops;

import java.io.*;
import java.net.*;
import java.util.*;
import com.yhj.PEP.cops.messages.*;
import com.yhj.PEP.cops.utils.*;

public class CopsPepImpl implements CopsPep, CopsConstants {
  // support for muliple PDP
  protected Hashtable connections; // contains mapping between PDP address and the socket
  static RecvThread rt;
  static int maxCon ;
  static ServerThread st;
  static ServRecvThread rt0;
  public Socket[] pepSockets;
  /**
   * the message handler. currently only support one message handler.
   */
  protected CopsMsgHandler cmh;

  public CopsPepImpl(CopsMsgHandler cmh) {
    connections = new Hashtable();
    this.cmh = cmh;
  }

  /**
   * Establish a connection to a PDP on a given address and port.
   * The socket that results in the connection is stored in a hashtable.
   *
   * @param hostname the address of the PDP
   * @param port the port no PDP is listening to
   * @throws CopsPepException exception when address is not valid, can't establish connection or any other error
   */
  public void connect(String hostname, short port) throws CopsPepException {
    Socket socket = null;
    try {
      socket = new Socket(hostname, port);
      connections.put(hostname + port, socket);
    } catch (UnknownHostException uhe) {
      CopsPepException cpe = new CopsPepException(uhe, ERR_PARAMS);
      throw cpe;
    } catch (IOException ioe) {
      CopsPepException cpe = new CopsPepException(ioe, ERR_CONTACT);
      throw cpe;
    } catch (Exception e) {
      CopsPepException cpe = new CopsPepException(e, ERR_INTERNAL);
      throw cpe;
    }
    rt = new RecvThread(socket, cmh, getMsgParser(socket));
    rt.start();
  }

  /**
   * Send a message over a given socket.
   *
   * @param sock the socket that the connection is using.
   * @param msg the message to be sent
   * @throws CopsPepException thrown if connection or the message is not valid.
   */
  public void sendMessage(Socket sock, CopsMessage msg) throws CopsPepException {
    byte[] b;

    System.out.println("Sending " + msg);
    try {
      DataOutputStream out = new DataOutputStream(sock.getOutputStream());
      if ((b = msg.toBytes()) == null) {
        System.err.println("CopsPep.sendMessage CopsMessage.toBytes failed");
        throw new CopsPepException("Invalid message", ERR_PARAMS);
      }
      out.write(b, 0, b.length);
      out.flush();
     // for (int i=0; i<b.length; i++)
    	 // System.out.print(b[i]);
    }
    catch (IOException e) {
      e.printStackTrace();
      throw new CopsPepException(e, ERR_CONTACT);
    }
  }

  /**
   * A minimal open request. Only requires the address of PDP, client type and pepid.
   *
   * @param addr the address of PDP
   * @param port the port no PDP listens to
   * @param clientType the client type code
   * @param pepid an id for this PEP.
   *
   * @see CopsOPN
   */
  public void open(String addr, short port, short clientType, String pepid)
      throws CopsPepException {
    Socket sock = getSocket(addr, port);
    if (sock == null) {
      throw new CopsPepException("Invalid address: " + addr + ":" + port, ERR_PARAMS);
    }
    CopsOPN opn = new CopsOPN(clientType, pepid);
    sendMessage(sock, opn);
  }

  /**
   * Send a Client Close message.
   */
  public void close(String addr, short port, short clientType,
                    short errorCode, short errorSubcode)
      throws CopsPepException {
    Socket sock = getSocket(addr, port);
    if (sock == null) {
      throw new CopsPepException("Invalid address: " + addr + ":" + port, ERR_PARAMS);
    }
    CopsCC cc = new CopsCC(clientType, errorCode, errorSubcode);
    sendMessage(sock, cc);
  }
  
  /**
   * Send a Report State message.
   *
   */
  public void ReportState(Socket s, short clientType,int handle, short reportType)
  throws CopsPepException {
	  if (s == null) {
	      throw new CopsPepException("Invalid socket: " +s, ERR_PARAMS);
	    }
	  System.out.println();
	  if(reportType == 1){
		  System.out.println("Decision was successful at the PEP");
	  }
	  if(reportType == 2){
		  System.out.println("Decision could not be completed by PEP");
	  }
	  if(reportType == 3){
		  System.out.println("Accounting update for an installed state");
	  }
	  CopsRPT apt = new CopsRPT(clientType, handle, reportType);
	  
	  sendMessage(s, apt);
	  }  
                             
  
  
  
  
  /**
   * Send a keep alive message.
   *
   */
 public void keepAlive(Socket s) 
  throws CopsPepException {
	 short clientType = 0;
	 if (s == null) {
	      throw new CopsPepException("Invalid socket: " +s, ERR_PARAMS);
	    }
 CopsKA ka = new CopsKA(clientType);
 sendMessage(s, ka);
}
  public void keepAlive(String addr, short port, short clientType) 
  throws CopsPepException {
Socket sock = getSocket(addr, port);
if (sock == null) {
  throw new CopsPepException("Invalid address: " + addr + ":" + port, ERR_PARAMS);
}
CopsKA ka = new CopsKA(clientType);
sendMessage(sock, ka);
}
  
  public void keepAlive(String addr, short port) 
      throws CopsPepException {
	  short clientType = 0;
    Socket sock = getSocket(addr, port);
    if (sock == null) {
      throw new CopsPepException("Invalid address: " + addr + ":" + port, ERR_PARAMS);
    }
    CopsKA ka = new CopsKA(clientType);
    sendMessage(sock, ka);
  }
  private Socket getSocket(String addr, int port) {
    return (Socket) connections.get(addr + port);
  }

  public void shutdown() throws CopsPepException {
    rt.close();
  }

  protected CopsMsgParser getMsgParser(Socket s) {
    return new CopsMsgParser(s);
  }
  

  /**
   * pep listening.*/
  public boolean init(short port, int maxConnections,Socket[] pepSockets) throws CopsPepException
  {
    /*
     * Some resources may be leaked if return value is false. This is no
     * problem since the process cannot continue anyway.
     */
    maxCon = maxConnections;
    try {
      ServerSocket ss = new ServerSocket(port);
      
      st = new ServerThread(ss, cmh,pepSockets);
      st.start();
    }
    catch (IOException ioe) {
        System.err.println(ioe.getMessage());
    }
    return true;
  }
  
  class ServerThread extends Thread {
	    ServerSocket ss;
	    CopsMsgHandler cmh;
	    Socket[] pepSockets;
	    private volatile Thread t;

	    ServerThread(ServerSocket ss, CopsMsgHandler cmh,Socket[] pepSockets) {
	      this.ss = ss;
	      this.cmh = cmh;
	      this.pepSockets=pepSockets;
	    }

	    public void close(){
	      st = null;
	    }

	    public void run() {
	      System.out.println("Listening to port: "+ss.getLocalPort());
	      
	      Socket sServer = null;
	      Thread thisThread = Thread.currentThread();
	      try {
	        while (st != null) {
	          sServer = ss.accept();
	          System.out.println("About to go to ServRecvThread");
	          rt0 = new ServRecvThread(sServer, cmh, getMsgParser(sServer),pepSockets);
	          rt0.start();
	          connections.put(sServer.getInetAddress().getHostAddress() + sServer.getLocalPort(), rt0);
	        }
	        sServer.close();
	        System.out.println("Socket server closed");
	      }
	      catch (IOException ioe) {
	        System.err.println(ioe.getMessage());
	      }
	    }
	  }
	
} // End class CopsPepImpl
