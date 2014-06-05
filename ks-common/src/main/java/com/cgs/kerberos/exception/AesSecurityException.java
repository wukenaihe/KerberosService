package com.cgs.kerberos.exception;

public class AesSecurityException extends SecurityException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -821066350673940923L;

	public AesSecurityException(String msg) {
		super(msg);
	}
	
	public AesSecurityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
