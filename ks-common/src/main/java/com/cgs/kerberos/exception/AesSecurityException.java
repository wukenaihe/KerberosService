package com.cgs.kerberos.exception;

public class AesSecurityException extends SecurityException{

	public AesSecurityException(String msg) {
		super(msg);
	}
	
	public AesSecurityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
