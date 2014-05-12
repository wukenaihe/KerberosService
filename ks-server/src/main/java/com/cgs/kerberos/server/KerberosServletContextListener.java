package com.cgs.kerberos.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.builder.BaseTGTHandlerBuilder;

public class KerberosServletContextListener implements ServletContextListener {

	private static Logger logger = LoggerFactory.getLogger(KerberosServletContextListener.class);

	private TicketGrantTicketServer ticketGrantTicketServer;
	private TicketGrantServer ticketGrantServer;

	public void contextDestroyed(ServletContextEvent sce) {
		logger.debug("KerberosServletContextListener.contextDestroyed called");
		ticketGrantServer.close();
		ticketGrantTicketServer.close();
	}

	public void contextInitialized(ServletContextEvent sce) {
		logger.debug("KerberosServletContextListener.contextInitialized called");
		BaseTGTHandlerBuilder baseTGTHandlerBuilder = new BaseTGTHandlerBuilder();

		ServletContext servletContext = sce.getServletContext();
		String tgtServerPortStr = servletContext.getInitParameter(KdcConstants.TGT_SERVER_PORT_PARAMTER);
		int tgtServerPort;
		if (tgtServerPortStr == null) {
			ticketGrantTicketServer = new TicketGrantTicketServer();
			ticketGrantTicketServer.setTgtHandlerBuilder(baseTGTHandlerBuilder);
			new Thread(ticketGrantTicketServer).start();
		} else {
			try {
				tgtServerPort = Integer.valueOf(tgtServerPortStr);
				ticketGrantTicketServer.setTgtHandlerBuilder(baseTGTHandlerBuilder);
				new Thread(ticketGrantTicketServer).start();
			} catch (NumberFormatException e) {
				logger.error("TGT server port can not convert to int,please check out it");
			}
		}

		String tgsServerPortStr=servletContext.getInitParameter(KdcConstants.TGS_SERVER_PORT_PARAMTER);
		int tgsServerPort;
		if(tgsServerPortStr==null){
			ticketGrantServer = new TicketGrantServer();
			new Thread(ticketGrantServer).start();
		}else{
			try {
				tgsServerPort = Integer.valueOf(tgsServerPortStr);
				ticketGrantServer = new TicketGrantServer(tgsServerPort);
				new Thread(ticketGrantServer).start();
			} catch (NumberFormatException e) {
				logger.error("TGS server port can not convert to int,please check out it");
			}
		}
	}

}
