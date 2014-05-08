package com.cgs.kerberos.client;

import com.cgs.kerberos.bean.TicketGrantingTicket;
import com.cgs.kerberos.client.bean.FirstResponseWrapper;
import com.cgs.kerberos.client.bean.SecondResponseWrapper;
import com.cgs.kerberos.exception.KerberosException;

public interface KerberosClient {
	/**
	 * 获取TGT包装类，名字通过ClientDatabaseProcessor获取，有效期为永久
	 * 
	 * @return
	 * @throws KerberosException
	 */
	FirstResponseWrapper getTgt() throws KerberosException;

	/**
	 * 获取TGT包装类，名字通过ClientDatabaseProcessor获取
	 * 
	 * @param lifeTime 有效期单位分钟
	 * @return
	 * @throws KerberosException
	 */
	FirstResponseWrapper getTgt(long lifeTime) throws KerberosException;

	/**
	 *  获取TGT包装类，有效期为永久
	 * 
	 * @param name
	 * @return
	 * @throws KerberosException
	 */
	FirstResponseWrapper getTgt(String name) throws KerberosException;

	/**
	 * 获取TGT包装类
	 * 
	 * @param name
	 * @param lifeTime
	 * @return
	 * @throws KerberosException
	 */
	FirstResponseWrapper getTgt(String name, long lifeTime) throws KerberosException;
		
	SecondResponseWrapper getSt(FirstResponseWrapper firstResponseWrapper,String clientName,String serverName,String ip,long lifeTime) throws KerberosException;
	
	SecondResponseWrapper getSt(FirstResponseWrapper firstResponseWrapper,String serverName,String ip,long lifeTime) throws KerberosException;
	
SecondResponseWrapper getSt(FirstResponseWrapper firstResponseWrapper,String clientName,String serverName,String ip) throws KerberosException;
	
	SecondResponseWrapper getSt(FirstResponseWrapper firstResponseWrapper,String serverName,String ip) throws KerberosException;

}
