package com.springboot.moving.service;

import com.google.common.collect.Lists;
import com.springboot.common.dto.ListResponseDto;
import com.springboot.common.dto.ResponseDto;
import com.springboot.common.util.FastDFSUtil;
import com.springboot.moving.dao.MovingDao;
import com.springboot.moving.entity.Moving;
import com.springboot.moving.ws.dto.MovingDto;
import com.springboot.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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


    @Value("${image.position:}")
    private String imagePosition;

    @Value("${image.url:}")
    private String imageUrl;

    public ResponseDto publishMoving(MultipartFile file, MovingDto movingDto, Long userId) {
        Moving moving = new Moving();
        if (null == file) {
            moving.setContent(movingDto.getContent());
            moving.setUser(movingDao.findById(User.class, userId));
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
        String path = imagePosition + "/" + fileName;
        FastDFSUtil.savePic(fileInputStream, fileName, imagePosition);
        return imageUrl + fileName;
    }

    public ListResponseDto<MovingDto> listMoving() {
        ListResponseDto<MovingDto> listResponseDto = new ListResponseDto<>();
        List<MovingDto> listMovingDto = Lists.newArrayList();
        List<Moving> listMoving = movingDao.listMoving();
        listMoving.stream().forEach(p -> listMovingDto.add(formatMoving(p)));
        listResponseDto.setObjs(listMovingDto);
        return listResponseDto;
    }

    public MovingDto formatMoving(Moving moving) {
        MovingDto movingDto = new MovingDto();
        movingDto.setAvatar(moving.getUser().getAvatar());
        movingDto.setUserid(String.valueOf(moving.getUser().getId()));
        movingDto.setUserName(moving.getUser().getUsername());
        movingDto.setContent(moving.getContent());
        movingDto.setImageUrl(moving.getImageUrl());
        movingDto.setPublishTime(String.valueOf(moving.getCreationTime()));
        movingDto.setPosition(moving.getPosition());
        return movingDto;
    }
}
