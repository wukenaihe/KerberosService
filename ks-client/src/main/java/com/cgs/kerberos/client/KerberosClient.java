package com.cgs.kerberos.client;

import com.cgs.kerberos.bean.TicketGrantingTicket;
import com.cgs.kerberos.client.bean.FirstResponseWrapper;
import com.cgs.kerberos.exception.KerberosException;

public interface KerberosClient {
	FirstResponseWrapper getTgt() throws KerberosException;
	
	FirstResponseWrapper getTgt(int lifeTime) throws KerberosException;
}
