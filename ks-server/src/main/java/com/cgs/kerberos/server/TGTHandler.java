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
import com.cgs.kerberos.exception.DatabaseException;
import com.cgs.kerberos.exception.KerberosException;
import com.cgs.kerberos.exception.TgsException;
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
public class TGTHandler extends BaseHandler implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(TGTHandler.class);

	private TgtProcessor tgtProcessor;

	public void setTgtProcessor(TgtProcessor tgtProcessor) {
		this.tgtProcessor = tgtProcessor;
	}

	public TGTHandler(Socket socket) {
		super(socket);
//		tgtProcessor=new BaseTgtProcessor();
	}

	public void run() {
		byte[] bytes = new byte[1024 * 10];

		try {
			ois.read(bytes);
			FirstRequest obj = (FirstRequest) serializer.byte2Object(bytes);
			String ip=socket.getInetAddress().toString();
			obj.setIp(ip);
			FirstResponse responseBody = tgtProcessor.check(obj);

			writeResponse(responseBody);
		} catch (KerberosException e) {
			logger.debug(e.getMessage(), e);
			writeResponse(e.getMessage());
		}catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally{
			try {
				ois.close();
				socket.close();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}

}
