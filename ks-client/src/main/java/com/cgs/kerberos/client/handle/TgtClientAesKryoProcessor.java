package com.cgs.kerberos.client.handle;

import java.util.Date;

import com.cgs.kerberos.bean.FirstRequest;
import com.cgs.kerberos.bean.FirstResponse;
import com.cgs.kerberos.bean.TgtResponse;
import com.cgs.kerberos.client.bean.FirstResponseWrapper;
import com.cgs.kerberos.exception.AesSecurityException;
import com.cgs.kerberos.exception.PasswordError;
import com.cgs.kerberos.util.KryoSerializer;
import com.cgs.kerberos.util.SecurityUtil;
import com.cgs.kerberos.util.Serializer;
import com.cgs.util.KryoUtilTest;

public class TgtClientAesKryoProcessor implements TgtClientProcessor{
	
	private ClientDatabaseProcessor databaseProcessor;
	private Serializer serializer=new KryoSerializer();

	public void setSerializer(Serializer serializer) {
		this.serializer = serializer;
	}

	public void setDatabaseProcessor(ClientDatabaseProcessor databaseProcessor) {
		this.databaseProcessor = databaseProcessor;
	}
	
	public TgtClientAesKryoProcessor(){
		this.databaseProcessor=new FileClientDatabaseProcessor();
	}
	
	public TgtClientAesKryoProcessor(String path){
		this.databaseProcessor=new FileClientDatabaseProcessor(path);
	}


	public FirstResponseWrapper getTgtResponse(FirstResponse firstResponse) {
		String password=databaseProcessor.getPassord();
		byte[] bytes=firstResponse.getTgtReponse();
		try{
			bytes=SecurityUtil.decryptAes(bytes, password);
			TgtResponse result=(TgtResponse) serializer.byte2Object(bytes);
			FirstResponseWrapper firstResponseWrapper=new FirstResponseWrapper();
			firstResponseWrapper.setTgt(firstResponse.getTgt());
			firstResponseWrapper.setTgtResponse(result);
			return firstResponseWrapper;
		}catch(AesSecurityException e){
			throw new PasswordError("Password is error;Client's password is different from KDC's your password");
		}
	}

	public FirstRequest getFirstRequest(long lifeTime) {
		FirstRequest firstRequest=new FirstRequest();
		String name=databaseProcessor.getName();
		firstRequest.setName(name);
		firstRequest.setTimestamp(new Date());
		firstRequest.setLifeTime(lifeTime);
		return firstRequest;
	}
	
	public byte[] getFirstRequestByte(long lifeTime){
		FirstRequest firstRequest=getFirstRequest(lifeTime);
		byte[] bytes=serializer.object2Byte(firstRequest);
		return bytes;
	}
	
}
