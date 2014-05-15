package com.cgs.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cgs.kerberos.builder.BaseTGTHandlerBuilder;
import com.cgs.kerberos.client.KerberosClient;
import com.cgs.kerberos.client.KerberosClientServerImpl;
import com.cgs.kerberos.client.KerberosSocketClient;
import com.cgs.kerberos.client.bean.FirstResponseWrapper;
import com.cgs.kerberos.client.bean.SecondResponseWrapper;
import com.cgs.kerberos.client.handle.FileClientDatabaseProcessor;
import com.cgs.kerberos.server.TicketGrantServer;
import com.cgs.kerberos.server.TicketGrantTicketServer;
import com.cgs.kerberos.util.KryoSerializer;

public class ServerAndClient {
	
	static TicketGrantTicketServer a;
	static TicketGrantServer s;
	
	@BeforeClass
	public static void init(){
		BaseTGTHandlerBuilder baseTGTHandlerBuilder=new BaseTGTHandlerBuilder();
		
		a = new TicketGrantTicketServer(8906);
		a.setTgtHandlerBuilder(baseTGTHandlerBuilder);
		new Thread(a).start();
		
//		BaseTGSHandlerBuilder baseTGSHandlerBuilder=new BaseTGSHandlerBuilder();
		s=new TicketGrantServer(8907);
		
		new Thread(s).start();
	}
	
	@AfterClass
	public static void close(){
		a.close();
		s.close();
	}
	
	@Test
	public void okTest(){
		KerberosClient k = new KerberosSocketClient("127.0.0.1", 8906, 8907);
		FirstResponseWrapper f = k.getTgt();
		System.out.println(f);
		System.out.println(f.getTgt().length);

		SecondResponseWrapper s = k.getSt(f, "server", "172.18.110.3");
		System.out.println(s);
		System.out.println(s.getSt().length);

		KerberosClientServerImpl kcs = new KerberosClientServerImpl();
		kcs.setCdp(new FileClientDatabaseProcessor());
		kcs.setSerializer(new KryoSerializer());
		
		byte[] bytes = kcs.getThirdRequestByte(s);

		byte[] responseByte = kcs.checkServiceTicket(bytes);
		boolean result = kcs.checkServiceResponse(responseByte, s);

		System.out.println(result);
	}
}
