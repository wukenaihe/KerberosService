package com.cgs.kerberos.handle;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgs.kerberos.exception.DatabaseException;
import com.cgs.kerberos.util.SecurityUtil;

public class FileDatabaseProcessor implements DatabaseProcessor {

	private static Logger logger = LoggerFactory.getLogger(FileDatabaseProcessor.class);

	private static final String DEFAULT_PATH = "ks-database.bin";
	private String path;
	private final String key = "zhejiangchenggong";
	
	public FileDatabaseProcessor(){
		
	}
	
	public FileDatabaseProcessor(String path){
		this.path=path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean contain(String name) throws DatabaseException {
		try {
			Map<String, String> map = loadDatabase();
			return map.containsKey(name);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseException("Does not contain " + name + " in KDC Database");
		}
	}

	public String getSelfPassword() throws DatabaseException {
		try {
			LinkedHashMap<String, String> map = loadDatabase();
			if(map.isEmpty()){
				throw new DatabaseException("KDC Password does not exist");
			}
			Set<String> keys = map.keySet();
			for (String string : keys) {
				String password = map.get(string);
				return password;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseException("Can not read KDC database");
		}
		return null;
	}

	public String getPassword(String name) throws DatabaseException {
		try {
			LinkedHashMap<String, String> map = loadDatabase();
			String passWord=map.get(name);
			if(passWord==null){
				throw new DatabaseException(name+" does not exist in KDC Database");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseException("Can not read KDC database");
		}
		return null;
	}

	private LinkedHashMap<String, String> loadDatabase() throws Exception {
		File file;
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		if (path == null) {
			file = new File(DEFAULT_PATH);
		} else {
			file = new File(path);
		}

		ObjectInputStream obj = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
		String name;
		try {
			while (true) {
				name = (String) obj.readObject();
				int num = obj.readInt();
				byte[] bytes = new byte[num];
				obj.read(bytes);
				String value = new String(SecurityUtil.decryptAes(bytes, key));
				map.put(name, value);

			}
		} catch (EOFException e) {
			// e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			obj.close();
		}
		return map;
	}

}
