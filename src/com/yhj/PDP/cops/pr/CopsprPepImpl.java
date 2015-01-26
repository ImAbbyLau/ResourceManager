package com.yhj.PDP.cops.pr;

import java.net.Socket;
import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.messages.*;
import com.yhj.PDP.cops.utils.*;
import com.yhj.PDP.cops.utils.pr.*;

public class CopsprPepImpl extends CopsPepImpl {

  public CopsprPepImpl(CopsMsgHandler handler) {
    super(handler);
  }

  public CopsMsgParser getMsgParser(Socket s) {
    return new CopsprMsgParser(s);
  }

}