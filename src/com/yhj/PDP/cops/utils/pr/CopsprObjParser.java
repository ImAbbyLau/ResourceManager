package com.yhj.PDP.cops.utils.pr;

import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.utils.*;
import com.yhj.PDP.cops.objects.*;
import com.yhj.PDP.cops.objects.pr.*;

/**
 * A Message parser for COPS-PR specific objects.
 * Since COPS-PR only defines 3 messages, only need to override those.
 * They are Decision and ClientSI
 */
public class CopsprObjParser extends CopsObjParser {

  /**
   * Create a CopsObjParser based on an array of bytes.
   *
   * @param bytes the input data.
   */
  public CopsprObjParser(byte[] bytes) {
    super(bytes, 0, bytes.length);
  }

  protected COObject parseDecision(COHeader coh, byte[] obj)
      throws CopsException {
    if (coh.getCType() == CODecision.NAMED_DEC_DATA) {
      return new NamedDecData(coh, obj);
    } else if (coh.getCType() == CODecision.FLAG) {
      return super.parseDecision(coh, obj);
    } else {
      String str = "Invalid Decision object. Only support Named Decision Data";
      throw new ParsingException(str);
    }
  }

  protected COObject parseClientSI(COHeader coh, byte[] data)
      throws CopsException {
    COObject result = null;
    CopsprProtObjParser parser = new CopsprProtObjParser(data);
    COPRObject obj = parser.nextObject();
    Binding b = null;

    if (obj == null) return new NamedClientSIReq();
    switch (obj.getSNum()) {
    case COPRObject.PRID:
      // a NamedClientSI with bindings (Request or Accounting)
      result = new NamedClientSIReq(coh);
      COPRPRID prid = (COPRPRID) obj;
      obj = parser.nextObject();
      if ((obj == null) || (obj.getSNum() != COPRObject.EPD)) {
        throw new ParsingException("Invalid NamedClientSI object. expect EPD after PRID");
      }
      ((NamedClientSIReq) result).addData(new Binding(prid, (COPREPD) obj));
      while ((b = getNextBinding(parser)) != null) {
        ((NamedClientSIReq) result).addData(b);
      }
      break;
    case COPRObject.GPERR:
      result = new NamedClientSIRpt(coh);
      ((NamedClientSIRpt) result).setGPERR((COPRGPERR) obj);
      // falls through
    case COPRObject.ERRORPRID:
      if (result == null) result = new NamedClientSIRpt(coh);
      while (obj != null) {
        Report rpt = new Report((COPRErrorPRID) obj, null);
        obj = parser.nextObject();
        if ((obj == null) || (obj.getSNum() != COPRObject.CPERR)) {
          throw new ParsingException("Invalid NamedClientSIRpt. expect CPERR");
        }
        rpt.setCPERR((COPRCPERR) obj);
        ((NamedClientSIRpt) result).addReport(rpt);
        try {
          while ((b = getNextBinding(parser)) != null) {
            rpt.addBinding(b);
          }
        } catch (ParsingException e) {
          if (!(e.getObject() instanceof COPRErrorPRID)) throw e;
          obj = (COPRErrorPRID) e.getObject();
        }
      }
    default:
      throw new ParsingException("Invalid or unsupported ClientSI object");
    }

    return result;
  }

  protected static final Binding getNextBinding(CopsprProtObjParser parser)
      throws CopsException {
    Binding result = null;
    COPRObject obj = parser.nextObject();
    if (obj == null) return null;
    if (obj.getSNum() != COPRObject.PRID) {
      // throw back the object, to be examined by catcher.
      throw new ParsingException("Invalid Binding. Does not start with PRID", obj);
    }
    COPRPRID prid = (COPRPRID) obj;
    obj = parser.nextObject();
    if ((obj == null) || (obj.getSNum() != COPRObject.EPD)) {
      throw new ParsingException("Invalid Binding. Does not end with EPD");
    }
    result = new Binding(prid, (COPREPD) obj);
    return result;
  }
}