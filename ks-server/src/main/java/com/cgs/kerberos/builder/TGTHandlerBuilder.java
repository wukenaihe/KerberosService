package com.cgs.kerberos.builder;

import java.net.Socket;

import com.cgs.kerberos.server.TGTHandler;

public interface TGTHandlerBuilder {
	TGTHandler getTGTHandler(Socket socket);
}
