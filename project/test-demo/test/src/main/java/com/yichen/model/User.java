package com.yichen.model;

import java.io.Serializable;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/23 11:02
 * @describe
 */
public class User implements Serializable {

    private String loginName;
    private String username;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "loginName='" + loginName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
