package com.cgs.kerberos.bean;

import java.util.Date;

public class FirstResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6332028047953466335L;
	
	private byte[] tgt;//TicketGrantingTicket对象序列化，用服务器自己密码加密后的内容
	private byte[] tgtReponse;//TgtReponse对象序列化，用客户端密码加密后的内容
	public byte[] getTgt() {
		return tgt;
	}
	public void setTgt(byte[] tgt) {
		this.tgt = tgt;
	}
	public byte[] getTgtReponse() {
		return tgtReponse;
	}
	public void setTgtReponse(byte[] tgtReponse) {
		this.tgtReponse = tgtReponse;
	}
	
}
