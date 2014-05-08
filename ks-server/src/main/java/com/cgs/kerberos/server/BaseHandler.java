package com.cgs.kerberos.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.util.KryoSerializer;
import com.cgs.kerberos.util.Serializer;

public class BaseHandler {
	private static Logger logger=LoggerFactory.getLogger(BaseHandler.class);
	
	final Socket socket;

	protected OutputStream oos;
	protected InputStream ois;
	protected Serializer serializer;// 可更换序列化方式
	protected boolean closed = false;
	
	
	
	public BaseHandler(Socket socket){
		this.socket=socket;
		serializer = new KryoSerializer();
		
		try {
			ois = socket.getInputStream();
			oos = socket.getOutputStream();
		} catch (Exception e) {
			logger.error("Could not open ObjectInputStream to " + socket, e);
		}
	}
	
	
	
	public Serializer getSerializer() {
		return serializer;
	}
	public void setSerializer(Serializer serializer) {
		this.serializer = serializer;
	}



	protected void close() {
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
	
	protected void writeResponse(Object outgoingObject) {
		try {
			byte[] bytes = serializer.object2Byte(outgoingObject);
			oos.write(bytes);
			oos.flush();
		} catch (IOException e) {
			logger.error("Failed to send acknowledgement", e);
		}
	}
}
