package com.cgs.test;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cgs.kerberos.builder.BaseTGTHandlerBuilder;
import com.cgs.kerberos.client.KerberosFacade;
import com.cgs.kerberos.exception.KerberosException;
import com.cgs.kerberos.server.TicketGrantServer;
import com.cgs.kerberos.server.TicketGrantTicketServer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config.xml")
public class KerberosFacadeTest {

	@Autowired
	public KerberosFacade kerberosFacade;

	static TicketGrantTicketServer a;
	static TicketGrantServer s;

	@BeforeClass
	public static void init() {
		BaseTGTHandlerBuilder baseTGTHandlerBuilder = new BaseTGTHandlerBuilder();

		a = new TicketGrantTicketServer(8906);
		a.setTgtHandlerBuilder(baseTGTHandlerBuilder);
		new Thread(a).start();

		// BaseTGSHandlerBuilder baseTGSHandlerBuilder=new
		// BaseTGSHandlerBuilder();
		s = new TicketGrantServer(8907);

		new Thread(s).start();
	}

	@AfterClass
	public static void close() {
		a.close();
		s.close();
	}

	@Test
	@Ignore
	public void getServerRequest() {
		byte[] request = kerberosFacade.getThirdRequestByte("server");
		byte[] response = kerberosFacade.checkServiceTicket(request);
		boolean ok = kerberosFacade.checkServiceResponse(response, "server");
		Assert.assertTrue(ok);
	}

	@Test
	@Ignore
	public void getServerTwice() {
		byte[] request = kerberosFacade.getThirdRequestByte("server");
		byte[] response = kerberosFacade.checkServiceTicket(request);
		boolean ok = kerberosFacade.checkServiceResponse(response, "server");
		Assert.assertTrue(ok);

		request = kerberosFacade.getThirdRequestByte("server");
		response = kerberosFacade.checkServiceTicket(request);
		ok = kerberosFacade.checkServiceResponse(response, "server");

		Assert.assertTrue(ok);
	}
	
	@Test(expected=KerberosException.class)
//	@Ignore
	public void getServerNoServer() {
		byte[] request = kerberosFacade.getThirdRequestByte("server1");
		byte[] response = kerberosFacade.checkServiceTicket(request);
		boolean ok = kerberosFacade.checkServiceResponse(response, "server1");
		Assert.assertTrue(ok);
	}

}
