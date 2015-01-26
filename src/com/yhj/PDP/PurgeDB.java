package com.yhj.PDP;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.net.*;
import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.pr.*;
import com.yhj.PDP.cops.messages.*;
import com.yhj.PDP.cops.messages.pr.*;
import com.yhj.PDP.cops.utils.*;
import com.yhj.PDP.cops.objects.*;
import com.yhj.PDP.cops.objects.pr.*;
import com.yhj.PDP.pib.*;
import com.yhj.PDP.pib.test.*;

public class PurgeDB {

	private String mysqlURL = "";
	CopsprPdpImpl pdp;
	Socket[] pepSockets;
	Socket[] peerBB;
	int[] peerID;
	int bbID;
	String currentDomain;
	String[] statusList;
	

	public PurgeDB(CopsprPdpImpl pdp,Socket[] pepSockets,String mysqlURL,Socket[] peerBB,int[] peerID,int bbID,String[] statusList,String currentDomain){
		
		this.mysqlURL = mysqlURL;
		this.pdp=pdp;
		this.bbID=bbID;
		this.peerBB=peerBB;
		this.peerID=peerID;
		this.currentDomain=currentDomain;
		this.statusList=statusList;
		this.pepSockets=pepSockets;
		
    		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();        
    		} catch (Exception e) {
        		System.out.println("BBSQL: JDBC exception");
        		System.exit(1);
    		}
    	}
	


	public void purgeRAR() {
		
		
		try {
            
            	Connection conn;
            	conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB");
            	Statement stmt1 = conn.createStatement();
            	Statement stmt2 = conn.createStatement();



           	ResultSet res_RAR = stmt2.executeQuery ("select *,current_date(),current_time() from RAR");

           	while (res_RAR.next()) {
            		String rarEnd = res_RAR.getString("enddate");
            		String rar_etime = res_RAR.getString("endtime");
            		String currentdate = res_RAR.getString("current_date()");
			String currenttime = res_RAR.getString("current_time()");
			String source = res_RAR.getString("source");
            		int rarid = res_RAR.getInt("rar_id");
            		int slaid = res_RAR.getInt("sla_id");
			
			matchNetwork mn = new matchNetwork(mysqlURL);
			
			if (mn.srcDomain(source,currentDomain).equals("SRC Domain")) {
            			
            			mysqlDate msd = new mysqlDate();

            			if (msd.compareDate(rarEnd,currentdate,rar_etime,currenttime)) {
            				System.out.println("The source : " + source + ", the current domain : " + currentDomain);
            				System.out.println("RAR " + rarid + " ends on " + rarEnd + " at " + rar_etime);
            				System.out.println("It is currently " + currentdate + " " + currenttime);

					RARdel delrars = new RARdel(mysqlURL);
					delrars.deleteRAR(pdp,pepSockets,rarid,slaid,peerBB,peerID,bbID,statusList,currentDomain);
				
					try {
						String now = new java.util.Date().toString();
						PrintWriter log = new PrintWriter(new FileWriter("./logs/BBLog.txt",true));
						log.println(now + " - RAR : " + rarid + " is expired and has been automatically deleted.");
						log.close();
					} catch(IOException ex) {
    						System.err.println("IOException: " + ex.getMessage());
   					}
            				System.out.println("RAR " + rarid + " is expired and has been deleted.");
            				System.out.println();

            			}
			}

            	 }



       		} catch(SQLException ex) {
         	   System.err.println("SQLException: " + ex.getMessage());
        	}






	}







}



