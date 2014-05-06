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
import com.cgs.kerberos.bean.FirstResponse;
import com.cgs.kerberos.exception.KerberosException;
import com.cgs.kerberos.handle.BaseTgtProcessor;
import com.cgs.kerberos.handle.TgtProcessor;
import com.cgs.kerberos.util.KryoSerializer;
import com.cgs.kerberos.util.Serializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

/**
 * TGS 请求处理器
 * 
 * @author xumh
 * 
 */
public class TGTHandler implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(TGTHandler.class);

	final Socket socket;

	private OutputStream oos;
	private InputStream ois;
	private Serializer serializer;// 可更换序列化方式
	private TgtProcessor tgtProcessor;

	boolean closed = false;

	static final int RESET_FREQUENCY = 1000;

	public TGTHandler(Socket socket) {
		this.socket = socket;

		serializer = new KryoSerializer();
		tgtProcessor=new BaseTgtProcessor();

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
		byte[] bytes = new byte[1024 * 10];

		try {
			ois.read(bytes);
			FirstRequest obj = serializer.byte2Object(bytes);
			FirstResponse responseBody = tgtProcessor.check(obj);

			writeResponse(responseBody);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (KerberosException e) {
			logger.debug(e.getMessage(), e);
			writeResponse(e);
		}finally{
			try {
				ois.close();
				socket.close();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}

	private void writeResponse(Object outgoingObject) {
		try {
			byte[] bytes = serializer.object2Byte(outgoingObject);
			oos.write(bytes);
		} catch (IOException e) {
			logger.error("Failed to send acknowledgement", e);
		}
	}

}
