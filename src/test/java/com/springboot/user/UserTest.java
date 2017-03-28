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
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;

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
        registerDto.setUsername("18345968280");
        registerDto.setPassword("111111");
        Boolean isSuccess =  userService.register(registerDto).getSuccess();
        System.out.println(isSuccess);
    }
    @Test
    public void updataUserInfoTest(){
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setSex("男");
        userDto.setNickName("殃殃");
        userDto.setUniversityName("hlj");
        MultipartFile multipartFile = null;
        Boolean isSuccess = userService.updataUserInfo(multipartFile,userDto).getSuccess();
        System.out.println(isSuccess);
    }

    @Test
    public void loginTest(){
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("13936754904");
        registerDto.setPassword("yangyang5214");
        Boolean isSuccess =  userService.login(registerDto).getSuccess();
    }

    @Test
    public void recordLoginTest(){
        userService.recordLogin("13936754904","yangyang5214");
    }

    @Test
    public void exportToMailbox(){
        userService.exportToMailbox("1679937491@qq.com");
    }

    @Test
    public void exportResumeTest(){
        userService.exportResume();
    }






}
