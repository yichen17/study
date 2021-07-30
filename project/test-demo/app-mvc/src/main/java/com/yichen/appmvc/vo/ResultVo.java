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

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
