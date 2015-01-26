package com.yhj.PDP.cops.objects;

import java.io.*;

/**
 * The class PDPRedirAddr
 */
public class COPDPRedirAddr extends COPDPAddr {

  /**
   * Construct this object with the specified address, port no.
   * The C-Type field will be guessed by the length of ip address
   * if length is 16, it is IPv6 address.
   * Otherwise, it is IPv4 and assume length is 4 bytes.
   */
  public COPDPRedirAddr(byte[] addr, short portNo) {
    // if the length is 16, IPv6, otherwise IPv4. No checking done.
    this(addr, portNo, (addr.length == ipv6Length) ? ipv6 : ipv4);
  }

  /**
   * Construct this object with the specified address, port no, and C-Type.
   * C-Type is 1 if IPv4 address, 2 if IPv6 address.
   */
  public COPDPRedirAddr(byte[] addr, short portNo, byte ctype) {
    // currently treat invalid ctype as ipv4. No checking done.
    super(new COHeader((ctype == 2) ? ipv6Length : ipv4Length, REDIRECT, ctype),
          addr, portNo);
  }
}
