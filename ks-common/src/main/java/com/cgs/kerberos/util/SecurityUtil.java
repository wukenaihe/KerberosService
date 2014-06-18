package com.cgs.kerberos.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.exception.AesSecurityException;
import com.cgs.kerberos.exception.DesSecurityException;

/**
 * 
 * FileName：EncryptUtil.java
 * 
 * Description：DES加密解密工具类
 * 
 */
public class SecurityUtil {

	private static Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

	/**
	 * Des加密
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptDes(byte[] bytes, String key) throws DesSecurityException {
		try {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(buildDesKey(key));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(buildDesKey(key));
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			return cipher.doFinal(bytes);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DesSecurityException(e.getMessage(), e);
		}
	}

	/**
	 * Des解密
	 * 
	 * @param message
	 * @param key
	 * @return
	 */
	public static byte[] decryptDes(byte[] bytes, String key) throws DesSecurityException {
		try {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(buildDesKey(key));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(buildDesKey(key));
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			byte[] retByte = cipher.doFinal(bytes);
			return retByte;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DesSecurityException(e.getMessage(), e);
		}
	}

	/**
	 * AES加密
	 * 
	 * @param bytes
	 * @param key
	 * @return
	 * @throws AesSecurityException
	 */
	public static byte[] encryptAes(byte[] bytes, String key) throws AesSecurityException {
		try {
			Key keySpec = buildAesKey(key);
			// 实例化
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// 使用密钥初始化，设置为加密模式
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			// 执行操作
			return cipher.doFinal(bytes);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AesSecurityException(e.getMessage(), e);
		}
	}
	
	/**
	 * AES加密
	 * 
	 * @param bytes
	 * @param key
	 * @return
	 * @throws AesSecurityException
	 */
	public static byte[] encryptAes(byte[] bytes, byte[] key) throws AesSecurityException{
			Key keySpec = null;
			try {
				keySpec = buildAesKey(key);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				throw new AesSecurityException(e.getMessage(), e);
			}
			return encryptAes(bytes, keySpec);

	}
	
	/**
	 * AES加密
	 * 
	 * @param bytes
	 * @param key
	 * @return
	 * @throws AesSecurityException
	 */
	private static byte[] encryptAes(byte[] bytes, Key key) throws AesSecurityException{
		try {
			// 实例化
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// 使用密钥初始化，设置为加密模式
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 执行操作
			return cipher.doFinal(bytes);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AesSecurityException(e.getMessage(), e);
		}
	}
	
	/**
	 * AES解密
	 * 
	 * @param bytes
	 * @param key
	 * @return
	 * @throws AesSecurityException
	 */
	public static byte[] decryptAes(byte[] bytes, String key) throws AesSecurityException{
		Key keySpec;
		try {
			keySpec = buildAesKey(key);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new AesSecurityException(e.getMessage(), e);
		}
		return decryptAes(bytes, keySpec);
	}
	
	/**
	 * AES解密
	 * 
	 * @param bytes
	 * @param key
	 * @return
	 * @throws AesSecurityException
	 */
	public static byte[] decryptAes(byte[] bytes, byte[] key) throws AesSecurityException{
			Key keySpec;
			try {
				keySpec = buildAesKey(key);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				throw new AesSecurityException(e.getMessage(), e);
			}
			return decryptAes(bytes, keySpec);

	}
	
	/**
	 * AES解密
	 * 
	 * @param bytes
	 * @param key
	 * @return
	 * @throws AesSecurityException
	 */
	public static byte[] decryptAes(byte[] bytes, Key key) throws AesSecurityException{
		try {
			// 实例化
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// 使用密钥初始化，设置为加密模式
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 执行操作
			return cipher.doFinal(bytes);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AesSecurityException(e.getMessage(), e);
		}
	}

	/*
	 * 根据字符串生成密钥字节数组 ,Des规定为64位
	 * 
	 * @param keyStr 密钥字符串
	 * 
	 * @return
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] buildDesKey(String keyStr) throws UnsupportedEncodingException {
		byte[] key = new byte[8]; // 声明一个8位的字节数组，默认里面都是0
		byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组

		/*
		 * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}

	/*
	 * 根据字符串生成密钥字节数组 ,Aes规定为128位
	 * 
	 * @param keyStr 密钥字符串
	 * 
	 * @return
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private static Key buildAesKey(String keyStr) throws UnsupportedEncodingException {
		byte[] key = new byte[16]; // 声明一个8位的字节数组，默认里面都是0
		byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组

		/*
		 * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}

		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

		return keySpec;
	}
	
	/*
	 * 根据字符串生成密钥字节数组 ,Aes规定为128位
	 * 
	 * @param keyStr 密钥字符串
	 * 
	 * @return
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private static Key buildAesKey(byte[] keyStr) throws UnsupportedEncodingException {
		byte[] key = new byte[16]; // 声明一个8位的字节数组，默认里面都是0
		byte[] temp =keyStr; // 将字符串转成字节数组

		/*
		 * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}

		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

		return keySpec;
	}
}