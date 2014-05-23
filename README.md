KerberosService
===============

  MIT的kerberos、window的AD与Aapche Directory均提供了Kerberos服务器，但是这三者均是面向PC而不是应用的，所以配置与初始化相对来说比较复杂。所以，在这里自己用java实现了一个kerberos服务端与客户端，包含kerberos所有的基本功能。默认的加密方式为AES，默认的序列化方式为kryo，账户密码默认通过文件进行保存。
  
Example Usage
=============
<h3>创建账号密码</h3>
<ol>
  <li>创建服务端账户密码，通过ks-tool中的FileDatabaseProcessor创建（main函数中已注释掉部分）。或者根据命令行来进行创建。创建结果为ks-example中的ks-database.dat</li>
  <li>创建客户端账户密码，通过ks-tool中的FileClientDatabaseProcessor创建。创建结果为ks-example中的ks-client-database.dat</li>
</ol>


<h3>启动服务端</h3>
  main函数方式启动
```java
    BaseTGTHandlerBuilder baseTGTHandlerBuilder = new BaseTGTHandlerBuilder();

		a = new TicketGrantTicketServer(8906);
		a.setTgtHandlerBuilder(baseTGTHandlerBuilder);
		new Thread(a).start();

		// BaseTGSHandlerBuilder baseTGSHandlerBuilder=new
		// BaseTGSHandlerBuilder();
		s = new TicketGrantServer(8907);

		new Thread(s).start();
```
