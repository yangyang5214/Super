package com.springboot.user;

import com.springboot.Application;
import com.springboot.user.service.UserService;
import com.springboot.user.ws.dto.RegisterDto;
import com.springboot.user.ws.dto.UserDto;
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


    @Test
    public void registerTest(){
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("13936754903");
        registerDto.setPassword("yangyang5214");
        userService.register(registerDto);
    }
    @Test
    public void updataUserInfoTest(){
        UserDto userDto = new UserDto();
        userDto.setId(3L);
        userDto.setSex("男");
        userDto.setNickName("殃殃");
        userDto.setUniversityName("hlj");
        userService.updataUserInfo(userDto);
    }
}
