package com.xjt.secunity.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xjt.dao.MemberDao;
import com.xjt.dao.OrderDao;
import com.xjt.secunity.MemberService;
import com.xjt.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/21 下午 01:49
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceimpl implements MemberService {
   @Autowired
   private MemberDao memberDao;
   @Autowired
   private OrderDao orderDao;
    /**
     * 会员数量折线图
     * @return
     */
    @Override
    public List<Integer> getMemberReport(List<String> menths) {
       List<Integer> men = new ArrayList<Integer>();
        for (String menth : menths) {
            //dao统计查询
            menth =menth+"31";
          Integer ss=  memberDao.findMemberCountBeforeDate(menth);
            men.add(ss);
        }
        return men;
    }

    /**
     * 套餐占比统计
     * @return
     */
    @Override
    public List<Map<String, Object>> getSetmealReport() {
        return memberDao.getSetmealReport();
    }

    /**
     * 获取运营统计数据
     * Map数据格式：
     *      todayNewMember -> number
     *      totalMember -> number
     *      thisWeekNewMember -> number
     *      thisMonthNewMember -> number
     *      todayOrderNumber -> number
     *      todayVisitsNumber -> number
     *      thisWeekOrderNumber -> number
     *      thisWeekVisitsNumber -> number
     *      thisMonthOrderNumber -> number
     *      thisMonthVisitsNumber -> number
     *      hotSetmeals -> List<Setmeal>
     * @return
     */
    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
       //获得当前日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        //获得本周一的日期
        String thisweekmonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //获得本周星期天的日期
        String thisWeekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek(),"yyyy-MM-dd");
        //获得本月第一天的日期
        String firstDayOfThisMonth =DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        //获得本月最后一天的日期
        String lastofThisMoth = DateUtils.parseDate2String(DateUtils.getLastDayOfThisMonth());
        //今日新增会员数
        Integer todayNewMember = memberDao.findMemberCountAfterDate(today);
        //总会员数
        Integer totalMember = memberDao.findMemberTotalCount();
        //本周新增会员数
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisweekmonday);
        //本月新增会员数
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDayOfThisMonth);
        //今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);
        //本周预约数
        Integer thisWeekOrderNumber = orderDao.findOrderCountBetweenDate(thisweekmonday,thisWeekSunday);
        //本月预约数
        Integer thisMonOrderNumber = orderDao.findOrderCountBetweenDate(firstDayOfThisMonth,lastofThisMoth);
        //今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);
        //本周到诊数
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(thisweekmonday);
        //本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDayOfThisMonth);
        //热门套餐(前四)
        List<Map> hotPackage = orderDao.findHotPackage();

        Map<String,Object> result = new HashMap<>();
        result.put("reportDate",today);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);

        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);
        result.put("todayOrderNumber",todayOrderNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisMonthOrderNumber",thisMonOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        result.put("hotPackage",hotPackage);
        return result;
    }
}
