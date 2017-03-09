package com.springboot.user;

import com.springboot.Application;
import com.springboot.user.service.LoginService;
import com.springboot.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * Created by zl on 2017/3/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class UserTest {

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @Test
    public void saveTest(){
        userService.save();
    }

    @Test
    public void loginTest(){
        loginService.saveNamePwd();
    }
}
