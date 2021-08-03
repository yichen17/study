package client.demo.test;

import client.demo.model.ReturnT;
import client.demo.utils.RemoteUtils;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/3 15:20
 * @describe
 */
public class Client {
    private static volatile Boolean stop=false;
    public static void main(String[] args) {

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(!stop){
                    ReturnT returnT = RemoteUtils.postBody("http://localhost:8088/order/heartbeat", "", 3, null, String.class);
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
