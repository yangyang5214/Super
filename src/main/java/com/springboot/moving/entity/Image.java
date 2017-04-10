package com.springboot.moving.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/4/10.
 */
@Entity
@Table(name = "IMAGE")
public class Image {

    @Id
    @GeneratedValue()
    @Column(name = "ID", nullable = false, unique = true)
    private long id;

    @Column(name = "URL")
    private String url; // 图片url


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
