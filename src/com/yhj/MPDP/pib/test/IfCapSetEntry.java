package com.yhj.MPDP.pib.test;

import java.io.*;
import com.yhj.MPDP.cops.utils.*;
import com.yhj.MPDP.pib.*;

public class IfCapSetEntry implements PRI {
  public static byte[] prcIndex = ObjectID.parseFrom("1.3.6.1.2.2.1.2.1");

  public byte prid;
  public String name;
  public byte[] capability; // a prid of the capability

  public IfCapSetEntry(byte[] berEncodedData) {
    BERUtil parser = new BERUtil(berEncodedData);
    parser.nextObject(); prid = (byte) parser.getInteger();
    parser.nextObject();
    if (parser.getType() == BERUtil.OCTET_STRING) name = new String(parser.getArray());
    parser.nextObject();
    if (parser.getType() == BERUtil.OBJECT_ID) capability = parser.getArray();
  }

  public IfCapSetEntry(byte prid, String name, byte[] capability) {
    this.prid = prid;
    this.name = name;
    this.capability = capability;
  }

  public byte[] getPRID() {
    byte[] result = new byte[prcIndex.length + 1];
    for (int i = 0; i < prcIndex.length; i++) {
      result[i] = prcIndex[i];
    }
    result[result.length - 1] = prid;
    return result;
  }

  public byte[] getPRCIndex() {
    return prcIndex;
  }

  /**
   * Return a BER encoded representation of this object as an array of bytes.
   */
  public byte[] toBytes() {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      writeTo(os);
    } catch (IOException e) {e.printStackTrace();}
    return os.toByteArray();
  }

  public void writeTo(OutputStream os) throws IOException {
    os.write(BERUtil.encodeInteger(prid));
    os.write(BERUtil.encodeOctetString(name.getBytes()));
    os.write(BERUtil.encodeObjectId(capability));
  }
}