package com.xjt.secunity;


import com.xjt.pojo.CheckItem;
import com.xjt.pojo.PageResult;
import com.xjt.pojo.QueryPageBean;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/10 17:29
 */
public interface ConllorService {
    /**
     * 新增
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询
     * @param
     * @return
     */
    PageResult find(QueryPageBean queryPageBean);

    /**
     * 删除一项
     * @param id
     */
    void delete(Integer id);

    /**
     * 回显数据
     * @param id
     * @return
     */
    CheckItem compile(Integer id);

    /**
     * 编辑表单提交
     * @param checkItem
     */
    void update(CheckItem checkItem);
}
