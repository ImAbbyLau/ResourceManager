package com.yhj.PEP.cops.messages;

import java.io.*;
import com.yhj.PEP.cops.*;
import com.yhj.PEP.cops.utils.*;
import com.yhj.PEP.cops.objects.*;

public class CopsRPT extends AbstractCopsMessage {
  //Compulsory
  protected COHandle handle;
  protected COReportType reportType;

  //Optional
  protected COClientSI clientSI;
  protected COIntegrity integrity;

  public CopsRPT(short clientType, int handle, short reportType) {
    this(clientType, handle, reportType, null);
  }

  public CopsRPT(short clientType, int handle, short reportType, COClientSI clientSI){
    super((byte) CopsMessage.RPT, clientType);
    this.handle = new COHandle(handle);
    this.reportType = new COReportType(reportType);
    int length = cch.clen + this.handle.totalLength() + this.reportType.totalLength();
    if (clientSI != null) {
      this.clientSI = clientSI;
      length += clientSI.totalLength();
    }
    setLength(length);
  }

  /**
   * This constructor is for the subclass (extension of COPS)
   * to setup its own data.
   */
  protected CopsRPT(CopsCommonHeader cch) {
    super(cch);
  }

  public CopsRPT(CopsCommonHeader cch, byte[] bytes) throws CopsException {
    super(cch);
    parseData(bytes);
  }

  /**
   * This method setup this object.
   * Override this to setup the object in extension.
   */
  protected void parseData(byte[] bytes) throws CopsException {
    // interpret objects found inside RPT message
    CopsObjParser cop = getObjParser(bytes);
    COObject obj;

    // compulsory objects
    obj = cop.nextObject();
    if ((obj == null) || (obj.getCNum() != COObject.HANDLE)) {
      throw new CopsException("Invalid RPT message, expect Handle");
    }
    obj = cop.nextObject();
    if ((obj == null) || (obj.getCNum() != COObject.REPORT_TYPE)) {
      throw new CopsException("Invalid RPT message, expect Report Type");
    }

    while ((obj = cop.nextObject()) != null) {
      switch (obj.getCNum()) {
      case COObject.CLIENT_SI:
        this.clientSI = (COClientSI) obj;
        break;
      case COObject.INTEGRITY:
        this.integrity = (COIntegrity) obj;
        break;
      default:
        // found invalid object
        throw new CopsException("Invalid RPT message: get obj with C-Num: " + obj.getCNum());
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
    super.writeTo(os); // write the common header
    handle.writeTo(os); // write the COHandle
    reportType.writeTo(os); // write the COReportType

    // Writing optional components
    if (clientSI != null) clientSI.writeTo(os);
    if (integrity != null) integrity.writeTo(os);
  }

  public String toString() {
    StringBuffer result = new StringBuffer("RPT: ").append(super.toString());
    result.append(";Handle: ").append(handle);
    result.append(";Report-Type: ").append(reportType);
    if (clientSI != null) result.append(";ClientSI: ").append(clientSI);
    if (integrity != null) result.append(";Integrity: ").append(integrity);
    return result.toString();
  }

  public COHandle getHandle() {
    return handle;
  }

  public COReportType getReportType() {
    return reportType;
  }

  public COClientSI getClientSI() {
    return clientSI;
  }

}
