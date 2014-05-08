package com.cgs.kerberos.server;

import java.io.IOException;
import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseServer implements Runnable{
	private static Logger logger = LoggerFactory.getLogger(BaseServer.class);
	
	final int port;
	protected boolean closed = false;
	protected boolean serverSocketSucessfullyOpened = false;
	protected ServerSocket serverSocket;
	
	public BaseServer(int port){
		this.port=port;
	}
	
	public boolean isServerSocketSucessfullyOpened() {
		return serverSocketSucessfullyOpened;
	}
	
	public void close() {
		closed = true;
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				logger.error("Failed to close serverSocket", e);
			}
		}

	}
}
