package com.cgs.tool.processor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.util.SecurityUtil;

public class FileClientDatabaseProcessor {
	private static Logger logger = LoggerFactory.getLogger(FileClientDatabaseProcessor.class);

	private static final String DEFAULT_PATH = "ks-client-database.dat";
	private String path;
	private final String key = "5tgbNHY^";

	private void save(String name,String password) throws Exception {
		File file;
		if (path == null) {
			file = new File(DEFAULT_PATH);
		} else {
			file = new File(path);
		}
		ObjectOutputStream objOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
		try {
				objOutputStream.writeObject(name);
				byte[] bytes = password.getBytes();
				bytes = SecurityUtil.encryptAes(bytes, key);
				objOutputStream.writeInt(bytes.length);
				objOutputStream.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			objOutputStream.close();
		}

	}

	public static void main(String[] args) throws Exception {
		FileClientDatabaseProcessor f = new FileClientDatabaseProcessor();

		try {
			f.save("sourceFep", "123456");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
