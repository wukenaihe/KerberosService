package com.cgs.kerberos.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.builder.BaseTGTHandlerBuilder;
import com.cgs.kerberos.builder.TGTHandlerBuilder;
import com.cgs.kerberos.handle.BaseTgtProcessor;
import com.cgs.kerberos.handle.TgtProcessor;

public class TicketGrantTicketServer extends BaseServer{
	private static Logger logger = LoggerFactory.getLogger(TicketGrantTicketServer.class);
	
	private TGTHandlerBuilder tgtHandlerBuilder=new BaseTGTHandlerBuilder();

	public void setTgtHandlerBuilder(TGTHandlerBuilder tgtHandlerBuilder) {
		this.tgtHandlerBuilder = tgtHandlerBuilder;
	}

	public TicketGrantTicketServer(int port) {
		super(port);
	}

	public void run() {
		try {
			logger.info("Ticket grant ticket server listening on port " + port);
			serverSocket = new ServerSocket(port);
			serverSocketSucessfullyOpened = true;
			while (!closed) {
				logger.info("Ticket grant ticket server waiting to accept a new Authentication.");
				Socket socket = serverSocket.accept();
				InetAddress inetAddress = socket.getInetAddress();
				logger.info("Connected to client at " + inetAddress);

				logger.info("Starting new TGTHandler.");
				// don't allow simultaneous access to the socketNodeList
				// (e.g. removal whole iterating on the list causes
				// java.util.ConcurrentModificationException
				
				new Thread(tgtHandlerBuilder.getTGTHandler(socket)).start();
			}
		} catch (SocketException e) {
			if ("socket closed".equals(e.getMessage())) {
				logger.info("TGTHandler server has been closed");
			} else {
				logger.info("Caught an KerberosException", e);
			}
		} catch (IOException e) {
			logger.info("Caught an IOException", e);
		} catch (Exception e) {
			logger.error("Caught an unexpectged exception.", e);
		}
	}
}
