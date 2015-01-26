package com.jzh.LRRM;
import java.io.*;


public class LRRMMain {
	
	
	public static void main(String[] args)throws IOException {
		
		LRRMServer ter = new LRRMServer();
		ter.start();
		SentNetInfoThread sni = new SentNetInfoThread(2,"./log_jzh/Local.log");
		sni.start();
		
	}
}
	
