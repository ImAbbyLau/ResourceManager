package com.yhj.MPDP;

import java.util.*;
import java.io.*;
import java.net.*;

public class mysqlTime {


	String hour1="";
	String hour2="";
	String min1="";
	String min2="";
	String sec1="";
	String sec2="";
	
	
	
	public boolean compareTime(String time1,String time2) {
		
		hour1=time1.substring(0,2);
		hour2=time2.substring(0,2);
		min1=time1.substring(3,5);
		min2=time2.substring(3,5);
		sec1=time1.substring(6,8);
		sec2=time2.substring(6,8);
		
		
		int h1 = Integer.parseInt(hour1);
		int h2 = Integer.parseInt(hour2);
		int m1 = Integer.parseInt(min1);
		int m2 = Integer.parseInt(min2);
		int s1 = Integer.parseInt(sec1);
		int s2 = Integer.parseInt(sec2);
		
		
		if (h1<h2) {
			return true;
		}
		
		if (h1==h2) {
			if (m1<m2) {
				return true;
			}
			else if (m1==m2) {
				if (s1<s2) {
					return true;
				}
				else return false;
			}
			else return false;
		}
		else return false;
		
		
		
		
	}
	
	
	boolean checkTime(String theTime) {
		
		// format = "hh:mm:ss";

		if (theTime.length() != 8) {
			return false;
		}

		if (theTime.charAt(2) != ':') {
			return false;
		}

		if (theTime.charAt(5) != ':') {
			return false;
		}

		String[] time = new String[3];
		StringTokenizer token = new StringTokenizer(theTime,":");

		int i = 0;
    		while (token.hasMoreTokens()) {


			time[i]=token.nextToken();
			i++;


		}

		int hour=-1;
		int min=-1;
		int sec=-1;

		try {

			hour = Integer.parseInt(time[0]);
			min = Integer.parseInt(time[1]);
			sec = Integer.parseInt(time[2]);
		} catch (NumberFormatException e) {
			return false;
		}

		if (hour < 0 || hour > 24) {
			return false;
		}

		if (min < 0 || min > 59) {
			return false;
		}

		if (sec < 0 || sec > 59) {
			return false;
		}

		return true;


	}


	public static void main(String args[]) {

		try {
			Socket sss = new Socket("localhost",4444);
			System.out.println(sss.getPort());
		} catch (IOException e) {
			System.out.println(e);
		}

		mysqlTime mt = new mysqlTime();
		System.out.println(mt.compareTime("23:59:01","16:28:59"));

	}




}
