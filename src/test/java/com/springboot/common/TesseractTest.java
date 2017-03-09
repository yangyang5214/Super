package com.springboot.common;

import com.springboot.common.util.OCRHelp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * Created by zl on 2017/3/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TesseractTest {


    @Test
    public void getCode(){
        File file = new File("D:\\temp\\imageCode\\code-1b51f425-51ff-4c0d-a343-a9bac2511e95.png");
        String code = null;
        try {
            code = new OCRHelp().recognizeText(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(code);
    }
}
