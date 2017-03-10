package com.springboot.user.service;

import com.springboot.common.dao.BaseDao;
import com.springboot.common.dto.ResponseDto;
import com.springboot.common.util.StringUtil;
import com.springboot.user.entity.User;
import com.springboot.user.ws.dto.RegisterDto;
import com.springboot.user.ws.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zl on 2017/3/9.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private BaseDao baseDao;

   public void register(RegisterDto registerDto){
       User user = new User();
       user.setUsernaem(registerDto.getUsername());
       user.setPassword(registerDto.getPassword());
       baseDao.persist(user);
   }

    public ResponseDto login(RegisterDto registerDto){
        ResponseDto responseDto = new ResponseDto();
        User user = baseDao.findById(User.class,registerDto.getUsername());
        if (!user.getPassword().equals(registerDto.getPassword())){
            responseDto.setSuccess(Boolean.FALSE);
            return responseDto;
        }
        responseDto.setObj(new UserDto(user));
        return responseDto;
    }

    public ResponseDto updataUserInfo(UserDto userDto){
        ResponseDto responseDto = new ResponseDto();
        if (StringUtil.isEmpty(userDto.getId().toString())){
            responseDto.setSuccess(Boolean.FALSE);
            return responseDto;
        }
        User user = baseDao.findById(User.class,userDto.getId());
        user.setUniversityName(userDto.getUniversityName());
        user.setNickName(userDto.getNickName());
        baseDao.persist(user);
        return  new ResponseDto();
    }
}
