package server.demo.controller;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.yichen.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.demo.utils.ReturnT;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/10 15:31
 * @describe 个人数据请求 controller
 */
@RestController
@Slf4j
public class PersonalController {

    @Value("${yichen.file.path}")
    private String filePath;

    @Value("${yichen.secret}")
    private String secret;

    @RequestMapping("/getFile")
    public ReturnT getFile(){
        // TODO 读取的数据没了换行,需要存在换行，不然不易查看
//        FileReader fileReader=new FileReader(filePath);
//        String data=fileReader.readString();
        String data= FileUtils.readFileByPath(filePath);
        log.info("原数据{}",data);
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, "yichenshanliangz".getBytes());
        String encodeData=aes.encryptHex(data);
        log.info("加密后的数据{}",encodeData);
        return new ReturnT(encodeData);
    }

//    public static void main(String[] args) {
//        FileReader fileReader=new FileReader("F:\\test_store\\test.txt");
//        String data=fileReader.readString();
//        System.out.println(data);
//    }



}
