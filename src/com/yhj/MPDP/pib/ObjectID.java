package com.yhj.MPDP.pib;

import java.util.*;

/**
 * This class represents an Object ID. Which is just an array of integers.
 * However, this class provides parsing from String.
 */

public class ObjectID {

  protected byte[] oid;

  public ObjectID(byte[] oid) {
    if (oid != null) {
      this.oid = new byte[oid.length];
      for (int i = 0; i < oid.length; i++) {
        this.oid[i] = oid[i];
      }
    }
  }

  public ObjectID(String oidStr) {
    oid = parseFrom(oidStr);
  }

  public byte[] getObjectID() {
    return oid;
  }

  public static byte[] parseFrom(String oidStr) {
    StringTokenizer tok = new StringTokenizer(oidStr, ".");
    byte[] result = new byte[tok.countTokens()];
    for (int i = 0; i < result.length; i++) {
      result[i] = (byte) Integer.parseInt(tok.nextToken());
    }
    return result;
  }

  public String toString() {
    StringBuffer result = new StringBuffer(32);
    if (oid != null) {
      result.append(oid[0] & 255); // treat as unsigned byte
      for (int i = 1; i < oid.length; i++) {
        result.append('.').append(oid[i] & 255); // treat as unsigned byte
      }
    }
    return result.toString();
  }
}
