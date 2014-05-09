package com.cgs.kerberos.exception;

public class InvalidStException extends KerberosException {

	private static final long serialVersionUID = 4315516427757331128L;

	public InvalidStException(String msg) {
		super(msg);
	}

	public InvalidStException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
