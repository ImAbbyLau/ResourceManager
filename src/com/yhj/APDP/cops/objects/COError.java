package com.yhj.APDP.cops.objects;

import java.io.*;

public class COError extends AbstractCOObject {
  private short code;
  private short subcode;
  public static final short clen = 4;

  // The enumerated list of error code
  public static final short BAD_HANDLE = 1;
  public static final short INVALID_HANDLE_REF = 2;
  public static final short BAD_MESSAGE_FORMAT = 3;
  public static final short UNABLE_TO_PROCESS = 4;
  public static final short CLIENT_INFO_MISSING = 5;
  public static final short UNSUPPORTED_CLIENT_TYPE = 6;
  public static final short COPS_OBJECT_MISSING = 7;
  public static final short CLIENT_FAILURE = 8;
  public static final short COMMUNICATION_FAILURE = 9;
  public static final short UNSPECIFIED = 10;
  public static final short SHUTTING_DOWN = 11;
  public static final short REDIRECT = 12;
  public static final short UNKNOWN_COPS_OBJECT = 13;
  public static final short AUTHENTICATION_FAILURE = 14;
  public static final short AUTHENTICATION_REQUIRED = 15;

  public COError(short ec, short es) {
    super(new COHeader(clen, COObject.ERROR, (byte) 1));
    this.code = ec;
    this.subcode = es;
  }

  public COError(COHeader coh, byte[] bytes) {
    super(coh);
    DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
    try {
      this.code = dis.readShort();
      this.subcode = dis.readShort();
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
    dos.writeShort(code);
    dos.writeShort(subcode);
  }

  public short getErrorCode() {
    return code;
  }

  public short getErrorSubCode() {
    return subcode;
  }

  public void setErrorCode(short code) {
    this.code = code;
  }

  public void setErrorSubCode(short subcode) {
    this.subcode = subcode;
  }

  public String toString() {
    StringBuffer result = new StringBuffer(super.toString());
    result.append("; code: " + code).append("; subcode: " + subcode);

    return result.toString();
  }

}
