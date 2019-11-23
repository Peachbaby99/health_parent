package com.xjt.secunity.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xjt.pojo.CheckItem;
import com.xjt.pojo.PageResult;
import com.xjt.pojo.QueryPageBean;
import com.xjt.secunity.ConllorService;
import com.xjt.dao.Conllordao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/10 17:43
 */
@Service(interfaceClass = ConllorService.class)
public class ConllorServiceimpl implements ConllorService {
    @Autowired
    private Conllordao conllordao;

    /**
     * 增加
     *
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {

        /**
         * 新增
         */
        conllordao.add(checkItem);
    }

    /**
     * 分页查询
     *
     * @param
     * @return
     */
    @Override
    public PageResult find(QueryPageBean queryPageBean) {
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //queryPageBean.getCurrentPage(), 当前页码
        // queryPageBean.getPageSize(),总页数
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //查询
        Page<CheckItem> sss = conllordao.find(queryPageBean.getQueryString());
        //封装PageResult
        PageResult pageResult = new PageResult(sss.getTotal(), sss.getResult());
        return pageResult;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Integer id) throws RuntimeException {
        long i = conllordao.findis(id);
        //查询当前检查项是否和检查组关联
        if (i > 0) {
            //当前检查项被引用，不能删除
            throw new RuntimeException("当前检查项被引用，不能删除");
        }
        conllordao.delete(id);
    }

    /**
     * 回显数据
     *
     * @param id
     * @return
     */
    @Override
    public CheckItem compile(Integer id) {
        return conllordao.compile(id);
    }

    /**
     * 编辑表单提交
     *
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem) {
        conllordao.update(checkItem);
    }


}
