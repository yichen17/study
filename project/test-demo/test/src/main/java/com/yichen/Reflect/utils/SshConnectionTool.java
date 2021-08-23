package com.yichen.Reflect.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/23 9:53
 * @describe 构建 通过ssh桥接实现的 连接
 */
public class SshConnectionTool {

    /**
     * ssh连接的用户名
     */
    private  String sshUsername;
    /**
     * ssh连接的密码
     */
    private  String sshPassword;
    /**
     * ssh远程连接的ip地址
     */
    private  String sshHost;
    /**
     * ssh连接的端口号
     */
    private  int sshPort;

    /**
     * 本地端口
     */
    private int localPort;
    /**
     * 本地数据库ip地址
     */
    private String localHost;
    /**
     * 远程数据库 端口
     */
    private int remotePort;
    /**
     *  远程数据库ip地址
     */
    private String remoteHost;

    /**
     *  ssh 连接实例
     */
    private Session session;


    /**
     * 关闭  ssh
     */
    public void closeSsh() {
        if(session!=null){
            session.disconnect();
        }
    }

    /**
     * 设置 转发相关参数
     * @param localPort  本地端口
     * @param remotePort 远程 mysql 端口
     * @param remoteHost 远程 mysql ip地址
     * @param localHost 本地 ip地址
     */
    public void setForwardProperties(Integer localPort,String localHost,Integer remotePort,String remoteHost){
        this.localPort=localPort;
        this.localHost=localHost;
        this.remotePort=remotePort;
        this.remoteHost=remoteHost;
    }

    /**
     *  设置 连接的  ssh 相关参数
     * @param username  ssh 连接用户名
     * @param password ssh 连接密码
     * @param port  ssh连接端口
     * @param host ssh连接 ip地址
     */
    public void setRemoteSshProperties(String username,String password,Integer port,String host){
        this.sshUsername=username;
        this.sshPassword=password;
        this.sshPort=port;
        this.sshHost=host;
    }


    /**
     * 构建 ssh连接
     * @return ssh连接实例
     * @throws Throwable ssh 连接异常
     */
    public Session getSession() throws Throwable {

        JSch jsch = new JSch();

        session = jsch.getSession(sshUsername, sshHost, sshPort);

        session.setPassword(sshPassword);

        //设置连接过程不校验known_hosts文件中的信息
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect(); //ssh 建立连接！

        //端口转发，将 原本配置的本地mysql 转发为 远程mysql 地址
        session.setPortForwardingL(localHost,localPort, remoteHost, remotePort);
        return session;
    }
}
