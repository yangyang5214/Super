package com.springboot.moving.dao;

import com.springboot.common.dao.BaseDao;
import com.springboot.moving.entity.Moving;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by zl on 2017/3/14.
 */
@Repository
public class MovingDao extends BaseDao {

    public List<Moving> listMoving(int start,int size){
        String jpql="from Moving m order by m.creationTime desc ";
        Query query = em.createQuery(jpql);
        query.setMaxResults(size);
        query.setFirstResult(start);
        return query.getResultList();
    }
}
