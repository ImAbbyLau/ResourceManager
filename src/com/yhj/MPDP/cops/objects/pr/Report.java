package com.yhj.MPDP.cops.objects.pr;

import java.util.*;
import java.io.*;
import com.yhj.MPDP.cops.objects.*;

public class Report {

  //Compulsory
  protected COPRErrorPRID eprid;
  protected COPRCPERR cperr;
  protected Vector bindings = new Vector();

  protected short length = 0; // more convenient for tracking the total length

  public Report(byte[] id, short errorCode, short errorSubCode, Binding[] bindings) {
    this.eprid = new COPRErrorPRID(id);
    this.length += eprid.totalLength();
    this.cperr = new COPRCPERR(errorCode, errorSubCode);
    this.length += cperr.totalLength();
    if (bindings != null) {
      for (int i = 0; i < bindings.length; i++) {
        this.bindings.addElement(bindings[i]);
        incrementLength(bindings[i]);
      }
    }
  }

  public Report(COPRErrorPRID eprid, COPRCPERR cperr) {
    if (eprid != null) {
      this.eprid = eprid;
      length += eprid.totalLength();
    }
    if (cperr != null) {
      this.cperr = cperr;
      length += cperr.totalLength();
    }
  }

  public COPRErrorPRID getErrorPRID() {
    return eprid;
  }

  public COPRCPERR getCPERR() {
    return cperr;
  }

  public Enumeration getBindings() {
    return bindings.elements();
  }

  public void addBinding(Binding b) {
    this.bindings.addElement(b);
    incrementLength(b);
  }

  public short length() {
    return length;
  }

  public void setErrorPRID(COPRErrorPRID eprid) {
    if (this.eprid != null) length -= this.eprid.totalLength();
    this.eprid = eprid;
    length += eprid.totalLength();
  }

  public void setCPERR(COPRCPERR cperr) {
    if (this.cperr != null) length -= this.cperr.totalLength();
    this.cperr = cperr;
    length += cperr.totalLength();
  }

  protected void incrementLength(Binding data) {
    this.length += data.length();
  }

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    // writing compulsory objects
    eprid.writeTo(os);
    cperr.writeTo(os);
    if (bindings != null) {
      for (int i = 0; i < bindings.size(); i++) {
        Binding b = (Binding) bindings.elementAt(i);
        b.writeTo(os);
      }
    }
  }
}
