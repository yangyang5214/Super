package com.springboot.common;

import com.springboot.Application;
import com.springboot.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2017/3/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class PoiTest {


    @Autowired
    private UserService userService;

    @Test
    public  void uploadUserMessageTest(){
        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\ZhangLong\\user.xlsx");
            userService.uploadUserMessage(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void exportUserMessageTest(){
        userService.exportUserMessage();
    }


}
