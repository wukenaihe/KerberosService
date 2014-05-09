package com.cgs.client;

import org.junit.BeforeClass;

import com.cgs.kerberos.client.KerberosClient;
import com.cgs.kerberos.client.KerberosClientServer;
import com.cgs.kerberos.client.KerberosClientServerImpl;
import com.cgs.kerberos.client.KerberosSocketClient;
import com.cgs.kerberos.client.bean.FirstResponseWrapper;
import com.cgs.kerberos.client.bean.SecondResponseWrapper;
import com.cgs.kerberos.client.handle.FileClientDatabaseProcessor;
import com.cgs.kerberos.util.KryoSerializer;

public class KerberosSocketClientTest {


	public static void main(String[] args) {
		KerberosClient k = new KerberosSocketClient("127.0.0.1", 8906, 8907);
		FirstResponseWrapper f = k.getTgt();
		System.out.println(f);
		System.out.println(f.getTgt().length);

		SecondResponseWrapper s = k.getSt(f, "server", "172.18.110.3");
		System.out.println(s);
		System.out.println(s.getSt().length);

		KerberosClientServerImpl kcs = new KerberosClientServerImpl();
		kcs.setCdp(new FileClientDatabaseProcessor());
		kcs.setSerializer(new KryoSerializer());
		byte[] bytes = kcs.getThirdRequestByte(s);

		byte[] responseByte = kcs.checkServiceTicket(bytes);
		boolean result = kcs.checkServiceResponse(responseByte, s);

		System.out.println(result);

	}
}
