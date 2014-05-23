KerberosService
===============

  MIT的kerberos、window的AD与Aapche Directory均提供了Kerberos服务器，但是这三者均是面向PC而不是应用的，所以配置与初始化相对来说比较复杂。所以，在这里自己用java实现了一个kerberos服务端与客户端，包含kerberos所有的基本功能。默认的加密方式为AES，默认的序列化方式为kryo，账户密码默认通过文件进行保存。
  
Example Usage
=============
<h3>创建账号密码</h3>
1、创建服务端账户密码，通过ks-tool中的FileDatabaseProcessor创建（main函数中已注释掉部分）。或者根据命令行来进行创建。
2、创建客户端账户密码，通过ks-tool中的FileClientDatabaseProcessor创建。

