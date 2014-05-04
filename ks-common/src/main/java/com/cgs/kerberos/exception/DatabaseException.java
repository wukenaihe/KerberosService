package com.cgs.kerberos.exception;

public class DatabaseException extends KerberosException{

	private static final long serialVersionUID = 6237101549893893353L;

	public DatabaseException(String msg) {
		super(msg);
	}

	public DatabaseException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
