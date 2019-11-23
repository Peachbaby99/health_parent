package com.xjt.dao;

import com.xjt.pojo.Member;
import com.xjt.pojo.OrderSetting;
import com.xjt.pojo.ShumiteDate;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/15 下午 07:13
 */
public interface OrdersettingDao {

    /**
     * 通过日期查询预约设置信息是否存在
     * @param orderDate
     * @return
     */
    OrderSetting findbyDate(Date orderDate);

    /**
     * 添加
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 更新可预约数量
     * @param orderSetting
     */
    void update(OrderSetting orderSetting);

    /**
     * 回显全部预约信息
     * @param a
     * @param b
     * @return
     */
    List<OrderSetting> select(@Param("a") String a,@Param("b") String b);

    /***
     * 查看是否存在
     * @param
     * @return
     */

    OrderSetting cx(String s);
    /**
     * 添加
     * @param
     * @param
     */
    void tj(OrderSetting shumiteDate);

    /**
     * 更改
     * @param
     * @param
     */
    void up(OrderSetting shumiteDate);
    /**
     * 增加已预约数量
     */
    void  inReservations(@Param("id") int id,@Param("num") int num);

    /**
     * 查询预约完成后的页面
     * @param id
     * @return
     */
    Member findById(int id);
}
