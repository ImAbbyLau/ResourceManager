package com.yhj.MPDP.cops.messages;

import java.io.*;
import com.yhj.MPDP.cops.*;
import com.yhj.MPDP.cops.objects.*;
import com.yhj.MPDP.cops.utils.*;

public class CopsOPN extends AbstractCopsMessage {
  // Compulsory
  public COPEPID pepid = null;
  // Optional components
  public COClientSI clientSI = null;
  public COPDPAddr lastPdpAddr = null;
  public COIntegrity integrity = null;

  /**
   * Construct the simplest of Open message.
   * Only contains PEPID
   *
   * @param clientType a 16-bit number that identify a client
   * @param pepid the name that uniquely identifies PEP to PDP
   */
  public CopsOPN(short clientType, String pepid) {
    super((byte) CopsMessage.OPN, clientType);
    this.pepid = new COPEPID(pepid);
    // set the message length
    int length = cch.clen + this.pepid.length();
    setLength(length);
  }

  public CopsOPN(CopsCommonHeader cch, byte[] bytes) throws CopsException {
    super(cch);
    // interpret objects found inside OPN message
    CopsObjParser cop = getObjParser(bytes);
    COObject obj = cop.nextObject();
    // first one should be PEPID
    if ((obj == null) || (obj.getCNum() != COObject.PEPID)) {
      throw new CopsException("Invalid OPN message, expect PEPID");
    }
    this.pepid = (COPEPID) obj;

    // check for other optional objects
    while ((obj = cop.nextObject()) != null) {
      switch (obj.getCNum()) {
      case COObject.CLIENT_SI:
        this.clientSI = (COClientSI) obj;
        break;
      case COObject.LAST_PDP:
        this.lastPdpAddr = (COPDPAddr) obj;
        break;
      case COObject.INTEGRITY:
        this.integrity = (COIntegrity) obj;
        break;
      default:
        // found invalid object
        throw new CopsException("Invalid OPN message: get C-Num: " + obj.getCNum());
      }
    }
  }

  /**
   * Construct an OPN message from an array of bytes
   */
  public CopsOPN(byte[] bytes) {
    super(bytes);
  }

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os);
    pepid.writeTo(os);

    // writing optional components
    if (clientSI != null) {
      //clientSI.writeTo();
    }
    if (lastPdpAddr != null) {
      //lastPdpAddr.writeTo();
    }
    if (integrity != null) {
      //integrity.writeTo();
    }
  }

  public String toString() {
    StringBuffer result = new StringBuffer("OPN: ").append(super.toString());
    result.append(";PEPID: ").append(pepid.toString());
    if (clientSI != null) {
      result.append(";ClientSI: ").append(clientSI.toString());
    }
    if (lastPdpAddr != null) {
      result.append(";PDP Redir: ").append(lastPdpAddr.toString());
    }
    if (integrity != null) {
      result.append(";Integrity: ").append(integrity.toString());
    }

    return result.toString();
  }
}
