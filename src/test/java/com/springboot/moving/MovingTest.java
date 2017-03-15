package com.springboot.moving;

/**
 * Created by zl on 2017/3/14.
 */

import com.springboot.Application;
import com.springboot.moving.service.MovingService;
import com.springboot.moving.ws.dto.CommentDto;
import com.springboot.moving.ws.dto.MovingDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MovingTest {

    @Autowired
    private MovingService movingService;
    @Test
    public void  publishMovingTest(){
        File file = new File("C:\\Users\\lixi2000\\Desktop\\timg.jpg");
        MovingDto movingDto = new MovingDto();
        movingDto.setContent("qqq");
        movingDto.setPosition("shanghai");
        MultipartFile multipartFile = null;
        movingService.publishMoving(multipartFile,movingDto);
    }

    @Test
    public  void listMoving(){
        movingService.listMoving(0,10);
    }

    @Test
    public  void  publishComment(){
        CommentDto commentDto = new CommentDto();
        commentDto.setMovingId(1+"");
        commentDto.setContent("我是评论");
        commentDto.setCommentUserId("2");
        commentDto.setUnCommentUserId("1");
        movingService.publishComment(commentDto);
    }


}
