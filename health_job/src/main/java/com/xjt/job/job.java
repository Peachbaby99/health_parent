package com.xjt.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisPool;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/14 12:59
 */
public class job {
    @Autowired
    private JedisPool jedisPool;
    public void sss(){

        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(time.format(new Date()));
    }
}
