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
  servlet listener方式启动,通过配置web.xml实现
```xml
   	<context-param>
		<param-name>tgtServerPort</param-name>
		<param-value>8906</param-value>
	</context-param>
	<context-param>
		<param-name>tgsServerPort</param-name>
		<param-value>8907</param-value>
	</context-param>
	<context-param>
		<param-name>fileDatabase</param-name>
		<param-value>E:/ks-database.dat</param-value>
	</context-param>
	<listener>
		<listener-class>com.cgs.kerberos.server.KerberosServletContextListener</listener-class>
	</listener>
```
<h3>客户端验证</h3>

```java
	//创建服务请求的内容（自动获取tgt,st）,如在电脑pc1上
	byte[] request = kerberosFacade.getThirdRequestByte("server");
	
	//你请求的服务端验证，并创建回送的信息()，如在电脑pc2上
	byte[] response = kerberosFacade.checkServiceTicket(request);
	
	//验证是否是你请求的服务回送的信息，如在电脑pc1上
	boolean ok = kerberosFacade.checkServiceResponse(response, "server");
```

Licenses
=======
Copyright [2014] [XuMinhua]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Remark
======
   E-mail:wukenaihesos@gmail.com
