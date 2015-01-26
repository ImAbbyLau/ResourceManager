package com.yhj.PEP.pib.test;

import java.io.*;
import com.yhj.PEP.cops.utils.*;
import com.yhj.PEP.pib.*;

/**
 * An entry in frwkIpFilterTable.
 * This is modified to suit our needs.
 */
public class IpFilterEntry implements PRI {

  public static byte[] prcIndex = ObjectID.parseFrom("1.3.6.1.2.2.1.3.2");
  public byte prid;

  public byte[] dstAddr;
  public byte[] dstAddrMask;
  public byte[] srcAddr;
  public byte[] srcAddrMask;
  public int dscp;
  public int protocol;
  public int dstL4PortMin;
  public int dstL4PortMax;
  public int srcL4PortMin;
  public int srcL4PortMax;

  public IpFilterEntry(byte[] berEncodedData) {
    BERUtil parser = new BERUtil(berEncodedData);
    parser.nextObject(); prid = (byte) parser.getInteger();
    parser.nextObject(); dstAddr = parser.getArray();
    parser.nextObject(); dstAddrMask = parser.getArray();
    parser.nextObject(); srcAddr = parser.getArray();
    parser.nextObject(); srcAddrMask = parser.getArray();
    parser.nextObject();
    dscp = (parser.getType() == BERUtil.INTEGER)? parser.getInteger() : -1;
    parser.nextObject();
    protocol = (parser.getType() == BERUtil.INTEGER)? parser.getInteger() : -1;
    parser.nextObject();
    dstL4PortMin = (parser.getType() == BERUtil.INTEGER)? parser.getInteger() : -1;
    parser.nextObject();
    dstL4PortMax = (parser.getType() == BERUtil.INTEGER)? parser.getInteger() : -1;
    parser.nextObject();
    srcL4PortMin = (parser.getType() == BERUtil.INTEGER)? parser.getInteger() : -1;
    parser.nextObject();
    srcL4PortMax = (parser.getType() == BERUtil.INTEGER)? parser.getInteger() : -1;
  }

  public IpFilterEntry(byte prid, byte[] dstAddr, byte[] dstAddrMask, byte[] srcAddr,
                       byte[] srcAddrMask) {
    this(prid, dstAddr, dstAddrMask, srcAddr, srcAddrMask, -1, -1, -1, -1, -1, -1);
  }

  public IpFilterEntry(byte prid, byte[] dstAddr, byte[] dstAddrMask, byte[] srcAddr,
                       byte[] srcAddrMask,int dscp) {
    this(prid, dstAddr, dstAddrMask, srcAddr, srcAddrMask, dscp, -1, -1, -1, -1, -1);
  }


  public IpFilterEntry(byte prid, byte[] dstAddr, byte[] dstAddrMask, byte[] srcAddr,
                       byte[] srcAddrMask, int dscp, int protocol, int dstL4PortMin,
                       int dstL4PortMax, int srcL4PortMin, int srcL4PortMax) {
    this.prid = prid;
    this.dstAddr = dstAddr;
    this.dstAddrMask = dstAddrMask;
    this.srcAddr = srcAddr;
    this.srcAddrMask = srcAddrMask;
    this.dscp = dscp;
    this.protocol = protocol;
    this.dstL4PortMin = dstL4PortMin;
    this.dstL4PortMax = dstL4PortMax;
    this.srcL4PortMin = srcL4PortMin;
    this.srcL4PortMax = srcL4PortMax;
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
    os.write(BERUtil.encodeOctetString(dstAddr));
    os.write(BERUtil.encodeOctetString(dstAddrMask));
    os.write(BERUtil.encodeOctetString(srcAddr));
    os.write(BERUtil.encodeOctetString(srcAddrMask));
    os.write(BERUtil.encodeInteger(dscp));
    os.write(BERUtil.encodeInteger(protocol));
    os.write(BERUtil.encodeInteger(dstL4PortMin));
    os.write(BERUtil.encodeInteger(dstL4PortMax));
    os.write(BERUtil.encodeInteger(srcL4PortMin));
    os.write(BERUtil.encodeInteger(srcL4PortMax));
  }

  public String toString() {
    StringBuffer result = new StringBuffer(64);
    result.append("prid: "+prid);
    if (dstAddr != null) {
      result.append("; dstAddr: ");
      for (int i = 0; i < dstAddr.length-1; i++) {
        result.append(dstAddr[i] & 255).append('.');
      }
      result.append(dstAddr[dstAddr.length-1] & 255);
    }
    if (dstAddrMask != null) {
      result.append("; dstAddrMask: ");
      for (int i = 0; i < dstAddrMask.length-1; i++) {
        result.append(dstAddrMask[i] & 255).append('.');
      }
      result.append(dstAddrMask[dstAddrMask.length-1] & 255);
    }
    if (srcAddr != null) {
      result.append("; srcAddr: ");
      for (int i = 0; i < srcAddr.length-1; i++) {
        result.append(srcAddr[i] & 255).append('.');
      }
      result.append(srcAddr[srcAddr.length-1] & 255);
    }
    if (srcAddrMask != null) {
      result.append("; dstAddrMask: ");
      for (int i = 0; i < srcAddrMask.length-1; i++) {
        result.append(srcAddrMask[i] & 255).append('.');
      }
      result.append(srcAddrMask[srcAddrMask.length-1] & 255);
    }
    result.append("; dscp: ").append(dscp);
    result.append("; protocol: ").append(protocol);
    result.append("; dstL4PortMin: ").append(dstL4PortMin);
    result.append("; dstL4PortMax: ").append(dstL4PortMax);
    result.append("; srcL4PortMin: ").append(srcL4PortMin);
    result.append("; srcL4PortMax: ").append(srcL4PortMax);
    // etc etc
    return result.toString();
  }
}