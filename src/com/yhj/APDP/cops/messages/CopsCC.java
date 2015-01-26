package com.yhj.APDP.cops.messages;

import java.io.*;
import com.yhj.APDP.cops.*;
import com.yhj.APDP.cops.utils.*;
import com.yhj.APDP.cops.objects.*;

public class CopsCC extends AbstractCopsMessage {
  // Compulsory
  private COError error;
  // Optional Components
  private COPDPAddr pdpRedirAddr;
  private COIntegrity integrity;

  /**
   * Cops Client Close that only has COError object
   */
  public CopsCC(short clientType, short code, short subcode) {
    super(CopsMessage.CC, clientType);
    this.error = new COError(code, subcode);
    setLength(cch.clen + this.error.totalLength());
  }

  public CopsCC(CopsCommonHeader cch, byte[] bytes) throws CopsException {
    super(cch);
    // interpret objects found inside OPN message
    CopsObjParser cop = getObjParser(bytes);
    COObject obj = cop.nextObject();
    // first one should be COError
    if ((obj == null) || (obj.getCNum() != COObject.ERROR)) {
      throw new CopsException("Invalid message, expect COError");
    }
    this.error = (COError) obj;

    // check for other optional objects
    while ((obj = cop.nextObject()) != null) {
      switch (obj.getCNum()) {
      case COObject.REDIRECT:
        this.pdpRedirAddr = (COPDPAddr) obj;
        break;
      case COObject.INTEGRITY:
        this.integrity = (COIntegrity) integrity;
        break;
      default:
        // found invalid object
        throw new CopsException("Invalid object: get C-Num: " + obj.getCNum());
      }
    }
  }

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os); // write the common header
    error.writeTo(os); // write the COError

    // Writing optional components
    if (pdpRedirAddr != null) pdpRedirAddr.writeTo(os);
    if (integrity != null) integrity.writeTo(os);
  }

  public COError getError() {
    return error;
  }

  public COPDPAddr getRedirAddr() {
    return pdpRedirAddr;
  }

  public String toString() {
    StringBuffer result = new StringBuffer("CC: ").append(super.toString());
    result.append(";Error: ").append(error.toString());
    if (pdpRedirAddr != null) {
      result.append(";PdpRedirAddr: ").append(pdpRedirAddr.toString());
    }
    if (integrity != null) {
      result.append(";Integrity: ").append(integrity.toString());
    }

    return result.toString();
  }
}
