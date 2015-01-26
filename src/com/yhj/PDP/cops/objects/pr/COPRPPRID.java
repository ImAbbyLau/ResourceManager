package com.yhj.PDP.cops.objects.pr;

import java.io.*;
import com.yhj.PDP.cops.objects.*;

public class COPRPPRID extends AbstractCOPRObject {
  protected byte[] PRIDPrefix;
  protected byte[] encodedPRIDPrefix;

  public COPRPPRID(byte[] prefix) {
    super(new COHeader((short) prefix.length, (byte) COPRObject.EPD, (byte)1));
    this.PRIDPrefix = new byte[prefix.length];
    for (int i = 0; i < prefix.length; i++) {
      this.PRIDPrefix[i] = prefix[i];
    }
    encodedPRIDPrefix = com.yhj.APDP.cops.utils.BERUtil.encodeObjectId(prefix);
    setLength((short) (encodedPRIDPrefix.length + COHeader.clen));
  }

  public COPRPPRID(COHeader coh, byte[] bytes) {
    super(coh);
    byte[] decoded = com.yhj.APDP.cops.utils.BERUtil.decodeObjectId(bytes);
    this.PRIDPrefix = new byte[decoded.length];
    for (int i = 0; i < decoded.length; i++) {
      this.PRIDPrefix[i] = decoded[i];
    }
    // copy the encoded pprid
    this.encodedPRIDPrefix = new byte[bytes.length];
    for (int i = 0; i < bytes.length; i++) {
      this.encodedPRIDPrefix[i] = bytes[i];
    }
  }

  public byte[] getPPRID() {
    return this.PRIDPrefix;
  }

  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os);
    os.write(encodedPRIDPrefix);

    // padding with zeroes to the next 32-bit boundary
    int remainder = encodedPRIDPrefix.length % 4;
    if (remainder != 0) {
      remainder = 4 - remainder;
      for (; remainder > 0; remainder--) {
        os.write(0);
      }
    }
  }

  public String toString() {
    StringBuffer result = new StringBuffer("PPRID: ").append(super.toString());
    result.append("; PRID Prefix: ");
    if (PRIDPrefix != null) {
      for (int i = 0; i < PRIDPrefix.length; i++) {
        result.append(PRIDPrefix).append(' ');
      }
    }
    return result.toString();
  }

}
