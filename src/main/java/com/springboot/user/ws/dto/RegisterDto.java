package com.springboot.user.ws.dto;

/**
 * Created by zl on 2017/3/10.
 */
public class RegisterDto {

    private String username;
    private String password;

    public RegisterDto(UserPoiDto userPoiDto) {
        this.username = userPoiDto.getUsername();
        this.password = userPoiDto.getPassword();
    }

    public RegisterDto() {
    }

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
