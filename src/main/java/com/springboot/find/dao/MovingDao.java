package com.springboot.find.dao;

import com.springboot.common.dao.BaseDao;
import com.springboot.find.entity.Beauty;
import com.springboot.find.entity.Market;
import com.springboot.find.entity.Moving;
import com.springboot.find.entity.WeiXin;
import com.springboot.find.ws.dto.WeiXinDto;
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

    public List<WeiXin> listWeiXin(){
        String jpql="select weixin from WeiXin weixin order by weixin.creationTime desc ";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    public long getBeaytyUserData(long userId){
        String jpql="select count(beauty.id) from Beauty beauty where beauty.user.id =:userId";
        Query query = em.createQuery(jpql);
        query.setParameter("userId",userId);
        return (long) query.getSingleResult();
    }

    public long getMovingUserData(long userId){
        String jpql="select count(moving.id) from Moving moving where moving.user.id =:userId";
        Query query = em.createQuery(jpql);
        query.setParameter("userId",userId);
        return (long) query.getSingleResult();
    }

    public long getMarketUserData(long userId){
        String jpql="select count(market.id) from Market market where market.user.id =:userId";
        Query query = em.createQuery(jpql);
        query.setParameter("userId",userId);
        return (long) query.getSingleResult();
    }
}
