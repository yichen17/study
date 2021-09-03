# 待添加功能
1、远程传输视屏和文本， 文本使用对称加密算法   登陆成功给密钥，  ==》 http 抓包 还是没办法避免
2、心跳机制，断线重连，使用二进制退避算法,识别管道连接状态，一方失联，另一方监听并关闭   `√`
3、拆包 粘包问题，数据大的时候需要进行考虑
4、身份认证 
  1)、用户登陆，登陆成功生成隐私文件传输key、将key放入map中，与内网使用需要用到、key有过期时间
  2)、访问私密数据需要鉴权、使用shiro
5、提供服务功能列表-身份鉴权
6、改造返回方式，用 Callable 替换 while(true) 逻辑   =》测试不太成功，还是依赖循环逻辑，待思考
7、springboot @ResponseBody 不显示 换行
8、传输协议 优化
9、netty channel 添加异常处理 handler
10、自定义网关  判断是否正常请求、netty是否请求正确， 出现异常则对它进行标记拉黑，   `√`


# 设计抉择
1、直接用jsp写前端还是前后端分离用vue写前端
2、


# 待处理问题
t_visit_host 表的自增id是跳跃式的 ，可能原因为插入数据 由于唯一索引拒绝后id仍在自增  <font color=red> 确实如此</font>
netty 连接的监控，添加异常处理 handler   => 已处理，添加 NettyExceptionHandler

# 待研究问题
1、netty 心跳机制具体是怎么实现的  =>  指定时间间隔如果没有读取或者写入消息，发送心跳包
2、ctx.writeAndFlush()  同  ctx.channel().writeAndFlush()  区别
3、ctx.disconnect()     ctx.channel().closeFuture().sync()  区别  后者运行好像会报错
4、替换日志  将springboot默认的logback 改为 slf4j2    `√`
5、内网通过netty与外网建立连接后，内网对应的外网ip是绑定单个端口，还是全部端口都对应
6、springboot 多次请求是复用链路还是 分开重建


# 传输协议字段
1、type： 心跳、请求
2、数据长度：
3、数据内容
4、分段位置(分段序列)