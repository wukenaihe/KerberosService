package com.cgs.kerberos.server;

public abstract class BaseServer implements Runnable{
	final int port;
	boolean closed = false;
	boolean serverSocketSucessfullyOpened = false;
	
	public BaseServer(int port){
		this.port=port;
	}
	
	public boolean isServerSocketSucessfullyOpened() {
		return serverSocketSucessfullyOpened;
	}
}
