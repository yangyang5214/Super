package com.springboot.moving.service;

import com.springboot.common.dao.BaseDao;
import com.springboot.common.dto.ResponseDto;
import com.springboot.common.util.FastDFSUtil;
import com.springboot.common.util.StringUtil;
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
import java.util.UUID;

/**
 * Created by zl on 2017/3/10.
 */
@Service
@Transactional
public class MovingService {

    @Autowired
    private BaseDao baseDao;

    @Value("${image.position:}")
    private String imagePosition;

    @Value("${image.url:}")
    private String imageUrl;

    public ResponseDto publishMoving(MultipartFile file, MovingDto movingDto, Long userId) {
        Moving moving = new Moving();
        if (null == file) {
            moving.setContent(movingDto.getContent());
            moving.setUser(baseDao.findById(User.class, userId));
            moving.setPosition(StringUtil.isEmpty(movingDto.getPosition()) ? "" : movingDto.getPosition());
        } else {
            try {
                moving.setImageUrl(saveImage(file.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        baseDao.persist(moving);
        return new ResponseDto();
    }

    public String saveImage(InputStream fileInputStream) {
        String imgId = UUID.randomUUID().toString();
        String fileName = imgId + ".png";
        String path = imagePosition + "/" + fileName;
        FastDFSUtil.savePic(fileInputStream, fileName, imagePosition);
        return imageUrl + fileName;
    }

    public ResponseDto listMoving() {
        return null;
    }
}
