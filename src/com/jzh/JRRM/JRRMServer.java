package com.jzh.JRRM;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class JRRMServer {
	private static int n = 0;
	public static void main(String[] args) {
		System.out.println("JRRM Waiting...");
		try {			
			ServerSocket serverSocket = new ServerSocket(6666);
			Socket clientSocket = null;
			//¼àÌý¿Í»§¶Ë//
			while(true) {
				clientSocket = serverSocket.accept();
				n++;
				new JRRMServerThread(clientSocket, n);
			}
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}
}