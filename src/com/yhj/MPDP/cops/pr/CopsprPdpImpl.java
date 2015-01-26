package com.yhj.MPDP.cops.pr;

import java.net.Socket;
import com.yhj.MPDP.cops.*;
import com.yhj.MPDP.cops.messages.*;
import com.yhj.MPDP.cops.utils.*;
import com.yhj.MPDP.cops.utils.pr.*;

public class CopsprPdpImpl extends CopsPdpImpl {


  public Socket[] pepSockets;


  public CopsprPdpImpl(CopsMsgHandler handler,Socket[] pepSockets) {
    
    super(handler,pepSockets);
    
  }

  public CopsMsgParser getMsgParser(Socket s) {
    return new CopsprMsgParser(s);
  }

}