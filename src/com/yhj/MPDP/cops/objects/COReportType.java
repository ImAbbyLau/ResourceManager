package com.yhj.MPDP.cops.objects;

import java.io.*;

public class COReportType extends AbstractCOObject {
  public short reportType;
  public static final short clen = 4;

  // The enumerated list of report type
  public static final short SUCCESS = 1;
  public static final short FAILURE = 2;
  public static final short ACCOUNTING = 3;
  
  public COReportType (short reportType) {
    super(new COHeader(clen, COObject.REPORT_TYPE, (byte)1));
    this.reportType = reportType;
  }

  public COReportType(COHeader coh, byte[] bytes) {
    super(coh);
    DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
    try {
      dis.readShort();
      reportType = dis.readShort();
    } catch (IOException e) {
      e.printStackTrace(); // shouldn't ever catch this.
    }
  }
  
  public short getReportType() {
    return reportType;
  }
  
  /**
   * Write this object to the given output stream.
   * 
   * @param os the output stream to be written to.
   * @throws IOException if writing outputstream produce an exception.
   */
  public void writeTo(OutputStream os) throws IOException {
    DataOutputStream dos = new DataOutputStream(os);
    
    super.writeTo(os);
    dos.writeShort((short) 0);// must be 0
    dos.writeShort(reportType);
  }

}

