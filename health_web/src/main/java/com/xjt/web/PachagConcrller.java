package com.xjt.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xjt.MessageConstant;
import com.xjt.pojo.*;
import com.xjt.pojo.Package;
import com.xjt.secunity.PachagService;
import com.xjt.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/13 15:08
 */
@RestController
@RequestMapping("/package")
public class PachagConcrller {

    @Autowired
    private JedisPool jedisPool;
    @Reference
    private   PachagService pachagService;
    /**
     * 图片上传
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile) {
        //1.去后缀名
        String originalFilename = imgFile.getOriginalFilename();
        //截取字符串获得扩展名 (.jpg)
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));//截取'.'点后面以及.
        //2.生成唯一名
        String uuid = UUID.randomUUID().toString()+substring;//UUID
        //3.调用七牛上传
        try {
            QiNiuUtil.uploadViaByte(imgFile.getBytes(),uuid);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        //保存到redis中
        //key集合的key
        //members 元素
//jedisPool.getResource().sadd(RedisConstant.PACKAGE_PIC_RESOURCES,uuid);

//图片回显数据
        Map<String,String> map = new HashMap<String, String>();
        //图片域名
        map.put("domain",QiNiuUtil.DOMAIN);
        //图片名
        map.put("imgName",uuid);
        //4.返回结果
        return  new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,map);
    }

    /**
     * 查询回显检查项信息
     * @return
     */
    @GetMapping("/select")
    public Result select() {
           List<CheckGroup> checkGroups =  pachagService.select();
       return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroups);
    }

    /**
     * 新增检查套餐
     * @param
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public  Result add(@RequestBody Package s,Integer[] checkgroupIds) {
        try {
            pachagService.add(s,checkgroupIds);
//           jedisPool.getResource().sadd(RedisConstant.PACKAGE_PIC_DB_RESOURCES,s.getImg());
             return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    /**
     * dataList分页查询
     * @return
     */
    @PostMapping("/sss")
    public @ResponseBody Result findall(@RequestBody QueryPageBean qqq ) {
        PageResult results = pachagService.findall(qqq);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,results);

    }


}
