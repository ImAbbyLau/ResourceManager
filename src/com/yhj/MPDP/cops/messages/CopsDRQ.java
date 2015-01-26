package com.yhj.MPDP.cops.messages;

import java.io.*;
import com.yhj.MPDP.cops.*;
import com.yhj.MPDP.cops.utils.*;
import com.yhj.MPDP.cops.objects.*;

public class CopsDRQ extends AbstractCopsMessage {
  protected COHandle handle;
  protected COReason reason;
  protected COIntegrity integrity;

  public CopsDRQ(CopsCommonHeader cch, COHandle handle, COReason reason){
    super(cch);
    this.handle = handle;
    this.reason = reason;
    setLength(cch.clen + handle.totalLength() + reason.totalLength());
  }

  public CopsDRQ(CopsCommonHeader cch, byte[] bytes) throws CopsException {
    super(cch);
    // interpret objects found inside DRQ message
    CopsObjParser cop = getObjParser(bytes);
    COObject obj = null;

    // compulsory objects
    obj = cop.nextObject();
    if ((obj == null) || (obj.getCNum() != COObject.HANDLE)) {
      throw new CopsException("Invalid DRQ message, expect Handle");
    }
    obj = cop.nextObject();
    if ((obj == null) || (obj.getCNum() != COObject.REASON)) {
      throw new CopsException("Invalid DRQ message, expect Reason");
    }

    // check for other optional objects
    while ((obj = cop.nextObject()) != null) {
      switch (obj.getCNum()) {
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
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os); // write the common header
    // writing compulsory objects
    handle.writeTo(os);
    reason.writeTo(os);
    if (integrity != null) integrity.writeTo(os);
  }
}
