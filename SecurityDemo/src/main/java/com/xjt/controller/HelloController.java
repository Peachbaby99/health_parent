package com.xjt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping("/add")
    //给方法标定权限,要访问这个方法登录的用户,必须有这个权限
    @PreAuthorize("hasAnyAuthority('add')")//表示用户必须拥有add权限才能调用当前方法
    public String add(){
        System.out.println("add...");
        return null;
    }

    @RequestMapping("/delete")
    //给方法标定权限,要访问这个方法登录的用户,必须有这个角色
    @PreAuthorize("hasRole('ROLE_ADMIN')")//表示用户必须拥有ROLE_ADMIN角色才能调用当前方法
    public String delete(){
        System.out.println("delete...");
        return null;
    }
}