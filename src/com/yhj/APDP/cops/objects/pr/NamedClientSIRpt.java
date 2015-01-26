package com.yhj.APDP.cops.objects.pr;

import java.io.*;
import java.util.*;
import com.yhj.APDP.cops.objects.*;

public class NamedClientSIRpt extends COClientSI {

  // Optional
  protected COPRGPERR gperr = null;

  // a list of Report
  protected Vector reports = new Vector();

  protected short length; // more convenient for tracking the total length

  public NamedClientSIRpt(COPRGPERR gperr, Report[] reports) {
    super(NAMED);
    this.length = coh.clen;
    this.gperr = gperr;
    this.length += gperr.totalLength();
    if (reports != null) {
      for (int i = 0; i < reports.length; i++) {
        this.reports.addElement(reports[i]);
        this.length += reports[i].length();
      }
    }
    setLength(length);
  }

  public NamedClientSIRpt(short errorCode, short errorSubCode, Report[] report) {
    this(new COPRGPERR(errorCode, errorSubCode), report);
  }

  /**
   * minimalistic approach.
   */
  public NamedClientSIRpt(short errorCode, short errorSubCode) {
    this(new COPRGPERR(errorCode, errorSubCode), null);
  }

  /**
   * Construct only with headers. Either empty data or added later on.
   */
  public NamedClientSIRpt(COHeader coh) {
    super(coh);
    this.length = coh.clen;
    setLength(length);
  }

  public COPRGPERR getGPERR() {
    return gperr;
  }

  public Enumeration getReports() {
    return reports.elements();
  }

  public void setGPERR(COPRGPERR gperr) {
    this.gperr = gperr;
  }

  public void addReport(Report report) {
    if (report != null) {
      reports.addElement(report);
      length += report.length();
    }
    setLength(length);
  }

  public short length() {
    return length;
  }

  /**
   * Write this object to the given output stream.
   *
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    super.writeTo(os);
    if (gperr != null) gperr.writeTo(os);
    if (reports != null) {
      for (int i = 0; i < reports.size(); i++) {
        ((Report) reports.elementAt(i)).writeTo(os);
      }
    }
  }
}
