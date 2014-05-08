package com.cgs.kerberos.client.bean;

import java.io.Serializable;
import java.util.Arrays;

import com.cgs.kerberos.bean.SecondResponse.SecondResponseBody;

public class SecondResponseWrapper implements Serializable{

	private static final long serialVersionUID = -7549229944925116162L;
	
	private SecondResponseBody secondResponseBody;
	private byte[] st;
	public SecondResponseBody getSecondResponseBody() {
		return secondResponseBody;
	}
	public void setSecondResponseBody(SecondResponseBody secondResponseBody) {
		this.secondResponseBody = secondResponseBody;
	}
	public byte[] getSt() {
		return st;
	}
	public void setSt(byte[] st) {
		this.st = st;
	}
	@Override
	public String toString() {
		return "SecondResponseWrapper [secondResponseBody=" + secondResponseBody + ", st=" + Arrays.toString(st) + "]";
	}
	
	
	
}
