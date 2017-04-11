package com.springboot.moving;

/**
 * Created by zl on 2017/3/14.
 */

import com.springboot.Application;
import com.springboot.find.service.MovingService;
import com.springboot.find.ws.dto.CommentDto;
import com.springboot.find.ws.dto.MovingDto;
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
        movingDto.setUserId(1 + "");
        movingDto.setContent("使用hibernate的e-r映射pojo类的时候遇到org.hibernate.AnnotationException: No identifier specified");
        movingDto.setPosition("beijing");
//        movingDto.setMovingType(0);
        MultipartFile multipartFile = null;
//        try {
//            movingService.publishMoving(multipartFile);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public  void listMovingTest(){
        movingService.listMoving(1,5);
    }

    @Test
    public  void listBeautyTest(){
        movingService.listBeauty(1,5);
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
