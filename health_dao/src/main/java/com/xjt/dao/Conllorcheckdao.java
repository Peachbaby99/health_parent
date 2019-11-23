package com.xjt.dao;

import com.github.pagehelper.Page;
import com.xjt.pojo.CheckGroup;
import com.xjt.pojo.CheckItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/12 21:50
 */
@Repository
public interface Conllorcheckdao {

    /**
     * 查询新增检查项信息
     * @return
     */
    List<CheckItem> newcheck();

    /**
     * 添加检查组信息
     * @param checkGroup
     */
    void handleAdd1(CheckGroup checkGroup);

    /**
     * 获得新增检查组的id
     * @return
     */
    int getid();

    /**
     * 插入检查组额检查项的关系
     * @param aid
     * @param integer
     */
    void addcheckGroupAndintegers(@Param("aid") Integer aid,@Param("integer") Integer integer);

    /**
     * 检查组分页查询
     * @param queryPageBean
     * @return
     */
    Page<CheckGroup> fenyechaxun(String queryPageBean);

    /**
     * 通过id查询检查组
     * @param id
     * @return
     */
    CheckGroup huixian1(Integer id);

    /**
     * 通过检查组id查询对应选中的检查项
     * @param idss
     * @return
     */
    List<Integer> huixian3(Integer idss);

    /**
     * 回显数据
     * @return
     */
    List<CheckItem> sss();

    /**
     * 更新编辑提交检查组
     * @param checkGroup
     * @param
     */
    void ddd(CheckGroup checkGroup);

    /**
     * 删除组和项关系
     * @param id
     */
    void deleteddd(Integer id);
}
