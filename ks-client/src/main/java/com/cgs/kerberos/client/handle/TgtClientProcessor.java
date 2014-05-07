package com.cgs.kerberos.client.handle;

import com.cgs.kerberos.bean.FirstRequest;
import com.cgs.kerberos.bean.FirstResponse;
import com.cgs.kerberos.client.bean.FirstResponseWrapper;

public interface TgtClientProcessor {
	FirstResponseWrapper getTgtResponse(FirstResponse firstResponse);
	
	FirstRequest getFirstRequest(long lifeTime);
	
	byte[] getFirstRequestByte(long lifeTime);
}
