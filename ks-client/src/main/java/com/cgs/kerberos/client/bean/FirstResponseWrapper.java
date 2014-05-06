package com.cgs.kerberos.client.bean;

import com.cgs.kerberos.bean.TgtResponse;

public class FirstResponseWrapper {
	private TgtResponse tgtResponse;
	private byte[] tgt;
	public TgtResponse getTgtResponse() {
		return tgtResponse;
	}
	public void setTgtResponse(TgtResponse tgtResponse) {
		this.tgtResponse = tgtResponse;
	}
	public byte[] getTgt() {
		return tgt;
	}
	public void setTgt(byte[] tgt) {
		this.tgt = tgt;
	}
	
	
}
