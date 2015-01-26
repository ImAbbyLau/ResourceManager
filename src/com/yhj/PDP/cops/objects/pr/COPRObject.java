package com.yhj.PDP.cops.objects.pr;

import java.io.*;

/**
 * This class represents COPS-PR message.
 * This is implemented as interface to make it convenient if changes occur
 * for the current message format.
 * Usually, a Cops Message is implemented from AbstractCopsMessage.
 * @see AbstractCopsMessage
 */


public interface COPRObject {

  // These constants are the defined type of COPS-PR Message.
  // Found in the field snum.
  public static final byte PRID      = 1;
  public static final byte PPRID     = 2;
  public static final byte EPD       = 3;
  public static final byte GPERR     = 4;
  public static final byte CPERR     = 5;
  public static final byte ERRORPRID = 6;
  public static final int slen       = 4;
  /**
   * Returns the S-Num of the object. It can be one of the constants defined above.
   */
  public byte getSNum();

  /**
   * Returns the S-Type of the object.
   */
  public byte getSType();

  /**
   * Returns the total length of the message in number of bytes.
   */
  public short length();

  /**
   * Return the total length of the object in number of bytes
   * INCLUDING the padding. This will always be multiple of 4.
   */
  public short totalLength();

  /**
   * Returns the representation of this COPS message as an array of bytes.
   */
  public byte[] toBytes();

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException;
}
