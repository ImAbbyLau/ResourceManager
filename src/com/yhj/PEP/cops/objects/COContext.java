package com.yhj.PEP.cops.objects;

import java.io.*;

public class COContext extends AbstractCOObject {

  public short rType;
  public short mType;

  // accepted R-Type
  public static final short INCOMING_MESSAGE = 1;
  public static final short RESOURCE_ALLOCATION = 2;
  public static final short OUTGOING_MESSAGE = 4;
  public static final short CONFIGURATION = 8;

  public COContext(short rType, short mType) {
    super(new COHeader((short) 4, (byte) COObject.CONTEXT, (byte) 1));
    this.rType = rType;
    this.mType = mType;
  }

  public COContext(COHeader coh, byte[] bytes) {
    super(coh);
    DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
    try {
      rType = dis.readShort();
      mType = dis.readShort();
    } catch (IOException e) {
      e.printStackTrace(); // shouldn't ever catch this.
    }
  }

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    DataOutputStream dos = new DataOutputStream(os);

    super.writeTo(os);
    dos.writeShort(rType);
    dos.writeShort(mType);
  }

  public String toString() {
    StringBuffer result = new StringBuffer(super.toString());
    result.append("; rType: ").append(rType);
    result.append("; mType: ").append(mType);
    return result.toString();
  }
}
