package com.cgs.kerberos.server;

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

/**
 * TGS 请求处理器
 * 
 * @author xumh
 *
 */
public class TGTHandler implements Runnable{
	private static Logger logger = LoggerFactory.getLogger(TGTHandler.class);
	
	final Socket socket;
	
	private OutputStream oos;
	private InputStream ois;
	private Kryo kryo;
	
	boolean closed = false;

	static final int RESET_FREQUENCY = 1000;
	
	
	public TGTHandler(Socket socket){
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
				obj.setIp(socket.getInetAddress().toString());//客户端传送的密码是不可靠的，需要根据socket获取
				
				ois.close();
				input.close();
				socket.close();
				break;
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}
