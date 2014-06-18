package com.cgs.kerberos.handle;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.cgs.kerberos.exception.DatabaseException;
import com.cgs.kerberos.util.SecurityUtil;

public class DbDatabaseProcessor implements DatabaseProcessor{
	
	private static Logger logger=LoggerFactory.getLogger(DbDatabaseProcessor.class);

	private NamedParameterJdbcTemplate jdbcTemplate;
	private BasicDataSource dataSource;
	
	private static final String key="zhejiangchenggong";
	
	public static final String HAS_NAME="select count(*) from kerberos_client_database k where k.name=:name";
	public static final String GET_SERVER_PASSWORD="select encryptPassword from kerberos_server_database k";
	public static final String GET_CLIENT_PASSWORD="select encryptPassword from kerberos_client_database k from k.name=:name";
	public static final String GET_SERVER_NAME="select name from kerberos_server_database k";
	
	
	public DbDatabaseProcessor(BasicDataSource dataSource){
		this.dataSource=dataSource;
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Transactional(readOnly=true)
	public boolean contain(String name) throws DatabaseException {
		Map<String,String> param = new HashMap<String, String>();
		param.put("name", name);
		int num=jdbcTemplate.queryForInt(HAS_NAME, param);
		if(num<=0){
			return false;
		}
		return true;
	}

	@Transactional(readOnly=true)
	public String getSelfPassword() throws DatabaseException {
		List<Map<String, Object>> result=jdbcTemplate.queryForList(GET_SERVER_PASSWORD, new HashMap());
		Map<String, Object> map=result.get(0);
		byte[] bytes=(byte[]) map.get("encodePassword");
		byte[] decryptBytes=SecurityUtil.decryptAes(bytes, key);
		String s=new String(decryptBytes);
		return s;
	}

	@Transactional(readOnly=true)
	public String getPassword(String name) throws DatabaseException {
		Map<String,String> param = new HashMap<String, String>();
		param.put("name", name);
		List<Map<String, Object>> result=jdbcTemplate.queryForList(GET_CLIENT_PASSWORD, param);
		Map<String, Object> map=result.get(0);
		byte[] bytes=(byte[]) map.get("encodePassword");
		byte[] decryptBytes=SecurityUtil.decryptAes(bytes, key);
		String s=new String(decryptBytes);
		return s;
	}

	@Transactional(readOnly=true)
	public String getSelfName() throws DatabaseException {
		List<String> result=jdbcTemplate.queryForList(GET_SERVER_NAME, new HashMap(), String.class);
		String name=result.get(0);
		return name;
	}

	public void close() throws DatabaseException {
		try {
			dataSource.close();
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		}
	}

}
