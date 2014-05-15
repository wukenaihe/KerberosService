package com.cgs.tool.commander;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.cgs.tool.processor.FileDatabaseProcessor;

public class FileDatabaseTool {
		private static Logger logger;
	
		public static void main(String[] args) {
			logger = LoggerFactory.getLogger(FileDatabaseTool.class);
			
			final String USAGE = "创建KDC文件式数据库，保存服务器用户名密码，保存客户端用户名和密码 \n" +
					"[--create|--update|--append|--updateKdcPassword|--updateKdc|--delete|--updateClient   --file] \n" + 
					"[-c  --create(-n -p)  创建数据库，如果不指定文件路径，则当前目录下创建。创建时，必须要指定KDC的用户名和密码] \n" +
					"[-a  --append(-n -p)  添加客户端用户名与密码] \n" +
					"[-s  --updateKdcPassword(-p)  更新KDC的密码] \n" +
					"[-k  --updateKdc(-n -p)  更新KDC的用户名和密码] \n" +
					"[-d  --delete(-n)  删除指定的用户名] \n" +
					"[-u  --updateClient(-n -p)]\n"+
					"[-f  --filePath    数据库文件路径]\n"+
					"[-n  --name   用户名]\n"+
					"[-p  --password 密码]\n";
			if(args.length < 2) {
				System.out.println(USAGE);
				return;
			}
			
			FileDatabaseCommander commander=new FileDatabaseCommander();
			new JCommander(commander,args);
			
			if(!commander.validate()){
				System.out.println(USAGE);
				return;
			}
			
			FileDatabaseProcessor fileDatabaseProcessor=new FileDatabaseProcessor();
			
			if(commander.create){
				fileDatabaseProcessor.create(commander.filePath, commander.name, commander.password);
				return;
			}
			
			if(commander.append){
				fileDatabaseProcessor.append(commander.name, commander.password, commander.filePath);
				return;
			}
			
			if(commander.updateClient){
				fileDatabaseProcessor.changePassword(commander.name, commander.password, commander.filePath);
				return;
			}
			
			if(commander.updateKdc){
				fileDatabaseProcessor.updateKdc(commander.name, commander.password, commander.filePath);
			}
			
			if(commander.updateKdcPassword){
				fileDatabaseProcessor.changeSelfPassword(commander.filePath, commander.filePath);
				return;
			}
			
			if(commander.delete){
				fileDatabaseProcessor.delete(commander.name, commander.filePath);
				return;
			}
			
		}
}
