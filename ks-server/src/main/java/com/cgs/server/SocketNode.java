package com.cgs.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.bean.FirstRequest;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

public class SocketNode implements Runnable{
	private static Logger logger = LoggerFactory.getLogger(SocketNode.class);
	
	final Socket socket;
	
	private OutputStream oos;
	private InputStream ois;
	private Kryo kryo;
	
	boolean closed = false;

	static final int RESET_FREQUENCY = 1000;
	
	
	public SocketNode(Socket socket){
		this.socket=socket;
		
		kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
		
		try {
			ois = socket.getInputStream();
			oos = socket.getOutputStream();
		} catch (Exception e) {
			logger.error("Could not open ObjectInputStream to " + socket, e);
		}
		
	}
	
	void close() {
		if (closed) {
			return;
		}
		closed = true;
		if (ois != null) {
			try {
				ois.close();
				ois = null;
			} catch (IOException e) {
				logger.warn("While in close method caught: " + e.getMessage());
			}
		}
	}

	public void run() {
		byte[] bytes=new byte[1024*10];
		
		while (!closed) {
			// read an event from the wire
			try {
				ois.read(bytes);
				Input input  = new Input(new ByteArrayInputStream(bytes), 1024);
				FirstRequest obj=(FirstRequest) kryo.readClassAndObject(input);
				System.out.println(obj);
				ois.close();
				input.close();
				socket.close();
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
