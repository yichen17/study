package client.demo.config;

import client.demo.model.ReturnT;
import client.demo.utils.RemoteUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/3 16:15
 * @describe
 */
@Component
public class LinkServer implements InitializingBean, DisposableBean {

    private  volatile boolean stop=false;
    private Thread thread;

    @Override
    public void destroy() throws Exception {
        stop = true;

        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("初始化构建");
                while(!stop){
                    ReturnT returnT = RemoteUtils.postBody("http://localhost:8088/order/url", "", 3, null, String.class);
                    if(returnT.getCode()!=ReturnT.SUCCESS_CODE){
                        stop=true;
                    }
                    else{
                        System.out.println(returnT.getMsg());
                        try{
                            Thread.sleep(1000);
                        }
                        catch (Exception e){

                        }
                    }
                }

            }
        });
        thread.start();
    }
}
