package client.demo.controller;

import client.demo.utils.MapTools;
import com.yichen.handler.NettyMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/5 15:37
 * @describe 远程请求 controller ，单纯转发数据
 */
@RestController
@Slf4j
public class TestController {

    @RequestMapping("/test")
    public String test(){
        log.info("test接口访问成功");
        return "test";
    }


    @RequestMapping(value = "/get")
    public String getData(){
        try{
            log.info("get方法开始调用");
            Channel channel=MapTools.channels.iterator().next();
            // 远程调用服务
            NettyMessage nettyMessage=new NettyMessage();
            nettyMessage.setCode(2);
            nettyMessage.setData("调用内网服务");
            ChannelFuture channelFuture = channel.writeAndFlush(nettyMessage);
            log.info("向管道写入数据，管道为"+channel+"数据为"+nettyMessage);
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(channelFuture.isSuccess()){
                        MapTools.channelFutureMap.put(channel,new CompletableFuture<String>());
                        log.info("写入成功");
                    }
                    else{
                        log.info("写入失败");
                        // 打印向 channel 管道发送消息失败的内容
                        future.cause().printStackTrace();
                    }
                }
            });
            while(true){
                try{
                    CompletableFuture<String> future=(CompletableFuture<String>)MapTools.channelFutureMap.get(channel);
                    if(future==null){
                        Thread.sleep(1000);
                        continue;
                    }
                    if(future.isDone()){
                        String res= future.get();
                        log.info("远程调用返回的结果{}",res);
                        return res;
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
//            return "future 没有获取到值";
        }
        catch (Exception e){
            e.printStackTrace();
            return "get future error";
        }
    }

}
