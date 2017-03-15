package com.springboot.moving.service;

import com.google.common.collect.Lists;
import com.springboot.common.dto.ListResponseDto;
import com.springboot.common.dto.ResponseDto;
import com.springboot.common.util.FastDFSUtil;
import com.springboot.moving.dao.MovingDao;
import com.springboot.moving.entity.Comment;
import com.springboot.moving.entity.Moving;
import com.springboot.moving.ws.dto.CommentDto;
import com.springboot.moving.ws.dto.MovingDto;
import com.springboot.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by zl on 2017/3/10.
 */
@Service
@Transactional
public class MovingService {

    @Autowired
    private MovingDao movingDao;


    @Value("${image.save.position:}")
    private String imagePosition;

    @Value("${image.url:}")
    private String imageUrl;

    public ResponseDto publishMoving(MultipartFile file, MovingDto movingDto) {
        Moving moving = new Moving();
        if (null == file) {
            moving.setContent(movingDto.getContent());
            moving.setUser(movingDao.findById(User.class, movingDto.getUserId()));
            moving.setPosition(movingDto.getPosition());
        } else {
            try {
                moving.setImageUrl(saveImage(file.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        movingDao.persist(moving);
        return new ResponseDto();
    }

    public String saveImage(InputStream fileInputStream) {
        String imgId = UUID.randomUUID().toString();
        String fileName = imgId + ".png";
        String filePath = imagePosition + "/" + fileName;
        FastDFSUtil.savePic(fileInputStream, fileName, imagePosition);
        double imageSize = new File(filePath).length()/1024.0/1024.0;
        if (imageSize > 1){   //文件大于1M进行压缩
            String zipFileName = imgId + "-1.png";
            String zipFilePath = imagePosition + "/" + zipFileName;
            FastDFSUtil.zipImageFile(filePath,zipFilePath,1000,1);
            return imageUrl + zipFileName;
        }
        return imageUrl + fileName;
    }

    public ListResponseDto<MovingDto> listMoving(int offset,int size) {
        ListResponseDto<MovingDto> listResponseDto = new ListResponseDto<>();
        List<MovingDto> listMovingDto = Lists.newArrayList();
        List<Moving> listMoving = movingDao.listMoving(offset*size-1,size);
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
        movingDto.setListComment(moving.getCommentList());
        return movingDto;
    }

    public  ListResponseDto<Comment> publishComment(CommentDto commentDto){
        ListResponseDto<Comment> listResponseDto = new ListResponseDto<>();
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setMoving(movingDao.findById(Moving.class,Long.parseLong(commentDto.getMovingId())));
        comment.setCommentUser(movingDao.findById(User.class,Long.parseLong(commentDto.getCommentUserId())));
        comment.setUnCommentUser(movingDao.findById(User.class,Long.parseLong(commentDto.getUnCommentUserId())));
        movingDao.persist(comment);
        listResponseDto.setObjs(movingDao.findById(Moving.class,Long.parseLong(commentDto.getMovingId())).getCommentList());
        return listResponseDto;
    }

}
