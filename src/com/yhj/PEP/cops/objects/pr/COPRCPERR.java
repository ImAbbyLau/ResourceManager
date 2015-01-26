package com.yhj.PEP.cops.objects.pr;

import java.io.*;
import com.yhj.PEP.cops.objects.*;

public class COPRCPERR extends AbstractCOPRObject {
  public short errorCode;
  public short errorSubCode;

  public static final short clen = 4;

   // The enumerated list of error code
  public static final short PRI_SPACE_EXHAUSTED = 1;
  public static final short PRI_INSTALLMENT_INVALID = 2;
  public static final short ATTR_VALUE_INVALID = 3;
  public static final short ATTR_VALUE_SUP_LIMITED = 4;
  public static final short ATTR_ENUM_SUP_LIMITED = 5;
  public static final short ATTR_MAX_LENGTH_EXCEEDED = 6;
  public static final short ATTR_REFERENCE_UNKNOWN = 7;
  public static final short PRI_NOTIFY_ONLY = 8;
  public static final short UNKNOWN_PRC = 9;
  public static final short TOO_FEW_ATTRS = 10;
  public static final short INVALID_ATTR_TYPE = 11;
  public static final short DELETED_IN_REF = 12;
  public static final short PRI_SPECIFIC_ERROR = 13;

  public COPRCPERR(short errorCode, short errorSubCode) {
    super(new COHeader(clen, COPRObject.CPERR, (byte) 1));
    this.errorCode = errorCode;
    this.errorSubCode = errorSubCode;
  }

  public COPRCPERR(COHeader coh, byte[] bytes) {
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
    StringBuffer result = new StringBuffer("CPERR").append(super.toString());
    result.append("; Error Code: ").append(errorCode);
    result.append("; Error Sub Code: ").append(errorSubCode);
    return result.toString();
  }

}
