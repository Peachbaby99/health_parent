package com.xjt.web;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description:
 * @author: 向某人
 * @date:11/14/2019 7:23 PM
 */
public class fun01 {
    @Autowired
   private JedisPool jedisPool;


    @Test
    public  void ss() {
        Jedis resource = jedisPool.getResource();
        System.out.println(resource.get("jian"));
    }
    /**
     * 读取表格
     * @throws Exception
     */
    @Test
    public  void sss( ) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\shinelon\\Desktop\\hello.xlsx");
        XSSFSheet sheetAt = workbook.getSheetAt(0);
        for (Row cells : sheetAt) {
            for (Cell cell : cells) {
                System.out.println(cell);
            }
            System.out.println("=================");
        }
        workbook.close();
    }
    //使用POI可以在内存中创建一个Excel文件并将数据写入到这个文件，最后通过输出流将内存中的Excel文件下载到磁盘
    @Test
    public  void fun02() throws Exception {
//        工作簿对象
      XSSFWorkbook workbook = new XSSFWorkbook();
//      表对象
      XSSFSheet xssfSheetsheet = workbook.createSheet("学生名单");
        //行
        XSSFRow row = xssfSheetsheet.createRow(0);
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("性别");
        row.createCell(2).setCellValue("地址");

        XSSFRow row02 = xssfSheetsheet.createRow(1);
        row02.createCell(0).setCellValue("张三");
        row02.createCell(1).setCellValue("男");
        row02.createCell(2).setCellValue("深圳");

        XSSFRow row03 = xssfSheetsheet.createRow(2);
        row03.createCell(0).setCellValue("李四");
        row03.createCell(1).setCellValue("男");
        row03.createCell(2).setCellValue("北京");

        //存入磁盘
        FileOutputStream os = new FileOutputStream("D:\\student.xlsx");
        workbook.write(os);
        os.flush();
        os.close();
    }


}
