package com.cgs.kerberos.exception;

public abstract class SecurityException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -588963529358718668L;

	/**
	 * Constructor for SecurityException.
	 * @param msg the detail message
	 */
	public SecurityException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for SecurityException.
	 * @param msg the detail message
	 * @param cause the root cause (usually from using a underlying
	 * data access API such as JDBC)
	 */
	public SecurityException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
