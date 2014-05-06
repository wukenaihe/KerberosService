package com.cgs.client;

import com.cgs.kerberos.client.KerberosClient;
import com.cgs.kerberos.client.KerberosKryoSocketClient;


public class KerberosSocketClientTest {
	public static void main(String[] args) {
		KerberosClient k=new KerberosKryoSocketClient(null, 0);
		k.getTgt("a");
	}
}
