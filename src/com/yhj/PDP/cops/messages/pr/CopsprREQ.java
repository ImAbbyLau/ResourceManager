package com.yhj.PDP.cops.messages.pr;

import java.io.*;
import java.util.*;
import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.messages.*;
import com.yhj.PDP.cops.objects.*;
import com.yhj.PDP.cops.objects.pr.*;
import com.yhj.PDP.cops.utils.*;
import com.yhj.PDP.cops.utils.pr.*;

/**
 * A COPS-PR Request message.
 * To use this, create a message with just client type, handle and context.
 * Then add the configuration request through the method
 * addClientSI(COClientSI clientSI).
 */
public class CopsprREQ extends CopsREQ {

  /**
   * Minimalistic approach. Only initialize Client Handle and Context
   */
 public CopsprREQ(short clientType, int chandle, short rType, short mType) {
    super(clientType, chandle, rType, mType);
  }
  public CopsprREQ(short clientType,String pepID, int chandle, short rType, short mType) {
	 super(clientType, pepID,chandle, rType, mType);
	  
  }

  public CopsprREQ(CopsCommonHeader cch, byte[] bytes) throws CopsException {
    super(cch, bytes);
  }

  /**
   * This method is a factory that returns the default object parser for this class.
   * Since this is a COPS-PR message, it has different object parser.
   */
  protected CopsObjParser getObjParser(byte[] data) {
    return new CopsprObjParser(data);
  }
}
