package com.yhj.PDP.cops.utils.pr;

import java.io.*;
import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.objects.*;
import com.yhj.PDP.cops.objects.pr.*;

/**
 * This class is a parser for COPS-PR Protocol objects.
 * They include: PRID, PPRID, EPD, GPERR, CPERR and ErrorPRID
 */
public class CopsprProtObjParser {
  protected ByteArrayInputStream bais = null;
  protected DataInputStream dis = null;

  /**
   * Create a CopsprProtObjParser based on an array of bytes.
   *
   * @param bytes the input data.
   */
  public CopsprProtObjParser(byte[] bytes) {
    this(bytes, 0, bytes.length);
  }

  /**
   * Create a CopsprProtObjParser based on an array of bytes.
   * The parsing starts at offset and ends at offset+length.
   *
   * @param bytes the input data.
   * @param offset the offset of the first byte to be parsed.
   * @param length the length of the input to be parsed.
   */
  public CopsprProtObjParser(byte[] bytes, int offset, int length) {
    bais = new ByteArrayInputStream(bytes, offset, length);
    dis = new DataInputStream(bais);
  }

  /**
   * Returns the next COPRObject. This is based from the input bytes in the constructor.
   *
   * @return the next COObject of <code>null</code> if there is no more object.
   * @throws CopsException if the bytes read contain invalid Cops Object.
   */
  public synchronized COPRObject nextObject() throws CopsException {
    try {
      short length = dis.readShort();
      byte cnum = dis.readByte();
      byte ctype = dis.readByte();
      COHeader coh = new COHeader((short)(length-COHeader.clen), cnum, ctype);
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
   * get the COPRObject based on the cnum field of the header.
   */
  private COPRObject getObject(COHeader coh, byte[] obj) throws CopsException {
    COPRObject result = null;

    switch (coh.getCNum()) {
    case COPRObject.PRID:
      result = parsePRID(coh, obj);
      break;
    case COPRObject.PPRID:
      result = parsePPRID(coh, obj);
      break;
    case COPRObject.EPD:
      result = parseEPD(coh, obj);
      break;
    case COPRObject.GPERR:
      result = parseGPERR(coh, obj);
      break;
    case COPRObject.CPERR:
      result = parseCPERR(coh, obj);
      break;
    case COPRObject.ERRORPRID:
      result = parseErrorPRID(coh, obj);
      break;
    default:
      result = parseUnknown(coh, obj);
      break;
    }
    return result;
  }

  protected COPRObject parsePRID(COHeader coh, byte[] obj)
      throws CopsException {
    return new COPRPRID(coh, obj);
  }

  protected COPRObject parsePPRID(COHeader coh, byte[] obj)
      throws CopsException {
    return new COPRPPRID(coh, obj);
  }

  protected COPRObject parseEPD(COHeader coh, byte[] obj)
      throws CopsException {
    return new COPREPD(coh, obj);
  }

  protected COPRObject parseGPERR(COHeader coh, byte[] obj)
      throws CopsException {
    return new COPRGPERR(coh, obj);
  }

  protected COPRObject parseCPERR(COHeader coh, byte[] obj)
      throws CopsException {
    return new COPRCPERR(coh, obj);
  }

  protected COPRObject parseErrorPRID(COHeader coh, byte[] obj)
      throws CopsException {
    return new COPRErrorPRID(coh, obj);
  }

  protected COPRObject parseUnknown(COHeader coh, byte[] obj)
      throws CopsException {
    // currently throws exception
    throw new CopsException("Unknown object: S-Num: " + coh.getCNum());
  }
}