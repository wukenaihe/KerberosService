package com.cgs.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.cgs.kerberos.bean.FirstRequest;
import com.cgs.kerberos.util.SecurityUtil;
import com.cgs.kerberos.util.KryoSerializer;

public class EncyptUtilTest {
	private FirstRequest f = FirstRequest.getInstance();
	private KryoSerializer kryUtil = new KryoSerializer();
	private final String key = "1345678";

	@Test
	public void encryptDes() {
		byte[] bytes = kryUtil.object2Byte(f);
		byte[] encyptedBytes = null;
		try {
			encyptedBytes = SecurityUtil.encryptDes(bytes, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Arrays.toString(encyptedBytes));
	}

	@Test
	public void decryptDes() throws Exception {
		byte[] bytes = kryUtil.object2Byte(f);
		byte[] encyptedBytes = null;
		encyptedBytes = SecurityUtil.encryptDes(bytes, key);
		byte[] def = SecurityUtil.decryptDes(encyptedBytes, key);
		Assert.assertNotNull(def);
		FirstRequest defirst = kryUtil.byte2Object(def);
		Assert.assertTrue(f.equals(defirst));
	}

	@Test
	public void encryptAes() {
		byte[] bytes = kryUtil.object2Byte(f);
		byte[] encyptedBytes = null;
		encyptedBytes = SecurityUtil.encryptAes(bytes, key);

		System.out.println(Arrays.toString(encyptedBytes));
	}

	@Test
	public void decryptAes() throws Exception {
		byte[] bytes = kryUtil.object2Byte(f);
		byte[] encyptedBytes = null;
		encyptedBytes = SecurityUtil.encryptAes(bytes, key);
		byte[] def = SecurityUtil.decryptAes(encyptedBytes, key);
		Assert.assertNotNull(def);
		FirstRequest defirst = kryUtil.byte2Object(def);
		Assert.assertTrue(f.equals(defirst));
	}
}
