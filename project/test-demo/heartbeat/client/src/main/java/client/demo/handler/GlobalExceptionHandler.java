package client.demo.handler;

import client.demo.utils.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/30 10:21
 * @describe 全局异常拦截器
 */

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ReturnT<String> exceptionHandler(HttpServletRequest request, Exception e){
        log.error("全局捕获Exception异常，异常信息为{}", Arrays.toString(e.getStackTrace()));
        return new ReturnT<>("2",e.getMessage());
    }
}
