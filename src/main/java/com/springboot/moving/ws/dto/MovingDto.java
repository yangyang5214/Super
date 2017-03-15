package com.springboot.moving.ws.dto;

import com.google.common.collect.Lists;
import com.springboot.moving.entity.Comment;

import java.util.List;

/**
 * Created by Administrator on 2017/3/11.
 */
public class MovingDto {

    private String content;
    private String position;
    private String imageUrl;
    private String publishTime;

    private String userid;
    private String userName;
    private String avatarUrl;//头像

    private List<Comment> listComment = Lists.newArrayList();

    public List<Comment> getListComment() {
        return listComment;
    }

    public void setListComment(List<Comment> listComment) {
        this.listComment = listComment;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
