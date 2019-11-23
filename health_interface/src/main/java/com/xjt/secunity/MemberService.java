package com.xjt.secunity;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/21 下午 01:48
 */
public interface MemberService {
    /**
     * 会员数量折线图
     * @return
     */
    List<Integer> getMemberReport(List<String> menths);

    /**
     * 套餐占比统计
     * @return
     */
    List<Map<String, Object>> getSetmealReport();

    /**
     *获取运营统计数据
     * @return
     */
    Map<String, Object> getBusinessReportData() throws Exception;
}
