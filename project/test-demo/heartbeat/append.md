# 待添加功能
1、远程传输视屏和文本， 文本使用堆成加密算法
2、心跳机制，断线重连，使用二进制退避算法,识别管道连接状态，一方失联，另一方监听并关闭
3、拆包 粘包问题，数据大的时候需要进行考虑
4、身份认证
5、提供服务功能列表-身份鉴权
6、改造返回方式，用 Callable 替换 while(true) 逻辑


# 待研究问题
1、netty 心跳机制具体是怎么实现的
2、ctx.writeAndFlush()  同  ctx.channel().writeAndFlush()  区别
3、ctx.disconnect()     ctx.channel().closeFuture().sync()  区别  后者运行好像会报错


# 传输协议字段
1、type： 心跳、请求
2、数据长度：
3、数据内容
4、