package com.cgs.kerberos.client.handle;

import com.cgs.kerberos.bean.FirstRequest;
import com.cgs.kerberos.bean.FirstResponse;
import com.cgs.kerberos.client.bean.FirstResponseWrapper;
import com.cgs.kerberos.util.Serializer;

public interface TgtClientProcessor {
	FirstResponseWrapper getTgtResponse(FirstResponse firstResponse);
	
	FirstRequest getFirstRequest(long lifeTime);
	
	FirstRequest getFirstRequest(String name,long lifeTime);
	
	void setDatabaseProcessor(ClientDatabaseProcessor databaseProcessor);
	
	void setSerializer(Serializer serializer);
//	byte[] getFirstRequestByte(long lifeTime);
//	
//	byte[] getFirstRequestByte(String name,long lifeTime);
	
}
