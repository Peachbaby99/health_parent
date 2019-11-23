package com.xjt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xjt.MessageConstant;
import com.xjt.constant.RedisMessageConstant;
import com.xjt.pojo.Member;
import com.xjt.pojo.Order;
import com.xjt.pojo.Result;
import com.xjt.secunity.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/17 下午 05:08
 */
@RestController
@RequestMapping("/order")
public class OrderContrllor {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    /**
     * 提交预约
     *
     * @param orderInfo
     * @return
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map orderInfo) {
        Jedis jedis = jedisPool.getResource();
        //获取手机号码
        String telephone = (String) orderInfo.get("telephone");
        //   验证码验证,把提交过来的验证码和存在Redis当中的比较
        String key = RedisMessageConstant.SENDTYPE_ORDER + telephone;
        //redis中存的验证码
        String s = jedis.get(key);
        if (s == null) {
            return new Result(false, "请点击获取验证码");
        }
//        比较是否一致
        if (!s.equals((String) orderInfo.get("validateCode"))) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //设置预约的方式,当下的工程师微信公众号的,所以通过这个controller所预约的订单都是都是微信的
        orderInfo.put("orderType", Order.ORDERTYPE_WEIXIN);
        //调用业务服务
        Result result = orderService.submit(orderInfo);
        return result;
    }

    /**
     * 查询预约成功的后的画面
     *
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id) {
        Member member = orderService.findById(id);
        return new Result(true, MessageConstant.ORDER_SUCCESS, member);
    }
}
