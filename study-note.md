# 常用软件

# postman

> 用于模拟接口请求

## apiPost

> 国内版 postman

## 101editor

> 可以查看字节码文件，可以加在模板

## virtual box

> 虚拟机

## vmware  

> 虚拟机



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









# linux 命令

## 问题记录

### $'\r': command not found

[参考解决办法](https://blog.csdn.net/bird3014/article/details/93122974)

> 是因为windows系统的文件换行是  `\r\n` 而 unix系统是 `\n`
>
> 打开bash文件    =>   esc  :set ff=unix   =>  :wq

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

## 功能脚本

### 文件归档



## 页面切换命令

#### 全屏导航

+ ctrl + F 向前移动一屏
+ ctrl + B 向后移动一屏
+ ctrl + D 向前移动半屏
+ ctrl + U 向后移动半屏





## 便捷操作

+ 快速清空当前输入的命令      ctrl + c



## 使用到的命令

> //  .gz文件解压  hello.gz  源文件消失
>
> gunzip  hello.gz
>
> // 解压保留源文件
>
> gunzip -c  hello.gz >  hello

## 有用命令

+ 根据 `pid`查询进程启动时间

```java
ps -eo pid,user,comm,lstart,etime | grep mysql
```



### wc

==用来计算字数==

+ -c 或 --bytes 或 --chars  显示字节数
+ -l  或 --lines 显示行数
+ -w 或 --words 显示字数**以空格划分**
+ --version 显示版本

### 根据关键字过滤并且指定查看宽度

+ 查看关键字上的几行   cat web.log | grep -B 4 one
+ 查看关键字下的几行   cat web.log | grep -A 4 one
+ 查看关键字前几行和后几行  cat web.log | grep -C one
+ <font color=red> 使得查询出来的字段变红，容易识别    cat a.txt | grep hello --color=auto 即可</font>



### vi下 关键字查询

按下`/` ，然后输入关键字，最后按下回车即可，之后按 `n`  下一个，按`N` 上一个



## vim 常用命令

> yy      // 复制当前光标所在行
>
> p   //   粘贴到下一行，原来的往下顺移
>
> u  // 撤回操作
>
> esc  =》  :set number // 显示行数      :set nonu  // 不显示行数

###  !pwd

退出vim窗口   执行pwd命令



### 缩小 vim 窗口

+ ​    :sh    缩小当前vim  窗口
+ ctrl+d  返回vim窗口



##  系统命令

### 查看unbutu端口开放情况

```java
// 查看指定端口开放情况    =>   unbutu 不可用
lsof -i:5672   // 查看rabbitmq 端口开放情况
```

### 打印环境变量

```
printenv
```



###  根据 command 批量删除进程

[参考文章](https://my.oschina.net/u/4255280/blog/3781794)

```java
kill -9 `ps -ef |grep xxx|awk '{print $2}'      // xxx 中输入 command 中公有的名词
//  rabbitmq 中存在的 启动在关闭后 后台有很多 daemon
// awk 用于输出某一列    {print $2} 表示输出第二列，即 PID列
kill -9 `ps -ef | grep daemon | awk '{print $2}'`  
//  多余选项  grep -v root  表示排除 root 字段
    
//   查询 java 进行对应的pid，排除查询的命令
// 正常执行 grep ，后台都会有一个  grep --color 的进行，需要把它排除
kill -9 `ps -ef |grep xxx| grep -v grep |awk '{print $2}'
```



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

###  查看服务列表

> service --status-all

### 查看系统使用的  init system  类型

> ps --no-headers -o comm 1

- `systemd` - select the **systemd (systemctl)** tab below.
- `init` - select the **System V Init (service)** tab below.

## 内存监控

### atop

### free

> free -m  // 以M为单位展示

## apt

+ 模糊查 可以安装的

```java
apt search *top
```

## sftp 连接

> sftp -oPort=22 root@127.0.0.1

## grep 命令

### 单词准确匹配

> //a.txt内容如下
>
> name = yichen
> age = shanliang
> sex = gril
>
> grep -w 'e' a.txt    //  显示空
>
> grep -w "name"  // 显示   name = yichen
>
> //  同类型 通用查询
>
> grep -C 10 "name" *.log

## awk 命令

==默认分隔符为空格或者 tab键==

[参考文章](https://www.cnblogs.com/ggjucheng/archive/2013/01/13/2858470.html)

### 常用变量

> ARGC               命令行参数个数
> ARGV               命令行参数排列
> ENVIRON            支持队列中系统环境变量的使用
> FILENAME           awk浏览的文件名
> FNR                浏览文件的记录数
> FS                 设置输入域分隔符，等价于命令行 -F选项
> NF                 浏览记录的域的个数
> NR                 已读的记录数
> OFS                输出域分隔符
> ORS                输出记录分隔符
> RS                 控制记录分隔符

### 起头 结尾

> // 添加 起头和即为
>
> cat /etc/passwd |awk  -F ':'  'BEGIN {print "name,shell"}  {print $1","$7} END {print "blue,/bin/nosh"}'
>
> // 结果
>
> name,shell
> root,/bin/bash
> daemon,/bin/sh
> bin,/bin/sh
> sys,/bin/sh
> ....
> blue,/bin/nosh

### 命令

> //  查看 a.txt ，以 | 分隔，获取第一个  =》  第一个为空则显示空。
>
> cat a.txt | awk -F '|' '{print $1}'

## top命令 介绍

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

#### cpu颜色

> 绿色：CPU使用时间或者说CPU的占用比例
>
> 红色：内核线程
>
> 蓝色：低优先级线程

#### mem 颜色

> 蓝色：缓冲区（buffer)
>
> 绿色：已使用的内存
>
> (橘)黄色：高速缓存(cache)

## tar 命令

[参考链接](https://blog.51cto.com/11934539/2058466)

###  命令语法
tar [选项...] [FILE]...

### 选项详解
#### 主要选项:
-A, --catenate, --concatenate 追加 tar 文件至归档
-c, --create 创建一个新归档
-d, --diff, --compare 找出归档和文件系统的差异
--delete 从归档(非磁带！)中删除
-r, --append 追加文件至归档结尾
-t, --list 列出归档内容
--test-label 测试归档卷标并退出
-u, --update 仅追加比归档中副本更新的文件
-x, --extract, --get 从归档中解出文件
#### 压缩选项
-a, --auto-compress 使用归档后缀名来决定压缩程序
-I, --use-compress-program=PROG ，通过 PROG 过滤(必须是能接受 -d 选项的程序)
-j, --bzip2 通过 bzip2 压缩归档
-J, --xz 通过 xz 压缩归档
-z, --gzip, --gunzip, --ungzip 通过 gzip 压缩归档
-Z, --compress, --uncompress 通过 compress 压缩归档
#### 辅助选项
-v, --verbose 详细地列出处理的文件
-f, --file=ARCHIVE 使用归档文件或 ARCHIVE 设备，**该选项后必须接*打包的.tar**



### 参考示例

>  tar -cvf  total.tar --exclude test/user.txt  test   // 将test 文件夹打包，排除其中的hello.txt ，打包的产生的文件名叫  total.tar.gz
>
>  tar -tf total.tar   //  查看tar包中的内容
>
> tar -xvf total.tar  // 解压  -cvf 加压的文件，<font color=red>注意，这里解压会覆盖原始文件并且没有提示</font>
>
> 







## crul命令

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

## ionice

[参考文章](http://linux.51yip.com/search/ionice)

```java
命令格式：
ionice [[-c class] [-n classdata] [-t]] -p PID [PID]…
ionice [-c class] [-n classdata] [-t] COMMAND [ARG]…

参数说明：
-c class ：class表示调度策略，其中0 for none, 1 for real time, 2 for best-effort, 3 for idle。
-n classdata：classdata表示IO优先级级别，对于best effort和real time，classdata可以设置为0~7。
    数值越小，优先级越高
-p pid：指定要查看或设置的进程号或者线程号，如果没有指定pid参数，
    ionice will run the listed program with the given parameters。
-t ：忽视设置优先级时产生的错误。
COMMAND：表示命令名


```

## link

[参考文章](https://www.linuxprobe.com/soft-hard-links-comments.html)

### 硬链接

硬链接是通过索引节点进行的链接。在Linux中，多个文件指向同一个索引节点是允许的，像这样的链接就是硬链接。硬链接只能在同一文件系统中的文件之间进行链接，不能对目录进行创建。如果删除硬链接对应的源文件，则硬链接文件仍然存在，而且保存了原有的内容，这样可以起到防止因为误操作而错误删除文件的作用。由于硬链接是有着相同 inode 号仅文件名不同的文件，因此，删除一个硬链接文件并不影响其他有相同 inode 号的文件。

```java
link oldfile newfile 
ln oldfile newfile
```

###  软连接

软链接（也叫符号链接）与硬链接不同，文件用户数据块中存放的内容是另一文件的路径名的指向。软链接就是一个普通文件，只是数据块内容有点特殊。软链接可对文件或目录创建。

软链接主要应用于以下两个方面：一是方便管理，例如可以把一个复杂路径下的文件链接到一个简单路径下方便用户访问；另一方面就是解决文件系统磁盘空间不足的情况。例如某个文件文件系统空间已经用完了，但是现在必须在该文件系统下创建一个新的目录并存储大量的文件，那么可以把另一个剩余空间较多的文件系统中的目录链接到该文件系统中，这样就可以很好的解决空间不足问题。删除软链接并不影响被指向的文件，但若被指向的原文件被删除，则相关软连接就变成了死链接。

```java
ln -s old.file soft.link
ln -s old.dir soft.link.dir
```

## ls

### -i

显示 inode 信息

```java
// 显示当前目录文件，附带 inode信息
ls -li
// 查看对应 inode 文件所在位置，当前目录
find . inum 4222124650801021
```

### -lh

> 以 K M G 等单位展示文件大小

## tree

```shell
// 树形展示目录
tree /project
```

## more

```java
//  空格向下翻页
// enter 下移一行
//  shift + pageUp 向上翻页
//  shift + pageDown  向下翻页
```

## less

```java
//  空格向下翻页
//  b 向上翻页
// enter 下移一行
//  shift + pageUp 向上翻页
//  shift + pageDown  向下翻页
```

### less 和 more 区别

[less、more、most差别](https://www.baeldung.com/linux/more-less-most-commands)

> 1、less 能回退
>
> 2、支持文件格式
>
> 3、能够设置 mark 回退  设置标记  m + 标记值    返回标记  ' + 标记值
>
> 4、能够监控更新内容  less +F /var/log/syslog

## file 判断文件类型

### 参数

```bash
-b  # 列出识别结果时，不显示文件名称
-c  #  详细显示指令执行过程，便于排错或分析程序执行的情况
-f <文件名称>  #  指定文件名称，其内容有一个或多个文件名称时，让file依序辨识这些文件，
	格式为每列一个文件名称
-L  #  直接显示符号连接所指向的文件的类型
-m <魔法数字文件> #  指定魔法数字文件
-v  # 显示版本信息
-z  # 尝试去解读压缩文件的内容
```

### 示例 

> file  jar

## bash 脚本命令

### 重要-必看

> 1、赋值 = 号左右不要加空格 
>
> 2、函数调用 后返回结果 通过 $? 获取， ==> 注意，这里需要该调用函数后立即获取，如果中间有echo 等操作后，是获取不到了的
>
> 3、if 后面必须有then，不能有其他语句，不然会只执行该语句

[归纳](https://blog.csdn.net/h70614959/article/details/8985165)

[入门文档](https://www.w3cschool.cn/bashshell/bashshell-n2xd37ig.html)

### 查看系统支持的bash，以及 bash的绝对路径

```java
// 查看支持的bash
cat /etc/shells
// 查看  bash  绝对路径
which bash
```

### bash 文件系统

```java
//  ls -l 可以查看文件目录
// 一共有7列，意义如下
1、代表文件类型及权限，每个文件均以类型开头，接着指定访问权限。以特定字符表示：
    常规文件（-）
    特殊档案（c）
    命名管道（p）
    块设备（b）
    套接字（s）
    目录（d）
    链接（l）
2、代表存储块的数量。
3、代表文件的所有者或具有管理权限的超级用户。
4、代表所有者、超级用户组
5、代表文件大小
6、代表文件的最后修改日期（具体到分钟）
7、代表文件或目录的名称
```

### 更改权限

```java
//  基本格式
chmod [class][operator][permission] file_name
chmod [ugoa][+or-][rwx] file_name
    
class由u（超级用户）,g（用户组）,o（其他用户）,a（所有类型）表示。
operator（+或-）表示添加或删除权限。
permission由指示符r（读取）,w（修改）,x（运行）表示。
// 实例，给超级用户加 a.txt  运行权限
chmod u+x a.txt
```

### bash 注释

```java
// 单行注释
行前使用  #
// 多行注释
<< BLOCK 开头
BLOCK 结尾
```

###  引号

```java
// 双引号可以打印变量(即在引号内替换成变量的值，变量通过$引用)单引号则直接输出，如果打印纯文本，单双引号没有区别。
//  示例
$ ./bash_script.sh
echo
echo "Hello World!"
comment='Welcome to W3Cschool!'
echo $comment

echo
echo "Hello World!"
comment="Welcome to W3Cschool!"
echo $comment

echo
echo "Hello World!"
remark="Hello User, $comment"
echo $remark
    
echo
echo "Hello World!"
remark='Hello User, $comment'
echo $remark
// 结果
$ ./bash_script.sh
Hello World!
Welcome to W3Cschool!
    
Hello World!
Welcome to W3Cschool!
    
Hello World!
Hello User, Welcome to W3Cschool!
    
Hello World!
Hello User, $comment
```

### 常用

> // 大写字母，引入系统环境变量
>
> echo JAVA_HOME
>
> // 引用
>
> () 命令组，创建子SHELL执行
> {} 命令组，不创建子SHELL
> ' ' 保护所有的元字符不被解析，想打印'，必须放在双引号内，或者使用/转义
> " " 只允许变量和命令替换，保护其余的元字符不被解析
>
> //  数学表达式
>
> $[ exp ]
> $(( exp ))

### 常用脚本命令

#### 前n 天日期

> //  获取 前 n天日期
>
> now=`date +%D
>
> day=`date -d "15 days ago $now" +%Y-%m-%d`    # 今天前15天的日期

#### 循环遍历

> for num in {0..10..1}
> do
> echo $num
> done

#### 删除部分字符

[参考](https://www.cnblogs.com/zwgblog/p/6031256.html)

> // 原字符     var=http://www.aaa.com/123.htm
>
> echo ${val#*//}     //  删除左边第一个匹配的，  结果   www.aaa.com/123.htm
>
> echo ${val##*/}    // 删除左边左后一个出现的    结果   123.htm
>
> echo ${var%/*}  // 从右边开始删除第一个 / 及右边的字符   结果 http://www.aaa.com
>
> echo ${var%%/*}   // 从右边开始删除最左边  / 及右边的子反复  结果 http:
>
> echo ${var:0:5}  // 截取 从左边开始  n个字符   结果 http:
>
> echo ${var:7}   // 从左边开始第n个字符，直到结束   结果 www.aaa.com/123.htm
>
> echo ${var:0-7:3}    // 从右边开始第几个字符开始，拿到n个字符  结果   123
>
> echo ${var:0-7}  // 从右边第n个字符开始，直到结束  结果 123.htm

#### 字符串包含关系

[参考](https://blog.csdn.net/rznice/article/details/71086839)

## 文件命令

### du 查看文件大小

du => disk usage

> du -k a.txt 以kb显示文件大小
>
> du -m a.txt 以mb显示文件大小
>
> du -h a.txt 以 k、m、g显示文件大小

### df 看磁盘大小

df  => disk free

> df -h  以k、m、g显示文件大小
>
> df -a 显示全部磁盘，默认为 KB

## 权限命令

```bash
# 将目录/opt 及其下面的所有文件、子目录的文件主改成 liuhai
chown -R liuhai:liuhai /opt 
```



## 清空运行中文件

### 清空 tomcat catalina.out

[参考](https://www.cnblogs.com/ainihaozhen/p/9466524.html)

```shell
du -h catalina.out # 查看大小
> catalina.out  # 清空
du -h catalina.out  # 再次查看大小
```

## 环境部署

### mysql

[参考步骤](https://blog.csdn.net/qq_36539042/article/details/122924794)

#### 清除系统自带的

> whereis mysql
>
> find / -name mysql

####  安装

> wget https://downloads.mysql.com/archives/get/p/23/file/mysql-5.7.35-linux-glibc2.12-x86_64.tar.gz

### java jdk

#### 安装

> 从官网下载，然后放到服务器上，因为有登录校验

### redis

#### 安装

> 

## 在linux 上部署常用命令

+ jps -l  查看java进程
+ pwdx 2772  查看指定进程id 的执行目录



## 基础性知识

### 文件描述符

[参考文章](https://blog.csdn.net/zhaominpro/article/details/82630528)

| 名称                 | 代码 | 操作符                                                       | JAVA中表示 |
| -------------------- | ---- | ------------------------------------------------------------ | ---------- |
| 标准输入(stdin)      | 0    | <或<<                                                        | System.in  |
| 标准输出(stdout)     | 1    | >,>>,1>或1>><br /><font color=red> `>`为覆盖，`>>`为追加</font> | System.out |
| 标准错误输出(stderr) | 2    | 2>或2>>                                                      | System.err |

#### 基本操作

> // 修改错误日志
>
> cat a.txt 2>/dev/null
>
> // 错误日志和正常日志放在一起
>
> cat a.txt 2>&1

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
+ taskkill  /f  /pid  进程号

## 重启window 子系统

[参考文章](https://blog.csdn.net/weixin_37251044/article/details/114108199)

```java
//  管理员 运行  cmd
net stop LxssManager
net start LxssManaer
```

## xshell 连接 window子系统

==失败==

[参考解决办法](https://www.cnblogs.com/ACDIV/p/9047825.html)

```java
sudo apt-get remove --purge openssh-server
sudo apt-get install openssh-server
// 备份  ssh_config
cp  /etc/ssh/ssh_config  /yichen/
sudo rm /etc/ssh/ssh_config
sudo service ssh --full-restart
```

## window搜索文件

> win+r 打开搜索框
>
> where  java     //  搜索java目录，根据环境变量 

# git 常用命令

## 配置  ssh key

```
git config --list     //查看用户信息
// 设置登陆名以及邮箱信息
git config --global user.name "qiuxinchao"
git config --global user.email qiuxinchao@9fbank.com.cn
//  生成公钥，默认存放在  C:\Users\E480\.ssh\    该目录下
ssh-keygen -t rsa  -C  "qiuxinchao@9fbank.com.cn"
//  获取公钥  
cat /c/Users/E480/.ssh/id_rsa.pub  
// 将显示出来的信息配置到github 上的 ssh keys中即可

```

## 本地配置多个ssh key

[参考链接](https://www.jianshu.com/p/8a55e023425e)

```
git config --global user.name "yichen17"
git config --global user.email q07218396@163.com
//  生成公钥， 指定文件名字，是指不与之前的冲突
ssh-keygen -t rsa -f /c/Users/E480/.ssh/id_rsa.personal  -C  "q07218396@163.com"
// 在 .ssh 文件夹下创建配置文件
touch config
// 填写配置信息
Host github.com
        IdentityFile ~/.ssh/id_rsa.personal
        User yichen17
Host https://gerrit.9f.cn
        IdentityFile ~/.ssh/id_rsa
        User qiuxinchao
//  获取公钥  
cat /c/Users/E480/.ssh/id_rsa.pub  
// 将显示出来的信息配置到github 上的 ssh keys中
// 测试是否成功
ssh -T git@github.com 
// 如果返回 Hi yichen17! You've successfully authenticated, but GitHub does not # provide shell access  表示成功
```

## 配置  .gitignore 文件

[编写规范](#以上规则 可用于 git 的 `.gitignore`文件)

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

// 清除缓存中的文件， 根据目录清除(删除study目录下git add 的文件)
git rm -r --cached /d/study

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

// 清楚之前登陆的错误信息
git config --system --unset credential.helper

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

## git rebase

### 合并多个提交

#### idea 快捷 rebase

![操作示例](./images/2021-11-04-1.jpg)

[合并多个提交](https://blog.csdn.net/w57685321/article/details/86597808)

#### 提示字段选择

```java
pick：保留该commit（缩写:p）
reword：保留该commit，但我需要修改该commit的注释（缩写:r）
edit：保留该commit, 但我要停下来修改该提交(不仅仅修改注释)（缩写:e）
squash：将该commit和前一个commit合并（缩写:s）
fixup：将该commit和前一个commit合并，但我不要保留该提交的注释信息（缩写:f）
exec：执行shell命令（缩写:x）
drop：我要丢弃该commit（缩写:d）
```

>合并最近的 n个提交
>
>git rebase -i head~n
>
>合并到指定 commit 为止
>
>git rebase -i 332d43c31d5cf536ae6971f5e9f5b73100c591ce
>
> 
>
>修改前面几行的提示字段，含义如上，从上到下commit时间变长
>
>  :wq 退出编辑
>
> 
>
>将 This is the commit message #2 下面的内容改成你想提交的概述即可
>
>  :wq 退出编辑
>
>

## git add

> git add -u <==> git add –update
> 提交所有被删除和修改的文件到数据暂存区
>
> git add .
> 提交所有修改的和新建的数据暂存区
>
> git add -A <==>  git add –all
> 提交所有被删除、被替换、被修改和新增的文件到数据暂存区

### 合并分支

[合并分支](https://blog.csdn.net/nrsc272420199/article/details/85555911)

==示例 yichen分支合到master分支==

> 1、先拉取需要合上去的分支的最新代码   git pull 
>
> 2、切换到 需要被合的分支  git checkout yichen
>
> 3、将 yichen 做的修改加到 master分支的最后   git rebase master
>
> 4、切换到 需要合的分支   git checkout master
>
> 5、合并分支  git merge yichen
>
> 6、推送分支  git push origin master

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

+ 查看文件区别

> git diff note.txt

+ 将一个分支的 一个commit 合并到另一个分支

>  git cherry-pick b64ca7aec5c818f1fe13331197a4f8f10049da35(版本号)

## 查看提交历史  git log

> //   -p  每次提交差异  -2 显示日志条数(这里表示两条)
>
> git log -p -2
>
> // --stat  提交的简略统计
>
> git log --stat
>
> //  对比不同分支之间的差异(dev  master  两个分支提交的不同)
>
> git log dev...master
>
> // 具体指明 差异的归属   < 表示dev   > 表示master 
>
> git log --left-right dev...master

##  合并分支  git merge

[参考链接](https://www.jianshu.com/p/ff1877c5864e)

> //删除远程分支(dev)
> git push origin --delete dev
>
> // 合并分支
>
> git merge dev

## 版本回退

### revert 回退

[参考](https://www.jb51.net/article/192219.htm)

> git revert commit_id
> //如果commit_id是merge节点的话,-m是指定具体哪个提交点
> git revert commit_id -m 1
> //接着就是解决冲突
> git add -A
> git commit -m ".."
> git revert commit_id -m 2
> //接着就是解决冲突
> git add -A
> git commit -m ".."
> git push

### reset 回退

[版本回退](https://yijiebuyi.com/blog/8f985d539566d0bf3b804df6be4e0c90.html)

[index理解](https://www.jianshu.com/p/6bb76450d763)

+ --mixed  会保留源码,只是将git commit和index 信息回退到了某个版本.

  > git reset --mixed 版本号

+ --soft  保留源码,只回退到commit 信息到某个版本.不涉及index的回退,如果还需要提交,直接commit即可

  > git reset --soft 版本号

+ --hard  源码也会回退到某个版本,commit和index 都回回退到某个版本.(注意,这种方式是改变本地代码仓库源码)

  > git reset --hard 版本号

<font color=red>此处所说的index是指对暂存区文件的版本标识，如果index回退，即`--mixed`，此时即使有新文件，它也无法识别。因为旧的   index 中不存在</font>

```
// 远程仓库版本回退
// 1、本地仓库回退
git reset --hard  xxx(版本号)
// 2、强制提交
git push -f
```

## 将模块独立成单独 git

```
//目录结构
  project
  |_____  case-test
  |_____  heartbeat
// 进入父目录(project目录)，为新目录创建新分支   成功后会提示成功，并提供一个 revision
git subtree split -P case-test -b case-test
// 退到父目录同级
cd ..
// 创建目录并进入目录
mkdir case-test
cd case-test
// 初始化 git
git init 
// 将分离出来的分支 full到新的文件目录下
git pull ../project case-test
// 上传到仓库
git remote add origin XXX.git
git push -u origin master
```

## 冲突解决

### 命令行解决冲突

> // 先通过 vi 打开冲突文件，其中
> `<<<<<<< HEAD` 和 `=======` 中间的是你自己的代码    `=======` 和 `>>>>>>>` 中间的是其他人修改的代码
>
> 选择需要保留的代码，然后删除以上的标志，之后再`git add`  `git commit` 即可。
>
> ==注意,如果没有删除标志，则它们会作为代码的一部分被提交==



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

### 提示 filename too long

> git config --global core.longpaths true

### 每次都需要输入账号密码

```java
// 保存凭证  之后再输入一次账号密码之后就不用再输入了
git config --global credential.helper store
```

### 初始化项目后无法通过token 登录

```java
// 虽然弹窗还是提示输入用户名和密码，但是实际的密码那里输入(PAT token即可)
```



### 删除是 因为文件或者路径是中文找不到

```
git config --global core.quotepath false
```

### 初始化仓库 master 合并到main  报错 refusing to merge unrelated histories

[参考解决方法](https://developer.aliyun.com/article/614459)

> git merge master --allow-unrelated-histories

### Support for password authentication was removed.

[密码认证移除解决办法](https://blog.csdn.net/qq_31310793/article/details/119779574)

> // token 注意保存，单次生成后只有第一次能看到，之后不在能看到它，只能在生成一个信息
>
> settings  =>  Developer Settings =>  Personal Access Token  => Generate New Token  =>  填写该 token 拥有的对应权限 =>  Generate token  => 复制

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

## 事务

> multi  事务开始
>
> exec 执行
>
> discard 丢弃

> redis 的事务只保持 `隔离性中的串行`，`不保持原子性`。中间失败后面仍然执行。它的事务只是把所有命令整合到一起执行，避免中途被打断。

## watch

> 监听变量，如果变量期间被改变了，则 `multi` `exec`会执行失败，返回一个`nil`
>
> redis 禁止在 `multi` 和 `exec`中间加`watch`，不然会报错。

## pubsub缺点

> pubsub 的生产者传递过来一个消息，Redis会直接找到相应的消费者传递过去。如果一个消费者没有，那么消息会被直接丢弃。如果开始有3个消费者，一个消费者突然挂了，生产者会继续发消息，另外两个消费者可以持续收到消息，但是当挂掉的消费者重新连上的时候，在断连期间生产者发送的消息，对于这个消费者来说就是彻底丢失了。
>
> 如果redis停机重启，pubsub的消息是不会持久化的，毕竟redis宕机就相当于一个消费者都没有，所有的消息会被直接丢弃。
>
> `已经被stream替代，它给redis带来了持久化的消息队列`

## 查看内存使用情况

[参考文章](https://linuxhint.com/check-redis-memory-usage/)

>//   查看内存信息   _human  代表人类可读格式
>
>info memory 
>
>// 查看某个库的key数量
>
>dbsize
>
>//   具体的key信息
>
>info keyspace
>
>// 查看某个key具体使用多少内存
>
>memory usage key
>
>// 

## 缓存策略

[学习文章](https://codeahoy.com/2017/08/11/caching-strategies-and-how-to-choose-the-right-one/)

[学习文章](https://www.jianshu.com/p/ca6bb4f4a31a)

### Cache-Aside 策略

> 会先查询缓存，如果缓存命中则直接返回。如果没有命中，则返回结果并写入缓存。这种场景下缓存内容和数据库DO可能不一样。因为缓存内容可能是由多个DO拼凑而成的

### Read-Through 策略

> 整体逻辑同`Cache-Aside`，但是有两个区分点。
>
> 1、`Cache-Aside`是应用主动查数据并写入缓存，而`Read-Through`则是`library`或者是`cache provider`来执行这个职能
>
> 2、`Read-Through`的缓存内容必须和数据库`DO`一致

### Write-Through 策略

> 先将数据写入缓存，在写入数据库。单看它，没啥作用。但是和`Read-Through`配合，它采用了延迟写入。获得了`Read-Through`的所有好处，能保证数据一致性以及不需要在使用缓存失效策略

### Write-Around

> 数据直接写入数据库，查询时才写入缓存。适用于写一次，读很少或没有的场景。可以配合`Read-aside`使用。使用场景如日志，聊天消息。

### Write-Back|write-behind

> 先将数据写入缓存，然后延迟一段时间写入数据库。它可以提高写入性能，适合写入繁重的工作场景。

## redis 运行变慢，性能排查

### 查询redis中的大key

> //  -i 0.1 表示每个100条scan指令会休眠0.1s 
>
> redis-cli -h 127.0.0.1 -p 6379 -a yichen --bigkeys -i 0.1

### 管道压力测试

>//  -P 14 表示单个管道内并行的请求数量
>
>redis-benchmark -h 127.0.0.1 -p 6379 -a yichen -t set -P 14  -q

### 管道理解

<img src="./images/2022-07-31-1.jpg" alt="交互流程图" style="zoom:67%;" />

> ​		我们开始以为`write`操作是要等到对方收到消息后才会返回，但实际上不是这样的。`write`操作只负责将数据写到本地操作系统内核的发送缓冲中然后就返回了，剩下的事交给操作系统内核异步将数据送到目标机器。但是如果发送缓冲区满了，那么就需要等待缓冲空出空闲空间来，这个就是写操作IO操作的真正耗时。
>
> ​		我们开始以为`read`操作是从目标机器拉取数据，但实际上不是这样的。`read`操作只负责将数据从本地操作系统内核的接收缓冲中取出来就了事了。但是如果缓冲是空的，那么就需要等待数据到来，这个就是`read`操作IO操作的真正耗时。
>
> ​		所以对于`value=redis.get(key)`这样一个简单的请求来说，`write`操作几乎没有耗时，直接写到发送缓冲中就返回，而`read`就比较好事了，因为它要等待消息经过网络路由到目标机器处理后的响应消息，再回送到当前的内核读缓冲才可以返回。这才是又给网络来回的真正开销。
>
> ​		而对于管道来说，连续的`write`操作根本就没有耗时，之后第一个`read`操作会等待一个网络的来回开销，然后所有的响应消息就都已经送回到内核的读缓冲了，后续的`read`操作就直接可以从缓冲中拿到结果，瞬间就返回了。

### 思维导图

![思维导图](./images/2021-02-05-1.png)

### 参考文章

[参考文章](https://mp.weixin.qq.com/s/Cvq51psUQaNFThL0HJy11g)

## 常用命令

> select 2   // 切换到2号数据库
>
> dbzise  //  当前库的key数量
>
> keys * // 显示所有key，数量少可行，数量多容易卡死
>
> get name //获取 name 的值
>
> ttl name  // 获取name 的过期时间

## 特殊记录

### 删除key

> 通常可以使用 `del` 删除，但如果这个`key`包含了上千万个元素的`hash`，会导致系统卡顿，此时可以使用`unlink`命令，来交给后台线程异步回收。如果`key`占用空间比较小，此时`unlink` 等价与`del`，会实时处理。`unlink`不需要考虑到并发问题，因为一旦有一个节点使用了`unlink`，那么其他节点都无法在接触到它了。。

### 清空db

> 一般清空`db`，使用`flushall` 或者`flushdb`，这是如果占用内存比较大，会导致卡顿。此时可以通过添加异步参数`async`来交由后台线程处理。清空`db`还可能在从节点同步时发生。

### redis 4.0 异步删除机制

> `slave-lazy-flush`：从节点接受完`rdb`文件后的`flush`操作。
>
> `lazyfree-lazy-eviction`：内存达到`maxmemory`时进行淘汰。
>
> `lazyfree-lazy-expire key`：过期删除。
>
> `lazyfree-lazy-server-del rename`：指令删除 `destKey`。

## 配置

### 设置密码

> requirepass 密码

### 重命名命令或者禁用命令

> rename-command rename renamexxx  // 重命名
>
> rename-command del ""  // 禁止使用del



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

## 修改默认配置文件夹

[参考步骤](https://blog.csdn.net/weixin_44159171/article/details/119393044)

> // 默认配置存放目录
>
> C:\Users\administrator\AppData\Local\JetBrains
>
> // 修改idea安装配置文件  idea.properties
>
> idea.config.path=E:/Intellij_ideal/idea-config/config
>
> idea.system.path=E:/Intellij_ideal/idea-config/system

## 使用配置记录

> 1、安装插件
>
> Translate
>
> Alibaba Java Coding Guidelines    
>
> Activate-power-mode   
>
> Lombok    
>
> Material Theme UI
>
> free mybatis plugins 
>
> Maven helper

### 配置-选中块颜色

![参考设置方法](./images/2022-01-21-1.jpg)

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

ctrl+shift+F    全局搜索  <font color=red>如果失效，则是搜狗输入法-繁简切换占了键位</font>    [参考链接](https://blog.csdn.net/c15158032319/article/details/79498540)

折叠代码块：
1、所有   ctrl+shift+  -
2、选中内容  ctrl+  -

展开代码块
1、所有  ctrl+shift+=
2、选中内容   ctrl+  =

## 便捷方法

### 打印有关  sql 的执行语句  
logging.level.dao对应的位置=debug
例子：   logging.level.com.tianyilan.shardingsphere.demo.repository=debug

### 将项目与  github  隔离

选中项目   点击 file 》setting 》 version control 》选中目录 ，右侧插掉即可

### 项目中隐藏  `.idea` 文件 

+ 打开file--》settings--》editor---->file types
+ 选择 `*.gitignore`    在底下已有的很多类文件中追加   `.idea;`

### <font color=red>查看  class 文件的字节码内容</font>

> 通过 `javac`命令 编译指定的  	`.java`文件，例如  `javac hello.java`  此时会在同一目录下生成对应的`.class`文件，此时在idea中打开该文件是反编译优化后的内容，如果想查看原始的   字节码文件，可以cd到对应的`.class`文件目录，然后执行 `javap -c xxx`，例如，`javap -c hello.class`,此时，控制台就会显示对应的指令信息。  `javap -v xxx` 命令则是显示具体内容

[参考文章](https://blog.csdn.net/weixin_41276238/article/details/103769956)

### 快速查看java动态生成class内容的方法

[参考链接](https://blog.csdn.net/wenyuan65/article/details/82634118)

### 快速删除代码中的空行

> ctrl+ r 进行代码替换，用正则表达式`^\s*\n` 替换为空即可

![操作图片](./images/2021-04-02-1.jpg)

### idea 显示内存使用

> view  >  Appearance  > status bar widges  > memory indicator

### 打开类图

> settings > tools > diagrams > java class disgrams 下勾选
>
> 打开类， ctrl+alt+u 打开类图

### 设置标记

#### 标记设置

> 鼠标右键行的最左边，选择 Set Bookmark with Mnemonic 设置标记

![操作方法](./images/2021-07-26-1.jpg)

#### 查看标记

> 快捷键  shift+f11 即可 查看标记

## 常用设置

### 字体缩放快捷键

[参考操作](https://blog.csdn.net/m0_52673390/article/details/113094714)

> file > settings > keymap 
>
> 输入 decrease 右键  `decrease font size` 选择`add mouse shortcut`， 按住`crtl` 滚轮向下滚动即可
>
> 输入 increase 右键  `increase font size` 选择`add mouse shortcut`， 按住`crtl` 滚轮向上滚动即可

### 不显示  .iml 文件

> File > Settings > Editor > FIle Type  在最下面的  Ignore Files and Folders 末尾添加 *.iml;    即可

![参考步骤](./images/2021-08-07-3.jpg)

### 设置 选中对象时背景颜色

setting =》  editor  =》  color scheme  =》  general    右侧   

1、editor  =》  selection background   设置为`BA4040`

2、code =》 identifier under caret  设置为 `BA4040`

3、 code  =》  identifier under caret（write） 设置为  `BA4040`

### 设置 idea 打开项目默认设置

==可以设置编码格式，默认jdk等==

>  file  > new projects settings  >  structure for new projects
>
> 指定你想设定的jdk 即可

### 关闭文件后目录跳转到前一个文件的位置

<img src="./images/2021-4-24-1.jpg" alt="图示" style="zoom: 67%;" />

### 设置 打开的文件的个数上线

> file > settings > editor  >general > editor tabs  > closing policy 下的 tab limit 修改数量即可

<img src="./images/2021-05-11-2.jpg" alt="操作步骤参考" style="zoom: 67%;" />

### idea 中 mybatis 的 xml配置文件 黄色波浪线

[参考文章](https://blog.csdn.net/wsjzzcbq/article/details/89528252)

>  file > settting > editor >inspections >右侧 SQL  
>
> 去掉 no data sources  configured 和
>
> ![操作步骤](./images/2021-05-14-2.jpg)
>

### idea中 鼠标放置到 方法上显示内容

>  file > settings > editor  >code editing > quick documentation  > show quick documentation on mouse move
>
> 也可以   file >settings > 搜索 mouse move

![参考设置](./images/2021-05-15-1.jpg)

<font color=red>如果设置之后是侧边栏 则做如下处理

![处理变成侧边栏](./images/2021-05-15-2.jpg)

### 查看源码报错 Decompiled.class file, bytecode version 52.0

> edit > settings  > Build,Execution,Deployment > BuildTools > Maven > importing 
>
> 在右侧  Automatically download 中勾选 Source、Document、Annotations。

![操作示例](./images/2021-06-03-1.jpg)

### 设置方法模板和类模板

[参考链接](https://blog.csdn.net/sdut406/article/details/81750858)

#### 设置类模板

> file > settings > Editor > File and Code Templates > Includes 

输入如下类注释模板

```java
/**
 * @author  Qiuxinchao
 * @date  ${DATE} ${TIME}
 * @version 1.0
 * @describe 
 */
```

![示例](./images/2021-06-28-1.jpg)

#### 方法模板

没啥用，自带的就挺好的。



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

[jvm 内存大小修改方法](https://blog.csdn.net/weixin_35781693/article/details/114506311)

<font color=red size=5dp>以上都不行，可用版本</font>

> help > edit custom vm options 

## 推荐插件  2020.2.2

+ Translation    翻译工具

+ GsonFormat

+ Free-idea-mybatis

+ CodeGlance

+ Activate-power-mode
  
  + <font color=red>去掉 代码界面的 max 0  </font>              window   =》   activate-power-mode  =》  combo  (不勾选即可)
  
+ Alibaba Java Coding Guidelines

  + 去除一些规约，通过 settings > editor >inspections

  ![示意图](./images/2021-05-26-1.jpg)

+ Lombok

+ Material Theme UI
  + 主题选的是 material oceanic 、
  + <font color=red>修改默认 选中对象背景颜色以及关联对象的颜色</font>     setting  =》 editor  =》 color scheme  =》  general   右侧  code  =》  identifier under caret
  
+ Rainbow Brackets

+ Maven Helper

+ SequenceDiagram

+ jclasslib  用于查看字节码的工具，   功能类似  javap

+ Git Commit Template     git  提交的commit 模板

+ free mybatis plugins   =》  能够从mybatis 的 dao 层快速跳转到  mapper

+ RestfulTookit  =》 快捷跳转到方法，快捷请求接口  =>  快捷键 `ctrl+ \ `

## 插件 2021.3.2

```java
1、alibaba java coding guidelines
2、gsonformatflus  =>  alt+s
3、material theme ui   => material oceanic
4、maven helper
5、mybatisX
6、translation
7、restfulToolKit-fix  =>  ctrl + \
8、jclasslib bytecode viewer
9、atom material icons
10、rainbow brackets => 括号显示优化
11、CodeGlance  =>  代码视图窗口
12、SequenceDiagram  => 依赖美化展示
```





## 调试技巧

### debug 循环时，指定到某个条件，例如0-10遍历，想直接到6

+ 在循环的地方打上断点，然后右击，可以设置条件，设置完毕后debug的时候，会直接进入符合改条件的情况

<img src="./images/2021-06-02-1.jpg" alt="示例" style="zoom: 67%;" />

或者这样进行复杂设置

<img src="./images/2021-06-02-2.jpg" alt="方法二" style="zoom: 67%;" />

<img src="./images/2021-06-02-3.jpg" alt="方法二-1" style="zoom:50%;" />

### 方法之间 回跳，回到前一个方法

<img src="./images/2021-01-27-3.jpg" alt="图解" style="zoom:67%;" />

### 修改 force step into 键位

> debug 的时候默认的时候 F7 为 step into，此时同行有多个方法的时候，debug 需要选择方法很烦，而 force step into 是逐个进入，默认键位是 ctrl+shift+f7，操作起来不方便，故修改键位

![参考修改键位](./images/2022-02-12-1.jpg)



## 问题记录

### idea vm配置目录

> C:\Users\E480\AppData\Roaming\JetBrains\IntelliJIdea2021.3

### 全区搜没有搜到jar包中的文件

> 需要下载jar包的源文件。
>
> 将项目打包并保存源代码
>
> 方法一：mvn source:jar install
>
> 方法二：
>
> ```
> <plugin>
>     <groupId>org.apache.maven.plugins</groupId>
>     <artifactId>maven-source-plugin</artifactId>
>     <executions>
>         <execution>
>             <id>attach-sources</id>
>             <goals>
>                 <goal>jar</goal>
>             </goals>
>         </execution>
>     </executions>
> </plugin>
> ```

### idea一直卡在启动界面

[参考解决方法](https://blog.csdn.net/weixin_42241455/article/details/124961455?spm=1001.2014.3001.5502)

#### 错误信息

```
2022-05-25 09:58:41,706 [   4882]  ERROR - llij.ide.plugins.PluginManager - java.net.BindException: Address already in use: bind 
java.util.concurrent.CompletionException: java.net.BindException: Address already in use: bind
	at java.base/java.util.concurrent.CompletableFuture.encodeThrowable(CompletableFuture.java:314)
	at java.base/java.util.concurrent.CompletableFuture.completeThrowable(CompletableFuture.java:319)
	at java.base/java.util.concurrent.CompletableFuture$AsyncSupply.run(CompletableFuture.java:1702)
	at java.base/java.util.concurrent.CompletableFuture$AsyncSupply.exec(CompletableFuture.java:1692)
	at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:290)
	at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1020)
	at java.base/java.util.concurrent.ForkJoinPool.scan(ForkJoinPool.java:1656)
	at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1594)
	at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:183)
Caused by: java.net.BindException: Address already in use: bind
	at java.base/sun.nio.ch.Net.bind0(Native Method)
	at java.base/sun.nio.ch.Net.bind(Net.java:455)
	at java.base/sun.nio.ch.Net.bind(Net.java:447)
	at java.base/sun.nio.ch.ServerSocketChannelImpl.bind(ServerSocketChannelImpl.java:227)
	at io.netty.channel.socket.nio.NioServerSocketChannel.doBind(NioServerSocketChannel.java:134)
	at io.netty.channel.AbstractChannel$AbstractUnsafe.bind(AbstractChannel.java:562)
	at io.netty.channel.DefaultChannelPipeline$HeadContext.bind(DefaultChannelPipeline.java:1334)
	at io.netty.channel.AbstractChannelHandlerContext.invokeBind(AbstractChannelHandlerContext.java:506)
	at io.netty.channel.AbstractChannelHandlerContext.bind(AbstractChannelHandlerContext.java:491)
	at io.netty.channel.DefaultChannelPipeline.bind(DefaultChannelPipeline.java:973)
	at io.netty.channel.AbstractChannel.bind(AbstractChannel.java:260)
	at io.netty.bootstrap.AbstractBootstrap$2.run(AbstractBootstrap.java:356)
	at io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:164)
	at io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:469)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:503)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:986)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.base/java.lang.Thread.run(Thread.java:829)
2022-05-25 09:58:41,722 [   4898]  ERROR - llij.ide.plugins.PluginManager - IntelliJ IDEA 2021.3.2  Build #IU-213.6777.52 
2022-05-25 09:58:41,722 [   4898]  ERROR - llij.ide.plugins.PluginManager - JDK: 11.0.13; VM: OpenJDK 64-Bit Server VM; Vendor: JetBrains s.r.o. 
2022-05-25 09:58:41,722 [   4898]  ERROR - llij.ide.plugins.PluginManager - OS: Windows 10 
```

#### 解决办法

```
// 以管理员方式运行 CMD(win + q 然后输入 cmd,右键，以管理员身份运行)
netsh int ipv4 set dynamicport tcp start=49152 num=16383
netsh int ipv4 set dynamicport udp start=49152 num=16383
// 运行了上面两个，再次尝试打开 idea，还是失败
net stop winnat
net start winnat
// 运行了这两个后，再次尝试打开idea，成功了。
```

### 文件存在但是无法引用

> idea 缓存问题
>
> File >  invalidate caches

### 2021.3.2 没有 local change

> file  > settings >  version control  >  commit > 去掉勾选  use non-model commit interface

### 2020.2.2  translation 提示网络问题

```java
// 可能原因是 idea 版本太低且插件库中该插件无法升级导致，去官方下载最新插件导入
```

### 设置打开的文件tab数

> settings > editor > general  > editor tabs  下边的closing policy 中 tab limit   以及 show tab in one row

### 触摸板双指滚动失效

```java
//  具体请款待排查
1、visualVm 启动的原因
2、c盘内存空间太小  4个g
3、win+r 输入 controller 后，选择个性化，查看触摸板设置，界面为关闭 => 测试 有一部分是该原因
    

 ==>  电脑重启后就可以用了
```

<img src="./images/2022-02-13-1.jpg" alt="步骤三截图" style="zoom:67%;" />



### springboot 项目打开未被识别为 maven

> 选中 pom.xml ，右键选择 add as a maven 

### 拷贝别人能正常运行的maven 仓库，idea 没有自动引入仓库里的包

> 查看项目结构中的 libraries
>
> 如果没有手动引入，点击上面的加号 》 选择 java  》 选中 拷贝的maven 仓库目录

<img src="./images/2021-11-15-1.jpg" alt="看libraries" style="zoom:67%;" />

### idea 内启动 tomat 报错 增加缓存空间大小

> 具体内容如下
>
> 。。。的资源添加到Web应用程序[]的缓存中，因为在清除过期缓存条目后可用空间仍不足 - 请考虑增加缓存的最大空间。

[解决办法](https://www.cnblogs.com/saoge/p/15035909.html)

```java
// tomcat 安装目录 下 conf/context.xml  添加如下内容
//  <WatchedResource>WEB-INF/web.xml</WatchedResource>
//    <WatchedResource>${catalina.base}/conf/web.xml</WatchedResource>  下面
<Resources
          cachingAllowed="true"
		  cacheMaxSize="100000"
    />



```



### idea 内启动tomcat，日志显示乱码

[解决办法](https://blog.csdn.net/weixin_43160251/article/details/105434911)

== tomcat 日志编码格式和 idea 虚拟机中的编码格式一致，即都为 UTF-8

### 启动项目 报错端口占用，但是查看本地端口显示没被使用

[参考解决办法](https://blog.csdn.net/weixin_40555722/article/details/109046172)

> typee-v  端口保留问题
>
> //  查看保留端口，里面的端口不能使用
>
> netsh interface ipv4 show excludedportrange protocol=tcp

<img src="./images/2021-09-24-3.jpg" alt="查询结果" style="zoom:67%;" />

### 引入外部的jar包

[参考文章-可行](https://blog.csdn.net/limengautoman/article/details/81186120)

file > project structure  > SDKs  右侧引入外部jar包

![参考步骤](./images/2021-09-24-2.jpg)

然后点击 ok，之后重新加载项目即可

![参考图片](./images/2021-09-24-1.jpg)

[参考文章-待验证](https://blog.csdn.net/cm15835106905/article/details/107964194)

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

### Method threw 'java.lang.NullPointerException' exception. Cannot evaluate xxx.toString()

> // 产生条件
>
> //  new对象是 调用了空构造函数，debug 运行时标红显示

<img src="./images/2021-04-26-1.jpg" alt="图示" style="zoom:80%;" />

<font color=red> 解决方法</font>

> 选中 异常的变量  》 右键  》 view as  》 由原来的 toString 改为 Object

<img src="./images/2021-04-26-2.jpg" alt="具体执行流程" style="zoom:50%;" />

###  在 idea 中通过tomcat 运行war包 报错  由于之前的错误，Context[/cas_client1_war_exploded]启动失败

<font color=red size=10px>这里的错误是前后空格的问题</font>

> //  查过错误日志如下
>
> org.apache.catalina.core.StandardContext.filterStart 启动过滤器异常
> 	java.lang.ClassNotFoundException: org.jasig.cas.client.validation.
>             Cas20ProxyReceivingTicketValidationFilter

<font color=red>解决方法（本质问题是缺少jar包）</font>

步骤一：  进行 project structure  》 找到左边的 artifacts  》 中间找到对应的项目 》 右边将 WEB-INF下的lib 文件夹删除

![步骤一](./images/2021-04-28-1.jpg)

步骤二   然后在创建一个  lib文件夹，选中文件夹 add copy of ，选择 library files ，然后 ctrl+a全选，即可。

![步骤二](./images/2021-04-28-2.jpg)

### idea 下启动tomcat 设置启动路径

![方法](./images/2021-04-28-3.jpg)

###  项目运行报错  no log4j-web module available

具体内容如下

```java
Log4j appears to be running in a Servlet environment, but there's no log4j-web module available. If you want better web container support, please add the log4j-web JAR to your web archive or server lib directory.
```

解决办法

```java
 // 添加如下maven 依赖
<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-web</artifactId>
	<version>2.10.0</version>
</dependency>
```

### 本机maven仓库有 jar包 ，但是打包失败，报错找不到jar包

[参考链接](https://blog.csdn.net/huqiankunlol/article/details/100277702)

> 到仓库的对应目录，将 `_remote.repositories` 文件删除，然后重新加载maven依赖即可

### terminal  打印乱码

[参考解决办法](https://qq52o.me/2520.html)

### springboot 项目 Application.main() 启动 无报错但是无法访问页面

#### 描述

> 使用了 jetty，main()方法启动内容如下
>
>  ```java
> public static void main(String[] args) {
>     new SpringApplicationBuilder(App.class).web(true).run(args);
>     SystemService.printKeyLoadMessage();
> }
>  ```
>
> 报错为 404，找不到页面

#### 解决办法

==指定工作目录==

![解决办法](C:\Users\E480\AppData\Roaming\Typora\typora-user-images\image-20210722165020449.png)

### idea 实体类存在但是提示找不到 报红

![参考解决办法](C:\Users\E480\AppData\Roaming\Typora\typora-user-images\image-20210730085931598.png)

### springboot 中通过 InitializingBean 创建netty，运行时web无法访问

> 原因是 netty 阻塞了  springboot 内置的 tomcat 的创建
>
> // 解决方法，不通过 springboot bean 的初始化函数，而是通过 手动跑 Thread

[参考解决方法](https://blog.csdn.net/qq_41571395/article/details/108635892)

### springboot 配置日志

<img src="./images/2021-08-07-1.jpg" alt="参考步骤" style="zoom:67%;" />

#### 方法一：View > Tool Windows > Maven 选中 maven即可打开右侧的菜单


#### 方法二： Help > Find Action > 输入 Maven Projects ，选择下面的 Add  Maven Project

<img src="./images/2021-08-07-2.jpg" alt="参考步骤" style="zoom: 80%;" />

### maven 启动项目profile失效

![参考图片](./images/2022-01-18-1.jpg)

#### 解决

这里指定的是`maven`的`profile`，而不是项目的`profile`

### idea中忽略文件

> setting > editor > file types 

# 响应式编程

<font color=red size=5px ><b>全栈式响应式编程，指的是响应式开发方式的有效性取决于整个请求链路的各个环节是否都采用了响应式编程模型。</b><br/><b>响应式系统的价值在于提供了即时响应性、可维护性和扩展性，表现的形式是回弹性和弹性，而实现的手段则是消息驱动。</b></font>

## 组件

```java
// Flux 构建
Flux.fromIteratable()
// Mono
Mono.just()
```

### idea 没有maven 窗口，也没有进行maven 加载

[参考解决办法](https://www.cnblogs.com/learcher/p/13420116.html)


## 异步请求的三种实现

### 回调函数

#### 流程

<img src="./images/2021-03-26-1.jpg" alt="流程图" style="zoom: 80%;" />

如上图所示，服务A需要调用服务B，正常的同步请求是阻塞服务A直至服务B处理完毕，而回调则不会在服务B处理期间阻塞，可以在期间执行其他操作，当服务B执行完毕后，服务B会主动调用服务A的callback()方法，来继续执行操作

#### 分析

<font color=red>缺点：回调的最大问题是复杂性，一旦在执行流程中包含了多层的异步执行和回调，那么就会形成一种嵌套结构，给代码的开发和调试带来很大的挑战。所以回调很难大规模地组合起来使用，因为很快就会导致代码难以理解和维护，从而造成所谓的“回调地狱”问题。</font>

### Future

#### 概念

我们有一个需要处理的任务，然后把这个任务提交到 Future，Future 就会在一定时间内完成这个任务，而在这段时间内我们可以去做其他事情。

本质是一种多线程技术。多线程假设一些线程可以共享一个 CPU，而 CPU 时间能在多个线程之间共享，这一点就引入了“上下文切换”的概念。

如果想要恢复线程，就需要涉及加载和保存寄存器等一系列计算密集型的操作。因此，大量线程之间的相互协作同样会导致资源利用效率低下。



### 响应式编程

#### 概念

与同步请求逆向思维，基于发布/订阅模式，从同步的拉改为推送。<font color=red>即从原来的消费者向生产者请求改为生产者推送消息给消费者。</font>

## 创建  Flux 和  Mono

### 静态创建

> // just  方式
>
> Flux.just("Hello", "World").subscribe(System.out::println);
>
> // fromxxx()  
>
> Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
>
> //  range()  范围创建，以第一个为初始值，第二个参数为个数。
>
> Flux.range(2020, 5).subscribe(System.out::println);
>
> //  interval()  间隔创建，第一个参数为间隔时间，第二个参数为延迟
>
> Flux.interval(Duration.ofSeconds(2), Duration.ofMillis(200)).subscribe(System.out::println);

### 动态创建

> //  通过  generate()方法创建
>
> Flux.generate(sink -> {
>     sink.next("Jianxiang");
>     sink.complete();
> }).subscribe(System.out::println);
>
> 
>
> Flux.generate(() -> 1, (i, sink) -> {
>             sink.next(i);
>             if (i == 5) {
>                 sink.complete();
>             }
>             return ++i;
> }).subscribe(System.out::println);
>
> // create() 方法创建
>
> Flux.create(sink -> {
>             for (int i = 0; i < 5; i++) {
>                 sink.next("jianxiang" + i);
>             }
>             sink.complete();
> }).subscribe(System.out::println);

## 响应式宣言

![响应式宣言](./images/2021-05-11-1.jpg)

### 概念定义

+ 回弹性：是指系统在出现失败时，依然能够保持即时响应性
+ 弹性：是指系统在各种请求压力之下，都能保持即时响应性

## 背压机制

纯“推”模式下的数据流量会有很多不可控制的因素，并不能直接应用，而是需要在“推”模式和“拉”模式之间考虑一定的平衡性，从而优雅地实现流量控制。这就需要引出响应式系统中非常重要的一个概念——背压机制（Backpressure）。

### 背压定义

简单来说就是下游能够向上游反馈流量请求的机制。采用背压机制，消费者会根据自身的处理能力来请求数据，而生产者也会根据消费者的能力来生产数据，从而在两者之间达成一种动态的平衡，确保系统的即时响应性。

### 实现方法

Subscription 对象是确保生产者和消费者针对数据处理速度达成一种动态平衡的基础，也是流量控制中实现背压机制的关键所在。

### 背压传播模式

+ 纯推模式：这种模式下，订阅者通过 subscription.request(Long.MAX_VALUE) 请求有效无限数量的元素。

+ 纯拉模式：这种模式下，订阅者通过 subscription.request(1) 方法在收到前一个元素后只请求下一个元素。

+ 推-拉混合模式：这种模式下，当订阅者有实时控制需求时，发布者可以适应所提出的数据消费速度。

### 背压处理策略

+ BUFFER：代表一种缓存策略，缓存消费者暂时还无法处理的数据并放到队列中，这时候使用的队列相当于是一种无界队列。

+ DROP：代表一种丢弃策略，当消费者无法接收新的数据时丢弃这个元素，这时候相当于使用了有界丢弃队列。

+ LATEST：类似于 DROP 策略，但让消费者只得到来自上游组件的最新数据。

+ ERROR：代表一种错误处理策略，当消费者无法及时处理数据时发出一个错误信号。

## reactor 操作符

### 分类

+ 转换（Transforming）操作符，负责将序列中的元素转变成另一种元素。
+ 过滤（Filtering）操作符，负责将不需要的数据从序列中剔除出去。
+ 组合（Combining）操作符，负责将序列中的元素进行合并，连接和集成。
+ 条件（Conditional）操作符，负责根据特定条件对序列中的元素进行处理。
+ 裁剪（Reducing）操作符，负责对序列中的元素执行各种自定义的裁剪操作。
+ 工具（Utility）操作符，负责一些针对流式处理的辅助性操作。

### 介绍

#### 转换操作符

> //buffer 操作符   把当前流中的元素统一收集到一个集合中，并把这个集合对象作为新的数据流。
>
> Flux.range(1, 25).buffer(10).subscribe(System.out::println);
>
> //window 操作符   把当前流中的元素收集到另外的 Flux 序列中
>
> Flux.range(1, 5).window(2).toIterable().forEach(w -> {
>         w.subscribe(System.out::println);
>         System.out.println("-------");
> });
>
> // map 操作符   对流中的每个元素应用一个映射函数从而达到转换效果
>
> Flux.just(1, 2).map(i -> "number-" + i).subscribe(System.out::println);
>
> // flatMap 操作符  会把流中的每个元素映射成一个流而不是一个元素，然后再把得到的所有流中的元素进行合并
>
> Flux.just(1, 5)
>      .flatMap(x -> Mono.just(x * x))
>      .subscribe(System.out::println);

#### 过滤操作符

> //  filter 操作符
>
> Flux.range(1, 10).filter(i -> i % 2 == 0)
> 	.subscribe(System.out::println);
>
> //  first/last 操作符     skip/skipLast      take/takeLast

#### 组合操作符

> //then  操作符    等到上一个操作完成再进行下一个
>
> // when 操作符  等到多个操作一起完成
>
> //  merge 操作符   把多个 Flux 流合并成一个 Flux 序列，而合并的规则就是按照流中元素的实际生成的顺序进行
>
> // mergeSequential 操作符   按照所有流被订阅的顺序，以流为单位进行合并。
>
> //  zip 操作符   zip 操作符的合并规则比较特别，是将当前流中的元素与另外一个流中的元素按照一对一的方式进行合并
>
> Flux flux1 = Flux.just(1, 2);
> Flux flux2 = Flux.just(3, 4);
> Flux.zip(flux1, flux2).subscribe(System.out::println);
>
> // 结果  
>
> [1,3]
> [2,4]

#### 条件操作符

> defaultEmpty 操作符   用来返回来自原始数据流的元素，如果原始数据流中没有元素，则返回一个默认元素。
>
> takeUntil 操作符   获取直到条件不满足元素
>
> takeWhile 操作符   当条件满足是获取元素

#### 裁剪操作符

> // any 操作符    检查是否至少有一个元素具有所指定的属性
>
> Flux.just(3, 5, 7, 9, 11, 15, 16, 17).any(e -> e % 2 == 0).subscribe(isExisted -> System.out.println(isExisted));
>
> // all操作符   是否所有元素都满足条件
>
> Flux.just("abc", "ela", "ade", "pqa", "kang").all(a -> a.contains("a")).subscribe(isAllContained -> System.out.println(isAllContained));
>
> //  concat 操作符   合并来自不同 Flux 的数据，根据流的先后顺序进行复制
>
> Flux.concat(Flux.range(1, 3),Flux.range(4, 2),Flux.range(6, 5)).subscribe(System.out::println);
>
> //  reduce 操作符  ，对元素进行累加操作
>
> Flux.range(1, 10).reduce((x, y) -> x + y).subscribe(System.out::println);
>
> // reduceWith 操作符，对元素进行累加操作，可以设定初值
>
> Flux.range(1, 10).reduceWith(() -> 5, (x, y) -> x + y).subscribe(System.out::println);

#### 工具操作符

> //  subscribe 操作符
>
> ```
> //  不常用的自定义  subscriber
> Subscriber<String> subscriber = new Subscriber<String>() {
>     volatile Subscription subscription;
>     @Override
>     public void onSubscribe(Subscription s) {
>         subscription = s;
>         System.out.println("initialization");
>         subscription.request(1);
>     }
>     @Override
>     public void onNext(String s) {
>         System.out.println("onNext:" + s);
>         subscription.request(1);
>     }
>     @Override
>     public void onComplete() {
>         System.out.println("onComplete");
>     }
>     @Override
>     public void onError(Throwable t) {
>         System.out.println("onError:" + t.getMessage());
>     }
> };
> // 推荐使用
> class MySubscriber<T> extends BaseSubscriber<T> {
>     @Override
>     public void hookOnSubscribe(Subscription subscription) {
>         System.out.println("initialization");
>         request(1);
>     }
> 
>     @Override
>     public void hookOnNext(T value) {
>         System.out.println("onNext:" + value);
>         request(1);
>     }
> }
> ```
>
> //  block 操作符  在接收到下一个元素之前会一直阻塞。block 操作符常用来把响应式数据流转换为传统数据流
>
> //  log 操作符  针对日志的工具操作符 log，它会观察所有的数据并使用日志工具进行跟踪
>
> //  debug 操作符   这是全局的，需要添加在程序启动的地方
>
> Hooks.onOperator(providedHook ->providedHook.operatorStacktrace())
>
> //  checkpoint  操作符   观察特定流，`debug` 只输出错误日志
>
> Mono.just(1).map(x -> 1 / x).checkpoint("debug").subscribe(System.out::println);

## webclient

### 构建方法

```java
WebClient webClient=WebClient.create();
WebClient webClient=WebClient.builder().build();
```

### 访问服务

```java
// retrieve()  方法是获取响应主体并对其进行解码的最简单方法
WebClient webClient = WebClient.create("http://localhost:8081");
Mono<Account> result = webClient.get()
        .uri("/accounts/{id}", id)
	    .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Account.class);
//exchange() 可以对响应拥有更多的控制权。它可以访问整个响应结果，该响应结果是一个 ClientResponse 对象，包含了响应的状态码、Cookie 等信息。
Mono<Account> result = webClient.get()
 .uri("/accounts/{id}", id)
 .accept(MediaType.APPLICATION_JSON)
 .exchange() 
 .flatMap(response -> response.bodyToMono(Account.class));
```

### 添加请求体

#### 如果请求体是 Mono 或者 Flux 类型  可用Body()方法

```java
Mono<Account> accountMono = ... ;
Mono<Void> result = webClient.post()
            .uri("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .body(accountMono, Account.class)
            .retrieve()
            .bodyToMono(Void.class);
```

#### 如果是普通的pojo 对象，则用 asynBody()方法

```java
Account account = ... ;
Mono<Void> result = webClient.post()
            .uri("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .syncBody(account)
            .retrieve()
            .bodyToMono(Void.class);
```

### 表单和文件提交

> 表单可以通过  asynBody()  传递 MultiValueMap  对象，而文件则 通过构建 MultipartBodyBuilder 对象，然后通过 build()  方法构建 MultiValueMap  对象

## RSocket 协议

RSocket 是一种新的第 7 层语言无关的应用网络协议，用来解决单一的请求-响应模式以及现有网络传输协议所存在的问题，提供 Java、JavaScript、C++ 和 Kotlin 等多种语言的实现版本。

RSocket 是一个二进制的协议，以异步消息的方式提供 4 种交互模式：

+ 请求-响应模式(request/response):这是最典型也是最常见的模式。发送方在发送消息给接收方之后，等待与之对应的相应消息。
+ 请求-响应流模式(request/stream):发送方的每个请求消息，都对应于接收方的一个消息流作响应。
+ 即发-即忘模式(fire-and-forget):发送方的请求消息没有与之对应的响应。
+ 通道模式(channel):在发送方和接收方之间建立一个双向传输的通道。

## R2DBC 

它使得关系型数据库具有响应性

![对比图](./images/2021-05-13-1.png)

### 核心组件

+ R2DBC SPI：定义了实现驱动程序的简约API。该API非常简洁，以便彻底减少驱动程序实现着必须遵守的API。SPI并不是面向业务开发人员的API，不适合在应用程序代码中直接使用；相反，它面向的是框架开发人员，用来设计并实现专用的客户端库。任何人都可以直接使用SPI或者通过R2DBC SPI 实现自己的客户端库。
+ R2DBC 客户端：提供了一个人性化的API 和帮助类，可将用户请求转换为SPI，也就是说面向业务开发人员提供了对底层SPI的访问入口。
+ R2DBC 驱动：截至目前，为 PostgreSQL、H2、Microsoft SQL Server、MariaDB 以及 MySQL 提供了 R2DBC 驱动程序。

## Spring Cloud Stream

Spring Cloud Streams 为异步跨服务消息通信提供了简化的编程模型。Spring Cloud Stream 能够构建具有高度伸缩性的应用程序，而无须处理过于复杂的配置，也无须深入了解特定的消息中间件。

### 工作流程

Spring Cloud Stream 中有三个角色，即消息的发布者、消费者以及消息通信系统本身，以消息通信系统为中心，整个工作流程表现为一种对称结构，如下图所示。

![工作流程](./images/2021-05-13-2.png)

在上图中，充当消息发布者的服务 A 根据业务需要产生消息发送的需求，Spring Cloud Stream 中的 Source 组件是真正生成消息的组件，然后消息通过 Channel 传送到 Binder，这里的 Binder 是一个抽象组件，通过 Binder，Channel 可以与特定的消息中间件进行通信。在 Spring Cloud Stream 中，目前已经内置集成的消息中间件实现工具包括 RabbitMQ 和 Kafka。

另一方面，消息消费者则同样通过 Binder 从消息中间件中获取消息，消息将通过 Channel 流转到 Sink 组件。这里的 Sink 组件是服务级别的，即类似上图中服务 B 的不同服务可能会实现不同的 Sink 组件，分别对消息进行不同业务上的处理。

### 核心组件

- Binder

Binder 是 Spring Cloud Stream 的一个核心概念，它充当了服务与消息中间件之间的桥梁。通过 Binder，我们可以很方便地连接 RabbitMQ、Kafka 等消息中间件。同时，Binder 组件也为我们提供了消费者分组和消息分区等特性，关于这些特性我会在“20 | 消息消费：如何选择可用的高级开发技巧？”中详细介绍。Binder 的核心价值就在于我们可以直接使用这些特性，而不需要了解其背后的各种消息中间件在实现上的差异。

- Channel

Channel 即通道，是对队列（Queue）的一种抽象。我们知道在消息中间件中，队列的作用就是实现存储转发的媒介，消息发布者所生成的消息都将保存在队列中并由消息消费者进行消费。通道的名称对应的就是队列的名称，但是作为一种抽象和封装，各个消息中间件所特有的队列概念并不会直接暴露在业务代码中，而是通过通道来对队列进行配置。

- Source 和 Sink

我们可以把 Source 和 Sink 简单理解为输出和输入，但还是要明确这里输入输出的参照对象是 Spring Cloud Stream 自身，即从 Spring Cloud Stream 发布消息的组件就是 Source，而通过 Spring Cloud Stream 接收消息的就是 Sink。

在 Spring Cloud Stream 中，表面上 Source 组件是使用一个 POJO 对象来作为需要发布的消息，通过将该对象进行序列化（默认的序列化方式是 JSON）然后发布到通道中。另一方面，Sink 组件监听通道并等待消息的到来，一旦有可用消息，Sink 将该消息反序列化为一个 POJO 对象并用于处理业务逻辑。而在内部，Spring Cloud Stream 在实现这一过程中需要借助 Spring 家族中的底层消息处理机制。

## 响应流 测试

### reactor-test 测试

<font color=red>引入 reactor-test maven 依赖</font>

```java
@Test
public void testStepVerifier() {
        Flux<String> helloWorld = Flux.just("Hello", "World");
 
        StepVerifier.create(helloWorld)  // 初始化
           .expectNext("Hello")
           .expectNext("World")  // 验证数据流中的内容
           .expectComplete()   //完成数据流断言
           .verify();     // 启动测试
}
```

###  spring boot 应用程序测试

<font color=red>spring-boot-starter-test maven 依赖引入</font>

```java
@SpringBootTest
@RunWith(SpringRunner.class)

//  SpringBootTest 可以设置如下两个参数
classes   //  springBoot 启动类，即被Application 修饰的
webEnvironment  // 容器环境，有四个选项可供选择
MOCK：加载 WebApplicationContext 并提供一个 Mock 的容器环境，内置的容器并没有真正启动。
RANDOM_PORT：加载 EmbeddedWebApplicationContext 并提供一个真实的容器环境，也就是说会启动内置容器，并使用随机端口。
DEFINED_PORT ：这个配置也是通过加载 EmbeddedWebApplicationContext 提供一个真实的容器环境，但使用的是默认的端口，如果没有配置端口就使用 8080。
NONE：加载 ApplicationContext 但并不提供任何真实的容器环境。
```

### 测试 响应式 Repository 组件，mongodb

<font color=red>引入 de.flapdoodle.embed.mongo maven 依赖</font>

```java
@RunWith(SpringRunner.class)
@DataMongoTest  // 默认使用了内置 mongodb
//  (excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class) 可以排除内置的
public class EmbeddedAccountRepositoryTest {
 
    @Autowired
    ReactiveAccountRepository repository;
 
    @Autowired
    ReactiveMongoOperations operations;
    
    @Before
    public void setUp() {
        operations.dropCollection(Account.class);
 
        operations.insert(new Account("Account1", "AccountCode1", "AccountName1"));
        operations.insert(new Account("Account2", "AccountCode2", "AccountName2"));
        
        operations.findAll(Account.class).subscribe(
               account -> {
                   System.out.println(account.getId()
               );}
        );
    }
    
    @Test
    public void testGetAccountByAccountName() {
        Mono<Account> account = repository.findAccountByAccountName("AccountName1");
 
        StepVerifier.create(account)
           .expectNextMatches(results -> {
               assertThat(results.getAccountCode()).isEqualTo("AccountCode1");
               assertThat(results.getAccountName()).isEqualTo("AccountName1");
               return true;
        });
    }
}
```

### 测试 响应式 service 组件

测试 service 需要将 service与repository 隔离

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {
 
    @Autowired
    AccountService service;
    
    @MockBean
    ReactiveAccountRepository repository;
 
    @Test
    public void testGetAccountByAccountName() {
        Account mockAccount = new Account("Account1", "AccountCode1", "AccountName1");
        
     given(repository.findAccountByAccountName("AccountName1")).willReturn(Mono.just(mockAccount));
        
        Mono<Account> account = service.getAccountByAccountName("AccountName1");
        
        StepVerifier.create(account).expectNextMatches(results -> {
            assertThat(results.getAccountCode()).isEqualTo("AccountCode1");
            assertThat(results.getAccountName()).isEqualTo("AccountName1");
           return true;
        }).verifyComplete();
        
    }   
}
```

### 测试响应式 controller 组件

```java
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
 
import com.springcss.account.controller.AccountController;
import com.springcss.account.domain.Account;
import com.springcss.account.service.AccountService;
 
import reactor.core.publisher.Mono;
 
@RunWith(SpringRunner.class)
@WebFluxTest(controllers = AccountController.class)
public class AccountControllerTest {
 
    @Autowired
    WebTestClient webClient;
 
    @MockBean
    AccountService service;
 
    @Test
    public void testGetAccountById() {
        Account mockAccount = new Account("Account1", "AccountCode1", "AccountName1");       
 
     given(service.getAccountById("Account1")).willReturn(Mono.just(mockAccount));
 
        EntityExchangeResult<Account> result = webClient.get()
               .uri("http://localhost:8082/accounts/{accountId}", "Account1").exchange().expectStatus()
               .isOk().expectBody(Account.class).returnResult();
 
        verify(service).getAccountById("Account1");
        verifyNoMoreInteractions(service);
        
     assertThat(result.getResponseBody().getId()).isEqualTo("Account1");
     assertThat(result.getResponseBody().getAccountCode()).isEqualTo("AccountCode1");
    }
}
```



# 网络编程

## java nio

### 介绍

#### 核心成员 

==channel、buffer、selector==

#### selector

##### 创建

```java
Selector selector = Selector.open();
```

##### channel 注册

```java
// 必须为非阻塞模式，故不能使用FileChannel
channel.configureBlocking(false);
// register 有四种事件
// Connect =>  SelectionKey.OP_CONNECT   代表一个管道成功连接另一个服务
// Accept  => SelectionKey.OP_ACCEPT  服务端 socket 管道接收到连接请求
// Read => Selection.OP_READ    管道内有数据，可以被读取
// Write => Selection.OP_WRITE  管道能写入
SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
// 如果感兴趣多个事件
int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;    
```

##### Interest Set

==查看 selectKey上注册的类型==

```java
int interestSet = selectionKey.interestOps();

boolean isInterestedInAccept  = SelectionKey.OP_ACCEPT  == (interests & SelectionKey.OP_ACCEPT);
boolean isInterestedInConnect = SelectionKey.OP_CONNECT == (interests & SelectionKey.OP_CONNECT);
boolean isInterestedInRead    = SelectionKey.OP_READ    == (interests & SelectionKey.OP_READ);
boolean isInterestedInWrite   = SelectionKey.OP_WRITE   == (interests & SelectionKey.OP_WRITE);
```

##### Ready Set

==管道中准备就绪的类型==

```java
int readySet = selectionKey.readyOps();
selectionKey.isAcceptable();
selectionKey.isConnectable();
selectionKey.isReadable();
selectionKey.isWritable();
```

##### selector+channel

==获取selectKey上的channel、selector==

```java
Channel  channel  = selectionKey.channel();

Selector selector = selectionKey.selector();  
```

##### 附加额外信息

```java
selectionKey.attach(theObject);

Object attachedObj = selectionKey.attachment();
SelectionKey key = channel.register(selector, SelectionKey.OP_READ, theObject);
```

##### select()

==获取 ready的管道。但是每次只能有一个管道准备好，即使当前已经有多个准备好了==

##### selectedKeys()

==显示所有就绪的管道的 SelectKey==

```java
Set<SelectionKey> selectedKeys = selector.selectedKeys();    
```

##### full selector case

```java
Selector selector = Selector.open();
channel.configureBlocking(false);
SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
while(true) {
  int readyChannels = selector.selectNow();
  if(readyChannels == 0) continue;
  Set<SelectionKey> selectedKeys = selector.selectedKeys();
  Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
  while(keyIterator.hasNext()) {
    SelectionKey key = keyIterator.next();
    if(key.isAcceptable()) {
        // a connection was accepted by a ServerSocketChannel.
    } else if (key.isConnectable()) {
        // a connection was established with a remote server.
    } else if (key.isReadable()) {
        // a channel is ready for reading
    } else if (key.isWritable()) {
        // a channel is ready for writing
    }
    keyIterator.remove();
  }
}
```







#### buffer

缓冲区本质上是一个内存块，管道可以向其中写入数据，也可以从它那里获取数据。缓冲区基本使用步骤：

1、向缓冲区中写入数据

2、调用  buffer.flip()    // 切换模式，从写模式切换到读模式

3、从缓冲区中读取数据

4、调用buffer.clear()或buffer.compact()  // clear()会将缓冲区的数据清空，且切换到读模式。compact()会将已读取数据移除，未读数据移至缓冲区首位，然后切换到写模式，在数据末尾开始写入。

```java
// 示例
RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
FileChannel inChannel = aFile.getChannel();

//create buffer with capacity of 48 bytes
ByteBuffer buf = ByteBuffer.allocate(48);

int bytesRead = inChannel.read(buf); //read into buffer.
while (bytesRead != -1) {

  buf.flip();  //make buffer ready for read

  while(buf.hasRemaining()){
      System.out.print((char) buf.get()); // read 1 byte at a time
  }

  buf.clear(); //make buffer ready for writing
  bytesRead = inChannel.read(buf);
}
aFile.close();
```

##### 属性介绍

==capacity、position、limit==

+ capacity：缓冲区的容量
+ position：写模式从0开始写入数据，最大数值为 capacity-1。flip() 切换到读模式融，仍从0开始读取数据。
+ limit：边界。写模式等同于capacity，读模式下为写模式下的position。

##### 类型

==ByteBuffer、MappedByteBuffer、CharBuffer、DoubleBuffer、FloatBuffer、IntBuffer、LongBuffer、ShortBuffer==

##### 初始化

```java
ByteBuffer buf = ByteBuffer.allocate(48);
CharBuffer buf = CharBuffer.allocate(1024);
```

##### 写入数据

```java
int bytesRead = inChannel.read(buf); //read into buffer.
buf.put(127);    // 自己将数据写入
```

##### 读取数据

```java
//read from buffer into channel.
int bytesWritten = inChannel.write(buf);
byte aByte = buf.get();    // 各种方式可以选择
```

##### 其他

```java
rewind()   //将 position置为0
clear()  // position 置为0，limit=capacity。其中的数据仍在，为覆盖写。只是忘记了读的位置
compact() // 移除已读数据，将未读的数据移至最前面。position更新为未读长度。
mark()  // 标记position位置
reset()  // 回退到 mark()标记的位置
equals() // 比较两个缓冲区，相同条件：类型一样，长度一样，数据一样
compareTo() //
```

#### channel

==可以异步读取或写入缓冲区==

##### 常见类型

FileChannel、DatagramChannel、SocketChannel、ServerSocketChannel

**可见，支持文件、tcp、udp**

##### 示例

```
 RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
    FileChannel inChannel = aFile.getChannel();

    ByteBuffer buf = ByteBuffer.allocate(48);

    int bytesRead = inChannel.read(buf);
    while (bytesRead != -1) {

      System.out.println("Read " + bytesRead);
      buf.flip();

      while(buf.hasRemaining()){
          System.out.print((char) buf.get());
      }

      buf.clear();
      bytesRead = inChannel.read(buf);
    }
    aFile.close();
```

##### FileChannel

###### 打开管道

```java
RandomAccessFile aFile     = new RandomAccessFile("data/nio-data.txt", "rw");
FileChannel      inChannel = aFile.getChannel();
```

###### 从管道中读取数据

```java
ByteBuffer buf = ByteBuffer.allocate(48);
int bytesRead = inChannel.read(buf);  // read() 方法返回的是读取的字节数
```

###### 将数据写入管道

```java
String newData = "New String to write to file..." + System.currentTimeMillis();

ByteBuffer buf = ByteBuffer.allocate(48);
buf.clear();
buf.put(newData.getBytes());

buf.flip();

while(buf.hasRemaining()) {  // 一直写入直到 buf 中没有数据
    channel.write(buf);
}
```

###### 获取更改管道的读写位置

```java
long pos=channel.position();// 获取位置

channel.position(pos +123);  //设置位置
// 如果读的时候将位置设置为大于capacity，则返回-1
// 如果写的时候将位置设置为大于capacity，则会扩容在写入。这种情况下文件空洞， 即数据间存在间隙。
```

###### 大小

```java
long fileSize = channel.size();   // 返回与该管道对应的文件大小
```

###### 截取

```java
channel.truncate(1024); // 截取指定字节长度的数据
```

###### 写入磁盘

```java
channel.force(true);
```

##### SocketChannel

==tcp连接==

###### 创建管道

1. You open a `SocketChannel` and connect to a server somewhere on the internet.
2. A `SocketChannel` can be created when an incoming connection arrives at a [`ServerSocketChannel`](http://tutorials.jenkov.com/java-nio/server-socket-channel.html).

###### 打开管道

```java
SocketChannel socketChannel = SocketChannel.open();
socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));
```

##### DatagramChannel

==接收或者发送 udp包裹==

###### 打开管道

```java
DatagramChannel channel = DatagramChannel.open();
channel.socket().bind(new InetSocketAddress(9999));
```

###### 接收数据

```java
ByteBuffer buf = ByteBuffer.allocate(48);
buf.clear();
// 如果发送的数据大于缓冲区的数据，则多余的数据默认被丢弃
channel.receive(buf);
```

###### 发送数据

```java
String newData = "New String to write to file..."
                    + System.currentTimeMillis();
    
ByteBuffer buf = ByteBuffer.allocate(48);
buf.clear();
buf.put(newData.getBytes());
buf.flip();
// 这种发送 无法获得任何反馈信息
int bytesSent = channel.send(buf, new InetSocketAddress("jenkov.com", 80));
```

###### 连接具体地址

```java
// 它没有创建真实的连接，只是锁定了管道只能向某个固定的地址收发消息
channel.connect(new InetSocketAddress("jenkov.com", 80));    
// 可以向正常管道那样读写
int bytesRead = channel.read(buf); 
int bytesWritten = channel.write(buf);
```



#### Scatter / Gather

##### scatter 

==将管道(channel)内的数据写入到多个缓冲区(buffer)==

```java
ByteBuffer header = ByteBuffer.allocate(128);
ByteBuffer body   = ByteBuffer.allocate(1024);

ByteBuffer[] bufferArray = { header, body };

channel.read(bufferArray);
```

<font color=red>它的写入是按数组中出现的顺序，且是逐一写入的。故它不支持变化长度的数据写入，只支持定长的</font>

##### gather

==将多个缓冲区的数据写入到一个管道中==

```java
ByteBuffer header = ByteBuffer.allocate(128);
ByteBuffer body   = ByteBuffer.allocate(1024);

//write data into buffers

ByteBuffer[] bufferArray = { header, body };

channel.write(bufferArray);
```

<font color=red>它写入的数据是按照数组中出现的顺序，且每个buf 中的数据返回是position-limit，可以支持变长写入</font>

### jdk  NIO 存在的问题

- **Java NIO 的 API 非常复杂。** 要写出成熟可用的 Java NIO 代码，需要熟练掌握 JDK 中的 Selector、ServerSocketChannel、SocketChannel、ByteBuffer 等组件，还要理解其中一些反人类的设计以及底层原理，这对新手来说是非常不友好的。
- **如果直接使用 Java NIO 进行开发，难度和开发量会非常大**。我们需要自己补齐很多可靠性方面的实现，例如，网络波动导致的连接重连、半包读写等。这就会导致一些本末倒置的情况出现：核心业务逻辑比较简单，但补齐其他公共能力的代码非常多，开发耗时比较长。这时就需要一个统一的 NIO 框架来封装这些公共能力了。
- **JDK 自身的 Bug**。其中比较出名的就要属 Epoll Bug 了，这个 Bug 会导致 Selector 空轮询，CPU 使用率达到 100%，这样就会导致业务逻辑无法执行，降低服务性能。

### java IO 和 java NIO区别

|         IO         |          NIO           |
| :----------------: | :--------------------: |
| 以流为方向(stream) | 以缓冲区为方向(buffer) |
|      阻塞 IO       |        非阻塞IO        |
|                    |       Selectors        |



## nettty的优点

Netty 在 JDK 自带的 NIO API 基础之上进行了封装，解决了 JDK 自身的一些问题

- 入门简单，使用方便，文档齐全，无其他依赖，只依赖 JDK 就够了。
- 高性能，高吞吐，低延迟，资源消耗少。
- 灵活的线程模型，支持阻塞和非阻塞的I/O 模型。
- 代码质量高，目前主流版本基本没有 Bug。

### 常用 解码器

#### 长度域解码器 LengthFieldBasedFrameDecoder

```
// 长度字段的偏移量，也就是存放长度数据的起始位置
private final int lengthFieldOffset; 
// 长度字段所占用的字节数
private final int lengthFieldLength; 
/*
 * 消息长度的修正值
 *
 * 在很多较为复杂一些的协议设计中，长度域不仅仅包含消息的长度，而且包含其他的数据，如版本号、数据类型、数据状态等，那么这时候我们需要使用 lengthAdjustment 进行修正
 * 
 * lengthAdjustment = 包体的长度值 - 长度域的值
 *
 */
private final int lengthAdjustment; 
// 解码后需要跳过的初始字节数，也就是消息内容字段的起始位置
private final int initialBytesToStrip;
// 长度字段结束的偏移量，lengthFieldEndOffset = lengthFieldOffset + lengthFieldLength
private final int lengthFieldEndOffset;
```

##### 与固定长度解码器和特定分隔符解码器相似的属性

```java
private final int maxFrameLength; // 报文最大限制长度
private final boolean failFast; // 是否立即抛出 TooLongFrameException，与 maxFrameLength 搭配使用
private boolean discardingTooLongFrame; // 是否处于丢弃模式
private long tooLongFrameLength; // 需要丢弃的字节数
private long bytesToDiscard; // 累计丢弃的字节数
```



##### 示例1  典型的基于消息长度 + 消息内容的解码。

```
BEFORE DECODE (14 bytes)         AFTER DECODE (14 bytes)
+--------+----------------+      +--------+----------------+
| Length | Actual Content |----->| Length | Actual Content |
| 0x000C | "HELLO, WORLD" |      | 0x000C | "HELLO, WORLD" |
+--------+----------------+      +--------+----------------+
```

上述协议是最基本的格式，报文只包含消息长度 Length 和消息内容 Content 字段，其中 Length 为 16 进制表示，共占用 2 字节，Length 的值 0x000C 代表 Content 占用 12 字节。该协议对应的解码器参数组合如下：

- lengthFieldOffset = 0，因为 Length 字段就在报文的开始位置。
- lengthFieldLength = 2，协议设计的固定长度。
- lengthAdjustment = 0，Length 字段只包含消息长度，不需要做任何修正。
- initialBytesToStrip = 0，解码后内容依然是 Length + Content，不需要跳过任何初始字节。

##### 示例 2：解码结果需要截断。

```
BEFORE DECODE (14 bytes)         AFTER DECODE (12 bytes)
+--------+----------------+      +----------------+
| Length | Actual Content |----->| Actual Content |
| 0x000C | "HELLO, WORLD" |      | "HELLO, WORLD" |
+--------+----------------+      +----------------+
```

示例 2 和示例 1 的区别在于解码后的结果只包含消息内容，其他的部分是不变的。该协议对应的解码器参数组合如下：

- lengthFieldOffset = 0，因为 Length 字段就在报文的开始位置。
- lengthFieldLength = 2，协议设计的固定长度。
- lengthAdjustment = 0，Length 字段只包含消息长度，不需要做任何修正。
- initialBytesToStrip = 2，跳过 Length 字段的字节长度，解码后 ByteBuf 中只包含 Content字段。

##### 示例 3：长度字段包含消息长度和消息内容所占的字节。

```
BEFORE DECODE (14 bytes)         AFTER DECODE (14 bytes)
+--------+----------------+      +--------+----------------+
| Length | Actual Content |----->| Length | Actual Content |
| 0x000E | "HELLO, WORLD" |      | 0x000E | "HELLO, WORLD" |
+--------+----------------+      +--------+----------------+
```

与前两个示例不同的是，示例 3 的 Length 字段包含 Length 字段自身的固定长度以及 Content 字段所占用的字节数，Length 的值为 0x000E（2 + 12 = 14 字节），在 Length 字段值（14 字节）的基础上做 lengthAdjustment（-2）的修正，才能得到真实的 Content 字段长度，所以对应的解码器参数组合如下：

- lengthFieldOffset = 0，因为 Length 字段就在报文的开始位置。
- lengthFieldLength = 2，协议设计的固定长度。
- lengthAdjustment = -2，长度字段为 14 字节，需要减 2 才是拆包所需要的长度。
- initialBytesToStrip = 0，解码后内容依然是 Length + Content，不需要跳过任何初始字节。

##### 示例 4：基于长度字段偏移的解码。

```
BEFORE DECODE (17 bytes)                      AFTER DECODE (17 bytes)
+----------+----------+----------------+      +----------+----------+----------------+
| Header 1 |  Length  | Actual Content |----->| Header 1 |  Length  | Actual Content |
|  0xCAFE  | 0x00000C | "HELLO, WORLD" |      |  0xCAFE  | 0x00000C | "HELLO, WORLD" |
+----------+----------+----------------+      +----------+----------+----------------+
```

示例 4 中 Length 字段不再是报文的起始位置，Length 字段的值为 0x00000C，表示 Content 字段占用 12 字节，该协议对应的解码器参数组合如下：

- lengthFieldOffset = 2，需要跳过 Header 1 所占用的 2 字节，才是 Length 的起始位置。
- lengthFieldLength = 3，协议设计的固定长度。
- lengthAdjustment = 0，Length 字段只包含消息长度，不需要做任何修正。
- initialBytesToStrip = 0，解码后内容依然是完整的报文，不需要跳过任何初始字节。

##### 示例 5：长度字段与内容字段不再相邻。

```
BEFORE DECODE (17 bytes)                      AFTER DECODE (17 bytes)
+----------+----------+----------------+      +----------+----------+----------------+
|  Length  | Header 1 | Actual Content |----->|  Length  | Header 1 | Actual Content |
| 0x00000C |  0xCAFE  | "HELLO, WORLD" |      | 0x00000C |  0xCAFE  | "HELLO, WORLD" |
+----------+----------+----------------+      +----------+----------+----------------+
```

示例 5 中的 Length 字段之后是 Header 1，Length 与 Content 字段不再相邻。Length 字段所表示的内容略过了 Header 1 字段，所以也需要通过 lengthAdjustment 修正才能得到 Header + Content 的内容。示例 5 所对应的解码器参数组合如下：

- lengthFieldOffset = 0，因为 Length 字段就在报文的开始位置。
- lengthFieldLength = 3，协议设计的固定长度。
- lengthAdjustment = 2，由于 Header + Content 一共占用 2 + 12 = 14 字节，所以 Length 字段值（12 字节）加上 lengthAdjustment（2 字节）才能得到 Header + Content 的内容（14 字节）。
- initialBytesToStrip = 0，解码后内容依然是完整的报文，不需要跳过任何初始字节。

##### 示例 6：基于长度偏移和长度修正的解码。

```
BEFORE DECODE (16 bytes)                       AFTER DECODE (13 bytes)
+------+--------+------+----------------+      +------+----------------+
| HDR1 | Length | HDR2 | Actual Content |----->| HDR2 | Actual Content |
| 0xCA | 0x000C | 0xFE | "HELLO, WORLD" |      | 0xFE | "HELLO, WORLD" |
+------+--------+------+----------------+      +------+----------------+
```

示例 6 中 Length 字段前后分为别 HDR1 和 HDR2 字段，各占用 1 字节，所以既需要做长度字段的偏移，也需要做 lengthAdjustment 修正，具体修正的过程与 示例 5 类似。对应的解码器参数组合如下：

- lengthFieldOffset = 1，需要跳过 HDR1 所占用的 1 字节，才是 Length 的起始位置。
- lengthFieldLength = 2，协议设计的固定长度。
- lengthAdjustment = 1，由于 HDR2 + Content 一共占用 1 + 12 = 13 字节，所以 Length 字段值（12 字节）加上 lengthAdjustment（1）才能得到 HDR2 + Content 的内容（13 字节）。
- initialBytesToStrip = 3，解码后跳过 HDR1 和 Length 字段，共占用 3 字节。

##### 示例 7：长度字段包含除 Content 外的多个其他字段。

```
BEFORE DECODE (16 bytes)                       AFTER DECODE (13 bytes)
+------+--------+------+----------------+      +------+----------------+
| HDR1 | Length | HDR2 | Actual Content |----->| HDR2 | Actual Content |
| 0xCA | 0x0010 | 0xFE | "HELLO, WORLD" |      | 0xFE | "HELLO, WORLD" |
+------+--------+------+----------------+      +------+----------------+
```

示例 7 与 示例 6 的区别在于 Length 字段记录了整个报文的长度，包含 Length 自身所占字节、HDR1 、HDR2 以及 Content 字段的长度，解码器需要知道如何进行 lengthAdjustment 调整，才能得到 HDR2 和 Content 的内容。所以我们可以采用如下的解码器参数组合：

- lengthFieldOffset = 1，需要跳过 HDR1 所占用的 1 字节，才是 Length 的起始位置。
- lengthFieldLength = 2，协议设计的固定长度。
- lengthAdjustment = -3，Length 字段值（16 字节）需要减去 HDR1（1 字节） 和 Length 自身所占字节长度（2 字节）才能得到 HDR2 和 Content 的内容（1 + 12 = 13 字节）。
- initialBytesToStrip = 3，解码后跳过 HDR1 和 Length 字段，共占用 3 字节。

## 内存分配器

### ptmalloc

ptmalloc 是基于 glibc 实现的内存分配器，它是一个标准实现，所以兼容性较好。pt 表示 per thread 的意思。当然 ptmalloc 确实在多线程的性能优化上下了很多功夫。由于过于考虑性能问题，多线程之间内存无法实现共享，只能每个线程都独立使用各自的内存，所以在内存开销上是有很大浪费的。

### tcmalloc 

tcmalloc 出身于 Google，全称是 thread-caching malloc，所以 tcmalloc 最大的特点是带有线程缓存，tcmalloc 非常出名，目前在 Chrome、Safari 等知名产品中都有所应有。tcmalloc 为每个线程分配了一个局部缓存，对于小对象的分配，可以直接由线程局部缓存来完成，对于大对象的分配场景，tcmalloc 尝试采用自旋锁来减少多线程的锁竞争问题。

### jemalloc 

jemalloc 借鉴了 tcmalloc 优秀的设计思路，所以在架构设计方面两者有很多相似之处，同样都包含 thread cache 的特性。但是 jemalloc 在设计上比 ptmalloc 和 tcmalloc 都要复杂，jemalloc 将内存分配粒度划分为 Small、Large、Huge 三个分类，并记录了很多 meta 数据，所以在空间占用上要略多于 tcmalloc，不过在大内存分配的场景，jemalloc 的内存碎片要少于 tcmalloc。tcmalloc 内部采用红黑树管理内存块和分页，Huge 对象通过红黑树查找索引数据可以控制在指数级时间。

## 内存分配算法

常用的内存分配器算法有三个：动态内存分配、伙伴算法和Slab 算法

### 动态内存分配

**动态内存分配（Dynamic memory allocation）又称为堆内存分配，后面简称 DMA**，操作系统根据程序运行过程中的需求即时分配内存，且分配的内存大小就是程序需求的大小。在大部分场景下，只有在程序运行的时候才知道所需要分配的内存大小，如果提前分配可能会分配的大小无法把控，分配太大会浪费空间，分配太小会无法使用。

DMA 是从一整块内存中按需分配，对于分配出的内存会记录元数据，同时还会使用空闲分区链维护空闲内存，便于在内存分配时查找可用的空闲分区，常用的有三种查找策略：

#### ⾸次适应算法（first fit）

空闲分区链以地址递增的顺序将空闲分区以双向链表的形式连接在一起，从空闲分区链中找到第一个满足分配条件的空闲分区，然后从空闲分区中划分出一块可用内存给请求进程，剩余的空闲分区仍然保留在空闲分区链中。该算法**每次都从低地址开始查找，造成低地址部分会不断被分配，同时也会产生很多小的空闲分区。**

#### 循环首次适应算法（next fit）

该算法是由首次适应算法的变种，循环首次适应算法不再是每次从链表的开始进行查找，而是从上次找到的空闲分区的下⼀个空闲分区开始查找。该算法相比⾸次适应算法空闲分区的**分布更加均匀，而且查找的效率有所提升，但是正因为如此会造成空闲分区链中大的空闲分区会越来越少。**

#### 最佳适应算法（best fit）

空闲分区链以空闲分区大小递增的顺序将空闲分区以双向链表的形式连接在一起，每次从空闲分区链的开头进行查找，这样第一个满足分配条件的空间分区就是最优解。**该算法的空间利用率更高，但同样也会留下很多较难利用的小空闲分区，由于每次分配完需要重新排序，所以会有造成性能损耗。**

### 伙伴算法

伙伴算法是一种非常经典的内存分配算法，它采用了分离适配的设计思想，将物理内存按照 2 的次幂进行划分，内存分配时也是按照 2 的次幂大小进行按需分配，例如 4KB、 8KB、16KB 等。假设我们请求分配的内存大小为 10KB，那么会按照 16KB 分配。

伙伴算法把内存划分为 11 组不同的 2 次幂大小的内存块集合，每组内存块集合都用双向链表连接。链表中每个节点的内存块大小分别为 1、2、4、8、16、32、64、128、256、512 和 1024 个连续的 Page，例如第一组链表的节点为 2^0 个连续 Page，第二组链表的节点为 2^1 个连续 Page，以此类推。

假设我们需要分配 10K 大小的内存块，看下**伙伴算法的具体分配过程**：

+ 首先需要找到存储 2^4 连续 Page 所对应的链表，即数组下标为 4；

+ 查找 2^4 链表中是否有空闲的内存块，如果有则分配成功；

+ 如果 2^4 链表不存在空闲的内存块，则继续沿数组向上查找，即定位到数组下标为 5 的链表，链表中每个节点存储 2^5 的连续 Page；

+ 如果 2^5 链表中存在空闲的内存块，则取出该内存块并将它分割为 2 个 2^4 大小的内存块，其中一块分配给进程使用，剩余的一块链接到 2^4 链表中。

以上是伙伴算法的分配过程，那么释放内存时候伙伴算法又会发生什么行为呢？当进程使用完内存归还时，需要检查其伙伴块的内存是否释放，所谓伙伴块是不仅大小相同，而且两个块的地址是连续的，其中低地址的内存块起始地址必须为 2 的整数次幂。如果伙伴块是空闲的，那么就会将两个内存块合并成更大的块，然后重复执行上述伙伴块的检查机制。直至伙伴块是非空闲状态，那么就会将该内存块按照实际大小归还到对应的链表中。频繁的合并会造成 CPU 浪费，所以并不是每次释放都会触发合并操作，当链表中的内存块个数小于某个阈值时，并不会触发合并操作。

**由此可见，伙伴算法有效地减少了外部碎片，但是有可能会造成非常严重的内部碎片，最严重的情况会带来 50% 的内存碎片。**

### Slab算法

因为伙伴算法都是以 Page 为最小管理单位，在小内存的分配场景，伙伴算法并不适用，如果每次都分配一个 Page 岂不是非常浪费内存，因此 Slab 算法应运而生了。Slab 算法在伙伴算法的基础上，对小内存的场景专门做了优化，采用了内存池的方案，解决内部碎片问题。

Linux 内核使用的就是 Slab 算法，因为内核需要频繁地分配小内存，所以 Slab 算法提供了一种高速缓存机制，使用缓存存储内核对象，当内核需要分配内存时，基本上可以通过缓存中获取。此外 Slab 算法还可以支持通用对象的初始化操作，避免对象重复初始化的开销。下图是 Slab 算法的结构图，Slab 算法实现起来非常复杂，本文只做一个简单的了解

<img src="./images/2021-04-02-2.jpg" alt="图解" style="zoom:67%;" />

在 Slab 算法中维护着大小不同的 Slab 集合，在最顶层是 cache_chain，cache_chain 中维护着一组 kmem_cache 引用，kmem_cache 负责管理一块固定大小的对象池。通常会提前分配一块内存，然后将这块内存划分为大小相同的 slot，不会对内存块再进行合并，同时使用位图 bitmap 记录每个 slot 的使用情况。

kmem_cache 中包含三个 Slab 链表：完全分配使用 slab_full、部分分配使用 slab_partial和完全空闲 slabs_empty，这三个链表负责内存的分配和释放。每个链表中维护的 Slab 都是一个或多个连续 Page，每个 Slab 被分配多个对象进行存储。Slab 算法是基于对象进行内存管理的，它把相同类型的对象分为一类。当分配内存时，从 Slab 链表中划分相应的内存单元；当释放内存时，Slab 算法并不会丢弃已经分配的对象，而是将它保存在缓存中，当下次再为对象分配内存时，直接会使用最近释放的内存块。

**单个 Slab 可以在不同的链表之间移动，例如当一个 Slab 被分配完，就会从 slab_partial 移动到 slabs_full，当一个 Slab 中有对象被释放后，就会从 slab_full 再次回到 slab_partial，所有对象都被释放完的话，就会从 slab_partial 移动到 slab_empty。**







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

### Reactor线程模型

<img src="./images/2021-05-26-2.jpg" alt="线程模型" style="zoom:50%;" />

连接注册： ==Channel 建立后，注册至 Reactor 线程中的 Selector 选择器。==

事件轮询：==轮询 Selector 选择器中已注册的所有 Channel 的 I/O 事件。==

事件分发：为准备就绪的 I/O 事件分配相应的处理线程。

任务处理：Reactor 线程还负责任务队列中的非 I/O 任务，每个 Worker 线程从各自维护的任务队列中取出任务异步执行。

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

##### 1. 内存分配&释放

当服务向内存池请求内存时，Netty 会将请求分配的内存数向上取整到最接近的分组大小，然后在该分组的相应层级中从左至右寻找空闲分组。例如，服务请求分配 3 * PageSize 的内存，向上取整得到的分组大小为 4 * PageSize，在该层分组中找到完全空闲的一组内存进行分配即可，如下图：

![fd](./images/Ciqc1F9IluuAFgouAADoPKhjuW4842.png)

当分组大小 4 * PageSize 的内存分配出去后，为了方便下次内存分配，分组被标记为全部已使用（图中红色标记），向上更粗粒度的内存分组被标记为部分已使用（图中黄色标记）。

Netty 使用**完全平衡树的结构**实现了上述算法，这个完全平衡树底层是基于一个 byte 数组构建的，如下图所示：

<img src="./images/Ciqc1F9IlvKACdYpAAF2w22m4sQ981.png" alt="fds" style="zoom:50%;" />

##### 2. 大对象&小对象的处理

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

##### 3. 并发处理

内存分配释放不可避免地会遇到多线程并发场景，PoolChunk 的完全平衡树标记以及 PoolSubpage 的 bitmap 标记都是多线程不安全的，都是需要加锁同步的。为了减少线程间的竞争，Netty 会提前创建多个 PoolArena（默认数量为 2 * CPU 核心数），当线程首次请求池化内存分配，会找被最少线程持有的 PoolArena，并保存线程局部变量 PoolThreadCache 中，实现线程与 PoolArena 的关联绑定。

Netty 还提供了延迟释放的功能，来提升并发性能。当内存释放时，PoolArena 并没有马上释放，而是先尝试将该内存关联的 PoolChunk 和 Chunk 中的偏移位置等信息存入 ThreadLocal 的固定大小缓存队列中，如果该缓存队列满了，则马上释放内存。当有新的分配请求时，PoolArena 会优先访问线程本地的缓存队列，查询是否有缓存可用，如果有，则直接分配，提高分配效率。

## 内存管理

### 堆外内存和堆内内存差异

+ 堆内内存由 JVM GC 自动回收内存，降低了 Java 用户的使用心智，但是 GC 是需要时间开销成本的，堆外内存由于不受 JVM 管理，所以在一定程度上可以降低 GC 对应用运行时带来的影响。

+ 堆外内存需要手动释放，这一点跟 C/C++ 很像，稍有不慎就会造成应用程序内存泄漏，当出现内存泄漏问题时排查起来会相对困难。

+ 当进行网络 I/O 操作、文件读写时，堆内内存都需要转换为堆外内存，然后再与底层设备进行交互，，所以直接使用堆外内存可以减少一次内存拷贝。

+ 堆外内存可以实现进程之间、JVM 多实例之间的数据共享。

## 应用层通信协议

### 消息长度固定

每个数据报文都需要一个固定的长度。当接收方累计读取到固定长度的报文后，就认为已经获得一个完整的消息。当发送方的数据小于固定长度时，则需要空位补齐。

```java
+----+------+------+---+----+
| AB | CDEF | GHIJ | K | LM |
+----+------+------+---+----+
// 假设固定长度为4，则结果为
+------+------+------+------+
| ABCD | EFGH | IJKL | M000 |
+------+------+------+------+
```

==缺陷：无法很好设定固定长度的值，如果长度太大会造成字节浪费，长度太小又会影响消息传输。==

### 特定分隔符

我们可以在每次发送报文的尾部加上**特定分隔符**，接收方就可以根据特殊分隔符进行消息拆分。

```java
+-------------------------+
| AB\nCDEF\nGHIJ\nK\nLM\n |
+-------------------------+
```

==由于在发送报文时尾部需要添加特定分隔符，所以对于分隔符的选择一定要避免和消息体中字符相同，以免冲突。否则可能出现错误的消息拆分。比较推荐的做法是将消息进行编码，例如 base64 编码，然后可以选择 64 个编码字符之外的字符作为特定分隔符。特定分隔符法在消息协议足够简单的场景下比较高效，例如大名鼎鼎的 Redis 在通信过程中采用的就是换行分隔符。==

### 消息长度 + 消息内容

```java
消息头     消息体
+--------+----------+
| Length |  Content |
+--------+----------+
```

**消息长度 + 消息内容**是项目开发中最常用的一种协议，如上展示了该协议的基本格式。消息头中存放消息的总长度，例如使用 4 字节的 int 值记录消息的长度，消息体实际的二进制的字节数据。接收方在解析数据时，首先读取消息头的长度字段 Len，然后紧接着读取长度为 Len 的字节数据，该数据即判定为一个完整的数据报文。

```java
+-----+-------+-------+----+-----+
| 2AB | 4CDEF | 4GHIJ | 1K | 2LM |
+-----+-------+-------+----+-----+
```

## netty开发问题

### @Component不起作用，使用@Controller却无法访问url

[解决办法](#springboot 无法进入controller 中的方法)

### 客户端能与服务端连通，但是发送请求后提示 请求超时(3s范围)

> 1、查看传输是否实现了序列化，如果没有则会在底层序列化失败使得netty没有成功向服务端发送请求，服务端自然没有反馈。



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

## java 变量命名问题

> 请求入参如果带 is前缀变量  貌似会舍弃
>
> 通过序列化  is前缀变量反序列化没问题

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









# 基础知识

## windows 下 java jdk安装

[java-jdk-下载地址](https://www.oracle.com/java/technologies/downloads/archive/)

### jdk

+ 首先在oracle 选择指定版本的java-jdk进行下载

+ 对下载的jdk进行安装，记得修改安装目录，默认是C盘，最好修改。

+ 配置环境变量

  + 配置`JAVA_HOME` 指定为 `java-jdk`安装目录

  ```java
  //  系统变量中添加如下内容，可以指定多个版本的jdk
  JAVA_HOME   =>  %JAVA8_HOME%
  JAVA6_HOME  =>  E:\java\jdk\jdk6\setup
  JAVA8_HOME  =>  E:\java\jdk\jdk8\setup
  ```

  + 配置`CLASSPATH`

  ```java
  .;%JAVA_HOME%\lib;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;
  ```

  + 配置`PATH`

  ```java
  %JAVA_HOME%\bin
  %JAVA_HOME%\jre\bin
  ```

+ 测试

```java
win+r  打开 运行框
输入  cmd
输入  java -version  查看当前 jdk 版本信息
```

### openjdk

####  下载 openjdk-可行

[参考下载步骤](https://www.cnblogs.com/jpfss/p/10936167.html)

> http://hg.openjdk.java.net/
>
> 通过访问  以上链接可以选择指定版本的  openjdk 并进行下载操作
>
> <font color=red>下面的是openjdk 的源代码，不包含native 部分</font>
>
> [参考步骤](https://stackoverflow.com/questions/410756/is-it-possible-to-browse-the-source-of-openjdk-online/410765)
>
> 操作方式 ： 选择  jdk8u> jdk8u 下的jdk>点击左侧tags > 选择指定版本 > 点击 browse >  进入到 src/share/classes > 左侧选择格式下载

#### object.wait 和 object.notify

[参考链接](https://blog.51cto.com/13981400/2374217)

#### 安装redhat 提供的openjdk

[下载链接](https://developers.redhat.com/products/openjdk/download)

下载安装包解压，然后配置环境变量即可

## 问题

### 切换版本失效

[参考解决办法](https://blog.csdn.net/weixin_42241455/article/details/122850351?spm=1001.2014.3001.5501)

> 1、切换版本后需要将原来的cmd关掉重新开一个
>
> 2、在 cmd 中 输入 where java，看看有几个  java.exe  把不是的删掉即可

## 并发队列  

### 阻塞队列

+ ArrayBlockingQueue
+ LinkedBlockingQueue
+ PriorityBlockingQueue
+ DelayQueue
+ SynchronizedQueue
+ LinkedTransferQueue

#### ArrayBlockingQueue

最基础且开发中最常用的阻塞队列，底层采用数组实现的有界队列，初始化需要指定队列的容量。ArrayBlockingQueue 是如何保证线程安全的呢？它内部是使用了一个重入锁 ReentrantLock，并搭配 notEmpty、notFull 两个条件变量 Condition 来控制并发访问。从队列读取数据时，如果队列为空，那么会阻塞等待，直到队列有数据了才会被唤醒。如果队列已经满了，也同样会进入阻塞状态，直到队列有空闲才会被唤醒。

#### LinkedBlockingQueue

内部采用的数据结构是链表，队列的长度可以是有界或者无界的，初始化不需要指定队列长度，默认是 Integer.MAX_VALUE。LinkedBlockingQueue 内部使用了 takeLock、putLock两个重入锁 ReentrantLock，以及 notEmpty、notFull 两个条件变量 Condition 来控制并发访问。采用读锁和写锁的好处是可以避免读写时相互竞争锁的现象，所以相比于 ArrayBlockingQueue，LinkedBlockingQueue 的性能要更好。

#### PriorityBlockingQueue

采用最小堆实现的优先级队列，队列中的元素按照优先级进行排列，每次出队都是返回优先级最高的元素。PriorityBlockingQueue 内部是使用了一个 ReentrantLock 以及一个条件变量 Condition notEmpty 来控制并发访问，不需要 notFull 是因为 PriorityBlockingQueue 是无界队列，所以每次 put 都不会发生阻塞。PriorityBlockingQueue 底层的最小堆是采用数组实现的，当元素个数大于等于最大容量时会触发扩容，在扩容时会先释放锁，保证其他元素可以正常出队，然后使用 CAS 操作确保只有一个线程可以执行扩容逻辑。

#### DelayQueue

一种支持延迟获取元素的阻塞队列，常用于缓存、定时任务调度等场景。DelayQueue 内部是采用优先级队列 PriorityQueue 存储对象。DelayQueue 中的每个对象都必须实现 Delayed 接口，并重写 compareTo 和 getDelay 方法。向队列中存放元素的时候必须指定延迟时间，只有延迟时间已满的元素才能从队列中取出

#### SynchronizedQueue

又称无缓冲队列。比较特别的是 SynchronizedQueue 内部不会存储元素。与 ArrayBlockingQueue、LinkedBlockingQueue 不同，SynchronizedQueue 直接使用 CAS 操作控制线程的安全访问。其中 put 和 take 操作都是阻塞的，每一个 put 操作都必须阻塞等待一个 take 操作，反之亦然。所以 SynchronizedQueue 可以理解为生产者和消费者配对的场景，双方必须互相等待，直至配对成功。在 JDK 的线程池 Executors.newCachedThreadPool 中就存在 SynchronousQueue 的运用，对于新提交的任务，如果有空闲线程，将重复利用空闲线程处理任务，否则将新建线程进行处理。

#### LinkedTransferQueue

一种特殊的无界阻塞队列，可以看作 LinkedBlockingQueues、SynchronousQueue（公平模式）、ConcurrentLinkedQueue 的合体。与 SynchronousQueue 不同的是，LinkedTransferQueue 内部可以存储实际的数据，当执行 put 操作时，如果有等待线程，那么直接将数据交给对方，否则放入队列中。与 LinkedBlockingQueues 相比，LinkedTransferQueue 使用 CAS 无锁操作进一步提升了性能。

### 非阻塞队列

+ ConcurrentLinkedQueue
+ ConcurrentLinkedDeque

#### ConcurrentLinkedQueue

它是一个采用双向链表实现的无界并发非阻塞队列，它属于 LinkedQueue 的安全版本。ConcurrentLinkedQueue 内部采用 CAS 操作保证线程安全，这是非阻塞队列实现的基础，相比 ArrayBlockingQueue、LinkedBlockingQueue 具备较高的性能。

#### ConcurrentLinkedDeque

也是一种采用双向链表结构的无界并发非阻塞队列。与 ConcurrentLinkedQueue 不同的是，ConcurrentLinkedDeque 属于双端队列，它同时支持 FIFO 和 FILO 两种模式，可以从队列的头部插入和删除数据，也可以从队列尾部插入和删除数据，适用于多生产者和多消费者的场景。

### 开源精品非阻塞队列

#### Disruptor 

==一般称它为RingBuffer，其设计初衷是为了解决内存队列的延迟问题。==

[github地址](https://github.com/LMAX-Exchange/disruptor)

#### JCTools

==它是适用于 JVM 并发开发的工具。==

提供了如下四种非阻塞队列：

+ Spsc	单生产者单消费者
+ Mpsc   多生产者单消费者
+ Spmc   单生产者多消费者
+ Mpmc  多生产者多消费者

## 线程

java 中线程存在6种状态，new、runnable、blocked、waiting、timed_waiting、terminated

###  new

> 新创建的线程，此时还没有运行

### runnable

运行中，此状态一般为调用了`thread.start()`方法。在该状态下线程不一定处于运行中，可以能等待运行。因为操作系统是以时间片的方式轮流调用线程的。

### blocked

线程虽然启动但是缺少必要的资源(一般为互斥访问或者定量访问)，则会阻塞，直至拿到缺少的资源便进入 `runnable`状态。

### waiting

它一般由 `thread.wait()`方法触发。它需要特定事件唤醒(即 `notify()`或`notifyall()`)。唤醒后理想状态是变为`runnable`,但是一旦缺少运行时的资源，则会变为`blocked`状态。

### timed waiting

等待一定时间。一般由 `thread.wait(timeout)`或`thread.sleep(timeout)`触发

### terminated

结束，两种情况一般是正常结束或者异常中断。

### 关系图

<img src="./images/2021-06-07-1.png" alt="关系图"  />

### 参考文章

[waiting状态和blocked状态的区别](https://stackoverflow.com/questions/15680422/difference-between-wait-and-blocked-thread-states)

[wait唤醒后进入runnable状态还是blocked状态](https://stackoverflow.com/questions/28378592/java-thread-state-transition-waiting-to-blocked-or-runnable)

### wait() 同sleep()区别

==wait会释放拥有的共享资源，而sleep不会释放==

## 访问控制权限

### public

>表示所有的类都能访问

### protected

> 表示自身、子类及同一个包中类可以访问

### private

> 只有自己能够访问

## 位移

###  >>

> 带符号移动，正数高位补0，负数高位补1

### >>>

> 代表无符号移动，即高位始终填补 0

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

### notepading 正则替换

#### 替换规则

```java
原文

xxxxxx
 name:yichen
 sex:girl

替换后
xxxxxx
"name":yichen
"sex":girl
```

#### 方法

```java
查找目标： \s(\w*):
替换：  "$1":
```

####  空行替换

```java
^\s*\r\n
```

#### 中文替换

```java
替换中文
[\u4e00-\u9fa5]*
替换中间所有字符，直至 ]
[a-zA-z0-9\{\},:" \u4e00-\u9fa5]*\[
```





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

## 代码监控  sonarqube







# 生活技巧

## 宽带连接

### 连接wifi 提示 无网络 安全

#### 具体内容描述

> 同一个wifi下，其他电脑能连接上。。
>
> 你的设备已连接,并且可以访问本地网络中的其他设备,但可能无法访问 Internet.如果你的流量套餐有限制,则你可以此网络设置为按流量计费的连接,或者更改其他属性。

#### 解决办法

> 删除原先的wifi连接配置，
>
> 网络和Internet设置  >  管理已知网络  >   选中对应的网络  > 点击忘记即可

## xshell

### 日志展示不全

[参考方法](https://www.cnblogs.com/niceland/p/0x0009.html)

> 文件  》  会话属性   》   终端    》 缓冲区大小    》 默认的1024行改为4096

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



## window   

### 安装子系统 (WSL)

#### 简便方法

> 通过 Microsoft store  搜索  unbutu  进行安装
>
> 默认安装路径   C:\Users\wine_light\AppData\Local\Packages\CanonicalGroupLimited.Unbutu\

#### 通过安装包安装

> // 下载安装包
>
> https://docs.microsoft.com/zh-cn/windows/wsl/install-manual
>
> // 将下载的文件 由后缀  .Appx  改为  .zip  并进行解压
>
> // 进入解压目录，运行  .exe文件即可

### 节省 启动盘 占用空间

#### 将应用数据移出

> 例如 qq 微信之类的聊天记录可以通过手动设置，将它们放到其他盘，而不是c盘

#### 设置 新文件安装位置，例如通过 Microsoft store 安装的软件默认装到  c盘

> 查看下一条步骤

### 设置 microsoft store 下载默认安装路径

[参考步骤](https://jingyan.baidu.com/article/2c8c281d78b0580008252abb.html)

>  设置  》  系统  》  存储  》 选择中间的  <font color=red>更改新内容的保存位置</font>

### 自定义屏保

[参考链接](https://www.maxiaobang.com/5885.html)

#### 步骤

+ 下载  `PotPlayer播放器`   播放器
+ 打开，按 `F5`   进入选项配置界面，点击屏保、安装屏保
+ 自动打开屏幕保护程序设置界面，**选择PotScreenSaver屏保程序 -> 设置**
+ 设置屏保视频即可

### 设置禁止输入法切换

> 开发的工程中，时常切换到微软拼音输入法，导致idea 的 ctrl+shift+f 失效

#### 设置步骤

+ 在任务栏中点击输入法，选中语言首选项

<img src="./images/2022-04-25-1.jpg" alt="示例" style="zoom:50%;" />

+ 在打开的窗口下滑，点击   拼写、键入和键盘设置
+ 下滑 点击  高级键盘设置
+ 下滑 点击 输入语言热键
+ 选中在输入语言之间，点击更换按键顺序，在切换语言顺序下选择未分配

<img src="./images/2022-04-25-2.jpg" alt="示例" style="zoom:50%;" />

## excel

### 首行 固定不滚动

> 视图  》  冻结窗口   》 冻结首行

<img src="./images/2021-04-21-1.jpg" alt="操作示意图" style="zoom:50%;" />

### 移动行或者列

> 选中所要移动的行，按住shift ，将鼠标放置行(列)号上直至出现黑色十字(需要靠近列名或者行数)，然后移动到目标位置即可。注意。如果移动的区域内有合并单元格的，则会导致移动失败  



### 单元格内数据较多，启用多行

> 选中目标单元格    》 开始菜单  》 自动换行

<img src="./images/2021-05-10-1.jpg" alt="操作方法" style="zoom:67%;" />

### 冻结前两行，且列数足够多一页无法显示

> 1、先将首行固定
>
> 2、选中首行，点击插入，这时原来首行上面又多了一行
>
> 3、此时前两行都是固定的



## notepading++

### 调整字体大小

![图示](./images/2021-04-22-1.jpg)



### notepadding 中取消网址自动链接

> 通过  设置 =》 首选项 =》 其他 =》 右侧 超链接设置不启用

### 远程打开文件

>  

### 设置为中文

> settings > references > 左边的   localization  选中文简体即可

### 竖向选择

> 按住 alt 键，鼠标左键进行选区

## typora 

### 页内跳转

定义：通过点击链接跳转到页面内的某个标签

![方法](./images/2021-05-08-1.jpg)

## google

### 开启 google 导入密钥

[参考操作](https://www.zmki.cn/6519.html)

> chrome地址栏输入: chrome://flags/#password-import-export  进入chrome配置
>
> 找到password import 并将其打开 设置调整为enabled 

### 将edge密钥导入google

> 1、打开edge，设置 >  个人信息  >  密码  >  在 `...`点击，导出密码
>
> 2、打开 google >  设置  >   自动填充  >   密码 >  在`...`点击，导入edge导出的密码即可

### 无用文章

1、配置注册表 [参考](http://www.win7zhijia.cn/jiaocheng/win7_39722.html)

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









# unbutu 

## windows 中子系统  20.04版本命令对比

[参考链接](https://blog.csdn.net/qq_43685040/article/details/112056242)

> service service_name start     //  开启
>
> service service_name stop  //   关闭
>
> service service_name restart  //  重启
>
> service service_name status  //  状态
>
> checonfig service_name on  //  开机启动
>
> checonfig service_name off //  关闭开机启动

## 设置root账户的密码

> sudo passwd root
>
> // 输入当前用户的密码
>
> // 输入两次root账户的密码

## 查看磁盘信息

> df -h 

## rpm、deb

> rpm包主要用于redhat及分支如redhat，centos，Fedora等
> 而deb包主要用于debian及分支如debian，ubuntu等。

## xshell  连接

[xshell 连接wsl参考](https://blog.csdn.net/sinat_33784741/article/details/83865490)

> sudo apt-get remove --purge openssh-server   ## 先删ssh
> sudo apt-get install openssh-server          ## 在安装ssh  
>
> sudo rm /etc/ssh/ssh_config                  ## 删配置文件，让ssh服务自己想办法链接
> sudo service ssh --full-restart   启动 ssh 服务

### 使用私钥登录

> // 生成公钥和私钥
>
> ssh-keygen -t rsa  // 存放默认目录在 /root/.ssh/

### 问题处理

#### 无法登陆 root账户

[无法用root账户连接参考](https://jingyan.baidu.com/article/154b463112d8f328ca8f419b.html)

> 1、关闭 `ssh`服务，服务启动中可能无法修改文件
>
> 2、修改  `/etc/ssh/sshd_config` 文件 ，把 `PermitRootLogin Prohibit-password` 添加 `#` 注释掉，新添加 `PermitRootLogin yes` 
>
> 3、启动 ssh服务 `service ssh restart`
>
> 4、重新使用 `root`账户连接，测试成功

## kafka

### 架构理解

> kafka集群理解：
>
> kafka集群中每个机器对应broker，每个broken中可以有多个topic，每个topic可以分配多个partition，同一个topic下的partition消息不重复，每个partition有leader和follower之分，可用的维护在ISP(in sync replica)，follower会定时从leader拉取数据，如果指定时间获取offset偏亮大于指定值，则会移出ISP。
>
> 如果leader移出则会重新进行选举

### 安装

> [安装包下载](https://mirrors.tuna.tsinghua.edu.cn/)
>
> // 启动zookeeper
>
> bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
>
> // 停止
>
> bin/zookeeper-server-stop.sh
>
> // 启动 kafka
>
> bin/kafka-server-start.sh -daemon config/server.properties
>
> // 停止
>
> bin/kafka-server-stop.sh

### 可视化界面  kafka-manager

### 修改启动内存大小

[参考修改方式](https://www.jianshu.com/p/944d7f129abb)

```java
//   ===> 可使用默认值，可以改小点，但是不要改成下面这么小  Xmx和Xms最好一样大
// zookeeper 启动内存修改  /bin/zookeeper-server-start.sh
export KAFKA_HEAP_OPTS="-Xmx128M -Xms32M"
//  kafka 启动内存修改  /bin/kafka-server-start.sh
export KAFKA_HEAP_OPTS="-Xmx128 -Xms32M"
// 修改初始化  buffer size  => Kafka初始化LogManager时候用到的buffer size  => 无用
log.cleaner.dedupe.buffer.size= prefered buffer size in byte
```





### 问题

#### No snapshot found, but there are log entries. Something is broken!

[参考解决办法](https://blog.csdn.net/laoyao199044/article/details/104158467)

> dataDir 数据目录冲突，本地有其他zookeeper 在使用该目录，如果是没有的目录，直接删除，如果还在使用，则手动修改目录。

#### Caused by: java.lang.ClassNotFoundException: org.apache.kafka.clients.consumer.ConsumerGroupMetadata

具体报错如下：

```java
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.boot.autoconfigure.kafka.KafkaAnnotationDrivenConfiguration]: Constructor threw exception; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name ‘kafkaTemplate’ defined in class path resource [org/springframework/boot/autoconfigure/kafka/KafkaAutoConfiguration.class]: Post-processing of merged bean definition failed; nested exception is java.lang.IllegalStateException: Failed to introspect Class [org.springframework.kafka.core.KafkaTemplate] from ClassLoader [org.springframework.boot.loader.LaunchedURLClassLoader@439f5b3d]
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name ‘kafkaTemplate’ defined in class path resource [org/springframework/boot/autoconfigure/kafka/KafkaAutoConfiguration.class]: Post-processing of merged bean definition failed; nested exception is java.lang.IllegalStateException: Failed to introspect Class [org.springframework.kafka.core.KafkaTemplate] from ClassLoader [org.springframework.boot.loader.LaunchedURLClassLoader@439f5b3d]
Caused by: java.lang.IllegalStateException: Failed to introspect Class [org.springframework.kafka.core.KafkaTemplate] from ClassLoader [org.springframework.boot.loader.LaunchedURLClassLoader@439f5b3d]
Caused by: java.lang.NoClassDefFoundError: org/apache/kafka/clients/consumer/ConsumerGroupMetadata
```

解决办法： springboot 与 kafka 版本问题  

> springboot2.2.x 对应kafka2.3.x和2.4.x 版本
> springboot2.3.x 对应kafka2.5.x 版本
>
> // 自己测试使用的版本
>
> springboot  2.0.6.RELEASE  kafka-clients  2.3.1

#### kafka 无法关闭问题

> 先关闭 kafka，后关闭 zookeeper，如果先关闭 zookeeper 就会出现该问题

#### 启动报错 java.io.IOException: Invalid argument at java.io.RandomAccess

> 查看日志  logs 目录下，  执行   rm -rf /tmp/kafka-logs 

## redis

### centeros 安装

```
$ wget https://download.redis.io/releases/redis-6.2.6.tar.gz
$ tar xzf redis-6.2.6.tar.gz
$ cd redis-6.2.6
$ make
//  编译好后，命令脚本在 src目录下，配置文件 redis.conf在src同级目录
```

### 常用设置

```bash
# 设置运行内存
maxmemory 128mb
# 后台启动
daemonize yes
```

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
>
>auth yichen  // 登陆
>
>flushall   //清空所有数据
>
>// 连接远程 redis
>
>redis-cli -h host -p port -a password



### 操作命令

>keys *   // 查看所有的key
>
>select 6 使用6号数据库  <font color=red> 在jedis中如果设置了    redis_database=6 ，如果数字大于六，到时候查询数据的时候可能存在查不到的情况，原因是保存在不同的数据库中，可以用该命令切换数据库</font>
>

### 查询命令

```java
keys *hello*    //对key 进行模糊查询
type  hello    // 查看 hello 保存的类型
info server   // 查看 server 的相关参数，需要先 redis-cli 连接上
```

### 性能监控

```java
redis-cli -h 192.168.80.24 -p 6379 --latency-dist   // 延时监控
latency doctor  // 潜伏登陆，需要先登陆redis
```

### 插件安装

#### bloom

[bloom官网](https://github.com/RedisBloom/RedisBloom)

[参考教程](https://www.codenong.com/cs110873427/)

##### 问题

###### [Makefile:403: adlist.o] Error 127

```
make cc Command not found，make: * [adlist.o] Error 127
```

没有安装c语言环境

> apt install cpp
>
> apt install binutils 
>
> apt install gcc
>
> apt install pkg-config
>
> apt install make

#### cell

[github官网](https://github.com/brandur/redis-cell)

>wget https://github.com/brandur/redis-cell/releases/download/v0.3.0/redis-cell-v0.3.0-x86_64-unknown-linux-gnu.tar.gz
>
>// 安装环境   无用跳过，直接拿现成文件
>
>apt install cargo
>
>// 修改redis.conf
>
>vi /etc/redis/redis.conf
>
>loadmodule /software/redis-cell-use/libredis_cell.so

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

### windows 安装 mysql

[多版本mysql](#windows下安装多个数据库)



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

##### 用密码登陆mysql失败

==被人攻击了，数据库改了密码，root被删了==

[参考解决办法](https://blog.csdn.net/weixin_48838458/article/details/111867356)

>找到mysql的配置文件  `my.ini` 或者 `my.cnf`  在`[mysqld]`后添加`skip-grant-tables`(可以不用密码登陆)
>
>重启mysql服务器 service mysql stop    service mysql start
>
>mysql -uroot -p  接着两个回车就能登陆了
>
>update user set password=PASSWORD(“Shanliang28”) where user=‘root’;
>
>将mysql的配置文件改回来，重启服务再次登陆即可

```java
先看看 `mysql`.`user`表里有没有root  

5.7 添加记录sql  
    
INSERT INTO `mysql`.`user`(`Host`, `User`, `Select_priv`, `Insert_priv`, `Update_priv`, `Delete_priv`, `Create_priv`, `Drop_priv`, `Reload_priv`, `Shutdown_priv`, `Process_priv`, `File_priv`, `Grant_priv`, `References_priv`, `Index_priv`, `Alter_priv`, `Show_db_priv`, `Super_priv`, `Create_tmp_table_priv`, `Lock_tables_priv`, `Execute_priv`, `Repl_slave_priv`, `Repl_client_priv`, `Create_view_priv`, `Show_view_priv`, `Create_routine_priv`, `Alter_routine_priv`, `Create_user_priv`, `Event_priv`, `Trigger_priv`, `Create_tablespace_priv`, `ssl_type`, `ssl_cipher`, `x509_issuer`, `x509_subject`, `max_questions`, `max_updates`, `max_connections`, `max_user_connections`, `plugin`, `authentication_string`, `password_expired`, `password_last_changed`, `password_lifetime`, `account_locked`) VALUES ('localhost', 'root', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', '', '', '', '', 0, 0, 0, 0, 'mysql_native_password', '*23AE809DDACAF96AF0FD78ED04B6A265E05AA257', 'N', '2021-11-15 22:54:28', NULL, 'N');

// 非本地能连接数据库
update user set host = '%' where user = 'root';   // %表示所有，默认为本机
```



##### centeros  rpm 安装修改密码提示不符合当前策略要求

> // 修改密码错误提示
>
> Your password does not satisfy the current policy requirement
>
> //  执行其他语句报错
>
> You must reset your password using ALTER USER statement before executing this statement.

<font color=red>解决办法</font>

> // 先修改成它要求的策略  密码长度为8  密码中包含大小写字母，数字，特殊字符
>
> ALTER USER 'root'@'localhost' IDENTIFIED BY 'qwe!@#QWE123';
>
> // 查看密码规则
>
> show variables like 'validate_password%';
>
> // 修改策略
>
> set global validate_password_policy=0;
> set global validate_password_length=4;  // 最小长度即为4
>
> // 再次修改密码
>
> ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';

#### apt 安装后无法通过  service mysql start 启动

<font color=red> 子系统和主系统公用端口号，需要把主系统的mysql关闭。</font>

####  赋权问题

##### mysql 5.x

> GRANT ALL ON zabbix.* TO 'zabbix'@'localhost' IDENTIFIED BY 'change-with-strong-password';

##### 8.x

> create user 'zabbix'@'localhost' identified by 'change-with-strong-password';
>
> grant all on zabbix.* to 'zabbix'@'localhost';

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

[参考步骤](https://blog.csdn.net/mario08/article/details/107243730)

### 主要内容

> 修改 runserver.sh 和 runbroker.sh 中 jvm 参数，改为 256m

### 问题

#### 启动 server 报错 端口拒绝

==分析==

> 出错原因是电脑本机开启了 hyper-v 功能，禁用了某些端口。具体查看
>
> windows cmd 输入下面命令
>
> netsh interface ipv4 show excludedportrange protocol=tcp
>
> // 查询后会有如下不可用端口范围，其中有 9876，即造成端口不可用，而不是常见的非 root账户无法使用 1000以下端口
>
> ----------    --------
>     协议 tcp 端口排除范围
>     开始端口    结束端口
>       1064        1163
>       1364        1463
>       1464        1563
>       1564        1663
>       2501        2600
>       2801        2900
>       2901        3000
>       3001        3100
>       5357        5357
>       7639        7738
>       7739        7838
>       9834        9933
>      10034       10133
>      10241       10340
>      10341       10440
>      10441       10540
>      10541       10640
>      12066       12165
>      12166       12265
>      50000       50059     
>      管理的端口排除。
>

##### 解决

[参考解决办法](https://www.cnblogs.com/quchunhui/p/9234860.html)

> 修改rocketmq 使用的端口，改为 9950 端口
>
> 项目目录 ：  /software/rocketmq
>
> 在 /conf 目录下创建   namesrv.properties 文件
>
> 内容为    listenPort=9950 
>
> server 启动脚本  start-server.sh 
>
> nohup sh /software/rocketmq/bin/mqnamesrv -c /software/rocketmq/conf/namesrv.properties & > ./server.log 2>&1
>
> broker 启动脚本 start-broker.sh
>
> nohup sh /software/rocketmq/bin/mqbroker -n localhost:9950 & > ./broker.log 2>&1

## rabbitMq

<font color=red size=6px> unbutu下安装 java程序无法连接，window下安装则正常。</font>

### window 下安装

#### 快捷启动

```java
// 进入安装目录  cmd
d:
cd \rabbitmq\setup\rabbitmq_server-3.8.16\sbin
rabbitmq-plugins enable rabbitmq_management  // 启动 web界面
rabbitmq-plugins disable rabbitmq_management    // 关闭
// 双击  rabbitmq-service.bat 启动
```



[参考步骤](https://blog.51cto.com/ccj168/2362486)

> // 下载 erlang
>
> http://www.erlang.org/downloads
>
> // 配置环境变量   path 中添加安装目录，到 /bin
>
> // 下载rabbitmq
>
> http://www.rabbitmq.com/download.html
>
> // 到 安装目录下的  sbin，启动 插件
>
> rabbitmq-plugins enable rabbitmq-management
>
> // 启动服务，双击 sbin 下的rabbitmq-server.bat。



### 常用命令

>service rabbitmq-server start
>service rabbitmq-server stop
>service rabbitmq-server restart
>service rabbitmqctl status

```java
// 由于rabbitMq需要erlang语言的支持，在安装rabbitMq之前需要安装erlang
apt-get install erlang-nox
//  安装
apt-get install rabbitmq-server
// 添加admin用户，密码设置为admin  下面的需要先启动 rabbitmq 服务
rabbitmqctl add_user  admin  admin  
// 赋予权限
rabbitmqctl set_user_tags admin administrator
//赋予virtual host中所有资源的配置、写、读权限以便管理其中的资源
rabbitmqctl  set_permissions -p / admin '.*' '.*' '.*'
//  RabbitMQ GUID  web页面 (官方提供的一个web管理工具（rabbitmq_management）,apt 安装的时候默认安装)
rabbitmq-plugins enable rabbitmq_management
//  关闭插件
rabbitmq-plugins disable rabbitmq_management
//  访问 web页面
http://localhost:15672/
```

### 问题

安装 rabbitmq-server 报错

```java
// 报错内容如下
E:Unbale to fetch some archives,maybe run apt-get update or try with --fix-missing
// 解决办法
apt unpate
apt install rabbitmq-server
```



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

### centeros

> 下载jar包，解压
>
> //  vi /etc/profile   在末尾添加如下信息
>
> export JAVA_HOME=/usr/local/java/jdk1.8.0_171
> export JRE_HOME=${JAVA_HOME}/jre
> export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
> export PATH=${JAVA_HOME}/bin:$PATH
>
> //生效
>
> source /etc/profile
>
> // 查看是否成功
>
> java -version

### 安装  

<font color=red>默认安装路径  `/usr/lib/jvm/java-8-openjdk-amd64` </font>

> apt-get install openjdk-8-jdk  
>
> java -version

### 卸载

[参考链接](https://www.cnblogs.com/dengtang/p/11644751.html)

> apt-get remove openjdk*



## mongodb

### 常用命令

[参考链接](https://www.cnblogs.com/--smile/p/11055204.html)

>net start mongodb   // 开启mongodb 服务
>
>net stop mongodb  // 关闭mongodb服务
>
>use admin   // 如果没有对应的数据库，则默认自动创建
>
>快捷连接 =》 配置环境变量的情况下  cmd =》 mongo
>// 查看所有数据库
>show dbs;
>// 切换数据库(这里切换到test 数据库)
>use test   
>//获取当前数据库名称
>db.getName();
>// 查看创建的集合
>db.getCollectionNames();
>// 查看集合(order)中的内容
>db.order.find();

### window 安装

[安装步骤](https://blog.csdn.net/dandanfengyun/article/details/95497728)

### unbutu 安装   20.04版

<font color=red>**window下的子系统 unbutu 无法安装mongodb，因为不支持 fsync**</font>

[官网安装参考链接](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-ubuntu/)

> wget -qO - https://www.mongodb.org/static/pgp/server-4.4.asc | sudo apt-key add -    //导入公共key 通过包管理系统
>
> echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/4.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-4.4.list    // 创建文件目录   /etc/apt/sources.list.d/mongodb-org-4.4.list
>
> sudo apt-get update   // 重新加载本地包数据库
>
> sudo apt-get install -y mongodb-org   // 安装mongodb 包
>
> ps --no-headers -o comm 1   // 查看 init system 类型
>
> sudo service mongodb start   // 启动服务
>
> 

#### 不能使用  systemctl  不支持

> systemctl status mongodb    // 查看 状态
>
> systemctl stop mongodb
>
> systemctl start mongodb
>
> systemctl restart mongodb
>
> systemctl disable mongodb   // 关闭开机启动
>
> systemctl enable mongodb   //  打开开机启动

#### 问题记录

##### mongod 错误 data directory  /data/db not found

> mkdir -p /data/db      //  -p  只递归创建目录

##### 启动失败

> // 查看错误日志
>
> cat   /var/log/mongodb/mongodb.log

##### E: Sub-process /usr/bin/dpkg returned an error code (1)   

[参考链接](https://blog.csdn.net/stickmangod/article/details/85316142)

##### Failed to unlink socket file /tmp/mongodb-27017.sock errno:1 Operation not permitted

将 该文件删除即可

## tomcat 安装

### 清空catalina.out

[参考解决办法](https://www.cnblogs.com/ainihaozhen/p/9466524.html)

> // 执行如下命令   =>  重定向方法清空文件
>
> `> catalina.out`
>
> // 查看执行后的文件大小
>
> df -h catalina.out

### 修改运行内存大小

[参考文章](http://www.xitongzhijia.net/xtjc/20171211/114356.html)

> 修改 catalina.bat ，添加如下内容
>
> JAVA_OPTS='-Xms512m -Xmx1024m'
> JAVA_OPTS=" -server -XX:PermSize=64M -XX:MaxPermSize=256m"

![填写位置](./images/2021-11-15-2.jpg)

### 快速启动tomcat

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



### 基础知识

tomcat 下 webapp 目录为运行的web 目录，其中可以放多个项目，但是都使用的是同一个端口，区分方式是在请求端口后面加文件夹名称做匹配。如果不想这样，直接默认为 `/` 做为根目录，则将原有 ROOT 文件夹删除，然后将你的文件夹改名为 ROOT

### window下部署

#### 替换日志 改为log4j打印

[资源链接](https://www.jianguoyun.com/p/DXJ66S8Quu_8CBiPsIAE)

[参考步骤](https://blog.csdn.net/jeryjeryjery/article/details/69342542)

> 1、删除 conf 下的 logging.properties 文件
>
> 2、下载  tomcat-juli.jar   tomcat-juli-adapter.jar  log4j-1.2.7.jar    以及自己写一个log4j.properties文件
>
> 3、将 tomcat-juli.jar  丢到 bin文件夹下   其他三个丢到 lib文件夹下
>
> 4、conf 文件夹下  context.xml 中 Context 标签加 swallowOutput="true"

#### 问题记录

##### tomcat 7 和jdk8，启动程序报错  org.apache.tomcat.util.bcel.classfile.ClassFormatException: Invalid byte tag in constant pool

[解决办法](https://blog.51cto.com/1008610086/1925269)

```java
{tomcat路径}/conf/web.xml 在下面代码中添加红色部分:  metadata-complete="true"
<web-app version="3.0" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" metadata-complete="true">
```

更换 lib 文件夹下的  ecj-4.4.2.jar，改用tomcat8的jar包，实际测试无用。

### 分割catalina 日志

[参考步骤](https://www.cnblogs.com/dxy1451713982/p/8079145.html)

### 安装步骤

apt install tomcat9 tomcat9-docs tomcat9-examples tomcat9-admin

>  //  安装目录
>
>  /etc/tomcat9 /usr/libexec/tomcat9 /usr/share/tomcat9
>
>  //  复制启动文件
>
>  cp /usr/share/tomcat9/etc/server.xml  /usr/share/tomcat9/conf/server.xml 
>
>  //  复制 web.xml
>
>  cp /etc/tomcat9/web.xml    /usr/share/tomcat9/conf/

### 问题记录

####   Package 'tomcat8' has no installation candidate

<font color=red>unbutu子系统版本 Ubuntu 20.04.2 LTS</font>

>apt update
>
>apt upgrade
>
>apt install tomcat8
>
>// 如果不能解决问题
>
>[参考步骤](https://blog.csdn.net/qq_42824983/article/details/110236257)
>
>// 切换软件源
>
>sudo  cp   /etc/apt/sources.list   /etc/apt/sources.list.bak
>
>sudo vim /etc/apt/sources.list
>
>// 将其中内容替换，写成如下内容
>
>deb http://mirrors.aliyun.com/ubuntu/ groovy main restricted universe multiverse
>deb-src http://mirrors.aliyun.com/ubuntu/ groovy main restricted universe multiverse
>
>deb http://mirrors.aliyun.com/ubuntu/ groovy-security main restricted universe multiverse
>deb-src http://mirrors.aliyun.com/ubuntu/ groovy-security main restricted universe multiverse
>
>deb http://mirrors.aliyun.com/ubuntu/ groovy-updates main restricted universe multiverse
>deb-src http://mirrors.aliyun.com/ubuntu/ groovy-updates main restricted universe multiverse
>
>deb http://mirrors.aliyun.com/ubuntu/ groovy-backports main restricted universe multiverse
>deb-src http://mirrors.aliyun.com/ubuntu/ groovy-backports main restricted universe multiverse
>
>\# 预发布软件源，不建议启用
>
>#deb http://mirrors.aliyun.com/ubuntu/ groovy-proposed main restricted universe multiverse
>#deb-src http://mirrors.aliyun.com/ubuntu/ groovy-proposed main restricted universe multiverse

#### 使用 shutdown.bat 报错 No shutdown port configured. Shut down server through OS signal. Server not shut down.

> 修改启动文件  /conf/server.xml 中   shutdown  的端口，默认值是-1(当前版本  tomcat  9)，改为  8005。再次重启后使用  shutdown 可以成功，当前关闭需要调用  kill 命令来关闭

#### zookeeper 和  tomcat 端口冲突，都使用 8080端口

[参考解决方案](https://blog.csdn.net/liujian8654562/article/details/100860002)

#### tomcat 运行报错  this web application instance has been stopped already.

```
// 具体报错内容
20-Aug-2021 10:32:03.183 INFO [Abandoned connection cleanup thread] org.apache.catalina.loader.WebappClassLoaderBase.checkStateForResourceLoading Illegal access: this web application instance has been stopped already. Could not load []. The following stack trace is thrown for debugging purposes as well as to attempt to terminate the thread which caused the illegal access.
 java.lang.IllegalStateException: Illegal access: this web application instance has been stopped already. Could not load []. The following stack trace is thrown for debugging purposes as well as to attempt to terminate the thread which caused the illegal access.
        at org.apache.catalina.loader.WebappClassLoaderBase.checkStateForResourceLoading(WebappClassLoaderBase.java:1305)
        at org.apache.catalina.loader.WebappClassLoaderBase.getResource(WebappClassLoaderBase.java:986)
        at com.mysql.jdbc.AbandonedConnectionCleanupThread.checkContextClassLoaders(AbandonedConnectionCleanupThread.java:90)
        at com.mysql.jdbc.AbandonedConnectionCleanupThread.run(AbandonedConnectionCleanupThread.java:63)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
        at java.lang.Thread.run(Thread.java:745)
```

##### 解决办法

具体产生错误的原因，可以看 tomcat 日志目录下的 `localhost.2021-08-20.log`,将里面的错误解决该问题也会消失

## 防火墙

> // 安装模块
>
> apt install firewalld 
>
> // 查看防火墙状态
>
> firewall-cmd --state
>
> //  启动  dbus
>
> /etc/init.d/dbus start
>
> //  启动守护线程
>
> dbus-daemon --system

## ElasticSearch

[参考安装步骤](https://blog.csdn.net/weixin_44596128/article/details/103970665)

> #下载压缩包
> wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.4.0.tar.gz
> #解压
> tar -zxvf elasticsearch-6.4.0.tar.gz -C /usr/local/
>
> #给非root用户赋予权限
>
> chown -R yichen /software/elasticsearch-6.4.0
>
> #更改内存大小
>
> sysctl -w vm.max_map_count=262144
>
> #修改jvm内存大小
>
> vi /usr/local/elasticsearch-6.4.0/config/jvm.option 
>
> -Xms512m
>
> -Xmx512m
>
> #启动命令   后加 -d  后台启动
>
> /software/elasticsearch-6.4.0/bin/elasticsearch
>
> #测试启动成功  
>
> curl 'http://localhost:9200/?pretty'

### 安装步骤 other

[elasticsearch 和 kibana集成](https://blog.csdn.net/zhoudatianchai/article/details/113045119)

> 1、下载  https://www.elastic.co/cn/downloads/elasticsearch    选择 linux-x86_64
>
> 2、tar -zxvf 解压
>
> 3、   ./bin/elasticsearch 启动，在最后会显示 密码和token
>
> ```linux
> ✅ Elasticsearch security features have been automatically configured!
> ✅ Authentication is enabled and cluster connections are encrypted.
> 
> ℹ️  Password for the elastic user (reset with `bin/elasticsearch-reset-password -u elastic`):
>   c7AV_OQwlgQz=JjmzBQX
> 
> ℹ️  HTTP CA certificate SHA-256 fingerprint:
>   cda0ca37b1221a3ab4ed2b4b4370e7c30276b0bc8425a812343bd9716b2f2f60
> 
> ℹ️  Configure Kibana to use this cluster:
> • Run Kibana and click the configuration link in the terminal when Kibana starts.
> • Copy the following enrollment token and paste it into Kibana in your browser (valid for the next 30 minutes):
>   eyJ2ZXIiOiI4LjEuMCIsImFkciI6WyIxMC4zLjEuMjc6OTIwMCIsIjE5Mi4xNjguNTYuMTo5MjAwIiwiMTAuMjAzLjAuMjI2OjkyMDAiLCIxOTIuMTY4LjE3LjE6OTIwMCJdLCJmZ3IiOiJjZGEwY2EzN2IxMjIxYTNhYjRlZDJiNGI0MzcwZTdjMzAyNzZiMGJjODQyNWE4MTIzNDNiZDk3MTZiMmYyZjYwIiwia2V5IjoiX1E5U2tYOEJPVEE1SUdRT2p2cUE6cUpJMlF0dGZSLW0tVHd5VW5CSUFoQSJ9
> 
> ℹ️  Configure other nodes to join this cluster:
> • On this node:
>   ⁃ Create an enrollment token with `bin/elasticsearch-create-enrollment-token -s node`.
>   ⁃ Uncomment the transport.host setting at the end of config/elasticsearch.yml.
>   ⁃ Restart Elasticsearch.
> • On other nodes:
>   ⁃ Start Elasticsearch with `bin/elasticsearch --enrollment-token <token>`, using the enrollment token that you generated.
> ```
>
> 4、验证是否启动成功   curl --cacert config/certs/http_ca.crt -u elastic https://localhost:9200
>
> 

### 使用

#### 执行命令



#### 构造数据

```java
PUT /megacorp/employee/1
{
    "first_name" : "John",
    "last_name" :  "Smith",
    "age" :        25,
    "about" :      "I love to go rock climbing",
    "interests": [ "sports", "music" ]
}

PUT /megacorp/employee/2
{
    "first_name" :  "Jane",
    "last_name" :   "Smith",
    "age" :         32,
    "about" :       "I like to collect rock albums",
    "interests":  [ "music" ]
}

PUT /megacorp/employee/3
{
    "first_name" :  "Douglas",
    "last_name" :   "Fir",
    "age" :         35,
    "about":        "I like to build cabinets",
    "interests":  [ "forestry" ]
}
```

#### 集群健康

```java
GET /_cluster/health
```

#### 单机启动多个实例







#### 聚合报错

==未解决==

```java
Fielddata is disabled on text fields by default. Set fielddata=true ...
// 解决办法  先删除 index，再重新导入数据
PUT megacorp
{
    "mappings": {
        "properties": {
            "megacorp": {
                "type": "text",
                "fielddata": true
            }
        }
    }
}
```





### 问题

#### kibana environment token 失效

```bash
./bin/elasticsearch-create-enrollment-token -s kibana
```



#### Caused by: java.lang.RuntimeException: can not run elasticsearch as root

> 不能使用root账户，用其他账户，不过要给该账户对应的目录所有权

#### java.lang.UnsupportedOperationException: seccomp unavailable: CONFIG_SECCOMP not compiled into kernel, CONFIG_SECCOMP and CONFIG_SECCOMP_FILTER are needed

>  修改 安装目录下的   /config/elasticsearch.yml ，添加如下内容
>
> bootstrap.memory_lock: false
>
> bootstrap.system_call_filter: false

#### java.nio.file.NoSuchFileException: /proc/sys/vm/max_map_count

> 没有设置  /proc/sys/vm/max_map_count
>
> // 尝试执行如下命令 
>
> sysctl -w vm.max_map_count=262144
>
> // 如果失败直接 vi创建对应文件，然后写入数值即可

####  max file descriptors [4096] for elasticsearch process is too low, increase to at least [65536]

```java
// 查看进程打开的文件数数量限制
ulimit -Sn
ulimit -Hn
```

#### 启动测试连接性报错   curl: (52) Empty reply from server

```bash
curl 'http://localhost:9200/?pretty'
curl: (52) Empty reply from server   # 错误信息

# 解决办法   https://stackoverflow.com/questions/35921195/curl-52-empty-reply-from-server-timeout-when-querying-elastiscsearch
vi ./config/elasticsearch.yml
and replace this setting with false
# Enable security features
xpack.security.enabled: false
xpack.security.enrollment.enabled: false
```



## kibana

[参考解决办法](https://www.cnblogs.com/cnsdhzzl/p/9564097.html)

```java
wget https://artifacts.elastic.co/downloads/kibana/kibana-6.4.0-linux-x86_64.tar.gz

tar -zxvf kibana-6.4.0-linux-x86_64.tar.gz

// 启动 
//  默认配置即可运行，需要 elasticsearch 安装在本地  如今不在需要 sense ，改为 dev tools 
bin/kibana
//  启动页面
http://localhost:5601
```

### 安装步骤 other

> 1、下载  https://www.elastic.co/cn/downloads/kibana   linux-x86_64
> 2、  ./bin/kibana 启动  启动完成后，会暴露目录一个网址
>
> http://localhost:5601/?code=189206
>
> 3、页面打开输入 之前elasticsearch 启动中的 environment token
>
> 4、账户登陆  用户名为 elastic  密码为 elasticsearch 启动中的密码
>
> 5、选择  add integration 搜索框输入 sample 导入样例数据，开始测试   [参考](https://www.elastic.co/guide/en/kibana/current/get-started.html#gs-get-data-into-kibana)

### 基本使用

[KQL官方文档](https://www.elastic.co/guide/en/kibana/current/kuery-query.html)

```java
1、空格  =>  准确搜索词
2、引号  => 短语查询 
3、优先级  and > or  取消默认优先级用括号
4、取反  not
5、多字段匹配值   => 示例  tags:(success and info and security)
6、 >  < >=  <=   => 只在数字和时间有效
7、通用匹配值  response:*
8、嵌套查询
	items:{ name:banana and stock > 10 }   // 匹配单个文档
	items:{ name:banana } and items:{ stock:9 }   // 匹配不同文档
	// 匹配单个不同文档
	items:{ name:banana and stock > 10 } and items:{ category:vegetable }
	// 嵌套字段内部其他字段
	level1.level2:{ prop1:foo and prop2:bar }
```

### date math

[官方 DATE MATH文档](https://www.elastic.co/guide/en/elasticsearch/reference/8.1/common-options.html#date-math)

#### 语法

- `+1h`: Add one hour
- `-1d`: Subtract one day
- `/d`: Round down to the nearest day

#### 单位

+ y   => 年
+ M  =>  月
+ w  =>  周
+ d  =>  天
+ h  => 12小时
+ H  => 24小时
+ m  => 分钟
+ s  => 秒

```java
以 || 结尾手动指定日期 示例 2001.02.01\|\|   => 2001.02.01
```

#### case

|       表达式        |                             含义                             |
| :-----------------: | :----------------------------------------------------------: |
|       now+1h        | now  in milliseconds plus one hour. Resolves to: 2001-01-01 13:00:00 |
|       now-1h        | now in milliseconds minus one hour. Resolves to: 2001-01-01 11:00:00 |
|      now-1h/d       | now in milliseconds minus one hour, rounded down to UTC 00:00. Resolves to: 2001-01-01 00:00:00 |
| 2001.02.01\|\|+1M/d | 2001-02-01 in milliseconds plus one month. Resolves to: 2001-03-01 00:00:00 |



### 配置安全认证

```bash
./bin/kibana-encryption-keys generate   # 自动生成
# 示例
xpack.encryptedSavedObjects.encryptionKey: 25393453696a6186dea52b91c76f00b6
xpack.reporting.encryptionKey: fc6cbc1284644d0ae9fdccd64e3c0a4c
xpack.security.encryptionKey: 72090d5dcfa7d057d59b0e5210f60b04
```



[官方使用教程](https://www.elastic.co/guide/en/kibana/current/get-started.html#gs-get-data-into-kibana)

## hbase

[安装步骤参考](https://blog.csdn.net/hhy1107786871/article/details/88551234)

> wget https://mirrors.tuna.tsinghua.edu.cn/apache/hbase/2.2.7/hbase-2.2.7-bin.tar.gz
>
> // 修改pom.xml 中的zookeeper 和 hadoop 的版本
>
> //  打包  完成后会在 /target/ 目录下生成jar 包
>
> mvn clean package -DskipTests assembly:single
>
> // 修改  /conf/hbase-env.sh
>
> export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
>
> #true为单机，false 为集群                                                                                              
>
> export HBASE_MANAGES_ZK=true                                                                                           
>
> export HADOOP_HOME=/software/hadoop-3.2.2                                                                               
>
> export HBASE_HOME=/software/hbase-2.2.7  

### 问题记录

#### Error: Could not find or load main class org.apache.hadoop.hbase.util.GetJavaProperty

<font color=red> 可能问题，下载的是src 文件，没有进行编译</font>

[参考文章](https://blog.csdn.net/pycrossover/article/details/102627807)

## hadoop

[参考文章1](https://www.cnblogs.com/tanrong/p/10645467.html)

[参考文章2](https://blog.csdn.net/weixin_42001089/article/details/81865101)

> // hadoop 默认就是单机版
>
> wget https://mirrors.tuna.tsinghua.edu.cn/apache/hadoop/common/hadoop-3.2.2/hadoop-3.2.2.tar.gz
>
> // 配置 JAVA_HOME的环境变量
>
> sudo vi /etc/profile
>
> export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
> export PATH=${JAVA_HOME}/bin:$PATH
>
> export HADOOP_HOME=/software/hadoop-2.7.7
> export PATH=${HADOOP_HOME}/bin:$PATH
>
> // 查看是否安装成功
>
> 安装目录/bin/hadoop version
>
> 

## zookeeper 

### 本机伪集群启动

> cd /software/apache-zookeeper-3.7.0-bin/bin        ./zkServer.sh start 
>
> cd /software/zookeeper-1/bin                ./zkServer.sh start 
>
> cd /software/zookeeper-2/bin          ./zkServer.sh start 

[参考安装步骤](https://www.cnblogs.com/tyoutetu/p/11115357.html)

> wget https://mirrors.tuna.tsinghua.edu.cn/apache/zookeeper/zookeeper-3.7.0/apache-zookeeper-3.7.0-bin.tar.gz
>
> // 解压文件目录
>
> tar -zxvf apache-zookeeper-3.7.0
>
> // 修改配置文件
>
> cd apache-zookeeper-3.7.0
>
> cd conf
>
> cp zoo-sample.cfg zoo.cfg
>
> vi zoo.cfg
>
> // 更改内容如下
>
> dataDir=../data
>
>    // 需要 修改启动端口，如2181,2182,2183等
>
> server.1=localhost:2287:3387
> server.2=localhost:2288:3388
> server.3=localhost:2289:3389
>
> //  表示主机编号, 创建数据目录
>
> cd ..
>
> mkdir data
>
> vi data/myid
>
> // 输入 对应的主机编号，即1、2、3
>
> // 启动
>
> bin/zkServer.sh start
>
> // 查看启动状态
>
> bin/zkServer.sh start

## nginx

[参考安装步骤](https://blog.csdn.net/qq_23832313/article/details/83578836)

> apt install nginx
>
> // 查看nginx 版本号
>
> nginx -v
>
> // 启动
>
> service nginx start

### 常用文件的位置

- /usr/sbin/nginx：主程序
- /etc/nginx：存放配置文件
- /usr/share/nginx：存放静态文件
- /var/log/nginx：存放日志

### 前置安装

+ gcc
+ pcre-devel
+ zlib-devel
+ openssl-devel

#### center os 安装命令如下

```
yum -y install gcc pcre-devel zlib-devel openssl openssl-devel
```

##### 查看是否成功

+ rpm包安装的，可以用 rpm -qa 看到，如果要查找某软件包是否安装，用 **rpm -qa | grep "软件或者包的名字"**
+ 以deb包安装的，可以用 dpkg -l 看到。如果是查找指定软件包，用 **dpkg -l | grep "软件或者包的名字"**
+ yum方法安装的，可以用 yum list installed 查找，如果是查找指定包，用 **yum list installed | grep "软件名或者包名"**

#### unbutu 安装

[参考解决办法](https://blog.csdn.net/z920954494/article/details/52132125)

```bash
# 查看软件是否安装
dpkg -l | grep zlib   # 查看 zlib 是否安装
# 解决依赖包openssl安装，命令
sudo apt-get install openssl libssl-dev
# 解决依赖包pcre安装，命令：
sudo apt-get install libpcre3 libpcre3-dev
# 解决依赖包zlib安装，命令
sudo apt-get install zlib1g-dev
```



### 安装

[参考教程](https://www.cnblogs.com/xxoome/p/5866475.html)

+ 在 `https://nginx.org/download/`  下载  nginx
+ 将该压缩包放到  /usr/local/software   目录下
+ 解压 下载的压缩包   tar -zxvf    nginx-1.9.9.tar.gz（文件名）
+ 进入该目录 cd nginx-1.9.9.tar.gz
+ 配置安装路径   

```bash
#  --prefix 为编译后保存的目录，可执行
./configure --prefix=/usr/local/software/nginx    
```

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

### location 介绍

[参考介绍](https://blog.csdn.net/luoyang_java/article/details/83507193)

```java
=    开头表示精确匹配
^~   开头表示 uri 以某个常规字符串开头，理解为匹配 url 路径即可。nginx不对url做编码，因此请求为 
    /static/20%/aa 可以被规则 ^~/static/ /aa 匹配到 (注意是空格)。
~ 	 开头表示区分大小写的正则匹配
~*   开头表示不区分大小写的正则匹配
!~ 和  !~*  分别表示 区分大小写不匹配  以及 不区分大小写不匹配  的正则
/   通用匹配，任何请求都会匹配到
```

### 匹配规则

###  负载均衡

> 1、轮询(RR)：默认的策略，每个请求按时间顺序逐一分配到不同的后端服务器，如果后端服务器宕机，能自动剔除。
>
> 2、权重(weight)：可以给不同的后端服务器设置一个权重值，用于调整不同的服务器上请求的分配率。权重数据越大，被分配到请求的几率越大。该权重值，主要是针对实际工作环境中不同的后端服务器硬件配置进行调整的。
>
> 3、ip_hash：每个请求按照发起客户端的ip的hash结果进行匹配，这样的算法下一个固定ip地址的客户端总会访问到同一个后端服务器，这也在一定程度上解决了集群部署环境下session共享的问题。
>
> 4、fair：智能调整调度算法，动态的根据后端服务器的请求处理和响应时间进行均衡分配。响应时间短处理效率高的服务器分配到的请求的概率高，响应时间长处理效率低的服务器分配到的请求少。nginx默认不支持fair算法，如果要使用这种调度算法，请安装 upstream_fair 模块。
>
> 5、url_hash：按照访问的URL 的 hash 结果分配请求，每个请求的URL会指向后端固定的某个服务器，可以在nginx 作为静态服务器的情况下提高缓存效率。使用这种调度算法，需要安装  hash 软件包。

### 日志格式

```bash
# nginx.conf 下 http{}  中
log_format main '$remote_addr - $remote_user [$time_local] "$request" '
                  '$status $body_bytes_sent "$http_referer" '
                  '"$http_user_agent" "$http_x_forwarded_for" "$request_body"';

log_format json '{"@timestamp":"$time_iso8601",'
                '"host":"$server_addr",'
                '"clientip":"$remote_addr",'
                '"size":$body_bytes_sent,'
                '"responsetime":$request_time,'
                '"upstreamtime":"$upstream_response_time",'
                '"upstreamhost":"$upstream_addr",'
                '"http_host":"$host",'
                '"url":"$uri",'
                '"xff":"$http_x_forwarded_for",'
                '"referer":"$http_referer",'
                '"agent":"$http_user_agent",'
                '"body":"$request_body",'	
                '"status":"$status"}';
# 使用
http{
	server{
		access_log  logs/host.access.log  main;
	}
}
```

### 常用配置

```bash
# 运行请求头有 下划线 _
http{
	# 添加如下配置
	underscores_in_headers on
}
```



## CAS server

### 安装步骤

[参考步骤](https://blog.csdn.net/u011872945/article/details/81044124)

> https://github.com/apereo/cas/releases/tag/v4.0.0  // 下载 release 版本
>
> //  unzip 解压
>
>  // 将  modules/cas-server-webapp-4.0.0.jar  移动到 tomcat 安装目录下的webapp 目录
>
> cp cas-server-webapp-4.0.0.war  /usr/share/tomcat9/webapps/cas-server.war
>
>  // 启动tomcat     bin/start.sh
>
> //登陆  cas   
>
> http://localhsot:8080/cas(webapp下的文件夹名称)/login     
>
> //   用户名  casuser      密码  Mellon

### 更改认证方式  由https 改为 http

> webapps\cas\WEB-INF\spring-configuration\warnCookieGenerator.xml  
>
> bean-id=warnCookieGenerator
>
> p:cookieSecure  true => false
>
>  
>
> webapps\cas\WEB-INF\spring-configuration\ticketGrantingTicketCookieGenerator.xml 
>
> bean-id  ticketGrantingTicketCookieGenerator
>
> p:cookieSecure  true => false
>
>  
>
> webapps\cas\WEB-INF\deployerConfigContext.xml
>
> bean-id proxyAuthenticationHandler
>
> 增加  p:requireSecure="false"



### 错误

#### 访问报错 404 org.eclipse.jetty.servlet.ServletHandler$Default404Servlet-145eaa29

本机的  zookeeper 导致的   可能 zookeeper  内置了jetty

#### 访问登陆页面报错  org.springframework.web.util.NestedServletException: Request processing failed; nested exception is org.springframework.webflow.execution.FlowExecutionException

[参考文章](https://www.it1352.com/1513832.html)

> 在 tomcat 安装目录    /usr/share/tomcat9/conf/  缺少 web.xml ，可从   /etc/tomcat9下面复制

### 执行验证报错 未能识别出目标票根

[参考解决方案](https://blog.csdn.net/weixin_30810583/article/details/95836229)

>  // 修改  WEB-INF/spring-configuration/ticketExpirationPolicies.xml
>
> 找到 bean-id = serviceTicketExpirationPolicy    c:timeToKill 属性
>
> 由  c:timeToKill="${st.timeToKillInSeconds:10}"  改为   c:timeToKill="1800000"

## Zabbix 

[参考链接](https://www.linuxidc.com/Linux/2020-04/162818.htm)

### 问题记录

#### sudo apt install zabbix-server-mysql zabbix-frontend-php zabbix-agent  报错    The following packages have unmet dependencies

[参考链接](https://blog.csdn.net/GanSir_Auto/article/details/103655984)

> apt install aptitidu
>
> aptitude install  zabbix-server-mysql zabbix-frontend-php zabbix-agent

#### zabbix-server 表面启动成功，实际未成功

> service zabbix-service restart   // 执行后显示两个ok
>
> ps -ef | grep zabbix-service   // 查看显示未启动
>
> cat /etc/zabbix/zabbix_server.conf   // 查看启动文件中的日志存储位置
>
>  cat /var/log/zabbix-server/zabbix_server.log    // 查看启动日志
>
> <font color=red>**报错如下：**query failed: [1146] Table 'zabbix.users' doesn't exist [select userid from users limit 1]</font>
>
>  cd   /usr/share/doc/zabbix-server-mysql/create.sql.gz  // sql 存放的位置

#### 没有 /usr/share/doc/zabbix-server-mysql/create.sql.gz

[参考链接](https://www.lanqiao.cn/questions/174591/)

> //apt 更新指定为  阿里云
>
> vim /etc/apt/sources.list.d/zabbix.list
>
> //  更改为如下
>
> deb http://mirrors.aliyun.com/zabbix/zabbix/4.0/ubuntu xenial main 
>
> deb-src http://mirrors.aliyun.com/zabbix/zabbix/4.0/ubuntu xenial main
>
> // 获取
>
> apt update

#### Could not resolve 'mirrors.cloud.aliyuncs.com'

[参考链接](https://my.oschina.net/u/4339825/blog/3318996)

> vi  /etc/resolv.conf
>
> // 增加下面两个配置
>
> nameserver  119.29.29.29
>
> nameserver  8.8.8.8
>
> // 重启  网络
>
> service network-manager restart
>
>  // 如果无法  识别 network-manager
>
> apt intall network-manager

#### Failed to enable APR_TCP_DEFER_ACCEPT

[参考链接](https://blog.csdn.net/Allen_xiaofei/article/details/79839719)

> vi /etc/apache2/apache2.conf 
>
> // 底下新加一条配置
>
> AcceptFilter http none

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



# maven 

## 介绍

### META-INF/services

[官方解读](https://docs.oracle.com/javase/6/docs/api/java/util/ServiceLoader.html)

>  =>   保存服务提供配置文件，
>
> 文件名 =>   接口的全限定类名
>
> 文件内容  =>  接口实现的全限定类名

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

### RSocket 

```java
<dependency>
    <groupId>io.rsocket</groupId>
    <artifactId>rsocket-core</artifactId>
</dependency>
<dependency>
    <groupId>io.rsocket</groupId>
    <artifactId>rsocket-transport-netty</artifactId>
</dependency>
```

### RSocket 与 Spring 集成

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-rsocket</artifactId>
</dependency>
```

### Reactive MongoDB

```java
<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
</dependency>
```

### Reactive Redis

```java
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
</dependency>
```

### Spring Data R2DBC

```java
<!-- spring data r2dbc -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-r2dbc</artifactId>
</dependency>
 
<!-- r2dbc 连接池 -->
<dependency>
     <groupId>io.r2dbc</groupId>
     <artifactId>r2dbc-pool</artifactId>
</dependency>
 
<!--r2dbc mysql 库 -->
<dependency>
     <groupId>dev.miku</groupId>
     <artifactId>r2dbc-mysql</artifactId>
</dependency>
```

### spring-cloud-stream

```java
<dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-stream</artifactId>
</dependency>
```

### spring-cloud-stream-reactive

```java
<dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-stream-reactive</artifactId>
</dependency>
```

### spring-cloud-starter-stream-rabbit

```java
<dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
</dependency>
```

### reactor-test 测试组件

```java
<dependency>
        <groupId>io.projectreactor</groupId>
        <artifactId>reactor-test</artifactId>
        <scope>test</scope>
</dependency>
```

### validate

```java
//  注意 如果两者版本不匹配，导致  No validator could be found for constraint
<!--字段验证 start-->
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>6.0.16.Final</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
    <version>2.0.1.Final</version>
</dependency>
<!--字段验证 end-->
//  字段前加 @NotEmpty()
// controller 入参加 @Valid
// 全局 Exception 监听   MethodArgumentNotValidException
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

## 父子工程版本控制

查看自己的博客

##  通过javax.servlet-api  无法访问web-inf下的jsp，已经指定了jsp的前后缀

如下图所示，运行`spring-boot:run`

<img src="./images/2021-04-19-1.jpg" alt="解决办法" style="zoom:67%;" />



## 运行上面的spring-boot:run 保存无法 process terminated 且 报错为乱码

+ 乱码解决方案：  `Setting->maven->runner`   修改编码格式  `VMoptions` 改为 `-Dfile.encoding=GB2312`

##  maven 运行报错  Cannot resolve plugin

[参考链接](https://blog.csdn.net/qq_38287890/article/details/101628637)

查看一下maven home 目录以及本地仓库，路径 `setting>build,execution,deployment>maven 

## Maven 报错 Could not transfer artifact 和 501 HTTPS Required.

[参考链接](https://blog.csdn.net/u010565545/article/details/105135180)

## 导出maven 项目依赖的jar包

[参考文章](https://www.cnblogs.com/52liming/p/7750633.html)

```java
// 进入需要 导出的项目根目录(即 pom.xml所在目录)
// 执行如下命令，生成的依赖 jar包会在  /target/dependency  下
mvn dependency:copy-dependencies
```

## 引入导出的jar包

<img src="./images/2021-05-14-1.jpg" alt="操作步骤" style="zoom:67%;" />

## 将本地jar包放入maven仓库

[参考链接](https://blog.csdn.net/lvdaan/article/details/79760976)

```java
// 配置maven 环境变量
mvn install:install-file -DgroupId=com.jf.ams -DartifactId=ams-api -Dversion=1.2.2-SNAPSHOT -Dpackaging=jar -Dfile=ams-api-1.2.2-20171019.023745-1.jar
```

## mvn springboot:run 中文乱码

[解决办法](https://blog.csdn.net/weixin_42610529/article/details/95602598)

```java
//application.properties或application.yml
spring.http.encoding.force=true
spring.http.encoding.charset=UTF-8
spring.http.encoding.enabled=true
server.tomcat.uri-encoding=UTF-8
```





## springboot 将默认的logback改为 slf4j2

```java
// springboot 版本
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.4.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

// 变量声明
<properties>
    <java.version>1.8</java.version>
    <log4j.version>2.13.3</log4j.version>
</properties>

// 替换日志类型
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<!--使用log4j -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-1.2-api</artifactId>
    <version>${log4j.version}</version>
</dependency>

<!-- 日志 Log4j2 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
<!-- 桥接：告诉Slf4j使用Log4j2 -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>${log4j.version}</version>
</dependency>
<!-- 桥接：告诉commons logging使用Log4j2 -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-jcl</artifactId>
    <version>${log4j.version}</version>
</dependency>
<!-- log4j2 无锁并发 -->
<dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>3.4.2</version>
</dependency>
```

==参考 log4j2.xml==

[官方参考文档](https://logging.apache.org/log4j/2.x/manual/appenders.html#RolloverStrategies)

```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xml>
<!-- Log4j 2.DSConfig 配置文件。每60秒自动检查和应用配置文件的更新； -->
<Configuration status="warn" monitorInterval="60"
	 strict="true">
	<Properties>
		<Property name="PROJECT_NAME">client</Property>
		<Property name="LOG_HOME">./logs/</Property>
		<Property name="COMMON_LEVEL">info</Property>
		<Property name="LOG_PATTERN">%d{yyyy-MM-dd HH:mm:ss,SSS} [%C %M %L] %t [%p] - %msg%n
		</Property>
	</Properties>
	<Appenders> 
		<!-- 输出到控制台 -->
		<Console name="console" target="SYSTEM_OUT" follow="true">
			<PatternLayout charset="utf-8" pattern="${LOG_PATTERN}" />
		</Console>
		<RollingFile name="debugAppender"
			fileName="${LOG_HOME}/debug.log"
			filePattern="${LOG_HOME}/$${date:yyyy-MM-dd}-%i/debug-%d{MM-dd}.log">
			<filters>
				<ThresholdFilter level="error" onMatch="DENY"
					onMismatch="NEUTRAL" />
				<ThresholdFilter level="info" onMatch="DENY"
					onMismatch="NEUTRAL" />
				<ThresholdFilter level="warn" onMatch="DENY"
					onMismatch="NEUTRAL" />
				<ThresholdFilter level="debug" onMatch="ACCEPT"
					onMismatch="DENY" />
			</filters>
			<PatternLayout charset="UTF-8">
				<pattern>${LOG_PATTERN}</pattern>
			</PatternLayout>
			<Policies>
				<OnStartupTriggeringPolicy />
				<TimeBasedTriggeringPolicy />
				<!-- 封存文件的大小 -->
				<SizeBasedTriggeringPolicy size="100 MB" />
			</Policies>
			<!-- 封存文件的序号的最大值。（超过最大值时，将有文件被删除）默认7 -->
			<DefaultRolloverStrategy max="500">
				<Delete basePath="${LOG_HOME}" maxDepth="500">
					<IfFileName glob="*/*/debug-*.log">
						<IfLastModified age="15d" />
					</IfFileName>
				</Delete>
			</DefaultRolloverStrategy>
		</RollingFile>
		<RollingFile name="infoAppender"
			fileName="${LOG_HOME}/info.log"
			filePattern="${LOG_HOME}/$${date:yyyy-MM-dd}-%i/info-%d{MM-dd}.log">
			<filters>
				<ThresholdFilter level="error" onMatch="DENY"
					onMismatch="NEUTRAL" />
				<ThresholdFilter level="warn" onMatch="DENY"
					onMismatch="NEUTRAL" />
				<ThresholdFilter level="info" onMatch="ACCEPT"
					onMismatch="DENY" />
			</filters>
			<PatternLayout charset="UTF-8">
				<pattern>${LOG_PATTERN}</pattern>
			</PatternLayout>
			<Policies>
				<OnStartupTriggeringPolicy />
				<TimeBasedTriggeringPolicy />
				<SizeBasedTriggeringPolicy size="100 MB" />
			</Policies>
			<DefaultRolloverStrategy max="500">
				<Delete basePath="${LOG_HOME}" maxDepth="500">
					<IfFileName glob="*/*/info-*.log">
						<IfLastModified age="15d" />
					</IfFileName>
				</Delete>
			</DefaultRolloverStrategy>
		</RollingFile>
		<RollingFile name="warnAppender"
			fileName="${LOG_HOME}/warn.log"
			filePattern="${LOG_HOME}/$${date:yyyy-MM-dd}-%i/warn-%d{MM-dd}.log">
			<filters>
				<ThresholdFilter level="error" onMatch="DENY"
					onMismatch="NEUTRAL" />
				<ThresholdFilter level="warn" onMatch="ACCEPT"
					onMismatch="DENY" />
			</filters>
			<PatternLayout charset="UTF-8">
				<pattern>${LOG_PATTERN}</pattern>
			</PatternLayout>
			<Policies>
				<OnStartupTriggeringPolicy />
				<TimeBasedTriggeringPolicy />
				<SizeBasedTriggeringPolicy size="100 MB" />
			</Policies>
			<DefaultRolloverStrategy max="500">
				<Delete basePath="${LOG_HOME}" maxDepth="500">
					<IfFileName glob="*/*/warn-*.log">
						<IfLastModified age="15d" />
					</IfFileName>
				</Delete>
			</DefaultRolloverStrategy>
		</RollingFile>
		<RollingFile name="errorAppender"
			fileName="${LOG_HOME}/error.log"
			filePattern="${LOG_HOME}/$${date:yyyy-MM-dd}-%i/error-%d{MM-dd}.log">
			<filters>
				<ThresholdFilter level="error" onMatch="ACCEPT"
					onMismatch="DENY" />
			</filters>
			<PatternLayout charset="UTF-8">
				<pattern>${LOG_PATTERN}</pattern>
			</PatternLayout>
			<Policies>
				<OnStartupTriggeringPolicy />
				<TimeBasedTriggeringPolicy />
				<SizeBasedTriggeringPolicy size="100 MB" />
			</Policies>
			<DefaultRolloverStrategy max="500">
				<Delete basePath="${LOG_HOME}" maxDepth="500">
					<IfFileName glob="*/*/error-*.log">
						<IfLastModified age="15d" />
					</IfFileName>
				</Delete>
			</DefaultRolloverStrategy>
		</RollingFile>
	</Appenders>
	<Loggers>
		<AsyncRoot level="${COMMON_LEVEL}" includeLocation="true">
			<AppenderRef ref="console" />
			<AppenderRef ref="debugAppender" />
			<AppenderRef ref="infoAppender" />
			<AppenderRef ref="warnAppender" />
			<AppenderRef ref="errorAppender" />
		</AsyncRoot>
	</Loggers>
</Configuration>
```

## mvn install 报错 在类路径或引导类路径中找不到程序包 java.lang

[参考解决办法](https://blog.csdn.net/cleargreen/article/details/56284013)

```java
<!-- ... -->  
<plugin>  
 <groupId>org.apache.maven.plugins</groupId>  
 <artifactId>maven-compiler-plugin</artifactId>  
 <version>${maven.compiler.version}</version>  
 <configuration>  
   <source>${java.version}</source>  
   <target>${java.version}</target>  
   <encoding>${project.build.sourceEncoding}</encoding>  
   <showWarnings>true</showWarnings>  
   <compilerArguments>  
     <verbose />  
     <!-- 这个配置很特殊：windows下使用分号（;）分隔，linux/mac下使用冒号（:）分隔 -->  
     <bootclasspath>${java.home}/lib/rt.jar;${java.home}/lib/jce.jar</bootclasspath>  
   </compilerArguments>  
 </configuration>  
</plugin>  
<!-- ... -->  
```



## springboot 项目构建 编译报错，提示 找不到符号

==具体报错内容示例==

```java
17:00:10   symbol:   method setSmscontent(java.lang.String)
17:00:10   location: variable smsLog of type com.onecard.orderservice.model.loando.SmsLogDO
17:00:10 [ERROR] /data/jenkins-home/archive/src/XJ-onecard-orderService/orderService.git/test-k8s/orderService-web/src/main/java/com/onecard/orderservice/service/impl/OrgCapitalLoanNoticeServiceImpl.java:[192,43] cannot find symbol
17:00:10   symbol:   method getContent()
17:00:10   location: variable notice of type com.onecard.orderservice.model.dto.OrgCapitalLoanNoticeDTO
17:00:10 [ERROR] /data/jenkins-home/archive/src/XJ-onecard-orderService/orderService.git/test-k8s/orderService-web/src/main/java/com/onecard/orderservice/service/impl/OrgCapitalLoanNoticeServiceImpl.java:[193,74] cannot find symbol
17:00:10   symbol:   method getAppId()
17:00:10   location: variable content of type com.onecard.orderservice.model.dto.OrgCapitalLoanNoticeDTO.NoticeContent
17:00:10 [ERROR] /data/jenkins-home/archive/src/XJ-onecard-orderService/orderService.git/test-k8s/orderService-web/src/main/java/com/onecard/orderservice/service/impl/OrgCapitalLoanNoticeServiceImpl.java:[194,38] cannot find symbol
17:00:10   symbol:   method getAppStatus()
```

### 解决办法

> 找到一开始报错的地方，进入代码查看。
>
> 我这里是因为git 分支合并导致controller中有两个一模一样的方法

## 排除 父项目中的插件

```java
// 通过 configuration/skip 来进行排除 
<build>
     <plugins>
         <plugin>
             <groupId>org.sonarsource.scanner.maven</groupId>
             <artifactId>sonar-maven-plugin</artifactId>
             <configuration>
            	 <skip>true</skip>
             </configuration>
         </plugin>
     </plugins>
</build>
```

## deploy打包到私服

```java
// 修改maven 的配置信息，配置私服的账号和密码  => settings.xml
<servers>
    <server>   
		<id>releases</id>   
		<username>admin</username>   
		<password>admin123</password>   
	</server>
	<server>   
		<id>snapshots</id>   
		<username>admin</username>   
		<password>admin123</password>   
	</server>
</servers>
// 修改pom.xml中私服地址
<distributionManagement>
	<snapshotRepository>
		<id>snapshots</id>
		<name>snapshots</name>
		<url>http://maven.9f.cn/nexus/content/repositories/snapshots/</url>
		<layout>default</layout>
	</snapshotRepository>
</distributionManagement>
//  =======  注意：这里 pom.xml中repository.id字段需要和 settings.xml中的server.id字段一样
//            不然会上传失败，报错401 Unauthorized   ======
```

## 打包-工具包

```java
   <build>
        <plugins>
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                </configuration>
            </plugin>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
        </plugins>
    </build>
```



# gradle 

## 安装

[参考安装步骤](https://blog.csdn.net/qq_34972627/article/details/124911994)

> 1、官网下载二进制文件  binary-only
>
> https://gradle.org/releases/
>
> 2、解压文件
>
> 3、配置环境变量
>
> GRADLE_HOME   => 解压目录
>
> Path  => 新加一行   %GRADLE_HOME%
>
> GRADLE_USER_HOME  =>  同级创建用户目录
>
> 4、验证  win+r  cmd   输入 gradle -v
>
> 5、添加配置文件    在 `init.d` 目录下添加 `init.gradle`文件，内容如下
>
> allprojects {
>     repositories {
>         mavenLocal()
>         maven { name "aliyun" ; url 'https://maven.aliyun.com/repository/public' }
>         mavenCentral()
>     }
>     buildscript { 
>         repositories { 
>             mavenLocal()
>             maven { name "aliyun" ; url 'https://maven.aliyun.com/repository/public' }
>             mavenCentral()
>         }
>     }
> } 

## 问题

### 构建提示 Received status code 401 from server: Unauthorized

> spring 5 用 gradle 	4.x版本

### 下载包 read time out

> 修改 gradle.properties  添加调用时长
>
> org.gradle.jvmargs=-Xmx1536M -Dorg.gradle.internal.http.connectionTimeout=120000 -Dorg.gradle.internal.http.socketTimeout=120000

### springboot 构建时报错  Execution failed for task ':buildSrc:checkFormatMain'.

[参考解决办法](https://blog.csdn.net/fxzzq/article/details/121804843)

> 修改项目目录下的 `buildSrc` > `build.gradle` ，将其中的  `io.spring.javaformat` 行注释掉

# JAVA

## 简写对照表

```java
ECS ==>  Elastic Compute Service  =>  云服务器
RDS ==>  Relation Data System  => 关系数据系统
```



## 便捷方法

### 手动执行分页

```java
List<Object> data=getData();
int pageNo=1;
int pageSize=30;
int iPageSize=(pageNo-1)*pageSize;
Stream<Object> pageData=data.stream().skip(iPageSize).limit(pageSize);
List<Object> res=pageData.collect(Collectors.toList());
```



## JVM

### 线上问题定位

```java
// 生成当前 jvm dump文件
jmap -dump:format=b,file=heap.prof pid
//  查看整个 jvm 内存状态
jmap -heap pid
// 查看JVM堆中对象详细占用情况
jmap -histo pid
//  栈信息
jstack pid > stack.txt
// 查看进程中线程占cpu的比例
ps -mp pid -o THREAD,tid,time
```



### 观测工具

#### visual vm

##### 启动 jconsole 插件

[参考使用方法](https://blog.csdn.net/qq_22597325/article/details/86623911)

[demos-and-samples下载样例，不同jdk版本好像通用](https://download.oracle.com/otn/java/jdk/8u321-b07-demos/df5ad55fdd604472a86a45a217032c7d/jdk-8u321-windows-x64-demos.zip)

```java
1、安装该插件
2、关联目录，需要下载jdk的 demos and samples
3、重启 visual vm
```



### 个人版 idea  jvm配置

##### cms收集器

```java
-Xms2g
-Xmx3g
-Xmn1536m
-XX:MetaspaceSize=256m
-XX:MaxMetaspaceSize=768m
-XX:ReservedCodeCacheSize=256m
-XX:CompressedClassSpaceSize=256m
-XX:+PrintGCDetails             
-Xloggc:G:\base\logs\idea.log
-XX:MaxDirectMemorySize=512m
```

#####  parallel 收集器

> 根据观察 -Xmn即为eden的大小，survivor的区域没包含进去

```java
-Xms2048m
-Xmx2048m
-Xmn1g
-XX:SurvivorRatio=6
-XX:MetaspaceSize=512m
-XX:MaxMetaspaceSize=768m
-XX:ReservedCodeCacheSize=256m
-XX:CompressedClassSpaceSize=256m
-XX:+PrintGCDetails             
-Xloggc:D:\personal-project-list\gc-logs\idea.log
-XX:+UseParallelOldGC
-XX:ParallelGCThreads=2
```

##### 别人的参考

[参考webstorm](https://gist.github.com/macd2/c5b5692a47abd06e6298d676971fcc7a)

[idea64.vm](https://developpaper.com/intellij-idea-performance-optimization-tutorial-detailed-explanation/)

```java
-server
-Xms3g
-Xmx3g
-XX:NewRatio=3
-Xss16m
-XX:+UseConcMarkSweepGC
-XX:+CMSParallelRemarkEnabled
-XX:ConcGCThreads=4
-XX:ReservedCodeCacheSize=240m
-XX:+AlwaysPreTouch
-XX:+TieredCompilation
-XX:+UseCompressedOops
-XX:SoftRefLRUPolicyMSPerMB=50
-Dsun.io.useCanonCaches=false
-Djava.net.preferIPv4Stack=true
-Djsse.enableSNIExtension=false
-ea
-Dsun.io.useCanonCaches=false
-Djdk.http.auth.tunneling.disabledSchemes=""
-Djdk.attach.allowAttachSelf=true
-Djdk.module.illegalAccess.silent=true
-Dkotlinx.coroutines.debug=off
-XX:+HeapDumpOnOutOfMemoryError
```

##### 注意点

+ 高版本可能不知道打印gc日志的命令，测试版本`2021.3.2`
+ metaspace 和 heap 大小别固定。因为可能同时打开很多个项目，可能只打开一个。让他动态变化内容能使用更充分

### 常用命令

> // 查看基础配置信息
>
> java -XX:+PrintFlagsInitial
>
> // 打印 gc日志，并保存到外部文件
>
> -XX:+PrintGCDetails             -Xloggc:./gc/gc.log
>
> //  配置 eden 和 survivor 大小 =>  eden/from  =>  默认8
>
> -XX:SurvivorRatio
>
> // 配置 新生代 和 老生代 比例  =>  老年代/新生代  =>  默认2
>
> -XX:NewRatio
>
> // 设置直接内存大小  =>   貌似无用，直接内存不归jvm分配
>
> -XX:MaxDirectMemorySize=512m
>
> // 设置年轻代大小  => 优先级比 NewRatio高
>
> -Xmn1536m
>
> //  设置指针压缩大小  => 可行  
>
> //  需要先开启 -XX:+UseCompressedOops    -XX:+UseCompressedClassesPointers   =>  jdk8默认开启
>
> -XX:CompressedClassSpaceSize=128m
>
> // 本地内存追踪  => 三个选项 off | summary | detail   =>   配合  jcmd pid VM.native_memory 使用
>
> //   =>  windows 下无效
>
> -XX:NativeMemoryTracking=detail
> // 回收缓存
> -XX:+UseCodeCacheFlushing
>
> // 打印内存溢出 dump 文件
>
> -XX:+HeapDumpOnOutOfMemoryError 
>
> -XX:HeapDumpPath=/dump/heapdump.dump
>
> // 指定致命错误日志
>
> -XX:ErrorFile=/var/log/java/java_error%p.log
>
> // 设置永久代 jdk7之前
>
> -XX:PermSize
>
> -XX:MaxPermSize
>
> //  元数据大小，也在 heap 上
>
> XX:MaxMetaspaceSize

### 通用查询命令

```java
// 查看运行中的 Metaspace  =>  以字节为单位
jinfo -flag MaxMetaspaceSize pid
//  查看使用了哪种垃圾回收算法
java -XX:+PrintCommandLineFlags -version
//  给运行中的进程 添加日志打印 => 输出到标准输出流  => 为启动bash中nohup指向文件
jinfo -flag +PrintGCDetails  或  jinfo -flag +PrintGC

```

### GC收集器选择

[参考](https://blog.csdn.net/pf1234321/article/details/82288921)

|                   java 命令                   |                新生代GC方式                 |          老年代/持久代GC方式           |
| :-------------------------------------------: | :-----------------------------------------: | :------------------------------------: |
|               -XX:+UseSerialGC                |                   串行GC                    |                 串行GC                 |
|              -XX:+UseParallelGC               |              PS GC-ParallerGC               |              并行 MSC GC               |
|            -XX:+UseConcMarkSweepGC            |                  ParNew GC                  |           并行GC，出错串行GC           |
|               -XX:+UseParNewGC                |                并行GC-ParNew                |                 串行GC                 |
|             -XX:+UseParallelOldGC             |                    PS GC                    |           并行 Compacting GC           |
| -XX:+UseConcMarkSweepGC<br />-XX:-UseParNewGC |                   串行GC                    |      并发GC，当出现错误使用穿行GC      |
|                  不支持方式                   | -XX:+UseParNewGC<br />-XX:+UseParallelOldGC | -XX:+UseParNewGC<br />-XX:+UseSerialGC |

#### 名词定义

```java
ParNew  =>  多线程的串行
PS GC  =>  Parallel Scavenge  
并行GC   =>  Parallel Old
MSC  =>  Serial Old
```

### GC ROOT	

```java
1、虚拟机栈(栈帧中的本地变量表)中引用的对象
2、方法区中类静态变量引用的对象
3、方法区中常量引用的对象
4、本机方法栈中JNI(即一般说的Native方法)引用的对象
```

### 垃圾收集器

+ `-XX:+UseSerialGC`

> 新生代 - Serial   复制算法  单线程 gc  STW(stop the world)
>
> 老年代 - Serial Old 标记整理算法  单线程 gc STW(stop the world)

+ `-XX:+UseParNewGC` 

> 新生代 - ParNew 复制算法   多线程 gc STW(stop the world)
>
> 老年代 - Serial Old 标记整理算法  单线程 gc  STW(stop the world)

+ `-XX:+UseParallerGC`

> 追求吞吐量：  (运行用户代码时间)/(运行用户代码时间 + 垃圾收集时间)
>
> -XX:MaxGCPauseMills  最大垃圾收集停顿时间
>
> -XX:GCTimeRatio  吞吐量大小
>
> -XX:+UseAdaptiveSizePolicy  自动调整

> 新生代 - ParallelScavenge  复制算法  多线程gc
>
> 老年代 -  Serial Old  标记整理算法  单线程  STW(stop the world)

+ `-XX:+UseParallelOldGC`

> 新生代 - Parallel Scavenge 复制算法  多线程gc
>
> 老年代 - Parallel Old  标记整理算法  多线程gc

+ `XX:+UseConcMarkSweepGC`

> 过程：1、初始标记 2、并发标记 3、重新标记 4、并发清除

> -XX:CMSInitiatingOccupancyFraction 触发 gc条件  =>  需要预留空间给浮动垃圾(并发清理时生成的对象)
>
> -XX:CMSFullGCsBeforeCompaction  多少次不压缩gc后压缩一次
>
> -XX:UseCMSCompactAtFullCollection   Full gc时开启内存碎片整合

> 新生代 - ParNew 复制算法  多线程 gc  STW(stop the world)
>
> 老年代 - Concurrent Mark Sweep 标记清除算法 多线程 gc    初始标记、重新标记  => STW(stop the world)
>
> =>  Concurrent Mode Failure  时改为  Serial Old   STW(stop the world)

+ `XX:+UseG1GC`

> 过程：1、初始标记 2、并发标记 3、最终标记  4、筛选回收

> 新生代、老年代都是用 G1

### 汇总

> serial  单线程收集 新生代   复制
>
> parnew 多线程版 serial
>
> Parallel Scavenge  新生代，能控制吞吐量  多线程复制
>
> Serial Old  serial老年代版本   标记清除整理
>
> Parallel Old   Parallel Scavenge 老年代版本  标记整理
>
> 



### 查看运行中的类

#### 使用 jdk自带的  HSDB

[参考步骤](https://www.cnblogs.com/alinainai/p/11070923.html)

```java
// 启动 HSDB
java -cp ./sa-jdi.jar sun.jvm.hotspot.HSDB   // sa-jdi.jar在 jdk安装目录下的 lib目录  可用
java -classpath "%JAVA_HOME%/lib/sa-jdi.jar" sun.jvm.hotspot.HSDB   // 待测试
// 绑定要查看的jvm
jps -l  //查看对应的 进程号
==>  HSDB 右上角  File >  Attach to HotSpot process 输入对应的进程号
// 开始查看相关信息
```

### 问题

#### Native memory tracking is not enabled

==window好像无法查看本地内存，unbutu测试可以==

#### -XX:MetaspaceSize貌似没生效

[参考](https://stackoverflow.com/questions/47426407/java8-metaspacesize-flag-not-working)

[官方解读参数](https://bugs.openjdk.java.net/browse/JDK-8067205)

```java
1、如果没有配置-XX:MetaspaceSize，那么触发FGC的阈值是21807104（约20.8m），可以通过jinfo -flag MetaspaceSize pid得到这个值；
2、如果配置了-XX:MetaspaceSize，那么触发FGC的阈值就是配置的值；
3、Metaspace由于使用不断扩容到-XX:MetaspaceSize参数指定的量，就会发生FGC；且之后每次Metaspace扩容都可能会发生FGC（至于什么时候会，比较复杂，跟几个参数有关）；
4、如果Old区配置CMS垃圾回收，那么扩容引起的FGC也会使用CMS算法进行回收；
5、如果MaxMetaspaceSize设置太小，可能会导致频繁FullGC，甚至OOM；
```

> -XX:MetaspaceSize 设置了不代表它的初始大小就是改设定值，我观察发现它的默认大小一般在20m，之后再每次GC(不论新生代还是老生代)触发后，它的大小都会动态变化。可以通过看GC日志。

#### jstat MC 小于 MU

[参考](https://stackoverflow.com/questions/65841876/why-is-mc-greater-than-mu-when-i-use-the-jstat-command)

## mybatis 配置

### 日期大小比对

<font size=6px color=red>datetime数据库类型，jdbcType必须为TIMESTAMP，不能是DATE</font>

```java
// 数据库中的类型是  datetime   
<select id="selectByTime" resultMap="BaseResultMap" >
    select
    <include refid="Base_Column_List" />
    from t_vip_dz_recharge_record
    where  <![CDATA[ recharge_date >= #{start ,jdbcType=TIMESTAMP}  ]]> and  <![CDATA[ recharge_date < #{end ,jdbcType=TIMESTAMP}  ]]>
  </select>
```



## springboot

### java 发邮件服务

#### 问题

##### 配置了相关参数，报错535

> 535 Error: authentication failed, system busy

###### 解决办法

> 1、确认对应的邮箱是否开启  POP3/SMTP服务
>
> 2、确认 客户端授权码 填写正确

### 校验 validate

[maven配置参考](#validate)

#### 源码解读

> 源码跟踪   入口：
> 启动点   ValidationInitializer
>
> 文件    javax.validation.spi.ValidationProvider
>
> 类    HibernateValidator

### AOP配置

#### 源码解读

> 入口 ：
>
> 类 AopAutoConfiguration

#### 总结

> 
>
> 

#### 注解优先级

>  //  一般流程
>
> around-before  =>  before => controller =>  afterReturing => after  =>  around-after 
>
> // 异常情况
>
> around-before  =>  before => controller =>  afterThrowing => after  =>  around-after 

#### 前置依赖

```java
<!-- spring aop start-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<!-- spring aop end-->
```



#### 一般模板

```java
@Aspect
@Component
@Slf4j
public class DecryptHandlerAspect {
    @Pointcut("execution(* com.yichen.basic.controller.*.*(..))")
    	public void decryptPointCut() {
    }
	@Before("decryptPointCut()")
    public void check(JoinPoint joinPoint) {
        
    }
}
```

#### 多个切面优先级问题

[参考解答](https://blog.csdn.net/Aeve_imp/article/details/93098524)

> 可以通过 @Order() 注解，值越小越先  
>
> 经实验确定，确实是order越小越是最先执行，但更重要的是最先执行的最后结束。

#### 测试demo

##### aspect A

```java
@Aspect
@Slf4j
@Component
@Order(-999)
public class AspectTestA {

    @Pointcut("execution(* com.yichen.casetest.controller.AspectController.*(..))")
    public void decryptPointCut() {
    }

    @Before("decryptPointCut()")
    public void before(){
        log.info("AspectTestA before");
    }

    @After("decryptPointCut()")
    public void after(){
        log.info("AspectTestA after");
    }

    @AfterReturning("decryptPointCut()")
    public void afterReturning(){
        log.info("AspectTestA afterReturning");
    }

    @AfterThrowing("decryptPointCut()")
    public void afterThrowing(){
        log.info("AspectTestA AfterThrowing");
    }


    @Around("decryptPointCut()")
    public Object around(ProceedingJoinPoint proceedJoinPoint) {
        Object proceed = null;
        log.info("AspectTestA around start");
        try {
            proceed = proceedJoinPoint.proceed();
            log.info("AspectTestA 请求结果 {}",proceed);
        }
        catch (Throwable e){
            log.error("AspectTestA 错误 {}",e.getMessage(),e);
        }
        log.info("AspectTestA around stop");
        return proceed;
    }

}
```

##### aspect B

```java
@Aspect
@Slf4j
@Component
@Order(-100)
public class AspectTestB {

    @Pointcut("execution(* com.yichen.casetest.controller.AspectController.*(..))")
    public void decryptPointCut() {
    }

    @Before("decryptPointCut()")
    public void before(){
        log.info("AspectTestB before");
    }

    @After("decryptPointCut()")
    public void after(){
        log.info("AspectTestB after");
    }

    @AfterReturning("decryptPointCut()")
    public void afterReturning(){
        log.info("AspectTestB afterReturning");
    }

    @AfterThrowing("decryptPointCut()")
    public void afterThrowing(){
        log.info("AspectTestB AfterThrowing");
    }


    @Around("decryptPointCut()")
    public Object around(ProceedingJoinPoint proceedJoinPoint) {
        Object proceed = null;
        log.info("AspectTestB around start");
        try {
            proceed = proceedJoinPoint.proceed();
            log.info("AspectTestB 请求结果 {}",proceed);
        }
        catch (Throwable e){
            log.error("AspectTestB 错误 {}",e.getMessage(),e);
        }
        log.info("AspectTestB around stop");
        return proceed;
    }

}
```

##### controller

```java
@RequestMapping("/aspect")
@RestController
@Slf4j
public class AspectController {

    @RequestMapping("/test")
    public String test(){
        log.info("请求到达controller");
        return "controller arrive";
    }

}
```

##### 测试

> http://localhost:8088/aspect/test

##### 运行结果

```java
2022-03-24 11:29:45.408  INFO 16208 --- [nio-8088-exec-1] com.yichen.casetest.aspect.AspectTestA   : AspectTestA around start
2022-03-24 11:29:45.409  INFO 16208 --- [nio-8088-exec-1] com.yichen.casetest.aspect.AspectTestA   : AspectTestA before
2022-03-24 11:29:45.409  INFO 16208 --- [nio-8088-exec-1] com.yichen.casetest.aspect.AspectTestB   : AspectTestB around start
2022-03-24 11:29:45.409  INFO 16208 --- [nio-8088-exec-1] com.yichen.casetest.aspect.AspectTestB   : AspectTestB before
2022-03-24 11:29:45.417  INFO 16208 --- [nio-8088-exec-1] c.y.c.controller.AspectController        : 请求到达controller
2022-03-24 11:29:45.418  INFO 16208 --- [nio-8088-exec-1] com.yichen.casetest.aspect.AspectTestB   : AspectTestB afterReturning
2022-03-24 11:29:45.418  INFO 16208 --- [nio-8088-exec-1] com.yichen.casetest.aspect.AspectTestB   : AspectTestB after
2022-03-24 11:29:45.418  INFO 16208 --- [nio-8088-exec-1] com.yichen.casetest.aspect.AspectTestB   : AspectTestB 请求结果 controller arrive
2022-03-24 11:29:45.419  INFO 16208 --- [nio-8088-exec-1] com.yichen.casetest.aspect.AspectTestB   : AspectTestB around stop
2022-03-24 11:29:45.419  INFO 16208 --- [nio-8088-exec-1] com.yichen.casetest.aspect.AspectTestA   : AspectTestA afterReturning
2022-03-24 11:29:45.420  INFO 16208 --- [nio-8088-exec-1] com.yichen.casetest.aspect.AspectTestA   : AspectTestA after
2022-03-24 11:29:45.420  INFO 16208 --- [nio-8088-exec-1] com.yichen.casetest.aspect.AspectTestA   : AspectTestA 请求结果 controller arrive
2022-03-24 11:29:45.420  INFO 16208 --- [nio-8088-exec-1] com.yichen.casetest.aspect.AspectTestA   : AspectTestA around stop
```



### 通用jar启动脚本

```java
nohup java -jar -Xms256m -Xmn256m -XX:+PrintGCDetails -XX:+PrintGC -Xloggc:./gc.log  eureka-server-0.0.1-SNAPSHOT.jar  > eureka.log 2>&1 &
```



### springboot 和 springcloud 版本对应

[版本对应-范围判定](https://start.spring.io/actuator/info)

#### 两者具体的对应关系

+ 打开springcloud 官网  https://spring.io/projects/spring-cloud#learn

<img src="./images/2021-12-14-1.jpg" alt="示例" style="zoom: 50%;" />

+ 点击对应的 `Reference Doc`  我这里选  `Hoxton.SR12`

<img src="./images/2021-12-14-2.jpg" alt="示例" style="zoom:50%;" />

+ 通过 改 url切换

<img src="./images/2021-12-14-3.jpg" alt="示例" style="zoom:50%;" />

#### 全局指定 spring-cloud版本

```java
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Hoxton.SR12</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```



 ### 搭建本地 eureka

[参考方法](https://blog.csdn.net/tlycherry/article/details/106401579)

##### 步骤

1、添加依赖

```java
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    <version>2.0.2.RELEASE</version>
</dependency>
//  注意 springboot 和 springcloud 版本号需要对应。
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.6.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

2、添加配置信息

```java

#由于该应用为注册中心，所以设置为false，表示不向注册中心注册自己
eureka.client.fetch-registry=false
#注册中心的职责是维护实例，并不需要去检索服务，所以设置为false
eureka.client.register-with-eureka=false
#服务注册中心地址
eureka.client.serviceUrl.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/
eureka.instance.hostname=127.0.0.1
server.port=8099
```

3、启动注册中心服务

```java
@EnableEurekaServer // 启动类上添加注解
@SpringBootApplication
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
```

4、测试

> 浏览器访问  http://127.0.0.1:8099

#### springcloud 使用 eureka

##### pom 依赖

```java
<!--  eureka 注册中心   -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        <version>2.0.2.RELEASE</version>
    </dependency>
<!--  end  -->
```

##### 配置

```java
// 配置文件
# eureka 配置信息
eureka.client.service-url.defaultZone=http://127.0.0.1:8099/eureka/
spring.application.name=feign-user
// 启动类  添加注解
@EnableEurekaClient
```



### 整合redis-jedis

[参考链接](https://www.iteye.com/blog/ydlmlh-2259129)

#### 所需依赖

```java
<!-- redis 所需要的依赖 -->
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-redis</artifactId>
    <version>2.2.6.RELEASE</version>
</dependency>
<!-- 连接池需要配置 -->
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
</dependency>
<!-- end -->
```



#### 配置信息  application.properties

```java
# redis  配置信息，设置默认 database库为 10
spring.redis.password=yichen
spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.database=10
# jedis 连接池配置信息
spring.redis.max.idle=10
spring.redis.max.total=30
spring.redis.max.wait.mills=-1
```

####  配置类

```java
@Configuration
public class RedisConfig {

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.database}")
    private Integer index;

    @Value("${spring.redis.max.idle}")
    private Integer maxIdle;

    @Value("${spring.redis.max.total}")
    private Integer maxTotal;

    @Value("${spring.redis.max.wait.mills}")
    private Integer maxWaitMills;


    /**
     * 每次连接都会创建新的连接，资源消耗较大
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(index);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setPort(port);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    /**
     * 使用 redis 连接池
     * @return
     */
    @Bean("jedisConnectionFactory2")
    public RedisConnectionFactory redisConnectionFactory(){
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(10);
        poolConfig.setTimeBetweenEvictionRunsMillis(60000L);
        poolConfig.setMaxWaitMillis(maxWaitMills);
        RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(index);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setPort(port);
        JedisClientConfiguration.JedisClientConfigurationBuilder builder=JedisClientConfiguration.builder();
        JedisClientConfiguration jedisClientConfiguration=builder.usePooling().poolConfig(poolConfig).build();
        return new JedisConnectionFactory(redisStandaloneConfiguration,jedisClientConfiguration);
    }


    @Bean
    public StringRedisTemplate redisTemplate(JedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(factory);
        // key采用String的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        // value序列化方式采用jackson
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

}
```



### 引入外部jar包

[参考文章](https://www.jianshu.com/p/8c28a52e90c6)

```java
//  在resource目录下创建 jar文件夹(可以自定义)，放入需要引入的jar包。然后在pom中添加如下内容
<!-- 视频 音频流处理 -->
<dependency>
    <groupId>xuggle</groupId>
    <artifactId>xuggle-xuggler</artifactId>
    <version>5.4</version>
    <scope>system</scope>
    <systemPath>${project.basedir}\src\main\resources\jar\xuggle-xuggler-5.4.jar</systemPath>
</dependency>
    <!-- end -->
```

![放置jar包位置](./images/2021-09-16-1.jpg)

### 整合shiro

[参考链接](https://cloud.tencent.com/developer/article/1643122)

### 读取配置文件

#### @Value()

> // 使用方法
>
> // application.properties中配置信息
>
> yichen.age=10
>
> // 类中获取配置信息
>
> @Value("${yichen.age}")
>
> private int age;

#### @Configuration

[参考链接](https://www.cnblogs.com/tian874540961/p/12146467.html)

###  mybatis 中mysql 处理json 类型数据

[参考实现](https://cloud.tencent.com/developer/article/1649021)

#### 实体类定义字段

```
private JSONObject extend;
```

#### 自定义 handler

```java
package client.demo.handler.mybatis;

@MappedTypes(JSONObject.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MySqlJsonHandler extends BaseTypeHandler<JSONObject>{
    /**
     * 设置非空参数
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONObject parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,String.valueOf(parameter.toJSONString()));
    }
    /**
     * 根据列名，获取可以为空的结果
     * @param rs
     * @param columnName
     * @return
     * @throws SQLException
     */
    @Override
    public JSONObject getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String sqlJson = rs.getString(columnName);
        if (null != sqlJson) {
            return JSONObject.parseObject(sqlJson);
        }
        return null;
    }
    /**
     * 根据列索引，获取可以为内控的接口
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public JSONObject getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String sqlJson = rs.getString(columnIndex);
        if (null != sqlJson) {
            return JSONObject.parseObject(sqlJson);
        }
        return null;
    }
    /**
     *
     * @param cs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public JSONObject getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String sqlJson = cs.getNString(columnIndex);
        if (null != sqlJson) {
            return JSONObject.parseObject(sqlJson);
        }
        return null;
    }
}
```

#### application.properties 引入相应配置

```java
# 自定义 handler 所在包
mybatis.type-handlers-package=client.demo.handler.mybatis
```

#### xml中引入自定义类型

```java
<insert id="insertExtend" parameterType="client.demo.model.VisitLog" >
    insert into t_visit_log (host_id, visit_url, status, extend
    )
    values (#{hostId,jdbcType=BIGINT}, #{visitUrl,jdbcType=VARCHAR}, #{status,jdbcType=VARCHAR},
    #{extend,jdbcType=OTHER,typeHandler=client.demo.handler.mybatis.ExtendHandler}
    )
  </insert>
```

### 全局异常拦截器

```java
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ReturnT<String> exceptionHandler(HttpServletRequest request, Exception e){
        log.error("全局异常捕获，异常信息为{}", Arrays.toString(e.getStackTrace()));
        return new ReturnT<>("2",e.getMessage());

    }
}
```

### test 中读取配置文件

1、指定某个文件夹为 test 的资源文件夹

<img src="./images/2021-08-31-1.jpg" alt="设置方式" style="zoom:67%;" />

2、指定读取的配置文件

```java
// 测试类前加注解 
@TestPropertySource("classpath:model.properties")
// 注入所需要的 配置名称
@Value("${loan_order}")
private String loanOrderProperties;
```

### yml指定logging.level

```java
logging:
  config: classpath:conf/log4j2-dev.xml
  level:
    com.onecard.ordertask.dao: debug
```

#### web项目默认存在请求 favicon

##### 关闭 favicon

> // 在application.properties 写入如下内容
>
> spring.mvc.favicon.enabled=false

##### 配置 favicon

[参考步骤](https://blog.csdn.net/VariatioZbw/article/details/108139453)

>1、挑选图片， 图库 =》  https://www.iconfont.cn/
>
>2、将图片转为  .ico     =>  http://favicon.htmlkit.com/favicon/
>
>3、将图片放到  resources >  static 该目录下即可

### logback 记录日志

```xml
<!-- 参考模板 --> 
<?xml version="1.0" encoding="UTF-8"?>
<!-- 日志配置 -->
<!-- 日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出 -->
<!-- scan:当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true -->
<!-- scanPeriod:设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。 -->
<!-- debug:当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。 -->
<configuration scan="false" scanPeriod="10 seconds">

    <contextName>logback</contextName>
    <!-- name的值是变量的名称，value的值时变量定义的值。通过定义的值会被插入到logger上下文中。定义变量后，可以使“${}”来使用变量。 -->
    <property name="log.path" value="log"/>

    <!-- 彩色日志 -->
    <!-- 彩色日志依赖的渲染类 -->
    <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter"/>
    <conversionRule conversionWord="wex"
                    converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter"/>
    <conversionRule conversionWord="wEx"
                    converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter"/>
    <!-- 彩色日志格式 -->
    <property name="CONSOLE_LOG_PATTERN"
              value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>


    <!--输出到控制台-->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <!--此日志appender是为开发使用，只配置最底级别，控制台输出的日志级别是大于或等于此级别的日志信息-->
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>info</level>
        </filter>
        <encoder>
            <Pattern>${CONSOLE_LOG_PATTERN}</Pattern>
            <!-- 设置字符集 -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>


    <!--输出到文件-->

    <!-- 时间滚动输出 level为 DEBUG 日志 -->
    <appender name="DEBUG_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${log.path}/debug.log</file>
        <!--日志文件输出格式-->
        <encoder>
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 日志归档 -->
            <fileNamePattern>${log.path}/debug/debug-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文件保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文件只记录debug级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>debug</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- 时间滚动输出 level为 INFO 日志 -->
    <appender name="INFO_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${log.path}/info.log</file>
        <!--日志文件输出格式-->
        <encoder>
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符 -->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 每天日志归档路径以及格式 -->
            <fileNamePattern>${log.path}/info/info-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>2048MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文件保留天数-->
            <maxHistory>90</maxHistory>
        </rollingPolicy>
        <!-- 此日志文件只记录info级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>info</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- 时间滚动输出 level为 WARN 日志 -->
    <appender name="WARN_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${log.path}/warn.log</file>
        <!--日志文件输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 此处设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.path}/warn/warn-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>1024MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文件保留天数-->
            <maxHistory>365</maxHistory>
        </rollingPolicy>
        <!-- 此日志文件只记录warn级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>warn</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>


    <!-- 时间滚动输出 level为 ERROR 日志 -->
    <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${log.path}/error.log</file>
        <!--日志文件输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 此处设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.path}/error/error-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>1024MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文件保留天数-->
            <maxHistory>365</maxHistory>
        </rollingPolicy>
        <!-- 此日志文件只记录ERROR级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!--
        <logger>用来设置某一个包或者具体的某一个类的日志打印级别、
        以及指定<appender>。<logger>仅有一个name属性，
        一个可选的level和一个可选的addtivity属性。
        name:用来指定受此logger约束的某一个包或者具体的某一个类。
        level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
              还有一个特俗值INHERITED或者同义词NULL，代表强制执行上级的级别。
              如果未设置此属性，那么当前logger将会继承上级的级别。
        addtivity:是否向上级logger传递打印信息。默认是true。
    -->
    <!--<logger name="org.springframework.web" level="info"/>-->
    <!--<logger name="org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor" level="INFO"/>-->
    <!--
        使用mybatis的时候，sql语句是debug下才会打印，而这里我们只配置了info，所以想要查看sql语句的话，有以下两种操作：
        第一种把<root level="info">改成<root level="DEBUG">这样就会打印sql，不过这样日志那边会出现很多其他消息
        第二种就是单独给dao下目录配置debug模式，代码如下，这样配置sql语句会打印，其他还是正常info级别：
     -->


    <!--
        root节点是必选节点，用来指定最基础的日志输出级别，只有一个level属性
        level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
        不能设置为INHERITED或者同义词NULL。默认是DEBUG
        可以包含零个或多个元素，标识这个appender将会添加到这个logger。
    -->

    <!--设置日志输出文件的内容-->
    <root level="info">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="DEBUG_FILE"/>
        <appender-ref ref="INFO_FILE"/>
        <appender-ref ref="WARN_FILE"/>
        <appender-ref ref="ERROR_FILE"/>
    </root>

</configuration>
```



### 使用 log4j2 记录日志

[解决办法](#springboot 将默认的logback改为 slf4j2)

#### 期间问题

##### 按配置文件配置，但是项目重启后日志没归档

[官方文档](https://logging.apache.org/log4j/2.x/manual/appenders.html#RolloverStrategies)

具体描述

> log4j2.xml中配置了对应的日志 策略( OnStartupTriggeringPolicy )，但是在 k8s中重启项目时，发现日志没有归档，仍使用的是同一个日志文件，而不是新生成一个，将旧的日志文件归档

==解决==

> k8s项目的重启是旧的关闭同时新的开启，而 OnStartupTriggeringPolicy  比较的是日志中的时间和jvm启动时间。  如果先将 k8s项目关闭等一会 在启动k8s项目，此时日志会归档。

##### 启动报错  multiple SLF4J bindings

```java
// 具体错误内容
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/D:/maven/repository/ch/qos/logback/logback-classic/1.2.3/logback-classic-1.2.3.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/D:/maven/repository/org/apache/logging/log4j/log4j-slf4j-impl/2.13.3/log4j-slf4j-impl-2.13.3.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [ch.qos.logback.classic.util.ContextSelectorStaticBinder]
Logging system failed to initialize using configuration from 'classpath:log4j2.xml'
java.lang.IllegalStateException: Logback configuration error detected: 
```

###### 解决过程

+ 打开 `pom.xml`  打开依赖图标(disgrams)

![示例](./images/2021-12-24-1.jpg)

+ 搜索冲突 `ctrl+f`，这里是 `logback`

![示例](./images/2021-12-24-2.jpg)

+ 查找源头 

![查找源头](./images/2021-12-24-3.jpg)

+ 最终结构

```java
// 该依赖中有 spring-boot-starter-logging
//  spring-boot-starter-web 存在该依赖可以直接删除。
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
```



### 整合 前端页面

<font color=red size=5px>注意，两种只能一种生效，都存在的话会覆盖其中一种导致出错</font>

#### 遍历目录

==项目运行后target目录下没有jsp文件或者有文件但是无法访问到==

```java
<resources>
			<resource>
				<directory>src/main/java</directory>
				<includes>
					<include>**/*.xml</include>
				</includes>
			</resource>
			<resource>
				<directory>src/main/resources</directory>
				<includes>
					<include>**/*.*</include>
				</includes>
			</resource>
			<resource>
				<directory>src/main/webapp</directory>
				<targetPath>META-INF/resources</targetPath>
				<includes>
					<include>**/*.*</include>
				</includes>
			</resource>
		</resources>
```



#### 访问 jsp 页面

```java
//添加如下依赖

   <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
    </dependency>
//  配置文件中添加信息
// 前端配置  匹配路径
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp
// controller 示例
    @RequestMapping("/login")
    public String login(HttpServletRequest httpServletRequest){
        log.info("请求ip地址为{}",httpServletRequest.getRemoteAddr());
        return "login";
    }
```

<img src="./images/2021-09-10-2.jpg" alt="目录结构" style="zoom:67%;" />

#### 访问 html 页面

```java
springboot 默认资源(html、js、css等)放置位置：

classpath:/META-INF/resources/      classpath:/resources/    classpath:/statis/  classpath:/public/    /

//  1、手动指定资源目录   = 》 直接通过url 访问，不能通过 controller 跳转

spring.resources.static-locations=classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/,classpath:/templates/

// 2、controller 跳转 到 html 页面

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
    
// 配置文件添加如下内容
// 指定静态页面资源 地址  存在默认值  默认值为 1中的4个
// spring.resources.static-locations=
// 指定  controller 返回 string 后的跳转到指定界面的 路径前缀
spring.thymeleaf.prefix=classpath:/static/    // 这里只能这样不能 /static/views
spring.thymeleaf.suffix=.html
// controller 调用示例
@RequestMapping("/hello")
    public String hello(HttpServletRequest httpServletRequest){
        log.info("请求ip地址为{}",httpServletRequest.getRemoteAddr());
        return "views/hello";
    }
```

<img src="./images/2021-09-10-1.jpg" alt="目录结构" style="zoom:67%;" />

### springboot 修改 请求结果

> 具体描述：
>
> 前端请求 localhost:8090/hello，而我后端没有  /hello 对应的 url，正常请求的话会报错 404。我这里想修改请求结果，改为 200 加自定义结果返回

```java
@Order(0)
@WebFilter(value = "/*")
@Component
public class EntranceFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         // 请求放行
        chain.doFilter(request,wrapper);
        try{
                Class clazz=ResponseFacade.class;
                Field field=clazz.getDeclaredField("response");
                field.setAccessible(true);
                Object o = field.get(((ResponseFacade) response));
                //  修改状态，默认情况 放过调用链执行后 response.setStatus 失败
                ((Response)o).setAppCommitted(false);
                ((Response)o).setSuspended(false);
            }
            catch (Exception e){
                logger.error("========> 反射获取response失败");
            }
        // =====>  reset() 方法可以清空缓冲区以及重置状态码 200   但是这里又需要依赖前面的try模块，需要先设置他们
        //  =====>  还需要设置 内容类型，这块也被重置了
        response.reset();
        response.setContentType("text/html;charset=UTF-8");
        response.getOutputStream().write("Don't ask at will".getBytes());
        response.flushBuffer();
    }
}
```

### 服务熔断降级

#### feign和hystrix实现

[参考实现](http://www.javashuo.com/article/p-zysimjgd-r.html)

```java
<dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-hystrix</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
</dependency>
```

> //  添加 fallback 实现类，为接口异常默认值
>
> @FeignClient(name = "mall-user", url = "${wk.url.mall}",fallback = MallHystrix.class)
>
> // 配置文件开启 hystrix
>
> ```java
> feign:
>   hystrix:
>     enabled: true    
> ```
>
> // 方法限流
>
> @HystrixCommand

### 排除本地配置，不上传

```
// 修改 .gitignore ,指定排除本地文件，例如  application-dev.yml
**/application-dev.yml
// 删除本地 git cache  一般都是命中的
git rm -rf --cached src/main/resource/application-dev.yml
// 删除 本地 .gitignore cache，也不上传
git rm -rf --cached .gitignore
```

### swagger

#### pom

```java
<properties>
        <swagger.version>2.9.2</swagger.version>
</properties>
<!--swagger 查询接口定义和调用  调用方式  服务启动地址后加  /swagger-ui.html -->
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger2</artifactId>
        <version>${swagger.version}</version>
    </dependency>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger-ui</artifactId>
        <version>${swagger.version}</version>
    </dependency>
<!-- end -->
```

#### 基本配置

```java
// 启动类上开启 swagger
@EnableSwagger2
// controller上的方法添加
@ApiOperation(value = "测试接口请求数据")
```

#### 问题记录

##### 打开swagger报错 Unable to infer base url

```java
Unable to infer base url. This is common when using dynamic servlet registration or 
when the API is behind an API Gateway. The base url is the root of where all 
the swagger resources are served. For e.g. if the api is available at 
http://example.org/api/v2/api-docs then the base url is http://example.org/api/. 
Please enter the location manually: 
```

```java
启动类中添加启用swagger注解
@EnableSwagger2
```



### 单元测试

#### 构建model对象数据

==如果是微服务或者存在远程调用的情况，开发阶段无法请求其他服务，只能本地构建测试==

##### SqlUtils

```java
/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/30 16:58
 * @describe mysql 数据库相关的工具类
 *   用途：根据 mysql表构建 其他服务的 model 类供 自己进行 单元测试
 *   1、在 navicat 中找到对应表，选择一条记录，右键行前选择 复制 insert 语句
 *   2、将语句放于 下面类的 main 中，运行即可生成对应的 由 fastjson 转换而成的 string
 *   3、将运行的结果放于 单元测试中，通过 JSONObject.parseObject() 生成对象
 */

public class SqlUtils {

    private static Logger logger= LoggerFactory.getLogger(SqlUtils.class);

    /**
     * 根据 insert sql 构造 json格式 数据字符串
     * <font color=red>这里的sql 必然是简单插入.也不存在列取别名</font>
     * @param insertSql insert 类型的数据sql
     * @return json 转换而来的 字符串
     */
    public static String constructMapFromInsertSql(String insertSql){
        String[] a=insertSql.replace("(","|").split("\\|");
        String [] b=a[1].replace(")","|").split("\\|");
        String []c=a[2].replace(")","|").split("\\|");
        String []fields=b[0].replace("`","").split(",");
        String []values=c[0].replace("'","").split(",");
        JSONObject res=new JSONObject();
        for(int i=0;i<fields.length;i++){
            res.put(FormatUtils.lineToHump(fields[i].trim()),values[i].trim());
        }
        // TODO 这里存在一个缺陷， NULL 类型被加了 双引号，需要手动转换，不然无法转回对象
        String r=JSONObject.toJSONString(res);

        //  去掉null 前后的 双引号
        r=r.replace("\"NULL\"","NULL");

        logger.info("转换后的结果{}",r);
        return r;
    }




    public static void main(String[] args) {
        constructMapFromInsertSql("INSERT INTO `wk_db_user`.`wk_customer`(`customerId`, `name`, `certId`, `source`, `birthDay`, `sex`, `tradePwd`, `createTime`, `updatedTime`, `serial_no`, `certIdphoto`, `pinyin`, `address`, `authority`, `timelimit`, `mobile`, `tenant_id`, `proId`, `nationality`) VALUES (1170262913, '杨云鹏', '140481199003136811', 1799, '1990-03-13', 1, '', '2021-08-30 16:59:06', '2021-08-30 16:59:06', NULL, NULL, 'yangyunpeng', NULL, NULL, NULL, '15601292370', 1001, '360sjzs8a518bd623ddbecbb2b2a3bb7f640e30', NULL);\n");

    }

}
```

##### FormatUtils

```java
/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/31 9:05
 * @describe 格式工具类
 */
public class FormatUtils {

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /** 下划线转驼峰 */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /** 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)}) */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /** 驼峰转下划线,效率比上面高 */
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
```

#### mock 示例

> 过程： 由于对象数据长，所以通过读取配置文件来读取对象数据
> 1、将 FormatUtils构造出来的对象的 string从配置文件中读取(
> == @TestPropertySource(value = "classpath:model.properties") 指定配置文件，需要先指定 test 资源文件夹
> ),然后通过fastjson 再次构建对象
> UserCustomerExtendDTO dto=JSONObject.parseObject(userCustomerExtend,UserCustomerExtendDTO.class);
> 2、Mock本地无法调用的对象，然后指定其中我们使用的方法返回指定的返回值
> @Mock UserInfoApiService userInfoApiService;
> Mockito.when(userInfoApiService.custExtend(Mockito.anyString(),Mockito.anyInt())).thenReturn(dto);
> 3、如果有其他类使用我们 mock的类，则通过 @InjectMocks 注入。例 a 使用了 b，b已被mock，a 使用了 @InjectMocks，则a中的b则使用的是我们mock的b。
> <font color=red>一旦使用了该注解，使用 @Autowired 失效,为被 mock 的均为 null，需要注意</font>
> 4、如果单元测试还使用到其他 本地类，则通过  @SpringBootTest(classes = StartApplication.class) 构建

==示例代码，供参考==

```java
package com.onecard.ordertask.test;

import com.alibaba.fastjson.JSONObject;
import com.onecard.order.common.model.dto.UserCustomerDTO;
import com.onecard.order.common.model.dto.UserCustomerExtendDTO;
import com.onecard.ordertask.StartApplication;
import com.onecard.ordertask.dao.firstdatasource.OrderLabeMapper;
import com.onecard.ordertask.dao.firstdatasource.SysconfigMapper;
import com.onecard.ordertask.model.loando.LoanOrderDO;
import com.onecard.ordertask.model.loando.LoanOrderExtendDO;
import com.onecard.ordertask.model.loando.SysconfigDO;
import com.onecard.ordertask.remote.service.UserInfoApiService;
import com.onecard.ordertask.remote.service.UserServiceService;
import com.onecard.ordertask.service.BuildLoanApp561;
import com.onecard.ordertask.service.SysconfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
// 指定启动 类
@SpringBootTest(classes = StartApplication.class)
// 获取test 中的配置
@TestPropertySource(value = "classpath:model.properties")
public class ImportPartsTest {



    @Mock
    UserInfoApiService userInfoApiService;

    @Mock
    UserServiceService userServiceService;

    @Mock
    OrderLabeMapper orderLabeMapper;

    @Mock
    SysconfigMapper sysconfigMapper;

    @Mock
    SysconfigService sysconfigService;


    @InjectMocks
    BuildLoanApp561 app561;

    @Value("${loan_order}")
    private String loanOrderProperties;

    @Value("${loan_order_extend}")
    private String loanOrderExtendProperties;

    @Value("${user_customer_extend}")
    private String userCustomerExtend;

    @Value("${user_customer}")
    private String userCustomer;

    @Value("${shuhe}")
    private String shuhe;

    @Value("${old_and_new}")
    private String oldAndNew;
    @Value("${big_data_appid}")
    private String bigDataAppid;
    @Value("${big_data_appsecret}")
    private String bigDataAppsecret;


    @Test
    public void TestApp561() throws Exception {

        // 构造model 数据对象
        UserCustomerExtendDTO dto=JSONObject.parseObject(userCustomerExtend,UserCustomerExtendDTO.class);
        UserCustomerDTO userCustomerDTO=JSONObject.parseObject(userCustomer,UserCustomerDTO.class);
        SysconfigDO shuheConfig=JSONObject.parseObject(shuhe,SysconfigDO.class);
        SysconfigDO oldConfig=JSONObject.parseObject(oldAndNew,SysconfigDO.class);
        SysconfigDO appIdConfig=JSONObject.parseObject(bigDataAppid,SysconfigDO.class);
        SysconfigDO secretConfig=JSONObject.parseObject(bigDataAppsecret,SysconfigDO.class);
        LoanOrderDO loanOrder= JSONObject.parseObject(loanOrderProperties, LoanOrderDO.class);
        LoanOrderExtendDO loanOrderExtend=JSONObject.parseObject(loanOrderExtendProperties,LoanOrderExtendDO.class);

        // mock 模拟请求  有返回值
        Mockito.when(userServiceService.findById(Mockito.anyLong(),Mockito.anyInt())).thenReturn(userCustomerDTO);
        Mockito.when(userInfoApiService.custExtend(Mockito.anyString(),Mockito.anyInt())).thenReturn(dto);
        Mockito.when(orderLabeMapper.findByAppId(Mockito.anyLong())).thenReturn(null);
        Mockito.when(sysconfigMapper.getByKeyAndSystem("isXyApp","wk")).thenReturn(shuheConfig);
        Mockito.when(sysconfigService.getValueByKeyAndSystem("get_new_or_old_customer","wk")).thenReturn(oldConfig.getOptionvalue());
        Mockito.when(sysconfigService.getValueByKeyAndSystem("reuqst_big_data_appid","wk")).thenReturn(appIdConfig.getOptionvalue());
        Mockito.when(sysconfigService.getValueByKeyAndSystem("reuqst_big_data_appsecret","wk")).thenReturn(secretConfig.getOptionvalue());
		//  mock 请求，无返回值
        Mockito.doNothing().when(app561).setNewAndoldCustomerData(Mockito.any(),Mockito.anyMap());

        //
        Map<String,Object> res=new HashMap<>();
        app561.set39Info(res,loanOrder,null,loanOrderExtend,null);
        System.out.println("处理结果"+res);
    }
}
```

#### SpyBean vs MockBean

[参考文章](https://zhuanlan.zhihu.com/p/258977808#:~:text=SpyBean%20%E5%92%8C%20MockBean%20%E6%98%AF%20spring-boot-test%20%E5%8C%85%E6%89%80%E6%8F%90%E4%BE%9B%E7%9A%84%E4%B8%A4%E4%B8%AA%E6%B3%A8%E8%A7%A3%EF%BC%8C%E7%94%A8%E4%BA%8ESpy%E6%88%96Mock%20Spring%E5%AE%B9%E5%99%A8%E6%89%80%E7%AE%A1%E7%90%86%E7%9A%84%E5%AE%9E%E4%BE%8B%EF%BC%9B%20%E4%BD%BF%E7%94%A8,SpyBean%20%E6%88%96%E8%80%85%20Spy%20%E6%97%B6%EF%BC%8C%E5%BD%93%E9%9C%80%E8%A6%81%E5%AF%B9%E6%9F%90%E4%B8%AA%E6%96%B9%E6%B3%95%E8%BF%9B%E8%A1%8C%E6%89%93%E6%A1%A9%E6%97%B6%EF%BC%8C%E9%9C%80%E8%A6%81%E6%B3%A8%E6%84%8F%E4%B8%80%E4%BA%9B%E4%BD%BF%E7%94%A8%E9%99%90%E5%88%B6%EF%BC%9B%20%E4%B8%BA%E6%B5%8B%E8%AF%95%E4%B8%BB%E4%BD%93%E7%B1%BB%E9%83%A8%E5%88%86%E6%89%93%E6%A1%A9%E8%80%83%E8%99%91%E4%BD%BF%E7%94%A8%20SpyBean%2C%E4%B8%BA%E5%A4%96%E9%83%A8%E4%BE%9D%E8%B5%96%E6%89%93%E6%A1%A9%EF%BC%8C%E8%80%83%E8%99%91%E4%BD%BF%E7%94%A8%20MockBean%20%E3%80%82)

```java
// spybean 是复制一个真实的bean，然后通过 以下方式指定其中某个方法的实现，其他方法走原有逻辑
Mockito.doReturn(返回值).when(spyBean对象)
    .方法名称(入参);
// Mockbean 是mock掉原来的bean，通过一下方法 指定其中某个方法的实现，其他方法默认不执行。
Mockito.when(mockBean对象.方法名称(入参)).thenReturn(返回值);

// 测试类常用添加注解
@RunWith(SpringRunner.class)
@SpringBootTest
```





#### 思考

+ a使用b，b使用c，c对象mock了，b对象使用 injectMock，那a怎么办。  ==》 理论，测a到b的时候应该已经测试了b到c

==》 解决方式，通过 @SpyBean 和 @MockBean 即可实现自动注入，且能调用我们自定义的方法

+ 指定使用某个配置文件

  > //  默认还是会使用类路径下的 application.properties 或者 application.yml，且不区分是测试包下还是代码包下
  >
  > @TestPropertySource(value = "classpath:application-test.properties")

+ 访问数据库

  > 默认情况下，即单纯使用  @SpringBootTest 注解就能访问数据库。前提是 jdbc配置的没有问题

+ @Autowired 注入报空指针

  > 类上添加如下注解  测试运行于Spring测试环境  <font color=red>但是我在本地测试没加这个注解也能直接跑。。</font>
  >
  > @RunWith(SpringJUnit4ClassRunner.class)

+ 

### 问题记录

#### unable to create new native thread

##### 校验是否超出用户最大线程限制

> // 用户最大进程
> ulimit -a 
> //  正在运行的线程
>
> ps -elfT | wc -l

##### 一个进程是否超出最大线程

> // 对应进程有多少线程
>
> ps -p <PROCESS_PID> -lfT | wc -l
>
> // 系统支持最大线程数量
> cat /proc/sys/kernel/threads-max
>
> // 系统全局 pid 限制，进程和线程都要 pid
>
> cat /proc/sys/kernel/pid_max

#### package org.apache.commons.io does not exist error

[参考解答](https://stackoverflow.com/questions/21673618/package-org-apache-commons-io-does-not-exist-error)

[FileUtils手册](https://zetcode.com/java/fileutils/)

> 解决办法  将 commons-io 的版本号从2.6 改成 2.5
>
> 好像是maven指向不明，jar包中依赖版本是2.2， dependencyManagement 中指定的2.6，如果当前项目中没指定版本 jenkins compiler  failed ,如果指定版本号2.6.0，那编译可以成功。

#### Assert.isTrue(ZLjava/util/function/Supplier;)V

具体内容

```java
java.lang.NoSuchMethodError: org.springframework.util.Assert.isTrue(ZLjava/util/function/Supplier;)V
	at org.springframework.boot.context.properties.bind.Bindable.withExistingValue(Bindable.java:162) ~[spring-boot-2.0.6.RELEASE.jar:2.0.6.RELEASE]
	at org.springframework.boot.context.properties.bind.Bindable.ofInstance(Bindable.java:192) ~[spring-boot-2.0.6.RELEASE.jar:2.0.6.RELEASE]
```

##### 解决办法

[常规解决](https://www.cnblogs.com/hjieone/articles/9854597.html)

==特殊情况，修改项目的jre目录==

![修改配置图示](./images/2021-10-19-1.jpg)

##### 特殊情况

==引入了重复jar包，可能包不一样==

<img src="./images/2022-06-23-1.jpg" alt="解决办法" style="zoom:80%;" />

#### @Builder 导致 fastjson转对象出错

==报错内容==

```java
"com.alibaba.fastjson.JSONException","commonElementCount":0,"message":"default constructor not found. class 
```

##### 解决办法

> // Builder  在添加以下两个注解
>
> @NoArgsConstructor
> @AllArgsConstructor

#### 读取配置文件中文显示乱码

[参考解决方案](https://blog.csdn.net/yuyanchuan/article/details/105838292)

> // 在读取的配置文件中添加如下内容
>
> spring.http.encoding.enabled=true

#### redis 改变后其他地方未查询到

==springboot 默认使用的databases 是0==

> 排查连个地方使用的redis 地址是否是同一个，以及使用的数据库是否是同一个。redis不同数据库之间是隔离的。  

## springcloud



## mybatis generator

### 源码分析

#### 问题

##### 源码打包报错  MavenReportException: Error while generating Javadoc

> // 跳过 doc
>
> mvn clean install -Dmaven.test.skip=true -X -Dmaven.javadoc.skip=true

### 详细版 generatorConfig.xml

>```xml-dtd
><?xml version="1.0" encoding="UTF-8"?>
><!DOCTYPE generatorConfiguration
>        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
>        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
><!-- 配置生成器 -->
><generatorConfiguration>
>    <!-- 可以用于加载配置项或者配置文件，在整个配置文件中就可以使用${propertyKey}的方式来引用配置项
>    resource：配置资源加载地址，使用resource，MBG从classpath开始找，比如com/myproject/generatorConfig.properties 
>    url：配置资源加载地质，使用URL的方式，比如file:///C:/myfolder/generatorConfig.properties.
>    注意，两个属性只能选址一个;
>    
>    另外，如果使用了mybatis-generator-maven-plugin，那么在pom.xml中定义的properties都可以直接在generatorConfig.xml中使用
>    <properties resource="" url="" />
>    -->
>
>    <!-- 在MBG工作的时候，需要额外加载的依赖包
>    location属性指明加载jar/zip包的全路径
>    <classPathEntry location="/Program Files/IBM/SQLLIB/java/db2java.zip" />
>    -->
>
>    <!-- 
>    context:生成一组对象的环境 
>    id:必选，上下文id，用于在生成错误时提示
>    defaultModelType:指定生成对象的样式
>    1，conditional：类似hierarchical；
>    2，flat：所有内容（主键，blob）等全部生成在一个对象中；
>    3，hierarchical：主键生成一个XXKey对象(key class)，Blob等单独生成一个对象，其他简单属性在一个对象中(record class)
>    targetRuntime:
>    1，MyBatis3：默认的值，生成基于MyBatis3.x以上版本的内容，包括XXXBySample；
>    2，MyBatis3Simple：类似MyBatis3，只是不生成XXXBySample；
>    introspectedColumnImpl：类全限定名，用于扩展MBG
>    -->
>    <context id="mysql" defaultModelType="hierarchical" targetRuntime="MyBatis3Simple" >
>
>        <!-- 自动识别数据库关键字，默认false，如果设置为true，根据SqlReservedWords中定义的关键字列表；
>        一般保留默认值，遇到数据库关键字（Java关键字），使用columnOverride覆盖
>        -->
>        <property name="autoDelimitKeywords" value="false"/>
>        <!-- 生成的Java文件的编码 -->
>        <property name="javaFileEncoding" value="UTF-8"/>
>        <!-- 格式化java代码 -->
>        <property name="javaFormatter" value="org.mybatis.generator.api.dom.DefaultJavaFormatter"/>
>        <!-- 格式化XML代码 -->
>        <property name="xmlFormatter" value="org.mybatis.generator.api.dom.DefaultXmlFormatter"/>
>
>        <!-- beginningDelimiter和endingDelimiter：指明数据库的用于标记数据库对象名的符号，比如ORACLE就是双引号，MYSQL默认是`反引号； -->
>        <property name="beginningDelimiter" value="`"/>
>        <property name="endingDelimiter" value="`"/>
>
>        <!-- 必须要有的，使用这个配置链接数据库
>        -->
>        <jdbcConnection driverClass="com.mysql.jdbc.Driver" connectionURL="jdbc:mysql:///pss" userId="root" password="admin">
>            <!-- 这里面可以设置property属性，每一个property属性都设置到配置的Driver上 -->
>        </jdbcConnection>
>
>        <!-- java类型处理器 
>        用于处理DB中的类型到Java中的类型，默认使用JavaTypeResolverDefaultImpl；
>        注意一点，默认会先尝试使用Integer，Long，Short等来对应DECIMAL和 NUMERIC数据类型； 
>        -->
>        <javaTypeResolver type="org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl">
>            <!-- 
>            true：使用BigDecimal对应DECIMAL和 NUMERIC数据类型
>            false：默认,
>            scale>0;length>18：使用BigDecimal;
>            scale=0;length[10,18]：使用Long；
>            scale=0;length[5,9]：使用Integer；
>            scale=0;length<5：使用Short；
>            -->
>            <property name="forceBigDecimals" value="false"/>
>        </javaTypeResolver>
>
>        <!-- java模型创建器，是必须要的元素
>        负责：1，key类（见context的defaultModelType）；2，java类；3，查询类
>        targetPackage：生成的类要放的包，真实的包受enableSubPackages属性控制；
>        targetProject：目标项目，指定一个存在的目录下，生成的内容会放到指定目录中，如果目录不存在，MBG不会自动建目录
>        -->
>        <javaModelGenerator targetPackage="com._520it.mybatis.domain" targetProject="src/main/java">
>            <!-- for MyBatis3/MyBatis3Simple
>            自动为每一个生成的类创建一个构造方法，构造方法包含了所有的field；而不是使用setter；
>            -->
>            <property name="constructorBased" value="false"/>
>
>            <!-- 在targetPackage的基础上，根据数据库的schema再生成一层package，最终生成的类放在这个package下，默认为false -->
>            <property name="enableSubPackages" value="true"/>
>
>            <!-- for MyBatis3 / MyBatis3Simple
>            是否创建一个不可变的类，如果为true，
>            那么MBG会创建一个没有setter方法的类，取而代之的是类似constructorBased的类
>            -->
>            <property name="immutable" value="false"/>
>
>            <!-- 设置一个根对象，
>            如果设置了这个根对象，那么生成的keyClass或者recordClass会继承这个类；在Table的rootClass属性中可以覆盖该选项
>            注意：如果在key class或者record class中有root class相同的属性，MBG就不会重新生成这些属性了，包括：
>            1，属性名相同，类型相同，有相同的getter/setter方法；
>            -->
>            <property name="rootClass" value="com._520it.mybatis.domain.BaseDomain"/>
>
>            <!-- 设置是否在getter方法中，对String类型字段调用trim()方法 -->
>            <property name="trimStrings" value="true"/>
>        </javaModelGenerator>
>
>        <!-- 生成SQL map的XML文件生成器，
>        注意，在Mybatis3之后，我们可以使用mapper.xml文件+Mapper接口（或者不用mapper接口），
>        或者只使用Mapper接口+Annotation，所以，如果 javaClientGenerator配置中配置了需要生成XML的话，这个元素就必须配置
>        targetPackage/targetProject:同javaModelGenerator
>        -->
>        <sqlMapGenerator targetPackage="com._520it.mybatis.mapper" targetProject="src/main/resources">
>            <!-- 在targetPackage的基础上，根据数据库的schema再生成一层package，最终生成的类放在这个package下，默认为false -->
>            <property name="enableSubPackages" value="true"/>
>        </sqlMapGenerator>
>
>        <!-- 对于mybatis来说，即生成Mapper接口，注意，如果没有配置该元素，那么默认不会生成Mapper接口 
>        targetPackage/targetProject:同javaModelGenerator
>        type：选择怎么生成mapper接口（在MyBatis3/MyBatis3Simple下）：
>        1，ANNOTATEDMAPPER：会生成使用Mapper接口+Annotation的方式创建（SQL生成在annotation中），不会生成对应的XML；
>        2，MIXEDMAPPER：使用混合配置，会生成Mapper接口，并适当添加合适的Annotation，但是XML会生成在XML中；
>        3，XMLMAPPER：会生成Mapper接口，接口完全依赖XML；
>        注意，如果context是MyBatis3Simple：只支持ANNOTATEDMAPPER和XMLMAPPER
>        -->
>        <javaClientGenerator targetPackage="com._520it.mybatis.mapper" type="ANNOTATEDMAPPER" targetProject="src/main/java">
>            <!-- 在targetPackage的基础上，根据数据库的schema再生成一层package，最终生成的类放在这个package下，默认为false -->
>            <property name="enableSubPackages" value="true"/>
>
>            <!-- 可以为所有生成的接口添加一个父接口，但是MBG只负责生成，不负责检查
>            <property name="rootInterface" value=""/>
>            -->
>        </javaClientGenerator>
>
>        <!-- 选择一个table来生成相关文件，可以有一个或多个table，必须要有table元素
>        选择的table会生成一下文件：
>        1，SQL map文件
>        2，生成一个主键类；
>        3，除了BLOB和主键的其他字段的类；
>        4，包含BLOB的类；
>        5，一个用户生成动态查询的条件类（selectByExample, deleteByExample），可选；
>        6，Mapper接口（可选）
>        
>        tableName（必要）：要生成对象的表名；
>        注意：大小写敏感问题。正常情况下，MBG会自动的去识别数据库标识符的大小写敏感度，在一般情况下，MBG会
>        根据设置的schema，catalog或tablename去查询数据表，按照下面的流程：
>        1，如果schema，catalog或tablename中有空格，那么设置的是什么格式，就精确的使用指定的大小写格式去查询；
>        2，否则，如果数据库的标识符使用大写的，那么MBG自动把表名变成大写再查找；
>        3，否则，如果数据库的标识符使用小写的，那么MBG自动把表名变成小写再查找；
>        4，否则，使用指定的大小写格式查询；
>        另外的，如果在创建表的时候，使用的""把数据库对象规定大小写，就算数据库标识符是使用的大写，在这种情况下也会使用给定的大小写来创建表名；
>        这个时候，请设置delimitIdentifiers="true"即可保留大小写格式；
>        
>        可选：
>        1，schema：数据库的schema；
>        2，catalog：数据库的catalog；
>        3，alias：为数据表设置的别名，如果设置了alias，那么生成的所有的SELECT SQL语句中，列名会变成：alias_actualColumnName
>        4，domainObjectName：生成的domain类的名字，如果不设置，直接使用表名作为domain类的名字；可以设置为somepck.domainName，那么会自动把domainName类再放到somepck包里面；
>        5，enableInsert（默认true）：指定是否生成insert语句；
>        6，enableSelectByPrimaryKey（默认true）：指定是否生成按照主键查询对象的语句（就是getById或get）；
>        7，enableSelectByExample（默认true）：MyBatis3Simple为false，指定是否生成动态查询语句；
>        8，enableUpdateByPrimaryKey（默认true）：指定是否生成按照主键修改对象的语句（即update)；
>        9，enableDeleteByPrimaryKey（默认true）：指定是否生成按照主键删除对象的语句（即delete）；
>        10，enableDeleteByExample（默认true）：MyBatis3Simple为false，指定是否生成动态删除语句；
>        11，enableCountByExample（默认true）：MyBatis3Simple为false，指定是否生成动态查询总条数语句（用于分页的总条数查询）；
>        12，enableUpdateByExample（默认true）：MyBatis3Simple为false，指定是否生成动态修改语句（只修改对象中不为空的属性）；
>        13，modelType：参考context元素的defaultModelType，相当于覆盖；
>        14，delimitIdentifiers：参考tableName的解释，注意，默认的delimitIdentifiers是双引号，如果类似MYSQL这样的数据库，使用的是`（反引号，那么还需要设置context的beginningDelimiter和endingDelimiter属性）
>        15，delimitAllColumns：设置是否所有生成的SQL中的列名都使用标识符引起来。默认为false，delimitIdentifiers参考context的属性
>        
>        注意，table里面很多参数都是对javaModelGenerator，context等元素的默认属性的一个复写；
>        -->
>        <table tableName="userinfo" >
>
>            <!-- 参考 javaModelGenerator 的 constructorBased属性-->
>            <property name="constructorBased" value="false"/>
>
>            <!-- 默认为false，如果设置为true，在生成的SQL中，table名字不会加上catalog或schema； -->
>            <property name="ignoreQualifiersAtRuntime" value="false"/>
>
>            <!-- 参考 javaModelGenerator 的 immutable 属性 -->
>            <property name="immutable" value="false"/>
>
>            <!-- 指定是否只生成domain类，如果设置为true，只生成domain类，如果还配置了sqlMapGenerator，那么在mapper XML文件中，只生成resultMap元素 -->
>            <property name="modelOnly" value="false"/>
>
>            <!-- 参考 javaModelGenerator 的 rootClass 属性 
>            <property name="rootClass" value=""/>
>            -->
>
>            <!-- 参考javaClientGenerator 的 rootInterface 属性
>            <property name="rootInterface" value=""/>
>            -->
>
>            <!-- 如果设置了runtimeCatalog，那么在生成的SQL中，使用该指定的catalog，而不是table元素上的catalog 
>            <property name="runtimeCatalog" value=""/>
>            -->
>
>            <!-- 如果设置了runtimeSchema，那么在生成的SQL中，使用该指定的schema，而不是table元素上的schema 
>            <property name="runtimeSchema" value=""/>
>            -->
>
>            <!-- 如果设置了runtimeTableName，那么在生成的SQL中，使用该指定的tablename，而不是table元素上的tablename 
>            <property name="runtimeTableName" value=""/>
>            -->
>
>            <!-- 注意，该属性只针对MyBatis3Simple有用；
>            如果选择的runtime是MyBatis3Simple，那么会生成一个SelectAll方法，如果指定了selectAllOrderByClause，那么会在该SQL中添加指定的这个order条件；
>            -->
>            <property name="selectAllOrderByClause" value="age desc,username asc"/>
>
>            <!-- 如果设置为true，生成的model类会直接使用column本身的名字，而不会再使用驼峰命名方法，比如BORN_DATE，生成的属性名字就是BORN_DATE,而不会是bornDate -->
>            <property name="useActualColumnNames" value="false"/>
>
>            <!-- generatedKey用于生成生成主键的方法，
>            如果设置了该元素，MBG会在生成的<insert>元素中生成一条正确的<selectKey>元素，该元素可选
>            column:主键的列名；
>            sqlStatement：要生成的selectKey语句，有以下可选项：
>            Cloudscape:相当于selectKey的SQL为： VALUES IDENTITY_VAL_LOCAL()
>            DB2 :相当于selectKey的SQL为： VALUES IDENTITY_VAL_LOCAL()
>            DB2_MF :相当于selectKey的SQL为：SELECT IDENTITY_VAL_LOCAL() FROM SYSIBM.SYSDUMMY1
>            Derby :相当于selectKey的SQL为：VALUES IDENTITY_VAL_LOCAL()
>            HSQLDB :相当于selectKey的SQL为：CALL IDENTITY()
>            Informix :相当于selectKey的SQL为：select dbinfo('sqlca.sqlerrd1') from systables where tabid=1
>            MySql :相当于selectKey的SQL为：SELECT LAST_INSERT_ID()
>            SqlServer :相当于selectKey的SQL为：SELECT SCOPE_IDENTITY()
>            SYBASE :相当于selectKey的SQL为：SELECT @@IDENTITY
>            JDBC :相当于在生成的insert元素上添加useGeneratedKeys="true"和keyProperty属性
>            <generatedKey column="" sqlStatement=""/>
>            -->
>
>            <!-- 
>            该元素会在根据表中列名计算对象属性名之前先重命名列名，非常适合用于表中的列都有公用的前缀字符串的时候，
>            比如列名为：CUST_ID,CUST_NAME,CUST_EMAIL,CUST_ADDRESS等；
>            那么就可以设置searchString为"^CUST_"，并使用空白替换，那么生成的Customer对象中的属性名称就不是
>            custId,custName等，而是先被替换为ID,NAME,EMAIL,然后变成属性：id，name，email；
>            
>            注意，MBG是使用java.util.regex.Matcher.replaceAll来替换searchString和replaceString的，
>            如果使用了columnOverride元素，该属性无效；
>            
>            <columnRenamingRule searchString="" replaceString=""/>
>            -->
>
>            <!-- 用来修改表中某个列的属性，MBG会使用修改后的列来生成domain的属性；
>            column:要重新设置的列名；
>            注意，一个table元素中可以有多个columnOverride元素哈~
>            -->
>            <columnOverride column="username">
>                <!-- 使用property属性来指定列要生成的属性名称 -->
>                <property name="property" value="userName"/>
>
>                <!-- javaType用于指定生成的domain的属性类型，使用类型的全限定名
>                <property name="javaType" value=""/>
>                -->
>
>                <!-- jdbcType用于指定该列的JDBC类型 
>                <property name="jdbcType" value=""/>
>                -->
>
>                <!-- typeHandler 用于指定该列使用到的TypeHandler，如果要指定，配置类型处理器的全限定名
>                注意，mybatis中，不会生成到mybatis-config.xml中的typeHandler
>                只会生成类似：where id = #{id,jdbcType=BIGINT,typeHandler=com._520it.mybatis.MyTypeHandler}的参数描述
>                <property name="jdbcType" value=""/>
>                -->
>
>                <!-- 参考table元素的delimitAllColumns配置，默认为false
>                <property name="delimitedColumnName" value=""/>
>                -->
>            </columnOverride>
>
>            <!-- ignoreColumn设置一个MGB忽略的列，如果设置了改列，那么在生成的domain中，生成的SQL中，都不会有该列出现 
>            column:指定要忽略的列的名字；
>            delimitedColumnName：参考table元素的delimitAllColumns配置，默认为false
>            
>            注意，一个table元素中可以有多个ignoreColumn元素
>            <ignoreColumn column="deptId" delimitedColumnName=""/>
>            -->
>        </table>
>    </context>
></generatorConfiguration>
>```



### maven 运行 mybatis generator

> 可以直接通过命令行 mvn mybatis-generator:generate 或者通过idea 的可视化界面

![示例](./images/2021-12-03-1.jpg)





### varchar(1) 自动构造 变成 byte类型

#### 说明

**查询构造**

```java
VisitLogExample example=new VisitLogExample();
        example.createCriteria().andHostIdEqualTo(hostId)
                .andStatusEqualTo("N").andValidEqualTo("Y");
```

**查看mysql日志**

```java
select  host_id, visit_url, status  from t_visit_log  
    WHERE (  host_id = 15 and status = 78 and valid = 89 )
```

<font color=red>这里可以看到，他把原来的一个字节的字符串变成了数字

#### 解决

```java
// 新建类，指定 tinyint 转换类型
public class MyResolver extends JavaTypeResolverDefaultImpl {
    public MyResolver(){
        super();
        typeMap.put(-6, new JavaTypeResolverDefaultImpl.JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Integer.class.getName()))); // 当类型为TINYINT时，则生成的Java类型为Integer
    }
}
// 在 GeneratorConfig.xml 中  <javaModelGenerator> 标签上添加 解析标签
<javaTypeResolver type="client.config.MyResolver">
            <!--
            true：使用BigDecimal对应DECIMAL和 NUMERIC数据类型
            false：默认,
               scale>0;length>18：使用BigDecimal;
               scale=0;length[10,18]：使用Long；
               scale=0;length[5,9]：使用Integer；
               scale=0;length<5：使用Short；
             -->
            <property name="forceBigDecimals" value="true"/>
</javaTypeResolver>
```

### 提示无法获得主键

#### 具体内容

```
Cannot obtain primary key information from the database, generated objects may be incomplete
```

#### 解决

[参考解决办法](https://blog.csdn.net/babybabyup/article/details/84099469)

```java
// jdbc url中添加如下内容
nullCatalogMeansCurrent=true
// 示例
jdbc:mysql://localhost:3307/dev_clearing?useUnicode=true&amp;characterEncoding=UTF-8&amp;zeroDateTimeBehavior=convertToNull&amp;serverTimezone=GMT&amp;nullCatalogMeansCurrent=true
```





## 日志级别(log level)

### 分类

+ ERROR。一些糟糕的东西发生了必须被处理。例如：数据库不可用
+ WARN。过程可以继续，但是要格外小心。例如数据查询不到走了缓存，程序启用了开发模式而不是生成模式
+ INFO。重要的业务流程已经完成。可以根据里面记录的信息快速定位程序在干什么
+ DEBUG。开发人员的东西
+ TRACE。非常详细的东西，为开发人员所设计的。在生产环境中部署后，您可能会在短时间内保留跟踪消息，但将这些日志语句视为临时的，最终应该或可能会关闭。例如用户敏感信息。它同DEBUG相比，区别在于会删除记录的数据。

### logback 日志请求级别

TRACE<DEBUG<INFO<WARN<ERROR

==日志请求的级别高于或等于其记录器的有效级别，则称该日志请求已启用。==

### log4j 日志级别

OFF<FATAL<ERROR<WARN<INFO<DEBUG<TRACE<ALL

## 权限控制

### SecurityManager



### permission

#### policy

##### 默认位置

==它是由安全属性文件规定的==

```java
//     D:\idea\openjdk-2\openjdk-1.8.0.292\jre\lib\security\java.security
policy.url.1=file:${java.home}/lib/security/java.policy
policy.url.2=file:${user.home}/.java.policy
```



|        策略类型         |                             位置                             |
| :---------------------: | :----------------------------------------------------------: |
| 系统策略(system policy) | java.home/lib/security/java.policy  (Solaris/Linux) java.home\lib\security\java.policy  (Windows) |
|  用户策略(user policy)  | user.home/.java.policy  (Solaris/Linux) user.home\\.java.policy  (Windows) |

[参考文章](https://docs.oracle.com/javase/7/docs/technotes/guides/security/PolicyFiles.html)

> //  本地两个位置
>
> C:\Users\E480\.java.policy
>
> D:\idea\openjdk-2\openjdk-1.8.0.292\jre\lib\security\java.policy

##### 运行时指定

> // 这种情况下会和默认路径下的policy一起生效。   someURL 为policy的路径
>
> java -Djava.security.manager -Djava.security.policy=someURL SomeApp
>
> // <font color=red>-Djava.security.policy 后使用两个==，则 安全属性文件内的policy失效</font>
>
> java -Djava.security.manager -Djava.security.policy==someURL SomeApp

##### 替换 Policy实现类

> ==//默认实现类在安全属性文件内指定,必须为完全限定名称，且自定义类需要继承Policy，并实现必要方法==
>
> policy.provider=sun.security.provider.PolicyFile

##### 整体格式

```java
// signedby:keystore 中保存的证书别名，默认为 any signer  
// principal  默认为any principals
// codeBase 代表源代码位置，默认 any code   
grant signedBy "signer_names", codeBase "URL",
        principal principal_class_name "principal_name",
        principal principal_class_name "principal_name",
        ... {

      permission permission_class_name "target_name", "action", 
          signedBy "signer_names";
      permission permission_class_name "target_name", "action", 
          signedBy "signer_names";
      ...
  };
```

==codeBase默认使用斜杠作为文件分隔（windows中也是一样的）。==

> ```
> grant codeBase "file:/C:/somepath/api/" {
>         ...
>     };
> ```

## 进制转换

```java
int a = 0b11;   //声明二进制变量
int b = 011;    //声明八进制变量
int c = 11;     //声明十进制变量
int d = 0x11;   //声明十六进制变量

十进制转二进制：Integer.toBinaryString(i);
十进制转八进制：Integer.toOctalString(i);
十进制转十六进制：Integer.toHexString(i);
```



## 运算符优先级

| 优先级 | 运算符                                           | 结合性   |
| ------ | ------------------------------------------------ | -------- |
| 1      | ()、[]、{}                                       | 从左向右 |
| 2      | !、+、-、~、++、--                               | 从右向左 |
| 3      | *、/、%                                          | 从左向右 |
| 4      | +、-                                             | 从左向右 |
| 5      | «、»、>>>                                        | 从左向右 |
| 6      | <、<=、>、>=、instanceof                         | 从左向右 |
| 7      | ==、!=                                           | 从左向右 |
| 8      | &                                                | 从左向右 |
| 9      | ^                                                | 从左向右 |
| 10     | \|                                               | 从左向右 |
| 11     | &&                                               | 从左向右 |
| 12     | \|\|                                             | 从左向右 |
| 13     | ?  :                                             | 从右向左 |
| 14     | =、+=、-=、*=、/=、&=、\|=、^=、~=、«=、»=、>>>= | 从右向左 |



## 开发问题记录

### 空指针报错行号为空或者}或者注释行

[参考办法](https://stackoverflow.com/questions/5987463/stack-trace-shows-exception-at-wrong-position)

> 出现场景：
>
> 将原先test分支删除了。从master分支拉取新的test分支，上传。jenkins上打包能成功，docker运行能成功，报错行号有问题
>
> 解决办法：
>
> 删除分支后强制提交，删除本地的target文件

### springboot 无法进入controller 中的方法

==查看springboot 启动类和controller所在文件夹的关系，理论上应该是同级，且在同一个父文件夹下。==

![示例](./images/2021-06-17-1.jpg)

#### 如果不符合上面的要求，则可以指定springboot 扫描指定目录。

> // 指定具体的controller 存放目录
>
> @ComponentScan("com.yichen.cook.food.controller")

### mybatis dao 接口和 mapper.xml 无法匹配

错误内容如下

```
org.apache.ibatis.binding.BindingException: Invalid bound statement (not found):
```

解决方法有两种：

1、通过  `@MapperScan("com.yichen.useall.dao")` 指定  dao 接口位置，通过`mybatis.mapper-locations=classpath:com/yichen/useall/dao/*.xml` 指定对应的  xml 文件存放位置

2、通过  `@MapperScan("com.yichen.useall.dao")` 指定  dao 接口位置，同时创建对应的xml文件，<font color=red>这里对xml的放置位置以及文件名称有要求：文件位置需要同dao接口位置一致，只不过是放置在类路径下，文件名同dao接口一致，不过文件后缀由dao  的  `.java` 改为  `.xml`</font>

<font color=green>`@MapperScan("com.yichen.useall.dao")` 指定到目录，不要加`*`</font>

#### 排查问题

<font color=red>运行后去target 中查看  dao和mapper是否在一起，如果不在则删除target，重新生成</font>

### spring cloud gateway 报错   chunkedTransfer

#### 报错具体内容

```
java.lang.NoSuchMethodError: reactor.netty.http.client.HttpClient.chunkedTransfer(Z)Lreactor/netty/http/client/HttpClient;
```

#### 解决

> 由于 springboot 和 springcloud 版本不一致，本机均设为了  2.2.2.RELEASE

###  A bean with that name has already been defined in class path resource and overriding is disabled.

#### 详情

```
The bean 'dataSource', defined in BeanDefinition defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class], could not be registered. A bean with that name has already been defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class] and overriding is disabled.

```

#### 解决

springboot 和  springcloud  版本不一致

### mvn install 错误 Failed to execute goal org.springframework.boot:spring-boot-maven-plugin

创建的springboot项目如果把启动类删除了，则把`build>plugins`下的`spring-boot-maven-plugin`删除。

### 返回给前端的时间和数据库中时间不一样

[参考解决办法](https://blog.csdn.net/weixin_42241455/article/details/123445943?spm=1001.2014.3001.5501)

> 看数据库时区，jdbc连接时区，jackson全局配置

## mybatis 代码自动生成

### 步骤

#### 方法一  调用jar包

```java
// 下载 mybatis-generator-core-1.3.5.jar 和 mysql-connector-java-8.0.13.jar
下载方式可以通过 maven 依赖下载
// 编写 generatorConfig.xml 如下为范例
// 填写规则 可以查看官方文档   
    http://mybatis.org/generator/configreference/table.html

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd" >
<generatorConfiguration>
    <!--数据库的jdbc驱动的jar包地址  可以是相对路劲，相对于 mybatis-generator-core 的位置-->
    <classPathEntry location="./mysql-connector-java-8.0.13.jar"/>
    <context id="tt">
        <!--url 中的后缀是解决 Cannot obtain primary key information from the database, generated objects may be incomplete 问题-->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://127.0.0.1:3308/cook_food?nullCatalogMeansCurrent=true" userId="root"
                        password="123">
        </jdbcConnection>
        <javaModelGenerator targetPackage="com.yichen.cook.food.model"
                            targetProject="D:/personal/learn note/project/test-demo/mybatis-generator/src/main/java">
            <property name="enableSubPackages" value="false"/>
            <property name="trimStrings" value="true"/>
        </javaModelGenerator>
        <sqlMapGenerator targetPackage="com.yichen.cook.food.mapper"
                         targetProject="D:/personal/learn note/project/test-demo/mybatis-generator/src/main/java">
            <property name="enableSubPackages" value="false"/>
        </sqlMapGenerator>
        <javaClientGenerator targetPackage="com.yichen.cook.food.mapper"
                             targetProject="D:/personal/learn note/project/test-demo/mybatis-generator/src/main/java"
                             type="XMLMAPPER">
            <property name="enableSubPackages" value="false"/>
        </javaClientGenerator>
        <table tableName="t_food_cook_steps" domainObjectName="foodCookSteps" >
            <!--设置自增主键-->
            <generatedKey column="id" sqlStatement="Mysql"></generatedKey>
            <!--用于配置不需要手动插入，即自动生成的，如修改时间-->
            <ignoreColumn column="create_time"></ignoreColumn>
        </table>
    </context>

</generatorConfiguration>
// 创建  mapper 和model 的存放目录

//运行jar包     
java -jar mybatis-generator-core-1.3.5.jar -configfile ..\generatorConfig.xml -overwrite
```

#### 方法二 java程序执行

```java
// 导入maven 依赖
<dependency>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-core</artifactId>
    <version>1.3.2</version>
</dependency>
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.3.2</version>
    <configuration>
    <!-- mybatis用于生成代码的配置文件 -->
    <configurationFile>src/main/resources/generatorConfig.xml</configurationFile>
    <verbose>true</verbose>
    <overwrite>true</overwrite>
    </configuration>
</plugin>
// 编写构造规则文件   generatorConfig.xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd" >
<generatorConfiguration>
    <!--数据库的jdbc驱动的jar包地址  写绝对路径-->
    <classPathEntry location="D:\personal\learn note\project\test-demo\mybatis-generator\src\main\resources\lib\mysql-connector-java-8.0.13.jar"/>
    <context id="tt">
        <!--url 中的后缀是解决 Cannot obtain primary key information from the database, generated objects may be incomplete 问题-->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://127.0.0.1:3308/cook_food?nullCatalogMeansCurrent=true" userId="root"
                        password="123">
        </jdbcConnection>
        <javaModelGenerator targetPackage="com.yichen.cook.food.model"
                            targetProject="D:/personal/learn note/project/test-demo/mybatis-generator/src/main/java">
            <property name="enableSubPackages" value="false"/>
            <property name="trimStrings" value="true"/>
        </javaModelGenerator>
        <sqlMapGenerator targetPackage="com.yichen.cook.food.mapper"
                         targetProject="D:/personal/learn note/project/test-demo/mybatis-generator/src/main/java">
            <property name="enableSubPackages" value="false"/>
        </sqlMapGenerator>
        <javaClientGenerator targetPackage="com.yichen.cook.food.mapper"
                             targetProject="D:/personal/learn note/project/test-demo/mybatis-generator/src/main/java"
                             type="XMLMAPPER">
            <property name="enableSubPackages" value="false"/>
        </javaClientGenerator>
        <table tableName="t_food_cook_steps" domainObjectName="foodCookSteps" >
            <!--设置自增主键-->
            <generatedKey column="id" sqlStatement="Mysql"></generatedKey>
            <!--用于配置不需要手动插入，即自动生成的，如修改时间-->
            <ignoreColumn column="create_time"></ignoreColumn>
        </table>
    </context>

</generatorConfiguration>
    
// java 运行程序
public class Generator {
    public static void main(String[] args)throws Exception {
        //MBG 执行过程中的警告信息
        List<String> warnings = new ArrayList<String>();
        //当生成的代码重复时，覆盖原代码
        boolean overwrite = true;
        //读取我们的 MBG 配置文件
        InputStream is = Generator.class.getResourceAsStream("/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        is.close();

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        //创建 MBG
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        //执行生成代码
        myBatisGenerator.generate(null);
        //输出警告信息
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
```







### 问题

#### The content of element type must match "property*, plugin*, commentGenerator?, jdbcConnection, javaTypeResolver?,javaModelGenerator, sqlMapGenerator?, javaClientGenerator?, table+"

>  常理是 标签放置的顺序问题，但 <font color=red>如果标签顺序已经按照要求放置了，那么可能就是排列问题，最好的方法就是手打内容而不是复制粘贴</font>

### Cannot obtain primary key information from the database, generated objects may be incomplete

[参考文章](https://blog.csdn.net/jpf254/article/details/79571396)

> 在 mysql 的url 中加    ?nullCatalogMeansCurrent=true

## java  lambda

[参考链接](https://blog.csdn.net/ioriogami/article/details/12782141/)

### 什么是   lambda  表达式

#### 版本一

```
public int add(int x, int y) {
    return x + y;
}
```

#### 版本二

```
(int x, int y) -> {return x + y;}
```

#### 版本三

```
(x,y)->{return x+y;}
```

#### 版本四

```
(x,y)->x+y;
```

<font color=red>可以看到它由三部分组成：参数列表，箭头（->），以及一个表达式或语句块。</font>

#### 如果无参数

```
()->return 3;
```

 #### 如果单个参数

```
x->x*x;//return 可以省略
```

### 函数式接口  funcational interface

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



### 设计lambda的一个原因

集合类的批处理操作与λ表达式的配合使用乃是Java8的最主要特性。集合类的批处理操作API的目的是实现集合类的“内部迭代”，并期望充分利用现代多核CPU进行并行计算。
**Java8之前集合类的迭代（Iteration）都是外部的，即客户代码。而内部迭代意味着改由Java类库来进行迭代，而不是客户代码。**

#### 外部迭代

```
for(Object o: list) { 
        System.out.println(o);
    }
```

#### 内部迭代

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

### Stream 方法中常用接口

#### reduce()

reduce操作可以实现从一组元素中生成一个值，`sum()`、`max()`、`min()`、`count()`等都是reduce操作，将他们单独设为函数只是因为常用。`reduce()`的方法定义有三种重写形式：

- `Optional<T> reduce(BinaryOperator<T> accumulator)`

<img src="./images/2021-02-05-4.jpg" alt="等价代码" style="zoom:67%;" />

- `T reduce(T identity, BinaryOperator<T> accumulator)`

<img src="./images/2021-02-05-3.jpg" alt="等价代码" style="zoom:67%;" />

- `<U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)`

<font color=red> 三个参数整体理解</font>

<img src="./images/2021-02-05-2.jpg" alt="三个参数说明" style="zoom:50%;" />

##### 示例代码

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

##### 运行结果

![运行结果](./images/2021-02-05-5.jpg)

#### collect()

##### 方法引用

|    方法引用类型    |      举例      |
| :----------------: | :------------: |
|    引用静态方法    |  Integer::sum  |
| 引用某个对象的方法 |   list::add    |
|  引用某个类的方法  | String::length |
|    引用构造函数    |  HashMap::new  |

##### 参数介绍

<img src="./images/2021-02-05-6.jpg" alt="三个参数示意" style="zoom:50%;" />









## 计算 程序运行时间

### 方法一

```java
//  开始计算时间
long start=System.currentTimeMillis();
//  结束计算时间
long stop=System.currentTimeMillis();
```



##  spring 路径匹配规则

+ `？` 匹配一个字符
+ `*` 匹配零个或多个字符
+ `**` 匹配路径中的零个或多个目录
+ `{spring:[az]+}` 与正则表达式   `[az]+` 匹配，并将其作为名为 `spring` 的路径变量

### 以上规则 可用于 git 的 `.gitignore`文件

```java
**/target/*.*      => 代表忽略target目录下的所有文件
**/*.iml    => 表示忽略 idea 项目的配置文件  .iml
```



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

## pointcut 表达式

[参考链接](https://cloud.tencent.com/developer/article/1497814#:~:text=Pointcut%E8%A1%A8%E8%BE%BE%E5%BC%8F%E7%B1%BB%E5%9E%8B%201%20execution%EF%BC%9A%E4%B8%80%E8%88%AC%E7%94%A8%E4%BA%8E%E6%8C%87%E5%AE%9A%E6%96%B9%E6%B3%95%E7%9A%84%E6%89%A7%E8%A1%8C%EF%BC%8C%E7%94%A8%E7%9A%84%E6%9C%80%E5%A4%9A%E3%80%82%202%20within%EF%BC%9A%E6%8C%87%E5%AE%9A%E6%9F%90%E4%BA%9B%E7%B1%BB%E5%9E%8B%E7%9A%84%E5%85%A8%E9%83%A8%E6%96%B9%E6%B3%95%E6%89%A7%E8%A1%8C%EF%BC%8C%E4%B9%9F%E5%8F%AF%E7%94%A8%E6%9D%A5%E6%8C%87%E5%AE%9A%E4%B8%80%E4%B8%AA%E5%8C%85%E3%80%82%203%20this%EF%BC%9ASpring%20Aop%E6%98%AF%E5%9F%BA%E4%BA%8E%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86%E7%9A%84%EF%BC%8C%E7%94%9F%E6%88%90%E7%9A%84bean%E4%B9%9F%E6%98%AF%E4%B8%80%E4%B8%AA%E4%BB%A3%E7%90%86%E5%AF%B9%E8%B1%A1%EF%BC%8Cthis%E5%B0%B1%E6%98%AF%E8%BF%99%E4%B8%AA%E4%BB%A3%E7%90%86%E5%AF%B9%E8%B1%A1%EF%BC%8C%E5%BD%93%E8%BF%99%E4%B8%AA%E5%AF%B9%E8%B1%A1%E5%8F%AF%E4%BB%A5%E8%BD%AC%E6%8D%A2%E4%B8%BA%E6%8C%87%E5%AE%9A%E7%9A%84%E7%B1%BB%E5%9E%8B%E6%97%B6%EF%BC%8C%E5%AF%B9%E5%BA%94%E7%9A%84%E5%88%87%E5%85%A5%E7%82%B9%E5%B0%B1%E6%98%AF%E5%AE%83%E4%BA%86%EF%BC%8CSpring,args%EF%BC%9A%E5%BD%93%E6%89%A7%E8%A1%8C%E7%9A%84%E6%96%B9%E6%B3%95%E7%9A%84%E5%8F%82%E6%95%B0%E6%98%AF%E6%8C%87%E5%AE%9A%E7%B1%BB%E5%9E%8B%E6%97%B6%E7%94%9F%E6%95%88%E3%80%82%206%20%40target%EF%BC%9A%E5%BD%93%E4%BB%A3%E7%90%86%E7%9A%84%E7%9B%AE%E6%A0%87%E5%AF%B9%E8%B1%A1%E4%B8%8A%E6%8B%A5%E6%9C%89%E6%8C%87%E5%AE%9A%E7%9A%84%E6%B3%A8%E8%A7%A3%E6%97%B6%E7%94%9F%E6%95%88%E3%80%82%207%20%40args%EF%BC%9A%E5%BD%93%E6%89%A7%E8%A1%8C%E7%9A%84%E6%96%B9%E6%B3%95%E5%8F%82%E6%95%B0%E7%B1%BB%E5%9E%8B%E4%B8%8A%E6%8B%A5%E6%9C%89%E6%8C%87%E5%AE%9A%E7%9A%84%E6%B3%A8%E8%A7%A3%E6%97%B6%E7%94%9F%E6%95%88%E3%80%82%20More%20items...%20)

### 类型匹配语法

`*`：匹配任何数量字符；

 `…`：匹配任何数量字符的重复，如在类型模式中匹配任何数量子包；而在方法参数模式中匹配任何数量参数。

 `+`：匹配指定类型的子类型；仅能作为后缀放在类型模式后边。

## luke

==lucence 索引查看工具==

[工具下载地址](https://github.com/DmitryKey/luke)

### 问题

#### index is locked try force unlock when opening

> 版本不一致，使用对应版本的 luke

## 零拷贝、MMAP、堆外内存

[参考解读文章](https://mp.weixin.qq.com/s/5hfW4VGaP34LilKCjGRZQg)

### IO 介绍

> 基本`I/O`：即不带缓冲区的`I/O`，如类`unix`系统中常用的`I/O`函数有：`read()`和`write()`等
>
> 标准`I/O`：在基本的`I/O`函数基础上增加了流和缓冲区的概念

### 读取文件数据传输到网上步骤

> 第一次数据拷贝：当用户进程发起`read()`调用后，上下文从用户态切换至内核态。`DMA`引擎从文件中读取数据，并存储到`Page Cache`（内核态缓冲区）。
>
> 第二次数据拷贝：请求的数据从内核态缓冲区拷贝到用户态缓冲区，然后返回给用户进程。同时会导致上下文从内核态再次切换到用户态。
>
> 第三次数据拷贝：用户进程调用`read()`方法期望将数据发送到网络中，此时用户态会再次切换到内核态，请求的数据从用户态缓冲区被拷贝到`Socket`缓冲区。
>
> 第四次数据拷贝：`send()`系统调用结束返回给用户进程，再次发生上下文切换 。此次操作会异步执行，从`Socket`缓冲区拷贝到协议引擎中。

### java 零拷贝技术 NIO  FileChannel

> `transferTo()`方法：可以将数据从`FileChannel`直接传输到另一个`Channel`
>
> `transferFrom`方法：将数据从`Channel`传输到`FileChannel`

> 1、用户进程调用`FileChannel#transferTo()`，上下文从用户态切换至内核态。
>
> 2、第一次数据拷贝：`DMA`从文件中读取数据，并存储到`Page Cache`
>
> 3、第二次数据拷贝：`CPU`将`Page Cache`中的数据拷贝到`Socket`缓冲区
>
> 4、第三次数据拷贝：`DMA`将`Socket`缓冲区数据拷贝到网卡进行数据传输

# 设计模式

**设计模式解决的是“可复用”的设计问题**

## 外观模式

> 外观模式提供了一个统一的接口，用来访问子系统中的一群接口。外观定义了一个高层接口，让子系统更容易使用。
>
> 个人理解：做菜包含买原材料、切菜、亨饪、出锅等步骤，我们可以把它们合起来放到一个类中，对外暴露出一个做菜接口。而原来的那些接口仍能使用。新的类新暴露出来的接口只是为了方便。

## 适配器模式

> 适配器模式将一个类的接口，转换成客户期望的另一个接口。适配器让原本接口不兼容的类可以合作无间。
>
> 个人理解：A 和 C 是不同类型的，B实现C的接口，内部有一个A属性，如此便可以将A转成C(B中通过A中的方法实现C接口中方法的功能)。     场景：调用外部服务，定义一个service接口，然后实现通过引用外部对象并实现该service接口。

> 对象适配器  =>  组合    类适配器  => 继承

## 策略模式

> 策略模式定义了算法族，分别封装起来，让它们之间可以互相替换，此模式让算法的变化独立于使用算法的客户。
>
> 个人理解：    Person person = new Man();    Person 有不同实现，变量定义使用接口，具体使用哪一个则看情况。

## 分层架构

### 优势

总结来说，代码分层架构设计主要为了实现责任分离、解耦、组件复用和标准制定

+ 只用关注整个结构中的某一层的具体实现
+ 降低层与层之间的依赖
+ 很容易用新的实现来替换原有层次的实现
+ 有利于标准化的统一
+ 各层逻辑方便复用

### 劣势

+ **开发成本变高**：因为不同层分别承担各自的责任，如果是高层次新增功能，则需要多个低层增加代码，这样难免会增加开发成本
+ **性能降低**：请求数据因为经过多层代码的处理，执行时间变长，性能会有所消耗
+ **代码复杂度增加**：因为层与层之间存在强耦合，所以对于一些组合功能的调用，则需要增加很多层之间的调用

### 总结

代码分层架构的核心作用有两个：

+ 对于功能性需求(使一个程序能为用户做些什么，比如文件上传、数据查询等)，将复杂问题分解为多个容易解决的子层问题
+ 对于非功能性需求(指功能性需求以外的其他必要需求，如性能、安全性、容错与恢复、本地化、国际化等)，可以提升代码可扩展性

**复杂的设计概念和简单的代码之间存在一种平衡，这就是分层架构**。

+ 代码分层架构设计的思维模型是简化思维，本质是抽象和拆解
+ 代码分层架构设计的目的是将复杂问题拆分为更容易解决的小问题，降低实现难度
+ 代码分层架构设计的原则和方法是通用方法，可以应用到其他需要分层设计的地方

# ddd

## 软件设计原则

### 单一职责原则

> 软件系统中的每个元素只完成自己职责范围内的事，而将其他的事交给别人去做，我只是去调用。
>
> 职责 =>  自己职责的范围到底在哪里  =>   一个职责就是软件变化的一个原因

### 开放-封闭原则(OCP)

> 开放原则：我们开发的软件系统，对于功能扩展是开发的(Open for Extension)，即当系统需求发生变更时，可以对软件功能进行扩展，使其满足用户新的需求。
>
> 封闭原则：对软件代码的修改应当是封闭的(Close for Modification)，即在修改软件的同时，不要影响到系统原有的功能，所以应当在不修改原有代码的基础上实现新的功能。也就是说，在增加新功能的时候，新代码与老代码应当隔离，不能在同一个类、同一个方法中。

### header first 设计原则

> 找出应用中可能需要变化之处，把它们独立出来，不要和那些不需要变化的代码混在一起。

> 针对接口编程，而不是针对实现编程。

> 多用组合，少用继承。

> 最少知识原则：只和你的密友谈话   =>  减少对象之间的交互

## 贫血模型和充血模型

## 两顶帽子

+ 在不添加新功能的前提下，重构代码，调整原有程序结构，以适应新功能
+ 实现你的功能

> 保持软件设计不退化的关键在于每次需求变更的设计，只有保证每次需求变更时做出正确的设计，才能保证软件以一种良性循环的方式不断维护下去。这种正确的设计方式就是"两顶帽子"。

### 领域驱动

+ 真实世界有什么事物，软件世界就有什么对象
+ 真实世界中这些事物都有哪些行为，软件世界中这些对象就有哪些方法
+ 真实世界中这些事物间都有哪些关系，软件世界中这些对象间就有什么关联



# 计算机网络

## NAT穿透

[具体讲解](https://www.cnblogs.com/idignew/p/7419074.html)

[参考内容](https://blog.csdn.net/Rookie_Manito/article/details/85261097)

### NAT分类

#### Full Cone NAT

内网主机建立一个socket(LocalIP:LocalPort) 第一次使用这个socket给外部主机发送数据时NAT会给其分配一个公网(PublicIP:PublicPort),以后用这个socket向外面任何主机发送数据都将使用这对(PublicIP:PublicPort)。此外，任何外部主机只要知道这个(PublicIP:PublicPort)就可以发送数据给(PublicIP:PublicPort)，内网的主机就能收到这个数据包。

#### Restricted Cone NAT

内网主机建立一个socket(LocalIP:LocalPort) 第一次使用这个socket给外部主机发送数据时NAT会给其分配一个公网(PublicIP:PublicPort),以后用这个socket向外面任何主机发送数据都将使用这对(PublicIP:PublicPort)。此外，如果任何外部主机想要发送数据给这个内网主机，只要知道这个(PublicIP:PublicPort)并且内网主机之前用这个socket曾向这个外部主机IP发送过数据。只要满足这两个条件，这个外部主机就可以用自己的(IP,任何端口)发送数据给(PublicIP:PublicPort)，内网的主机就能收到这个数据包。

#### Port Restricted Cone NAT

内网主机建立一个socket(LocalIP:LocalPort) 第一次使用这个socket给外部主机发送数据时NAT会给其分配一个公网(PublicIP:PublicPort),以后用这个socket向外面任何主机发送数据都将使用这对(PublicIP:PublicPort)。此外，如果任何外部主机想要发送数据给这个内网主机，只要知道这个(PublicIP:PublicPort)并且内网主机之前用这个socket曾向这个外部主机(IP,Port)发送过数据。只要满足这两个条件，这个外部主机就可以用自己的(IP,Port)发送数据给(PublicIP:PublicPort)，内网的主机就能收到这个数据包。

#### Symmetric NAT

内网主机建立一个socket(LocalIP,LocalPort),当用这个socket第一次发数据给外部主机1时,NAT为其映射一个(PublicIP-1,Port-1),以后内网主机发送给外部主机1的所有数据都是用这个(PublicIP-1,Port-1)，如果内网主机同时用这个socket给外部主机2发送数据，NAT会为其分配一个(PublicIP-2,Port-2), 以后内网主机发送给外部主机2的所有数据都是用这个(PublicIP-2,Port-2).如果NAT有多于一个公网IP，则PublicIP-1和PublicIP-2可能不同，如果NAT只有一个公网IP,则Port-1和Port-2肯定不同，也就是说一定不能是PublicIP-1等于 PublicIP-2且Port-1等于Port-2。此外，如果任何外部主机想要发送数据给这个内网主机，那么它首先应该收到内网主机发给他的数据，然后才能往回发送，否则即使他知道内网主机的一个(PublicIP,Port)也不能发送数据给内网主机，

## 抓包工具

### wireshark

[下载链接](https://www.wireshark.org/download.html)

[基本使用](https://www.cnblogs.com/lsdb/p/9254544.html)

#### 注意点

> 抓包需要 选择正确的网络接口，如果在错误的网络寻找包，无异于我是个傻子。。。
>
> 1、本地服务调用，选择  Adapter for loopback traffic capture

#### 常用捕获过滤器表达式

```java
// 根据远程 ip查询
ip.dst_host == '39.97.213.131'
// 协议 
最开始输入  http
```







## DNS

### DNS查询

==地 DNS 服务器包括用户自己路由器中的 DNS 缓存、小区的 DNS 服务器、ISP 的 DNS 服务器等==

<img src="./images/2021-06-24-1.jpg" alt="查询过程" style="zoom:50%;" />

### DNS记录

```java
// 定义www.example.com的ip地址
www.example.com.     IN     A     139.18.28.5;
```

+ `www.example.com` 是要解析的域名
+ `IN`  代表记录用于互联网，是Internet的缩写
+ `A` DNS记录类型，表示  IPv4记录
+ `139.18.28.5`  域名对应的ip地址

#### DNS 记录类型

##### CNAME(Canonical Name Record)

```java
// 定义www.example.com的别名
a.example.com.          IN     CNAME   b.example.com.
```

这条 DNS 记录定义了 a.example.com 是 b.example.com 的别名。用户在浏览器中输入 a.example.com 时候，通过 DNS 查询会知道 a.example.com 是 b.example.com 的别名，因此需要实际 IP 的时候，会去拿 b.example.com 的 A 记录。

##### AAAA

域名同IPv6之间的关系。

##### MX (Mail Exchanger Record)

MX 记录是邮件记录，用来描述邮件服务器的域名。

```java
IN MX mail.lagou.com
```

凡是 @lagou 的邮件都会发送到 mail.lagou.com 中，而 mail.lagou.com 的 IP 地址可以通过查询 mail.lagou.com 的 A 记录和 AAAA 记录获得。

##### NS(Name Server)

==记录是描述 DNS 服务器网址，即网站可以自己提供DNS解析。==

```java
a.com.     IN      NS      ns1.a.com.
a.com.     IN      NS      ns2.a.com.
```

a.com 的记录在 ns1.a.com 和 ns2.a.com 上。从设计上看，ns1 和 ns2 是网站 a.com 提供的智能 DNS 服务器，可以提供负载均衡、分布式 Sharding 等服务。

配置了上面的 NS 记录后，如果还配置了 a.com 的 A 记录，那么这个 A 记录会被 NS 记录覆盖。

## CDN

###### ==内容分发网络（Content Dilivery Network，CDN）是一个专门用来分发内容的分布式应用==

<font color=red>CDN的本质是缓存数据(图片，视频等)，最终来源是服务商</font>

### CDN回源

CDN 回源就是 CDN 节点到源站请求资源，重新设置缓存。通常服务提供方在使用 CDN 的时候，会在自己的某个域名发布静态资源，然后将这个域名交给 CDN。

比如源站在 s.example.com 中发布静态资源，然后在 CDN 管理后台配置了这个源站。在使用 CDN 时，服务提供方会使用另一个域名，比如说 b.example.com。然后配置将 b.example.com 用 CNAME 记录指向 CDN 的智能 DNS。这个时候，如果用户下载b.example.com/a.jpg，CDN 的智能 DNS 会帮用户选择一个最优的 IP 地址（最优的 CDN 节点）响应这次资源的请求。如果这个 CDN 节点没有 a.jpg，CDN 就会到 s.example.com 源站去下载，缓存到 CDN 节点，然后再返回给用户。

## RestFul架构

- Re（Representational）
- st（State）
- Ful（Transfer）

**在 RestFul 架构中，状态仅仅存在于服务端，前端无状态**。状态（State）可以理解为业务的状态，这个状态是由服务端管理的。

**前端（浏览器、应用等）没有业务状态，却又要展示内容，因此前端拥有的是状态的表示，也就是 Representation**。

前端没有状态，当用户想要改变订单状态的时候，比如支付，这个时候前端就向服务端提交表单，然后服务端触发状态的变化。这个过程我们称为**转化（Transfer）**。从这个角度来看，Restful 讲的是一套前端无状态、服务端管理状态，中间设计转化途径（请求、函数等）的架构方法。这个方法可以让前后端职责清晰，前端负责渲染， 服务端负责业务。前端不需要业务状态，只需要展示。服务端除了关心状态，还要提供状态的转换接口。

# 数据库

## navicate  premium

### 通过已有的数据库连接，获取密码

[参考方法](https://blog.csdn.net/weixin_45345798/article/details/114574640)

#### 步骤

+ `win+R`  输入`regedit` 打开注册表

+ 选择目录  `计算机`  > `HKEY_CURRENT_USER`  > `Software`  > `PrimiumSoft`  >  `Navicate`  >   `Servers`   该目录下的文件夹即为连接实例。但是这么的密码被加密了

+ 解密 密码

  + 登陆 [代码在线运行](https://tool.lu/coderunner)
  + 选择 `php`,输入如下代码

  ```php
  <?php
  namespace FatSmallTools;
  class NavicatPassword
  {
      protected $version = 0;
      protected $aesKey = 'libcckeylibcckey';
      protected $aesIv = 'libcciv libcciv ';
      protected $blowString = '3DC5CA39';
      protected $blowKey = null;
      protected $blowIv = null;
    	
      public function __construct($version = 12)
      {
          $this->version = $version;
          $this->blowKey = sha1('3DC5CA39', true);
          $this->blowIv = hex2bin('d9c7c3c8870d64bd');
      }
      
      public function encrypt($string)
      {
          $result = FALSE;
          switch ($this->version) {
              case 11:
                  $result = $this->encryptEleven($string);
                  break;
              case 12:
                  $result = $this->encryptTwelve($string);
                  break;
              default:
                  break;
          }
          
          return $result;
      }
      
      protected function encryptEleven($string)
      {
          $round = intval(floor(strlen($string) / 8));
          $leftLength = strlen($string) % 8;
          $result = '';
          $currentVector = $this->blowIv;
          
          for ($i = 0; $i < $round; $i++) {
              $temp = $this->encryptBlock($this->xorBytes(substr($string, 8 * $i, 8), $currentVector));
              $currentVector = $this->xorBytes($currentVector, $temp);
              $result .= $temp;
          }
          
          if ($leftLength) {
              $currentVector = $this->encryptBlock($currentVector);
              $result .= $this->xorBytes(substr($string, 8 * $i, $leftLength), $currentVector);
          }
          
          return strtoupper(bin2hex($result));
      }
      
      protected function encryptBlock($block)
      {
          return openssl_encrypt($block, 'BF-ECB', $this->blowKey, OPENSSL_RAW_DATA|OPENSSL_NO_PADDING); 
      }
      
      protected function decryptBlock($block)
      {
          return openssl_decrypt($block, 'BF-ECB', $this->blowKey, OPENSSL_RAW_DATA|OPENSSL_NO_PADDING); 
      }
      
      protected function xorBytes($str1, $str2)
      {
          $result = '';
          for ($i = 0; $i < strlen($str1); $i++) {
              $result .= chr(ord($str1[$i]) ^ ord($str2[$i]));
          }
          
          return $result;
      }
      
      protected function encryptTwelve($string)
      {
          $result = openssl_encrypt($string, 'AES-128-CBC', $this->aesKey, OPENSSL_RAW_DATA, $this->aesIv);
          return strtoupper(bin2hex($result));
      }
      
      public function decrypt($string)
      {
          $result = FALSE;
          switch ($this->version) {
              case 11:
                  $result = $this->decryptEleven($string);
                  break;
              case 12:
                  $result = $this->decryptTwelve($string);
                  break;
              default:
                  break;
          }
          
          return $result;
      }
      
      protected function decryptEleven($upperString)
      {
          $string = hex2bin(strtolower($upperString));
          
          $round = intval(floor(strlen($string) / 8));
          $leftLength = strlen($string) % 8;
          $result = '';
          $currentVector = $this->blowIv;
          
          for ($i = 0; $i < $round; $i++) {
              $encryptedBlock = substr($string, 8 * $i, 8);
              $temp = $this->xorBytes($this->decryptBlock($encryptedBlock), $currentVector);
              $currentVector = $this->xorBytes($currentVector, $encryptedBlock);
              $result .= $temp;
          }
          
          if ($leftLength) {
              $currentVector = $this->encryptBlock($currentVector);
              $result .= $this->xorBytes(substr($string, 8 * $i, $leftLength), $currentVector);
          }
          return $result;
      }
      
      protected function decryptTwelve($upperString)
      {
          $string = hex2bin(strtolower($upperString));
          return openssl_decrypt($string, 'AES-128-CBC', $this->aesKey, OPENSSL_RAW_DATA, $this->aesIv);
      }
  }
  use FatSmallTools\NavicatPassword;
  //需要指定版本，11或12
  //$navicatPassword = new NavicatPassword(12);
  $navicatPassword = new NavicatPassword(11);
  //解密
  $decode = $navicatPassword->decrypt('15057D7BA390');
  echo $decode."\n";
  
  ```

  + 在倒数第二行将字符串替换成 我们自己的加密字符，然后运行即可。
  + 解密后的密码在右侧，然后就ok了

## mysql

### 事务

#### 悬挂事务

```java
// 查看悬挂事务
XA RECOVER
// 查看状态
show engine innodb status\G
// 人工提交
XA COMMIT 'A';  //  XA RECOVER 查询出来的data 列的数据
```

#### 柔性事务

> 通过将每一步都插入表中，全部执行完毕后再插入事务完成表。
>
> 例如 某个事务需要执行  a、b两个步骤，a步骤完成则记录，b步骤完成则记录，a、b都完成再次记录。期间如果b出现意外，可以通过让定时任务来检验并执行处理逻辑。例如查询a记录表，有a，查询关联的b，没有则执行对应的处理逻辑。

### 高可用



### 有用插件

#### MGR（Mysql Group Replication）

mysql 5.7版本新加

### 查看运行日志

```java
//  日志是否开启
show variables like 'log_bin'
//  当前库对应的日志名称
show master status
//  查看日志内容
show binlog events in 'binlog.000056';
// 查看第一个binlog 中的日志
show binlog events
    
// 查看全局日志开启状态
show variables like 'general_log'
// 开启全局日志
set global general_log=on;
// 查看日志存放位置
show variables like '%log%'
```

#### 开启binlog 日志

[参考解决办法](https://blog.csdn.net/weixin_43944305/article/details/108620849)

```java
// my.ini 中 [mysqld] 下添加如下信息
log_bin=ON
log_bin_basename=/var/lib/mysql/mysql-bin
log_bin_index=/var/lib/mysql/mysql-bin.index
```



#### mysqlbinlog 查看

[参考链接](https://www.cnblogs.com/wqbin/p/14183943.html)

```java
mysqlbinlog binlog.000065
```

##### 错误问题

###### ERROR: Error in Log_event::read_log_event(): 'Sanity check failed', data_len: 31, event_type: 35

==mysqlbinlog的版本和binlog日志生成的版本不一致==

###### mysqlbinlog 查看binlog时报错unknown variable 'default-character-set=utf8'

[参考链接](https://www.cnblogs.com/cobbliu/p/4311926.html)

> 1、使用 --no-defaults。例： mysqlbinlog --no-defaults  --version
>
> 2、修改my.ini  将default-character-set=utf8 修改为 character-set-server = utf8   需要重启服务

### 慢日志分析

#### 查看慢日志功能

```java
//  默认不开启
show variables like '%slow_query_log%';
+---------------------+---------------------------------------------------------------+
| Variable_name       | Value                                                         |
+---------------------+---------------------------------------------------------------+
| slow_query_log      | ON                                                            |
| slow_query_log_file | D:\mysql-5.8\mysql-8.0.21-winx64data\DESKTOP-RR3GOJS-slow.log |
+---------------------+---------------------------------------------------------------+
// 开启   再次查询生效
set global slow_query_log=1;
```

#### 查看区分条件(多久被归为慢查询)

```java
//  默认为10s
show variables like '%long_query_time%'
//  自定义  关闭当前cmd 重新 查看生效
set global long_query_time=3;
```

#### 查询慢日志条数

```java
show global status like '%slow_queries';
```

#### 永久设置(重启后仍生效)

```java
slow_query_log=1
slow_query_log_file=d:/mysql/my.log
long_query_time=3
long_output=FILE
```



### sql执行计划 explain

#### 可执行情景

==select、delete、insert、replace、update==

#### 字段解释

[参考官方文档](https://dev.mysql.com/doc/refman/8.0/en/explain-output.html)

##### id(json格式叫:select_id)

select 标识符

##### select_type(json格式叫:none)

| `select_type` Value                                          | JSON Name                    | Meaning                                                      |
| :----------------------------------------------------------- | :--------------------------- | :----------------------------------------------------------- |
| `SIMPLE`                                                     | None                         | Simple [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) (not using [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html) or subqueries) |
| `PRIMARY`                                                    | None                         | Outermost [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) |
| [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html) | None                         | Second or later [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) statement in a [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html) |
| `DEPENDENT UNION`                                            | `dependent` (`true`)         | Second or later [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) statement in a [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html), dependent on outer query |
| `UNION RESULT`                                               | `union_result`               | Result of a [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html). |
| [`SUBQUERY`](https://dev.mysql.com/doc/refman/8.0/en/optimizer-hints.html#optimizer-hints-subquery) | None                         | First [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) in subquery |
| `DEPENDENT SUBQUERY`                                         | `dependent` (`true`)         | First [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) in subquery, dependent on outer query |
| `DERIVED`                                                    | None                         | Derived table                                                |
| `DEPENDENT DERIVED`                                          | `dependent` (`true`)         | Derived table dependent on another table                     |
| `MATERIALIZED`                                               | `materialized_from_subquery` | Materialized subquery                                        |
| `UNCACHEABLE SUBQUERY`                                       | `cacheable` (`false`)        | A subquery for which the result cannot be cached and must be re-evaluated for each row of the outer query |
| `UNCACHEABLE UNION`                                          | `cacheable` (`false`)        | The second or later select in a [`UNION`](https://dev.mysql.com/doc/refman/8.0/en/union.html) that belongs to an uncacheable subquery (see `UNCACHEABLE SUBQUERY`) |

##### table(json格式叫:table_name)

结果数据来源表

##### partitions(json格式叫:partitions)

结果匹配数据来源(存在分区的情况下)

##### type(json格式叫:access_type)

##### possible_keys(json格式叫:possible_keys)

possible_keys 列指示 MySQL 可以选择从中查找该表中行的索引。



#### 查看格式

+ FORMAT=TREE
+ FORMAT=JSON

### 数字类型

#### 整数类型

<font color=red>这里指定字段类型后，存储的是定长，即tinyint(1) 和 tinyint(3)都可以存储  `-128-127`范围的数值。</font>

==默认为signed类型,如果使用unsigned，搭配sql_mode=NO_UNSIGNED_SUBTRACTION使用==

| 类型      | 占用空间(字节数) | 最小值~最大值(signed)                    | 最小值~最大值(unsigned) |
| --------- | ---------------- | ---------------------------------------- | ----------------------- |
| TINYINT   | 1                | -128~127                                 | 0~255                   |
| SMALLINT  | 2                | -32768~32767                             | 0~65535                 |
| MEDUIMINT | 3                | -8388608~8388607                         | 0~16777215              |
| INT       | 4                | -2147483648~2147483647                   | 0~4294967295            |
| BIGINT    | 8                | -9223372036854775808~9223372036854775807 | 0~18446744073709551615  |

#### 浮点类型

`Float` 和 `Double` 这些类型因为不是高精度，也不是 SQL 标准的类型，所以在真实的生产环境中不推荐使用，否则在计算时，由于精度类型问题，会导致最终的计算结果出错。

#### 高精度

`Decimal`。在海量并发的互联网业务中使用，金额字段的设计并不推荐使用 DECIMAL 类型，而更推荐使用 INT 整型类型

#### 注意点

+ 自增主键用 `BIGINT`而不是`INT`，到达`INT`上限后自增id不会再变化。
+ MySQL 8.0 版本前，**自增不持久化**，自增值可能会存在回溯问题(mysql重启后自增id会变化)！<font color=red>即使建表指定了自增起始值或者通过 `alter table`设置起始值，重启数据库后都会丢失，需要重新设置</font>
+ 在海量互联网业务的设计标准中，并不推荐用 DECIMAL 类型，而是更推荐将 DECIMAL 转化为 整型类型。**如果精度到分，则可以通过除100实现。**

### 字符串类型

==推荐把 MySQL 的默认字符集设置为 UTF8MB4==

#### CHAR

CHAR(N) 用来保存固定长度的**字符**，N 的范围是 0 ~ 255

#### VARCHAR

VARCHAR(N) 用来保存变长字符，N 的范围为 0 ~ 65536， N 表示字符。

<font color=red>如果字符数量超过65536，则可以使用TEXT或BLOB，两者的最大存储长度为4G</font>

#### 排序规则

> SHOW CHARSET LIKE 'utf8%'
>
> // 排序规则以 _ci 结尾，表示不区分大小写（Case Insentive），_cs 表示大小写敏感，_bin 表示通过存储字符的二进制进行比较。
>
> SHOW COLLATION LIKE 'utf8mb4%';

#### 正确修改字符集

> // 新记录改为新的编码格式，旧的不变
>
> ALTER TABLE emoji_test CHARSET utf8mb4;
>
> // 更改所有记录编码格式
>
> ALTER TABLE emoji_test CONVERT TO CHARSET utf8mb4;

#### 字段约束

>// 8.0.16版本前 enum 外加设置严格模式
>
>`sex` enum('M','F') COLLATE utf8mb4_general_ci DEFAULT NULL,		
>
>SET sql_mode = 'STRICT_TRANS_TABLES'
>
>// 8.0.16版本后
>
>`sex` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL,
>
>CONSTRAINT `user_chk_1` CHECK (((`sex` = _utf8mb4'M') or (`sex` = _utf8mb4'F')))

#### 注意点

+ 比较 MySQL 字符串，默认采用不区分大小的排序规则。绝大部分业务的表结构设计无须设置排序规则为大小写敏感

### 时间类型

#### DATETIME

类型 DATETIME 最终展现的形式为：YYYY-MM-DD HH：MM：SS，固定**占用 8 个字节**。

从 MySQL 5.6 版本开始，DATETIME 类型支持毫秒，DATETIME(N) 中的 N 表示毫秒的精度。5.7.31 DATETIME五个字节。

#### TIMESTAMP

其实际存储的内容为‘1970-01-01 00:00:00’到现在的毫秒数。在 MySQL 中，由于类型 TIMESTAMP **占用 4 个字节**，因此其存储的时间上限只能到‘2038-01-19 03:14:07’。

从 MySQL 5.6 版本开始，类型 TIMESTAMP 也能支持毫秒。与 DATETIME 不同的是，**若带有毫秒时，类型 TIMESTAMP 占用 7 个字节，而 DATETIME 无论是否存储毫秒信息，都占用 8 个字节**。

#### 注意点

+ 日期字段推荐使用DATETIME，如果使用了TIMESTAMP，则需要显示配置时区(如果使用默认操作系统时区，会调用操作系统底层系统函数__tz_convert()，会执行加锁操作，影响性能)。且TIMESTAMP范围期限临近。
+ **每张业务核心表都增加一个 DATETIME 类型的 last_modify_date 字段，并设置修改自动更新机制，** 即便标识每条记录最后修改的时间。

#### 自带工具压测

[开发文档](https://dev.mysql.com/doc/refman/8.0/en/mysqlslap.html)

```java
mysqlslap -uroot -p123 -P3308 --number-of-queries=1000000 --concurrency=100 --query='SELECT NOW()'
```

### 非结构存储 json

#### 方式

```java
DROP TABLE IF EXISTS UserLogin;

CREATE TABLE UserLogin (

    userId BIGINT NOT NULL,

    loginInfo JSON,

    PRIMARY KEY(userId)

);
// 插入数据
SET @a = '
{
	"cellphone" : "13918888888",
	"wxchat" : "破产码农",
    "QQ" : "82946772"
}
';
INSERT INTO UserLogin VALUES (1,@a);
// 查询数据，具体见官方文档  ， mysql  5.7版本开始有json数据类型
SELECT
    userId,
    JSON_UNQUOTE(JSON_EXTRACT(loginInfo,"$.cellphone")) cellphone,
    JSON_UNQUOTE(JSON_EXTRACT(loginInfo,"$.wxchat")) wxchat
FROM UserLogin;
// 有效索引
ALTER TABLE UserLogin ADD COLUMN cellphone VARCHAR(255) AS (loginInfo->>"$.cellphone");
ALTER TABLE UserLogin ADD UNIQUE INDEX idx_cellphone(cellphone);

```

#### 注意点

+ JSON 数据类型的好处是无须预先定义列，数据本身就具有很好的描述性
+ JSON 数据类型推荐使用在不经常更新的静态数据存储。
+ 不要将有明显关系型的数据用 JSON 存储，如用户余额、用户姓名、用户身份证等，这些都是每个用户必须包含的数据；
+ 使用 JSON 数据类型，推荐用 MySQL 8.0.17 以上的版本，性能更好，同时也支持 Multi-Valued Indexes；

### 数据库范式

#### 三范式简化定义

+ 一范式要求所有属性都是不可分的基本数据项

+ 二范式解决部分依赖

+ 三范式解决传递依赖

#### 主键

##### 自增主键缺陷

> 1、自增存在回溯问题。
>
> 2、自增值在服务器端产生，存在并发性能问题。
>
> 3、自增值做主键，只能在当前实例中保证唯一，不能保证全局唯一。
>
> 4、公开数据值，容易引发安全问题，例如知道地址http://www.example.com/User/10/，很容猜出 User 有 11、12 依次类推的值，容易引发数据泄露。
>
> 5、MGR（MySQL Group Replication） 可能引起的性能问题。
>
> 6、分布式架构设计问题。

##### uuid

###### 缺陷

UUID 是根据时间位逆序存储， 也就是低时间低位存放在最前面，高时间位在最后，即 UUID 的前 4 个字节会随着时间的变化而不断“随机”变化，并非单调递增。而非随机值在插入时会产生离散 IO，从而产生性能瓶颈。这也是 UUID 对比自增值最大的弊端。

###### mysql 8.0优化

==UUID_TO_BIN/BIN_TO_UUID==

> UUID_TO_BIN(UUID())

> 优化内容如下：
>
> 1、通过参数将时间高位放在最前，解决了 UUID 插入时乱序问题；
>
> 2、去掉了无用的字符串"-"，精简存储空间；
>
> 3、将字符串其转换为二进制值存储，空间最终从之前的 36 个字节缩短为了 16 字节。

###### mysql 8.0版本之前，使用用户自己定函数

```java
CREATE FUNCTION MY_UUID_TO_BIN(_uuid BINARY(36))
    RETURNS BINARY(16)
    LANGUAGE SQL  DETERMINISTIC  CONTAINS SQL  SQL SECURITY INVOKER
    RETURN
        UNHEX(CONCAT(
            SUBSTR(_uuid, 15, 4),
            SUBSTR(_uuid, 10, 4),
            SUBSTR(_uuid,  1, 8),
            SUBSTR(_uuid, 20, 4),
            SUBSTR(_uuid, 25) ));

CREATE FUNCTION MY_BIN_TO_UUID(_bin BINARY(16))
    RETURNS  CHAR(36)
    LANGUAGE SQL  DETERMINISTIC  CONTAINS SQL  SQL SECURITY INVOKER
    RETURN
        LCASE(CONCAT_WS('-',
            HEX(SUBSTR(_bin,  5, 4)),
            HEX(SUBSTR(_bin,  3, 2)),
            HEX(SUBSTR(_bin,  1, 2)),
            HEX(SUBSTR(_bin,  9, 2)),
            HEX(SUBSTR(_bin, 11)) ));
```

### 表压缩

#### COMPRESS 页压缩

虽然 COMPRESS 压缩可以有效减小存储空间，但 COMPRESS 页压缩的实现对性能的开销是巨大的，性能会有明显退化。主要原因是一个压缩页在内存缓冲池中，存在压缩和解压两个页。

```java
CREATE TABLE Log (
  logId BINARY(16) PRIMARY KEY,
  ......
)
ROW_FORMAT=COMPRESSED
KEY_BLOCK_SIZE=8
//     后续数据压缩
ALTER TABLE Transaction202102 COMPRESSION=ZLIB；
//  全表压缩
OPTIMIZE TABLE Transaction202102;
```



#### TPC 压缩

TPC（Transparent Page Compression）是 5.7 版本推出的一种新的页压缩功能，其利用文件系统的空洞（Punch Hole）特性进行压缩。<font color=red>windows不支持。</font>

```java
CREATE TABLE Transaction （
  transactionId BINARY(16) PRIMARY KEY,
  .....
)
COMPRESSION=ZLIB | LZ4 | NONE;
```

### 索引

#### 唯一索引

[注意点一，自增id会变化](#唯一索引拦截不会影响自增id)

#### B+树索引结构

基于磁盘的平衡树，但树非常矮，通常为 3~4 层，能存放千万到上亿的排序数据。树矮意味着访问效率高，从千万或上亿数据里查询一条数据，只用 3、4 次 I/O。

==以下需要mysql8.0==

#### 查看索引情况

```java
SELECT 
table_name,index_name,stat_name,
stat_value,stat_description 
FROM innodb_index_stats 
WHERE table_name = 'orders' and index_name = 'PRIMARY';
```

#### 查看索引使用情况

```java
// 如果数据库使用了一段时间，索引还在该表中，则可以删除
SELECT * FROM schema_unused_indexes
WHERE object_schema != 'performance_schema';
// 保险起见，可以先将索引设置对优化器不可见，如果业务无影响，则可以删除
ALTER TABLE t1 
ALTER INDEX idx_name INVISIBLE/VISIBLE;
```

#### 查看表索引

```java
show index from test
```



### 索引出错：CBO索引规则

CBO（Cost-based Optimizer，基于成本的优化器），优化器的选择策略是执行成本。哪个索引的成本越低，优先使用哪个索引。

```java
// 一条sql的计算成本
Cost  = Server Cost + Engine Cost
      = CPU Cost + IO Cost
```



MySQL 数据库由 Server 层和 Engine 层组成：

- Server 层有 SQL 分析器、SQL优化器、SQL 执行器，用于负责 SQL 语句的具体执行过程；
- Engine 层负责存储具体的数据，如最常使用的 InnoDB 存储引擎，还有用于在内存中存储临时结果集的 TempTable 引擎。

#### server 计算规则

==select * from mysql.server_cost==

- disk_temptable_create_cost：创建磁盘临时表的成本，默认为20。
- disk_temptable_row_cost：磁盘临时表中每条记录的成本，默认为0.5。
- key_compare_cost：索引键值比较的成本，默认为0.05，成本最小。
- memory_temptable_create_cost：创建内存临时表的成本：默认为1。
- memory_temptable_row_cost：内存临时表中每条记录的成本，默认为0.1。
- row_evaluate_cost：记录间的比较成本，默认为0.1。

#### engine 计算规则

==select * from engine_cost==

- io_block_read_cost：从磁盘读取一个页的成本，默认值为1。
- memory_block_read_cost：从内存读取一个页的成本，默认值为0.25。

#### 自定义执行成本

```java
INSERT INTO 
engine_cost(engine_name,device_type,cost_name,cost_value,last_update,comment) 
VALUES ('InnoDB',0,'io_block_read_cost',12.5,CURRENT_TIMESTAMP,'Using HDD for InnoDB');


FLUSH OPTIMIZER_COSTS;
```

#### 查看 sql执行成本

```java
EXPLAIN FORMAT=json 
SELECT o_custkey,SUM(o_totalprice) 
FROM orders GROUP BY o_custkey\G
```

### 查询

#### join连接

#### 子查询

### 表分区

#### 注意问题

> 1、MySQL 分区表中，主键也必须是分区列的一部分，不然创建分区表时会失败。
>
> 2、MySQL 中的分区表是把一张大表拆成了多张表，每张表有自己的索引，从逻辑上看是一张表，但物理上存储在不同文件中。
>
> 3、在 MySQL 数据库中，分区表的索引都是局部，而非全局。也就是说，索引在每个分区文件中都是独立的，所以分区表上的唯一索引必须包含分区列信息。正因为唯一索引包含了分区列，唯一索引也就变成仅在当前分区唯一，而不是全局唯一了。

#### 错误想法

==分区表设计不解决性能问题，更多的是解决数据迁移和备份的问题。==

#### 示例

```java
// 分区创建
CREATE TABLE t (
    a INT,
    b INT,
    c DATETIME,
    d VARCHAR(32),
    e INT,
    PRIMARY KEY (a,b,c),
    KEY idx_e (e)
)
partition by range columns(c) (
    PARTITION p0000 VALUES LESS THAN ('2019-01-01'),
    PARTITION p2019 VALUES LESS THAN ('2020-01-01'),
    PARTITION p2020 VALUES LESS THAN ('2021-01-01'),
    PARTITION p9999 VALUES LESS THAN (MAXVALUE)
);
//  创建索引  需要包含主键
ALTER TABLE t ADD UNIQUE KEY idx_d(d,c);
// 插入数据    
insert into t values(1,1,'2021-01-01','aaa',1);
insert into t values(1,1,'2020-01-01','aaa',1);
// 删除分区数据
 alter table t truncate partition p2019;
```



### 命令

####  \g 和 \G

> \g 的作用是分号和在sql语句中写’;’是等效的 
> \G 的作用是将原来的行记录单独抽取出来，查看数据更加直观
>
> select * from userlogin\G

#### 导入备份数据

```java
 source d:/download/baidu_download/tpch_small/tpch_small.sql
```

#### 释放表空间

> // 查看表状态
> show table status from wk_db_loan  like 'wk_loan_order';

##### 提示错误

![错误示例](./images/2022-07-26-1.jpg)

##### 解决

> alter table wk_loan_order(目标表) engine='innodb';
>
> // 修改启动参数
>
> 用mysqld --skip-new或者mysqld --safe-mode命令来重启MySQL

innodb执行逻辑为

### 不太常用命令

```java
// 查看timeZone
show variables like '%time_zone%'
// 修改时区
set global time_zone = '+8:00';
FLUSH PRIVILEGES;
// 方法二  修改 my.ini(windows) 或 my.cnf(linux) 下的 [mysqld]  default-time_zone="+8:00"

// datetime  或 timestamp 中默认值
在字段中设置 默认值为  CURRENT_TIMESTAMP

// 查看数据库版本
select version()
// 查看当前使用的引擎
show engines
// 查看表自增id值
select auto_increment from information_schema.tables where table_schema=database() and table_name = 'test'
// 查看运行中的事务
select * from information_schema.innodb_trx;
// 查看当前运行的事务的账户和事务开始的时间,及其事务语句
select 
    a.id,a.user,a.host,b.trx_started,b.trx_query 
from information_schema.processlist a right outer join information_schema.innodb_trx b
on a.id = b.trx_mysql_thread_id;
// 查看连接数
SHOW FULL PROCESSLIST;
// 上面的连接数等于这里的  Threads_connected 字段
show status like 'Threads%'; 
// 
```

### 备注

> mysql 5.8版本(innodb引擎)前如果数据库重启，自增id会变化，如果表为空，则自增id变为0，否则变为当前表id最大加1。如果是MyISAM引擎则没事。

> mysql  `>` `=` `<` `!=` 不会比较 `null` ，`null`只能通过 `is`、`is not`比较

###  相关操作

#### windows下安装多个数据库

[本地安装多个mysql 参考步骤](https://blog.csdn.net/wudinaniya/article/details/82455431)

+ 下载 mysql zip安装包   [下载地址](https://downloads.mysql.com/archives/community/)。本地解压

+ 在安装目录下修改 `my.ini`，如果没有则手动创建，内容修改如下

```java
[mysqld]
# 设置3307端口(原先的mysql5已经占用3306)
port=3308
# 设置mysql的安装目录(你自己的目录)
basedir="D:/mysql-5.7/mysql-5.7.34-winx64"
# 设置mysql数据库的数据的存放目录
datadir="D:/mysql-5.7/mysql-5.7.34-winx64/data"
# 允许最大连接数
max_connections=200
# 允许连接失败的次数。
max_connect_errors=10
# 服务端使用的字符集默认为UTF8
character-set-server=utf8
# 创建新表时将使用的默认存储引擎
default-storage-engine=INNODB
# 默认使用“mysql_native_password”插件认证
#mysql_native_password
default_authentication_plugin=mysql_native_password
#处理日期不能 赋值  0000-00-00 00:00:00
sql_mode=ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION
#设置事务隔离级别
transaction_isolation=READ-COMMITTED
[mysql]
# 设置mysql客户端默认字符集
default-character-set=utf8
[client]
# 设置mysql客户端连接服务端时默认使用的端口
port=3307
default-character-set=utf8
[WinMySQLadmin]
Server="D:/mysql-5.7/mysql-5.7.34-winx64/bin/mysqld.exe"
```

+ 进入安装目录，安装mysql服务,<font color=red>记得以管理员方式启动cmd，服务名最好不要叫 MySQL 避免与 通多 exe安装时 的冲突</font>

> D:\mysql-5.7\mysql-5.7.34-winx64\bin>  mysqld install mysql7 --defaults-file="D:/mysql-5.7/mysql-5.7.34-winx64\my.ini"
>
> 成功安装后会提示：
> Service successfully installed.

+ 修改注册表

> //  win+r    regedit  打开注册表
>
> //  HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services\mysql7  修改ImagePath参数
>
> 修改为 mysql 安装目录 bin 下的mysqld  =>   D:\mysql-5.7\mysql-5.7.34-winx64\bin\mysqld mysql7

+ 在bin 目录下  初始化数据库

> //  数据库 初始化会根据 my.ini 的配置信息生成  data文件目录并初始化必要数据库
>
> D:\mysql-5.7\mysql-5.7.34-winx64\bin>   mysqld --initialize  

+ 启动 新安装 的mysql服务

> D:\mysql-5.7\mysql-5.7.34-winx64\bin>net start mysql7
> mysql7 服务正在启动 ..
> mysql7 服务已经启动成功。

+ 启动mysql D:\mysql-5.7\mysql-5.7.34-winx64\bin>mysql -P3308 -uroot -p
  Enter password: `********`

> //  去data/xxx.err文件(gs中文件名  DESKTOP-RR3GOJS.err )中找到临时密码(查找关键字 temporary)，进行登录
> 
> 2021-05-08T01:23:56.385718Z 1 [Note] A temporary password is generated for root@localhost: e#ez)<f8ef:T
>
>  
>
> // 登陆 数据库
>
> D:\mysql-5.7\mysql-5.7.34-winx64\bin>mysql -P3308 -uroot -p
>Enter password: 输入密码
>  
>// 修改密码，退出重进即可  5.7版本
> 
>set password for root@localhost=password('123');
> 
> // 修改密码  5.8版本
>
> ALTER USER 'root'@'localhost'  IDENTIFIED WITH mysql_native_password  BY 'root';



 

#### 将 `.sql` 文件中的数据插入到数据库中

> source /tmp/phone202103-mysql.sql     // 后面是对应的sql 文件位置
> <font color=red>这里需要在cmd命令行执行，而不能用三方工具，如navicat premiun</font>



### 问题记录

#### java date对象查mysql时间出现偏差

> 描述： 通过mybatis generator 生成实体类，其中有 example 协助插叙，其中将 datetime 转义为 Date对象，当对该对象进行范围查询时，出现日期偏差，经测试偏差时长为14个小时。 即目标查询时间为  2021-11-12 该天的记录，但是数据库中的记录信息  2021-11-11 11:00:00 该记录被查询出来了。

> 原因分析，因为数据库的时区是  CST,且默认时区就是系统时区。而电脑本地时区是东八区。CST 时区默认有4中解释，这里生效的是西六区。导致偏差了14个小时。

![数据库时区展示](./images/2021-11-23-1.jpg)

> 解决：在 jdbc  url中指定连接时区    serverTimezone=Asia/Shanghai  这样就能解决，或者指定系统时区。

#### mysql 5.8无法将timestamp 设为 0000-00-00 00:00:00

> 该问题与mysql 的sql_mode 有关，可以查询它的默认值
>
> SHOW VARIABLES LIKE 'sql_mode%';    //方法一
>
> SELECT @@sql_mode;    // 方法二
>
> // 查询结果中有如下两个  参数 NO_ZERO_IN_DATE,NO_ZERO_DATE	。修改mysql 的启动文件删除重启即可

<font color=red>apt 安装的mysql 的配置文件目录     /etc/mysql/mysql.conf.d/mysqld.cnf</font>

添加如下内容

> [mysqld]   // 未进行测试，不确定
>
> sql_mode=ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION

#### Could not retrieve transation read-only status server

> //查看 mysql 事务隔离级别   默认为 REPEATABLE-READ
>
> SHOW VARIABLES LIKE '%iso%';
>
> // 修改事务隔离级别
>
> set global transaction isolation level read committed
>
> <font color=red>配置永久生效   修改 my.cnf  [mysqld]下添加  transaction_isolation=READ-COMMITTED</font>

<font color=purple size=10dp solid>以上都是瞎扯，以下才是正确的姿势</font>

> 更改 mysql-connector-java 的版本，mysql版本为5.8的话用8.x.x即可

#### java jdbc数据库报错  closing inbound before receiving peer's close_notify

> 在数据库的url 加上    &useSSL=false

#### select * from table limit 1走了全表扫描

[参考解释，不太看的懂](https://dba.stackexchange.com/questions/62439/a-little-clarification-on-how-limit-works)

#### 自动更新字段 修改后没更新

> // 具体描述
>
> 例如时间字段，设定为自动更新(on update currrent_timestamp)，但是在数据行上修改数据后，发现该字段没有更新
>
> // 解决办法
>
> 因为在更新字段时，也修改了这个自动更新字段。此时该更新由于自动更新。

#### 唯一索引拦截不会影响自增id

> 例如我 host列设置为了唯一索引，id为自增主键，然后我重复插入两条host值相同的记录，则虽然插入会失败，但是自增id在插入失败的情况下还是会自动加一。导致产生数据跳跃。







# 算法-刷题

## 常用方法

### 字符串比较大小

```java
调用String.compareTo()方法即可，已经封装好了，不用在自己手写
```

### hashmap 自定义排序

```java
Map<String,Integer> map=new HashMap<>();
//   ...  给map赋值
List<Map.Entry<String, Integer>> result =
    new ArrayList<>(map.entrySet());

Collections.sort(result, new Comparator<Map.Entry<String, Integer>>() {
    @Override
    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        return o2.getValue()==o1.getValue()?o1.getKey().compareTo(o2.getKey()):o2.getValue()-o1.getValue();
    }
});
// 这里的 result 即为自定义排序后的结果集。
```



# 高端操作

## google 发送post请求

[参考链接](https://www.cnblogs.com/woyujiezhen/p/13961371.html)

```java
//参考代码
var xhr=new XMLHttpRequest();
xhr.open("POST","http://47.93.168.209:7006/ossFile/download.do?fileId=e2bdad90-06d9-470a-956c-869095a8ef71",true);
xhr.setRequestHeader("Content-Type","image/jpg");
xhr.setRequestHeader("Content-Disposition","hello");
xhr.send();
```

# 101Editor

[工具下载](https://www.bilibili.com/read/cv9946926/)

[模板下载](https://www.sweetscape.com/010editor/repository/templates/)

>  模板  > 新建模板 > 复制模板数据即可




# SSH

## 文章介绍

[ssh介绍](https://www.digitalocean.com/community/tutorials/understanding-the-ssh-encryption-and-connection-process)

## 连接步骤

## ssh-other

### 关键词介绍

> KEX    =>    Key Exchange
>
> 

### 执行逻辑

[ssh-handshake介绍](https://goteleport.com/blog/ssh-handshake-explained/)

### 主要步骤

> 1、SSH Version Exchange
>
> 2、Key Exchange
>
> 3、

### 开启日志

[参考](https://blog.csdn.net/handonch/article/details/120862961)

> cp /etc/ssh/sshd_config   /etc/ssh/sshd_config_back
>
> vi /etc/ssh/sshd_config
>
> //  取消下面的注释
>
> LogLevel INFO
>
> SyslogFacility AUTHPRIV  #日志级别
>
> 
>
> cp /etc/rsyslog.conf /etc/rsyslog.conf.2021-1-27.bak
>
> vi /etc/rsyslog.conf
>
> //  前面加 auth
>
> auth,authpriv.* /var/log/secure
>
> 
>
> // 重启服务
>
> service sshd restart