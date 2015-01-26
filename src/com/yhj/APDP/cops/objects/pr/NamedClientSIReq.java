package com.yhj.APDP.cops.objects.pr;

import java.io.*;
import java.util.*;
import com.yhj.APDP.cops.objects.*;

/**
 * This class is used in the Request message and in the Accounting Report msg.
 */
public class NamedClientSIReq extends COClientSI {

  // data in super class is not used. Use this instead
  // this is a list of Binding(s)
  protected Vector data = new Vector();
  protected short length; // more convenient for tracking the total length

  public NamedClientSIReq(Binding[] data) {
    super(NAMED);
    this.length = coh.clen; // length of header
    if (data != null) {
      for (int i = 0; i < data.length; i++) {
        this.data.addElement(data[i]);
        this.length += data[i].length();
      }
    }
    setLength(length);
  }

  /**
   * Construct this object with no data.
   * Data can be added through method addData.
   */
  public NamedClientSIReq() {
    super(NAMED);
    this.length = coh.clen;
    setLength(length);
  }
  public NamedClientSIReq(COHeader coh) {
    super(coh);
    this.length = coh.clen;
    setLength(length);
  }

  public void addData(Binding data) {
    if (data != null) {
      this.data.addElement(data);
      this.length += data.length();
      setLength(this.length);
    }
  }

  public short length() {
    return length;
  }

  public Enumeration getData() {
    return data.elements();
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
      for (int i = 0; i < data.size(); i++) {
        ((Binding) data.elementAt(i)).writeTo(os);
      }
    }
  }

  public String toString() {
    StringBuffer result = new StringBuffer(super.toString());
    result.append("; data: ");
    for (int i = 0; i < data.size(); i++) {
      result.append(data.elementAt(i)).append(';');
    }
    return result.toString();
  }
}
