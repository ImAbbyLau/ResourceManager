package com.yhj.PEP.cops.utils;

import java.net.*;
import java.io.*;
import java.lang.String;
import com.yhj.PEP.cops.*;
import com.yhj.PEP.cops.messages.*;

/**
 * This class parse a Cops Message from array of bytes.
 * If you want to change the behaviour for extension of COPS, subclass this
 * and override the method parseXXX(CopsCommonHeader cch, byte[] msg)
 * where XXX is the name of the message.
 * If there are new messages introduced, subclass this and override the method
 * parseUnknown(CopsCommonHeader cch, byte[] msg)
 */

public class CopsMsgParser {

  DataInputStream dis;

  public CopsMsgParser(Socket s) {
    try {
      this.dis = new DataInputStream(s.getInputStream());
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public CopsMessage nextMessage() throws CopsException, IOException {

    CopsMessage result = null;
    CopsCommonHeader cch = null;

    // To translate the byte[] to the CopsCommonHeader
    
    byte vf = dis.readByte();
    byte version = (byte)(vf >> 4);
    byte flag = (byte)(vf - (version << 4));
    byte opcode = dis.readByte();
    short clientType = dis.readShort();
    int msglen = dis.readInt();
    
    //System.out.println(msglen);
    //byte[] msgg = new byte[msglen];
    //dis.read(msgg, 0, msglen);
    //for (int i=0;i <msgg.length;i++)
       //System.out.println(msgg[i]);
    
    byte[] msg = new byte[msglen-8];
    dis.read(msg, 0, msglen-8);
    //for (int i=0;i <msg.length;i++)
    //System.out.print(msg[i]);
    cch = new CopsCommonHeader(version, flag, opcode, clientType, msglen);
    
    switch (opcode) {
    case CopsMessage.REQ:
     result = parseREQ(cch, msg);
     break;
    case CopsMessage.DEC:
      result = parseDEC(cch, msg);
      break;
    case CopsMessage.RPT:
      result = parseRPT(cch, msg);
      break;
    case CopsMessage.DRQ:
      result = parseDRQ(cch, msg);
      break;
    case CopsMessage.SSQ:
      result = parseSSQ(cch, msg);
      break;
    case CopsMessage.OPN:
      result = parseOPN(cch, msg);
      break;
    case CopsMessage.CAT:
      result = parseCAT(cch, msg);
      break;
    case CopsMessage.CC:
      result = parseCC(cch, msg);
      break;
    case CopsMessage.KA:
      result = parseKA(cch, msg);
      break;
    case CopsMessage.SSC:
      result = parseSSC(cch, msg);
      break;
    default:
      result = parseUnknown(cch, msg);
      break;
    }
    return result;
  }

  public CopsMessage parseREQ(CopsCommonHeader cch, byte[] msg)
      throws CopsException {
    return new CopsREQ(cch, msg);
  }

  public CopsMessage parseDEC(CopsCommonHeader cch, byte[] msg)
      throws CopsException {
    return new CopsDEC(cch, msg);
  }

  public CopsMessage parseRPT(CopsCommonHeader cch, byte[] msg)
      throws CopsException {
    return new CopsRPT(cch, msg);
  }

  public CopsMessage parseDRQ(CopsCommonHeader cch, byte[] msg)
      throws CopsException {
    return new CopsDRQ(cch, msg);
  }

  public CopsMessage parseSSQ(CopsCommonHeader cch, byte[] msg)
      throws CopsException {
    return new CopsSSQ(cch, msg);
  }

  public CopsMessage parseOPN(CopsCommonHeader cch, byte[] msg)
      throws CopsException {
    return new CopsOPN(cch, msg);
  }

  public CopsMessage parseCAT(CopsCommonHeader cch, byte[] msg)
      throws CopsException {
    return new CopsCAT(cch, msg);
  }

  public CopsMessage parseCC(CopsCommonHeader cch, byte[] msg)
      throws CopsException {
    return new CopsCC(cch, msg);
  }

  public CopsMessage parseKA(CopsCommonHeader cch, byte[] msg)
      throws CopsException {
    return new CopsKA(cch, msg);
  }

  public CopsMessage parseSSC(CopsCommonHeader cch, byte[] msg)
      throws CopsException {
    return new CopsSSC(cch, msg);
  }

  public CopsMessage parseUnknown(CopsCommonHeader cch, byte[] msg)
      throws CopsException {
    throw new CopsException("Unknown message: type: "+cch.opcode);
  }

}
