package com.xjt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xjt.MessageConstant;
import com.xjt.constant.RedisMessageConstant;
import com.xjt.pojo.Member;
import com.xjt.pojo.Result;
import com.xjt.secunity.LoginSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/19 下午 03:53
 */
@RestController
@RequestMapping("/login")
public class LoginContrllor {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private LoginSevice loginSevice;
    @PostMapping("/check")
    public Result check(@RequestBody Map<String,String> loginInfo, HttpServletResponse response) {
        //校验验证码
        //        - 判断是否重复发送(从redis中获取)
        Jedis resource = jedisPool.getResource();
        //获取手机号码
        String telephone = loginInfo.get("telephone");
        //判断是否一致
        String key = RedisMessageConstant.SENDTYPE_LOGIN+telephone;
        String s = resource.get(key);
        if (s == null){
            //        - 发送过，返回注意查收
            return new Result(false, "点击获取验证码");
        }
        //判断是否一致(redis当中)
        String validateCode = loginInfo.get("validateCode");
        if (!s.equals(validateCode)){
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }


        // 判断账号是否存在
      Member member = loginSevice.check(telephone);
        //不存在则添加
        if (member == null){
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            loginSevice.add(member);
        }
        //用户行踪跟踪,cookie
        //希望每当用户访问网站时,提交用户得手机号码
        Cookie cookie = new Cookie("login_member_telephone",telephone);
        cookie.setMaxAge(60*60*24*30);//有效期为一个月
        cookie.setPath("/");//符合路径时就会带上cookie上的值,游览器访问我们的网站时
        response.addCookie(cookie);//游览器访问时就会带上这个值
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}
