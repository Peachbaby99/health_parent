package com.xjt.dao;


import com.github.pagehelper.Page;
import com.xjt.pojo.CheckGroup;
import com.xjt.pojo.CheckItem;
import com.xjt.pojo.QueryPageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/10 17:46
 */
public interface Conllordao {
    /**
     * 增加
     * @param checkItem
     */
    void add(CheckItem checkItem);
    /**
     * 分页查询
     * @param
     * @return
     */
    Page<CheckItem> find(String queryString);


    /**
     * 查看是否能删除
     * @param
     * @return
     */
    long findis(@Param("ids") Integer ids);
    /**
     * 删除一项
     * @param id
     */
    void delete(@Param("id") Integer id);

    /**
     * 回显数据
     * @param id
     * @return
     */
    CheckItem compile(@Param("id") Integer id);
    /**
     * 编辑表单提交
     * @param checkItem
     */
    void update(CheckItem checkItem);

}
