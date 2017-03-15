package com.springboot.common;

import com.springboot.Application;
import com.springboot.moving.service.MovingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by zl on 2017/3/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class FileTest {

    @Autowired
    private MovingService movingService;

    @Test
    public void saveImageTest(){
        File file = new File("C:\\Users\\lixi2000\\Desktop\\521300e0a1904.jpg");
        try {
            String imageUrl = movingService.saveImage(new FileInputStream(file));
            System.out.println(imageUrl);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
