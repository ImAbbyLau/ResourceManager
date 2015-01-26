package com.yhj.PDP.cops.objects.pr;

import java.io.*;
import com.yhj.PDP.cops.objects.*;

/**
 * A Decision object for COPS-PR.
 * Only contains NamedDecisionData for the data.
 */
public class Decisionpr extends Decision {

  public short commandCode;

  public Decisionpr(COContext context, CODecFlag flag, NamedDecData ndd) {
    super(context, flag);
    setNamed(ndd);
  }

  public Decisionpr(short rType, short mType, short commandCode, short flags, NamedDecData ndd) {
    super(rType, mType, commandCode, flags);
    this.commandCode=commandCode;
    setNamed(ndd);
  }

  public NamedDecData getNamedDecData() {
    return (NamedDecData) named;
  }

  public String toString() {
    return super.toString();
  }
}
