package com.yhj.APDP.cops.messages;

import java.io.*;
import com.yhj.APDP.cops.*;
import com.yhj.APDP.cops.objects.*;
import com.yhj.APDP.cops.utils.*;

/**
 * This is the basic abstract implementation of CopsMessage.
 * A COPS message usually is inherited from this class.
 * This contains just the CopsCommonHeader
 * <p><b>IMPORTANT</b>: All subclass must override the method writeTo
 * and have at least constructor with argument of CopsCommonHeader and byte[]
 */

public abstract class AbstractCopsMessage implements CopsMessage {
  public CopsCommonHeader cch;

  /* The following Constructor(s) ensure that cch is never null. */
  /**
   * Minimalistic initialization of a CopsMessage.
   * Initialize CopsCommonHeader with only the opcode and clientType.
   *
   * @param opcode the COPS opcode that is set to the CommonHeader
   * @param clientType the client type. Set to CommonHeader
   */
  protected AbstractCopsMessage(byte opcode, short clientType) {
    this.cch = new CopsCommonHeader((byte) CopsConstants.COPS_VERSION, (byte) 0, opcode, clientType);
  }

  protected AbstractCopsMessage(byte opcode, short clientType, int msgLength) {
    this.cch = new CopsCommonHeader((byte) CopsConstants.COPS_VERSION, (byte) 0, opcode, clientType, msgLength);
  }

  /**
   * Initialise this with a given CopsCommonHeader
   *
   * @param cch the CopsCommonHeader that init this object.
   */
  protected AbstractCopsMessage(CopsCommonHeader cch) {
    this.cch = cch;
  }

  /**
   * Construct CopsCommonHeader based on an array of bytes.
   *
   * @param bytes the array of bytes to construct CopsCommonHeader
   */
  protected AbstractCopsMessage(byte[] bytes) {
    this.cch = new CopsCommonHeader(bytes);
  }

  /**
   * Return the header contained in this message.
   *
   * @return the header of this message.
   */
  public CopsCommonHeader getHeader() {
    return cch;
  }

  /**
   * Returns the type of the message. It can be one of the constants defined above.
   *
   * @return the type of this message.
   */
  public int getType() {
    return cch.opcode;
  }

  /**
   * Returns the total length of the message in number of bytes.
   *
   * @return the length of this message in number of bytes.
   */
  public int length() {
    return (cch == null) ? 0: cch.messageLength;
  }

  /**
   * Set the length of this message.
   * This length must be multiple of 4 as message must be aligned to 32-bit
   *
   * @param len the length of this message in number of bytes. must be multiple of 4
   */
  protected void setLength(int length) {
    if ((length % 4) != 0) {
      this.cch.messageLength = ((length / 4) + 1) * 4;
    } else {
      this.cch.messageLength = length;
    }
  }

  /**
   * Return the message as an array of bytes.
   * This method calls the method writeTo that is overriden
   * in concrete message classes.
   *
   * @return the common cops header in bytes.
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
   * Write the common cops header to the given output stream.
   * Override this method for every Cops Message that extends this class.
   * In the overriding method, the first line must call super.writeTo(os)
   * to call this method.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    cch.writeTo(os);
  }

  /**
   * Return the string representation of the CopsCommonHeader.
   *
   * @return String representation of CopsCommonHeader
   */
  public String toString() {
    return cch.toString();
  }

  /**
   * This method is a factory that returns the default object parser for this class.
   * Overrides this in subclass.
   */
  protected CopsObjParser getObjParser(byte[] data) {
    return new CopsObjParser(data);
  }

  /**
   * Set this message to be solicited.
   */
  public void setSolicited(boolean sol) {
    cch.setSolicited(sol);
  }

  /**
   * Return the client type of this message.
   */
  public short getClientType() {
    return cch.clientType;
  }
}
