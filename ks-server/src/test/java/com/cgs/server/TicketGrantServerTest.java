package com.cgs.server;

import com.cgs.kerberos.builder.BaseTGSHandlerBuilder;
import com.cgs.kerberos.builder.BaseTGTHandlerBuilder;
import com.cgs.kerberos.server.TicketGrantServer;
import com.cgs.kerberos.server.TicketGrantTicketServer;

public class TicketGrantServerTest {
	public static void main(String[] args) throws InterruptedException {
		BaseTGTHandlerBuilder baseTGTHandlerBuilder=new BaseTGTHandlerBuilder();
		
		TicketGrantTicketServer a = new TicketGrantTicketServer(8906);
		a.setTgtHandlerBuilder(baseTGTHandlerBuilder);
		new Thread(a).start();
		
//		BaseTGSHandlerBuilder baseTGSHandlerBuilder=new BaseTGSHandlerBuilder();
		TicketGrantServer s=new TicketGrantServer(8907);
		
		new Thread(s).start();
		
//		Thread.sleep(10000);
	}
}
