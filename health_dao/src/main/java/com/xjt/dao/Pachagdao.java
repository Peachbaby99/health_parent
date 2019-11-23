package com.xjt.dao;

import com.github.pagehelper.Page;
import com.xjt.pojo.CheckGroup;
import com.xjt.pojo.Package;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/13 18:44
 */
public interface Pachagdao {
    /**
     * 查询检查项信息
     * @return
     */
    List<CheckGroup> select();

    /**
     * 增加检查套餐
     * @param xinxi
     */
    void add(Package xinxi);

    /**
     * 增加检查组和套餐的关系
     * @param id
     * @param integer
     */
    void addtaocanAndzu(@Param("id") Integer id,@Param("integer") Integer integer);

    /**
     * 套餐查询
     * @param queryString
     * @return
     */
    Page<Package> findall(String queryString);

    /**
     * 查询所有套餐
     * @return
     */
    List<Package> getPackage();

    /**
     * 查询套餐信息
     * @param id
     * @return
     */
    Package findById(int id);

    /**
     * 查询预约详情
     * @param id
     * @return
     */
    Package findByIds(int id);
}
