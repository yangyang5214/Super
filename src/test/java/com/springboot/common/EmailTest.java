package com.springboot.common;

import com.springboot.Application;
import com.springboot.common.util.EmailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class EmailTest {

    @Autowired
    public EmailUtil emailUtil;

    @Test
    public void sendEmail() {
        List<String> filePathList = new ArrayList<>();
        filePathList.add("D:\\temp\\imageCode\\code-b35c37a1-19d5-4b42-b75e-b6fbb5005bbc.png");
        filePathList.add("D:\\temp\\haikou\\0a35fb22-a70e-4436-bb3d-3dc8f1c4cb40.xls");
        emailUtil.sendEmail("1679937491@qq.com", "通知", "hello",filePathList);
    }


}
