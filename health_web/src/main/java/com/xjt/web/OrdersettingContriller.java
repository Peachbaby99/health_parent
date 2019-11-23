package com.xjt.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xjt.MessageConstant;
import com.xjt.pojo.OrderSetting;
import com.xjt.pojo.Result;
import com.xjt.secunity.OrdersettingSevice;
import com.xjt.util.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/15 下午 04:24
 */
@RestController
@RequestMapping("/ordersetting")
public class OrdersettingContriller {
    @Reference
    private OrdersettingSevice ordersettingSevice;

    @PostMapping("/upload")
    public Result main(@RequestParam("excelFile") MultipartFile excelFile) {
        try {
            //读取前端传多来的excel表格数据
            List<String[]> strings = POIUtils.readExcel(excelFile);
            List<OrderSetting> list = new ArrayList<OrderSetting>();
            //转为实体类
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            for (String[] string : strings) {
                OrderSetting os = new OrderSetting(simpleDateFormat.parse(string[0]), Integer.valueOf(string[1]));
                //日期和可预约数量
                list.add(os);
                //调用实体类添加导入
                ordersettingSevice.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }


    /**
     * 回显全部预约信息
     *
     * @param date
     * @return
     */
    @PostMapping("/select")
    public Result select(@RequestParam String date) {
        List<OrderSetting> list = ordersettingSevice.select(date);
        List<Map> listmap = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map map = new HashMap();
            map.put("date", orderSetting.getOrderDate().getDate());
            map.put("number", orderSetting.getNumber());
            map.put("reservations", orderSetting.getReservations());
            listmap.add(map);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS, listmap);
    }

    /**
     * 设置
     * @param shumiteDate
     * @return
     */
    @PostMapping("/shezhi")
    public Result shezhi(@RequestBody OrderSetting shumiteDate) {
        ordersettingSevice.shezhi(shumiteDate);
//        System.out.println(shumiteDate.getNumber() + "++++++" + shumiteDate.getOrderDate());
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}
