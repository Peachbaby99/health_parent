package com.xjt.secunity.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xjt.dao.OrdersettingDao;
import com.xjt.pojo.OrderSetting;
import com.xjt.secunity.OrdersettingSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/15 下午 05:54
 */
@Service(interfaceClass = OrdersettingSevice.class)
public class OrdersettingSeviceimpl implements OrdersettingSevice {
    @Autowired
    private OrdersettingDao ordersettingDao;

    /**
     * 批量导入预约设置
     *
     * @param list
     */
    @Override
    @Transactional
    public void add(List<OrderSetting> list) {
        //- 通过日期查询预约设置表，看数据是否存在
        //- 存在则调用Dao新number数量
        //- 不存在则调用Dao插入新记录到t_ordersetting
        //循环list
        for (OrderSetting orderSetting : list) {
            //防止重复,重复,通过日期查询预约设置信息是否存在
            OrderSetting osso = ordersettingDao.findbyDate(orderSetting.getOrderDate());
            if (osso == null) {
                //不存在,可以插入
                ordersettingDao.add(orderSetting);
            }else {
                //存在则跟新
                ordersettingDao.update(orderSetting);
            }
        }

    }



    /**
     * 回显全部预约信息
     * @param date
     * @return
     */
    @Override
    public List<OrderSetting> select(String date) {
        String A =date+"-01";
        String B =date+"-31";
        return ordersettingDao.select(A,B);
    }

    /**
     * 设置预约
     * @param shumiteDate
     */
    @Override
    public void shezhi(OrderSetting shumiteDate) {
        OrderSetting orderSetting = ordersettingDao.cx(shumiteDate.getOrderDate().getYear()+"-"+shumiteDate.getOrderDate().getMonth()+"-"+shumiteDate.getOrderDate().getDay());
//        判断是否为空
        System.out.println(orderSetting);
        if (orderSetting == null){
            ordersettingDao.tj(shumiteDate);
        }else {
            ordersettingDao.up(shumiteDate);
        }
    }
}
