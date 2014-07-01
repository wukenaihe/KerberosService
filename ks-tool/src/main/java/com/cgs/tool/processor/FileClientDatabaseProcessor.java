package com.cgs.tool.processor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.util.SecurityUtil;

public class FileClientDatabaseProcessor {
	private static Logger logger = LoggerFactory.getLogger(FileClientDatabaseProcessor.class);

	private static final String DEFAULT_PATH = "ks-client-database.dat";
	private final String key = "5tgbNHY^";

	public void save(String name, String password,String path){
		File file;
		if (path == null) {
			file = new File(DEFAULT_PATH);
		} else {
			file = new File(path);
		}
		ObjectOutputStream objOutputStream=null;
		try {
			objOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			objOutputStream.writeObject(name);
			byte[] bytes = password.getBytes();
			bytes = SecurityUtil.encryptAes(bytes, key);
			objOutputStream.writeInt(bytes.length);
			objOutputStream.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				objOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void show(String path) throws Exception{
		File file;
		if (path == null) {
			file = new File(DEFAULT_PATH);
		} else {
			file = new File(path);
		}
		ObjectInputStream objectInputStream=null;
		try{
			objectInputStream=new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			String name=(String) objectInputStream.readObject();
			int length=objectInputStream.readInt();
			byte[] encrypted=new byte[length];
			objectInputStream.read(encrypted);
			byte[] decrypted=SecurityUtil.decryptAes(encrypted, key);
			String password=new String(decrypted);
			
			System.out.println("Name:"+name+"  Password:"+password);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		FileClientDatabaseProcessor f = new FileClientDatabaseProcessor();

		try {
//			f.save("secondFep", "123456",null);
			f.show(null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
