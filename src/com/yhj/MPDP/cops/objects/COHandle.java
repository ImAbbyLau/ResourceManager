package com.yhj.MPDP.cops.objects;

import java.io.*;

public class COHandle extends AbstractCOObject {

  private int cohData;
  public static short clen = 4;

  public COHandle(int cohData) {
    super(new COHeader((short) 4, (byte) COObject.HANDLE, (byte) 1));
    this.cohData = cohData;
  }

  public COHandle(COHeader coh, byte[] bytes) {
    super(coh);
    DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
    try {
      this.cohData = dis.readInt();
    } catch (IOException e) {
      e.printStackTrace(); // shouldn't ever catch this.
    }
  }

  public int getHandle() {
    return this.cohData;
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
    dos.writeInt(cohData);
  }

  public String toString() {
    StringBuffer result = new StringBuffer(super.toString());
    result.append("; cohData: ").append(cohData);

    return result.toString();
  }
}
