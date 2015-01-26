package com.yhj.APDP.cops.messages;

import java.io.*;
import com.yhj.APDP.cops.*;
import com.yhj.APDP.cops.utils.*;
import com.yhj.APDP.cops.objects.*;

public class CopsSSC extends AbstractCopsMessage {
  public COHandle handle;
  public COIntegrity integrity;

  public CopsSSC(CopsCommonHeader cch, byte[] bytes) throws CopsException {
    super(cch);
    // interpret objects found inside SSC message
    CopsObjParser cop = getObjParser(bytes);
    COObject obj;

    // optional components
    while ((obj = cop.nextObject()) != null) {
      switch(obj.getCNum()) {
      case COObject.HANDLE:
        this.handle = (COHandle) obj;
        break;
      case COObject.INTEGRITY:
        this.integrity = (COIntegrity) obj;
        break;
      default:
        // found invalid object
        throw new CopsException("Invalid SSC message: get obj with C-Num: " + obj.getCNum());
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
    if (handle != null) handle.writeTo(os);
    if (integrity != null) integrity.writeTo(os);
  }
}
