package com.springboot.user.ws.dto;

import com.springboot.common.anotation.ExcelHeadMap;

/**
 * Created by Administrator on 2017/3/11.
 */
public class UserPoiDto {

    @ExcelHeadMap(name = "用户名")
    private String username;

    @ExcelHeadMap(name = "密码")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
