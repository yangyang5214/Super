package com.springboot.moving.service;

import com.google.common.collect.Lists;
import com.springboot.common.dto.ListResponseDto;
import com.springboot.common.dto.ResponseDto;
import com.springboot.common.util.Collections3;
import com.springboot.common.util.FastDFSUtil;
import com.springboot.moving.dao.MovingDao;
import com.springboot.moving.entity.Comment;
import com.springboot.moving.entity.Moving;
import com.springboot.moving.ws.dto.CommentDto;
import com.springboot.moving.ws.dto.MovingDto;
import com.springboot.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by zl on 2017/3/10.
 */
@Service
@Transactional
public class MovingService {

    @Autowired
    private MovingDao movingDao;

    public ResponseDto publishMoving(MultipartFile file, MovingDto movingDto) {
        Moving moving = new Moving();
        if (null == file) {
            try {
                moving.setContent(URLDecoder.decode(movingDto.getContent(), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            moving.setUser(movingDao.findById(User.class, Long.parseLong(movingDto.getUserId())));
            moving.setPosition(movingDto.getPosition());
            moving.setType(movingDto.getMovingType());
        } else {
            try {
                moving.setImageUrl(FastDFSUtil.saveImage(file.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        movingDao.persist(moving);
        return new ResponseDto();
    }


    public ListResponseDto<MovingDto> listMoving(int offset, int size) {
        ListResponseDto<MovingDto> listResponseDto = new ListResponseDto<>();
        List<MovingDto> listMovingDto = Lists.newArrayList();
        List<Moving> listMoving = movingDao.listMoving((offset - 1) * size, size);
        listMoving.stream().forEach(p -> listMovingDto.add(formatMoving(p)));
        listResponseDto.setObjs(listMovingDto);
        return listResponseDto;
    }

    public MovingDto formatMoving(Moving moving) {
        MovingDto movingDto = new MovingDto();
        movingDto.setAvatarUrl(moving.getUser().getAvatarUrl());
        movingDto.setUserId(String.valueOf(moving.getUser().getId()));
        movingDto.setUserName(moving.getUser().getUsername());
        movingDto.setContent(moving.getContent());
        movingDto.setImageUrl(moving.getImageUrl());
        movingDto.setPublishTime(String.valueOf(moving.getCreationTime()));
        movingDto.setPosition(moving.getPosition());
        if (Collections3.isNotEmpty(moving.getCommentList())) {
            List<CommentDto> listComment = Lists.newArrayList();
            moving.getCommentList().stream().forEach(p -> listComment.add(formatComment(p)));
            movingDto.setListComment(listComment);
        }
        return movingDto;
    }

    public CommentDto formatComment(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setContent(comment.getContent());
        commentDto.setCommentUserId(String.valueOf(comment.getCommentUser().getId()));
        commentDto.setUnCommentUserId(String.valueOf(comment.getUnCommentUser().getId()));
        commentDto.setMovingId(String.valueOf(comment.getMoving().getId()));
        return commentDto;
    }

    public ListResponseDto<CommentDto> publishComment(CommentDto commentDto) {
        ListResponseDto<CommentDto> listResponseDto = new ListResponseDto<>();
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setMoving(movingDao.findById(Moving.class, Long.parseLong(commentDto.getMovingId())));
        comment.setCommentUser(movingDao.findById(User.class, Long.parseLong(commentDto.getCommentUserId())));
        comment.setUnCommentUser(movingDao.findById(User.class, Long.parseLong(commentDto.getUnCommentUserId())));
        movingDao.persist(comment);
        List<CommentDto> commentDtoList = Lists.newArrayList();
        List<Comment> commentList = movingDao.findById(Moving.class, Long.parseLong(commentDto.getMovingId())).getCommentList();
        commentList.stream().forEach(p ->commentDtoList.add(formatComment(p)));
        listResponseDto.setObjs(commentDtoList);
        return listResponseDto;
    }

}
