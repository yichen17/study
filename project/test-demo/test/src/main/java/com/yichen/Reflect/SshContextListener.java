package com.yichen.Reflect;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/23 10:36
 * @describe
 */

import com.yichen.Reflect.utils.SshConnectionTool;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author xcj
 * @date 2021/7/14 10:36
 */
@Component
public class SshContextListener implements ServletContextListener {
    private SshConnectionTool conexionssh;
    public SshContextListener() {
        super();
    }
    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("Context initialized ... !");
        try {
            conexionssh = new SshConnectionTool();
            conexionssh.setRemoteSshProperties("appuser","WWa7jn#2QQ8X",22,"123.57.186.183");
            conexionssh.setForwardProperties(3309,"127.0.0.1",3306,"192.168.68.6");
            conexionssh.getSession();
        } catch (Throwable e) {
            e.printStackTrace(); // 连接失败
        }
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("Context destroyed ... !");
        conexionssh.closeSsh(); // 断开连接
    }
}
