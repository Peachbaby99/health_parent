package com.xjt.job;

import com.xjt.constant.RedisConstant;
import com.xjt.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/15 下午 02:51
 */
public class CleanImgjob {
    @Autowired
    private JedisPool jedisPool;
    /**
     * 清理图片
     */
    public void cleaImg(){
        Jedis resource = jedisPool.getResource();
        //获取redis两个集合的差集,所有的减去保存到数据库的,
            //第一个为所有的,第二个为保存到数据库的   一减二
            //sdiff为需要删除的图片集合
        Set<String> sdiff = resource.sdiff(RedisConstant.PACKAGE_PIC_RESOURCES, RedisConstant.PACKAGE_PIC_DB_RESOURCES);
        //调用七牛删除
        if (null != sdiff){
            //把set集合转成字符串数组
            String[] strings = sdiff.toArray(new String []{});
            QiNiuUtil.removeFiles();
        }
        //删除两个key
        resource.del(RedisConstant.PACKAGE_PIC_RESOURCES,RedisConstant.PACKAGE_PIC_RESOURCES);

    }
}
