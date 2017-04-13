package com.springboot.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/13.
 */
public class DataUtil {

    public static long[] getDistanceTimes(Date date) {
        Date now = new Date();
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long time1 = date.getTime();
        long time2 = now.getTime();
        long diff;
        diff = time2 - time1;
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long[] times = {day, hour, min, sec};
        return times;
    }

    public static String  dateToString(Date date){
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return  df.format(date);
    }

    public static Date  stringToDate(String date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return  df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDate(Date date) {
        long[] longs = DataUtil.getDistanceTimes(date);
        if (longs[0] == 0) {
            if (longs[1] == 0){
                if (longs[2] ==0 ){
                    return String.valueOf(longs[3] + "秒前");

                }else{
                    return String.valueOf(longs[2] + "分钟前");
                }
            }else{
                return String.valueOf(longs[1] + "小时前");
            }
        } else {
            if (longs[0] <= 3) {
                return String.valueOf(longs[0] + "天前");
            } else {
                return DataUtil.dateToString(date);
            }
        }
    }
}
