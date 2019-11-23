package com.xjt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xjt.MessageConstant;
import com.xjt.pojo.Package;
import com.xjt.pojo.Result;
import com.xjt.secunity.PachagService;

import com.xjt.util.QiNiuUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/16 下午 04:57
 */
@RestController
@RequestMapping("/package")
public class MobileConcriller {
    @Reference
    private PachagService pachagService;

    @GetMapping("/getPackage")
    public Result getPackage() {
        //调用业务查询所有
        List<Package> list = pachagService.getPackage();
        //拼接图片的完整路径
        for (Package aPackage : list) {
            aPackage.setImg(QiNiuUtil.DOMAIN + "/" + aPackage.getImg());
        }
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, list);
    }

    /**
     * 套餐详情
     *
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id) {
        Package pkg = pachagService.findById(id);
        pkg.setImg(QiNiuUtil.DOMAIN + "/" + pkg.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, pkg);
    }

    /**
     * 点击预约
     *
     * @param id
     * @return
     */
    @GetMapping("/findByIds")
    public Result findByIds(int id) {
        //调用业务查询详情
        Package pss = pachagService.findByIds(id);
        //拼接图片的路径
        pss.setImg(QiNiuUtil.DOMAIN + "/" + pss.getImg());
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, pss);
    }
}
