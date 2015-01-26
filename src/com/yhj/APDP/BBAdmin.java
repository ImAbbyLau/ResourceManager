package com.yhj.APDP;

import java.io.*;
import java.net.*;

public class BBAdmin {


	public String adminLogin() throws IOException {

		String loginString="";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println();
		System.out.print("Enter password : ");
 		String pass = in.readLine();
 		loginString="0:"+pass;

		return loginString;

	}

	public static void main(String args[]) throws IOException {

		Socket bbSocket = null;
        	PrintWriter out = null;
        	BufferedReader in = null;
		String addy=null;
		String sla="";
		int port=0;



		final int WELCOME=0;
		final int MAIN=1;
		final int LOOP=2;
		final int DISCONNECT=100;

		int state = WELCOME;

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		System.out.println();
		System.out.print("Enter address of BB server : ");
		addy=stdIn.readLine();
		System.out.println();
		System.out.print("Enter port of BB server : ");
		
		try {
			port = Integer.parseInt(stdIn.readLine());
		} catch(NumberFormatException ne) {
			System.out.println();
			System.out.println("ERROR. Input for port must be an integer. Aborting!");
			System.out.println();
			System.exit(0);
		}

        	try {
           		bbSocket = new Socket(addy,port);
           	 	out = new PrintWriter(bbSocket.getOutputStream(), true);
           	 	in = new BufferedReader(new InputStreamReader(bbSocket.getInputStream()));
        		} catch (UnknownHostException e) {

          	  	System.err.println("Don't know about host: " + addy);
            		System.exit(1);
        		} catch (IOException e) {
            		System.err.println("Couldn't get I/O for the connection to the Server.");
            		System.exit(1);
        	}



        	String fromServer;
        	String fromUser = "";

		if (state==WELCOME) {


			System.out.println("-------------------------------------------");
			System.out.println("      Welcome to the BB Admin Console     ");
			System.out.println("-------------------------------------------");
	        	System.out.println(InetAddress.getLocalHost());

			BBAdmin a = new BBAdmin();
	        	out.println(a.adminLogin());

	        	state=MAIN;
	        }

	        fromServer=in.readLine();
	        System.out.println();
            	System.out.println("Server => " + fromServer);
        
        	System.out.println();	
        
        
        	if(fromServer.equalsIgnoreCase("Login failed.Restart client.")) {
        			
        		state=DISCONNECT;
        			
        				
        	}
        		
        		
        	while (state==MAIN){
	    		System.out.println("---------------------------------------");
        		System.out.println("          * Available Options *          ");
        		System.out.println("---------------------------------------");
	        	System.out.println();
	        	System.out.println("   1. SLA Info     ");
	        	System.out.println("   2. Request BW");
	        	System.out.println("   3. RAR Info ");
	       		System.out.println("   4. Delete RAR");
	       		System.out.println("   5. Modify RAR");
	       		System.out.println("   6. Modify SLA");
	       		System.out.println("   7. Shutdown Server");
	       		System.out.println();
	       		System.out.println("   0. Exit");
	       		System.out.println();
	       		System.out.println("----------------------------------------");
	       		System.out.println();
	        	System.out.println("Type the command required.");
	        	System.out.println();








			System.out.println();
			System.out.print("Enter Command :: ");
            		fromUser = stdIn.readLine();


	    		if (fromUser != null) {

	    			System.out.println();
                		System.out.println("Client => " + fromUser);

				if(fromUser.equalsIgnoreCase("1")) {
					System.out.print("Enter SLA to retrieve information : ");
					sla = stdIn.readLine();
					System.out.println();
					out.println("sla info:" + sla);

				}


				else if (fromUser.equalsIgnoreCase("2")) {
					System.out.print("Enter SLA : ");
					sla = stdIn.readLine();
					cRAR creq = new cRAR();
	    				out.println(creq.requestBW(sla));
	    			}

	    			else if(fromUser.equalsIgnoreCase("3")) {
					
					System.out.print("Enter SLA : ");
					sla = stdIn.readLine();
	    				cRAR cRI = new cRAR();
	    				out.println("RAR INFO:" + sla + ":" + cRI.rarInfo());
	    			}

	    			else if(fromUser.equalsIgnoreCase("4")) {

					System.out.print("Enter SLA : ");
					sla = stdIn.readLine();
	    				cRAR cdelete = new cRAR();
	    				out.println("delete RAR:" + sla + ":" + cdelete.delRAR());


	    			}


	    			else if(fromUser.equalsIgnoreCase("5")) {
					
					System.out.print("Enter SLA : ");
					sla = stdIn.readLine();
	    				cRAR mRAR = new cRAR();
	    				out.println(mRAR.modRAR(sla));

	    			}
	    			
	    			else if(fromUser.equalsIgnoreCase("6")) {
	    				
	    				System.out.print("Enter SLA : ");
					sla = stdIn.readLine();
	    				cRAR cSLA = new cRAR();
	    				out.println(cSLA.modSLA(sla));
	    			}
	    			
	    			else if(fromUser.equalsIgnoreCase("7")) {
	    				out.println("shutdown");
	    				state=DISCONNECT;
	    				break;
	    			}
	    			
	    			
	    			else if(fromUser.equalsIgnoreCase("0")) {
	    				
	    				out.println("exit");
	    				state=DISCONNECT;
	    				break;	
	    			}
	    			

	    			else {

					System.out.println("---------------********************--------------------");
					System.out.println("                       Invalid Input");
					System.out.println("---------------********************--------------------");
					System.out.println();
					continue;

				}


	    			System.out.println();
	    			System.out.println("-------------*******************------------");
	    			System.out.println();
	    			System.out.println(in.readLine());
	    			System.out.println();
	    			System.out.println("-------------*******************------------");
	    			System.out.println();
	    		}
	    		
	    		
	    		
	    		
	    		
	    		
	    		
	    	}	
	    		
		if(state==DISCONNECT) {
			System.out.println("Shutting down......");
			out.close();
			in.close();
			bbSocket.close();
		}

	}
	
	
}
