package com.yhj.PDP.cops.objects.pr;

import java.io.*;
import com.yhj.PDP.cops.objects.*;

public class COPRPRID extends AbstractCOPRObject {
  protected byte[] instanceID;
  // PRID is encoded upon creation. Length of this object is the encoded PRID
  protected byte[] encodedID;

  public COPRPRID(byte[] id) {
    super(new COHeader((short) 0, (byte) COPRObject.PRID, (byte) 1));
    this.instanceID = new byte[id.length];
    for (int i = 0; i < id.length; i++) {
      this.instanceID[i] = id[i];
    }
    encodedID = com.yhj.APDP.cops.utils.BERUtil.encodeObjectId(id);
    setLength((short) (encodedID.length + COHeader.clen));
  }

  public COPRPRID(COHeader coh, byte[] bytes) {
    super(coh);
    byte[] decoded = com.yhj.APDP.cops.utils.BERUtil.decodeObjectId(bytes);
    this.instanceID = new byte[decoded.length];
    for (int i = 0; i < decoded.length; i++) {
      this.instanceID[i] = decoded[i];
    }
    // copy the encoded prid
    this.encodedID = new byte[bytes.length];
    for (int i = 0; i < bytes.length; i++) {
      this.encodedID[i] = bytes[i];
    }
  }

  public byte[] getPRID() {
    return this.instanceID;
  }

  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os);
    os.write(encodedID);

    // padding with zeroes to the next 32-bit boundary
    int remainder = encodedID.length % 4;
    if (remainder != 0) {
      remainder = 4 - remainder;
      for (; remainder > 0; remainder--) {
        os.write(0);
      }
    }
  }

  public String toString() {
    StringBuffer result = new StringBuffer("PRID: ").append(super.toString());
    result.append("; Instance Identifier: ");
    if (instanceID != null) {
      for (int i = 0; i < instanceID.length; i++) {
        result.append(instanceID[i]).append(' ');
      }
    }
    return result.toString();
  }

}
