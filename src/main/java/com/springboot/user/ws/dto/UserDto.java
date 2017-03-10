package com.springboot.user.ws.dto;

import com.springboot.user.entity.User;

/**
 * Created by zl on 2017/3/9.
 */
public class UserDto {

    private Long id;
    private String universityName;
    private String nickName;
    private String sex;
    private String signature;
    private String area;
    public UserDto() {

    }

    public UserDto(User user) {

    }

    public void UserDto(User user) {
        this.id = user.getId();
        this.nickName = user.getNickName();
        this.universityName = user.getUniversityName();
        this.sex = user.getSex();
        this.signature = user.getSignature();
        this.area = user.getArea();
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
