package com.xjt.secunity.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xjt.MessageConstant;
import com.xjt.dao.MemberDao;
import com.xjt.dao.OrderDao;
import com.xjt.dao.OrdersettingDao;
import com.xjt.exception.MyException;
import com.xjt.pojo.Member;
import com.xjt.pojo.Order;
import com.xjt.pojo.OrderSetting;
import com.xjt.pojo.Result;
import com.xjt.secunity.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/17 下午 05:27
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceimpl implements OrderService {
   @Autowired
    private OrdersettingDao ordersettingDao;
   @Autowired
    private MemberDao memberDao;
   @Autowired
    private OrderDao orderDao;
    /**
     * 预约提交
     * @param orderInfo
     * @return
     */
    @Override
    public Result submit(Map  orderInfo) throws MyException {
        //判断用户是否可以预约 通过日期查询设置 调用OrderSettingDao
        String oederDate = (String) orderInfo.get("orderDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          //将oederDate转化为Date
        Date parse =null;
        try {
             parse = sdf.parse(oederDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new MyException("日期格式不正确");
        }
        //调用dao
      OrderSetting sss = ordersettingDao.findbyDate(parse);
        //不存在，报错不能预约
        if (sss == null){
            throw  new MyException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //存在：
        //    判断已预约人数是否>=可预约人数
        //    reservations >= number
        if (sss.getReservations()>=sss.getNumber()){
            //    true: 满，报错
            throw new MyException(MessageConstant.ORDER_FULL);
        }
            //    false: 未满，可预约
            //判断是否存在
        String telephone = (String) orderInfo.get("telephone");
        //通过手机号码查询
        Member byTelephone = memberDao.findByTelephone(telephone);
         if (byTelephone == null){
             //不存在，插入会员
             byTelephone = new Member();
             byTelephone.setName(((String) orderInfo.get("name")));
             byTelephone.setSex(((String) orderInfo.get("sex")));
             byTelephone.setPhoneNumber(telephone);
             byTelephone.setRegTime(new Date());
             memberDao.add(byTelephone);
         }
        //存在则取id是 为了给插入订单表数据用
            Integer id = byTelephone.getId();
            // 判断是否重复预约
            // 通过member_id,orderDate,packageId
            Order order = new Order();
        System.out.println("packageId======="+orderInfo.get("packageId"));
        String packageId1 = (String) orderInfo.get("packageId");
        int packageId =  Integer.parseInt(packageId1);
            order.setPackageId(packageId);
            order.setOrderDate(parse);
            order.setMemberId(id);
        List<Order> byCondition = orderDao.findByCondition(order);
        // 如果存在说明已经预约过这个套餐了，
        if (byCondition!=null && byCondition.size()>0){
            throw  new MyException(MessageConstant.HAS_ORDERED);
        }
        // 不存在才可以预约
            // 插入：
            // member_id 会员表来
            // orderDate 前端
            // orderType 从Controller中写死
        order.setOrderType(((String) orderInfo.get(Order.ORDERTYPE_WEIXIN)));
            // orderStatus 未到诊
        order.setOrderStatus(order.ORDERSTATUS_NO);
            // package_id 前端
        orderDao.add(order);
        //更新已预约数量,默认一次只能预约一个
        ordersettingDao.inReservations(sss.getId(),1);
        //返回结果
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    /**
     * 查询预约后的详情
     * @param id
     * @return
     */
    @Override
    public Member findById(int id) {
        return ordersettingDao.findById(id);
    }
}
