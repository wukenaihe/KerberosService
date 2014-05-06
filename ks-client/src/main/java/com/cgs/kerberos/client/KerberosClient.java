package com.cgs.kerberos.client;

import com.cgs.kerberos.bean.TicketGrantingTicket;
import com.cgs.kerberos.client.bean.FirstResponseWrapper;
import com.cgs.kerberos.exception.KerberosException;

public interface KerberosClient {
	FirstResponseWrapper getTgt(String name) throws KerberosException;
	
	FirstResponseWrapper getTgt(String name,int lifeTime) throws KerberosException;
}
