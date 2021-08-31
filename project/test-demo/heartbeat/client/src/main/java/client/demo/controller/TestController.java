package client.demo.controller;

import client.demo.service.VisitHostService;
import client.demo.service.VisitLogService;
import client.demo.utils.MapTools;
import client.demo.utils.ReturnT;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.alibaba.fastjson.JSONObject;
import com.yichen.handler.NettyMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    public ReturnT test(){
        log.info("test接口访问成功");
        return new ReturnT("test");
    }
    @RequestMapping("/ers")
    public ReturnT error(){
        log.info("error访问成功");
        return new ReturnT(""+1/0);
    }


    @RequestMapping(value = "/get")
    public ReturnT getData(HttpServletRequest request){
        try{
            log.info("接收到请求，访问ip为{}",request.getRemoteAddr());
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
                        Thread.sleep(10000);
                        continue;
                    }
                    if(future.isDone()){
                        String res= future.get();
                        ReturnT data= JSONObject.parseObject(res,ReturnT.class);
                        log.info("远程调用返回的结果{},加密数据为:{}",res,data.getData().toString());
                        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, "yichenshanliangz".getBytes());
                        String decryptStr = aes.decryptStr(data.getData().toString(), CharsetUtil.CHARSET_UTF_8);
                        log.info("解密后的数据结果{}",decryptStr);
                        return new ReturnT(decryptStr.replace("|","\n"));
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
            return new ReturnT("1","出现异常");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ReturnT("1","get future error");
        }
    }

}
