package com.jf.crawl.cloud.callback;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpClientPost {

    public static final String POST_URL = "http://127.0.0.1:8087/callback/recv";
    public static void postUrl() throws ClientProtocolException, IOException {
        long begaintime = System.currentTimeMillis();//开始系统时间
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost post = new HttpPost(POST_URL);
        Map<String, Object> map = new HashMap<>(5);
        map.put("appId", "bb5ed4dda5f8da498d8fdaf39164c438");
        map.put("body", "");
        map.put("notifyEvent", "task.success");
        map.put("product", "SESU");
        map.put("taskId", "003bceab-fa33-4dd4-abae-b0195009f94f");

        StringEntity entity = new StringEntity(JSON.toJSONString(map),"utf-8");
        entity.setContentEncoding("UTF-8");
        post.setHeader("Content-type", "application/json");
        post.setEntity(entity);

        HttpResponse response = httpclient.execute(post);
        if (response.getStatusLine().getStatusCode() == 201) {
            HttpEntity entitys = response.getEntity();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(entitys.getContent()));
            String line = reader.readLine();
            System.out.println(line);
        }else{
            HttpEntity r_entity = response.getEntity();
            String responseString = EntityUtils.toString(r_entity);
            System.out.println("错误码是："+response.getStatusLine().getStatusCode()+"  "+response.getStatusLine().getReasonPhrase());
            System.out.println("出错原因是："+responseString);
            //你需要根据出错的原因判断错误信息，并修改
        }
        long endTime = System.currentTimeMillis(); //结束时间
        System.out.println("  接口请求耗时 ： "+(endTime-begaintime));

        httpclient.getConnectionManager().shutdown();
    }
}
