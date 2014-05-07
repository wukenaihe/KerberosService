package com.cgs.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cgs.kerberos.bean.FirstRequest;
import com.cgs.kerberos.util.KryoSerializer;

public class KryoUtilTest {
	
	private KryoSerializer kryoUtil=new KryoSerializer();
	
	@Test
	public void serialize(){
		FirstRequest f=new FirstRequest();
		f.setName("Ok");
		System.out.println(Arrays.toString(kryoUtil.object2Byte(f)));
	}
	
	@Test
	public void unSerialize(){
		FirstRequest f=new FirstRequest();
		f.setName("Ok");
		byte[] bytes=kryoUtil.object2Byte(f);
		FirstRequest decryptTest=(FirstRequest) kryoUtil.byte2Object(bytes);
		Assert.assertTrue(f.equals(decryptTest));
	}
	
	
}
