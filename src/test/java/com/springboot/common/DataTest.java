package com.springboot.common;

import com.springboot.Application;
import com.springboot.common.util.DataUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.crypto.Data;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by zl on 2017/3/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DataTest {


    @Test
    public void InstantTest(){
        Instant instant = Instant.now();
        System.out.println(instant.getEpochSecond()); //获取秒
        System.out.println(instant);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        System.out.println(localDateTime.toLocalDate()); //获取年月日  例：2013-05-03
    }

    @Test
    public void dataTest(){
        Instant instant = Instant.now();
        System.out.println(instant.toString());

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        Date data = new Date();
        System.out.println(data.toString());
        long[] log = DataUtil.getDistanceTimes(data);
        System.out.println(log);

        System.out.println(DataUtil.formatDate(DataUtil.stringToDate("2017-04-13 12:12:59")));
    }
}
