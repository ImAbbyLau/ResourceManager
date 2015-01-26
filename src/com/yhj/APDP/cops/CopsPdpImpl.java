/**
 * @(#) CopsPdpImpl.java 1.00 00/09/23
 *
 * Copyright (c) 2000
 *
 * This software is the translation of COPS implementation
 * from Telia Research AB to java.
 * For non-commercial purpose. This may not be redistributed.
 */

package com.yhj.APDP.cops;

import java.net.*;
import java.io.*;
import java.lang.String;
import java.util.*;
import com.yhj.APDP.cops.messages.*;
import com.yhj.APDP.cops.messages.pr.CopsprREQ;
import com.yhj.APDP.cops.objects.Decision;
import com.yhj.APDP.cops.utils.*;

public class CopsPdpImpl implements CopsPdp, CopsConstants {
  static int maxCon;
  // contains mapping < PEP address, RecvThread >
  // to keep track of all threads initiated by PEP
  protected Hashtable connections;
  /**
   * the message handler. currently only support one message handler.
   */
  protected CopsMsgHandler cmh;
  static ServerThread st;
  static ServRecvThread rt;
  public Socket[] pepSockets;


  public CopsPdpImpl(CopsMsgHandler cmh,Socket[] pepSockets) {
    connections = new Hashtable();
    maxCon = CopsConstants.COPS_PDP_MAX_CON;
    this.pepSockets=pepSockets;
    this.cmh = cmh;
  
  }

  /*
   * Init function, this *must* be called before starting to use the library.
   * Returns false if and only if initialisation fails. A failure probably means
   * that the computer is out of resources. Some resources may be leaked on a
   * failure, therefore the process should be terminated.
   *
   * port
   *      Port to listen for incoming connections, for example COPS_PORT (3288).
   * client_types
   *      array of supported client types.
   * n_types
   *      the length of the above array
   * max_connections
   *      maximum number of PEP connections allowed. One connection is needed as a buffer (for telling PEPs that it cannot connect), so this value should not be lower than 2
   */

  public Socket[] getArray() {
  	return pepSockets;
  }	
  
  private Socket getSocket(String addr, int port) {
	    return (Socket) connections.get(addr + port);
	  }
	
  public boolean init(short port, int maxConnections,Socket[] pepSockets) throws CopsPdpException
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

  

  public void shutdown() throws CopsPdpException {
      // TODO: closing all connections to the registered PEP
      //st.close();
      //rt.close();
  }

  public void putConnection(String pepAddr, Socket s) {
    connections.put(pepAddr, s);
  }

  public RecvThread getConnection(String pepAddr) {
    return (RecvThread) connections.get(pepAddr);
  }

  public Enumeration getConnections() {
    return connections.elements();
  }

  protected CopsMsgParser getMsgParser(Socket s) {
    return new CopsMsgParser(s);
  }

  public void sendMessage(Socket sock, CopsMessage msg) throws CopsPdpException {
    byte[] b;
    System.out.println("Sending " + msg);

    try {
      DataOutputStream out = new DataOutputStream(sock.getOutputStream());
      if ((b = msg.toBytes()) == null) {
        System.err.println("CopsPdp.sendMessage CopsMessage.toBytes failed");
        throw new CopsPdpException("Invalid message", ERR_PARAMS);
      }
      out.write(b, 0, b.length);
      out.flush();
    }
    catch (IOException e) {
      e.printStackTrace();
      throw new CopsPdpException(e, ERR_CONTACT);
    }
  }
  
  /**
   * Lpdp Send a DEC message to Lpep.
   */
  public void connect(String hostname, int port) throws CopsPdpException {
	    Socket socket = null;
	    try {
	      socket = new Socket(hostname, port);
	      connections.put(hostname + port, socket);
	    } catch (UnknownHostException uhe) {
	      CopsPdpException cpe = new CopsPdpException(uhe, ERR_PARAMS);
	      throw cpe;
	    } catch (IOException ioe) {
	      CopsPdpException cpe = new CopsPdpException(ioe, ERR_CONTACT);
	      throw cpe;
	    } catch (Exception e) {
	      CopsPdpException cpe = new CopsPdpException(e, ERR_INTERNAL);
	      throw cpe;
	    }
  }
  
  public void LpdpsendDEC(String addr, int port,CopsDEC dec) throws CopsPdpException {
	  
	 Socket  sock = getSocket(addr, port);
	   
	  if (sock == null) {
	      throw new CopsPdpException("Invalid address: " + addr + ":" + port, ERR_PARAMS);
	    }
	  sendMessage(sock,dec);  
  }

  
  public void accept(Socket s, short clientType, short kaTimer,String pepID,String mysqlURL)
      throws CopsPdpException {
    if (s == null) {
      throw new CopsPdpException("Invalid socket: " +s, ERR_PARAMS);
    }
    
    
    PEPIndex pepIndex = new PEPIndex();
    int index = pepIndex.Mstatus(pepID,mysqlURL);
    
    if (index==-1) {
    	System.out.println("PEP ERROR. PEP is NOT AUTHORISED. TERMINATING...");
    	close(s,clientType,(short) 14,(short) 11);
    	return;
    }
    
    
    //System.out.println("PEP ID : " + pepID + " maps to array index " + index);
    
    
    
    pepSockets[index] = s;
   
      
    CopsCAT cat = new CopsCAT(clientType, kaTimer);
    sendMessage(s, cat);
  }
  
   public void accept(Socket s, short clientType, short kaTimer)
      throws CopsPdpException {
    if (s == null) {
      throw new CopsPdpException("Invalid socket: " +s, ERR_PARAMS);
    }
    System.out.println("accept length : " + pepSockets.length);

    CopsCAT cat = new CopsCAT(clientType, kaTimer);
    sendMessage(s, cat);
  }
  
  public void close(Socket s, short clientType, short errorCode,
                    short errorSubcode)
      throws CopsPdpException {
    CopsCC cc = new CopsCC(clientType, errorCode, errorSubcode);
    sendMessage(s, cc);
  }

  public void keepAlive(Socket s, short clientType) throws CopsPdpException {
    if (s == null) {
      throw new CopsPdpException("Invalid socket: " +s, ERR_PARAMS);
    }
    CopsKA ka = new CopsKA(clientType);
    sendMessage(s, ka);
  }

  public void decision(Socket s, short clientType, int chandle, Decision[] decs)
      throws CopsPdpException {
    if (s == null) {
      throw new CopsPdpException("Invalid socket: " +s, ERR_PARAMS);
    }
    CopsDEC dec = new CopsDEC(clientType, chandle, decs);
    sendMessage(s, dec);
  }

  // A server thread that listens for incoming calls on certain port
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
          System.out.println();
          System.out.println("About to go to ServRecvThread");
          rt = new ServRecvThread(sServer, cmh, getMsgParser(sServer),pepSockets);
          rt.start();
          connections.put(sServer.getInetAddress().getHostAddress() + sServer.getLocalPort(), rt);
        }
        sServer.close();
        System.out.println("Socket server closed");
      }
      catch (IOException ioe) {
        System.err.println(ioe.getMessage());
      }
    }
  }
}
