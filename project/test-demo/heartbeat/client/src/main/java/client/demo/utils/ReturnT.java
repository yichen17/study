package client.demo.utils;

import lombok.Data;
import lombok.ToString;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/12 9:21
 * @describe 前端统一返回结果
 */
@Data
@ToString
public class ReturnT<T> {
    /**
     * 状态码 0 成功，1 失败
     */
    private String code;
    /**
     * 反馈消息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;

    /**
     * 没有获取数据构造
     * @param code 失败情况码值
     * @param msg 失败的原因
     */
    public ReturnT(String code,String msg){
        this.code=code;
        this.msg=msg;
    }

    /**
     * 成功情况自定义 消息和返回结果
     * @param msg
     * @param data
     */
    public ReturnT(String msg,T data){
        this.code="0";
        this.msg=msg;
        this.data=data;
    }

    /**
     * 执行成功，设置返回结果
     * @param data
     */
    public ReturnT(T data){
        this.code="0";
        this.msg="成功获取数据";
        this.data=data;
    }


}
