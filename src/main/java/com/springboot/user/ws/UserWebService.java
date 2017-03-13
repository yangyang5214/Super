package com.springboot.user.ws;

import com.springboot.common.dto.ResponseDto;
import com.springboot.user.service.UserService;
import com.springboot.user.ws.dto.RegisterDto;
import com.springboot.user.ws.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by zl on 2017/3/9.
 */
@Controller
@ResponseBody
@RequestMapping("/user")
public class UserWebService {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public ResponseDto register(@NotNull RegisterDto registerDto){
        userService.register(registerDto);
        return new ResponseDto();
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ResponseDto login(@NotNull RegisterDto registerDto){
        userService.login(registerDto);
        return new ResponseDto();
    }

    @RequestMapping(value = "/updata/userInfo",method = RequestMethod.GET)
    public ResponseDto updataUserInfo(@NotNull UserDto userDto){
        return userService.updataUserInfo(userDto);
    }

    @RequestMapping(value = "/export/userInfo",method = RequestMethod.GET)
    public Response exportUserInfo(){
        return Response.ok(userService.exportUserMessage()).
                header("Content-Disposition","attachment; filename=\"user" +DateTimeFormatter.
                ofPattern("yyyy-MM-dd HH:mm:ss").
                format(LocalDateTime.now()) + ".xls\"").
                build();
    }


}
