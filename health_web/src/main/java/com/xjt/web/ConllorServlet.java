package com.xjt.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xjt.MessageConstant;

import com.xjt.pojo.*;
import com.xjt.secunity.ConllorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/10 17:18
 */
@RestController//Rest写了就不用写@responbody
@RequestMapping("/user")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ConllorServlet {
    @Reference
    private ConllorService conllorService;

    /**
     * 增加
     * @param checkItem
     * @return
     */
    @PostMapping ("/add")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem) {
        System.out.println("==========="+checkItem.toString()+"===========");
        conllorService.add(checkItem);

        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/find")
    public  Result find(@RequestBody QueryPageBean queryPageBean) {
        PageResult checkItem = conllorService.find(queryPageBean);
        return  new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItem);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_DELETE')")
    public  Result delete(Integer id) {
        try {
            conllorService.delete(id);
        } catch (Exception e) {
            System.out.println("后台出错");
        }
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * 回显数据
     * @param id
     * @return
     */
    @GetMapping("/compile")
    public  Result compile(Integer id) {
        CheckItem checkItem =  conllorService.compile(id);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS,checkItem);
    }

    /**
     * 编辑表单提提交
     * @param checkItem
     * @return
     */
    @PostMapping("/update")
    public  Result update(@RequestBody CheckItem checkItem) {
        conllorService.update(checkItem);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }


}

