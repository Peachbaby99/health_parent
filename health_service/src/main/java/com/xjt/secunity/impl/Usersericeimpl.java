package com.xjt.secunity.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xjt.dao.UserDao;
import com.xjt.pojo.User;
import com.xjt.secunity.UserService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/20 下午 04:28
 */
@Service(interfaceClass = UserService.class)
public class Usersericeimpl implements UserService {
    @Autowired
    private UserDao userDao;

//    @Autowired
//    private RoleDao roleDao;
    @Override
    public User findbyusername(String s) {

       User user = userDao.findbyusername(s);
//        //2.如果用户存在
//       if (user != null){
//           //3.查询用户所拥有的角色
//       }
//
//        //4.查询每个角色下的权限
        return user;
    }
}
