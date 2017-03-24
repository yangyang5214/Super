package com.springboot.user.ws;

import com.springboot.common.dto.ResponseDto;
import com.springboot.common.filter.BaseWebService;
import com.springboot.user.service.UserService;
import com.springboot.user.ws.dto.RegisterDto;
import com.springboot.user.ws.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Created by zl on 2017/3/9.
 */
@Controller
@ResponseBody
@RequestMapping("/user")
public class UserWebService extends BaseWebService {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseDto register(@NotNull RegisterDto registerDto){
        return userService.register(registerDto);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseDto login(@NotNull RegisterDto registerDto){
        return userService.login(registerDto);
    }

    @RequestMapping(value = "/updata/userInfo",method = RequestMethod.POST)
    public ResponseDto updataUserInfo(
            @RequestParam("file") MultipartFile avatarFile,
            @NotNull UserDto userDto){
        return userService.updataUserInfo(avatarFile,userDto);
    }

    @RequestMapping(value = "/export/email",method = RequestMethod.GET)
    public ResponseDto exportToMailbox(@NotNull String email){
        userService.exportToMailbox(email);
        return new ResponseDto();
    }


    @RequestMapping(value = "/record/login",method = RequestMethod.GET)
    public ResponseDto recordLogin(String username,String password){
        return userService.recordLogin(username,password);
    }
}
