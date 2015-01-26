package com.yhj.APDP.cops;

import java.io.*;
import java.net.*;
import com.yhj.APDP.cops.messages.*;
import com.yhj.APDP.cops.utils.*;
import com.yhj.APDP.cops.messages.pr.*;
/**
 * The thread to handle connections to PDP/PEP.
 * This thread receive and parse CopsMessage and pass the message
 * to a given handler.
 */
public class ServRecvThread extends Thread {
  CopsMsgParser cmp = null;
  CopsMsgHandler cmh = null;
  Socket s = null;
  Thread thread;
  Socket[] pepSockets;

  ServRecvThread(Socket s, CopsMsgHandler cmh, CopsMsgParser cmp,Socket[] pepSockets) {
    this.cmp = cmp;
    this.cmh = cmh;
    this.s = s;
    this.pepSockets=pepSockets;
  }
  
  ServRecvThread(Socket s, CopsMsgHandler cmh, CopsMsgParser cmp) {
  	this.cmp=cmp;
  	this.cmh=cmh;
  	this.s=s;
  }

  public void close() {
    thread = null;
  }

  public void run() {
    thread = this;
    System.out.println("CopsPdp.ServRecvThread.run started");
    Thread thisThread = Thread.currentThread();
    
    
    
    while (thread == thisThread) {
      try {
        CopsMessage msg = cmp.nextMessage();
        if (msg == null) throw new CopsException("null message");

        if (cmh == null) continue;
        // handle message based on the type
        switch (msg.getType()) {
        case (CopsMessage.REQ):
          cmh.handleREQ((CopsprREQ) msg, s,pepSockets);
          break;
        case (CopsMessage.DEC):
          cmh.handleDEC((CopsDEC) msg, s);
          break;
        case (CopsMessage.RPT):
          cmh.handleRPT((CopsRPT) msg, s);
          break;
        case (CopsMessage.DRQ):
          cmh.handleDRQ((CopsDRQ) msg, s);
          break;
        case (CopsMessage.SSQ):
          cmh.handleSSQ((CopsSSQ) msg, s);
          break;
        case (CopsMessage.OPN):
          cmh.handleOPN((CopsOPN) msg, s);
          break;
        case (CopsMessage.CAT):
          cmh.handleCAT((CopsCAT) msg, s);
          break;
        case (CopsMessage.CC):
          cmh.handleCC((CopsCC) msg, s);
          break;
        case (CopsMessage.KA):
          cmh.handleKA((CopsKA) msg, s);
          break;
        case (CopsMessage.SSC):
          cmh.handleSSC((CopsSSC) msg, s);
          break;
        default:
          // unknown message
          cmh.handleUnknown(msg, s);
          break;
        }
      } catch (IOException e) {
        // Possibly lost connection
        e.printStackTrace(); break;
      } catch (CopsException e) {
        // invalid message received
        e.printStackTrace();
      }
    }
    try {
      s.close();
    } catch (IOException e) {
        // Possibly lost connection
        e.printStackTrace();
    }
  }
} // End class ServRecvThread

