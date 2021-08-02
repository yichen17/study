package com.yichen.appmvc.vo;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/7/28 9:28
 * @describe
 */
public class ResultVo<T> {
    private String code;
    private T data;


    public ResultVo() {
    }



    @Override
    public String toString() {
        return "ResultVo{" +
                "code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public String getCode() {
        return code;
    }

    public ResultVo setCode(String code) {
        this.code = code;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultVo setData(T data) {
        this.data = data;
        return this;
    }
}
