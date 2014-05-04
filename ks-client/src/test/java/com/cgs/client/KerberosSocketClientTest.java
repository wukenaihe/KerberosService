package com.cgs.client;


public class KerberosSocketClientTest {
	public static void main(String[] args) {
		KerberosClient k=new KerberosSocketClient();
		k.getTgt("a");
	}
}
