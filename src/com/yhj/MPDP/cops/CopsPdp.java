package com.yhj.MPDP.cops;

import java.net.*;
import java.io.*;
import com.yhj.MPDP.cops.messages.*;
import com.yhj.MPDP.cops.objects.Decision;

public interface CopsPdp {

  public static final int ERR_PARAMS = -1;    // one of the parameters was invalid
  public static final int ERR_CONTACT = -2;   // contact/socket error
  public static final int ERR_AUTH = -3;      // authentication error
  public static final int ERR_INTERNAL = -4;  // internal error

  /*
   * Init function, this *must* be called before starting to use the library.
   * Returns false if and only if initialisation fails. A failure probably means
   * that the computer is out of resources. Some resources may be leaked on a
   * failure, therefore the process should be terminated.
   *
   * port
   *      Port to listen for incoming connections, for example COPS_PORT (3288).
   * client_types
   *      array of supported client types.
   * n_types
   *      the length of the above array
   * max_connections
   *      maximum number of PEP connections allowed. One connection is needed as a buffer (for telling PEPs that it cannot connect), so this value should not be lower than 2
   */
  //public boolean init(short port, int max_connections) throws CopsPdpException;

  public boolean init(short port, int max_connections, Socket[] pepSockets) throws CopsPdpException;

  public void shutdown() throws CopsPdpException;

  //public void synchronise(CopsSSQ ssq) throws CopsPdpException;
  public void accept(Socket s, short clientType, short kaTimer)
      throws CopsPdpException;

  public void close(Socket s, short clientType, short errorCode,
                    short errorSubcode)
      throws CopsPdpException;

  public void keepAlive(Socket s, short clientType) throws CopsPdpException;

  public void sendMessage(Socket sock, CopsMessage msg) throws CopsPdpException;

  public void decision(Socket s, short clientType, int chandle, Decision[] decs)
      throws CopsPdpException;
}

