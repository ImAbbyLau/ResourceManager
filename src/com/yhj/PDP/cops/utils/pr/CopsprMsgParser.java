package com.yhj.PDP.cops.utils.pr;

import java.net.Socket;
import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.messages.*;
import com.yhj.PDP.cops.messages.pr.*;
import com.yhj.PDP.cops.utils.*;

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