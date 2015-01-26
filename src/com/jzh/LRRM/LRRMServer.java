package com.jzh.LRRM;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class LRRMServer extends Thread {
	
	
	public void run() {
		int n = 0;
	
		System.out.println("LRRM Waiting...");
		try {			
			ServerSocket serverSocket = new ServerSocket(7788);
			Socket clientSocket = null;
			//¼àÌý¿Í»§¶Ë//
			while(true) {
				clientSocket = serverSocket.accept();
				//System.out.println(1111111111);
				n++;
				new LRRMServerThread(clientSocket, n);
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}