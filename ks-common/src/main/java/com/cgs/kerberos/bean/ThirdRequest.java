package com.cgs.kerberos.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class ThirdRequest implements Serializable{
	
	public static class ThirdRequestInformation implements Serializable{
		private String clientName;
		private Date timeStamp;
		
		public Date getTimeStamp() {
			return timeStamp;
		}
		public void setTimeStamp(Date timeStamp) {
			this.timeStamp = timeStamp;
		}
		public String getClientName() {
			return clientName;
		}
		public void setClientName(String clientName) {
			this.clientName = clientName;
		}
		@Override
		public String toString() {
			return "ThirdRequestInformation [clientName=" + clientName + ", timeStamp=" + timeStamp + "]";
		}
	}
	
	private byte[] thirdRequestInformation;
	
	private byte[] st;

	public byte[] getThirdRequestInformation() {
		return thirdRequestInformation;
	}

	public void setThirdRequestInformation(byte[] thirdRequestInformation) {
		this.thirdRequestInformation = thirdRequestInformation;
	}

	public byte[] getSt() {
		return st;
	}

	public void setSt(byte[] st) {
		this.st = st;
	}

	@Override
	public String toString() {
		return "ThirdRequest [thirdRequestInformation=" + Arrays.toString(thirdRequestInformation) + ", st=" + Arrays.toString(st) + "]";
	}
}
