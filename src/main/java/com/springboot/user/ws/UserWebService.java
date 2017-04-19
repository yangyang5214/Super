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

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public ResponseDto register(String username,String password){
        return userService.register(username,password);
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ResponseDto login(String username,String password){
        return userService.login(username,password);
    }

    @RequestMapping(value = "/find/data",method = RequestMethod.GET)
    public ResponseDto getUserFindData(long userId){
        return userService.getUserFindData(userId);
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

    @RequestMapping(value = "/register/code/",method = RequestMethod.GET)
    public ResponseDto registerForCode(String code,String email){
        return userService.registerForCode(code,email);
    }




    @RequestMapping(value = "/send/resume",method = RequestMethod.POST)
    public ResponseDto recordLogin(){
         userService.exportResume();
         return new ResponseDto();
    }
}
