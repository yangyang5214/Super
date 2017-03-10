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
    private String usernaem;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "UNIVERSITYNAME")
    private String universityName;

    @Column(name = "NICKNAME")
    private String nickName;

    @Column(name = "SEX")
    private Boolean sex;

    @Column(name = "SIGNATURE")
    private String signature;


    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
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

    public String getUsernaem() {
        return usernaem;
    }

    public void setUsernaem(String usernaem) {
        this.usernaem = usernaem;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
