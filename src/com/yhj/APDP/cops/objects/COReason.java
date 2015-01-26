package com.yhj.APDP.cops.objects;

import java.io.*;

public class COReason extends AbstractCOObject {
  protected short reasonCode;
  protected short reasonSubcode;
  public static final short clen = 4;

  public COReason(short code, short subcode) {
    super(new COHeader(clen, REASON, (byte) 1));
    this.reasonCode = code;
    this.reasonSubcode = subcode;
  }

  public COReason(COHeader coh, byte[] bytes) {
    super(coh);
    DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
    try {
      // ignore the first 2 bytes
      reasonCode = dis.readShort();
      reasonSubcode = dis.readShort();
    } catch (IOException e) {
      e.printStackTrace(); // shouldn't ever catch this.
    }
  }

  public void writeTo(OutputStream os) throws IOException {
    DataOutputStream dos = new DataOutputStream(os);

    super.writeTo(os);
    dos.writeShort(reasonCode);
    dos.writeShort(reasonSubcode);
  }
}
