package com.yhj.PDP.cops.messages.pr;

import java.io.*;
import java.util.*;
import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.messages.*;
import com.yhj.PDP.cops.objects.*;
import com.yhj.PDP.cops.objects.pr.*;
import com.yhj.PDP.cops.utils.*;
import com.yhj.PDP.cops.utils.pr.*;

/**
 * A COPS-PR Report State message.
 * To use this, create a message with just client type, handle, report type
 * and a NamedClientSIRpt object (optional).
 */
public class CopsprRPT extends CopsRPT {

  public CopsprRPT(short clientType, int handle, short reportType) {
    super(clientType, handle, reportType);
  }

  public CopsprRPT(short clientType, int handle, short reportType,
                   NamedClientSIRpt clientSI) {
    super(clientType, handle, reportType, clientSI);
  }

  public CopsprRPT(CopsCommonHeader cch, byte[] bytes) throws CopsException {
    super(cch, bytes);
  }

  /**
   * This method is a factory that returns the default object parser for this class.
   * Since this is a COPS-PR message, it has different object parser.
   */
  protected CopsObjParser getObjParser(byte[] data) {
    return new CopsprObjParser(data);
  }
}
