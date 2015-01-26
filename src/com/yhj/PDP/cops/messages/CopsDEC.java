package com.yhj.PDP.cops.messages;

import java.io.*;
import java.util.*;

import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.utils.*;
import com.yhj.PDP.cops.objects.*;

public class CopsDEC extends AbstractCopsMessage {
  // Compulsory
  protected COHandle handle;
  public COPEPID PEPID;
  // Optional Components
  // either an Error or a list of Decisions
  protected COError error = null;
  protected Vector decisions = null;
  protected COIntegrity integrity = null;
  public Decision[] decs;


  /**
   * Construct the simplest of Decision message.
   * contains Client Handle
   */
  public CopsDEC(short clientType, int chandle) {
    this(clientType, chandle, null);
  }

  /**
   * This constructor is for the subclass (extension of COPS)
   * to setup its own data.
   */
  protected CopsDEC(CopsCommonHeader cch) {
    super(cch);
  }

  /**
   * Construct an Error decision
   */
  public CopsDEC(short clientType, int chandle, short ec, short es) {
    super(CopsMessage.DEC, clientType);
    int length = cch.clen;
    this.handle = new COHandle(chandle);
    length += handle.totalLength();
    this.error = new COError(ec, es);
    length += error.totalLength();

    setLength(length);
  }

  public CopsDEC(short clientType, int chandle, Decision[] decs) {
    super(CopsMessage.DEC, clientType);
    int length = cch.clen;
    this.decs=decs;
    this.handle = new COHandle(chandle);
    length += handle.length();
    if (decs != null) {
      decisions = new Vector(decs.length);
      for (int i = 0; i < decs.length; i++) {
        decisions.addElement(decs[i]);
        length += decs[i].length();
      }
    }

    setLength(length);
  }
  
  public CopsDEC(short clientType, String pepid, int chandle, Decision[] decs) {
	    super(CopsMessage.DEC, clientType);
	    int length = cch.clen;
	    this.decs=decs;
	    this.PEPID=new COPEPID(pepid);
	    length += PEPID.length();
	    this.handle = new COHandle(chandle);
	    length += handle.length();
	    if (decs != null) {
	      decisions = new Vector(decs.length);
	      for (int i = 0; i < decs.length; i++) {
	        decisions.addElement(decs[i]);
	        length += decs[i].length();
	      }
	    }

	    setLength(length);
}

  public CopsDEC(CopsCommonHeader cch, byte[] bytes) throws CopsException {
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
    COContext context = null;
    Decision currentDec = null;

    // compulsory objects
    //obj = cop.nextObject();
    //if ((obj == null) || (obj.getCNum() != COObject.PEPID)) {
     // throw new CopsException("Invalid DEC message, expect PEPID");
   // }
    //this.PEPID = (COPEPID) obj;
    
    
    obj = cop.nextObject();
    if ((obj == null) || (obj.getCNum() != COObject.HANDLE)) {
      throw new CopsException("Invalid DEC message, expect Handle");
    }
    this.handle = (COHandle) obj;

    // optional objects
    obj = cop.nextObject();
    while (obj != null) {
      switch (obj.getCNum()) {
      case COObject.ERROR:
        this.error = (COError) obj;
        break;
      case COObject.CONTEXT:
        // possibly a new Decision object
        context = (COContext) obj;
        // next one must be a Decision: Flag
        obj = cop.nextObject();
        if ((obj == null) || (obj.getCNum() != COObject.DECISION)
              || (obj.getCType() != CODecision.FLAG)) {
          throw new CopsException("Invalid DEC message, expect Dec: Flag");
        }
        currentDec = new Decision(context, (CODecFlag) obj);
        addDecision(currentDec);
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
    //PEPID.writeTo(os);
    handle.writeTo(os);
    // it is either a list of decisions or an error
    if (decisions != null) {
      for (int i = 0; i < decisions.size(); i++) {
        Decision dec = (Decision) decisions.elementAt(i);
        dec.writeTo(os);
      }
    } else if (error != null) {
      error.writeTo(os);
    }
    if (integrity != null) integrity.writeTo(os);
  }

  public String toString() {
    StringBuffer result = new StringBuffer("DEC: ").append(super.toString());
    if(PEPID != null) {
        result.append(";PEPID: ").append(PEPID.toString());
      }
    if(handle != null) {
      result.append(";Handle: ").append(handle.toString());
    }
    if(decisions != null) {
      for(int i = 0; i < decisions.size(); i++) {
        result.append(";Context[").append(i).append("]: ");
        result.append(decisions.elementAt(i));
      }
    } else if (error != null) {
      result.append(";Error: ").append(error.toString());
    }
    if (integrity != null) {
      result.append(";Integrity: ").append(integrity.toString());
    }

    return result.toString();
  }

  // a bunch of getters
  public COHandle getHandle() {
    return this.handle;
  }
  public COError getError() {
    return this.error;
  }
  public Enumeration getDecisions() {
    return this.decisions.elements();
  }

  // a bunch of setters and adders
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
  public void addDecision(Decision dec) {
    if (decisions == null) decisions = new Vector();
    this.decisions.addElement(dec);
    int length = length();
    //length += dec.length();
   // setLength(length);
  }
}
