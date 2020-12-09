# 项目说明
本项目分为两个示例，一个是shiro-simple，一个是shiro-web（重点）。<br>
打开项目后，第一件事就是找到docs文件夹下的sql文件，到MySQL客户端执行数据库、表的创建，并初始化数据。

### shiro-simple
简单测试了:

* SimpleAccountRealm
* IniRealm
* JdbcRealm
* CustomRealm（自定义）

测试用例在test包下。

### shiro-web
主要:

* 自定义Realm
* 重新配置SecurityManager
* 基于Redis的会话管理
* 基于Redis的缓存管理
* 自动登录（“记住我”功能）

SpringBoot + Shiro + JPA + MySQL，本机jdk11，如果使用jdk1.8+，注意依赖包的版本。<br>
直接启动ShiroWebApplication就行了。

