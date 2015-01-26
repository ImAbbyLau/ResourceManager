package com.yhj.PEP.cops.pr;

import java.net.Socket;
import com.yhj.PEP.cops.*;
import com.yhj.PEP.cops.messages.*;
import com.yhj.PEP.cops.utils.*;
import com.yhj.PEP.cops.utils.pr.*;

public class CopsprPepImpl extends CopsPepImpl {

  public CopsprPepImpl(CopsMsgHandler handler) {
    super(handler);
  }

  public CopsMsgParser getMsgParser(Socket s) {
    return new CopsprMsgParser(s);
  }

}