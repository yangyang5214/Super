package com.springboot.common;

import com.springboot.Application;
import com.springboot.common.util.RegexUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
}
