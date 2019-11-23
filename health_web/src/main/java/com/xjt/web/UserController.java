package com.xjt.web;

import com.xjt.MessageConstant;
import com.xjt.pojo.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/21 下午 01:03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/getUsername")
    public Result getUsername() {
        //SecurityContextHolder持有则,一般用于获取缓存中的内容
        //获取Security容器中存放的信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //得到用户名
        System.out.println(authentication.getName());
       User user = (User) authentication.getPrincipal();
      return new  Result(true, MessageConstant.GET_USERNAME_SUCCESS,user);
    }
}
