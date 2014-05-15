package com.cgs.tool.commander;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

public class FileDatabaseCommander {
	@Parameter(description="The database file path.")
	public List<String> metaFileName = new ArrayList<String>();
	
	@Parameter(names={"-c","--create"},description="Create a database file")
	public boolean create=false;
	
	@Parameter(names={"-a", "--append"}, description="Append a new clientName")
	public boolean append=false;
	
	@Parameter(names={"-s","--updateKdcPassword"},description="Update KDC's password")
	public boolean updateKdcPassword=false;
	
	@Parameter(names={"-k","--updateKdc"},description="Update KDC's name and password")
	public boolean updateKdc=false;
	
	@Parameter(names={"-d","--delete"},description="Delete a client'name and password")
	public boolean delete=false;
	
	@Parameter(names={"-u","--updateClient"},description="Delete a client'name and password")
	public boolean updateClient=false;
	
	@Parameter(names={"-f","-filePath"},description="Database's file path")
	public String filePath;
	
	@Parameter(names={"-n","-name"},description="KDC's name or client name")
	public String name;
	
	@Parameter(names={"-p","-password"},description="KDC's name or client password")
	public String password;
	
	public boolean validate(){
		boolean validate=true;
		
		int num=0;
		if(create) num++;
		if(append) num++;
		if(updateKdcPassword) num++;
		if(updateKdc) num++;
		if(delete) num++;
		if(updateClient) num++;
		
		if(num==0){
			System.out.println("操作方式有且只有一项");
			validate=false;
			return validate;
		}else if(num>1){
			System.out.println("操作方式有且只有一项");
			validate=false;
			return validate;
		}
		
		if(create){
			if(name==null||password==null){
				System.out.println("创建KDC数据库的时候必须指定KDC的用户名和密码");
				return false;
			}
		}
		
		if(append){
			if(name==null||password==null){
				System.out.println("添加客户端的时候必须指定KDC的用户名和密码");
				return false;
			}
		}
		
		if(updateKdcPassword){
			if(name==null){
				System.out.println("修改KDC密码的时候，必须指定修改的密码");
				return false;
			}
		}
		
		if(updateKdc){
			if(name==null||password==null){
				System.out.println("更新KDC的时候必须指定KDC的用户名和密码");
				return false;
			}
		}
		
		if(delete){
			if(name==null){
				System.out.println("删除指定客户端的时候，必须要指定用户名");
			}
		}
		
		if(updateClient){
			if(name==null||password==null){
				System.out.println("更新客户端的时候必须指定KDC的用户名和密码");
				return false;
			}
		}
		return validate;
	}
}
