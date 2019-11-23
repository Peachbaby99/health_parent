package com.xjt.job;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/15 下午 03:07
 */
public class Jobmian {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:springjob.xml");

    }
}
