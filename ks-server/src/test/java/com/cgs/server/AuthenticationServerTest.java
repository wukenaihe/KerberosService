package com.cgs.server;

public class AuthenticationServerTest {
	public static void main(String[] args) throws Exception {
		AuthenticationServer a = new AuthenticationServer(8906);
		new Thread(a).start();
		
		Thread.sleep(10000);
	}
}
