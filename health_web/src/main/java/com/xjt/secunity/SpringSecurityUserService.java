package com.xjt.secunity;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xjt.pojo.Permission;
import com.xjt.pojo.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/20 下午 04:10
 */
@Service("userService")//进入容器
//@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //1.通过名称查询用户
        com.xjt.pojo.User user = userService.findbyusername(s);
        //2.如果用户存在
        if (user != null) {
            //3.把所有的角色与权限都给这个用户授权
            List<GrantedAuthority> list =new ArrayList<GrantedAuthority>();
            SimpleGrantedAuthority simpleGrantedAuthority = null;
            //获得角色集合
            Set<Role> roles = user.getRoles();
            if (roles != null){
                for (Role role : roles) {
                    //授予角色
                    simpleGrantedAuthority = new SimpleGrantedAuthority(role.getKeyword());
                    list.add(simpleGrantedAuthority);
                    //授予权限
                    if (role.getPermissions()!=null){
                        //角色下的权限
                        for (Permission permission : role.getPermissions()) {
                            simpleGrantedAuthority = new SimpleGrantedAuthority(permission.getKeyword());
                            list.add(simpleGrantedAuthority);
                        }
                    }
                }
            }
            return new User(s, user.getPassword(), list);
        }
        return null;
    }
}
