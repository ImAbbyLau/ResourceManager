package com.yhj.PDP.cops.objects;

import java.io.*;

public class COInOutInterface extends AbstractCOObject {
  public byte[] ipv4address = null;
  public byte[] ipv6address = null;
  public int ifindex;

  public static final byte ipv4 = 1; // C-Type for IPv4 address
  public static final byte ipv6 = 2; // C-Type for IPv6 address

  public COInOutInterface(COHeader coh, byte[] bytes) {
    super(coh);
   // TODO
  }

  public void writeTo(OutputStream os) {
    // TODO
  }
}
