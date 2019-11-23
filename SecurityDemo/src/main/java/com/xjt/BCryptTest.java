package com.xjt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/20 下午 03:08
 */
public class BCryptTest {
    public static void main(String[] args) {
        //创建一个加密器
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("admin"));
    }
}
