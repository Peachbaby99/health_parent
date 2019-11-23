package com.xjt.secunity.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xjt.dao.Conllorcheckdao;

import com.xjt.pojo.CheckGroup;
import com.xjt.pojo.CheckItem;
import com.xjt.pojo.PageResult;
import com.xjt.pojo.QueryPageBean;

import com.xjt.secunity.ConllorcheckSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/12 21:47
 */
@Service(interfaceClass = ConllorcheckSevice.class)
@Transactional
public class ConllorcheckSeviceimpl implements ConllorcheckSevice {

    @Autowired
    private Conllorcheckdao conllorcheckdao;

    /**
     * 查询新增检查项信息
     *
     * @return
     */
    @Override
    public List<CheckItem> newcheck() {
        return conllorcheckdao.newcheck();
    }

    @Override
    //@Transactional//添加事务管理
    public void handleAdd(CheckGroup checkGroup, Integer[] integers) {
        //        先添加检查组信息
        conllorcheckdao.handleAdd1(checkGroup);
        //        获得添加检查组的id
        //select LAST_INSERT_ID()返回数据(id)checkGroup对象中
        Integer aid = checkGroup.getId();
        //遍历integers,循环插入检查组和检查项的关系
        if (integers != null) {
            for (Integer integer : integers) {
                conllorcheckdao.addcheckGroupAndintegers(aid, integer);
            }
        }
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageselete(QueryPageBean queryPageBean) {
        //判断queryPageBean.getQueryString()是否为空
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //分页查询工具
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckGroup> checkGroups =  conllorcheckdao.fenyechaxun(queryPageBean.getQueryString());
        //将查询结果封装为PageResult
        return new PageResult(checkGroups.getTotal(),checkGroups.getResult());
    }

    /**
     * 通过id查询检查组
     * @param id
     * @return
     */
    @Override
    public CheckGroup huixian1(Integer id) {
        return conllorcheckdao.huixian1(id);
    }

    /**
     * 回显
     * @return
     */

    @Override
    public List<CheckItem> sss() {
        return conllorcheckdao.sss();
    }

    /**
     * 通过检查组id查询对应选中的检查项
     * @param idss
     * @return
     */
    @Override
    public List<Integer> huixian3(Integer idss) {
        System.out.println("idss========================="+idss+"idss=========================");
        return  conllorcheckdao.huixian3(idss);
    }

    /**
     * 编辑提交
     * @param checkGroup
     * @param integers
     */
    @Override
    public void ddd(CheckGroup checkGroup, Integer[] integers) {
        //更新检查组
        conllorcheckdao.ddd(checkGroup);
        //删除旧的关系,组和项
        conllorcheckdao.deleteddd(checkGroup.getId());
        //添加心得关系
        if (null != integers){
            for (Integer integer : integers) {
                conllorcheckdao.addcheckGroupAndintegers(checkGroup.getId(),integer);
            }

        }
    }
}
