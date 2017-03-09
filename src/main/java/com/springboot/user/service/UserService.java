package com.springboot.user.service;

import com.springboot.common.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zl on 2017/3/9.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private BaseDao baseDao;

    public  void save(){
        User user = new User();
        user.setPassword("111");
        user.setUsernaem("222");
        baseDao.persist(user);
    }
}
