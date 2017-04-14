package com.springboot.find.dao;

import com.springboot.common.dao.BaseDao;
import com.springboot.find.entity.Beauty;
import com.springboot.find.entity.Market;
import com.springboot.find.entity.Moving;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by zl on 2017/3/14.
 */
@Repository
public class MovingDao extends BaseDao {

    public List<Moving> listMoving(int start,int size){
        String jpql="select m from Moving m order by m.creationTime desc ";
        Query query = em.createQuery(jpql);
        query.setMaxResults(size);
        query.setFirstResult(start);
        return query.getResultList();
    }

    public List<Beauty> listBeauty(int start, int size){
        String jpql="select b from Beauty b order by b.creationTime desc ";
        Query query = em.createQuery(jpql);
        query.setMaxResults(size);
        query.setFirstResult(start);
        return query.getResultList();
    }

    public List<Market> listMarket(int start, int size){
        String jpql="select b from Market b order by b.creationTime desc ";
        Query query = em.createQuery(jpql);
        query.setMaxResults(size);
        query.setFirstResult(start);
        return query.getResultList();
    }
}
