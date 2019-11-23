package com.xjt.secunity;

import com.xjt.pojo.Member;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/19 下午 03:59
 */
public interface LoginSevice {
    /**
     *通过手机号账号是否存在
     * @param telephone
     * @return
     */
    Member check(String telephone);

    /**
     * 添加
     * @param member
     */
    void add(Member member);
}
