package com.jzh.LRRM;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class GetNetInfo {
	
	File file;
	InputStreamReader read;
	BufferedReader bufferedReader;
	String strLineNet;
   
	public String readFile(String filePath,int line) {
	    try {
	            String encoding="GBK";
	            file = new File(filePath);
	            if(file.isFile() && file.exists()) { //�ж��ļ��Ƿ����
	            	InputStreamReader read = new InputStreamReader(
        			new FileInputStream(file),encoding);//���ǵ������ʽ
	            	BufferedReader bufferedReader = new BufferedReader(read);
	            	
	            	for(int n=1;n<line;n++) {
	            		bufferedReader.readLine();
	            	}
	            	return bufferedReader.readLine();
	            } else {
	               System.out.println("�Ҳ���ָ�����ļ�");
	            }
	        } catch (Exception e) {
	        	System.out.println("��ȡ�ļ����ݳ���");
	        	e.printStackTrace();
	        }
		return null;  
    }
}