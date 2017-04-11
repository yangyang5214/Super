package com.springboot.find.entity;

import com.springboot.common.entity.AggregateRoot;
import com.springboot.user.entity.User;

import javax.persistence.*;

/**
 * Created by zl on 2017/3/15.
 */
@Entity
@Table(name = "COMMENT")
public class Comment extends AggregateRoot {

    @Id
    @GeneratedValue()
    @Column(name = "ID", nullable = false, unique = true)
    private long id;

    @Column(name = "CONTENT")
    private String content; // 评论内容

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "UN_COMMENT_USER_ID", referencedColumnName = "ID", nullable = true)
    private  User unCommentUser; // 被评论人

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_USER_ID", referencedColumnName = "ID", nullable = true)
    private  User commentUser; // 评论人

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "MOVING_ID", referencedColumnName = "ID", nullable = false)
    private Moving moving; // 动态id


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

    public User getUnCommentUser() {
        return unCommentUser;
    }

    public void setUnCommentUser(User unCommentUser) {
        this.unCommentUser = unCommentUser;
    }

    public User getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(User commentUser) {
        this.commentUser = commentUser;
    }

    public Moving getMoving() {
        return moving;
    }

    public void setMoving(Moving moving) {
        this.moving = moving;
    }
}
