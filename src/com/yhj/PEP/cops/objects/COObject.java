package com.yhj.PEP.cops.objects;

import java.io.*;

/**
 * This class represents COPS message.
 * This is implemented as interface to make it convenient if changes occur
 * for the current message format.
 * Usually, a Cops Message is implemented from AbstractCopsMessage.
 * @see AbstractCopsMessage
 */


public interface COObject {

  // These constants are the defined type of COPS Message.
  // Found in the field cnum.
  public static final byte HANDLE      = 1;
  public static final byte CONTEXT     = 2;
  public static final byte IN_INT      = 3;
  public static final byte OUT_INT     = 4;
  public static final byte REASON      = 5;
  public static final byte DECISION    = 6;
  public static final byte LPDP_DEC    = 7;
  public static final byte ERROR       = 8;
  public static final byte CLIENT_SI   = 9;
  public static final byte KA_TIMER    = 10;
  public static final byte PEPID       = 11;
  public static final byte REPORT_TYPE = 12;
  public static final byte REDIRECT    = 13;
  public static final byte LAST_PDP    = 14;
  public static final byte ACCT_TIMER  = 15;
  public static final byte INTEGRITY   = 16;

  /**
   * Returns the c-num of the object. It can be one of the constants defined above.
   */
  public byte getCNum();

  /**
   * Returns the c-type of the object.
   */
  public byte getCType();

  /**
   * Returns the total length of the message in number of bytes.
   */
  public short length();

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
