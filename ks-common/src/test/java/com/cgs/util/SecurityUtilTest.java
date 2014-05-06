package com.cgs.util;

import com.cgs.kerberos.util.SecurityUtil;

public class SecurityUtilTest {
	public static void main(String[] args) {
		String test="Test";
		byte[] bytes=SecurityUtil.encryptAes(test.getBytes(), "123");
		byte[] reBytes=SecurityUtil.decryptAes(bytes, "123");
		System.out.println(new String(reBytes));
		reBytes=SecurityUtil.decryptAes(bytes, "1234");
		System.out.println(new String(reBytes));
	}
}
