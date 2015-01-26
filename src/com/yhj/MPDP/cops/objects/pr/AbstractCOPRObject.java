package com.yhj.MPDP.cops.objects.pr;

import java.io.*;
import com.yhj.MPDP.cops.objects.*;

/**
 * This is the basic abstract implementation of CopsObject.
 * A COPS object usually is inherited from this class.
 * (with the exception of CopsHeader)
 * This contains just the CopsCommonHeader and implement type() and length()
 */

public abstract class AbstractCOPRObject implements COPRObject {
  public COHeader coh;

  protected AbstractCOPRObject(COHeader coh) {
    this.coh = coh;
  }

  /**
   * Returns the type of the object. This is the c-num field of object.
   */
  public byte getSNum() {
    return coh.getCNum();
  }

  /**
   * Return the C-Type field of the object
   */
  public byte getSType() {
    return coh.getCType();
  }

  /**
   * Returns the length of the message in number of bytes.
   * Does not include padding.
   */
  public short length() {
    return (coh == null) ? 0: coh.length();
  }

  /**
   * Set the total length of the message in number of bytes.
   */
  public void setLength(short len) {
    if (coh != null) coh.setLength(len);
  }

  /**
   * Return the total length of the object in number of bytes
   * INCLUDING the padding. This will always be multiple of 4.
   */
  public short totalLength() {
    int result = length();
    if ((result % 4) != 0) {
      result = ((result / 4) + 1) * 4;
    }
    return (short) result;
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
