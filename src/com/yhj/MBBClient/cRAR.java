package com.yhj.MBBClient;

import java.io.*;
import java.util.*;

public class cRAR {


	


	public String rarInfo() throws IOException {
		
		
		String reqReturn="";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		
		System.out.print("Enter the RAR ID to receive status information : ");
		String rarid = in.readLine();
		reqReturn = rarid;
		
		
		
		return reqReturn;
		
	}


	public String delRAR() throws IOException {
		
		String delReturn = "";


		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter the RAR ID to delete : ");
		String rarid = in.readLine();
		delReturn = delReturn + rarid;
		return delReturn;

	}
	
	
	public String modRAR(String sla) throws IOException {

		mysqlDate mydate = new mysqlDate();
		mysqlTime mytime = new mysqlTime();

		String modReturn = "modify RAR;" + sla + ";";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Enter the RAR ID to modify : ");
		String userin = in.readLine();
		modReturn = modReturn + userin + ";";
		System.out.println();
		System.out.print("Enter start date for request (yyyy-mm-dd) : ");
		userin = in.readLine();
		while (!mydate.checkDate(userin)) {
			System.out.print("Invalid date format. Please re-enter (yyyy-mm-dd) : ");
			userin = in.readLine();
			System.out.println();
		}
		modReturn = modReturn + userin + ";";
		System.out.println();
		System.out.print("Enter start time for request (hh:mm:ss) : ");
		userin = in.readLine();
		while (!mytime.checkTime(userin)) {
			System.out.print("Invalid time format. Please reenter (hh:mm:ss) : ");
			userin = in.readLine();
			System.out.println();
			
		}
		modReturn = modReturn + userin + ";";
		System.out.println();
		System.out.print("Enter end date for request (yyyy-mm-dd) : ");
		userin = in.readLine();
		while (!mydate.checkDate(userin)) {
			System.out.print("Invalid date format. Please re-enter (yyyy-mm-dd) : ");
			userin = in.readLine();
			System.out.println();
		}
		modReturn = modReturn + userin + ";";
		System.out.println();
		System.out.print("Enter end time for request (hh:mm:ss) : ");
		userin = in.readLine();
		while (!mytime.checkTime(userin)) {
			System.out.print("Invalid time format. Please reenter (hh:mm:ss) : ");
			userin = in.readLine();
			System.out.println();
			
		}
		modReturn = modReturn + userin + ";";
		System.out.println();
		
		boolean illegalBW = true;

		while (illegalBW) {
			System.out.print("Enter amount of bandwidth requested : ");
			userin = in.readLine();
			if (!userin.equals("")){
				illegalBW = false;
			}
		}

		modReturn = modReturn + userin + ";";
		System.out.println();

		return modReturn;

	}


	public String requestBW(String sla) throws IOException {

		//request bw;sla;startdate;starttime;enddate;endtime;bw;src;dst

		mysqlDate mydate = new mysqlDate();
		mysqlTime mytime = new mysqlTime();
		String reqReturn="request bw;" + sla + ";";

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Enter start date for request (yyyy-mm-dd) : ");
		String sd = in.readLine();
		while (!mydate.checkDate(sd)) {
			System.out.print("Invalid date format. Please re-enter (yyyy-mm-dd) : ");
			sd = in.readLine();
			System.out.println();
		}

		reqReturn = reqReturn + sd + ";";
		System.out.println();
		System.out.print("Enter start time for request (hh:mm:ss) : ");
		String st = in.readLine();
		while (!mytime.checkTime(st)) {
			System.out.print("Invalid time format. Please reenter (hh:mm:ss) : ");
			st = in.readLine();
			System.out.println();
			
		}
		
		reqReturn = reqReturn + st + ";";
		System.out.println();
		System.out.print("Enter end date for request (yyyy-mm-dd) : ");
		String ed = in.readLine();
		while (!mydate.checkDate(ed)) {
			System.out.print("Invalid date format. Please re-enter (yyyy-mm-dd) : ");
			ed = in.readLine();
			System.out.println();
		}
		reqReturn = reqReturn + ed + ";";
		System.out.println();
		System.out.print("Enter end time for request (hh:mm:ss) : ");
		String et = in.readLine();
		while (!mytime.checkTime(et)) {
			System.out.print("Invalid time format. Pease reenter (hh:mm:ss) : ");
			et = in.readLine();
			System.out.println();
		}
		reqReturn = reqReturn + et + ";";
		System.out.println();
		boolean illegalBW = true;
		String bw="";
		while (illegalBW) {
			System.out.print("Enter amount of bandwidth requested : ");
			bw = in.readLine();
			if (!bw.equals("")){
				illegalBW = false;
			}
		}
		reqReturn = reqReturn + bw + ";";
		System.out.println();
		System.out.print("Enter the source address of the flow ( xxx.xxx.xxx.xxx ) : ");
		String src = in.readLine();
		reqReturn = reqReturn + src + ";";
		System.out.println();
		System.out.print("Enter the destination address for flow ( xxx.xxx.xxx.xxx ) : ");
		String des = in.readLine();
		reqReturn = reqReturn + des;
		System.out.println();

		
		
		return reqReturn;
		
	}

	public String modSLA(String sla) throws IOException {

		mysqlDate mydate = new mysqlDate();
		mysqlTime mytime = new mysqlTime();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String modSLA = "modify SLA;" + sla + ";";

		System.out.print("Enter new service type for SLA : ");
		String userin = in.readLine();
		modSLA = modSLA + userin + ";";
		System.out.println();
		System.out.print("Enter total bandwidth allocation : ");
		userin=in.readLine();
		modSLA = modSLA + userin + ";";
		System.out.println();
		System.out.print("Enter remaining bandwidth : ");
		userin=in.readLine();
		modSLA = modSLA + userin + ";";
		System.out.println();
		System.out.print("Enter start date for SLA (yyyy-mm-dd) : ");
		userin=in.readLine();
		while (!mydate.checkDate(userin)) {
			System.out.print("Invalid date format. Please re-enter (yyyy-mm-dd) : ");
			userin = in.readLine();
			System.out.println();
		}
		modSLA = modSLA + userin + ";";
		System.out.println();
		System.out.print("Enter start time for SLA (hh:mm:ss) : ");
		userin=in.readLine();
		while (!mytime.checkTime(userin)) {
			System.out.print("Invalid time format. Please reenter (hh:mm:ss) : ");
			userin = in.readLine();
			System.out.println();
			
		}
		modSLA = modSLA + userin + ";";
		System.out.println();
		System.out.print("Enter end date for SLA (yyyy-mm-dd) : ");
		userin=in.readLine();
		while (!mydate.checkDate(userin)) {
			System.out.print("Invalid date format. Please re-enter (yyyy-mm-dd) : ");
			userin = in.readLine();
			System.out.println();
		}
		modSLA = modSLA + userin + ";";
		System.out.println();
		System.out.print("Enter end time for SLA (hh:mm:ss) : ");
		userin=in.readLine();
		while (!mytime.checkTime(userin)) {
			System.out.print("Invalid time format. Please reenter (hh:mm:ss) : ");
			userin = in.readLine();
			System.out.println();
			
		}
		modSLA = modSLA + userin + ";";
		System.out.println();
		
		return modSLA;
		
	}

}
