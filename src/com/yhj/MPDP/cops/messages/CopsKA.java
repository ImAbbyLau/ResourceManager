package com.yhj.MPDP.cops.messages;

import java.io.*;
import com.yhj.MPDP.cops.*;
import com.yhj.MPDP.cops.utils.*;
import com.yhj.MPDP.cops.objects.*;

public class CopsKA extends AbstractCopsMessage {
  //Optional component
  public COIntegrity integrity = null;

  public CopsKA(short clientType){
    super((byte) CopsMessage.KA, clientType);
    // set the message length
    int length = cch.clen;
    setLength(length);
  }

  public CopsKA(CopsCommonHeader cch, byte[] bytes) throws CopsException {
    super(cch);
    // interpret objects found inside KA message
    CopsObjParser cop = getObjParser(bytes);
    COObject obj;

    // check for other optional objects
    while ((obj = cop.nextObject()) != null) {
      switch (obj.getCNum()) {
      case COObject.INTEGRITY:
        this.integrity = (COIntegrity) obj;
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
    super.writeTo(os);

    // writing optional components
    if (integrity != null) {
      //integrity.writeTo(os);
    }
  }

  public String toString() {
    StringBuffer result = new StringBuffer("KA: ");
    result.append(super.toString());
    if (integrity != null) {
      result.append(";Integrity: ").append(integrity.toString());
    }

    return result.toString();
  }
}
