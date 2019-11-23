package com.xjt.secunity.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xjt.dao.Pachagdao;
import com.xjt.pojo.CheckGroup;
import com.xjt.pojo.Package;
import com.xjt.pojo.PageResult;
import com.xjt.pojo.QueryPageBean;
import com.xjt.secunity.PachagService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/13 17:54
 */
@Service(interfaceClass = PachagService.class)
public class PachagServiceimpl implements PachagService {
    @Autowired
    private Pachagdao pachagdao;
    /**
     * 查询检查组信息
     */
    @Override
    public List<CheckGroup> select() {
        return pachagdao.select();
    }

    /**
     * 新增检查套餐
     *
     * @param xinxi
     * @param integers
     */
    @Override
    public void add(Package xinxi, Integer[] integers) {
        //1.增加检查套餐
        pachagdao.add(xinxi);
        //2.获得检查套餐的id
        Integer id = xinxi.getId();
        //3.添加检查套餐和检查组的关系
        if (integers != null && integers.length > 0) {
            for (Integer integer : integers) {
                pachagdao.addtaocanAndzu(id, integer);
            }

        }

    }

    /**
     * 查询套餐全部信息
     * @param qqq
     * @return
     */
    @Override
    public PageResult findall(QueryPageBean qqq) {
        if (qqq.getQueryString()!=null && qqq.getQueryString() !=""){
       qqq.setQueryString("%"+qqq.getQueryString()+"%");
        }
        PageHelper.startPage(qqq.getCurrentPage(),qqq.getPageSize());
       Page<Package> sss = pachagdao.findall(qqq.getQueryString());
        return new PageResult(sss.getTotal(),sss.getResult()) ;
    }

    /**
     * 查询所有套餐
     * @return
     */
    @Override
    public List<Package> getPackage() {
        return pachagdao.getPackage();
    }

    /**
     * 查询套餐信息
     * @param id
     * @return
     */
    @Override
    public Package findById(int id) {
        return pachagdao.findById(id);
    }

    /**
     * 查询预约详情
     * @param id
     * @return
     */
    @Override
    public Package findByIds(int id) {

        return pachagdao.findByIds(id);
    }
}
