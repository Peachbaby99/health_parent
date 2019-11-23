package com.xjt.secunity.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xjt.dao.MemberDao;
import com.xjt.pojo.Member;
import com.xjt.secunity.LoginSevice;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/19 下午 04:21
 */
@Service(interfaceClass = LoginSevice.class)
public class LoginSeviceimpl implements LoginSevice {
   @Autowired
   private MemberDao memberDao;


    /**
     * 通过手机号账号是否存在
     * @param telephone
     * @return
     */
    @Override
    public Member check(String telephone) {
        return memberDao.findByTelephone(telephone) ;
    }

    /**
     * 添加
     * @param member
     */
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
