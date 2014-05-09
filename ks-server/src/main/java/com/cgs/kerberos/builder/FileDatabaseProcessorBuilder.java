package com.cgs.kerberos.builder;

import com.cgs.kerberos.handle.DatabaseProcessor;
import com.cgs.kerberos.handle.FileDatabaseProcessor;

public class FileDatabaseProcessorBuilder implements DatabaseProcessorBuilder{
	private String path;
	
	public FileDatabaseProcessorBuilder(){
		
	}
	
	public FileDatabaseProcessorBuilder(String filePath){
		this.path=filePath;
	}

	public DatabaseProcessor getDatabaseProcessor() {
		FileDatabaseProcessor fdp=new FileDatabaseProcessor();
		if(path!=null) fdp.setPath(path);
		return fdp;
	}

}
