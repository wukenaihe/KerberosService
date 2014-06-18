package com.cgs.kerberos.client;

import com.cgs.kerberos.bean.ServiceTicket;
import com.cgs.kerberos.exception.KerberosException;

public interface KerberosFacade {
	
	/**
	 * 根据ST，生成第三次请求的内容。同时，通过统一化方式进行序列化
	 * 
	 * @param secondResponseWrapper
	 * @return
	 * @throws KerberosException
	 */
	byte[] getThirdRequestByte(String serverName) throws KerberosException;
	
	
	/**
	 * 检查请求者的ST，并生成ThirdResponse序列化之后的字节素数组
	 * 
	 * 如果产生异常，需要用别的方式告知请求客户端
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
	boolean checkServiceResponse(byte[] thirdResponse,String serverName) throws KerberosException;
	
	ServiceTicket decodeSt(byte[] thirdRequestByte) throws KerberosException;
}
