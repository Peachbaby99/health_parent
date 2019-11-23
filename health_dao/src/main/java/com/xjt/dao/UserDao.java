package com.xjt.dao;

import com.xjt.pojo.User;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/20 下午 04:45
 */
public interface UserDao {
    /**
     * 通过用户名获取登录用户信息
     * 权限
     * @param s
     * @return
     */
    User findbyusername(String s);
}
