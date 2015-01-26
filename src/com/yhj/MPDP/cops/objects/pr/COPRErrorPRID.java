package com.yhj.MPDP.cops.objects.pr;

import java.io.*;
import com.yhj.MPDP.cops.objects.*;

public class COPRErrorPRID extends AbstractCOPRObject {
  protected byte[] errorPRID;
  protected byte[] encodedErrorPRID;

  public static final short clen = 4;

  public COPRErrorPRID(byte[] id) {
    super(new COHeader((short) id.length, COPRObject.ERRORPRID, (byte) 1));
    this.errorPRID = new byte[id.length];
    for (int i = 0; i < id.length; i++) {
      this.errorPRID[i] = id[i];
    }
    encodedErrorPRID = com.yhj.MPDP.cops.utils.BERUtil.encodeObjectId(id);
    setLength((short) (encodedErrorPRID.length + COHeader.clen));
  }

  public COPRErrorPRID(COHeader coh, byte[] bytes) {
    super(coh);
    byte[] decoded = com.yhj.MPDP.cops.utils.BERUtil.decodeObjectId(bytes);
    this.errorPRID = new byte[decoded.length];
    for (int i = 0; i < decoded.length; i++) {
      this.errorPRID[i] = decoded[i];
    }
    // copy the encoded error prid
    this.encodedErrorPRID = new byte[bytes.length];
    for (int i = 0; i < bytes.length; i++) {
      this.encodedErrorPRID[i] = bytes[i];
    }
  }

  public byte[] getErrorPRID() {
    return this.errorPRID;
  }

  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os);
    os.write(encodedErrorPRID);

    // padding with zeroes to the next 32-bit boundary
    int remainder = encodedErrorPRID.length % 4;
    if (remainder != 0) {
      remainder = 4 - remainder;
      for (; remainder > 0; remainder--) {
        os.write(0);
      }
    }
  }

  public String toString() {
    StringBuffer result = new StringBuffer("Error PRID").append(super.toString());
    result.append("; Instance Identifier: ");
    if (errorPRID != null) {
      for (int i = 0; i < errorPRID.length; i++) {
        result.append(errorPRID).append(' ');
      }
    }
    return result.toString();
  }

}
