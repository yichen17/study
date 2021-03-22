# vmware中 安装虚拟机

## 虚拟机账户信息

### yichen_1

+ 用户名   yichen
+ 密码   admin
+ 虚拟机名称  yichen_1
+ 位置  D:\personal\learn note\virtual_marchine\yichen_1  
+ 单个磁盘文件

### banyu

+ 用户名  root
+ 密码 admin

### shanliang

 内容同上   为banyu 的副本



## 虚拟机和ip对应关系

| 虚拟机名称 | IP地址          | zooker 标识 |
| ---------- | --------------- | ----------- |
| yichen     | 192.168.175.129 | 0           |
| banyu      | 192.168.175.128 | 1           |
| shanliang  | 192.168.175.130 | 2           |



## 常用命令

+ 开启zookeeper  zkServer.sh start
+ 关闭 zookeeper  zkServer.sh stop





## 配置java环境

+ 官网下载jdk  [官网链接](https://www.oracle.com/java/technologies/javase-downloads.html)

+ 通过xftp将 本机下载的文件传输到 虚拟机中

  解压命令  ：   `tar -zxvf /opt/jdk-8u271-linux-x64.tar.gz` -C /usr/local/java

+ 对压缩文件进行解压，并配置相应的环境

```
#set java environment
JAVA_HOME=/usr/local/java/jdk1.8.0_271
CLASSPATH=.:$JAVA_HOME/lib.tools.jar
PATH=$JAVA_HOME/bin:$PATH
export JAVA_HOME CLASSPATH PATH 
```





[参考步骤](https://www.cnblogs.com/404code/p/10927570.html)



## center os 安装docker

[centos  安装docker 的步骤](https://www.cnblogs.com/xiaoyige/p/12673076.html)

## unbutu安装docker

```
apt-get install -y docker.io
// 开启docker
systectl start docker
```

## 防火墙

### unbutu  

查看防火墙 sudo ufw status

关闭/开启防火墙  sudo ufw disable/enable

###  center os 7

查看防火墙  firewall-cmd --state

关闭/开启防火墙  systemctl stop/start firewalld

关闭/开启  自启动防火墙    systemctl disable/enable firewalld

## 使用阿里镜像

```
vi /etc/docker/daemon.json
// 将下面的内容写进去
"registry-mirrors": ["https://f9dk003m.mirror.aliyuncs.com"]
// 重启  daemon
systemctl daemon-reload
// 重启 docker
systemctl restart docker
 // 下载指定版本进行，这里mysql 示例
 docker pull mysql:5.5.58
 // 查看镜像
 docker images
 // 删除镜像 
 docker rmi xxx(镜像名字)
 // 运行该镜像
docker run -p 3306:3306 -d --name mysql -e MYSQL_ROOT_PASSWORD=admin mysql:5.5.58
// 查看容器信息  
docker ps -a
docker ps 
// 启动，暂停 ps
docker start/stop xxx( 对应的container id)
//  docker 镜像的存放位置
/var/lib/docker
```







## 搭建zookeeper 

[zookeeper搭建参考链接](https://www.cnblogs.com/ysocean/p/9860529.html)

+ 常用到的命令
  - 开启 zookeeper  zkServer.sh start
  - 关闭  zkServer.sh stop
  - 客户端连接 zookeeper   zkCli.sh -server 192.168.175.128:2181
+ 通过scp远程拷贝 文件。(首先需要确保目的地目录存在)

```
// 将 zookeeper   远程拷贝
scp -r /usr/local/software/apache-zookeeper-3.5.8-bin root@192.168.175.128:/usr/local/software/apache-zookeeper-3.5.8-bin

// 将java  远程拷贝
scp -r /usr/java/jdk1.8.0_271  root@192.168.175.128:/usr/java/jdk1.8.0_271


```

+ 虽然通过远程拷贝的文件夹，但是环境变量还是得配置一下，毕竟没有拷贝 profile文件。

```
//   修改profile 文件
vi /etc/profile

// 在行尾添加如下内容
#set java environment
    JAVA_HOME=/usr/java/jdk1.8.0_271
    CLASSPATH=.:$JAVA_HOME/lib.tools.jar
    PATH=$JAVA_HOME/bin:$PATH
    export JAVA_HOME CLASSPATH PATH
    
#set zookeeper environment
export ZK_HOME=/usr/local/software/apache-zookeeper-3.5.8-bin
export PATH=$PATH:$ZK_HOME/bin

// 当然，也可以把当前的profile 删了，然后远程拷贝一个过去


// 让更改后的profile 生效
 source /etc/profile
```



## 命令行操作 zookeeper

通过  `zkCli.sh -server 192.168.175.128:2181`   登陆zookeeper

### 常用命令

+ 查看目录下的文件    ls /
+ 创建子节点       create  /config（目录名）  yichen（数据）
+ 查看节点的数据    get /config
+ 修改目录下的数据    set  /config（目录名）    banyu（新的数据）
+ 



## linux 下安装  rocketMQ

### 启动rocketMQ

+ 启动nameserver

```
# 后台启动 nameserver
nohup sh bin/mqnamesrv &
# 查看启动日志
tail -f ~/logs/rocketmqlogs/namesrv.log
# 关闭
sh bin/mqshutdown namesrv
```

+ 启动broker

```
# 后台启动 broker
nohup sh bin/mqbroker -n localhost:9876 &
# 查看启动日志
tail -f ~/logs/rocketmqlogs/broker.log
# 关闭
sh bin/mqshutdown broker
```

+ 修改虚拟机内存大小

```
vi runbroker.sh
vi runserver.sh
// java 虚拟机内存修改如下
JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn128m -XX:MetaspaceSize=128 -XX:MaxMetaspaceSize=320m"
```

### 测试 rocketMQ

+ 发送消息

```
# 设置环境变量
export NAMESRV_ADDR=localhost:9876
# 使用安装包的Demo 发送消息
sh bin/tools.sh org.apache.rocketmq.example.quickstart.Producer
```

+ 接收消息

```
# 设置环境变量
export NAMESRV_ADDR=localhost:9876
# 接收消息
sh bin/tools.sh org.apache.rocketmq.example.quickstart.Consumer
```

## rockerMQ 角色介绍

+ Producer：消息的发送者
+ Consumer：消息的接收者
+ Broker：暂存和传输消息
+ NameServer：管理Broker，类似于zookeeper ，注册中心
+ Topic： 区分消息的种类
+ Message Queue：相当于是Topic的分区，用于并行发送和接收消息



## 搭建rocket集群

| 序号 | IP              | 角色                     | 架构模式        |
| ---- | --------------- | ------------------------ | --------------- |
| 1    | 192.168.175.128 | namesever、brokersever   | master1、salve2 |
| 2    | 192.168.175.130 | nameserver、brokerserver | master2、slave1 |

### 配置host信息

```
vi /etc/hosts

# namesever
192.168.175.128 rocketmq-nameserver1
192.168.175.130 rocketmq-nameserver2
# broker
192.168.175.128 rocketmq-master1
192.168.175.128 rocketmq-slave2
192.168.175.130 rocketmq-master2
192.168.175.130 rocketmq-slave1
# 重启网卡
systemctl restart network
```

### 配置环境变量

```
vi /etc/profile

# set rocketmq
ROCKETMQ_HOME=/usr/local/software/rocketmq-all-4.7.1-bin-release
PATH=$PATH:$ROCKETMQ_HOME/bin
export ROCKETMQ_HOME PATH

// 使配置生效
source /etc/profile
```

### 设置消息存储位置

```
mkdir /usr/local/software/rocketmq
mkdir /usr/local/software/rocketmq/store
mkdir /usr/local/software/rocketmq/store/commitlog
mkdir /usr/local/software/rocketmq/store/consumequeue
mkdir /usr/local/software/rocketmq/store/index
```

### 启动broker

```
// banyu
nohup sh mqbroker -c /usr/local/software/rocketmq-all-4.7.1-bin-release/conf/2m-2s-sync/broker-a.properties &

nohup sh mqbroker -c /usr/local/software/rocketmq-all-4.7.1-bin-release/conf/2m-2s-sync/broker-b-s.properties &
// shanliang
nohup sh mqbroker -c /usr/local/software/rocketmq-all-4.7.1-bin-release/conf/2m-2s-sync/broker-b.properties &

nohup sh mqbroker -c /usr/local/software/rocketmq-all-4.7.1-bin-release/conf/2m-2s-sync/broker-a-s.properties &


```

## rocketMQ 的常见用法

### 发送消息

+ 同步发送消息：常用于重要的消息通知、短信通知等。
+ 异步发送消息：常用于对响应时间敏感的业务。
+ 单向发送消息：常用于不特别关心发送结果的场景，如日志发送。

### 顺序消息

消息有序是指按照消息的发送顺序来消费（FIFO）。rocketMQ 可以严格的保证消息有序，可以分为分区有序和全局有序。

顺序消费的原理解析，在默认的情况下消息发送会采取Round Robin轮询方式把消息发送到不同的queue(分区队列)；而消费消息的时候从多个queue上拉取消息，这种情况发送和消费是不能保证顺序。但是如果控制发送的顺序消息只依次发送到同一个queue中，消费的时候只从这个queue上依次拉取，则就保证了顺序。当发送和消费参与的queue只有一个，则是全局有序；如果多个queue参与，则为分区有序，即相对每个queue，消息都是有序的。

### 延迟消息

即发送消息的时候不是立即发送，而是指定时间进行延迟发送，比如10分钟发送

### 批量消息

即修改原来发送消息的方式，由for循环进行循环发送单挑数据转为直接发送一个消息集合，<font color=#ff0000>但这里需要主要集合的大小不能超过4M，不然需要进行消息分片。</font>

### 过滤消息

在大多数情况下，TAG是一个简单而有用的设计，其可以来选择您想要的消息。例如：

```
DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("CID_EXAMPLE");
consumer.subscribe("TOPIC", "TAGA || TAGB || TAGC");
```

消费者将接收包含TAGA或TAGB或TAGC的消息。但是限制是一个消息只能有一个标签，这对于复杂的场景可能不起作用。在这种情况下，可以使用SQL表达式筛选消息。SQL特性可以通过发送消息时的属性来进行计算。在RocketMQ定义的语法下，可以实现一些简单的逻辑。

#### 基本语法

RocketMQ只定义了一些基本语法来支持这个特性。你也可以很容易地扩展它。

- 数值比较，比如：**>，>=，<，<=，BETWEEN，=；**
- 字符比较，比如：**=，<>，IN；**
- **IS NULL** 或者 **IS NOT NULL；**
- 逻辑符号 **AND，OR，NOT；**

常量支持类型为：

- 数值，比如：**123，3.1415；**
- 字符，比如：**'abc'，必须用单引号包裹起来；**
- **NULL**，特殊的常量
- 布尔值，**TRUE** 或 **FALSE**

只有使用push模式的消费者才能用使用SQL92标准的sql语句，接口如下：

```
public void subscribe(finalString topic, final MessageSelector messageSelector)
```

#### 生产者指定

```
// 设置一些属性
msg.putUserProperty("a", String.valueOf(i));
```

#### 消费者过滤

```
// 只有订阅的消息有这个属性a, a >=0 and a <= 3
consumer.subscribe("TopicTest", MessageSelector.bySql("a between 0 and 3");
```

### 消息事务

#### 注意

如果生产者在`executeLocalTransaction` 上返回  LocalTransactionState.UNKNOW，则需要经历`transactionTimeOut`中设定的时间后才会 调用`checkLocalTransaction`来进行重新审查。该属性可以通过broker的配置文件进行配置。默认是6s。且由于是事务，所以不能在发送消息后把生产者 shutdown，应该让它保持运行。

#### 事务消息使用上的限制

1. 事务消息不支持延时消息和批量消息。
2. 为了避免单个消息被检查太多次而导致半队列消息累积，我们默认将单个消息的检查次数限制为 15 次，但是用户可以通过 Broker 配置文件的 `transactionCheckMax`参数来修改此限制。如果已经检查某条消息超过 N 次的话（ N = `transactionCheckMax` ） 则 Broker 将丢弃此消息，并在默认情况下同时打印错误日志。用户可以通过重写 `AbstractTransactionalMessageCheckListener` 类来修改这个行为。
3. 事务消息将在 Broker 配置文件中的参数 transactionTimeout 这样的特定时间长度之后被检查。当发送事务消息时，用户还可以通过设置用户属性 CHECK_IMMUNITY_TIME_IN_SECONDS 来改变这个限制，该参数优先于 `transactionTimeout` 参数。
4. 事务性消息可能不止一次被检查或消费。
5. 提交给用户的目标主题消息可能会失败，目前这依日志的记录而定。它的高可用性通过 RocketMQ 本身的高可用性机制来保证，如果希望确保事务消息不丢失、并且事务完整性得到保证，建议使用同步的双重写入机制。
6. 事务消息的生产者 ID 不能与其他类型消息的生产者 ID 共享。与其他类型的消息不同，事务消息允许反向查询、MQ服务器能通过它们的生产者 ID 查询到消费者。



## 开发测试项目

### 项目目录结构

+ springboot-dubbo-consumer
+ springboot-dubbo-interface
+ springboot-dubbo-provider
+ springboot-rocketmq-consumer
+ springboot-rocketmq-producer



## 将vmware 的镜像 格式转为 virtualBox 的镜像格式

```
VBoxManage.exe clonehd  D:\vmware\virtual_marchine\banyu\banyu.vmdk  D:\VirtualBox\virtual_marchine\banyu\banyu.vdi  

VBoxManage.exe clonehd  D:\vmware\virtual_marchine\shanliang\banyu-cl1.vmdk  D:\VirtualBox\virtual_marchine\shanliang\shanliang.vdi  

VBoxManage.exe clonehd  D:\vmware\virtual_marchine\wine\banyu-cl1.vmdk  D:\VirtualBox\virtual_marchine\wine\wine.vdi  
```



| 虚拟机名称 | uuid                                 |
| ---------- | ------------------------------------ |
| banyu      | 0e917e81-e9c3-4018-939a-e53293c680d6 |
| shanliang  | c791ec26-982f-425b-a4dc-bbac0847e4d7 |
| wine       | d5a4f614-b882-40a2-997d-6338b45c4a3b |







## 碰到的问题

### 在虚拟机中显示屏幕过小

设置unbutu的分辨率  ：setting->devices->resolution



###  让xshell 连接虚拟机

[参考博客](https://blog.csdn.net/n950814abc/article/details/79512834)

补充点，最终通过xshell 连接是，连接的ip地址是下图选框中的内容

![](./images/Snipaste_2020-12-10_16-35-25.png)



### unbutu 虚拟机启动出现错误     开启虚拟机提示 无法连接到虚拟设备 sata 0:1

关闭虚拟机，点击硬盘，高级 在设置虚拟设备节点 中 选择 SCSI 0:1 硬盘(SCSI)，如下图所示

<img src="./images/Snipaste_2020-12-10_16-45-30.png" style="zoom:50%;" />



### 虚拟机中unbutu 无法上网

[unbutu无法上网解决方法](https://blog.csdn.net/qq_43043256/article/details/91416839?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.control&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.control)

[xshell 连接虚拟机中的unbutu](https://www.cnblogs.com/ims-my/p/12344196.html)



### unbutu  在虚拟机中 运行卡顿 

将虚拟机的运行内存改为2g即可。



### 通过xftp 传输文件时提示权限不足

用root登陆  unbutu ，然后给指定目录 赋予权限  例如  sudo chmod 777  xxx(文件夹名称或文件名)



### 开启了zookeeper 但是 通过status 查询不到（错误如：Error contacting service. It is probably not running.）

查看是否关闭了防火墙



### VMware Workstation 与 Device/Credential Guard 不兼容

[参考步骤](https://blog.csdn.net/csdn18740599042/article/details/94796315)

主要流程：

+ 关闭内核隔离

  这里一般默认就是关闭的

+ 关闭基于虚拟化的安全性

  [解决方法](https://blog.csdn.net/abrahamcui/article/details/109422297)

+ 检查 hyper-v 是否关闭成功

  这里需要使用 命令行来执行，手动好像会出错。

  + 开启： bcdedit /set hypervisorlaunchtype auto
  + 关闭： bcdedit /set hypervisorlaunchtype off

 [错误提示中的网页链接](https://kb.vmware.com/s/article/2146361) 

执行了如下的操作：

win+r  gpedit.msc =>  本地计算机策略  =>  计算机配置  =>   管理模板  =>  系统  =>  device guard =>  将其中两个选项由未配置 改为  禁用



### 配置了zookeeper 的环境变量但是执行zkServer.sh  start 时提示 command not found

执行  source  /etc/profile 



### 主机连接虚拟机中的zookeeper 集群

通过zooInspect 连接虚拟机中的集群的时候，发现连接不通。后面通过在本机也 安装 zookeeper  才跑通，但是有点没理解。



### shardingsphere 读取zookeeper 上的配置文件，然后进行读表操作。配置文件信息填写

#### springboot 中的配置文件信息填写

```
spring.shardingsphere.orchestration.name=health_ms
spring.shardingsphere.orchestration.overwrite=false
spring.shardingsphere.orchestration.registry.type=zookeeper
spring.shardingsphere.orchestration.registry.server-lists=192.168.175.128:2181
spring.shardingsphere.orchestration.registry.namespace=orchestration-health_ms

spring.shardingsphere.props.sql.show=true
```

#### zookeeper 中相关信息

+ 目录结构

![目录结构](./images/Snipaste_2020-12-16_12-29-20.png)

+ 对于其中的rule目录，需要进行修改。先前通过本地覆盖zookeeper 上的配置文件设置会让zookeeper 上生成对应的文件信息，但是其中包含冗余数据，如果单纯的修改会导致db数据信息读取失败。只需要保留如下信息即可。

```
dsslave1: !!org.apache.shardingsphere.orchestration.yaml.config.YamlDataSourceConfiguration
  dataSourceClassName: com.zaxxer.hikari.HikariDataSource
  properties:
    password: 'admin'
    driverClassName: com.mysql.jdbc.Driver
    jdbcUrl: jdbc:mysql://192.168.175.128:3306/dbslaver1
    username: root
```



### centeros 无法识别 unzip命令

```
// 执行如下命令 
yum install -y zip unzip
```



### rocketMQ启动出错，提示Send [3] times, still failed, cost [2136]ms, Topic: sync, BrokersSent: [brok

没有在 `2m-2s-sync` 文件夹下的四个配置文件中  添加 brokerIP1和brokerIP2的相关信息



### rocketMQ 运行提示SLAVE_NOT_AVAILABLE

`2m-2s-sync` 文件夹下的四个配置文件中   brokerIP1和brokerIP2 ip信息填写错误，好像写的是本机ip地址

[关于这两个ip的讲解](https://blog.csdn.net/jiajiren11/article/details/80528406)



###  rocketMQ 过滤消息报错  The broker does not support consumer to filter message by SQL92

需要修改`conf`文件夹下的`broker.conf`文件，添加`enablePropertyFilter`属性，并设置为true即可。注意，这里要进行重启

<font color=#ff0000> 如果是集群则在对应的broker.properties中添加该属性</font>



### virtualbox 中运行 vmware 镜像，本机无法ping通虚拟机，虚拟机可以ping通主机且能上网

[参考链接](https://www.cnblogs.com/chenhaoqiang/p/9491902.html)

+ virtualBox 的四种网络连接方式

|                   | NAT  | Bridged Adapter | Internal       | Host-only Adapter |
| ----------------- | ---- | --------------- | -------------- | ----------------- |
| 虚拟机 =》 主机   | √    | √               | ×              | 默认不能需设置    |
| 主机=》虚拟机     | ×    | √               | ×              | 默认不能需设置    |
| 虚拟机=》其他主机 | √    | √               | ×              | 默认不能需设置    |
| 其他主机=》虚拟机 | ×    | √               | ×              | 默认不能需设置    |
| 虚拟机之间        | ×    | √               | 同网络名下可以 | √                 |

<font color=#ff0000>在vmware中我是通过网络地址转换(NAT)实现的，但在virtualbox中却出现了问题。在这种情况下通过`ip addr`命令可以看到虚拟机自动分配的ip地址是`10.0.2.15`。</font>



# virtualbox中安装虚拟机

## 流程

+ 创建虚拟机镜像 参考[virtualBox中安装centeros教程](https://blog.csdn.net/s_lisheng/article/details/77850179)
+ 查看主机的ip地址

```
// 通过ipconfig/all 查看主机ip地址相关信息，一般会有多个
ipconfig/all
//  如果是virtualBox 则查看  VirtualBox Host-Only Ethernet Adapter  对应的ip地址和子网掩码
```



+ 账户信息

| 虚拟机名称 |    ip地址     | 账号 |  密码  |
| :--------: | :-----------: | :--: | :----: |
|   banyu    | 192.168.56.10 | root | renhao |
| shanliang  | 192.168.56.11 | root | renhao |
|   yichen   | 192.168.56.12 | root | renhao |

+ [虚拟机可以上网且主机能ping通虚拟机](https://www.cnblogs.com/chenhaoqiang/p/9491902.html)



# nginx 安装步骤

## 前提

### 安装nginx 前首先要确认系统中安装了如下内容：

+ gcc
+ pcre-devel
+ zlib-devel
+ openssl-devel

### 安装命令如下

```
yum -y install gcc pcre-devel zlib-devel openssl openssl-devel
```

### 查看是否成功

+ rpm包安装的，可以用 rpm -qa 看到，如果要查找某软件包是否安装，用 **rpm -qa | grep "软件或者包的名字"**

+ 以deb包安装的，可以用 dpkg -l 看到。如果是查找指定软件包，用 **dpkg -l | grep "软件或者包的名字"**

+ yum方法安装的，可以用 yum list installed 查找，如果是查找指定包，用 **yum list installed | grep "软件名或者包名"**

## 安装

[参考教程](https://www.cnblogs.com/xxoome/p/5866475.html)

+ 在 https://nginx.org/  下载  nginx
+ 将该压缩包放到  /usr/local/software   目录下
+ 解压 下载的压缩包   tar -zxvf    nginx-1.9.9.tar.gz（文件名）
+ 进入该目录 cd nginx-1.9.9.tar.gz
+ 配置安装路径   ./configure --prefix=/usr/local/software/nginx
+ make   、make install   
+ 测试是否成功   

```
cd /usr/local/software/nginx/sbin
./nginx -t
```

+ 如果成功则正式开启

```
./nginx
```

+ 快速关闭

```
nginx -s stop
```

+ 完整有序停止

```
nginx -s quit
```

+ 重启

```
nginx -s reload
```

+ 查看版本

```
nginx -v
```



+ 快速启动，设置 path

```
vi /etc/profile
export NGINX_HOME=/usr/local/software/nginx
export PATH=$PATH:$NGINX_HOME/sbin
source /etc/profile
```

## 反向代理实验

+ 将 tomcat 加入 path

```
vi /etc/profile
export TOMCAT_HOME=/usr/local/software/apache-tomcat-9.0.41
export PATH=$PATH:$TOMCAT_HOME/bin
source /etc/profile
```

+ 开启 tomcat

```
startup.sh
```

+ 关闭 tomcat

```
shutdown.sh
```



# 开发问题记录

## mybatis dao 接口和 mapper.xml 无法匹配

错误内容如下

```
org.apache.ibatis.binding.BindingException: Invalid bound statement (not found):
```

解决方法有两种：

1、通过  `@MapperScan("com.yichen.useall.dao")` 指定  dao 接口位置，通过`mybatis.mapper-locations=classpath:com/yichen/useall/dao/*.xml` 指定对应的  xml 文件存放位置

2、通过  `@MapperScan("com.yichen.useall.dao")` 指定  dao 接口位置，同时创建对应的xml文件，<font color=red>这里对xml的放置位置以及文件名称有要求：文件位置需要同dao接口位置一致，只不过是放置在类路径下，文件名同dao接口一致，不过文件后缀有dao  的  `.java` 改为  `.xml`</font>



## spring cloud gateway 报错   chunkedTransfer

### 报错具体内容

```
java.lang.NoSuchMethodError: reactor.netty.http.client.HttpClient.chunkedTransfer(Z)Lreactor/netty/http/client/HttpClient;
```

### 解决

> 由于 springboot 和 springcloud 版本不一致，本机均设为了  2.2.2.RELEASE





# linux 命令

##  /目录下的文件夹介绍

![/目录下的文件夹](./images/v2-87fc82d4d9d3df7829ac3bbe6de9e77c_1440w.jpg)

+ bin(binaries) 存放二进制可执行文件
+ sbin(super user binaries) 存放二进制可执行文件，只有root才能访问
+ etc(etcetera)  存放系统配置文件
+ usr(unix shared resources) 用于存放共享的系统资源
+ home 存放用户文件的根目录
+ root  超级用户目录
+ dev(devices) 用于存放设备文件
+ lib(library)  存放跟文件系统中的程序运行所需要的共享库以及内核模块
+ mnt(mount) 系统管理员安装临时文件系统的安装点
+ boot  存放用于系统引导时使用的各种文件
+ tmp(temporary)  用于存放各种临时文件
+ var(variable)  用于存放运行时需要改变数据的文件



## 页面切换命令

#### 全屏导航

+ ctrl + F 向前移动一屏
+ ctrl + B 向后移动一屏
+ ctrl + D 向前移动半屏
+ ctrl + U 向后移动半屏





## 便捷操作

+ 快速清空当前输入的命令      ctrl + c



## 使用到的命令

#### 根据关键字过滤并且指定查看宽度

+ 查看关键字上的几行   cat web.log | grep -B 4 one
+ 查看关键字下的几行   cat web.log | grep -A 4 one
+ 查看关键字前几行和后几行  cat web.log | grep -C one



### vi下 关键字查询

按下`/` ，然后输入关键字，最后按下回车即可，之后按 `n`  下一个，按`N` 上一个



## vim 常用命令

> yy      // 复制当前光标所在行
>
> p   //   粘贴到下一行，原来的往下顺移



##  系统命令

### 切换用户 

+ su -         切换到root

### 查看系统内核  

+ uname -a

### 查询系统版本

lsb_release -a

### 查看linux 操作系统位数

getcong LONG_BIT

### 查看linux  磁盘空间占用情况

df -h

### top命令 介绍

[参考文章](https://blog.csdn.net/dxl342/article/details/53507673)

#### 第一行

> 10:01:23 — 当前系统时间
> 126 days, 14:29 — 系统已经运行了126天14小时29分钟（在这期间没有重启过）
> 2 users — 当前有2个用户登录系统
> load average: 1.15, 1.42, 1.44 — load average后面的三个数分别是1分钟、5分钟、15分钟的负载情况。
>
> <font color=red>load average数据是每隔5秒钟检查一次活跃的进程数，然后按特定算法计算出的数值。如果这个数除以逻辑CPU的数量，结果高于5的时候就表明系统在超负荷运转了。</font>

#### 第二行

> Tasks — 任务（进程），系统现在共有183个进程，其中处于运行中的有1个，182个在休眠（sleep），stoped状态的有0个，zombie状态（僵尸）的有0个。

#### 第三行

> 6.7% us — 用户空间占用CPU的百分比。  user
> 0.4% sy — 内核空间占用CPU的百分比。    system
> 0.0% ni — 改变过优先级的进程占用CPU的百分比   
> 92.9% id — 空闲CPU百分比     idle
> 0.0% wa — IO等待占用CPU的百分比   wait
> 0.0% hi — 硬中断（Hardware IRQ）占用CPU的百分比
> 0.0% si — 软中断（Software Interrupts）占用CPU的百分比

#### 第四行

> 8306544k total — 物理内存总量（8GB）
> 7775876k used — 使用中的内存总量（7.7GB）
> 530668k free — 空闲内存总量（530M）
> 79236k buffers — 缓存的内存量 （79M）

#### 第五行

> 2031608k total — 交换区总量（2GB）
> 2556k used — 使用的交换区总量（2.5M）
> 2029052k free — 空闲交换区总量（2GB）
> 4231276k cached — 缓冲的交换区总量（4GB）

#### 第七行

> PID — 进程id
> USER — 进程所有者
> PR — 进程优先级
> NI — nice值。负值表示高优先级，正值表示低优先级
> VIRT — 进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
> RES — 进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
> SHR — 共享内存大小，单位kb
> S — 进程状态。D=不可中断的睡眠状态 R=运行 S=睡眠 T=跟踪/停止 Z=僵尸进程
> %CPU — 上次更新到现在的CPU时间占用百分比
> %MEM — 进程使用的物理内存百分比
> TIME+ — 进程使用的CPU时间总计，单位1/100秒
> COMMAND — 进程名称（命令名/命令行）

### htop

[入门操作](https://linux.cn/article-3141-1.html)





### 缩小 vim 窗口

+ ​    :sh    缩小当前vim  窗口
+ ctrl+d  返回vim窗口



###  !pwd

退出vim窗口   执行pwd命令



### crul

[学习链接](https://www.ruanyifeng.com/blog/2019/09/curl-reference.html)

#### 参数介绍

> -A      // 用于设置   指定客户端的用户代理标头    即 User-Agent用于发送 POST 请求的数据体。
>
> curl -A "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36"  http://127.0.0.1:8080/getResult

> -b    // 用于设置向服务端发送的cookie
>
> curl -b 'name=yichen'  http://127.0.0.1:8080/getResult

> -c  将服务器设置的 Cookie 写入一个文件
>
> curl -c /test/cookie.txt http:/127.0.0.1:8080/getResult

> -d 用于发送 POST 请求的数据体
>
> curl -d 'name=yichen' http://127.0.0.1:8080/post     // -d 会自动将请求转为 post方式
>
> curl -d '@data.txt'  http://127.0.0.1:8080/post     // 读取文件中的信息

> -k  跳过 SSL 检测。
>
> curl -k http://www.baidu.com







## 在linux 上部署常用命令

+ jps -l  查看java进程
+ pwdx 2772  查看指定进程id 的执行目录



## 基础性知识

### - 和  -- 的区别

+ `-`  代表简写  字符形式
+ `--` 代表全拼   单词形式

### 简写解释

<font color=#ff0000> 当不理解简写代表的意思，可以通过 `命令+ -- help`来查询。例如 `tar --help`即会罗列出该命令下所有的参数以及对应的含义</font>

+ f    =》   force、file    强制
+ r   =》 recursively   递归执行
+ d  =》 directory   文件夹
+ v   =》   verbose   显示执行细节
+ i   =》 information   执行前通知

### 默认颜色代表的含义

+ 蓝色  =》  目录
+ 绿色  =》 可执行文件
+ 红色  =》  压缩文件
+ 浅蓝  =》  链接文件
+ 白色  =》 其他文件
+ 黄色  =》 设备文件，包括block、char、fifo

### 单双引号的区别

> 单引号与双引号的最大不同在于双引号仍然可以保有变量的内容，但单引号内仅能是
> 一般字符 ，而不会有特殊符号。
>
> 例子
>
> [root@linux ~]# name=VBird 
> [root@linux ~]# echo $name 
> VBird 
> [root@linux ~]# myname="$name its me" 
> [root@linux ~]# echo $myname 
> VBird its me 
> [root@linux ~]# myname='$name its me' 
> [root@linux ~]# echo $myname 
> $name its me 

# window相关问题记录

## window 查看端口以及对应的进程名称

+ netstat -ano  // 展示端口列表

+ netstat -ano | findstr "3306"   // 查找指定端口对应的pid，这里是找3306的端口
+ tasklist | findstr "上一步最后一列查询出来的  pid "

# git 常用命令

## 从github 上下载内容

```java
git clone 地址
```



## 本地文件上传到github

```
// 初始化 本地仓库
git init

// 将文件添加到暂存区
git add 文件名

// 查看暂存区中的文件信息，包含已提交，未提交
git ls-files --stage

// 查看暂存区（即本地仓库）内的文件信息
git status 

// 删除暂存区中的文件   只能是文件，文件夹不行。
git rm --cache 文件名

// 提交
git commit -m "说明注释"

// 连接远程仓库
git remote add origin 你的仓库地址

// 上传至远程仓库
git push -u origin master

// 创建分支  develop
git checkout -b develop master(被复制分支)    -b是指复制最后一个分支作为基础

// 切换分支
git checkout master

// 删除远程分支
git push origin -d master(分支名)

// 删除本地分支 非强制
git branch -d master(本地分支名称)

// 删除本地分支，强制删除
git branch -D master(本地分支名称)

// 分支进行合并
1、切换到需要合并的分支
git checkout master(需要合并的分支名称)
2、将被合并的分支合并
git merge --no-ff develop(被合并的分支名)  默认是fast-farword merge 会将master分支直接指向develop分支，而--no-ff 则是进行复制 并放在master分支上的新节点上。
3、将合并后的分支提交
git push master(需要合并的分支名称)
3.1 如果远程没有改分支，需要通过 --set-upstream 进行创建
git push --set-upstream master(需要合并的分支名称)


// 查看书对象存储内容
git cat-file -p b5erb5c(SHA-1值，取前七位)

// 查看提交历史
git log

选项     说明
%H    提交对象（commit）的完整哈希字串
%h    提交对象的简短哈希字串
%T    树对象（tree）的完整哈希字串
%t    树对象的简短哈希字串
%P    父对象（parent）的完整哈希字串
%p    父对象的简短哈希字串
%an    作者（author）的名字
%ae    作者的电子邮件地址
%ad    作者修订日期（可以用 -date= 选项定制格式）
%ar    作者修订日期，按多久以前的方式显示
%cn    提交者(committer)的名字
%ce    提交者的电子邮件地址
%cd    提交日期
%cr    提交日期，按多久以前的方式显示
%s    提交说明

```

## 用到的命令

+ 查看本地分支  

> git branch

+ 查看 本地和远程的所有分支

> git branch -a

+ 只查看远程的所有分支

> git branch -r

+ 拉取远程仓库  覆盖本地仓库

> git fetch --all
>
> git reset  --hard  origin/master
>
> git pull

##  合并分支  git merge

[参考链接](https://www.jianshu.com/p/ff1877c5864e)



## 版本回退

[版本回退](https://yijiebuyi.com/blog/8f985d539566d0bf3b804df6be4e0c90.html)

[index理解](https://www.jianshu.com/p/6bb76450d763)

+ --mixed  会保留源码,只是将git commit和index 信息回退到了某个版本.

  > git reset --mixed 版本号

+ --soft  保留源码,只回退到commit 信息到某个版本.不涉及index的回退,如果还需要提交,直接commit即可

  > git reset --soft 版本号

+ --hard  源码也会回退到某个版本,commit和index 都回回退到某个版本.(注意,这种方式是改变本地代码仓库源码)

  > git reset --hard 版本号

<font color=red>此处所说的index是指对暂存区文件的版本标识，如果index回退，即`--mixed`，此时即使有新文件，它也无法识别。因为旧的   index 中不存在</font>

## 能快速访问github.com 以及 加速下载

> 在 hosts中配置 github.com  的ip 映射关系   
>
>  //  window 下 hosts所在位置  C:\Windows\System32\drivers\etc
>
> // 添加内容如下
>
> 140.82.114.4 github.com
> 199.232.69.194 github.global.ssl.fastly.net
> 185.199.108.153 assets-cdn.github.com
> 151.101.88.249       github.global.ssl.fastly.net
> 151.101.73.194       github.global.ssl.fastly.net
> 151.101.229.194       github.global.ssl.fastly.net
> 151.101.184.133    assets-cdn.github.com
> 151.101.184.133    raw.githubusercontent.com
> 151.101.184.133    gist.githubusercontent.com
> 151.101.184.133    cloud.githubusercontent.com
> 151.101.184.133    camo.githubusercontent.com
> 151.101.184.133    avatars0.githubusercontent.com
> 151.101.184.133    avatars1.githubusercontent.com
> 151.101.184.133    avatars2.githubusercontent.com
> 151.101.184.133    avatars3.githubusercontent.com
> 151.101.184.133    avatars4.githubusercontent.com
> 151.101.184.133    avatars5.githubusercontent.com
> 151.101.184.133    avatars6.githubusercontent.com
> 151.101.184.133    avatars7.githubusercontent.com
> 151.101.184.133    avatars8.githubusercontent.com
>
> 



## 优化

### git push之后需要输入账号密码

```
// 执行如下文件会生成 一个  .git-crredentials 文件，里面记录了用户名，密码，git地址等信息
git config --global credential.helper store
```



## 问题

### 删除是 因为文件或者路径是中文找不到

```
git config --global core.quotepath false
```





# dubbo 源码学习

## 搭建源码环境

+ 访问[官方仓库](https://github.com/apache/dubbo)，克隆到本地

> git clone git@github.com:apache/dubbo.git

+ 切换分支，学习的是`2.7.7`

> git checkout -b dubbo-2.7.7 dubbo-2.7.7

+ 用mvn 命令进行编译

> mvn clean install -Dmaven.test.skip=true 

+ 转成idea 项目

> mvn idea:idea   // 要是执行报错，就执行这个 mvn idea:workspace 



## URL

在互联网领域，每个信息资源都有统一的且在网上唯一的地址，该地址就叫URL(uniform resource locator，统一资源定位符)，它是互联网的统一资源定位标志，也就是指网络地址。

URL 本质上就是一个特殊格式的字符串。一个标准的URL格式可以包含如下的几个部分。

> protocol://username:password@host:port/path?key=value&key=value

- **protocol**：URL 的协议。我们常见的就是 HTTP 协议和 HTTPS 协议，当然，还有其他协议，如 FTP 协议、SMTP 协议等。
- **username/password**：用户名/密码。 HTTP Basic Authentication 中多会使用在 URL 的协议之后直接携带用户名和密码的方式。
- **host/port**：主机/端口。在实践中一般会使用域名，而不是使用具体的 host 和 port。
- **path**：请求的路径。
- **parameters**：参数键值对。一般在 GET 请求中会将参数放到 URL 中，POST 请求会将参数放到请求体中。

URL 是整个 Dubbo 中非常基础，也是非常核心的一个组件，阅读源码的过程中你会发现很多方法都是以 URL 作为参数的，在方法内部解析传入的 URL 得到有用的参数，所以有人将 URL 称为**Dubbo 的配置总线**。



## @spi 和  @adaptive

### @spi 修饰接口，表示该接口是一个扩展接口

### @adaptive  修饰方法，且@spi修饰 接口。 会生成适配器类，其中会根据URL 中的codec 值确定具体的扩展实现类。



## dubbo SPI

Dubbo 为了更好地达到 OCP 原则（即“对扩展开放，对修改封闭”的原则），采用了“**微内核+插件**”的架构。那什么是微内核架构呢？微内核架构也被称为插件化架构（Plug-in Architecture），这是一种面向功能进行拆分的可扩展性架构。内核功能是比较稳定的，只负责管理插件的生命周期，不会因为系统功能的扩展而不断进行修改。功能上的扩展全部封装到插件之中，插件模块是独立存在的模块，包含特定的功能，能拓展内核系统的功能。

<font color=red>微内核架构中，内核通常采用 Factory、IoC、OSGi 等方式管理插件生命周期，**Dubbo 最终决定采用 SPI 机制来加载插件**。</font>

### jdk SPI 机制

当服务的提供者提供了一种接口的实现之后，需要在 Classpath 下的 META-INF/services/ 目录里创建一个以服务接口命名的文件，此文件记录了该 jar 包提供的服务接口的具体实现类。当某个应用引入了该 jar 包且需要使用该服务时，JDK SPI 机制就可以通过查找这个 jar 包的 META-INF/services/ 中的配置文件来获得具体的实现类名，进行实现类的加载和实例化，最终使用该实现类完成业务功能。

### JDK SPI 在 JDBC 中的应用

#### 前提在 pom 中导入 mysql 驱动依赖

```
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
</dependency>
```

#### 具体流程

+ 通过语句创建数据库连接

```
Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","123");
            System.out.println("连接数据库成功");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user");
            int rowCount=0 ;
            while(resultSet.next()){
                rowCount++;
            }
            System.out.println(rowCount);
```

+ 首先，通过class.forName() 方法加载类。是通过  装载当前类的类装载器来装载指定的类

```
//  com.mysql.cj.jdbc.Driver 中主要的代码为如下的静态代码块，它的作用是往调用  DriverManager 中注册驱动
static {
        try {
            java.sql.DriverManager.registerDriver(new Driver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }
    
//  registerDriver()方法 最终是往  静态成员变量 registeredDrivers 中写入
public static synchronized void registerDriver(java.sql.Driver driver)
        throws SQLException {

        registerDriver(driver, null);
    }
```

+ 之后 `DriverManager.getConnection` 来获取连接，其中就用到了这里的    `registeredDrivers` 

```
public static Connection getConnection(String url,String user, String password) throws SQLException{
   保存 url,pwd 内容省略了...
   return (getConnection(url, info, Reflection.getCallerClass()));
}
 
private static Connection getConnection(
String url, java.util.Properties info, Class<?> caller) throws SQLException {
...
for(DriverInfo aDriver : registeredDrivers) {
    // If the caller does not have permission to load the driver then
    // skip it.
    if(isDriverAllowed(aDriver.driver, callerCL)) {
        try {
            println("    trying " + aDriver.driver.getClass().getName());
            Connection con = aDriver.driver.connect(url, info);
            if (con != null) {
                // Success!
                println("getConnection returning " + aDriver.driver.getClass().getName());
                return (con);
                }
            } catch (SQLException ex) {
                if (reason == null) {
                reason = ex;
            }
    	}

    } else {
    	println("    skipping: " + aDriver.getClass().getName());
    }

}
...
```



#### 期间遇到的问题

+ `java.sql.SQLException: The server time zone value '�й���׼ʱ��' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the serverTimezone configuration property) to use a more specifc time zone value if you want to utilize time zone support.`

```
// 解决方法
  set global time_zone = '+8:00';　 ##修改mysql全局时区为北京时间，即我们所在的东8区
  set time_zone = '+8:00'; 　##修改当前会话时区
  flush privileges; 　 ##立即生效
```

### jdk spi 存在的问题

JDK SPI 在查找扩展实现类的过程中，需要遍历 SPI 配置文件中定义的所有实现类，该过程中会将这些实现类全部实例化。这会在只想实例化其中一个的时候造成资源浪费

### dubbo 对jdk spi 的优化

#### Dubbo 按照 SPI 配置文件的用途，将其分成了三类目录。

- META-INF/services/ 目录：该目录下的 SPI 配置文件用来兼容 JDK SPI 。
- META-INF/dubbo/ 目录：该目录用于存放用户自定义 SPI 配置文件。
- META-INF/dubbo/internal/ 目录：该目录用于存放 Dubbo 内部使用的 SPI 配置文件。

####  Dubbo 将 SPI 配置文件改成了 **KV 格式**

```
dubbo=org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol
```

其中 key 被称为<font color=red>扩展名（也就是 ExtensionName）</font>，当我们在为一个接口查找具体实现类时，可以指定扩展名来选择相应的扩展实现。例如，这里指定扩展名为 dubbo，Dubbo SPI 就知道我们要使用：org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol 这个扩展实现类，只实例化这一个扩展实现即可，无须实例化 SPI 配置文件中的其他扩展实现类。

使用 KV 格式的 SPI 配置文件的另一个好处是：让我们更容易定位到问题。假设我们使用的一个扩展实现类所在的 jar 包没有引入到项目中，那么 Dubbo SPI 在抛出异常的时候，会携带该扩展名信息，而不是简单地提示扩展实现类无法加载。这些更加准确的异常信息降低了排查问题的难度，提高了排查问题的效率。



## push 和 pull 区别

push 为服务器主动推送

pull 为客户端 主动获取消息



## 本地缓存  zookeeper 减压

### 接口介绍

> 接口所在包的位置  dubbo-register.dubbo-register-api

#### Node 接口

在 Dubbo 中，一般使用 Node 这个接口来抽象节点的概念。Node不仅可以表示 Provider 和 Consumer 节点，还可以表示注册中心节点。Node 接口中定义了三个非常基础的方法：

+ getUrl() 方法返回表示当前节点的 URL；

+ isAvailable() 检测当前节点是否可用；

+ destroy() 方法负责销毁当前节点并释放底层资源。

#### RegistryService

RegistryService 接口抽象了注册服务的基本行为

+ register() 方法和 unregister() 方法分别表示注册和取消注册一个 URL。

+ subscribe() 方法和 unsubscribe() 方法分别表示订阅和取消订阅一个 URL。订阅成功之后，当订阅的数据发生变化时，注册中心会主动通知第二个参数指定的 NotifyListener 对象，NotifyListener 接口中定义的 notify() 方法就是用来接收该通知的。

+ lookup() 方法能够查询符合条件的注册数据，它与 subscribe() 方法有一定的区别，**subscribe() 方法采用的是 push 模式，lookup() 方法采用的是 pull 模式。**

#### Register

Registry 接口继承了 RegistryService 接口和 Node 接口。它表示的就是一个拥有注册中心能力的节点，其中的 reExportRegister() 和 reExportUnregister() 方法都是委托给 RegistryService 中的相应方法。

#### AbstractRegistry

AbstractRegistry 实现了 Registry 接口，虽然 AbstractRegistry 本身在内存中实现了注册数据的读写功能，也没有什么抽象方法，但它依然被标记成了抽象类。Registry 接口的所有实现类都继承了 AbstractRegistry。

为了减轻注册中心组件的压力，AbstractRegistry 会把当前节点订阅的 URL 信息缓存到本地的 Properties 文件中，其核心字段如下：

+ registryUrl（URL类型）。 该 URL 包含了创建该 Registry 对象的全部配置信息，是 AbstractRegistryFactory 修改后的产物。

+ properties（Properties 类型）、file（File 类型）。 本地的 Properties 文件缓存，properties 是加载到内存的 Properties 对象，file 是磁盘上对应的文件，两者的数据是同步的。在 AbstractRegistry 初始化时，会根据 registryUrl 中的 file.cache 参数值决定是否开启文件缓存。如果开启文件缓存功能，就会立即将 file 文件中的 KV 缓存加载到 properties 字段中。当 properties 中的注册数据发生变化时，会写入本地的 file 文件进行同步。properties 是一个 KV 结构，其中 Key 是当前节点作为 Consumer 的一个 URL，Value 是对应的 Provider 列表，包含了所有 Category（例如，providers、routes、configurators 等） 下的 URL。properties 中有一个特殊的 Key 值为 registies，对应的 Value 是注册中心列表，其他记录的都是 Provider 列表。

+ syncSaveFile（boolean 类型）。 是否同步保存文件的配置，对应的是 registryUrl 中的 save.file 参数。

+ registryCacheExecutor（ExecutorService 类型）。 这是一个单线程的线程池，在一个 Provider 的注册数据发生变化的时候，会将该 Provider 的全量数据同步到 properties 字段和缓存文件中，如果 syncSaveFile 配置为 false，就由该线程池异步完成文件写入。

+ lastCacheChanged（AtomicLong 类型）。 注册数据的版本号，每次写入 file 文件时，都是全覆盖写入，而不是修改文件，所以需要版本控制，防止旧数据覆盖新数据。

+ registered（Set 类型）。 这个比较简单，它是注册的 URL 集合。

+ subscribed（ConcurrentMap<URL, Set> 类型）。 表示订阅 URL 的监听器集合，其中 Key 是被监听的 URL， Value 是相应的监听器集合。<font color=red>这里是作为消费者，订阅生产者并绑定监听器</font>

+ notified（ConcurrentMap<URL, Map<String, List>>类型）。 该集合第一层 Key 是当前节点作为 Consumer 的一个 URL，表示的是该节点的某个 Consumer 角色（一个节点可以同时消费多个 Provider 节点）；Value 是一个 Map 集合，该 Map 集合的 Key 是 Provider URL 的分类（Category），例如 providers、routes、configurators 等，Value 就是相应分类下的 URL 集合。<font color=red>这里是作为注册中心、消费者对生产者一对多的关系</font>

##### 本地缓存  notify方法

两个细节：

+ UrlUtils.isMatch() 方法。该方法会完成 Consumer URL 与 Provider URL 的匹配，依次匹配的部分如下所示：

匹配 Consumer 和 Provider 的接口（优先取 interface 参数，其次再取 path）。双方接口相同或者其中一方为“*”，则匹配成功，执行下一步。

匹配 Consumer 和 Provider 的 category。

检测 Consumer URL 和 Provider URL 中的 enable 参数是否符合条件。

检测 Consumer 和 Provider 端的 group、version 以及 classifier 是否符合条件。

+ URL.getServiceKey() 方法。该方法返回的 ServiceKey 是 properties 集合以及相应缓存文件中的 Key

**AbstractRegistry 的核心是本地文件缓存的功能。** 在 AbstractRegistry 的构造方法中，会调用 loadProperties() 方法将上面写入的本地缓存文件，加载到 properties 对象中。

在网络抖动等原因而导致订阅失败时，Consumer 端的 Registry 就可以调用 getCacheUrls() 方法获取本地缓存，从而得到最近注册的 Provider URL。可见，AbstractRegistry 通过本地缓存提供了一种容错机制，保证了服务的可靠性。

##### 注册/订阅

AbstractRegistry 实现了 Registry 接口，它实现的 registry() 方法会将当前节点要注册的 URL 缓存到 registered 集合，而 unregistry() 方法会从 registered 集合删除指定的 URL，例如当前节点下线的时候。

subscribe() 方法会将当前节点作为 Consumer 的 URL 以及相关的 NotifyListener 记录到 subscribed 集合，unsubscribe() 方法会将当前节点的 URL 以及关联的 NotifyListener 从 subscribed 集合删除。

##### 恢复/销毁

AbstractRegistry 中还有另外两个需要关注的方法：recover() 方法和destroy() 方法。

在 Provider 因为网络问题与注册中心断开连接之后，会进行重连，重新连接成功之后，会调用 recover() 方法将 registered 集合中的全部 URL 重新走一遍 register() 方法，恢复注册数据。同样，recover() 方法也会将 subscribed 集合中的 URL 重新走一遍 subscribe() 方法，恢复订阅监听器。

在当前节点下线的时候，会调用 Node.destroy() 方法释放底层资源。AbstractRegistry 实现的 destroy() 方法会调用 unregister() 方法和 unsubscribe() 方法将当前节点注册的 URL 以及订阅的监听全部清理掉，其中不会清理非动态注册的 URL（即 dynamic 参数明确指定为 false）。





# zookeeper 理解

## leader、follower、observer

### 三者介绍

+ leader 处理写请求，follower和observer接收到写请求时会转发给leader
+ follower 处理读请求， 会参与投票
+ observer 处理读请求，<font color=red>不会参与 投票，但是会接收投票信息</font>

> 增加observer的原因。如果client 的请求数增加，会迫使server（即最初的 leader和follower） 的数量增加，server数量的增加会产生一个问题，修改znode时投票结果统计耗时增加，即会因为投票过程产生一个性能瓶颈。因为leader 只能在超过半数同意的情况下才能更改生效。因此，才想出了observer，它几乎同follower一样，只是缺少了投票的功能。
>
> [参考链接](https://www.cnblogs.com/liujunjun/p/12427245.html)

<font color=red>没了投票功能为什么还能提供读服务呢</font>

> 因为虽然observer没有投票功能，但是leader接收到的写请求会通过广播方式 将 proposal（提案） 发送出去，follower和observer均在其中，所以observer也会进行的修改或者删除操作

<font color=red>只要超过半数就能执行，那么如果读请求被指向还没有执行成功的会怎么样</font>

> 会读取到旧数据，zookeeper没有保证强一直性。即读取到旧的数据这种情况的确是存在的，如果想读取最新数据，可以通过sync方法。sync是使得client当前连接着的zookeeper 服务器和该服务器的leader进行数据同步，以获得最新数据。
>
> [参考链接](http://www.crazyant.net/2120.html)

<font color=orange>是否存在一种情况，即半数选举成功后，未成功的执行事务失败</font>



# redis

## redis 运行变慢，性能排查

### 思维导图

![思维导图](./images/2021-02-05-1.png)

### 参考文章

[参考文章](https://mp.weixin.qq.com/s/Cvq51psUQaNFThL0HJy11g)



# 代理

## 代理模式

### 静态代理

#### 流程

<img src="./images/CgqCHl8_hxqAY6vaAAGcUb0A8A4971.png" alt="代理模式类图" style="zoom:67%;" />

图中的 Subject 是程序中的**业务逻辑接口**，RealSubject 是实现了 Subject 接口的**真正业务类**，Proxy 是实现了 Subject 接口的代理类，封装了一个 RealSubject 引用。在程序中不会直接调用 RealSubject 对象的方法，而是使用 Proxy 对象实现相关功能。

Proxy.operation() 方法的实现会调用其中封装的 RealSubject 对象的 operation() 方法，执行真正的业务逻辑。代理的作用不仅仅是正常地完成业务逻辑，还会在业务逻辑前后添加一些代理逻辑，也就是说，Proxy.operation() 方法会在 RealSubject.operation() 方法调用前后进行一些预处理以及一些后置处理。这就是我们常说的“**代理模式**”。

使用**代理模式可以控制程序对 RealSubject 对象的访问**，如果发现异常的访问，可以直接限流或是返回，也可以在执行业务处理的前后进行相关的预处理和后置处理，帮助上层调用方屏蔽底层的细节。例如，在 RPC 框架中，代理可以完成序列化、网络 I/O 操作、负载均衡、故障恢复以及服务发现等一系列操作，而上层调用方只感知到了一次本地调用。

**代理模式还可以用于实现延迟加载的功能**。我们知道查询数据库是一个耗时的操作，而有些时候查询到的数据也并没有真正被程序使用。延迟加载功能就可以有效地避免这种浪费，系统访问数据库时，首先可以得到一个代理对象，此时并没有执行任何数据库查询操作，代理对象中自然也没有真正的数据；当系统真正需要使用数据时，再调用代理对象完成数据库查询并返回数据。常见 ORM 框架（例如，MyBatis、 Hibernate）中的延迟加载的原理大致也是如此。

另外，代理对象可以协调真正RealSubject 对象与调用者之间的关系，在一定程度上实现了解耦的效果。

#### 缺陷

因为在编译阶段就要为每个RealSubject 类创建一个 Proxy 类，当需要代理的类很多时，就会出现大量的 Proxy 类。

## jdk动态代理

缺点：它只能基于接口进行代理，对于没有继承任何接口的类，JDK 动态代理就没有用武之地了。

## cglib 动态代理

**CGLib（Code Generation Library）是一个基于 ASM 的字节码生成库**，它允许我们在运行时对字节码进行修改和动态生成。CGLib 采用字节码技术实现动态代理功能，其底层原理是通过字节码技术为目标类生成一个子类，并在该子类中采用方法拦截的方式拦截所有父类方法的调用，从而实现代理的功能。

因为 CGLib 使用生成子类的方式实现动态代理，所以无法代理 final 关键字修饰的方法（因为final 方法是不能够被重写的）。

### 两个重要成员

+ Enhancer：指定要代理的目标对象以及实际处理代理逻辑的对象，最终通过调用 create() 方法得到代理对象，对这个对象所有的非 final 方法的调用都会转发给 MethodInterceptor 进行处理。

+ MethodInterceptor：动态代理对象的方法调用都会转发到intercept方法进行增强。



## Javassist

Javassist 是一个开源的生成 Java 字节码的类库，其主要优点在于简单、快速，直接使用Javassist 提供的 Java API 就能动态修改类的结构，或是动态生成类。







# idea 相关 问题

##  快捷键

ctrl+alt+T      生成try catch
ctrl+alt+L    对其
ctrl+alt+B  接口的实现类
ctrl+alt+v 自动补全变量名和变量属性
ctrl+o  重写方法
ctrl+n   查找类
ctrl+g  跳转到指定行
ctrl+f9 重新编译
ctrl+h 显示依赖关系
alt+7 显示当前类下的架构图，即有哪些成员方法和变量
alt+f7  查看当前方法被哪些类引用
shift+alt+鼠标左键点击选取   同步填充 光标信息

折叠代码块：
1、所有   ctrl+shift+  -
2、选中内容  ctrl+  -

展开代码块
1、所有  ctrl+shift+=
2、选中内容   ctrl+  =

## 便捷方法

+ 打印有关  sql 的执行语句  
  logging.level.dao对应的位置=debug
  例子：   logging.level.com.tianyilan.shardingsphere.demo.repository=debug

+ 将项目与  github  隔离

选中项目   点击 file 》setting 》 version control 》选中目录 ，右侧插掉即可

+ 项目中隐藏  `.idea` 文件 
  + 打开file--》settings--》editor---->file types
  + 选择 `*.gitignore`    在底下已有的很多类文件中追加   `.idea;`
+ <font color=red>查看  class 文件的字节码内容</font>

> 通过 `javac`命令 编译指定的  	`.java`文件，例如  `javac hello.java`  此时会在同一目录下生成对应的`.class`文件，此时在idea中打开该文件是反编译优化后的内容，如果想查看原始的   字节码文件，可以cd到对应的`.class`文件目录，然后执行 `javap -c xxx`，例如，`javap -c hello.class`,此时，控制台就会显示对应的指令信息。  `javap -v xxx` 命令则是显示具体内容

[参考文章](https://blog.csdn.net/weixin_41276238/article/details/103769956)

+ 快速查看java动态生成class内容的方法

[参考链接](https://blog.csdn.net/wenyuan65/article/details/82634118)



## 常用设置

### 设置 选中对象时背景颜色

setting =》  editor  =》  color scheme  =》  general    右侧   

1、editor  =》  selection background   设置为`BA4040`

2、code =》 identifier under caret  设置为 `BA4040`

3、 code  =》  identifier under caret（write） 设置为  `BA4040`



## idea 跑项目  cpu 狂响

> 修改 idea安装目录下的 idea.exe.vmoptions    位置是  安装位置/bin/idea.exe.vmoptions
>
> 设置其中的 
>
> -Xms 800m
>
> -Xmx 800m
>
> -XX:ReservedCodeCacheSize=350m
>
> 以上为8g内存的配置，如果是16g的，则改为 1g、2g、1g

[参考链接](https://blog.csdn.net/qq_27093465/article/details/81947933?utm_source=blogxgwz7)

## 推荐插件

+ Translation    翻译工具
+ GsonFormat
+ Free-idea-mybatis
+ CodeGlance
+ Activate-power-mode
  + <font color=red>去掉 代码界面的 max 0  </font>              window   =》   activate-power-mode  =》  combo  (不勾选即可)
+ Alibaba Java Coding Guidelines
+ Lombok
+ Material Theme UI
  + 主题选的是 material oceanic 、
  + <font color=red>修改默认 选中对象背景颜色以及关联对象的颜色</font>     setting  =》 editor  =》 color scheme  =》  general   右侧  code  =》  identifier under caret
+ Rainbow Brackets
+ Maven Helper
+ SequenceDiagram
+ jclasslib  用于查看字节码的工具，   功能类似  javap
+ Git Commit Template     git  提交的commit 模板





## 调试技巧

### debug 循环时，指定到某个条件，例如0-10遍历，想直接到6

+ 在循环的地方打上断点，然后右击，可以设置条件，设置完毕后debug的时候，会直接进入符合改条件的情况

<img src="./images/2021-01-27-2.jpg" alt="示例" style="zoom: 67%;" />

### 方法之间 回跳，回到前一个方法

<img src="./images/2021-01-27-3.jpg" alt="图解" style="zoom:67%;" />



## 问题记录

### 从git上下载的项目中，pom显示被划掉

[参考链接](https://blog.csdn.net/xufengzhu/article/details/114496727)

### 新建的 配置文件  bootstrap.properties 输入没有提示

+ 先打开项目架构

![第一步](./images/2021-03-11-1.jpg)

+ 点击 自定化配置图标

![第二部](./images/2021-03-11-2.jpg)

+ 输入配置文件的名称，加入即可

<img src="./images/2021-03-11-3.jpg" alt="第三部" style="zoom:50%;" />

+ 成功后，配置文件的图标颜色变化

![第四步](C:\Users\E480\AppData\Roaming\Typora\typora-user-images\image-20210311141658004.png)



### pom.xml 没有划掉，但是也无法将 bootstrap文件设置为配置文件

> 删除该项目模块，重新加入。





### mevan  pom.xml中包没有自动导入

1）看maven（%MAVEN_HOME%\bin） 是否加入环境变量中   mvn -V可以查看
2)查看idea中maven的配置：file>setting>build,Execution,deployment>build tools>maven 中最后三项，一般是最后两项没有匹配（自己设置安装目录）
默认：  User setting file:    C:\Users\wine_light\.m2\settings.xml
             Local repository:    C:\Users\wine_light\.m2\repository


3）修改本地仓库需要同时修改  E:\Apache\maven\apache-maven-3.6.0\conf\setttings.xml 和c:\users\wine_light\.m2\setttings.xml。将其中的<localRepository>E:\Apache\maven-repository</localRepository>配置为修改后的位置

mvn help:system  会下载很多文件，从中央仓库下载到本地仓库

### 创建springboot项目加载慢的问题：
1、修改本地hosts文件。  添加127.0.0.1   localhost    和   ::1  localhost  <font color=red>hosts文件中ip地址和名称之间至少两个空格</font>
2、更改  idea.exe.vmoptions和idea64.exe.vmoptions中的Xmx和Xms
这两个文件在IntellliJ IDEA运行程序的同一级目录下。
3、win+r    netsh winsock reset 重启电脑
4、切换网络。

### 父子maven项目    子项目  springboot 初始化存在问题。
1、maven 目录是否是自己安装maven 的目录
2、父子模块的依赖关系，父模块需要将子模块添加到 modules标签中   =>   可以解决子模块没有设置属性目录的问题
3、父文件夹同目录的  .idea文件夹可能会印象子模块的    类设置（如 source root等）

### idea 快捷键冲突  ctrl+alt+左箭头无法回退问题
setttings->appearance & behavior  -> menus and toolbars  ->navigation bar boolbars -> toolbar run actions 中的forward  和 back添加出来

### idea 怎么查看方法的调用链
先选中目标方法    然后   idea  菜单栏  view  >  sort line

### 数据库设置时区问题
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")



### IntelliJ IDEA如何运行单个程序 跳过错误项目中的错误JAVA类

Run - Edit Configurations - Before launch 里面，把 Build 换成 Build, no error check ， Apply之后按往常一样运行就可以啦。当然，前提是你这个main函数所依赖的所有class都没有错误



### java中怎么调用 .class文件中的类

根据  `.class`文件内的包名创建目录结构，然后把它丢进去，再在需要使用的地方通过import 引入，末尾的.class文件名改为* 即可。

###  idea 设置字体大小失效

[参考链接](https://blog.csdn.net/qq_35396093/article/details/89518591)

设置错误的原因是修改的是默认值，而系统使用的是主题的字体设置



### 在进行github相关操作的时候一直被要求"Enter passphrase for key". 如何避免每次操作都重新填写一遍passphrase?

[参考链接](https://blog.csdn.net/nankai0912678/article/details/105550421/)

### 同级目录下的两个项目有引用关系怎么解决

<img src="./images/2021-02-24-1.jpg" alt="引用关系图示" style="zoom:67%;" />

> outer-interface-parent 项目存在对 common-starter 项目的依赖，但是如果单纯启动outer-interface-parent项目时，在导入依赖的时候会提示错误，无法找到common-starter 。

#### 解决方法

<img src="./images/2021-02-24-2.jpg" alt="解决方法" style="zoom:50%;" />

在打开的  outer-interface-parent 项目中通过mevan 引入依赖的项目即可，最后通过重新导入maven 依赖就可以了



# 网络编程

## jdk  NIO 存在的问题

- **Java NIO 的 API 非常复杂。** 要写出成熟可用的 Java NIO 代码，需要熟练掌握 JDK 中的 Selector、ServerSocketChannel、SocketChannel、ByteBuffer 等组件，还要理解其中一些反人类的设计以及底层原理，这对新手来说是非常不友好的。
- **如果直接使用 Java NIO 进行开发，难度和开发量会非常大**。我们需要自己补齐很多可靠性方面的实现，例如，网络波动导致的连接重连、半包读写等。这就会导致一些本末倒置的情况出现：核心业务逻辑比较简单，但补齐其他公共能力的代码非常多，开发耗时比较长。这时就需要一个统一的 NIO 框架来封装这些公共能力了。
- **JDK 自身的 Bug**。其中比较出名的就要属 Epoll Bug 了，这个 Bug 会导致 Selector 空轮询，CPU 使用率达到 100%，这样就会导致业务逻辑无法执行，降低服务性能。

## nettty的优点

Netty 在 JDK 自带的 NIO API 基础之上进行了封装，解决了 JDK 自身的一些问题

- 入门简单，使用方便，文档齐全，无其他依赖，只依赖 JDK 就够了。
- 高性能，高吞吐，低延迟，资源消耗少。
- 灵活的线程模型，支持阻塞和非阻塞的I/O 模型。
- 代码质量高，目前主流版本基本没有 Bug。



## 传统   BIO

每个请求都需要独立的线程完成读数据、业务处理以及写回数据的完整操作。

<img src="./images/CgqCHl9EvKaAF18_AACJ4Y62QAY004.png" alt="bio 流程" style="zoom:50%;" />

一个线程在同一时刻只能与一个连接绑定，如下图所示，当请求的并发量较大时，就需要创建大量线程来处理连接，这就会导致系统浪费大量的资源进行线程切换，降低程序的性能。我们知道，**网络数据的传输速度是远远慢于 CPU 的处理速度，连接建立后，并不总是有数据可读，连接也并不总是可写，那么线程就只能阻塞等待，CPU 的计算能力不能得到充分发挥，同时还会导致大量线程的切换，浪费资源**。

<img src="./images/CgqCHl9EvLSAQzfFAACIPU0Pqkg586.png" alt="多个请求展示" style="zoom:50%;" />

## NIO    i/o 多路复用模型

I/O 复用模型中的多个连接会共用一个 Selector 对象，由 Selector 感知连接的读写事件，而此时的线程数并不需要和连接数一致，只需要很少的线程定期从 Selector 上查询连接的读写状态即可，无须大量线程阻塞等待连接。当某个连接有新的数据可以处理时，操作系统会通知线程，线程从阻塞状态返回，开始进行读写操作以及后续的业务逻辑处理。I/O 复用的模型如下图所示：

<img src="./images/Ciqc1F9EvNOACOC5AADhkXKnAFg681.png" alt="i/o复用模型" style="zoom:67%;" />

**Netty 就是采用了上述 I/O 复用的模型**。由于多路复用器 Selector 的存在，可以同时并发处理成百上千个网络连接，大大增加了服务器的处理能力。另外，Selector 并不会阻塞线程，也就是说当一个连接不可读或不可写的时候，线程可以去处理其他可读或可写的连接，这就充分提升了 I/O 线程的运行效率，避免由于频繁 I/O 阻塞导致的线程切换。如下图所示：

<img src="./images/Ciqc1F9EvOOADRMzAACeQMLGfbs278.png" alt="线程与连接绑定关系" style="zoom:67%;" />

从数据处理的角度来看，**传统的阻塞 I/O 模型处理的是字节流或字符流，也就是以流式的方式顺序地从一个数据流中读取一个或多个字节，并且不能随意改变读取指针的位置。**而在 NIO 中则抛弃了这种传统的 I/O 流概念，引入了 Channel 和 Buffer 的概念，可以从 Channel 中读取数据到 Buffer 中或将数据从 Buffer 中写入到 Channel。**Buffer 不像传统 I/O 中的流那样必须顺序操作，在 NIO 中可以读写 Buffer 中任意位置的数据**。

<font color=red>**Netty 采用了 Reactor 线程模型的设计。** Reactor 模式，也被称为 Dispatcher 模式，**核心原理是 Selector 负责监听 I/O 事件，在监听到 I/O 事件之后，分发（Dispatch）给相关线程进行处理**。</font>



为了帮助你更好地了解 Netty 线程模型的设计理念，我们将从最基础的单 Reactor 单线程模型开始介绍，然后逐步增加模型的复杂度，最终到 Netty 目前使用的非常成熟的线程模型设计。

## 模型进化

### 单 Reactor 单线程

Reactor 对象监听客户端请求事件，收到事件后通过 Dispatch 进行分发。如果是连接建立的事件，则由 Acceptor 通过 Accept 处理连接请求，然后创建一个 Handler 对象处理连接建立之后的业务请求。如果不是连接建立的事件，而是数据的读写事件，则 Reactor 会将事件分发对应的 Handler 来处理，由这里唯一的线程调用 Handler 对象来完成读取数据、业务处理、发送响应的完整流程。当然，该过程中也可能会出现连接不可读或不可写等情况，该单线程会去执行其他 Handler 的逻辑，而不是阻塞等待。具体情况如下图所示：

![单Reactor 单线程](./images/CgqCHl9EvVGAPXATAAEj0pK8ONM000.png)

单 Reactor 单线程的优点就是：线程模型简单，没有引入多线程，自然也就没有多线程并发和竞争的问题。

但其缺点也非常明显，那就是**性能瓶颈问题**，一个线程只能跑在一个 CPU 上，能处理的连接数是有限的，无法完全发挥多核 CPU 的优势。一旦某个业务逻辑耗时较长，这唯一的线程就会卡在上面，无法处理其他连接的请求，程序进入假死的状态，可用性也就降低了。正是由于这种限制，一般只会在**客户端**使用这种线程模型。



### 单 Reactor 多线程

在单 Reactor 多线程的架构中，Reactor 监控到客户端请求之后，如果连接建立的请求，则由Acceptor 通过 accept 处理，然后创建一个 Handler 对象处理连接建立之后的业务请求。如果不是连接建立请求，则 Reactor 会将事件分发给调用连接对应的 Handler 来处理。到此为止，该流程与单 Reactor 单线程的模型基本一致，**唯一的区别就是执行 Handler 逻辑的线程隶属于一个线程池**。

<img src="./images/CgqCHl9EvWqAJ5jpAAFbymUVJ8o272.png" alt="单Reactor 多线程" style="zoom: 33%;" />

很明显，单 Reactor 多线程的模型可以充分利用多核 CPU 的处理能力，提高整个系统的吞吐量，但引入多线程模型就要考虑线程并发、数据共享、线程调度等问题。在这个模型中，只有一个线程来处理 Reactor 监听到的所有 I/O 事件，其中就包括连接建立事件以及读写事件，当连接数不断增大的时候，这个唯一的 Reactor 线程也会遇到瓶颈。



### 主从 Reactor 多线程

为了解决单 Reactor 多线程模型中的问题，我们可以引入多个 Reactor。其中，Reactor 主线程负责通过 Acceptor 对象处理 MainReactor 监听到的连接建立事件，当Acceptor 完成网络连接的建立之后，MainReactor 会将建立好的连接分配给 SubReactor 进行后续监听。

当一个连接被分配到一个 SubReactor 之上时，会由 SubReactor 负责监听该连接上的读写事件。当有新的读事件（OP_READ）发生时，Reactor 子线程就会调用对应的 Handler 读取数据，然后分发给 Worker 线程池中的线程进行处理并返回结果。待处理结束之后，Handler 会根据处理结果调用 send 将响应返回给客户端，当然此时连接要有可写事件（OP_WRITE）才能发送数据。

<img src="./images/CgqCHl9EvXuARvm7AAF3raiQza8716.png" alt="主从 Reactor 多线程" style="zoom: 33%;" />

主从 Reactor 多线程的设计模式解决了单一 Reactor 的瓶颈。**主从 Reactor 职责明确，主 Reactor 只负责监听连接建立事件，SubReactor只负责监听读写事件**。整个主从 Reactor 多线程架构充分利用了多核 CPU 的优势，可以支持扩展，而且与具体的业务逻辑充分解耦，复用性高。但不足的地方是，在交互上略显复杂，需要一定的编程门槛。

### Netty 线程模型

Netty 同时支持上述几种线程模式，Netty 针对服务器端的设计是在主从 Reactor 多线程模型的基础上进行的修改，如下图所示：

![Netty 线程模型](./images/Ciqc1F9EvZyAZsQlAAMdGh4CXMI139.png)

**Netty 抽象出两组线程池：BossGroup 专门用于接收客户端的连接，WorkerGroup 专门用于网络的读写**。BossGroup 和 WorkerGroup 类型都是 NioEventLoopGroup，相当于一个事件循环组，其中包含多个事件循环 ，每一个事件循环是 NioEventLoop。

NioEventLoop 表示一个不断循环的、执行处理任务的线程，每个 NioEventLoop 都有一个Selector 对象与之对应，用于监听绑定在其上的连接，这些连接上的事件由 Selector 对应的这条线程处理。每个 NioEventLoopGroup 可以含有多个 NioEventLoop，也就是多个线程。

每个 Boss NioEventLoop 会监听 Selector 上连接建立的 accept 事件，然后处理 accept 事件与客户端建立网络连接，生成相应的 NioSocketChannel 对象，一个 NioSocketChannel 就表示一条网络连接。之后会将 NioSocketChannel 注册到某个 Worker NioEventLoop 上的 Selector 中。

每个 Worker NioEventLoop 会监听对应 Selector 上的 read/write 事件，当监听到 read/write 事件的时候，会通过 Pipeline 进行处理。一个 Pipeline 与一个 Channel 绑定，在 Pipeline 上可以添加多个 ChannelHandler，每个 ChannelHandler 中都可以包含一定的逻辑，例如编解码等。Pipeline 在处理请求的时候，会按照我们指定的顺序调用 ChannelHandler。



## 成员介绍

#### Channel 

Channel 是 Netty 对网络连接的抽象，核心功能是执行网络 I/O 操作。不同协议、不同阻塞类型的连接对应不同的 Channel 类型。我们一般用的都是 NIO 的 Channel，下面是一些常用的 NIO Channel 类型。

+ NioSocketChannel：对应异步的 TCP Socket 连接。

+ NioServerSocketChannel：对应异步的服务器端 TCP Socket 连接。

+ NioDatagramChannel：对应异步的 UDP 连接。

上述异步 Channel 主要提供了**异步**的网络 I/O 操作，例如：建立连接、读写操作等。异步调用意味着任何 I/O 调用都将立即返回，并且不保证在调用返回时所请求的 I/O 操作已完成。I/O 操作返回的是一个 ChannelFuture 对象，无论 I/O 操作是否成功，Channel 都可以通过监听器通知调用方，我们通过向 ChannelFuture 上注册监听器来监听 I/O 操作的结果。

Netty 也支持同步 I/O 操作，但在实践中几乎不使用。绝大多数情况下，我们使用的是 Netty 中异步 I/O 操作。虽然立即返回一个 ChannelFuture 对象，但不能立刻知晓 I/O 操作是否成功，这时我们就需要向 ChannelFuture 中注册一个监听器，当操作执行成功或失败时，监听器会自动触发注册的监听事件。

另外，Channel 还提供了**检测当前网络连接状态**等功能，这些可以帮助我们实现网络异常断开后自动重连的功能。

#### selector

**Selector 是对多路复用器的抽象**，也是 Java NIO 的核心基础组件之一。Netty 就是基于 Selector 对象实现 I/O 多路复用的，在 Selector 内部，会通过系统调用不断地查询这些注册在其上的 Channel 是否有已就绪的 I/O 事件，例如，可读事件（OP_READ）、可写事件（OP_WRITE）或是网络连接事件（OP_ACCEPT）等，而无须使用用户线程进行轮询。这样，我们就可以用一个线程监听多个 Channel 上发生的事件。

#### ChannelPipeline&ChannelHandler

提到 Pipeline，你可能最先想到的是 Linux 命令中的管道，它可以实现将一条命令的输出作为另一条命令的输入。Netty 中的 ChannelPipeline 也可以实现类似的功能：**ChannelPipeline 会将一个 ChannelHandler 处理后的数据作为下一个 ChannelHandler 的输入**。

下图我们引用了 Netty Javadoc 中对 ChannelPipeline 的说明，描述了 ChannelPipeline 中 ChannelHandler 通常是如何处理 I/O 事件的。Netty 中定义了两种事件类型：**入站（Inbound）事件和出站（Outbound）事件**。这两种事件就像 Linux 管道中的数据一样，在 ChannelPipeline 中传递，事件之中也可能会附加数据。ChannelPipeline 之上可以注册多个 ChannelHandler（ChannelInboundHandler 或 ChannelOutboundHandler），我们在 ChannelHandler 注册的时候决定处理 I/O 事件的顺序，这就是典型的**责任链模式**。

I/O 事件不会在 ChannelPipeline 中自动传播，而是需要调用ChannelHandlerContext 中定义的相应方法进行传播，例如：fireChannelRead() 方法和 write() 方法等。

这里我们举一个简单的例子，如下所示，在该 ChannelPipeline 上，我们添加了 5 个 ChannelHandler 对象：

```
ChannelPipeline p = socketChannel.pipeline(); 
p.addLast("1", new InboundHandlerA()); 
p.addLast("2", new InboundHandlerB());
p.addLast("3", new OutboundHandlerA());
p.addLast("4", new OutboundHandlerB());
p.addLast("5", new InboundOutboundHandlerX());
```

- 对于入站（Inbound）事件，处理序列为：1 → 2 → 5
- 对于出站（Outbound）事件，处理序列为：5 → 4 → 3

**入站（Inbound）与出站（Outbound）事件处理顺序正好相反。**

入站事件一般由I/O事件触发，而出站事件一般由用户触发。

#### NioEventLoop

一个 EventLoop 对象由一个永远都不会改变的线程驱动，同时一个 NioEventLoop 包含了一个 Selector 对象，可以支持多个 Channel 注册在其上，该 NioEventLoop 可以同时服务多个 Channel，每个 Channel 只能与一个 NioEventLoop 绑定，这样就实现了线程与 Channel 之间的关联。

我们知道，Channel 中的 I/O 操作是由 ChannelPipeline 中注册的 ChannelHandler 进行处理的，而 ChannelHandler 的逻辑都是由相应 NioEventLoop 关联的那个线程执行的。

**NioEventLoop 主要做三件事：监听 I/O 事件、执行普通任务以及执行定时任务**。NioEventLoop 到底分配多少时间在不同类型的任务上，是可以配置的。另外，为了防止 NioEventLoop 长时间阻塞在一个任务上，一般会将耗时的操作提交到其他业务线程池处理。

#### NioEventLoopGroup

**NioEventLoopGroup 表示的是一组 NioEventLoop**。Netty 为了能更充分地利用多核 CPU 资源，一般会有多个 NioEventLoop 同时工作，至于多少线程可由用户决定，Netty 会根据实际上的处理器核数计算一个默认值，具体计算公式是：CPU 的核心数 * 2，当然我们也可以根据实际情况手动调整。

当一个 Channel 创建之后，Netty 会调用 NioEventLoopGroup 提供的 next() 方法，按照一定规则获取其中一个 NioEventLoop 实例，并将 Channel 注册到该 NioEventLoop 实例，之后，就由该 NioEventLoop 来处理 Channel 上的事件。

#### 以上几者关系总结图

![自己总结的关系图](./images/Snipaste_2021-01-04_11-36-26.jpg)

#### ByteBuf

在进行跨进程远程交互的时候，我们需要以字节的形式发送和接收数据，发送端和接收端都需要一个高效的数据容器来缓存字节数据，ByteBuf 就扮演了这样一个数据容器的角色。

ByteBuf 类似于一个字节数组，其中维护了一个读索引和一个写索引，分别用来控制对 ByteBuf 中数据的读写操作，两者符合下面的不等式：`0 <= readerIndex <= writerIndex <= capacity`

![关系图](./images/CgqCHl9IlsaAJYcYAAA31kf6i_g696.png)

Netty 中主要分为以下三大类 ByteBuf：

- **Heap Buffer（堆缓冲区）**。这是最常用的一种 ByteBuf，它将数据存储在 JVM 的堆空间，其底层实现是在 JVM 堆内分配一个数组，实现数据的存储。堆缓冲区可以快速分配，当不使用时也可以由 GC 轻松释放。它还提供了直接访问底层数组的方法，通过 ByteBuf.array() 来获取底层存储数据的 byte[] 。
- **Direct Buffer（直接缓冲区）**。直接缓冲区会使用堆外内存存储数据，不会占用 JVM 堆的空间，使用时应该考虑应用程序要使用的最大内存容量以及如何及时释放。直接缓冲区在使用 Socket 传递数据时性能很好，当然，它也是有缺点的，因为没有了 JVM GC 的管理，在分配内存空间和释放内存时，比堆缓冲区更复杂，Netty 主要使用内存池来解决这样的问题，这也是 Netty 使用内存池的原因之一。
- **Composite Buffer（复合缓冲区）**。我们可以创建多个不同的 ByteBuf，然后提供一个这些 ByteBuf 组合的视图，也就是 CompositeByteBuf。它就像一个列表，可以动态添加和删除其中的 ByteBuf。

#### 内存管理

Netty 使用 ByteBuf 对象作为数据容器，进行 I/O 读写操作，其实 Netty 的内存管理也是围绕着ByteBuf 对象高效地分配和释放。从内存管理角度来看，ByteBuf 可分为 Unpooled 和 Pooled 两类。

- **Unpooled，是指非池化的内存管理方式**。每次分配时直接调用系统 API 向操作系统申请 ByteBuf，在使用完成之后，通过系统调用进行释放。Unpooled 将内存管理完全交给系统，不做任何特殊处理，使用起来比较方便，对于申请和释放操作不频繁、操作成本比较低的 ByteBuf 来说，是比较好的选择。
- **Pooled，是指池化的内存管理方式**。该方式会预先申请一块大内存形成内存池，在需要申请 ByteBuf 空间的时候，会将内存池中一部分合理的空间封装成 ByteBuf 给服务使用，使用完成后回收到内存池中。前面提到 DirectByteBuf 底层使用的堆外内存管理比较复杂，池化技术很好地解决了这一问题。

下面我们从如何高效分配和释放内存、如何减少内存碎片以及在多线程环境下如何减少锁竞争这三个方面介绍一下 Netty 提供的 ByteBuf 池化技术。

Netty 首先会向系统申请一整块连续内存，称为 Chunk（默认大小为 16 MB），这一块连续的内存通过 PoolChunk 对象进行封装。之后，Netty 将 Chunk 空间进一步拆分为 Page，每个 Chunk 默认包含 2048 个 Page，每个 Page 的大小为 8 KB。

在同一个 Chunk 中，Netty 将 Page 按照不同粒度进行分层管理。如下图所示，从下数第 1 层中每个分组的大小为 1 * PageSize，一共有 2048 个分组；第 2 层中每个分组大小为 2 * PageSize，一共有 1024 个组；第 3 层中每个分组大小为 4 * PageSize，一共有 512 个组；依次类推，直至最顶层。

![图片理解](./images/CgqCHl9IlteANQ8lAADm9qN5mgE993.png)

#### 1. 内存分配&释放

当服务向内存池请求内存时，Netty 会将请求分配的内存数向上取整到最接近的分组大小，然后在该分组的相应层级中从左至右寻找空闲分组。例如，服务请求分配 3 * PageSize 的内存，向上取整得到的分组大小为 4 * PageSize，在该层分组中找到完全空闲的一组内存进行分配即可，如下图：

![fd](./images/Ciqc1F9IluuAFgouAADoPKhjuW4842.png)

当分组大小 4 * PageSize 的内存分配出去后，为了方便下次内存分配，分组被标记为全部已使用（图中红色标记），向上更粗粒度的内存分组被标记为部分已使用（图中黄色标记）。

Netty 使用**完全平衡树的结构**实现了上述算法，这个完全平衡树底层是基于一个 byte 数组构建的，如下图所示：

<img src="./images/Ciqc1F9IlvKACdYpAAF2w22m4sQ981.png" alt="fds" style="zoom:50%;" />

#### 2. 大对象&小对象的处理

当申请分配的对象是超过 Chunk 容量的大型对象，Netty 就不再使用池化管理方式了，在每次请求分配内存时单独创建特殊的非池化 PoolChunk 对象进行管理，当对象内存释放时整个PoolChunk 内存释放。

如果需要一定数量空间远小于 PageSize 的 ByteBuf 对象，例如，创建 256 Byte 的 ByteBuf，按照上述算法，就需要为每个小 ByteBuf 对象分配一个 Page，这就出现了很多内存碎片。Netty 通过再将 Page 细分的方式，解决这个问题。Netty 将请求的空间大小向上取最近的 16 的倍数（或 2 的幂），规整后小于 PageSize 的小 Buffer 可分为两类。

- 微型对象：规整后的大小为 16 的整倍数，如 16、32、48、……、496，一共 31 种大小。
- 小型对象：规整后的大小为 2 的幂，如 512、1024、2048、4096，一共 4 种大小。

Netty 的实现会先从 PoolChunk 中申请空闲 Page，同一个 Page 分为相同大小的小 Buffer 进行存储；这些 Page 用 PoolSubpage 对象进行封装，PoolSubpage 内部会记录它自己能分配的小 Buffer 的规格大小、可用内存数量，并通过 bitmap 的方式记录各个小内存的使用情况（如下图所示）。虽然这种方案不能完美消灭内存碎片，但是很大程度上还是减少了内存浪费。

<img src="./images/CgqCHl9Ilv-ABhKnAAFcBHa9_3E953.png" alt="fds" style="zoom:50%;" />

为了解决单个 PoolChunk 容量有限的问题，Netty 将多个 PoolChunk 组成链表一起管理，然后用 PoolChunkList 对象持有链表的 head。

**Netty 通过 PoolArena 管理 PoolChunkList 以及 PoolSubpage**。

PoolArena 内部持有 6 个 PoolChunkList，各个 PoolChunkList 持有的 PoolChunk 的使用率区间有所不同，如下图所示：

<img src="./images/CgqCHl9IlwaAMIzVAACq5iIjcZk639.png" alt="fsd" style="zoom:50%;" />

6 个 PoolChunkList 对象组成双向链表，当 PoolChunk 内存分配、释放，导致使用率变化，需要判断 PoolChunk 是否超过所在 PoolChunkList 的限定使用率范围，如果超出了，需要沿着 6 个 PoolChunkList 的双向链表找到新的合适的 PoolChunkList ，成为新的 head。同样，当新建 PoolChunk 分配内存或释放空间时，PoolChunk 也需要按照上面逻辑放入合适的PoolChunkList 中。

<img src="./images/CgqCHl9Ilw2ATgO0AACWza-dQV4641.png" alt="fds" style="zoom:50%;" />

从上图可以看出，这 6 个 PoolChunkList 额定使用率区间存在交叉，这样设计的原因是：如果使用单个临界值的话，当一个 PoolChunk 被来回申请和释放，内存使用率会在临界值上下徘徊，这就会导致它在两个 PoolChunkList 链表中来回移动。

PoolArena 内部持有 2 个 PoolSubpage 数组，分别存储微型 Buffer 和小型 Buffer 的PoolSubpage。相同大小的 PoolSubpage 组成链表，不同大小的 PoolSubpage 链表的 head 节点保存在 tinySubpagePools 或者 smallSubpagePools 数组中，如下图：

<img src="./images/Ciqc1F9IlxSAWAuXAADUtE1ddhw421.png" alt="dsf" style="zoom:33%;" />

#### 3. 并发处理

内存分配释放不可避免地会遇到多线程并发场景，PoolChunk 的完全平衡树标记以及 PoolSubpage 的 bitmap 标记都是多线程不安全的，都是需要加锁同步的。为了减少线程间的竞争，Netty 会提前创建多个 PoolArena（默认数量为 2 * CPU 核心数），当线程首次请求池化内存分配，会找被最少线程持有的 PoolArena，并保存线程局部变量 PoolThreadCache 中，实现线程与 PoolArena 的关联绑定。

Netty 还提供了延迟释放的功能，来提升并发性能。当内存释放时，PoolArena 并没有马上释放，而是先尝试将该内存关联的 PoolChunk 和 Chunk 中的偏移位置等信息存入 ThreadLocal 的固定大小缓存队列中，如果该缓存队列满了，则马上释放内存。当有新的分配请求时，PoolArena 会优先访问线程本地的缓存队列，查询是否有缓存可用，如果有，则直接分配，提高分配效率。







# 额外的知识

## bitmap

[参考文章](https://www.cnblogs.com/myseries/p/10880641.html)

### 基本理解

例如，想存储32个不重复的数值，大小范围在0-255。一般情况下会通过创建一个int数组，例如`int[32]`。此时，占据的空间是32 * 32 * 8 bit。但如果采用bitmap，则可以通过 8个 int 来表示 0-255，此时只占据空间 32 * 8 bit。远远小于前者。

### BitMap应用

　　1：看个小场景 > 在3亿个整数中找出不重复的整数，限制内存不足以容纳3亿个整数。

　　对于这种场景我可以采用2-BitMap来解决，即为每个整数分配2bit，用不同的0、1组合来标识特殊意思，如00表示此整数没有出现过，01表示出现一次，11表示出现过多次，就可以找出重复的整数了，其需要的内存空间是正常BitMap的2倍，为：3亿*2/8/1024/1024=71.5MB。

　　具体的过程如下：

　　扫描着3亿个整数，组BitMap，先查看BitMap中的对应位置，如果00则变成01，是01则变成11，是11则保持不变，当将3亿个整数扫描完之后也就是说整个BitMap已经组装完毕。最后查看BitMap将对应位为11的整数输出即可。

　　2:已知某个文件内包含一些电话号码，每个号码为8位数字，统计不同号码的个数。

　　8位最多99 999 999，大概需要99m个bit，大概10几m字节的内存即可。 （可以理解为从0-99 999 999的数字，每个数字对应一个Bit位，所以只需要99M个Bit==1.2MBytes，这样，就用了小小的1.2M左右的内存表示了所有的8位数的电话）　　

### BitMap问题

　　BitMap 的思想在面试的时候还是可以用来解决不少问题的，然后在很多系统中也都会用到，算是一种不错的解决问题的思路。

　　但是 BitMap 也有一些局限，因此会有其它一些基于 BitMap 的算法出现来解决这些问题。

- 数据碰撞。比如将字符串映射到 BitMap 的时候会有碰撞的问题，那就可以考虑用 **Bloom Filter** 来解决，Bloom Filter 使用多个 Hash 函数来减少冲突的概率。
- 数据稀疏。又比如要存入(10,8887983,93452134)这三个数据，我们需要建立一个 99999999 长度的 BitMap ，但是实际上只存了3个数据，这时候就有很大的空间浪费，碰到这种问题的话，可以通过引入 Roaring BitMap 来解决。



## java序列化

主要为4个步骤：

+ 被序列化的对象需要实现 Serializable 接口。示例如下：

```
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private transient StudentUtil studentUtil;
}
```

在这个示例中我们可以看到**transient 关键字**，它的作用就是：**在对象序列化过程中忽略被其修饰的成员属性变量**。一般情况下，它可以用来修饰一些非数据型的字段以及一些可以通过其他字段计算得到的值。**通过合理地使用 transient 关键字，可以降低序列化后的数据量，提高网络传输效率。**

+ 生成一个序列号 serialVersionUID，这个序列号不是必需的，但还是建议你生成。serialVersionUID 的字面含义是序列化的版本号，只有序列化和反序列化的 serialVersionUID 都相同的情况下，才能够成功地反序列化。如果类中没有定义 serialVersionUID，那么 JDK 也会随机生成一个 serialVersionUID。如果在某些场景中，你希望不同版本的类序列化和反序列化相互兼容，那就需要定义相同的 serialVersionUID。
+ 根据需求决定是否要重写 writeObject()/readObject() 方法，实现自定义序列化。
+ 调用 java.io.ObjectOutputStream 的 writeObject()/readObject() 进行序列化与反序列化。

<font color=red>既然 Java 本身的序列化操作如此简单，那为什么市面上还依旧出现了各种各样的序列化框架呢？因为这些**第三方序列化框架的速度更快、序列化的效率更高，而且支持跨语言操作**。</font>







# 程序员的数学课

## 数制

幽默段子：世界上有10中人，一种懂二进制，另一种不懂二进制。

一般而言，没有说明的数字是十进制（decimal）下的数字。

+ 表示二进制（binary）时，会用`0b` 作为数字前缀；

+ 表示八进制（octonary）时，会用`0o` 或者`0`作为数字的前缀；
+ 表示十六进制（hexadecimal）时，会用`0x` 作为数字的前缀；

## 与、或、非

MECE原则（Mutually Exclusive Collectively Exhaustive）的中文意思是 "互相独立，完全穷尽"，简而言之，能够做到不重叠、不遗漏，兼顾排他性和完整性。

##  提高 投入和产出

对于一个没有外部力量作用的系统，它的总账为零。相反，如果一个系统受到了外部力量，那么总账就可能不是零了。

### 关键要素：系统、指标和兑换

系统，就是一个个对象，它包括了你研究的目标对象，也包括了影响你研究目标的外部系统。对于大漂亮的学习而言，大漂亮就是一个系统，老师也是一个系统。

指标，是评价系统运转结果的数学变量，即总账。例如，对于大漂亮的系统而言，指标包括但不限于考试成绩、生活愉悦度、日均自习时长、日均参加补习班的时长、日均娱乐时长等。

兑换，是个动作，也是个结果，即你在用什么来换取什么。算账定律（算账版的能量守恒定律）说到，对于一个没有外部力量作用的系统，它的总账为零；反过来说，要想指标（总账）有提高，就需要借助外部力量，并把它兑换为指标的提高。

### 转化漏斗分析法

有了外部力量之后，就要开始分析外部力量作用在系统中的效率，这就需要转化漏斗分析法。

+ 转化，是一个动作，表示的是外部力量转化为指标提高的动作过程。

+ 漏斗，代表了效率，即转化过程的投入和产出分别是多少。

## 万物皆可数学

### 双十一活动

某商家的促销规则是：某笔订单消费满 200 元，可以获得 100 元的代金券，代金券可以在下次消费中使用，下次使用时的规则是，消费满 300 元，直接抵扣 100 元。

#### 总结

+ 表面上 花费200 给100 代金券，折扣率为50%
+ 实际上 想使用 花费所得的代金券 必须消费 500，只是打了一个八折。

<font color=red>投资回报率 ROI（ROI=增量的回报/增量的投入）</font>

### 每天进步一点点

某公司的营业额以每月 5% 的速度增长，多少个月后能翻倍？  14天







# java  lambda

[参考链接](https://blog.csdn.net/ioriogami/article/details/12782141/)

## 什么是   lambda  表达式

### 版本一

```
public int add(int x, int y) {
    return x + y;
}
```

### 版本二

```
(int x, int y) -> {return x + y;}
```

### 版本三

```
(x,y)->{return x+y;}
```

### 版本四

```
(x,y)->x+y;
```

<font color=red>可以看到它由三部分组成：参数列表，箭头（->），以及一个表达式或语句块。</font>

### 如果无参数

```
()->return 3;
```

 ### 如果单个参数

```
x->x*x;//return 可以省略
```

## 函数式接口  funcational interface

这是Java8新引入的概念。它的定义是：一个接口，如果只有一个显式声明的抽象方法，那么它就是一个函数式接口。一般用@FunctionalInterface标注出来（也可以不标）。

**JDK预定义了很多函数式接口以避免用户重复定义。**

```
@FunctionalInterface
    public interface Function<T, R> { 
        R apply(T t);
    }
    
@FunctionalInterface
    public interface Consumer<T> {
        void accept(T t);
    }

@FunctionalInterface
    public interface Predicate<T> {
        boolean test(T t);
    }
```



## 设计lambda的一个原因

集合类的批处理操作与λ表达式的配合使用乃是Java8的最主要特性。集合类的批处理操作API的目的是实现集合类的“内部迭代”，并期望充分利用现代多核CPU进行并行计算。
**Java8之前集合类的迭代（Iteration）都是外部的，即客户代码。而内部迭代意味着改由Java类库来进行迭代，而不是客户代码。**

### 外部迭代

```
for(Object o: list) { 
        System.out.println(o);
    }
```

### 内部迭代

```
list.forEach(o -> {System.out.println(o);}); 
```



Java8为集合类引入了另一个重要概念：流（stream）。一个流通常以一个集合类实例为其数据源，然后在其上定义各种操作。流的API设计使用了管道（pipelines）模式。对流的一次操作会返回另一个流。如同IO的API或者StringBuffer的append方法那样，从而多个不同的操作可以在一个语句里串起来。



```
// 从shapes 中选中颜色是蓝的，然后把他们的颜色改成红色
List<Shape> shapes = ...
    shapes.stream()
      .filter(s -> s.getColor() == BLUE)
      .forEach(s -> s.setColor(RED));
```



```
 //给出一个String类型的数组，找出其中所有不重复的素数
    public void distinctPrimary(String... numbers) {
        List<String> l = Arrays.asList(numbers);
        List<Integer> r = l.stream()
                .map(e -> new Integer(e))
                .filter(e -> Primes.isPrime(e))
                .distinct()
                .collect(Collectors.toList());
        System.out.println("distinctPrimary result is: " + r);
    }
```



```
 //给出一个String类型的数组，找出其中各个素数，并统计其出现次数
    public void primaryOccurrence(String... numbers) {
        List<String> l = Arrays.asList(numbers);
        Map<Integer, Integer> r = l.stream()
            .map(e -> new Integer(e))
            .filter(e -> Primes.isPrime(e))
            .collect( Collectors.groupingBy(p->p, Collectors.summingInt(p->1)) );
        System.out.println("primaryOccurrence result is: " + r);
    }
```



```
//给出一个String类型的数组，求其中所有不重复素数的和
    public void distinctPrimarySum(String... numbers) {
        List<String> l = Arrays.asList(numbers);
        int sum = l.stream()
            .map(e -> new Integer(e))
            .filter(e -> Primes.isPrime(e))
            .distinct()
            .reduce(0, (x,y) -> x+y); // equivalent to .sum()
        System.out.println("distinctPrimarySum result is: " + sum);
    }
```



```
 // 统计年龄在25-35岁的男女人数、比例
    public void boysAndGirls(List<Person> persons) {
        Map<Integer, Integer> result = persons.parallelStream().filter(p -> p.getAge()>=25 && p.getAge()<=35).
            collect(
                Collectors.groupingBy(p->p.getSex(), Collectors.summingInt(p->1))
        );
        System.out.print("boysAndGirls result is " + result);
        System.out.println(", ratio (male : female) is " + (float)result.get(Person.MALE)/result.get(Person.FEMALE));
    }
```

## Stream 方法中常用接口

### reduce()

reduce操作可以实现从一组元素中生成一个值，`sum()`、`max()`、`min()`、`count()`等都是reduce操作，将他们单独设为函数只是因为常用。`reduce()`的方法定义有三种重写形式：

- `Optional<T> reduce(BinaryOperator<T> accumulator)`

<img src="./images/2021-02-05-4.jpg" alt="等价代码" style="zoom:67%;" />

- `T reduce(T identity, BinaryOperator<T> accumulator)`

<img src="./images/2021-02-05-3.jpg" alt="等价代码" style="zoom:67%;" />

- `<U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)`

<font color=red> 三个参数整体理解</font>

<img src="./images/2021-02-05-2.jpg" alt="三个参数说明" style="zoom:50%;" />

#### 示例代码

```
public class StreamDemo {
    private static final String [] WORD={"hello","hi","name","height","exciting"};
    public static void main(String[] args) {
        System.out.println(getLongestWord());
        System.out.println(getAllCharacter());

    }
    public static String getLongestWord(){
        Optional<String> result= Arrays.stream(WORD).reduce((a,b)->a.length()>=b.length()?a:b);
        return result.get();
    }

    public static Integer getAllCharacter(){
        Integer result=Arrays.stream(WORD).reduce(0,(a,b)->a+b.length(),(a,b)->a+b);
        return result;
    }
}
```

#### 运行结果

![运行结果](./images/2021-02-05-5.jpg)

### collect()

#### 方法引用

|    方法引用类型    |      举例      |
| :----------------: | :------------: |
|    引用静态方法    |  Integer::sum  |
| 引用某个对象的方法 |   list::add    |
|  引用某个类的方法  | String::length |
|    引用构造函数    |  HashMap::new  |

#### 参数介绍

<img src="./images/2021-02-05-6.jpg" alt="三个参数示意" style="zoom:50%;" />





# UML中类之间的关系

在软件系统中，类不是孤独存在的，类与类之间存在各种关系。根据类与类之间的耦合度从弱到强的排列，UML中的类图有以下几种关系：依赖关系、关联关系、聚合关系、组合关系、泛化关系和实现关系。其中泛化和实现的耦合度相等，它们是最强的。



+ 依赖关系

依赖（dependency）关系是一种使用关系，它是对象之间耦合度最弱的一种关联方式，是临时性的关联。在代码中，某个类的方法通过局部变量、方法的参数或者对静态方法的调用来访问另一个类（被依赖类）中的某些方法来完成一些职责。

在UML类图中，依赖关系使用带箭头的虚线来表示，<font color=#ff0000>虚线从使用类指向被依赖的类。单纯箭头</font>

+ 关联关系

关联（association）关系是对象之间的一种引用关系，用于表示一类对象与另一类对象之间的联系，如老师和学生、师傅和徒弟、丈夫和妻子等。关联关系是类与类之间最常用的一种关系，分为一般关联关系、聚合关系和组合关系。

关联可以是双向的，也可以是单向的。在UML类图中，双向的关联可以带两个箭头或者没有箭头的实线来表示，单向的关联用带一个箭头的实线来表示，<font color=#ff0000>箭头从使用类指向被关联的类，是实线。</font>也可以在关联线的两端标注角色名，代表两种不同的角色。

在代码中通常将一个类的对象作为另一个类的成员变量来实现关联关系。是实线

+ 聚合关系

聚合（aggregation）关系是关联关系的一种，是强关联关系，是整体和部分之间的关系，是has-a的关系。

聚合关系也是通过成员对象来实现的，其中成员对象是整体对象的一部分，但是成员对象可以脱离整体对象而独立存在。例如学校和老师的关系，学校包含老师，但如果学校停办了，老师依然存在。

在UML类图中，<font color=#ff0000>聚合关系可以用带空心菱形的实线来表示，菱形指向整体。</font>

+ 组合关系

组合（composition）关系也是关联关系的一种，也表示类之间的整体与部分的关系，但它是一种更强烈的聚合关系，是contains-a关系。

在组合关系中，整体对象可以控制部分对象的生命周期，一旦整体对象不存在，部分对象也将不存在，部分对象不能脱离整体对象而存在。例如，头和嘴的关系，没有了头，嘴也就不存在了。

在UML类图中，<font color=#ff0000>组合关系用带实心菱形的实线来表示， 菱形指向整体。</font>

+ 泛化

泛化（generalizaton）关系是对象之间耦合度最大的一种关系，表示一般与特殊的关系，是父类与子类之间的关系，是一种继承关系，是is-a的关系。

在UML类图中，<font color=#ff0000>泛化关系用带空心三角箭头的实线来表示，箭头从子类指向父类。</font>

+ 实现关系

实现（realization）关系是接口与实现类之间的关系。在这种关系中，类实现了接口，类中的操作实现了接口中所生命的所有抽象操作。

在UML类图中，<font color=#ff0000>实现关系使用带空心三角箭头的虚线来表示，箭头从实现类指向接口。</font>





# 问题记录

## 接口中的已经赋值的变量可以直接使用吗

两种方式 一种通过接口.变量，另一种通过import引入

### 补充内容

接口中的字段默认添加 static final 所以申明是必须赋予初值。接口继承接口 用implements

## map的remove方法有返回值？？？

没错，返回的是删除的值

## k -＞ (parentPath, currentChilds) -＞ {} 怎么理解

查看自己的博客



## nginx 反向代理报错404

### nginx 运行时  error.log 错误提示   request: "GET /favicon.ico HTTP/1.1",referrer: "http://192.168.175.130:9001/banyu/"

```
// 网站图标问题，添加如下内容
location /favicon.ico {
			log_not_found off;
			access_log off;
		}
```

### 无法访问 反向代理配置的url

```
location  /banyu/ {
	proxy_pass  http://192.168.175.128:8080/;
}
location /shanliang/ {
	proxy_pass http://192.168.175.130:8080/;
}
```

## postman 请求无 返回值

[参考链接](https://blog.csdn.net/qqxyy99/article/details/93876124)

修改postman中的setting，将其中的 SSL certificate verfication 改为off

##  如何下载指定版本 的openjdk

[参考步骤](https://www.cnblogs.com/jpfss/p/10936167.html)

> http://hg.openjdk.java.net/
>
> 通过访问  以上链接可以选择指定版本的  openjdk 并进行下载操作

## notepadding 中取消网址自动链接

> 通过  设置 =》 首选项 =》 其他 =》 右侧 超链接设置不启用



# 基础知识

## java 中四种引用

### 强引用

> Java中默认声明的就是强引用。只要强引用存在，垃圾回收器将永远不会回收被引用的对象，哪怕内存不足时，JVM也会直接抛出OutOfMemoryError，不会去回收。如果想中断强引用与对象之间的联系，可以显示的将强引用赋值为null，这样一来，JVM就可以适时的回收对象了

### 软引用

> 软引用是用来描述一些非必需但仍有用的对象。**在内存足够的时候，软引用对象不会被回收，只有在内存不足时，系统则会回收软引用对象，如果回收了软引用对象之后仍然没有足够的内存，才会抛出内存溢出异常**。这种特性常常被用来实现缓存技术，比如网页缓存，图片缓存等。

### 弱引用

> 弱引用的引用强度比软引用要更弱一些，**无论内存是否足够，只要 JVM 开始进行垃圾回收，那些被弱引用关联的对象都会被回收**。

### 虚引用

> 虚引用是最弱的一种引用关系，如果一个对象仅持有虚引用，那么它就和没有任何引用一样，它随时可能会被回收，在 JDK1.2 之后，用 PhantomReference 类来表示，通过查看这个类的源码，发现它只有一个构造函数和一个 get() 方法，而且它的 get() 方法仅仅是返回一个null，也就是说将永远无法通过虚引用来获取对象，虚引用必须要和 ReferenceQueue 引用队列一起使用。

### 引用队列

> 引用队列可以与软引用、弱引用以及虚引用一起配合使用，当垃圾回收器准备回收一个对象时，如果发现它还有引用，那么就会在回收对象之前，把这个引用加入到与之关联的引用队列中去。程序可以通过判断引用队列中是否已经加入了引用，来判断被引用的对象是否将要被垃圾回收，这样就可以在对象被回收之前采取一些必要的措施。
>
> 与软引用、弱引用不同，虚引用必须和引用队列一起使用。



## java  转义符号

```&#060;
&#64;   =》  @
&lt;    =》  <
&gt;    =》  >
&#060;   =》  <
&#062;  =>    >
&quot;   =>  "
```



## 解读字节码文件

### JVM 规定的字节码结构

<img src="./images/2021-01-21-1.jpg" alt="jvm中的字节码结构" style="zoom:50%;" />

### 内容介绍

#### 魔术（Magic Number）

​	所有的.class 文件的前四个字节都是魔数，魔数的固定值为：0xCAFEBABE。魔数放在文件开头，JVM 可以根据文件的开头来判断这个文件是否可能是一个.class 文件，如果是，才会继续进行之后的操作。
​	有趣的是，魔数的固定值是Java 之父James Gosling 制定的，为CafeBabe（咖啡宝贝），而Java 的图标为一杯咖啡。

#### 版本号 （version）

版本号为魔数之后的4 个字节，前两个字节表示次版本号（Minor Version），后两个字节表示主版本号（Major Version）。上图2 中版本号为“00 00 00 34”，次版本号转化为十进制为0，主版本号转化为十进制为52，在Oracle 官网中查询序号52 对应的主版本号为1.8，所以编译该文件的Java 版本号为1.8.0。

#### 常量池（Constant Pool）

紧接着主版本号之后的字节为常量池入口。常量池中存储两类常量：字面量与符号引用。字面量为代码中声明为Final 的常量值，符号引用如类和接口的全局限定名、字段的名称和描述符、方法的名称和描述符。常量池整体上分为两部分：常量池计数器以及常量池数据区，

##### 常量池计数器（constant_pool_count）

由于常量的数量不固定，所以需要先放置**两个字节**来表示常量池容量计数值。

##### 常量池数据区

数据区是由（constant_pool_count-1）个 cp_info结构组成，一个cp_info 结构对应一个常量。在字节码中共有14 种类型的cp_info，每种类型的结构都是固定的。

![14种类型的cp_info](./images/2021-01-22-1.jpg)

#### 访问标识

常量池结束之后的两个字节，描述该Class 是类还是接口，以及是否被Public、Abstract、Final 等修饰符修饰。JVM 规范规定了如下图9 的访问标志（Access_Flag）。需要注意的是，JVM 并没有穷举所有的访问标志，而是使用按位或操作来进行描述的，比如某个类的修饰符为Public Final，则对应的访问修饰符的值为ACC_PUBLIC | ACC_FINAL，即0x0001 | 0x0010=0x0011。

<img src="./images/2021-01-22-2.jpg" alt="访问标识" style="zoom: 67%;" />

#### 当前类名

访问标志后的两个字节，描述的是当前类的全限定名。这两个字节保存的值为常量池中的索引值，根据索引值就能在常量池中找到这个类的全限定名。

#### 父类名称

当前类名后的两个字节，描述父类的全限定名，同上，保存的也是常量池中的索引值。

#### 接口信息

父类名称后为两字节的接口计数器，描述了该类或父类实现的接口数量。紧接着的n 个字节是所有接口名称的字符串常量的索引值。

#### 字段表

字段表用于描述类和接口中声明的变量，包含类级别的变量以及实例变量，但是不包含方法内部声明的局部变量。字段表也分为两部分，第一部分为两个字节，描述字段个数；第二部分是每个字段的详细信息fields_info。

![字段表结构](./images/2021-01-22-3.jpg)



#### 方法表

字段表结束后为方法表，方法表也是由两部分组成，第一部分为两个字节描述方法的个数；第二部分为每个方法的详细信息。方法的详细信息较为复杂，包括方法的访问标志、方法名、方法的描述符以及方法的属性。

![方法表结构](./images/2021-01-22-4.jpg)

#### 附加属性表

字节码的最后一部分，该项存放了在该文件中类或接口所定义属性的基本信息。













## 正则表达式

### java中正则匹配

<font color=red>java中用正则表达式去分片时，如果两个分隔符中间没有内容，则会跳过而不是保存一个空的数据。</font>

>```java
>private static final Pattern NAME_SEPARATOR=Pattern.compile("\\s*[,]+\\s*");
>String[] names = NAME_SEPARATOR.split("a,b,c");
>for(String name:names){
>	System.out.println(name);
>}
>```
>
>以上，运行输出结果为：
>
>a
>
>b
>
>c

>```java
>private static final Pattern NAME_SEPARATOR=Pattern.compile("\\s*[,]+\\s*");
>String[] names = NAME_SEPARATOR.split(",,");
>for(String name:names){
>	System.out.println(name);
>}
>```
>
>以上，输出结果为空，即names 数组中没有内容

### 简单转义字符

|                            符号                             |       含义       |
| :---------------------------------------------------------: | :--------------: |
|                            `\n`                             |    代表换行符    |
|                            `\t`                             |      制表符      |
|                            `\\`                             |   代表`\`本身    |
| `\^`,`\$`,`\(`,`\)`,`\{`,`\}`,`\?`,`\+`,`\*`,`\|`,`\[`,`\]` | 匹配这些字符本身 |

### 

| 符号 |                             含义                             |
| :--: | :----------------------------------------------------------: |
| `\w` |    任一一个字母或数字或下划线，即0-9，a-z,A-Z,_中任意一个    |
| `\d` |                任一一个数字，即0-9 中任意一个                |
| `\s` |      包括 空格，制表符，换行符等空白字符的其中任意一个       |
| `\b` |             匹配一个单词便捷，即字与空格间的位置             |
| `.`  | 小数点可以匹配任意一个字符，<font color=red>除了换行符</font>，如果要匹配包括`\n`在内的所有字符，一般用`[\s\S]` |

<font color=red>如上，字母从小写变大写，代表取反操作</font>

### 特殊字符

| 符号 |           含义           |
| :--: | :----------------------: |
| `^`  | 匹配输入字符串开始的位置 |
| `$`  | 匹配输入字符串结尾的位置 |
| `?`  |     匹配0个或1个字符     |
| `*`  |  匹配0个或1个或多个字符  |
| `+`  |    匹配一个或多个字符    |

###  括号 ，匹配其中所有内容

+ 括号内 `^`  表示对之后的取反，即不包含
+ 括号内`-` 表示范围内均包括

### 三种括号的作用

#### 圆括号（）

() 是为了提取匹配的字符串。表达式中有几个()就有几个相应的匹配字符串。

#### 中括号[]

[]是定义匹配的字符范围。比如 [a-zA-Z0-9] 表示相应位置的字符要匹配英文字符和数字。[\s]表示空格或者号。

>```java
>Pattern p=Pattern.compile("[.]+");
>Matcher m1=p.matcher(".");  
>boolean b1=m.matches();  //1
>Matcher m2=p.matcher("");  
>boolean b2=m.matches();  //2
>```
>
>以上，1处的结果为true，2处的结果为false

#### 大括号{}

{}一般用来表示匹配的长度



## JVM 参数

### JVM 堆大小限制

+ 相关操作系统的数据类型：32bit、64bit
+ 系统的可用虚拟内存限制
+ 系统同可用物理内存限制

32bit 的系统，一般限制在1.5G~2G。64bit的操作系统对内存无限制。

[参考链接](https://www.cnblogs.com/fightingcode/p/11232694.html)

### 常用的JVM参数

+ `-Xmx3550m`： 设置JVM最大可用内存为3550M
+ `-Xms3550m`:  设置JVM 初始内存为 3550M。 <font color=red>此值可以设置与`-Xmx`一样，用来避免每次垃圾回收完成后JVM重新分配内存。</font>
+ `-Xmn2g`： 设置年轻代 大小为2G。整个堆大小=年轻代大小+年老代大小+持久代大小。持久代一般固定为64m，所以增大年轻代后，将会减少年老代大小。此值对系统性能影响较大，Sun官方推荐配置为整个堆的3/8。
+ `-Xss128k`：  设置每个线程的堆栈大小。JDK5.0之后每个线程堆栈大小为1M。以前每个线程堆栈大小为256K。需要根据应用的线程所需内存大小进行调整。在相同物理内存下，减小这个值能生成更多的线程。但是操作系统对一个进程内的线程数还是有限制的，不能无限生成，一般为3000-5000。
+ `-XX:NewRatio=4`:   设置年轻代（包括Eden和两个Survivor区）与年老代的比值（除去持久代）。设置为4，则年轻代与年老代所占比值为1:4
+ `-XX:SurvivorRatio=4`:  设置年轻代中Eden区和Survivor区的大小比值。设置为4，则两个Survivor区和一个Eden区的比值为2:4，一个Survivor区占整个年轻代的1/6。
+ `-XX:MaxPermSize=16m`： 设置持久代大小为16m
+ `-XX:MaxTenuringThreshold=0`： 设置垃圾最大年龄，如果设置为0的话，则年轻代对象不经过Survivor区，直接进入年老代。对于年老代比较多的应用，可以提高效率。如果将此值设置为一个较大值，则年轻代对象会在Survivor区进行多次复制，这样可以增加对象在年轻代的存活时间，增加在年轻代被回收的概率。

#### 收集器

+ `-XX:+UseParallelGC`：  选择垃圾收集器为并发收集器。此配置仅对年轻代有效。即上述配置下，年轻代使用并发收集，而年老代仍旧使用串行收集。
+ `-XX:ParallelGCThreads=20`：  配置并行收集器的线程数，即同时有多少个线程一起进行垃圾回收。此值最好配置与处理器数目相同。
+ `XX:+UseParallelOldGC`：配置年老代垃圾收集方式为并行收集。JDK6.0 支持对年老代并行收集。
+ `-XX:MaxGCPauseMills=100`：  设置每次年轻代垃圾回收的最长时间，如果无法满足此时间，JVM会自动调整年轻代的大小，以满足此值。
+ `-XX:+UseAdaptiveSizePolicy`： 设置此选项后，并行收集器会自动选择年轻代区大小和相应的Survivor区比例，以到达目标系统规定的最低响应时间或收集频率等，此值建议使用并行收集器时一直打开。



## java  自定义对象复制问题

### 测试 自定义类对象定义

```
package com.yichen.jvmtest.dataPosition;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/1 17:04
 */
public class Person {
    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Person person=new Person();
        person.setName(this.name);
        person.setAge(this.age);
        return person;
    }
}

```

### 测试 方法

```
public class Test {
    public static void main(String[] args)throws Exception {
        Person person=new Person("yichen",17);
        Person person1=(Person)person.clone();
        System.out.println(person==person1);
        Person person2=person;
        person.setName("banyu");
        person.setAge(18);
        System.out.println(person==person1);
        System.out.println(person==person2);
        System.out.println(person);
        System.out.println(person1);
        System.out.println(person2);
    }
}
```

### 测试结果

<img src="./images/2021-02-01-1.jpg" alt="测试结果" style="zoom:67%;" />

### 总结

复杂对象如果只是单纯的通过`=`复制，那么各个变量之间访问的还是同一块内存区域，如果通过实现`clone`方法，并且通过该方法来创建，那么两个对象就是不相关的。



## java 对象构建 顺序问题

+ 调用对应类的构造函数之前  先初始化类中 静态代码块以及 静态属性
+ 现根据入参 选择匹配的类的构造函数
+ 进入匹配的构造函数
+ 对有默认赋值的  类属性进行初始化赋值以及重写（如int 类型初始化赋值是0，但是如果一开始定义该类型时设置它为3，则此时会将该值由0改为3）
+ 如果 类属性 是 其他非基本数据类型的 定义的，则调用对应的构造方法，流程如上重复

### 测试代码

#### 测试顺序主类

```
public class TestOrder {
    private String name;
    private int age=10;
    private boolean state=true;
    private Student student=new Student();

    public String sex;

    static{
        System.out.println("test order has start-up");
    }

    private static String POSITION="test position";


    public TestOrder(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "TestOrder{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", state=" + state +
                ", student=" + student +
                ", sex='" + sex + '\'' +
                '}';
    }
}
```

#### 测试顺序  副类

```
public class Student {
    private String name="yichen";
    private Integer age=18;
    private String certId;
    static{
        System.out.println("student class static field");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public Student(String name, Integer age, String certId) {
        this.name = name;
        this.age = age;
        this.certId = certId;
    }
    public Student(){}

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", certId='" + certId + '\'' +
                '}';
    }
}
```

#### 调试方法 入口

```
public class Test {
    public static void main(String[] args) {
        TestOrder order=new TestOrder("yichen",18,"男");
        System.out.println(order);
    }
}
```



## 看 openjdk

### object.wait 和 object.notify

[参考链接](https://blog.51cto.com/13981400/2374217)



## 编码格式

[参考链接](http://www.ruanyifeng.com/blog/2007/10/ascii_unicode_and_utf-8.html)

### unicode概述

unicode ，占据2个字节，可以表示65535 个字符。

> Unicode 只是一个符号集，它只规定了符号的二进制代码，却没有规定这个二进制代码应该如何存储。

<img src="./images/2021-02-23-1.jpg" alt="常见的前128个unicode 对应表" style="zoom: 80%;" />

### ascll 概述

每个字符占用一个字节，ascll 可以表示最大字符数是255个。适用于英文，但不适合中文



### gb2312

简体中文的常用编码方式



### utf-8 

<font color=red>它是unicode 的一种实现</font>

> UTF-8 就是在互联网上使用最广的一种 Unicode 的实现方式。其他实现方式还包括 UTF-16（字符用两个字节或四个字节表示）和 UTF-32（字符用四个字节表示）

编码规则如下：

1、对于单字节的符号，字节的第一位设为`0`，后面7位为这个符号的 Unicode 码。因此对于英语字母，UTF-8 编码和 ASCII 码是相同的。

2、对于`n`字节的符号（`n > 1`），第一个字节的前`n`位都设为`1`，第`n + 1`位设为`0`，后面字节的前两位一律设为`10`。剩下的没有提及的二进制位，全部为这个符号的 Unicode 码。

<img src="./images/2021-02-23-2.jpg" alt="unicode与utf-8转换表" style="zoom:67%;" />

## java 中对象比较

###  == 比较对象

> == 比较的是两个对象的 地址值，只有两个对象的地址一样才返回true

### 非基本类型

> 备注，基本类型有如下8种
>
> byte,short,char,int ,long,float,double,boolean  <font color=red>都被final修饰，不可重写</font>

<font color=red> 非基本类型对象比较，与基本类型类似，但是它们有一个优势，即虽然通过`==`判断的仍然是两者的地址值，但是，如果使用`eqauls`方法去判断，那么此时我们可以通过重写该方法来实现我们自定义的对象比较规则</font>

### map中的key 值比对

```
if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
```

> 比较逻辑如上，首先比较的是原有key和插入key的hash值，这里调用了对象的hashcode方法
>
> 之后则 有两种方法核实是否为同一个key：
>
> 1、直接通过  两个key的地址值比对
>
> 2、通过equals方法比较两个key



## 数据库

### 将 `.sql` 文件中的数据插入到数据库中

> source /tmp/phone202103-mysql.sql     // 后面是对应的sql 文件位置
> <font color=red>这里需要在cmd命令行执行，而不能用三方工具，如navicat premiun</font>

### java jdbc数据库报错  closing inbound before receiving peer's close_notify

> 在数据库的url 加上    &useSSL=false





# 生活技巧

## ps

### 实现圆弧字

+ 打开 ps
+ 选中椭圆工具 如下图所示

![展示图](./images/2021-01-16-1.jpg)

+ 用鼠标左键按住画圆，按住shift 使得圆方正，按住tab 控制圆的位置移动，画好后释放
+ 选中文本工具，如下图所示

![文本工具](./images/2021-01-16-2.jpg)

+ 将鼠标移动到之前的圆上，当鼠标变为波浪线时点击即可进行弧形文字的输入

### 实现画空心圆并加粗

+ 选择选框工具中的椭圆选框工具，如下图所示

![椭圆选框工具](./images/2021-01-16-3.jpg)

跟上面一样的步骤调整位置画出正圆，然后鼠标右击该选框，选择描边，设定指定的宽度即可



## window   自定义屏保

[参考链接](https://www.maxiaobang.com/5885.html)

### 步骤

+ 下载  `PotPlayer播放器`   播放器
+ 打开，按 `F5`   进入选项配置界面，点击屏保、安装屏保
+ 自动打开屏幕保护程序设置界面，**选择PotScreenSaver屏保程序 -> 设置**
+ 设置屏保视频即可





# 知识盲区

##  http-client 

版本为4.5，使用`CloseableHttpClient` 发送 请求时，少量请求可行，频繁发送出现异常如下

```
// 异常信息
java.lang.IllegalStateException: Connection pool shut down
// 错误提示定位是在
CloseableHttpClient 类的  execute 方法执行过程中出错
```

[参考文章](http://www.mamicode.com/info-detail-2297359.html)

在构建 `CloseableHttpClient` 对象时设置 `setConnectionManagerShared` 属性为  `true`

![定义CloseableHttpClient](./images/2021-01-27-1.jpg)





# mongodb

[安装步骤](https://blog.csdn.net/dandanfengyun/article/details/95497728)

## 常用命令

>net start mongodb   // 开启mongodb 服务
>
>net stop mongodb  // 关闭mongodb服务
>
>use admin   // 如果没有对应的数据库，则默认自动创建





# unbutu 

## 设置root账户的密码

> sudo passwd root
>
> // 输入当前用户的密码
>
> // 输入两次root账户的密码

## 查看磁盘信息

> df -h 

## redis

### redis  安装

+ 执行命令  `sudo apt-get install redis-server` 安装

### redis 运行命令

>redis-cli   接入redis  客户端
>
>quit 退出redis 客户端
>
>shutdown  关闭redis
>
> 
>
>redis-server  /etc/redis/redis.conf    // 启动redis 服务器



### 操作命令

>keys *   // 查看所有的key
>
>select 6 使用6号数据库  <font color=red> 在jedis中如果设置了    redis_database=6 ，如果数字大于六，到时候查询数据的时候可能存在查不到的情况，原因是保存在不同的数据库中，可以用该命令切换数据库</font>
>
>

### 设置redis  访问 auth 密码

[参考文章](https://blog.csdn.net/u013829518/article/details/82621694)

#### 临时设置 重启后失效

> config get requirepass    //  查看有没有设置密码
>
> config set requirepass  yichen   // 设置密码为yichen
>
> auth yichen    // 连接redis 客户端后 需要认证才能  查询数据信息
>
> redis-cli -a yichen shutdown   // 关闭redis 是也需要该密码

#### 永久设置

> 修改  redis.conf   中的 requirepass 的值为你想要设置的密码

### 问题记录

#### apt-get安装软件Unable to locate package错误解决办法

> 这个错误一般是因为软件源未更新造成的，于是采用命令：sudo apt-get update 来更新软件源。

#### 安装好 redis 后无法启动

[参考链接](https://blog.csdn.net/weixin_43968936/article/details/102809536)

>ps agx|grep redis      查看redis 启动状态
>
><font color=red> 启动失败的原因是：默认主机上禁用了IPv6，而Ubuntu的redis-server软件包（版本5：4.0.9-1）附带了：绑定127.0.0.1 :: 1
>所以，通过在配置文件中注释掉绑定127.0.0.1::1</font>
>
>sudo vim /etc/redis/redis.conf   将其中的127.0.0.1::1 注销掉。
>
>sudo service redis-server start  启动redis  需要使用root 权限账号
>
>sudo service redis-server status  查看redis 状态
>
>ps agx|grep redis  查看redis 进程
>
>netstat -ntpl|grep 6379  查看redis 的端口是否在监听。

#### 启动redis-server 报错  Fatal error, can't open config file '/etc/redis/redis.conf'
failed

> whereis redis-server   // 找到redis-server 所在文件夹，到该文件夹
>
> redis-server  /etc/redis/redis.conf    //  以指定地方的配置文件运行  redis-server
>
> service redis-server status    // 查看启动状态





## ssdb 

### 步骤

[参考文章](https://blog.csdn.net/zwjyyy1203/article/details/89386301)



>wget --no-check-certificate https://github.com/ideawu/ssdb/archive/master.zip  // 下载
>
>unzip master    // 解压
>
>cd ssdb-master  //进入目录
>
>make //编译
>
>sudo make install  //  默认安装在  /usr/local
>
>sudo make install PREFIX=/your/directory     // 指定安装位置

### 常用命令

> ./ssdb-server ssdb.conf     // 启动
>
> ./ssdb-server ssdb.conf -s stop    //关闭

### 问题

#### make 报错 autoconf required! install autoconf first

`apt install autoconf` 因为没有  `autoconf` 而报错

#### cannot stat 'ssdb-server': No such file or directory

> 确认上面是不是还有错误如下
>
> configure: error: no acceptable C compiler found in $PATH

如果有，则表示需要安装 c的编译器，执行如下命令

> apt install gcc

#### 如果一直安装不了，尝试安装就版本

>wget --no-check-certificate https://github.com/ideawu/ssdb/archive/stable-1.9.5.zip



## mysql

### 安装

+ apt-cache search mysql | grep mysql-server    // 查看可以安装的版本
+ sudo apt-get install mysql-server-查询到的版本    // 安装mysql
+ service mysql start   // 启动mysql服务
+ sudo cat /etc/mysql/debian.cnf   // 第一次登陆查看账号密码
+ mysql -u  账号  -p密码    // 登陆mysql

### 第一次 修改root 密码

>ALTER USER 'root'@'localhost'  IDENTIFIED WITH mysql_native_password  BY 'root';
>
><font color=red> mysql 8后修改密码的方式</font>



>update mysql.user set authentication_string=password('root') where user='root' and Host ='localhost';
>update user set plugin="mysql_native_password"; 
>flush privileges;
>
><font color=red> mysql 8之前修改密码的方式</font>

### 遇到的问题

#### apt 安装后无法通过  service mysql start 启动

<font color=red> 子系统和主系统公用端口号，需要把主系统的mysql关闭。

## nacos

### 安装步骤

[参考链接](https://blog.csdn.net/weixin_41317520/article/details/90027449)

> 下载 nacos 的zip，解压
>
> 在conf 中修改配置文件
>
> sh start.sh -m standalone    // 启动 nacos
>
> http://127.0.0.1:8848/nacos/index.html   // 登陆nacos 后台界面

虽然nacos有内置mysql数据库，这里一般使用自己的数据库。其中数据库表定义在`nacos/distribution/conf` 路径下的 `nacos-mysql.sql`。同时修改该路径下的`application.properties`，修改其中如下参数

```
spring.datasource.platform=mysql
db.num=1
db.url.0=jdbc:mysql://ip:port/data_name?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user.0=root
db.password.0=root
```

### 问题记录

#### 启动 nacos 执行  sh start.sh -m standalone  后仍以集群模式启动，而不是单例

[参考链接](https://blog.csdn.net/wgc0802402/article/details/88171755)

执行  `bash start.sh -m standalone` 以代替



## activemq

### 安装

>下载jar包，地址如下      [下载地址](https://activemq.apache.org/components/classic/download/)
>
>解压      tar -zxvf apache-activemq-5.16.1-bin.tar.gz
>
>设置 java  jdk   ，修改安装目录下的bin文件夹下的env文件，在<font color=red>JAVACMD="auto"</font> 上添加  <font color=red>JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"</font>
>
>启动   ./activemq start
>
>打开 管理台    http://localhost:8161/admin/
>
>登陆密码  保存在   conf/jetty-realm.properties 中
>
><img src="./images/2021-03-15.jpg" alt="示例" style="zoom: 50%;" />



## rocketmq



## maven

[下载地址](https://maven.apache.org/download.cgi)

> //将下载的zip 文件解压。
>
> //配置 全局配置文件，添加如下内容
>
> export M2_HOME=/usr/local/apache-maven-3.5.3
> export PATH=${M2_HOME}/bin:$PATH
>
> //使修改的配置生效
>
> source /etc/profile
>
> // 查看是否配置成功
>
> mvn -v

### 简单方法

> apt install maven

## java  jdk

### 安装  

<font color=red>默认安装路径  `/usr/lib/jvm/java-8-openjdk-amd64` </font>

> apt-get install openjdk-8-jdk  
>
> java -version

### 卸载

[参考链接](https://www.cnblogs.com/dengtang/p/11644751.html)

> apt-get remove openjdk*

## 安装openjdk 7u4 所依赖的东西

>sudo apt-get install build-essential gawk m4 openjdk-6-jdk libasound2-dev libcups2-dev libxrender-dev xorg-dev xutils-dev xllproto-print-dev binutils libmotif3 libmotif-dev ant

# 数学知识

## 概率论

### 二项分布

比如你向 3 家公司投递简历，会被其中一家录用的概率分布就是二项分布，也就是说二项分布解决的是发生次数固定、求成功次数概率的事件，但是要保证这些事件的结果只有两种结果，也就是非 A 即 B。

更形象的例子就是抛硬币，抛出 10 次硬币，求不同正面朝上的次数概率就符合二项分布，而想要求出具体的数值，就要用到二项分布的公式：

![公式](./images/2021-03-03-2.jpg)

其中，p 表示概率，C 表示排列组合，n 表示试验次数，x 表示成功次数。

### 几何分布

几何分布与二项分布非常相似，唯一的区别就是几何分布是求试验几次才能获得第一次成功的概率，比如扔硬币扔到第几次才能第一次抛出正面的概率分布，即为几何分布。

因为二项分布是固定了试验次数，而几何分布是固定了成功次数，因此几何分布就是相当于二项分布成功累计 N 次的结果。

比如我在网上投递简历，失败一次就去另一家投递，求第几次投递时我才能第一次通过简历的概率，就符合几何分布，其计算公式为：

![计算公式](./images/2021-03-03-1.jpg)

### 泊松分布

与几何分布和二项分布不同的是，泊松分布是在一定的时间内，某个事件发生 N 次的概率分布，比如我一个月内闯红灯 30 次的概率、半年内旅游 10 次的概率。

### 正态分布

<img src="./images/2021-03-03-3.jpg" alt="正态分布图" style="zoom:67%;" />

正态分布是指越靠近中间取值的值越多，而越远离中间取值出现的次数越少，如全校同学的身高、平均每个月的开销等。

正态分布的平均值叫作 μ，它决定了中心点的位置，标准差 σ 决定数据的集中度。那么正态分布有什么用呢？

这时候就要涉及正态分布一个非常重要的性质：中心极限定理。也就是说，如果统计对象是大量独立的随机变量，那么这些变量的平均值分布就会趋向于正态分布，不管原来它们的概率分布是什么类型！

比如统计某地区人的身高，每个人的身高都是独立的随机事件，但是当统计量足够大的时候，这些数据的平均值分布就会呈现出正态分布的特点。这就是中心极限定理的魅力所在，它几乎适用于自然界中任何的随机变量分布。



## 相关性分析

相关性分析其实是进行回归分析的必要前提工作。通俗地讲，相关性分析是定性、回归分析是定量。我们在确定衡量某个变量时一定要遵循“先定性、再定量”，所以要先进行相关性分析，再做回归分析。

<img src="C:\Users\E480\AppData\Roaming\Typora\typora-user-images\image-20210305094311204.png" alt="步骤" style="zoom: 50%;" />

### 过程

分析两个因素是否有关联，一般通过因素拆解，拆解到有相关性因素时，用AB测试（即控制变量法），改变该因素发现是否发生相关的变动，如果发现没有相关性，则可以用前面拆解出来的因素，然后进行再次验证。

## 回归分析

### 建立回归模型的步骤

> 分析目的 =》 确定变量  =》 建立回归模型   =》回归方程检验  =》  建立回归公式



# meven 

## 常用注解以及对应的maven依赖

### @RestController

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### @ConfigurationProperties(prefix = "user")

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

一般在该注解上还要添加  @Component  ，将该类加入bean 或者

### JSONObject 

```
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.69</version>
</dependency>
```

####  常用操作

```
// 将字符串转为  JSONObject
String result=nulll;
JSONObject jsonResult=JSONObject.parseObject(result);

// 将 对象转为 JSONObject
Person body=new Person():
JSONObject.toJSONString(body)

//将map转为json对象
Map<String,Object> res=new LinkedHashMap<>();
res.put("code-describe","解码失败");
JSONObject jsonObject=new JSONObject(res);
```

### 判空工具类

```
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
</dependency>
```

### hutool 

```
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>5.5.7</version>
</dependency>
```



### 加密工具    DigestUtils.md5Hex("shanliang28");

```
<dependency>
    <groupId>commons-codec</groupId>
    <artifactId>commons-codec</artifactId>
</dependency>
```

## 打包常用命令

>mvn install -Dmaven.test.skip=true   // 跳过test 打包
>
>mvn -q clean install   //隐藏 打印  info 的相关信息



# JAVA

##  spring 路径匹配规则

+ `？` 匹配一个字符
+ `*` 匹配零个或多个字符
+ `**` 匹配路径中的零个或多个目录
+ `{spring:[az]+}` 与正则表达式   `[az]+` 匹配，并将其作为名为 `spring` 的路径变量



## 注解使用

### 后端用map接收前端请求

`@RequsetParam`



## CORN 表达式

### 语法格式

+ Seconds Minutes Hours DayofMonth  Month  DayofWeek Year
+ Seconds Minutes Hours DayofMonth Month DayofWeek

#### 对应意思

秒 分 小时 月份中的日期 月份 星期中的日期  （年份）

### 字段含义

|           字段           |            允许值             |  允许的特殊字符   |
| :----------------------: | :---------------------------: | :---------------: |
|       秒（Second）       |          0~59的整数           |     ， - * /      |
|      分（Minutes）       |          0~59的整数           |     ， - * /      |
|      小时（Hours）       |          0~23的整数           |     ， - * /      |
|    日期（DayofMonth）    |          1~31的整数           | ， - * ？ / L W C |
|      月份（Month）       |     1~12的整数或者JAN-DEC     |     ， - * /      |
|    星期（DayofWeek）     | 1~7的整数或者SUN-SAT（1=SUN） | ， - * ？ / L C # |
| 年（可选，留空）（Year） |           1970~2099           |     ， - * /      |

### 符号含义

+ `*`  : 表示匹配该域的任意值。假如在Minutes域使用 `*` ,即表示每分钟都会触发事件
+ `?`  ： 只能用在  `DayofMonth` 和  `DayofWeek` 两个域，它也匹配域的任意值，但不会实际生效。**因为DayofMonth和DayofWeek会相互影响。例如想在每月的20日触发调度，不管20日到底是星期几，则只能使用如下的写法： 13 13 15 20 * ？。其中最后一位只能用`?` ，而不能使用  `*`  ,如果使用  `*` 表示不管星期几都会触发。**
+  `-` :  表示范围，例如在 `Minutes` 域使用  5-20 ，表示从5分到20分钟每分钟触发一次
+ `/` ： 表示起始时间开始触发，然后每个固定时间触发一次，例如在 `Minutes` 域使用  5/20 ，则意味着5分钟触发一次，而25,45等分别触发一次。
+ `,` :  表示列出枚举值。例如在`Minutes` 域使用  5,20    ，则意味着5和20分每触发一次
+ `L` ： 表示最后，只能出现在  `DayofWeek` 和 `DayofMonth` 域，如果在  `DayofWeek`域使用  5L ，意味着在最后一个星期四触发。
+ `W` ： 表示有效工作日（周一至周五），只能出现在 `DayofMonth` 域，系统将在离指定日期最近的有效工作日触发事件。例如在`DayofMonth` 使用 5W  ，如果5日是星期六，则将在最近的工作日：星期五，即四日触发。如果5日是星期天，则在6日（周一触发）。如果5日在星期一到星期五中的一天，则就在5日触发。**另外一点，W的最近寻找不会跨过月份**。
+ `LW` ：这连个字符可以连用，表示在某个月最后一个工作日
+ `#` : 用于确定每个月第几个星期几，只能出现在  `DayofMonth` 域，例如在 `4#2` 表示某月的第二个星期三













