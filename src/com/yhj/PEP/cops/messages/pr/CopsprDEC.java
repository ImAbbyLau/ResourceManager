package com.yhj.PEP.cops.messages.pr;

import java.io.*;
import java.util.*;
import com.yhj.PEP.cops.*;
import com.yhj.PEP.cops.messages.*;
import com.yhj.PEP.cops.objects.*;
import com.yhj.PEP.cops.objects.pr.*;
import com.yhj.PEP.cops.utils.*;
import com.yhj.PEP.cops.utils.pr.*;

/**
 * A COPS-PR Decision message.
 * To use this, create a message with just client type, handle
 * Then add the decisions through the method
 * addDecision(Decision dec).
 */
public class CopsprDEC extends CopsDEC {

  public CopsprDEC(CopsCommonHeader cch, byte[] bytes) throws CopsException {
    super(cch, bytes);
  }

  public CopsprDEC(short clientType, int chandle, Decisionpr[] decpr) {
    super(clientType, chandle, decpr);
  }

  public CopsprDEC(short clientType, int chandle, short ec, short es) {
    super(clientType, chandle, ec, es);
  }

  /**
   * This method is a factory that returns the default object parser for this class.
   * Since this is a COPS-PR message, it has different object parser.
   */
  protected CopsObjParser getObjParser(byte[] data) {
    return new CopsprObjParser(data);
  }
}
