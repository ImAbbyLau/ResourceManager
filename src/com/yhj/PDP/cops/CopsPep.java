/**
 * @(#) CopsPep.java 1.00 00/09/23
 *
 * Copyright (c) 2000
 *
 * This software is the translation of COPS implementation
 * from Telia Research AB to java.
 * For non-commercial purpose. This may not be redistributed.
 */

package com.yhj.PDP.cops;

import java.net.*;
import java.io.*;
import java.lang.*;

import com.yhj.PDP.cops.messages.*;

import com.yhj.PDP.cops.CopsPepException;
import com.yhj.PDP.cops.messages.CopsRPT;
import com.yhj.PDP.cops.messages.pr.CopsprREQ;

/**
 * This is the COPS PEP interface.
 * It only contains error codes and methods declarations.
 * The basic implementation is provided in the class CopsPepImpl.
 *
 * @see CopsPepImpl
 */

public interface CopsPep {

  public static final int ERR_PARAMS = -1;    // one of the parameters was invalid
  public static final int ERR_CONTACT = -2;   // contact/socket error
  public static final int ERR_AUTH = -3;      // authentication error
  public static final int ERR_INTERNAL = -4;  // internal error

  /**
   * Connect to a PDP. It does not require authentication.
   * It will throws exceptions on error.
   *
   * @return returns nothing. errors reported in exceptions thrown.
   * @param hostname the hostname where PDP resides. It can be IP address
   * @param port the port number PDP is listening to.
   * @throws CopsPepException Contains the error code. May contain nested exception.
   */
  public void connect(String hostname, short port) throws CopsPepException;

  public void open(String addr, short port, short clientType, String pepid)
      throws CopsPepException;

  public void keepAlive(String addr, short port, short clientType)
      throws CopsPepException;

  public void close(String addr, short port, short clientType,
                    short errorCode, short errorSubcode)
      throws CopsPepException;
  
  public void MpepsendREQ(String addr, short port,CopsprREQ req) throws CopsPepException;
  
  public void CpepsendREQ(String addr, short port,CopsprREQ req) throws CopsPepException;
  
  public void CpepsendRPT(String addr, short port,CopsRPT rpt) throws CopsPepException;
/*
  public void request(String addr, short port, short clientType, int chandle, short rType, short mType, Decision[] dec) throws CopsPepException;
  public void reportState(CopsRPT rpt) throws CopsPepException;
  public void deleteState(CopsDRQ drq) throws CopsPepException;
  public void synchroniseComplete(CopsSSC ssc) throws CopsPepException;
*/

  /**
   * Send a message over a given socket.
   *
   * @param sock the socket that the connection is using.
   * @param msg the message to be sent
   * @throws CopsPepException thrown if connection or the message is not valid.
   */
  public void sendMessage(Socket sock, CopsMessage msg) throws CopsPepException;
  
  public void sendMessage(CopsMessage msg) throws CopsPepException;    //Add By Myself

  public void shutdown() throws CopsPepException;
}
