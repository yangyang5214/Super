package com.springboot.find.ws.dto;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/11.
 */
public class MovingDto {

    private long id;
    private String content;
    private String position;
    private List<String> imageUrl = new ArrayList<>();
    private String publishTime;

    private String userId;
    private String userName;
    private String avatarUrl;//头像

    private Integer movingType;

    private String commentCount; //评论总数
    private String goodCount;//点赞总数
    private List<CommentDto> listComment = Lists.newArrayList();

    public MovingDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public String getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(String goodCount) {
        this.goodCount = goodCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public int getMovingType() {
        return movingType;
    }

    public void setMovingType(Integer movingType) {
        this.movingType = movingType;
    }


    public List<CommentDto> getListComment() {
        return listComment;
    }

    public void setListComment(List<CommentDto> listComment) {
        this.listComment = listComment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
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
