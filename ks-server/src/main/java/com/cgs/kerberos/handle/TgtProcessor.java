package com.cgs.kerberos.handle;

import com.cgs.kerberos.bean.FirstRequest;
import com.cgs.kerberos.bean.FirstResponse;
import com.cgs.kerberos.exception.KerberosException;
import com.cgs.kerberos.exception.NoSuchUser;

/**
 * 根据Kerberos 流程处理TGS请求
 * 
 * @author xumh
 *
 */
public interface TgtProcessor {
	/**
	 * 根据请求信息进行验证
	 * 
	 * 1、从KDC数据库中查找是否有该用户,没有则抛出异常
	 * 2、如果有
	 * 	2.1、生成16位的TGT Session key
	 * 	2.1、生成
	 * 
	 * @param firstRequest
	 * @return
	 * @throws NoSuchUser
	 */
	FirstResponse check(FirstRequest firstRequest) throws NoSuchUser;
}
