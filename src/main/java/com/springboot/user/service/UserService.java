package com.springboot.user.service;

import com.springboot.common.dao.BaseDao;
import com.springboot.common.dto.ResponseDto;
import com.springboot.common.util.StringUtil;
import com.springboot.user.entity.User;
import com.springboot.user.ws.dto.RegisterDto;
import com.springboot.user.ws.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by zl on 2017/3/9.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private BaseDao baseDao;

   public ResponseDto register(RegisterDto registerDto){
       ResponseDto responseDto = new ResponseDto();
       User user = baseDao.find(User.class,"USERNAME",registerDto.getUsername());
       if (nonNull(user)){
           responseDto.setSuccess(Boolean.FALSE);
           responseDto.setMessage("用户已存在！");
           return responseDto;
       }
       user = new User();
       user.setUsernaem(registerDto.getUsername());
       user.setPassword(registerDto.getPassword());
       baseDao.persist(user);
       return new ResponseDto();
   }



    public ResponseDto login(RegisterDto registerDto){
        ResponseDto responseDto = new ResponseDto();
        User user = baseDao.find(User.class,"USERNAME",registerDto.getUsername());
        if (isNull(user)){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("用户未注册！");
            return responseDto;
        }
        if (!user.getPassword().equals(registerDto.getPassword())){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("用户名或密码错误！");
            return responseDto;
        }
        responseDto.setObj(new UserDto(user));
        return responseDto;
    }

    public ResponseDto updataUserInfo(UserDto userDto){
        ResponseDto responseDto = new ResponseDto();
        if (StringUtil.isEmpty(userDto.getId().toString())){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("用户ID不存在！");
            return responseDto;
        }
        User user = baseDao.findById(User.class,userDto.getId());
        if (isNull(user)){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("不存在此用户！");
            return responseDto;
        }
        user.setUniversityName(userDto.getUniversityName());
        user.setNickName(userDto.getNickName());
        user.setSex(userDto.getSex());
        user.setArea(userDto.getArea());
        user.setSignature(userDto.getSignature());
        baseDao.persist(user);
        return  new ResponseDto();
    }
}
