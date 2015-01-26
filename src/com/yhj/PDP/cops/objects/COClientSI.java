package com.yhj.PDP.cops.objects;

import java.io.*;

/**
 * This is the default ClientSI class.
 * Includes: Signaled ClientSI and NamedClientSI
 * It just consists of COHeader and data as byte[].
 * Subclass this in the extension of this COPS protocol
 * and overrides the method parseData(byte[] data) to setup the object.
 */
public class COClientSI extends AbstractCOObject {
  protected byte[] data;

  public static final byte SIGNALED = 1;
  public static final byte NAMED    = 2;

  /**
   * Construct this object based on the given data and C-Type
   */
  public COClientSI (byte[] data, byte ctype) {
    this(new COHeader((short) data.length, CLIENT_SI, (byte) ctype), data);
  }

  /**
   * This leaves the data for the subclass.
   */
  public COClientSI(COHeader coh) {
    super(coh);
  }

  public COClientSI(byte ctype) {
    this(new COHeader((short) 0, CLIENT_SI, ctype));
  }

  /**
   * Construct this object based on the header and the input data.
   *
   * @param coh this object's header.
   * @param bytes the input that makes up the body of this object.
   */
  public COClientSI(COHeader coh, byte[] bytes) {
    super(coh);
    parseData(bytes);
  }

  /**
   * This method setup this object.
   * Override this to setup the object in extension.
   */
  protected void parseData(byte[] data) {
    if (data != null) {
      this.data = new byte[data.length];
      // copying data
      for (int i = 0; i < data.length; i++) {
        this.data[i] = data[i];
      }
    }
  }

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os);

    if (data != null) {
      os.write(data, 0, data.length);

      // padding with zeroes to the next 32-bit boundary
      int remainder = data.length % 4;
      if (remainder != 0)
        pad(os, 4 - remainder);
    }
  }

  public String toString() {
    StringBuffer result = new StringBuffer(super.toString());
    result.append("; data: ").append(data);
    return result.toString();
  }

}
