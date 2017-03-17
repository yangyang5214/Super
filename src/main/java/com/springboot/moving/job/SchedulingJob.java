package com.springboot.moving.job;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by zl on 2017/3/17.
 */
@Configuration
@EnableScheduling // 启用定时任务
public class SchedulingJob {


    @Scheduled(cron = "0/20 * * * * ?") // 每20秒执行一次
    public void scheduler() {
        System.out.println("-------------------");
    }

}
