package com.xjt.secunity;

import com.xjt.pojo.CheckGroup;
import com.xjt.pojo.CheckItem;
import com.xjt.pojo.PageResult;
import com.xjt.pojo.QueryPageBean;

import java.util.List;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/12 20:41
 */

public interface ConllorcheckSevice {

    /**
     * 查询新增检查项信息
     * @return
     */
    List<CheckItem> newcheck();

    /**
     * 新增检查组和检查项信息
     * @param checkGroup
     * @param integers
     */
    void handleAdd(CheckGroup checkGroup, Integer[] integers);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult pageselete(QueryPageBean queryPageBean);

    /**
     * 通过id查询检查组
     * @param id
     * @return
     */
    CheckGroup huixian1(Integer id);

    /**
     * 回显
     * @return
     */
    List<CheckItem>  sss();
    /**
     * 通过检查组id查询对应选中的检查项
     * @param idss
     * @return
     */
    List<Integer> huixian3(Integer idss);

    /**
     * 编辑提交
     * @param checkGroup
     * @param integers
     */

    void ddd(CheckGroup checkGroup, Integer[] integers);
}
