package com.cgs.kerberos.builder;

import java.net.Socket;

import com.cgs.kerberos.server.TGSHandler;

public interface TGSHandlerBuilder {
	TGSHandler getTGSHandler(Socket socket);
}
