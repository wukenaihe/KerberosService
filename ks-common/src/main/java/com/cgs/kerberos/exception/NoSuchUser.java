package com.cgs.kerberos.exception;

public class NoSuchUser extends KerberosException{
	
	private static final long serialVersionUID = -8083144363647929376L;

	public NoSuchUser(String msg) {
		super(msg);
	}

	public NoSuchUser(String msg, Throwable cause) {
		super(msg, cause);
	}
}
