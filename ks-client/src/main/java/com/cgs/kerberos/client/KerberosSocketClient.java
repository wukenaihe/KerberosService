package com.cgs.kerberos.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.bean.FirstRequest;
import com.cgs.kerberos.bean.FirstResponse;
import com.cgs.kerberos.bean.SecondRequest;
import com.cgs.kerberos.bean.SecondResponse;
import com.cgs.kerberos.client.bean.FirstResponseWrapper;
import com.cgs.kerberos.client.bean.SecondResponseWrapper;
import com.cgs.kerberos.client.handle.ClientDatabaseProcessor;
import com.cgs.kerberos.client.handle.StClientAesProcessor;
import com.cgs.kerberos.client.handle.StClientProcessor;
import com.cgs.kerberos.client.handle.TgtClientAesProcessor;
import com.cgs.kerberos.client.handle.TgtClientProcessor;
import com.cgs.kerberos.exception.KerberosException;
import com.cgs.kerberos.exception.TgsException;
import com.cgs.kerberos.util.KryoSerializer;
import com.cgs.kerberos.util.Serializer;

public class KerberosSocketClient implements KerberosClient {
	private static final Logger logger = LoggerFactory.getLogger(KerberosSocketClient.class);

	public static final int DEFAULT_TGS_PORT = 8906;
	public static final int DEFAULT_ST_PORT=8907;
	public static final int DEFAULT_LIFT_TIME = -1;// 永久

	private String remoteHost;
	private TgtClientProcessor tgtClientProcessor;
	private StClientProcessor stClientProcessor;
	private ClientDatabaseProcessor cdp;
	
	private Serializer serializer=new KryoSerializer();

	private int tgsPort ;
	private int stPort;

	public KerberosSocketClient(String remoteHost, int tgsPort,int stPort) {
		this.remoteHost = remoteHost;
		this.tgsPort = tgsPort;
		this.stPort=stPort;
		
		TgtClientAesProcessor t = new TgtClientAesProcessor();
		t.setSerializer(serializer);
		this.tgtClientProcessor=t;
		
		StClientAesProcessor s=new StClientAesProcessor();
		s.setSerializer(serializer);
		this.stClientProcessor=s;
		
	}

	public KerberosSocketClient(String remoteHost) {
		this(remoteHost,DEFAULT_TGS_PORT,DEFAULT_ST_PORT);
	}
	
	public KerberosSocketClient(){
		this(null,DEFAULT_TGS_PORT,DEFAULT_ST_PORT);
	}
	
	
	

	public void setCdp(ClientDatabaseProcessor cdp) {
		this.cdp = cdp;
		tgtClientProcessor.setDatabaseProcessor(cdp);
		stClientProcessor.setDatabaseProcessor(cdp);
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public void setTgtClientProcessor(TgtClientProcessor tgtClientProcessor) {
		this.tgtClientProcessor = tgtClientProcessor;
	}

	public void setTgsPort(int tgsPort) {
		this.tgsPort = tgsPort;
	}

	public FirstResponseWrapper getTgt(long lifeTime) throws KerberosException {
		return getTgt(null,lifeTime);
	}

	public FirstResponseWrapper getTgt() throws KerberosException {
		return getTgt(DEFAULT_LIFT_TIME);
	}

	public FirstResponseWrapper getTgt(String name) throws KerberosException {
		return getTgt(name, DEFAULT_LIFT_TIME);
	}

	public FirstResponseWrapper getTgt(String name, long lifeTime) throws KerberosException {
		InputStream inputStream;
		OutputStream outputStream = null;

		Socket socket = null;
		try {
			socket = new Socket(remoteHost, tgsPort);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();

			// 写入
			FirstRequest firstRequest;
			if(name!=null){
				firstRequest=tgtClientProcessor.getFirstRequest(name,lifeTime);
			}else{
				firstRequest=tgtClientProcessor.getFirstRequest(lifeTime);
			}
			outputStream.write(serializer.object2Byte(firstRequest));
			outputStream.flush();

//			// 获取
			byte[] bytes = new byte[1024 * 10];
			inputStream.read(bytes);
			Object response= serializer.byte2Object(bytes);
			if (response instanceof String) {
					throw new TgsException("Ticket granting Server incurred an exception. The message information is"+response); 
			}else{
				FirstResponseWrapper firstResponseWrapper = tgtClientProcessor.getTgtResponse((FirstResponse)response);
				return firstResponseWrapper;
			}
		} catch (UnknownHostException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				outputStream.close();
				socket.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	public SecondResponseWrapper getSt(FirstResponseWrapper firstResponseWrapper, String clientName, String serverName, String ip, long lifeTime)
			throws KerberosException {
		InputStream inputStream;
		OutputStream outputStream = null;

		Socket socket = null;
		try {
			socket = new Socket(remoteHost,stPort);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();

			// 写入
			SecondRequest secondRequest;
			if(clientName!=null){
				secondRequest=stClientProcessor.getSecondRequest(firstResponseWrapper,clientName, serverName, ip, lifeTime);
			}else{
				secondRequest=stClientProcessor.getSecondRequest(firstResponseWrapper, serverName, ip, lifeTime);
			}
			outputStream.write(serializer.object2Byte(secondRequest));
			outputStream.flush();

//			// 获取
			byte[] bytes = new byte[1024 * 10];
			inputStream.read(bytes);
			Object response= serializer.byte2Object(bytes);
			if (response instanceof String) {
					throw new TgsException("Ticket granting Server incurred an exception. The message information is"+response); 
			}else{
				SecondResponse secondResponse=(SecondResponse) response;
				SecondResponseWrapper secondResponseWrapper=stClientProcessor.getStResponse(secondResponse, firstResponseWrapper.getTgtResponse().getTgsSessionKey());
				return secondResponseWrapper;
			}
			

		} catch (UnknownHostException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				outputStream.close();
				socket.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	public SecondResponseWrapper getSt(FirstResponseWrapper firstResponseWrapper, String serverName, String ip, long lifeTime) throws KerberosException {
		return getSt(firstResponseWrapper,null, serverName, ip, lifeTime);
	}

	public SecondResponseWrapper getSt(FirstResponseWrapper firstResponseWrapper, String clientName, String serverName, String ip) throws KerberosException {
		return getSt(firstResponseWrapper, clientName, serverName, ip,DEFAULT_LIFT_TIME);
	}

	public SecondResponseWrapper getSt(FirstResponseWrapper firstResponseWrapper, String serverName, String ip) throws KerberosException {
		return getSt(firstResponseWrapper, serverName, ip,DEFAULT_LIFT_TIME);
	}

}
