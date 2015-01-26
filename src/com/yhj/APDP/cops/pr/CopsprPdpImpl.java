package com.yhj.APDP.cops.pr;

import java.net.Socket;
import com.yhj.APDP.cops.*;
import com.yhj.APDP.cops.messages.*;
import com.yhj.APDP.cops.utils.*;
import com.yhj.APDP.cops.utils.pr.*;

public class CopsprPdpImpl extends CopsPdpImpl {


  public Socket[] pepSockets;


  public CopsprPdpImpl(CopsMsgHandler handler,Socket[] pepSockets) {
    
    super(handler,pepSockets);
    
  }

  public CopsMsgParser getMsgParser(Socket s) {
    return new CopsprMsgParser(s);
  }

}