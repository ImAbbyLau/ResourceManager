package com.yhj.PEP.cops.utils;

import java.rmi.*;

public interface Shutdownable extends Remote {
  public void shutdown() throws RemoteException;
}
