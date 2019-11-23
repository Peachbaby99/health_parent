package com.xjt.secunity;

import com.xjt.pojo.CheckGroup;
import com.xjt.pojo.Package;
import com.xjt.pojo.PageResult;
import com.xjt.pojo.QueryPageBean;

import java.util.List;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/13 17:53
 */
public interface PachagService {

    /**
     * 文件上传,查询检查组信息
     */
    List<CheckGroup> select();

    /**
     * 新增检查套餐
     * @param xinxi
     * @param integers
     */
    void add(Package xinxi, Integer[] integers);

    /**
     * 查询全部套餐数据
     * @param qqq
     * @return
     */
    PageResult findall(QueryPageBean qqq);

    /**
     * 查询所有套餐
     * @return
     */
    List<Package> getPackage();

    /**
     * 查询套餐详情
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
