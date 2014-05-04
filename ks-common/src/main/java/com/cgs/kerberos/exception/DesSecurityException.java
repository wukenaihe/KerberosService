package com.cgs.kerberos.exception;

public class DesSecurityException extends SecurityException{

	public DesSecurityException(String msg) {
		super(msg);
	}
	
	public DesSecurityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
