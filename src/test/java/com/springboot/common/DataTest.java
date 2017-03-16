package com.springboot.common;

import com.springboot.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.time.LocalDateTime;

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
}
