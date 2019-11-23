package com.xjt.secunity;

import com.xjt.pojo.OrderSetting;

import java.util.List;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/15 下午 04:28
 */
public interface OrdersettingSevice {
    /**
     *批量导入预约设置
     * @param list
     */
    void add(List<OrderSetting> list);

    /**
     * 回显全部预约信息
     * @param date
     * @return
     */
    List<OrderSetting> select(String date);

    /**
     * 设置预定
     * @param shumiteDate
     */
    void shezhi(OrderSetting shumiteDate);
}
