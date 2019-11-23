package com.xjt.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xjt.MessageConstant;
import com.xjt.pojo.Result;
import com.xjt.secunity.MemberService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/21 下午 01:29
 */
/**
 * 统计报表
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;
    /**
     * 会员数量统计
     * @return
     */
 @GetMapping("/getMemberReport")
 public Result getMemberReport() {
     //生成十二个月数据
     //获取日历实例,当前时间
     Calendar instance = Calendar.getInstance();
     //过去12个月
     instance.add(Calendar.MONTH,-12);
     //便利十二个月,每次加一个月(为0)
     List<String> menths = new ArrayList<String>();
     //格式化格式
     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
     for (int i = 0; i < 12; i++) {
         instance.add(Calendar.MONTH,1);
         Date time = instance.getTime();
         menths.add(simpleDateFormat.format(time));
     }
     //调用业务服务

   List<Integer> list =  memberService.getMemberReport(menths);
    //封装数据,返回结果(前端的格式)
     Map<String,Object> stringObjectMap = new HashMap<String,Object>();
     stringObjectMap.put("months",menths);
     stringObjectMap.put("memberCount",list);

     return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,stringObjectMap);
 }

    /**
     *套餐占比统计
     * @return
     */
    @RequestMapping("/getPackageReport")
    public  Result getSetmealReport() {
        List<Map<String,Object>> list = memberService.getSetmealReport();
        Map<String,Object> map =new HashMap<>();
        map.put("setmealCount",list);
        List<String> setmealNames = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : list) {
            String name = (String) stringObjectMap.get("name");
            setmealNames.add(name);
        }
        map.put("setmealNames",setmealNames);
        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
    }

    /**
     * 获取运营统计数据
     * @return
     */
    @GetMapping("/getBusinessReportData")
    public  Result getBusinessReportData() {
        try {
            Map<String,Object> result =  memberService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    /**
     * 导出excel表格
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/exportBusinessReport")
    public  Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            //远程调用报表服务获取报表数据
            Map<String,Object> result = memberService.getBusinessReportData();
            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotPackage = (List<Map>) result.get("hotPackage");

            //获得Excel模板文件路径(Excel文件在template里面)
            //数值皆为索引(薄-行=列)
            String temlateRealPath= request.getSession().getServletContext().getRealPath("template")+ File.separator+"report_template.xlsx";
            //读取模板文件创建excel表格对像
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(temlateRealPath)));
            XSSFSheet sheet = workbook.getSheetAt(0);
            //第3行
            XSSFRow row = sheet.getRow(2);
            //第3行的第4列
            row.getCell(5).setCellValue(reportDate);//日期
            //第4行
            row = sheet.getRow(4);
            //第4行的第6列
            row.getCell(5).setCellValue(todayNewMember);//新增会员数(本日)
            //第4行的第8列
            row.getCell(7).setCellValue(totalMember);//总会员数
            //第6行
            row=sheet.getRow(5);
            //第6行的第6列
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            //第6行的第8列
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数
            //第8行
            row = sheet.getRow(7);
            //第8行的第6列
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            //第8行的第8列
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数
            //第9行
            row = sheet.getRow(8);
            //第8行的第6列
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            //第8行的第9列
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数
            //第10行
            row = sheet.getRow(9);
            //第10行的第6列
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            //第10行的第8列
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum =12;
            for (Map map : hotPackage) {
                String name = (String) map.get("name");
                Long package_count = (Long) map.get("package_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum ++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(package_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比(.doubleValue换为分数)
            }
            String filename ="运营报表.xlsx";
            //如果文件名为中文,打散,由ISO-8859-1组装
            filename= new String(filename.getBytes(),"ISO-8859-1");
            //通过输出流进行下载
            ServletOutputStream outputStream = response.getOutputStream();
            //写响应头,告诉游览器,响应的内容为文件
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition","attachment;filename="+filename);
            workbook.write(outputStream);


            outputStream.flush();
            outputStream.close();
            workbook.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL, null);
        }
    }
}
