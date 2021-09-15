package client.demo.controller;

import cn.hutool.core.io.file.FileReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;

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
     * 测试本地文件 在前端展示视频
     * @return
     */
    @RequestMapping("/read")
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
