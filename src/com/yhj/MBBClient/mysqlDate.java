package com.yhj.MBBClient;

import java.sql.*;
import java.util.*;
import java.io.*;



public class mysqlDate {


	String year1="";
	String year2="";
	String month1="";
	String month2="";
	String day1="";
	String day2="";


	// Method will return TRUE if date1 is before date2


	public boolean compareDate(String date1,String date2,String time1,String time2) {
		
		year1=date1.substring(0,4);
		year2=date2.substring(0,4);
		
		month1=date1.substring(5,7);
		month2=date2.substring(5,7);
		
		day1=date1.substring(8,10);
		day2=date2.substring(8,10);
		
		
		
		int y1=Integer.parseInt(year1);
		int y2=Integer.parseInt(year2);
		int m1=Integer.parseInt(month1);
		int m2=Integer.parseInt(month2);
		int d1=Integer.parseInt(day1);
		int d2=Integer.parseInt(day2);
		
		if (y1<=y2) {
			if (m1<m2) {
				return true;
			}
			
			if (m1==m2) {
			 	if (d1<d2) {
			 		return true;
			 	}

			 	else if (d1==d2){

			 		mysqlTime mst = new mysqlTime();
			 		if(mst.compareTime(time1,time2)) {
			 			return true;
			 		}
			 		else return false;
			 		
			 		
			 	}
			 	
			 	else	{ 
			 		return false;
			 	}
			}
			else {
				return false;
			}
			
		}
		else {
			return false;	
		}
		
		
	}




	public boolean compareDate(String date1,String date2) {
		
		
		year1=date1.substring(0,4);
		year2=date2.substring(0,4);
		
		month1=date1.substring(5,7);
		month2=date2.substring(5,7);
		
		day1=date1.substring(8,10);
		day2=date2.substring(8,10);
		
		
		
		int y1=Integer.parseInt(year1);
		int y2=Integer.parseInt(year2);
		int m1=Integer.parseInt(month1);
		int m2=Integer.parseInt(month2);
		int d1=Integer.parseInt(day1);
		int d2=Integer.parseInt(day2);
		
		if (y1<=y2) {
			if (m1<m2) {
				return true;
			}
			
			if (m1==m2) {
			 	if (d1<=d2) {
			 		return true;
			 	}
			 		 	 	
			 	else	{ 
			 		return false;
			 	}
			}
			else {
				return false;
			}
			
		}
		else {
			return false;	
		}
		
	}
	
	
	public boolean checkDate(String theDate) {
		
		// format = "yyyy-mm-dd";
		
		if ( theDate.length()!=10 ) {
			
			return false;
		}
		
		if (theDate.charAt(4) != '-'){
			
			return false;
		}
		
		if (theDate.charAt(7) != '-') {
			
			return false;
		}
		
		
		
		String date[] = new String[3];
    		StringTokenizer token = new StringTokenizer(theDate,"-");

		int i = 0;
    		while (token.hasMoreTokens()) {


			date[i]=token.nextToken();
			i++;


		}
		
		String year = date[0];
		String month = date[1];
		String day = date[2];
		
		
		int iyear = Integer.parseInt(year);
		int imonth = Integer.parseInt(month);
		int iday = Integer.parseInt(day);
		
		
		if (iyear < 2003) {
			return false;
		}
		
		if (imonth < 0 || imonth > 12) {
			return false;
		}
		
		if (iday < 0 || iday > 31) {
			return false;
		}
		
		
		
		return true;
		
		
	}


	public static void main(String args[]) {
		
		
		mysqlDate md = new mysqlDate();
		System.out.println(md.checkDate("2003-13-30"));
		
		
	}




}
