package com.yhj.PDP.cops.utils;

import java.rmi.*;

public class Shutdown {

  public static void main(String[] args) {
    String hostname = "127.0.0.1";
    String server = "PDP";
    if (args.length > 0) {
      server = args[0];
    }
    try {
      String location = "//" + hostname + "/" + server;
      Shutdownable s = (Shutdownable) Naming.lookup(location);
      s.shutdown();
    } catch (Exception e) {e.printStackTrace();}
  }

}
