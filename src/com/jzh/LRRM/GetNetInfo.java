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
	            if(file.isFile() && file.exists()) { //判断文件是否存在
	            	InputStreamReader read = new InputStreamReader(
        			new FileInputStream(file),encoding);//考虑到编码格式
	            	BufferedReader bufferedReader = new BufferedReader(read);
	            	
	            	for(int n=1;n<line;n++) {
	            		bufferedReader.readLine();
	            	}
	            	return bufferedReader.readLine();
	            } else {
	               System.out.println("找不到指定的文件");
	            }
	        } catch (Exception e) {
	        	System.out.println("读取文件内容出错");
	        	e.printStackTrace();
	        }
		return null;  
    }
}