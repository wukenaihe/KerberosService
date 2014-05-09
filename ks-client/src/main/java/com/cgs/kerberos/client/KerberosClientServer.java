package com.cgs.kerberos.client;

import com.cgs.kerberos.bean.ThirdRequest;
import com.cgs.kerberos.client.bean.SecondResponseWrapper;
import com.cgs.kerberos.exception.KerberosException;

/**
 * 客户端请求服务，服务端验证客户端等功能
 * 
 * @author xumh
 *
 */
public interface KerberosClientServer {
	/**
	 * 根据ST，生成第三次请求的内容
	 * 
	 * @param secondResponseWrapper
	 * @return
	 * @throws KerberosException
	 */
	ThirdRequest getThirdRequest(SecondResponseWrapper secondResponseWrapper) throws KerberosException;
	
	/**
	 * 根据ST，生成第三次请求的内容。同时，通过统一化方式进行序列化
	 * 
	 * @param secondResponseWrapper
	 * @return
	 * @throws KerberosException
	 */
	byte[] getThirdRequestByte(SecondResponseWrapper secondResponseWrapper) throws KerberosException;
	
	
	/**
	 * 检查请求者的ST，并生成ThirdResponse序列化之后的字节素数组
	 * 
	 * @param thridRequestByte 请求者的请求信息
	 * @param ip 请求者IP
	 * @return
	 * @throws KerberosException
	 */
	byte[] checkServiceTicket(byte[] thirdRequestByte) throws KerberosException;
	
	/**
	 * 检查是否为你想要的服务器发给你的
	 * 
	 * @param thirdResponse
	 * @return
	 * @throws KerberosException
	 */
	boolean checkServiceResponse(byte[] thirdResponse,SecondResponseWrapper secondResponseWrapper) throws KerberosException;
}
