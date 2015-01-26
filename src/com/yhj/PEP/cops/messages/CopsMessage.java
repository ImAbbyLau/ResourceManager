package com.yhj.PEP.cops.messages;

/**
 * This class represents COPS message.
 * This is implemented as interface to make it convenient if changes occur
 * for the current message format.
 * Usually, a Cops Message is implemented from AbstractCopsMessage.
 * @see AbstractCopsMessage
 */

public interface CopsMessage {

  // These constants are the defined type of COPS Message.
  // Found in the field  opcode.
  public static final byte REQ = 1;
  public static final byte DEC = 2;
  public static final byte RPT = 3;
  public static final byte DRQ = 4;
  public static final byte SSQ = 5;
  public static final byte OPN = 6;
  public static final byte CAT = 7;
  public static final byte CC  = 8;
  public static final byte KA  = 9;
  public static final byte SSC = 10;

  /**
   * Returns the type of the message. It can be one of the constants defined above.
   *
   * @return the type of this message.
   */
  public int getType();

  /**
   * Returns the total length of the message in number of bytes.
   *
   * @return the length of this message in number of bytes.
   */
  public int length();

  /**
   * Returns the representation of this COPS message as an array of bytes.
   *
   * @return this object as an array of bytes.
   */
  public byte[] toBytes();
}
