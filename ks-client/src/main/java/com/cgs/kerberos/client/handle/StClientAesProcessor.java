package com.cgs.kerberos.client.handle;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.bean.SecondRequest;
import com.cgs.kerberos.bean.SecondRequest.RequestInformation;
import com.cgs.kerberos.bean.SecondRequest.Verification;
import com.cgs.kerberos.bean.SecondResponse;
import com.cgs.kerberos.bean.SecondResponse.SecondResponseBody;
import com.cgs.kerberos.bean.TgtResponse;
import com.cgs.kerberos.client.bean.FirstResponseWrapper;
import com.cgs.kerberos.client.bean.SecondResponseWrapper;
import com.cgs.kerberos.exception.KerberosException;
import com.cgs.kerberos.util.SecurityUtil;
import com.cgs.kerberos.util.Serializer;

public class StClientAesProcessor implements StClientProcessor{
	private static Logger logger=LoggerFactory.getLogger(StClientAesProcessor.class);
	
	private ClientDatabaseProcessor databaseProcessor;
	private Serializer serializer;
	
	public StClientAesProcessor(){
		this.databaseProcessor=new FileClientDatabaseProcessor();
	}
	
	public StClientAesProcessor(String path){
		this.databaseProcessor=new FileClientDatabaseProcessor(path);
	}
	
	public void setDatabaseProcessor(ClientDatabaseProcessor databaseProcessor) {
		this.databaseProcessor = databaseProcessor;
	}

	public void setSerializer(Serializer serializer) {
		this.serializer = serializer;
	}

	public SecondRequest getSecondRequest(FirstResponseWrapper firstResponseWrapper,String clientName, String serverName, String ip,long lifeTime) throws KerberosException {
		logger.debug("create secondRequest bean, serialize method is "+serializer.getSerializeMethod()+"; request serverName is "+serverName+"; request server ip is "+ip);
		SecondRequest secondRequest=new SecondRequest();
		secondRequest.setTgt(firstResponseWrapper.getTgt());
		
		
		
		TgtResponse tgtResponse=firstResponseWrapper.getTgtResponse();
		Verification v=new Verification();
		v.setName(clientName);
		v.setTimeStamp(new Date());
		
		byte[] sessionKey=tgtResponse.getTgsSessionKey();
		//序列化
		byte[] verificationByte=serializer.object2Byte(v);
		//用TGT Session Key加密
		byte[] encryptedVerification=SecurityUtil.encryptAes(verificationByte, sessionKey);
		secondRequest.setVerification(encryptedVerification);
		
		//明文传输部分
		RequestInformation requestInformation=new RequestInformation();
		requestInformation.setIp(ip);
		requestInformation.setLifeTime(lifeTime);
		requestInformation.setRequestServerName(serverName);
		secondRequest.setRequestInformation(requestInformation);
		
		//tgt
		secondRequest.setTgt(firstResponseWrapper.getTgt());
		
		return secondRequest;
	}

	public SecondRequest getSecondRequest(FirstResponseWrapper firstResponseWrapper, String serverName, String ip, long lifeTime) throws KerberosException {
		String clientName=databaseProcessor.getName();
		return getSecondRequest(firstResponseWrapper, clientName, serverName, ip, lifeTime);
	}

	public SecondResponseWrapper getStResponse(SecondResponse secondResponse, byte[] tgsSessionKey) throws KerberosException {
		byte[] encryptedSecondResponseBody=secondResponse.getResponseBody();
		byte[] decryptedSecondResponseBody=SecurityUtil.decryptAes(encryptedSecondResponseBody, tgsSessionKey);
		SecondResponseBody secondResponseBody=(SecondResponseBody) serializer.byte2Object(decryptedSecondResponseBody);
		
		SecondResponseWrapper secondResponseWrapper=new SecondResponseWrapper();
		secondResponseWrapper.setSecondResponseBody(secondResponseBody);
		secondResponseWrapper.setSt(secondResponse.getSt());
		
		return secondResponseWrapper;
	}

}
