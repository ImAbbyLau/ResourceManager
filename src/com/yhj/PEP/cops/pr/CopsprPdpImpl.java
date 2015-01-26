package com.yhj.PEP.cops.pr;

import java.net.Socket;
import com.yhj.PEP.cops.*;
import com.yhj.PEP.cops.messages.*;
import com.yhj.PEP.cops.utils.*;
import com.yhj.PEP.cops.utils.pr.*;

public class CopsprPdpImpl extends CopsPdpImpl {


  public Socket[] pepSockets;


  public CopsprPdpImpl(CopsMsgHandler handler,Socket[] pepSockets) {
    
    super(handler,pepSockets);
    
  }

  public CopsMsgParser getMsgParser(Socket s) {
    return new CopsprMsgParser(s);
  }

}