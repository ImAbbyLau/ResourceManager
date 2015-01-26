package com.yhj.MPDP.cops.messages;

import java.io.*;
import java.util.*;
import com.yhj.MPDP.cops.*;
import com.yhj.MPDP.cops.utils.*;
import com.yhj.MPDP.cops.objects.*;

public class CopsREQ extends AbstractCopsMessage {
  // Compulsory
  protected COHandle handle = null;
  public COPEPID pepid = null;
  protected COContext context = null;
  // Optional Components
  protected COInOutInterface inInterface = null;
  protected COInOutInterface outInterface = null;
  // a list of ClientSI
  protected Vector clientSIs = new Vector();
  // a list of Decision (LPDP decisions)
  protected Vector decisions = null; // mostly unused.
  protected COIntegrity integrity = null;


  /**
   * Minimalistic approach. Only initialize Client Handle and Context
   */
  public CopsREQ(short clientType, int chandle, short rType, short mType) {
    this(clientType, chandle, rType, mType, null, null);
  }
  
  public CopsREQ(short clientType,String pepID, int chandle, short rType, short mType) {
	    this(clientType,pepID,chandle,rType, mType, null, null);
  }
   
  public CopsREQ(short clientType, String pepID,int chandle, short rType, short mType,
          COInOutInterface inInt, COInOutInterface outInt) {
  super(CopsMessage.REQ, clientType);
  int length = cch.clen;
  this.pepid = new COPEPID(pepID);
  length += pepid.totalLength();
  this.handle = new COHandle(chandle);
  length += handle.totalLength();
  this.context = new COContext(rType, mType);
  length += context.totalLength();

  if (inInt != null) {
  this.inInterface = inInt;
  length += inInt.totalLength();
  }
  if (outInt != null) {
  this.outInterface = outInt;
  length += outInt.totalLength();
  }

  setLength(length);
} 

  public CopsREQ(short clientType, int chandle, short rType, short mType,
                 COInOutInterface inInt, COInOutInterface outInt) {
    super(CopsMessage.REQ, clientType);
    int length = cch.clen;
    this.handle = new COHandle(chandle);
    length += handle.totalLength();
    this.context = new COContext(rType, mType);
    length += context.totalLength();

    if (inInt != null) {
      this.inInterface = inInt;
      length += inInt.totalLength();
    }
    if (outInt != null) {
      this.outInterface = outInt;
      length += outInt.totalLength();
    }

    setLength(length);
  }

  /**
   * This constructor is for the subclass (extension of COPS)
   * to setup its own data.
   */
  protected CopsREQ(CopsCommonHeader cch) {
    super(cch);
  }

  /**
   * Construct this message from a given array of bytes.
   */
  public CopsREQ(CopsCommonHeader cch, byte[] bytes) throws CopsException {
    super(cch);
    parseData(bytes);
  }

  /**
   * This method setup this object.
   * Override this to setup the object in extension.
   */
  protected void parseData(byte[] data) throws CopsException {
    CopsObjParser cop = getObjParser(data);
    COObject obj = null;
    COPEPID pepid = null;
    COContext context = null;
    Decision currentDec = null;

    // compulsory objects
   // obj = cop.nextObject();
    //if ((obj == null) || (obj.getCNum() != COObject.PEPID )) {
     // throw new CopsException("Invalid REQ message, expect PEPID ");
   // }
   // this.pepid = (COPEPID) obj;
    
    obj = cop.nextObject();
    if ((obj == null) || (obj.getCNum() != COObject.HANDLE)) {
      throw new CopsException("Invalid REQ message, expect Handle");
    }
    this.handle = (COHandle) obj;
    obj = cop.nextObject();
    if ((obj == null) || (obj.getCNum() != COObject.CONTEXT)) {
      throw new CopsException("Invalid REQ message, expect Context");
    }
    this.context = (COContext) obj;

    obj = cop.nextObject();
    while (obj != null) {
      switch (obj.getCNum()) {
      case COObject.IN_INT:
        this.inInterface = (COInOutInterface) obj;
        break;
      case COObject.OUT_INT:
        this.outInterface = (COInOutInterface) obj;
        break;
      case COObject.CLIENT_SI:
        this.clientSIs.addElement(obj);
        break;
      case COObject.CONTEXT:
        // possibly a new LPDP Decision object
        context = (COContext) obj;
        // next one must be a Decision: Flag
        obj = cop.nextObject();
        if ((obj == null) || (obj.getCNum() != COObject.DECISION)
              || (obj.getCType() != CODecision.FLAG)) {
          throw new CopsException("Invalid DEC message, expect Dec: Flag");
        }
        currentDec = new Decision(context, (CODecFlag) obj);
        decisions.addElement(currentDec);
        break;
      case COObject.DECISION:
        if (currentDec == null) {
          throw new CopsException("Invalid DEC message, get CODecision without COContext");
        }
        switch (obj.getCType()) {
          case CODecision.STATELESS_DATA:
            currentDec.setStateless((CODecData) obj);
            break;
          case CODecision.REPLACEMENT_DATA:
            currentDec.setReplacement((CODecData) obj);
            break;
          case CODecision.CLIENT_SPECIFIC_DEC_DATA:
            currentDec.setClientSI((CODecData) obj);
            break;
          case CODecision.NAMED_DEC_DATA:
            currentDec.setNamed((CODecData) obj);
            break;
          default:
            String str = "Invalid DEC message, get CODecision with C-Type: " + obj.getCType();
            throw new CopsException(str);
        }
        break;
      case COObject.INTEGRITY:
        this.integrity = (COIntegrity) obj;
        break;
      default:
        // found invalid object
        throw new CopsException("Invalid DEC message: get obj with C-Num: " + obj.getCNum());
      }
      obj = cop.nextObject();
    }
  }

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os);
    // write compulsory
    //pepid.writeTo(os);
    handle.writeTo(os);
    context.writeTo(os);

    // writing optional components
    if (inInterface != null) inInterface.writeTo(os);
    if (outInterface != null) outInterface.writeTo(os);
    if (clientSIs != null) {
      for (int i = 0; i < clientSIs.size(); i++) {
        COClientSI clientSI = (COClientSI) clientSIs.elementAt(i);
        clientSI.writeTo(os);
      }
    }
    if (decisions != null) {
      for (int i = 0; i < decisions.size(); i++) {
        Decision dec = (Decision) decisions.elementAt(i);
        dec.writeTo(os);
      }
    }
    if (integrity != null) integrity.writeTo(os);
  }

  public String toString() {
    StringBuffer result = new StringBuffer("REQ: ").append(super.toString());
    if(pepid != null) result.append(";PEPID: ").append(pepid.toString());
    if(handle != null) result.append(";Handle: ").append(handle.toString());
    if(context != null) result.append(";Context: ").append(context.toString());
    // optionals
    if (clientSIs != null) {
      for (int i = 0; i < clientSIs.size(); i++) {
        result.append(";clientSIs[").append(i).append("]: ");
        result.append(clientSIs.elementAt(i));
      }
    }
    if (decisions != null) {
      for (int i = 0; i < decisions.size(); i++) {
        result.append(";decisions[").append(i).append("]: ");
        result.append(decisions.elementAt(i));
      }
    }
    if (integrity != null) result.append(";Integrity: ").append(integrity.toString());
    return result.toString();
  }

  // a bunch of getters
  public COHandle getHandle() {
    return handle;
  }
  public COContext getContext() {
    return context;
  }
  public COInOutInterface getInInterface() {
    return inInterface;
  }
  public COInOutInterface getOutInterface() {
    return outInterface;
  }
  public Enumeration getClientSIs() {
    return clientSIs.elements();
  }
  public Enumeration getDecisions() {
    return decisions.elements();
  }

  // a bunch of setters and adders
  public void setClientSIs(Vector clientSIs) {
    // remove the length from current clientSIs
    int length = length();
    if (this.clientSIs != null) {
      for (int i = 0; i < this.clientSIs.size(); i++) {
        length -= ((COClientSI) this.clientSIs.elementAt(i)).totalLength();
      }
    }
    this.clientSIs = clientSIs;
    // add the length of the vector
    for (int i = 0; i < clientSIs.size(); i++) {
      length += ((COClientSI) clientSIs.elementAt(i)).totalLength();
    }
    setLength(length);
  }
  public void setDecisions(Vector decisions) {
    // remove the length from current decisions
    int length = length();
    if (this.decisions != null) {
      for (int i = 0; i < this.decisions.size(); i++) {
        length -= ((Decision) this.decisions.elementAt(i)).length();
      }
    }
    this.decisions = decisions;
    // add the length of the vector
    for (int i = 0; i < decisions.size(); i++) {
      length += ((Decision) decisions.elementAt(i)).length();
    }
    setLength(length);
  }
  public void addClientSI(COClientSI clientSI) {
    if (clientSIs == null) clientSIs = new Vector();
    this.clientSIs.addElement(clientSI);
    int length = length();
    length += clientSI.totalLength();
    setLength(length);
  }
  public void addDecision(Decision dec) {
    if (decisions == null) decisions = new Vector();
    this.decisions.addElement(dec);
    int length = length();
    length += dec.length();
    setLength(length);
  }
}

