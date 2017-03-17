package com.springboot.user.dao;

import com.springboot.common.dao.BaseDao;
import com.springboot.user.ws.dto.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by zl on 2017/3/13.
 */
@Repository
public class UserDao extends BaseDao {

    public List<Object[]> queryUserMessageForExport(){
        String jpql="select u.username,u.password from User u";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }
}
