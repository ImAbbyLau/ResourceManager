package com.yhj.PEP;

import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.yhj.PEP.cops.*;
import com.yhj.PEP.cops.pr.*;
import com.yhj.PEP.cops.messages.*;
import com.yhj.PEP.cops.messages.pr.*;
import com.yhj.PEP.cops.utils.*;
import com.yhj.PEP.cops.objects.*;
import com.yhj.PEP.cops.objects.pr.*;

public class updateDB {

	private String mysqlURL = "";

	public updateDB(String mysqlURL){
		
		this.mysqlURL = mysqlURL;
	
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println("BBSQL: JDBC exception");
			System.exit(1);
		}
	}
	
	public void dropPEP(String currentDomain,Socket[] pepSockets,CopsprPdpImpl pdp) {
		
		try {
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://"+mysqlURL+"/PR","root","111111");
			Statement st1 = conn.createStatement();
			
			ResultSet res = st1.executeQuery("select sindex from LPEP where Domain='"+currentDomain+"' AND status='ON'");
			while(res.next()) {
				int sindex = res.getInt("sindex");
				pdp.close(pepSockets[sindex],(short)1,(short)9,(short)11);
			}
		}catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}catch (CopsPdpException ce) {
			System.out.println("COPS ERROR : " + ce);
		}	
		
		
	}
	
	public void statusPEP(String pepID,String status) {
		
		try {
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://"+mysqlURL+"/test_BB","root","111111");
			Statement st1 = conn.createStatement();
			
			st1.executeUpdate("update PEP set status='"+status+"' where pepID='"+pepID+"'");
			//System.out.println("PEP " + pepID + " is now " + status);
			
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex);
		}
		
	}
	
   public String getpdpAddr(String pepID) {
		
		String pdpAddr="";
		
		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/PR","root","111111");
			Statement stmt1 = conn.createStatement();

			//stmt1.executeUpdate("insert into PEP values('" + pepID+ "','" + pepAddy + "'," + pepPort + ")");
			ResultSet res = stmt1.executeQuery("select Address from LAPEP where LAPEP='" + pepID + "'");
			
			while (res.next()) {
				pdpAddr = res.getString("Address");
			}
		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
           }	
		return pdpAddr;
   }
	
	public short getPDPport(String pepID) {
		
		short pdpPort=-1;
		
		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/PR","root","111111");
			Statement stmt1 = conn.createStatement();

			//stmt1.executeUpdate("insert into PEP values('" + pepID+ "','" + pepAddy + "'," + pepPort + ")");
			ResultSet res = stmt1.executeQuery("select Address from LAPEP where LAPEP='" + pepID + "'");
			String Address="";
			while (res.next()) {
				Address = res.getString("Address");
			}
			res = stmt1.executeQuery("select pdpPort from LPDP where Address='" + Address + "'");
			while (res.next()) {
				pdpPort = (short) res.getInt("pdpPort");
			}
        	} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());

        	}	
		
		return pdpPort;
		
		
	}

	public void insertPEP(String pepAddy,int pepPort,String pepID) {
	

		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://"+mysqlURL+"/test_BB","root","111111");
			Statement stmt1 = conn.createStatement();

			stmt1.executeUpdate("insert into PEP values('" + pepID+ "','" + pepAddy + "'," + pepPort + ")");


        	} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());

        	}


	}

	public int CheckNumberFormat(String in) {
		int ret = -1;
		
		try {
			ret = Integer.parseInt(in);
		}
		catch (NumberFormatException e) {
			return ret;
		}
			return ret;
    	}

	
	String defaultRoute="127.0.0.1";


	public String modSLA(String params) {

		int i=0;
		String [] slaArray = new String[9];
		StringTokenizer s = new StringTokenizer(params,";");

		while (s.hasMoreTokens()) {

			slaArray[i]=s.nextToken();
			i++;


		}

		int slaID = CheckNumberFormat(slaArray[1]);
		
		if (slaID < 0 ) return "SLA INPUT ERROR";


		String servtype = slaArray[2];
		String totalbw = slaArray[3];
		String availbw = slaArray[4];
		String sd = slaArray[5];
    		String st = slaArray[6];
		String ed = slaArray[7];
		String et = slaArray[8];



    		try {

            		Connection conn;
            		conn = DriverManager.getConnection("jdbc:mysql://"+mysqlURL+"/test_BB","root","111111");
            		Statement stmt1 = conn.createStatement();

            		stmt1.executeUpdate("update SLA set service_type='"+servtype+"',rate="+totalbw+",availBW="+availbw+",startdate='"+sd+"',starttime='"+st+"',enddate='"+ed+"',endtime='"+et+ "' where sla_id=" + slaID);

            		System.out.println("Updated SLA ID = " + slaID);

        	} catch(SQLException ex) {
            			System.err.println("SQLException: " + ex.getMessage());
            			return "Action not performed. Database Error : " + ex;
        	}


		return "SLA changed accordingly.";


	}


	


	public void updateRAR(int rarID, String startd, String startt, String endd, String endt, int bw ) {

		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://"+mysqlURL+"/test_BB","root","111111");
			Statement stmt1 = conn.createStatement();

			stmt1.executeUpdate("update RAR set startdate='" + startd + "',starttime='"+startt+"',enddate='"+endd+"',endtime='"+endt+"',givenBW="+bw+" where rar_id=" + rarID);

			System.out.println("Updated RAR ID = " + rarID);

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	}






	public void updateSLA(int sla, int bwOld, int bwNew) {

		try {

			Connection conn;
			conn =DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB","root","111111");
			Statement stmt1 = conn.createStatement();

			stmt1.executeUpdate("update SLA set availBW=" + bwNew +" where sla_id=" + sla);

			System.out.println("Updated SLA ID = " + sla + ", changed availableBW from " + bwOld + " to " + bwNew);

		} catch(SQLException ex) {
				System.err.println("SQLException: " + ex.getMessage());
				return;
		}
	}


	public int insertRAR(int sla_id, String sDate,String stime,String eDate,String etime, int givenBW,String source,String destination) {

		int newRAR=0;

		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://"+mysqlURL+"/test_BB","root","111111");
			Statement stmt2 = conn.createStatement();

			stmt2.executeUpdate("insert into RAR(sla_id,startdate,starttime,enddate,endtime,givenBW,source,destination) VALUES (" + sla_id + ",'" + sDate + "','" + stime + "','" + eDate + "','" + etime + "'," + givenBW + ",'" + source + "','" + destination +  "')"  );

			Statement stmt3 = conn.createStatement();
			ResultSet res3 = stmt3.executeQuery("select max(rar_id) from RAR where sla_id=" + sla_id);


			while (res3.next()) {
				newRAR = res3.getInt("max(rar_id)");
			}

			return newRAR;

		} catch(SQLException ex) {

			try {

				PrintWriter log = new PrintWriter(new FileWriter("./logs/BBLog.txt",true));
				String now = new java.util.Date().toString();
				log.println(now + " - " + ex.getMessage());
				log.close();
			} catch (IOException e) {}

			System.err.println("SQLException: " + ex.getMessage());
			return -1;
		}


	}


	public String showRAR(int sla) {

		String results = "";

		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://"+mysqlURL+"/test_BB","root","111111");
			Statement stmt1 = conn.createStatement();

			ResultSet res1 = stmt1.executeQuery("select count(*) from RAR where sla_id="+sla);

			int rars=-1;

			while(res1.next()) {
				rars = res1.getInt("count(*)");
			}

			if (rars==0) {
				return "There are no current RAR's for this SLA.";
			}



			res1 = stmt1.executeQuery("select rar_id from RAR where sla_id="+sla);



			while (res1.next()) {
				String res = res1.getString("rar_id");
				results = results + res + " ";
			}


		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			return "Action not performed. Database error : " + ex;
		}

		return "The current RAR ID's in use for this SLA are : " + results;

	}


	public String rarInfo(int rarID,int slaID) {

		String info = "";

		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://"+mysqlURL+"/test_BB","root","111111");
			Statement stmt1x = conn.createStatement();
			Statement stmt1 = conn.createStatement();


			ResultSet res1x = stmt1x.executeQuery("select count(*) from RAR where rar_id="+rarID+" AND sla_id="+slaID);

			while (res1x.next()) {
			int counter = res1x.getInt("count(*)");
			
			if (counter==0) return "REQUEST FAILED : RAR Does Not Exist. Use 'SLA Info' to find out current RAR's in use.";
			
			}
		
			ResultSet res1 = stmt1.executeQuery("select * from RAR where rar_id="+rarID+" AND sla_id="+slaID);


			while (res1.next()) {
				String sla = res1.getString("sla_id");
				String sd  = res1.getString("startdate");
				String st  = res1.getString("starttime");
				String ed  = res1.getString("enddate");
				String et  = res1.getString("endtime");
				String bw  = res1.getString("givenBW");
				String src = res1.getString("source");
				String dst = res1.getString("destination");
				info = info + "RAR: " + rarID + " belongs to SLA: " + sla + ", valid from " + st + " " + sd + " until " + et + " " + ed + " with a bandwidth allocation of " + bw +". The destination address for the request is " + dst + " and it originates from " + src;
			}


		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			return "Action not performed. Database error : " + ex;
		}

		return info;

	}


	public String slaInfo(int slaID) {

		String info = "";

		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://"+mysqlURL+"/test_BB","root","111111");
			Statement stmt1x = conn.createStatement();
			Statement stmt1 = conn.createStatement();
			ResultSet res1x = stmt1x.executeQuery("select count(*) from SLA where sla_id="+slaID);


			while (res1x.next()) {
				int counter = res1x.getInt("count(*)");
				if (counter==0) return "REQUEST FAILED : SLA Does Not Exist.";
			}

			ResultSet res1 = stmt1.executeQuery("select * from SLA where sla_id="+slaID);

			while (res1.next()) {
			
				String sla = res1.getString("sla_id");
				String sd  = res1.getString("startdate");
				String stime  = res1.getString("starttime");
				String ed  = res1.getString("enddate");
				String et  = res1.getString("endtime");
				String r   = res1.getString("rate");
				String st  = res1.getString("service_type");
				String bw  = res1.getString("availBW");
				info = info + "SLA: " + slaID + " has a " + st + " service type.  The SLA is valid from " + stime + ", " + sd + " until " + et + ", " + ed + " with a total bandwidth rate of " + r + ". The remaining bandwidth for this SLA is : "+bw;
			}
			

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			return "Action not performed. Database error : " + ex;
		}

		return info;


	}

	public void shutdownServer(int bbID) {

		try{
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://"+mysqlURL+"/test_BB","root","111111");
			Statement stmt1 = conn.createStatement();
			stmt1.executeUpdate("update BBPeer set status='OFF' where BB_ID=" + bbID);
			System.out.println("BB # : " + bbID + " successfully shutdown.");
		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

	}



}
