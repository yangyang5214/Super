package com.springboot.user.entity;

import com.springboot.common.entity.AggregateRoot;

import javax.persistence.*;

/**
 * Created by zl on 2017/3/9.
 */
@Entity
@Table(name = "USER")

public class User extends AggregateRoot{

    @Id
    @GeneratedValue()
    @Column(name = "ID", nullable = false, unique = true)
    private long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "UNIVERSITYNAME")
    private String universityName;

    @Column(name = "NICKNAME")
    private String nickName;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "SIGNATURE")
    private String signature;

    @Column(name = "AREA")
    private String area;

    @Column(name = "AVATARURL")
    private String avatarUrl; //头像

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
