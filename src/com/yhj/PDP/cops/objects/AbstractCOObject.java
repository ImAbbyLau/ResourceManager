package com.yhj.PDP.cops.objects;

import java.io.*;

/**
 * This is the basic abstract implementation of CopsObject.
 * A COPS object usually is inherited from this class.
 * (with the exception of CopsHeader)
 * This contains just the COHeader
 * <p><b>IMPORTANT</b>: All subclass must override the method writeTo
 * and have at least constructor with argument of COHeader and byte[]
 */

public abstract class AbstractCOObject implements COObject {
  public COHeader coh;

  protected AbstractCOObject(COHeader coh) {
    this.coh = coh;
  }

  /**
   * Returns the type of the object. This is the c-num field of object.
   */
  public byte getCNum() {
    return coh.cnum;
  }

  /**
   * Return the C-Type field of the object
   */
  public byte getCType() {
    return coh.ctype;
  }

  /**
   * Returns the length of the object in number of bytes.
   */
  public short length() {
    return (coh == null) ? 0: coh.length;
  }

  /**
   * Return the total length of the object in number of bytes
   * INCLUDING the padding. This will always be multiple of 4.
   */
  public int totalLength() {
    int result = length();
    if ((result % 4) != 0) {
      result = ((result / 4) + 1) * 4;
    }
    return result;
  }

  /**
   * Set the length of this object.
   */
  public void setLength(short length) {
    coh.setLength(length);
  }

  /**
   * Returns this object as array of bytes.
   * This method calls the method writeTo that is overriden
   * in concrete object classes.
   *
   * @return an array of bytes.
   */
  public byte[] toBytes() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      writeTo(baos);
    } catch (IOException e) {
      e.printStackTrace(); // should not catch this.
    }

    return baos.toByteArray();
  }

  /**
   * Write CO Header to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    coh.writeTo(os);
  }

  public String toString() {
    return coh.toString();
  }

  /**
   * Write 0 padding to the output stream.
   */
  protected void pad(OutputStream os, int no) throws IOException {
    for (int i = 0; i < no; i++)
      os.write(0);
  }
}
