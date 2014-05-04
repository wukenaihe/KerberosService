package com.cgs.kerberos.exception;

public abstract class KerberosException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1973102846495089021L;

	public KerberosException(String msg) {
		super(msg);
	}

	public KerberosException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
