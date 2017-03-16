package com.springboot.common;

import com.springboot.Application;
import com.springboot.common.util.RegexUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zl on 2017/3/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RegexTest {

    @Test
    public  void RegexTest(){
        String number = "12344ssds";
        Pattern pattern = Pattern.compile(RegexUtil.NUMBER);
        Matcher matcher = pattern.matcher(number);
        System.out.println(matcher.matches());

        String email = "1679937491@qqq.com";
        Pattern pattern2 = Pattern.compile(RegexUtil.EMAIL);
        Matcher matcher2 = pattern2.matcher(email);
        System.out.println(matcher2.matches());

        String data = "2013-12-16";
        Pattern pattern3 = Pattern.compile(RegexUtil.DATA); //支持 - . /  例：2013-12-13 2015.12.23 2013/01/23
        Matcher matcher3 = pattern3.matcher(data);
        System.out.println(matcher3.matches());
    }

    /**
     * 获取随机整数 ，建议不要用 Math.random() 去放大取整
     */
    @Test
    public  void  mathRandomTest(){
        Random random = new Random();
        for (int i = 0 ;i <= 10; i++){
            System.out.println(random.nextInt(10)+1);
        }

    }


    /**
     * 获取当前纳秒数  精确
     */
    @Test
    public void  nanoTimeTest(){
        Long nanoTime = System.nanoTime();
        System.out.println(nanoTime);
    }
}
