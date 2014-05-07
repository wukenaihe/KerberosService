package com.cgs.client;

import com.cgs.kerberos.client.KerberosClient;
import com.cgs.kerberos.client.KerberosKryoSocketClient;
import com.cgs.kerberos.client.bean.FirstResponseWrapper;


public class KerberosSocketClientTest {
	public static void main(String[] args) {
		KerberosClient k=new KerberosKryoSocketClient("127.0.0.1", 8906);
		FirstResponseWrapper f=k.getTgt();
		System.out.println(f);
	}
}
