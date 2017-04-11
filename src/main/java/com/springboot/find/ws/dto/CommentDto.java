package com.springboot.find.ws.dto;

/**
 * Created by zl on 2017/3/15.
 */
public class CommentDto {

    private String movingId;
    private String content;
    private String commentUserId;
    private String unCommentUserId;

    public String getUnCommentUserId() {
        return unCommentUserId;
    }

    public void setUnCommentUserId(String unCommentUserId) {
        this.unCommentUserId = unCommentUserId;
    }

    public String getMovingId() {
        return movingId;
    }

    public void setMovingId(String movingId) {
        this.movingId = movingId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }
}
