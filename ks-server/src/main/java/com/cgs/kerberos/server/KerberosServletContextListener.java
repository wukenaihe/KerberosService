package com.cgs.kerberos.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.builder.BaseTGSHandlerBuilder;
import com.cgs.kerberos.builder.BaseTGTHandlerBuilder;
import com.cgs.kerberos.builder.DatabaseProcessorBuilder;
import com.cgs.kerberos.builder.FileDatabaseProcessorBuilder;

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
		String path=servletContext.getInitParameter(KdcConstants.FILE_DATABASE_PATH);
		String databaseConfig=servletContext.getInitParameter(KdcConstants.DATABASE_CONFIG);
		DatabaseProcessorBuilder dpb;
		if(path==null){
			dpb=new FileDatabaseProcessorBuilder();
		}else{
			dpb=new FileDatabaseProcessorBuilder(path);
		}
		
		
		String tgtServerPortStr = servletContext.getInitParameter(KdcConstants.TGT_SERVER_PORT_PARAMTER);
		int tgtServerPort;
		if (tgtServerPortStr == null) {
			ticketGrantTicketServer = new TicketGrantTicketServer();
		} else {
			try {
				tgtServerPort = Integer.valueOf(tgtServerPortStr);
				ticketGrantTicketServer = new TicketGrantTicketServer(tgtServerPort);
				
			} catch (NumberFormatException e) {
				logger.error("TGT server port can not convert to int,please check out it");
			}
		}
		baseTGTHandlerBuilder.setDatabaseProcessorBuilder(dpb);
		ticketGrantTicketServer.setTgtHandlerBuilder(baseTGTHandlerBuilder);
		new Thread(ticketGrantTicketServer).start();

		BaseTGSHandlerBuilder tgsHandlerBuilder=new BaseTGSHandlerBuilder();
		tgsHandlerBuilder.setDatabaseProcessorBuilder(dpb);
		String tgsServerPortStr=servletContext.getInitParameter(KdcConstants.TGS_SERVER_PORT_PARAMTER);
		int tgsServerPort;
		if(tgsServerPortStr==null){
			ticketGrantServer = new TicketGrantServer();
		}else{
			try {
				tgsServerPort = Integer.valueOf(tgsServerPortStr);
				ticketGrantServer = new TicketGrantServer(tgsServerPort);
			} catch (NumberFormatException e) {
				logger.error("TGS server port can not convert to int,please check out it");
			}
		}
		ticketGrantServer.setTgsHandlerBuilder(tgsHandlerBuilder);
		new Thread(ticketGrantServer).start();

	}

}
