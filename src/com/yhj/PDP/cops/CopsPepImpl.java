/**
 * @(#) CopsPepImpl.java 1.00 00/09/23
 *
 * Copyright (c) 2000
 *
 * This software is the translation of COPS implementation
 * from Telia Research AB to java.
 * For non-commercial purpose. This may not be redistributed.
 */

package com.yhj.PDP.cops;

import java.io.*;
import java.net.*;
import java.util.*;

import com.yhj.PDP.cops.messages.*;
import com.yhj.PDP.cops.messages.pr.CopsprREQ;
import com.yhj.PDP.cops.utils.*;


public class CopsPepImpl implements CopsPep, CopsConstants {
	// support for muliple PDP
	protected Hashtable connections; // contains mapping between PDP address and
										// the socket
										// caution : It should be protected
	static RecvThread rt;

	/**
	 * the message handler. currently only support one message handler.
	 */
	protected CopsMsgHandler cmh;

	protected Socket sock; // Add By Myself for Mpep send REQ
	protected CopsMessage minemsg; // Add By Myself

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
			socket = new Socket(hostname, port);    //MPEP connect to MPDP
			this.sock = socket; // Add by myself
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
    }
    catch (IOException e) {
      e.printStackTrace();
      throw new CopsPepException(e, ERR_CONTACT);
    }
  }

	public void sendMessage(CopsMessage msg) throws CopsPepException {
		byte[] b;

		System.out.println("Sending " + msg);
		try {
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			if ((b = msg.toBytes()) == null) {
				System.err
						.println("CopsPep.sendMessage CopsMessage.toBytes failed");
				throw new CopsPepException("Invalid message", ERR_PARAMS);
			}
			out.write(b, 0, b.length);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new CopsPepException(e, ERR_CONTACT);
		}
	}    // Add by myself

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
			throw new CopsPepException("Invalid address: " + addr + ":" + port,
					ERR_PARAMS);
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
   * Send a keep alive message.
   *
   */
  public void keepAlive(String addr, short port, short clientType)
      throws CopsPepException {
    Socket sock = getSocket(addr, port);
    if (sock == null) {
      throw new CopsPepException("Invalid address: " + addr + ":" + port, ERR_PARAMS);
    }
    CopsKA ka = new CopsKA(clientType);
    sendMessage(sock, ka);
  }
//  private Socket getSocket(String addr, int port) {
//    return (Socket) connections.get(addr + port);
//  }    //I change the private to public

  public Socket getSocket(String addr, int port) {
	    return (Socket) connections.get(addr + port);
	  }
  public void shutdown() throws CopsPepException {
    rt.close();
  }

  protected CopsMsgParser getMsgParser(Socket s) {
    return new CopsMsgParser(s);
  }


	public void MpepsendREQ(String addr, short port, CopsprREQ req)
			throws CopsPepException {
		// TODO Auto-generated method stub
		Socket sock = getSocket(addr, port);
		if (sock == null) {
			throw new CopsPepException("Invalid address: " + addr + ":" + port,
					ERR_PARAMS);
		}
		sendMessage(sock, req);
	}
public void MpepsendREQ(CopsprREQ req)throws CopsPepException {
//	if(sock == null){
//		throw new CopsPepException("Invalid address: " + addr + ":" + port, ERR_PARAMS);
//	}    Handle Exception
	sendMessage(sock, req);
}

public void CpepsendREQ(String addr, short port, CopsprREQ req)
		throws CopsPepException {
	// TODO Auto-generated method stub
	  Socket sock = getSocket(addr, port);
	  if (sock == null) {
	      throw new CopsPepException("Invalid address: " + addr + ":" + port, ERR_PARAMS);
	    }
	  sendMessage(sock,req );  
	
}

public void CpepsendRPT(String addr, short port, CopsRPT rpt)
		throws CopsPepException {
	// TODO Auto-generated method stub
	
}
} // End class CopsPepImpl