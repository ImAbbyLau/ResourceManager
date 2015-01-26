package com.yhj.APDP.cops.pr;

import java.net.Socket;
import com.yhj.APDP.cops.*;
import com.yhj.APDP.cops.messages.*;
import com.yhj.APDP.cops.utils.*;
import com.yhj.APDP.cops.utils.pr.*;

public class CopsprPepImpl extends CopsPepImpl {

  public CopsprPepImpl(CopsMsgHandler handler) {
    super(handler);
  }

  public CopsMsgParser getMsgParser(Socket s) {
    return new CopsprMsgParser(s);
  }

}