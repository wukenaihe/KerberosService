package com.cgs.server;

import com.cgs.kerberos.server.TicketGrantServer;
import com.cgs.kerberos.server.TicketGrantTicketServer;

public class TicketGrantServerTest {
	public static void main(String[] args) throws InterruptedException {
		TicketGrantTicketServer a = new TicketGrantTicketServer(8906);
		new Thread(a).start();
		
		TicketGrantServer s=new TicketGrantServer(8907);
		new Thread(s).start();
		
		Thread.sleep(10000);
	}
}
