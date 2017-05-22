package com.springboot.user.service;

import com.springboot.common.dao.BaseDao;
import com.springboot.common.entity.CityWeather;
import com.springboot.common.util.HttpUtil;
import com.springboot.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by zhanglong on 2017/5/22.
 */
@Service
@Transactional
public class CommonService {


    //  http://www.weather.com.cn/data/sk/101010100.html    获取天气
    @Autowired
    private BaseDao baseDao;

    public void readFile(String filePath) {
        try {
            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    System.out.println(lineTxt);
                    if (StringUtil.isNotEmpty(lineTxt)){
                        String[] city = lineTxt.split("=");
                        CityWeather cityWeather = new CityWeather();
                        cityWeather.setCode(city[0]);
                        cityWeather.setName(city[1]);
                        baseDao.persist(cityWeather);
                    }
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

    public void getWeatherByCity(){
        String path = "http://www.weather.com.cn/data/sk/101130608.html";
        System.out.println(HttpUtil.callServiceByGet(path));
    }
}
