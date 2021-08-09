package client.demo.utils;

import io.netty.channel.Channel;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/6 9:22
 * @describe 映射工具，存储各种映射关系
 */
@Data
public class MapTools {

    /**
     * 错误 连接上的 channel
     */
    public static HashSet<Channel> channels=new HashSet<Channel>();
    /**
     * 存储 channel 和 channelFuture之间的映射关系，在获取内网返回的结果后，根据 channel 反传 controller
     */
    public static HashMap<Channel, Future> channelFutureMap=new HashMap<>();
    /**
     * 内网主机 ip地址
     */
    public static HashSet<String> ips=new HashSet<>();

}
