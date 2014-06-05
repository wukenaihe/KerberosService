package com.cgs.kerberos.exception;

public class DesSecurityException extends SecurityException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5539168772773691737L;

	public DesSecurityException(String msg) {
		super(msg);
	}
	
	public DesSecurityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
