package com.cgs.kerberos.client.handle;

import com.cgs.kerberos.bean.SecondRequest;
import com.cgs.kerberos.bean.SecondResponse;
import com.cgs.kerberos.client.bean.FirstResponseWrapper;
import com.cgs.kerberos.client.bean.SecondResponseWrapper;
import com.cgs.kerberos.exception.KerberosException;
import com.cgs.kerberos.util.Serializer;

public interface StClientProcessor {
	/**
	 * 
	 * 组装ST请求信息
	 * 
	 * @param firstResponseWrapper
	 *            第一次请求获取的信息
	 * @param clientName
	 *            客户端名字
	 * @param serverName
	 *            请求服务名称（务必确保正确）
	 * @param ip
	 *            请求服务器IP地址
	 * @param lifeTime
	 *            ST的有效时间
	 * @return
	 * @throws KerberosException
	 */
	SecondRequest getSecondRequest(FirstResponseWrapper firstResponseWrapper, String clientName, String serverName, String ip, long lifeTime)
			throws KerberosException;

	/**
	 * 
	 * 组装ST请求信息
	 * 
	 * @param firstResponseWrapper
	 *            第一次请求获取的信息
	 * @param serverName
	 *            请求服务名称（务必确保正确）
	 * @param ip
	 *            请求服务器IP地址
	 * @param lifeTime
	 *            ST的有效时间
	 * @return
	 * @throws KerberosException
	 */
	SecondRequest getSecondRequest(FirstResponseWrapper firstResponseWrapper, String serverName, String ip, long lifeTime) throws KerberosException;

	SecondResponseWrapper getStResponse(SecondResponse secondResponse, byte[] tgsSessionKey) throws KerberosException;

	void setDatabaseProcessor(ClientDatabaseProcessor databaseProcessor);

	void setSerializer(Serializer serializer);
}
