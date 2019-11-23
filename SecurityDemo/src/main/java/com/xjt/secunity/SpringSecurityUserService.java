package com.xjt.secunity;

import com.xjt.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/19 下午 08:20
 */
//这个实现类必须进入容器,security通过getBean("userService",UserDetailsService.class)
@Service(value = ("userService"))
public class SpringSecurityUserService implements UserDetailsService {
    /**
     * security框架会调用这和方法来实现对用户登陆认证与授权
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库查询
        User user = findbyUsernameindb(username);
        if (user != null) {
            //授权
            //String username,String password
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            //GrantedAuthority权限角色信息
            SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ROLE_ADMIN");
            //权限给予
            authorities.add(sga);
            org.springframework.security.core.userdetails.User user1 =
                    //通过认证成功,反之失败
                    new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
            return user1;
        }
        return null;
    }

    /**
     * 模拟数据库查询
     *
     * @param name
     * @return
     */

    private User findbyUsernameindb(String name) {
        if ("admin".equals(name)) {
            User user = new User();
            user.setUsername(name);
            //adminc$2a$10$XHFG07YE1k6sOHuv1rNvsORZ14M4XyNhiEFEicnNqAE.h15JDHCgK
            user.setPassword("$2a$10$XHFG07YE1k6sOHuv1rNvsORZ14M4XyNhiEFEicnNqAE.h15JDHCgK");
            return user;
        } else {
            return null;
        }

    }
}
