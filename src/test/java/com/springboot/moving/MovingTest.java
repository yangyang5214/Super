package com.springboot.moving;

/**
 * Created by zl on 2017/3/14.
 */

import com.springboot.Application;
import com.springboot.moving.service.MovingService;
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
        File file = new File("C:\\Users\\lixi2000\\Desktop\\QQ图片20170314093510.png");
        Long userid = 1L;
        MovingDto movingDto = new MovingDto();
        movingDto.setContent("啦啦啦啦啦");
        movingDto.setPosition("shanghai");
        MultipartFile multipartFile = null;
        movingService.publishMoving(multipartFile,movingDto,userid);
    }
}
