package com.xjt.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xjt.MessageConstant;
import com.xjt.pojo.*;
import com.xjt.secunity.ConllorcheckSevice;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/12 20:34
 */
@RestController//Rest写了就不用写@responbody
@RequestMapping("/check")
public class ConllorcheckSevlet {
    @Reference
    private ConllorcheckSevice conllorcheckSevice;

    /**
     * 查询新增检查组基本信息数据
     * @param
     */
    @GetMapping("/newcheck")
    public Result newcheck() {
        List<CheckItem> checkItems = conllorcheckSevice.newcheck();
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS,checkItems);
    }

    /**
     * 新增检查组和检查项信息
     * @param
     * @param integers
     * @return
     */
    @PostMapping("/handleAdd")
    public  Result handleAdd(@RequestBody CheckGroup ss,@RequestBody Integer[] integers ) {
        try {
            conllorcheckSevice.handleAdd(ss, integers);
            return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant. ADD_CHECKGROUP_FAIL);
        }
    }



    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/pageselete")
    public PageResult pageselete(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult =  conllorcheckSevice.pageselete(queryPageBean);
        return pageResult;
    }

    /**
     * 通过id查询检查组
     * @param id
     * @return
     */
    @GetMapping("/huixian1")
    public Result huixian1(int id) {
        CheckGroup checkGroup = conllorcheckSevice.huixian1(id);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
     *回显表单
     */
    @GetMapping("/sss")
    public  Result sss() {
        List<CheckItem> checkItem =  conllorcheckSevice.sss();
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItem);
    }



    /**
     * 通过检查组id查询对应选中的检查项
     * @param id
     * @return
     */
    @GetMapping("/huixian3")
    public  Result huixian3(Integer id) {
        System.out.println("==========================="+id);
        List<Integer> integers = conllorcheckSevice.huixian3(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,integers);
    }
    /**
     *编辑提交
     */
    @PostMapping("/ddd")
    public  Result ddd(@RequestBody CheckGroup checkGroup, Integer[] integers) {
        conllorcheckSevice.ddd(checkGroup,integers);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
}
