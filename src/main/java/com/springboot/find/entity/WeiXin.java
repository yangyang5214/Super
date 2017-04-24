package com.springboot.find.entity;

import com.springboot.common.entity.AggregateRoot;

import javax.persistence.*;

/**
 * Created by wangxiaosan on 2017/4/24.
 */
@Entity
@Table(name = "WEIXIN")
public class WeiXin extends AggregateRoot {


    @Id
    @GeneratedValue()
    @Column(name = "ID", nullable = false, unique = true)
    private long id;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "IMAGEURL")
    private String imageUrl;

    @Column(name = "URL")
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
