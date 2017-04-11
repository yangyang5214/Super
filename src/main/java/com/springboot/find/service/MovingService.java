package com.springboot.find.service;

import com.google.common.collect.Lists;
import com.springboot.common.dto.ListResponseDto;
import com.springboot.common.dto.ResponseDto;
import com.springboot.common.util.Collections3;
import com.springboot.common.util.FastDFSUtil;
import com.springboot.common.util.StringUtil;
import com.springboot.find.dao.MovingDao;
import com.springboot.find.entity.Beauty;
import com.springboot.find.entity.Comment;
import com.springboot.find.ws.dto.BeautyDto;
import com.springboot.find.ws.dto.CommentDto;
import com.springboot.find.entity.Moving;
import com.springboot.find.ws.dto.MovingDto;
import com.springboot.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zl on 2017/3/10.
 */
@Service
@Transactional
public class MovingService {

    @Value("${ip:}")
    private String ip;

    @Autowired
    private MovingDao movingDao;

    @Autowired
    private FastDFSUtil fastDFSUtil;

    public ResponseDto publishMoving(List<MultipartFile> file) throws UnsupportedEncodingException {
        MovingDto movingDto = new MovingDto();
        movingDto.setContent("11");
        movingDto.setMovingType(1);
        movingDto.setPosition("11");
        movingDto.setUserId("1");
        Moving moving = new Moving();
        if (null != file) {
            StringBuffer imageUrls = new StringBuffer();
            try {
                for (int i = 0; i < file.size(); i++) {
                    imageUrls.append(fastDFSUtil.saveImage(file.get(i).getInputStream()));
                    imageUrls.append(",");
                }
                String imageUrl = imageUrls.toString();
                moving.setImageUrl(imageUrl.substring(0,imageUrl.length()-1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        moving.setContent(URLDecoder.decode(movingDto.getContent(), "utf-8"));
        moving.setUser(movingDao.findById(User.class, Long.parseLong(movingDto.getUserId())));
        moving.setPosition(movingDto.getPosition());
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

    public ListResponseDto<BeautyDto> listBeauty(int offset, int size) {
        ListResponseDto<BeautyDto> listResponseDto = new ListResponseDto<>();
        List<BeautyDto> listBeautyDto = Lists.newArrayList();
        List<Beauty> listMoving = movingDao.listBeauty((offset - 1) * size, size);
        listMoving.stream().forEach(p -> listBeautyDto.add(formatBeauty(p)));
        listResponseDto.setObjs(listBeautyDto);
        return listResponseDto;
    }

    public BeautyDto formatBeauty(Beauty beauty) {
        BeautyDto BeautyDto = new BeautyDto();
        BeautyDto.setContent(beauty.getContent());
        BeautyDto.setImageUrl(ip + beauty.getImageUrl());
        BeautyDto.setPublishTime(beauty.getCreationTime().toString());
        BeautyDto.setAvatarUrl(beauty.getUser().getAvatarUrl());
        BeautyDto.setUserId(String.valueOf(beauty.getUser().getId()));
        BeautyDto.setUserName(beauty.getUser().getUsername());
        return BeautyDto;
    }

    public MovingDto formatMoving(Moving moving) {
        MovingDto movingDto = new MovingDto();
        movingDto.setAvatarUrl(moving.getUser().getAvatarUrl());
        movingDto.setUserId(String.valueOf(moving.getUser().getId()));
        movingDto.setUserName(moving.getUser().getUsername());
        movingDto.setContent(moving.getContent());
        if (StringUtil.isNotEmpty(moving.getImageUrl())){
            List<String> imageUrl = Arrays.asList(moving.getImageUrl().split(","));
            movingDto.setImageUrl(imageUrl);
        }
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
        commentList.stream().forEach(p -> commentDtoList.add(formatComment(p)));
        listResponseDto.setObjs(commentDtoList);
        return listResponseDto;
    }

}
