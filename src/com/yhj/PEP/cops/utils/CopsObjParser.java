package com.yhj.PEP.cops.utils;

import java.io.*;
import com.yhj.PEP.cops.*;
import com.yhj.PEP.cops.objects.*;

/**
 * This class parse a Cops Object from array of bytes.
 * If you want to change the behaviour for extension of COPS,
 * subclass this and override the method parseXXXX(COHeader coh, byte[] obj)
 * where XXXX is the name of the object.
 * If there is new objects introduced, subclass this and override the method
 * parseUnknown(COHeader coh, byte[] obj)
 */

public class CopsObjParser {
  protected ByteArrayInputStream bais = null;
  protected DataInputStream dis = null;

  /**
   * Create a CopsObjParser based on an array of bytes.
   *
   * @param bytes the input data.
   */
  public CopsObjParser(byte[] bytes) {
    this(bytes, 0, bytes.length);
  }

  /**
   * Create a CopsObjParser based on an array of bytes.
   * The parsing starts at offset and ends at offset+length.
   *
   * @param bytes the input data.
   * @param offset the offset of the first byte to be parsed.
   * @param length the length of the input to be parsed.
   */
  public CopsObjParser(byte[] bytes, int offset, int length) {
    bais = new ByteArrayInputStream(bytes, offset, length);
    dis = new DataInputStream(bais);
  }

  /**
   * Returns the next COObject. This is based from the input bytes in the constructor.
   *
   * @return the next COObject of <code>null</code> if there is no more object.
   * @throws CopsException if the bytes read contain invalid Cops Object.
   */
  public synchronized COObject nextObject() throws CopsException {
    try {
      short length = dis.readShort();
      byte cnum = dis.readByte();
      byte ctype = dis.readByte();
      //short timer = dis.readShort();
      COHeader coh = new COHeader((short) (length - COHeader.clen), cnum, ctype);
      byte[] obj = new byte[length - coh.clen]; // only for the body
      for (int i = 0; i < obj.length; i++) {
        obj[i] = dis.readByte();
      }

      // get the padding used for 32-bit boundary condition
      int remainder = 4 - (length % 4);
      if (remainder != 4) {
        for (int i = 0; i < remainder; i++) dis.readByte();
      }

      // determine the cops object
      return getObject(coh, obj);
    } catch (EOFException e) {
      return null;
    } catch (IOException e) {e.printStackTrace();} // should not get this.

    return null;// should not reach this.
  }

  /**
   * get the COObject based on the cnum field of the header.
   */
  private COObject getObject(COHeader coh, byte[] obj) throws CopsException {
    COObject result = null;

    switch (coh.getCNum()) {
    case COObject.HANDLE:
      result = parseHandle(coh, obj);
      break;
    case COObject.CONTEXT:
      result = parseContext(coh, obj);
      break;
    case COObject.IN_INT:
      result = parseInInterface(coh, obj);
      break;
    case COObject.OUT_INT:
      result = parseOutInterface(coh, obj);
      break;
    case COObject.REASON:
      result = parseReason(coh, obj);
      break;
    case COObject.DECISION:
      result = parseDecision(coh, obj);
      break;
    case COObject.LPDP_DEC:
      result = parseLPDPDecision(coh, obj);
      break;
    case COObject.ERROR:
      result = parseError(coh, obj);
      break;
    case COObject.CLIENT_SI:
      result = parseClientSI(coh, obj);
      break;
    case COObject.KA_TIMER:
      result = parseKATimer(coh, obj);
      break;
    case COObject.PEPID:
      result = parsePEPID(coh, obj);
      break;
    case COObject.REPORT_TYPE:
      result = parseReportType(coh, obj);
      break;
    case COObject.REDIRECT:
      result = parsePDPRedirAddr(coh, obj);
      break;
    case COObject.LAST_PDP:
      result = parseLastPDPAddr(coh, obj);
      break;
    case COObject.ACCT_TIMER:
      result = parseAcctTimer(coh, obj);
      break;
    case COObject.INTEGRITY:
      result = parseIntegrity(coh, obj);
      break;
    default:
      result = parseUnknown(coh, obj);
      break;
    }

    return result;
  }

  protected COObject parseHandle(COHeader coh, byte[] obj)
      throws CopsException {
    return new COHandle(coh, obj);
  }

  protected COObject parseContext(COHeader coh, byte[] obj)
      throws CopsException {
    return new COContext(coh, obj);
  }

  protected COObject parseInInterface(COHeader coh, byte[] obj)
      throws CopsException {
    return new COInOutInterface(coh, obj);
  }

  protected COObject parseOutInterface(COHeader coh, byte[] obj)
      throws CopsException {
    return new COInOutInterface(coh, obj);
  }

  protected COObject parseReason(COHeader coh, byte[] obj)
      throws CopsException {
    return new COReason(coh, obj);
  }

  protected COObject parseDecision(COHeader coh, byte[] obj)
      throws CopsException {
    switch (coh.getCType()) {
    case CODecision.FLAG:
      return new CODecFlag(coh, obj);
    case CODecision.STATELESS_DATA:
      return new CODecData(coh, obj);
    case CODecision.REPLACEMENT_DATA:
      return new CODecData(coh, obj);
    case CODecision.CLIENT_SPECIFIC_DEC_DATA:
      return new CODecData(coh, obj);
    case CODecision.NAMED_DEC_DATA:
      return new CODecData(coh, obj);
    default:
      break;
    }
    return null;
  }

  protected COObject parseLPDPDecision(COHeader coh, byte[] obj)
      throws CopsException {
    return parseDecision(coh, obj);
  }

  protected COObject parseError(COHeader coh, byte[] obj)
      throws CopsException {
    return new COError(coh, obj);
  }

  protected COObject parseClientSI(COHeader coh, byte[] obj)
      throws CopsException {
    return new COClientSI(coh, obj);
  }

  protected COObject parseKATimer(COHeader coh, byte[] obj)
      throws CopsException {
    return new COKATimer(coh, obj);
  }

  protected COObject parsePEPID(COHeader coh, byte[] obj)
      throws CopsException {
    return new COPEPID(coh, obj);
  }

  protected COObject parseReportType(COHeader coh, byte[] obj)
      throws CopsException {
    return new COReportType(coh, obj);
  }

  protected COObject parsePDPRedirAddr(COHeader coh, byte[] obj)
      throws CopsException {
    return new COPDPAddr(coh, obj);
  }

  protected COObject parseLastPDPAddr(COHeader coh, byte[] obj)
      throws CopsException {
    return parsePDPRedirAddr(coh, obj);
  }

  protected COObject parseAcctTimer(COHeader coh, byte[] obj)
      throws CopsException {
    return new COTimer(coh, obj);
  }

  protected COObject parseIntegrity(COHeader coh, byte[] obj)
      throws CopsException {
    return new COHandle(coh, obj);
  }

  protected COObject parseUnknown(COHeader coh, byte[] obj)
      throws CopsException {
    // currently throws exception
    throw new CopsException("Unknown object: C-Num: " + coh.getCNum());
  }
}
