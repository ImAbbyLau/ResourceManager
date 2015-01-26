package com.yhj.PEP.cops.objects;

import java.io.*;

/**
 * The class that contains Address.
 * This is subclassed by COPDPRedirAddr and COLastPDPAddr.
 *
 */
public class COPDPAddr extends AbstractCOObject {
  public byte[] ipAddr;
  public short tcpPortNo;

  public static final byte ipv4 = 1; // C-Type for IPv4 address
  public static final byte ipv6 = 2; // C-Type for IPv6 address

  public static final short ipv4Length = 12; // length if contain IPv4 address
  public static final short ipv6Length = 24; // length if contain IPv6 address

  /**
   * Construct this object with the specified address, port no, and COHeader.
   * Two types of COObject share this class: COPDPRedirAddr and COLastPDPAddr.
   * C-Num is 13 if this object is a PDPRedirAddr, 14 if LastPDPAddr.
   * C-Type is 1 if IPv4 address, 2 if IPv6 address.
   */
  public COPDPAddr(COHeader coh, byte[] addr, short portNo) {
    // copying the address. No checking is done for validity.
    super(coh);
    ipAddr = new byte[addr.length];
    for (int i = 0; i < addr.length; i++) {
      ipAddr[i] = addr[i];
    }
    this.tcpPortNo = portNo;
  }

  public COPDPAddr(COHeader coh, byte[] bytes) {
    super(coh);
    DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
    try {
      switch(coh.getCType()) {
      case 1:
        dis.read(ipAddr,0,4);
        dis.readShort();
        tcpPortNo = dis.readShort();
        break;
      case 2:
        dis.read(ipAddr,0,12);
        tcpPortNo = dis.readShort();
        break;
      }
    } catch (IOException e) {
      e.printStackTrace(); // shouldn't ever catch this.
    }
  }

  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os);
    DataOutputStream dos = new DataOutputStream(os);

    dos.write(ipAddr);
    dos.writeShort(0); // required
    dos.writeShort(tcpPortNo);
  }

  public String toString() {
    StringBuffer result = new StringBuffer(super.toString());
    result.append("; ipAddr: ").append(ipAddr);
    result.append("; tcpPortNo: ").append(tcpPortNo);
    return result.toString();
  }

}
