package com.springboot.common;

import com.springboot.Application;
import com.springboot.common.util.ImageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhanglong on 2017/5/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ImageUtilTest {


    @Test
    public void imageFormatUtilTest(){
        try {
            ImageUtil.imageFormatChange(new File("C:\\Users\\lixi2000\\Desktop\\111.jpg"),"C:\\Users\\lixi2000\\Desktop\\222.png","png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void imageSizeUtilTest(){
        try {
            ImageUtil.imageSizeChange(new File("C:\\Users\\lixi2000\\Desktop\\111.jpg"),"C:\\Users\\lixi2000\\Desktop\\222.jpg",2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
