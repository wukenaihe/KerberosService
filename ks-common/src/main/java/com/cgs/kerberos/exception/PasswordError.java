package com.cgs.kerberos.exception;

public class PasswordError extends KerberosException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4801849019899458715L;

	public PasswordError(String msg) {
		super(msg);
	}

	public PasswordError(String msg, Throwable cause) {
		super(msg, cause);
	}
}
