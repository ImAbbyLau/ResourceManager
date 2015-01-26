package com.yhj.BBClient;

import java.io.*;
import java.net.*;

public class BBClient {

	public String browserClient(String sla, String password, String params)
			throws IOException {

		Socket bbSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		String addy = "127.0.0.1";
		int port = 7777;
		final int WELCOME = 0;
		final int MAIN = 1;
		final int LOOP = 2;
		final int DISCONNECT = 100;

		int state = WELCOME;

		try {
			bbSocket = new Socket(addy, port);
			out = new PrintWriter(bbSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					bbSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + addy);
			System.exit(1);
		} catch (IOException e) {
			System.err
					.println("Couldn't get I/O for the connection to the Server.");
			System.exit(1);
		}

		String fromServer;
		String fromUser = "";

		out.println(sla + ":" + password);
		fromServer = in.readLine();
		if (fromServer.equalsIgnoreCase("Login failed.Restart client.")) {

			in.close();
			out.close();
			bbSocket.close();
			return "Login failed. Please try again. Incorrect SLA OR Password";

		}

		else {
			out.println(params);
			String display = in.readLine();
			out.println("exit");
			out.close();
			in.close();
			bbSocket.close();
			return display;
		}

	}

	public void shutdownBB() {

		String addy = "";

		try {

			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.print("Enter Address of Server : ");
			addy = stdIn.readLine();
			System.out.println();
			System.out.print("Enter Port for BB : ");
			int port = 0;
			port = Integer.parseInt(stdIn.readLine());
			System.out.println();

			Socket bbSocket = new Socket(addy, port);
			PrintWriter out = new PrintWriter(bbSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					bbSocket.getInputStream()));
			out.println("0:BBPeer");
			System.out.println(in.readLine());
			out.println("shutdown");

		} catch (UnknownHostException e) {

			System.err.println("Don't know about host: " + addy);
			System.exit(1);
		} catch (IOException e) {
			System.err
					.println("Couldn't get I/O for the connection to the Server.");
			System.exit(1);
		}

	}

	public static void main(String args[]) throws IOException {

		Socket bbSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		String addy = null;
		String sla = "";
		int port = 0;

		final int WELCOME = 0;
		final int MAIN = 1;
		final int LOOP = 2;
		final int DISCONNECT = 100;

		int state = WELCOME;

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in));

		System.out.println();
		System.out.print("Enter address of BB server : ");
		addy = stdIn.readLine();
		System.out.println();
		System.out.print("Enter port of BB server : ");
		try {
			port = Integer.parseInt(stdIn.readLine());
		} catch (NumberFormatException ne) {
			System.out.println();
			System.out
					.println("ERROR. Input for Port must be an integer. Aborting!");
			System.exit(0);
		}

		try {
			bbSocket = new Socket(addy, port);    //与BBServer建立连接
			out = new PrintWriter(bbSocket.getOutputStream(), true);    //传给BBServer的数据流
			in = new BufferedReader(new InputStreamReader(
					bbSocket.getInputStream()));    //接收BBserver传来的的数据流
		} catch (UnknownHostException e) {

			System.err.println("Don't know about host: " + addy);
			System.exit(1);
		} catch (IOException e) {
			System.err
					.println("Couldn't get I/O for the connection to the Server.");
			System.exit(1);
		}

		String fromServer;
		String fromUser = "";

		if (state == WELCOME) {

			System.out.println("-------------------------------------");
			System.out.println("      Welcome to the BB      ");
			System.out.println("-------------------------------------");
			System.out.println(InetAddress.getLocalHost());

			System.out.print("Enter SLA : ");
			sla = stdIn.readLine();
			System.out.print("Enter password : ");
			String pass = stdIn.readLine();

			String loginString = sla + ":" + pass;

			out.println(loginString);    //将数据传给BBserver

			state = MAIN;
		}

		fromServer = in.readLine();
		System.out.println();
		System.out.println("Server => " + fromServer);

		System.out.println();

		if (fromServer.equalsIgnoreCase("Login failed.Restart client.")) {

			state = DISCONNECT;

		}

		while (state == MAIN) {
			System.out.println("---------------------------------------");
			System.out.println("          * Available Options *          ");
			System.out.println("---------------------------------------");
			System.out.println();
			System.out.println("   1. SLA Info     ");
			System.out.println("   2. Request BW");
			System.out.println("   3. RAR Info ");
			System.out.println("   4. Delete RAR");
			System.out.println("   5. Modify RAR");
			// System.out.println("   6. Modify SLA");
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

				if (fromUser.equalsIgnoreCase("1")) {

					out.println("SLA info:" + sla);

				}

				else if (fromUser.equalsIgnoreCase("2")) {

//					cRAR creq = new cRAR();
//					out.println(creq.requestBW(sla));
					out.println("request BW Blah");    //替代带宽请求语句
				}

				else if (fromUser.equalsIgnoreCase("3")) {

					cRAR cRI = new cRAR();
					out.println("RAR INFO;" + sla + ";" + cRI.rarInfo());
				}

				else if (fromUser.equalsIgnoreCase("4")) {

					cRAR cdelete = new cRAR();
					out.println("delete RAR;" + sla + ";" + cdelete.delRAR());

				}

				else if (fromUser.equalsIgnoreCase("5")) {

					cRAR mRAR = new cRAR();
					out.println(mRAR.modRAR(sla));

				}

				/*
				 * else if(fromUser.equalsIgnoreCase("6")) { cRAR cSLA = new
				 * cRAR(); out.println(cSLA.modSLA(sla)); }
				 */

				else if (fromUser.equalsIgnoreCase("0")) {

					out.println("exit");
					state = DISCONNECT;
					break;
				}

				else {

					System.out
							.println("---------------********************--------------------");
					System.out.println("                       Invalid Input");
					System.out
							.println("---------------********************--------------------");
					System.out.println();
					continue;

				}

				System.out.println();
				System.out
						.println("-------------*******************------------");
				System.out.println();
				System.out.println(in.readLine());
				System.out.println();
				System.out
						.println("-------------*******************------------");
				System.out.println();
			}

		}

		if (state == DISCONNECT) {
			System.out.println("Shutting down......");
			out.close();
			in.close();
			bbSocket.close();
		}

	}

}
