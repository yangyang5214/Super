package com.springboot.common;

import com.springboot.Application;
import com.springboot.user.service.CommonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhanglong on 2017/5/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class CityWeatherTest {


    @Autowired
    private CommonService commonService;

    @Test
    public  void inputCityCode(){
        String filePath = "C:\\Users\\lixi2000\\Desktop\\111.txt";
        commonService.readFile(filePath);
    }

    @Test
    public  void getWeatherByCity(){
        commonService.getWeatherByCity();
    }

}
