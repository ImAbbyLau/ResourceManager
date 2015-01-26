package com.yhj.APDP;

import java.io.*;


/*

	iproute2 stuff :
	
	ip route show
	ip route add x.x.x.x/s via w.x.y.z (existing interface)
	ip route default via w.x.y.z

	to add a extra table

	echo number title >> /etc/iproute2/rt_tables
	ip route show table title
	ip route add table title x.x.x.x/s via w.x.y.z
	
	ip route del

	Example for CBQ

	-- 100Mbit NIC

	tc qdisc add dev eth0 root handle 1:0 cbq bandwidth 100Mbit avpkt 1000 cell 8

	-- total usage to be 6Mbit

	tc class add dev eth0 parent 1:0 classid 1:1 cbq bandwidth 100Mbit rate 6Mbit weight 0.6Mbit prio 8 allot 1514 cell 8 maxburst 20 avpkt 1000 bounded

	-- limit http to 5Mbit and SMTP to 3Mbit
	
	tc class add dev eth0 parent 1:1 classid 1:3 cbq bandwidth 100Mbit rate 5Mbit weight 0.5Mbit prio 5 allot 1514 cell 8 maxburst 20 avpkt 1000
	
	tc class add dev eth0 parent 1:1 classid 1:4 cbq bandwidth 100Mbit rate 3Mbit weight 0.3 Mbit prio 5 allot 1514 cell 8 maxburst 20 avpkt 1000
	
	-- 2 leaf classes above will be bounded by the 1:1 class to 6Mbit limit
	
	-- Change qdisc from FIFO to SFQ
	
	tc qdisc add dev eth0 parent 1:3 handle 30: sfq

	tc qdisc add dev eth0 parent 1:4 handle 40: sfq
	
	-- Filter traffic to correct qdiscs
	
	tc filter add dev eth0 parent 1:0 protocol ip prio 1 u32 match ip \ sport 80 0xffff flowid 1:3
	
	tc filter add dev eth0 parent 1:0 protocol ip prio 1 u32 match ip \ sport 25 0xffff flowid 1:4



	-- Filter on TOS field (minimum delay traffic)

	tc filter add dev eth0 parent 1:0 protocol ip prio 10 u32 \ match ip tos 0x10 0xff \ flowid 1:4

	-- use 0x08 0xff for bulk traffics


	-- Filter according to dst or src addresses
	
	tc filter add dev eth0 parent 1:0 protocol ip prio 1 u 32 \ match ip dst 4.3.2.1/32 flowid 1:3
	
	tc filter add dev eth0 parent 1:0 protocol ip prio 1 u32 \ match ip src 1.2.3.4/32 flowid 1:3
	
	tc filter add dev eth0 protocol ip parent 1: prio 2 \ flowid 1:4

	-- To Show qdisc settings

	tc -s qdisc ls dev eth0

*/



public class LinuxRoute {

	// Assuming all Linux machines are set up to access network via NIC labelled eth0
	String device="wlan0";

	/* -------------------------------------------------------------------------
	   The following method can be modified in any way to set up
	   any DiffServ or traffic control environment. The included code
	   here is a simple DiffServ environment that caters for EF and BE behaviour
	   -------------------------------------------------------------------------*/ 

	public void setupDiff() {

		
		


		System.out.println();
		System.out.println("Preparing router for DiffServ operation.....");
		System.out.println();
		
		// Code from Sanjay Jha and Mahbub Hassan - Engineering Internet QoS 2002 page 180

		String diffserv = "tc qdisc add dev eth0 handle 1:0 root dsmark indices 64 set_tc_index";
		
		String checkDS = "tc filter add dev eth0 parent 1:0 protocol ip prio 1 tcindex mask 0xfc shift 2";

		String prioQ = "tc qdisc add dev eth0 parent 1:0 handle 2:0 prio";
		
		// EF class rate of 1.0Mbit (DSCP '101110' (46))

		String EFsetup = "tc qdisc add dev eth0 parent 2:1 tbf rate 1.0Mbit burst 1.5kB limit 1.6kB";

		String EFfilter = "tc filter add dev eth0 parent 2:0 protocol ip prio 1 handle 0x2e tcindex classid 2:1 pass_on";
		
		// BE class (DSCP '000000')

		String BEsetup = "tc qdisc add dev eth0 parent 2:2 red limit 60KB min 15KB max 45KB burst 20 avpkt 1000 bandwidth 10Mbit probability 0.4";

		String BEfilter = "tc filter add dev eth0 2:0 protocol ip prio 2 handle 0 tcindex mask 0 classid 2:2 pass_on";

		try {

		Runtime.getRuntime().exec(diffserv);
		Runtime.getRuntime().exec(checkDS);
		Runtime.getRuntime().exec(prioQ);
		Runtime.getRuntime().exec(EFsetup);
		Runtime.getRuntime().exec(EFfilter);
		Runtime.getRuntime().exec(BEsetup);
		Runtime.getRuntime().exec(BEfilter);




		} catch (IOException e) {}



	}


	/* ----------------------------------------------------------------------------------
	   Likewise, this method can be edited to suit whatever behaviour is required once
	   a request has been accepted and the configuration of a router needs to be changed.
	   This method currently simple adds a route to the routing table at the specified
	   Linux router (PEP)
	   ----------------------------------------------------------------------------------*/

	public String addRoute(String Destination,String Source) {

		// For EF flow
		// tc filter add dev eth0 parent 2:0 protocol ip prio 1 u32 \ 
		//	match ip dst 4.3.2.1/32 \ match ip src 1.2.3.4/32 flowid 2:1



		String processOut="";

		String command = "ip route add " + Destination + " dev " + device;


		try {

		System.out.println("Executing : " + command);
		Process x = Runtime.getRuntime().exec(command);
		BufferedReader in = new BufferedReader(
	    	new InputStreamReader(x.getInputStream()));


		System.out.println("Destination : " + Destination + " has been added to routing table via " + device);


		while((processOut=in.readLine()) != null) {

			System.out.println(processOut);

		}

		} catch(IOException e) {}


		return "routing modified";

	}


	public String delRoute(String destination,String source) {



		String processOut="";

		String command = "ip route del " + destination + " dev " + device;
		System.out.println("Executing : " + command);

		try {

		Process x = Runtime.getRuntime().exec(command);
			System.out.println("Destination : " + destination + " has been deleted from routing table via " + device);
			return "Route deleted";

		} catch(IOException e) {

			return "Unable to delete Route";
		}

	}


}
