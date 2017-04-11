package com.springboot.find.entity;

import com.springboot.common.entity.AggregateComponent;
import com.springboot.common.entity.AggregateRoot;

import javax.persistence.*;

/**
 * Created by wangxiaosan on 2017/4/7.
 */
@Entity
@Table(name = "IMAGE")
public class Image extends AggregateRoot {

    @Id
    @GeneratedValue()
    @Column(name = "ID", nullable = false, unique = true)
    private long id;

    @Column(name = "URL")
    private String url;

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
