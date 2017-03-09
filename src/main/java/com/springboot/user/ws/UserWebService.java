package com.springboot.user.ws;

import com.springboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zl on 2017/3/9.
 */
@Controller
@RequestMapping("/user")
public class UserWebService {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public void save(){
        userService.save();
    }
}
