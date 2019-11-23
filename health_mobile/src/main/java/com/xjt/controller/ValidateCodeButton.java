package com.xjt.controller;

import com.aliyuncs.exceptions.ClientException;
import com.xjt.MessageConstant;
import com.xjt.constant.RedisMessageConstant;
import com.xjt.pojo.Result;
import com.xjt.util.SMSUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/18 上午 10:50
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeButton {
    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/send4Order")
    public Result send4Order(String telephone) {

        //        - 判断是否重复发送(从redis中获取)
        Jedis resource = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_ORDER + telephone;
        if (resource.get(key) != null) {
            //        - 发送过，返回注意查收
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        //        - 没发送过，
        //        生成验证码，
        String s = RandomStringUtils.randomNumeric(6);
        //        调用SmsUtil发送，
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, s);
            //        保存到redis中，有效期为5分钟
            resource.setex(key, 60 * 60, s);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    /**
     * 登录验证码
     *
     * @param telephone
     * @return
     */
    @PostMapping("/send4Login")
    public Result send4Login(String telephone) {

        //        - 判断是否重复发送(从redis中获取)
        Jedis resource = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_LOGIN + telephone;
        if (resource.get(key) != null) {
            //        - 发送过，返回注意查收
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        //        - 没发送过，
        //        生成验证码，
        String s = RandomStringUtils.randomNumeric(6);
        //        调用SmsUtil发送，
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, s);
            //        保存到redis中，有效期为60分钟
            resource.setex(key, 60 * 60, s);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

}
