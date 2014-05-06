package com.cgs.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cgs.kerberos.util.KryoSerializer;

public class KryoUtilTest {
	
	private KryoSerializer kryoUtil=new KryoSerializer();
	
	@Test
	public void serialize(){
		String test="Hello world";
		System.out.println(Arrays.toString(kryoUtil.object2Byte(test)));
	}
	
	@Test
	public void unSerialize(){
		String test="Hello World";
		byte[] bytes=kryoUtil.object2Byte(test);
		String decryptTest=kryoUtil.byte2Object(bytes);
		Assert.assertTrue(test.equals(decryptTest));
	}
	
	
}
