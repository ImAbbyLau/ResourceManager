package com.yhj.PDP;


import java.io.*;
import java.sql.*;
import java.net.*;
import java.util.*;
import com.yhj.PDP.cops.*;
import com.yhj.PDP.cops.pr.*;
import com.yhj.PDP.cops.messages.*;
import com.yhj.PDP.cops.messages.pr.*;
import com.yhj.PDP.cops.utils.*;
import com.yhj.PDP.cops.objects.*;
import com.yhj.PDP.cops.objects.pr.*;
import com.yhj.PDP.pib.*;
import com.yhj.PDP.pib.test.*;



public class RARcops {

	String mysqlURL = "";

	public RARcops(String mysqlURL) {
		this.mysqlURL = mysqlURL;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println("BBSQL: JDBC exception");
			System.exit(1);
		}
	}
	
	private Socket findPEP(String dst,String currentDomain,Socket[] pepSockets) {
		
		Socket thePEP = null;
		int pepIndex=-1;
		String pepID="";
		
		try {

			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://" + mysqlURL + "/test_BB");
			Statement stmt1 = conn.createStatement();
						
			ResultSet res = stmt1.executeQuery("select count(*) from PEP where Domain='" + currentDomain + "'");
			int count=-1;
			while (res.next()) {
				count = res.getInt("count(*)");
			}
			System.out.println("PEP Count : " + count);
			if (count==1) {
				
				res = stmt1.executeQuery("select sindex from PEP where Domain='" + currentDomain + "'");
				while (res.next()) {
					pepIndex = res.getInt("sindex");
				}
			}
			
			else {
				
				matchNetwork mN = new matchNetwork(mysqlURL);
				String cdst = mN.findDomain(dst,currentDomain);
				
				if (cdst.equals("DST Domain")) {
					res = stmt1.executeQuery("select GatewayPEPID from BBPeer where Domain='" + currentDomain + "'");
					String pep = "";
					while (res.next()) {
						pep = res.getString("GatewayPEPID");
					}
					res = stmt1.executeQuery("select sindex from PEP where pepID='" + pep + "'");
					while (res.next()) {
						pepIndex = res.getInt("sindex");
					}
					thePEP = pepSockets[pepIndex];
				}
				else if (cdst.substring(0,7).equals("interBB")) {
					String neighbour = cdst.substring(8);
						
					res = stmt1.executeQuery("select sindex,pepID from PEP where Domain='" + currentDomain + "' AND Neighbour='" + neighbour + "'");
					while (res.next()) {
						pepIndex = res.getInt("sindex");
						pepID = res.getString("pepID");
					}
					
				}
				else {
					System.out.println("PEP ERROR");
				}
				
			}
			
        	} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());

        	}

        	thePEP = pepSockets[pepIndex];
		System.out.println("Sending DEC to PEP : " + pepID);
        	return thePEP;
		
		
	}

	public void sendcops(CopsprPdpImpl pdp,Socket[] pepSockets,String currentDomain,short commandCode,String src,String dst) {
		
		byte[] prid = IpFilterEntry.prcIndex;
		PIB pib = new PIBImpl();
		initIPFilterTable(pib,src,dst);
		PRI pri = pib.getPRI(prid);
		Socket pepSocket = findPEP(dst,currentDomain,pepSockets);
		if (pib.hasPRC(prid)) {
			
			// send all instances of this PRC
			PRC prc = pib.getPRC(prid);
			
			Binding[] decs = new Binding[prc.countPRIs()];
			int ii = 0;
			
			Enumeration pris = prc.getPRIs();
			
			while (pris.hasMoreElements()) {
				pri = (PRI) pris.nextElement();
				decs[ii] = new Binding(pri.getPRID(), pri.toBytes());
				ii++;
			}

			System.out.println("The Command Code : " + commandCode);
			
			Decisionpr[] decpr = {new Decisionpr(COContext.CONFIGURATION, (short) 0,commandCode,(short) 0, new NamedDecData(decs))};
			CopsprDEC dec = new CopsprDEC((short) 0, 1, decpr);
			
			try {
				pdp.sendMessage(pepSocket, dec);
			} catch (CopsPdpException cpe) { 
				System.out.println("UNABLE TO SEND COPS MESSAGE");
				System.out.println(cpe);
			}
		}	
	
	}

	public void initIPFilterTable(PIB pib,String src,String dst) {
		try {
			PRC prc = new PRCImpl(IpFilterEntry.class, IpFilterEntry.prcIndex);
			pib.putPRC(prc.getIndex(), prc);

			byte[] dstAddr = ObjectID.parseFrom(dst);
			byte[] srcAddr = ObjectID.parseFrom(src);
			int dscp = 8;
			byte[] mask = ObjectID.parseFrom("255.255.255.255");
			byte prid = (byte) 255;

			IpFilterEntry filterEntry = new IpFilterEntry(prid, dstAddr, mask, srcAddr, mask,dscp);
			prc.putPRI(prid, filterEntry);

			

		} catch (Exception e) {e.printStackTrace();}
	}

}
