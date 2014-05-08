package com.cgs.server;

import com.cgs.kerberos.server.TicketGrantTicketServer;

public class TicketGrantTicketServerTest {
	public static void main(String[] args) throws Exception {
		TicketGrantTicketServer a = new TicketGrantTicketServer(8906);
		new Thread(a).start();
		
		Thread.sleep(10000);
	}
}
