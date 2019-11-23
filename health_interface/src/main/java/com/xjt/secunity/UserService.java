package com.xjt.secunity;

import com.xjt.pojo.User;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/20 下午 04:27
 */
public interface UserService {
    /**
     * 认证与授权(获取用户权限信息)
     * @param s
     * @return
     */
    User findbyusername(String s);
}
