package com.cgs.kerberos.exception;

public class TgsException extends KerberosException{


	private static final long serialVersionUID = 4024043486569609206L;

	public TgsException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public TgsException(String msg,Exception e){
		super(msg,e);
	}
}
