package com.yhj.PEP.cops.objects;

import java.io.*;

public class COHeader {
  protected short length;
  protected byte cnum;
  protected byte ctype;
  public static final short clen = 4;

  /**
   * Initialize the COHeader length, cnum and ctype.
   * The input length does not include the length of this header.
   * This is added automatically.
   *
   * @param length the length of the object WITHOUT this header.
   * @param cnum the C-Num code for this object.
   * @param ctype the C-Type code for this object.
   */
  public COHeader(short length, byte cnum, byte ctype) {
    this.length = (short)(length + clen);
    this.cnum = cnum;
    this.ctype = ctype;
  }

  /**
   * Set the length field of this header.
   * This length includes the length of this header.
   *
   * @param length the length of the object including the header.
   */
  public void setLength(short length) {
    this.length = length;
  }

  public short length() {
    return this.length;
  }

  public byte getCType() {
    return this.ctype;
  }

  public byte getCNum() {
    return this.cnum;
  }


  /**
   * Return this header in array of bytes.
   *
   * @return this object as an array of bytes.
   */
  public byte[] toBytes() {
    byte[] result = null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    try {
      dos.writeShort(length);
      dos.writeByte(cnum);
      dos.writeByte(ctype);

      result = baos.toByteArray();
    } catch (IOException e) {
      // this should never happens, since we are only dealing with ByteArrayOutputStream
      e.printStackTrace();
    } finally {
      try {dos.close();}
      catch (IOException e) {} // ignore if trap exception in closing
    }

    return result;
  }

  /**
   * Write CO Header to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    DataOutputStream dos = new DataOutputStream(os);

    dos.writeShort(length);
    dos.writeByte(cnum);
    dos.writeByte(ctype);
  }

  public String toString() {
    StringBuffer result = new StringBuffer();
    result.append("length: ").append(length);
    result.append("; C-Num: ").append(cnum);
    result.append("; C-Type: ").append(ctype);

    return result.toString();
  }
}
