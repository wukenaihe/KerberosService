package com.cgs.client;

import com.cgs.kerberos.client.KerberosClient;
import com.cgs.kerberos.client.KerberosSocketClient;
import com.cgs.kerberos.client.bean.FirstResponseWrapper;
import com.cgs.kerberos.client.bean.SecondResponseWrapper;


public class KerberosSocketClientTest {
	public static void main(String[] args) {
		KerberosClient k=new KerberosSocketClient("127.0.0.1", 8906, 8907);
		FirstResponseWrapper f=k.getTgt();
		System.out.println(f);
		
		SecondResponseWrapper s=k.getSt(f, "server", "172.18.110.3");
		System.out.println(s);
		
	}
}
