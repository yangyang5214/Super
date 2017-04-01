package com.springboot.common;

import com.google.common.collect.Lists;
import com.springboot.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Administrator on 2017/3/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ListTest {

    @Test
    public void ListTest(){
        List<String> list = Lists.newArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        List<String> list2 = Lists.newArrayList();
        list2.add("1");
        list2.add("5");
        list2.add("8");
        list2.add("4");
        list2.add("9");
        list2.add("9");

        //求并集
//        list.retainAll(list2);
//        System.out.println(list);  //[1, 4]
//        System.out.println(list2);  //[1, 5, 8, 4, 9]

//        //求合集
//        list.addAll(list2);
//        System.out.println(list);  //[1, 2, 3, 4, 1, 5, 8, 4, 9]
//        System.out.println(list2);  //[1, 5, 8, 4, 9]

//        //求差集
//        list.removeAll(list2);
//        System.out.println(list);  //[2, 3]
//        System.out.println(list2);  //[1, 5, 8, 4, 9]

        //去重
        Set<String> set = new HashSet<>();
        set.addAll(list2);
        System.out.println(set);
    }
}
