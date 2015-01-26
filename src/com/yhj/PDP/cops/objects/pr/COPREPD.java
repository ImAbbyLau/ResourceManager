package com.yhj.PDP.cops.objects.pr;

import java.io.*;
import com.yhj.PDP.cops.objects.*;

public class COPREPD extends AbstractCOPRObject {
  public byte[] BEREncPRI;

  public COPREPD(byte[] value) {
    super(new COHeader((short) ((value == null) ? 0 : value.length), COPRObject.EPD, (byte)1));
    if (value != null) {
      this.BEREncPRI = new byte[value.length];
      for (int i = 0; i < value.length; i++) {
        this.BEREncPRI[i] = value[i];
      }
    }
  }

  public COPREPD(COHeader coh, byte[] bytes) {
    super(coh);
    if (bytes != null) {
      this.BEREncPRI = new byte[bytes.length];
      for (int i = 0; i < bytes.length; i++) {
        this.BEREncPRI[i] = bytes[i];
      }
    }
  }

  public byte[] getEncodedPRIValue() {
    return this.BEREncPRI;
  }

  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os);
    if (BEREncPRI != null) {
      os.write(BEREncPRI);

      // padding with zeroes to the next 32-bit boundary
      int remainder = BEREncPRI.length % 4;
      if (remainder != 0)
        pad(os, 4 - remainder);
    }
  }

  public String toString() {
    StringBuffer result = new StringBuffer("EPD: ").append(super.toString());
    result.append("; BER Encoded PRI Value: ");
    if (BEREncPRI != null) {
      for (int i = 0; i < BEREncPRI.length; i++) {
        result.append(BEREncPRI[i]).append(' ');
      }
    }
    return result.toString();
  }

}
