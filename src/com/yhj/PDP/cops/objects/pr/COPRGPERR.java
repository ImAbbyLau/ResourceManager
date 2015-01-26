package com.yhj.PDP.cops.objects.pr;

import java.io.*;
import com.yhj.PDP.cops.objects.*;

public class COPRGPERR extends AbstractCOPRObject {
  public short errorCode;
  public short errorSubCode;

  public static final short clen = 4;

   // The enumerated list of error code
  public static final short AVAIL_MEM_LOW = 1;
  public static final short AVAIL_MEM_EXHAUSTED = 2;
  public static final short UNKNOWN_ASN1_TAG = 3;
  public static final short MAX_MSG_SIZE_EXCEEDED = 4;
  public static final short UNKNOWN_ERROR = 5;
  public static final short MAX_REQUEST_STATES_OPEN = 6;
  public static final short INVALID_ASN1_LENGTH = 7;
  public static final short INVALID_OBJECT_PAD = 8;
  public static final short UNKNOWN_PIB_DATA = 9;
  public static final short UNKNOWN_COPSPR_OBJECT = 10;
  public static final short MALFORMED_DECISION = 11;


  public COPRGPERR(short errorCode, short errorSubCode) {
    super(new COHeader(clen, COPRObject.GPERR, (byte) 1));
    this.errorCode = errorCode;
    this.errorSubCode = errorSubCode;
  }

  public COPRGPERR(COHeader coh, byte[] bytes) {
    super(coh);
    DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
    try {
      errorCode = dis.readShort();
      errorSubCode = dis.readShort();
    } catch (IOException e) {
      e.printStackTrace(); // shouldn't ever catch this.
    }
  }

  public short getErrorCode() {
    return errorCode;
  }

  public short getErrorSubCode() {
    return errorSubCode;
  }

  public void writeTo(OutputStream os) throws IOException {
    DataOutputStream dos = new DataOutputStream(os);
    super.writeTo(os);
    dos.writeShort(errorCode);
    dos.writeShort(errorSubCode);
  }

  public String toString() {
    StringBuffer result = new StringBuffer("GPERR").append(super.toString());
    result.append("; Error Code: ").append(errorCode);
    result.append("; Error Sub Code: ").append(errorSubCode);
    return result.toString();
  }

}
