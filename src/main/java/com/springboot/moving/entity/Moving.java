package com.springboot.moving.entity;

import com.google.common.collect.Lists;
import com.springboot.common.entity.AggregateRoot;
import com.springboot.user.entity.User;

import javax.persistence.*;
import java.util.List;

/**
 * Created by zl on 2017/3/10.
 */
@Entity
@Table(name = "MOVING")
public class Moving extends AggregateRoot {

    @Id
    @GeneratedValue()
    @Column(name = "ID", nullable = false, unique = true)
    private long id;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "IMAGEURL")
    private String imageUrl;

    @Column(name = "POSITION")
    private String position;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private User user;

    //orphanRemoval:如：一级分类删除，是否自动删除和该一级分类外键的二级分类及关联的商品对象，true代表自动删除
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "moving",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = Lists.newArrayList();

    /**
     * type  0:动态 1：活动
     */
    @Column(name = "TYPE")
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
