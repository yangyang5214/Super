package com.springboot.common;

import com.springboot.Application;
import com.springboot.common.util.OCRUtil;
import com.springboot.user.service.TesseractService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * Created by zl on 2017/3/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TesseractTest {


    @Autowired
    public TesseractService tesseractService;

    @Test
    public void getCode() {
        File file = new File("D:\\temp\\imageCode\\code-1b51f425-51ff-4c0d-a343-a9bac2511e95.png");
        String code = null;
        try {
            code = new OCRUtil().recognizeText(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(code);
    }

    @Test
    public void saveNamePwdTest() {
        System.out.println(tesseractService.saveNamePwd("20134091122", "z167993_"));
    }
}
