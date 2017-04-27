package com.springboot.find.ws.dto;

/**
 * Created by zl on 2017/3/15.
 */
public class CommentDto {

    private long id;
    private String commentTime;
    private long movingId;
    private String content;
    private long commentUserId;
    private String commentUserName;
    private String commentUserImage;
    private long unCommentUserId;
    private String unCommentUserName;
    public CommentDto() {
    }
    public long getUnCommentUserId() {
        return unCommentUserId;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public long getId() {
        return id;
    }

    public String getCommentUserImage() {
        return commentUserImage;
    }

    public long getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserImage(String commentUserImage) {
        this.commentUserImage = commentUserImage;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getUnCommentUserName() {
        return unCommentUserName;
    }

    public void setUnCommentUserName(String unCommentUserName) {
        this.unCommentUserName = unCommentUserName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public void setUnCommentUserId(long unCommentUserId) {
        this.unCommentUserId = unCommentUserId;
    }

    public long getMovingId() {
        return movingId;
    }

    public void setMovingId(long movingId) {
        this.movingId = movingId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCommentUserId(long commentUserId) {
        this.commentUserId = commentUserId;
    }
}
