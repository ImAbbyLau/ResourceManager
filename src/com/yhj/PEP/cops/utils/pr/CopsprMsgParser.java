package com.yhj.PEP.cops.utils.pr;

import java.net.Socket;
import com.yhj.PEP.cops.*;
import com.yhj.PEP.cops.messages.*;
import com.yhj.PEP.cops.messages.pr.*;
import com.yhj.PEP.cops.utils.*;

/**
 * A Message parser for COPS-PR specific messages.
 * Since COPS-PR only defines 3 messages, only need to override those.
 * They are REQ, DEC and RPT
 */
public class CopsprMsgParser extends CopsMsgParser {

  public CopsprMsgParser(Socket s) {
    super(s);
  }

  public CopsMessage parseREQ(CopsCommonHeader cch, byte[] msg)
      throws CopsException {
    return new CopsprREQ(cch, msg);
  }

  public CopsMessage parseDEC(CopsCommonHeader cch, byte[] msg)
      throws CopsException {
    return new CopsprDEC(cch, msg);
  }

  public CopsMessage parseRPT(CopsCommonHeader cch, byte[] msg)
      throws CopsException {
    return new CopsprRPT(cch, msg);
  }
}