package com.cgs.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.cgs.kerberos.bean.FirstRequest;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

public class KerberosSocketClient implements KerberosClient{
	
	static final int DEFAULT_PORT = 8906;
	
	private String remoteHost;

	private InetAddress address;
	private int port = DEFAULT_PORT;
	private InputStream inputStream;
	private OutputStream outputStream;
	private Kryo kryo;
	
	private Socket socket;
	
	
	protected int counter = 0;

	// reset the ObjectOutputStream every RESET_FREQUENCY calls
	private static final int RESET_FREQUENCY = 200;
	
	public KerberosSocketClient(){
		
		kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
	}

	public void getTgt(String name) {
		try {
			socket=new Socket("127.0.0.1", DEFAULT_PORT);
//			inputStream=socket.getInputStream();
			outputStream=socket.getOutputStream();
			
			Output output = new Output(outputStream, 1024);
			kryo.writeClassAndObject(output, FirstRequest.getInstance());
			output.flush();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				outputStream.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
