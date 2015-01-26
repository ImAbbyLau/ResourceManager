package com.yhj.APDP.pib.test;

import java.io.*;
import com.yhj.APDP.cops.utils.*;
import com.yhj.APDP.pib.*;

public class IfQueueEntry implements PRI {
  public static byte[] prcIndex = ObjectID.parseFrom("1.3.6.1.2.2.2.1.1.5");

  public byte prid;
  public int bandwidthAllocation;

  public IfQueueEntry(byte[] berEncodedData) {
    BERUtil parser = new BERUtil(berEncodedData);
    parser.nextObject(); prid = (byte) parser.getInteger();
    parser.nextObject();
    if (parser.getType() == BERUtil.INTEGER)
      bandwidthAllocation = parser.getInteger();
  }

  public IfQueueEntry(byte prid, int bandwidth) {
    this.prid = prid;
    this.bandwidthAllocation = bandwidth;
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
    os.write(BERUtil.encodeInteger(bandwidthAllocation));
  }

}