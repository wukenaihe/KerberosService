package com.cgs.kerberos.exception;

public class InvalidTgsRequest extends KerberosException{

	private static final long serialVersionUID = -6550290774719898502L;

	public InvalidTgsRequest(String msg) {
		super(msg);
	}

	public InvalidTgsRequest(String msg, Throwable cause) {
		super(msg, cause);
	}

}
