package com.yhj.PEP.cops.messages;

import java.net.*;
import com.yhj.PEP.cops.*;
import com.yhj.PEP.cops.objects.*;

/**
 * A Cops message handler. Contains the logic of handling received messages
 */

public interface CopsMsgHandler {

  public void handleREQ(CopsREQ req, Socket s);
  
  public void handleREQ(CopsREQ req, Socket s, Socket[] pepSockets);

  public void handleDEC(CopsDEC dec, Socket s);

  public void handleRPT(CopsRPT rpt, Socket s);

  public void handleDRQ(CopsDRQ drq, Socket s);

  public void handleSSQ(CopsSSQ ssq, Socket s);

  public void handleOPN(CopsOPN opn, Socket s);

  public void handleCAT(CopsCAT cat, Socket s);

  public void handleCC(CopsCC cc, Socket s);

  public void handleKA(CopsKA ka, Socket s);

  public void handleSSC(CopsSSC ssc, Socket s);

  public void handleUnknown(CopsMessage msg, Socket s);

  public void handleException(CopsException e, Socket s);

}
