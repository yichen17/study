package client.demo.controller;

import client.demo.utils.MapTools;
import client.demo.utils.ReturnT;
import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson.JSONObject;
import com.yichen.handler.NettyMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.concurrent.CompletableFuture;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/9/15 13:53
 * @describe 视频控制器
 */
@Controller
@RequestMapping("/video")
@Slf4j
public class VideoController {

    @RequestMapping("/show")
    public String show(){
        log.info("VideoController > show,即将跳转视频页面");
        return "video";
    }

    /**
     *  调用远程服务获取 流数据
     */
    @RequestMapping("/readRemote")
    @ResponseBody
    public void readRemote(HttpServletResponse response)throws Exception{
        // TODO 由于是摄像头，所以可以容忍无法滑动(拖拽)  也可以有一点延迟，只要是连续的即可
        //  1、请求远程服务  2、远程服务一次一次发送数据流  3、循环写入 response  4、



        try{
            // 判定是否有现成的netty连接，如果没有则直接返回默认值
            if(MapTools.channels.size()==0){
                return ;
            }
            response.addHeader("Content-Type", "video/mp4");
            Channel channel=MapTools.channels.iterator().next();
            // 远程调用服务
            NettyMessage nettyMessage=new NettyMessage();
            nettyMessage.setCode(2);
            nettyMessage.setData("/video");
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
                // TODO 这里需要反复从 future 读取数据
                //  另一侧需要 持续不断的写入  不停调用视频接口？读取视频流？
                try{
                    CompletableFuture<String> future=(CompletableFuture<String>)MapTools.channelFutureMap.get(channel);
                    if(future==null){
                        Thread.sleep(10000);
                        continue;
                    }
                    if(future.isDone()){
                        String res= future.get();
                        ReturnT data= JSONObject.parseObject(res,ReturnT.class);
                        log.info("请求返回结果{}",data);
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(data.getData().toString().getBytes());
                        log.info("转换后的inputStream{}",inputStream);
                        //流转换
                        IOUtils.copy(inputStream,response.getOutputStream());
                        //设置返回类型
                        response.flushBuffer();

                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 测试本地文件 在前端展示视频
     */
    @RequestMapping("/readFile")
    @ResponseBody
    public void readFile(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String path="F:/test_store/file.mp4";
        FileReader fileReader = new FileReader(path);
        BufferedInputStream inputStream = fileReader.getInputStream();

        //流转换
        IOUtils.copy(inputStream,response.getOutputStream());
        //设置返回类型
        response.addHeader("Content-Type", "video/mp4");

        response.flushBuffer();
    }

}
