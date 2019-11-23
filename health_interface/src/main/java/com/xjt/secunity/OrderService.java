package com.xjt.secunity;

import com.xjt.exception.MyException;
import com.xjt.pojo.Member;
import com.xjt.pojo.Result;

import java.util.Map;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/17 下午 05:27
 */
public interface OrderService {
    /**
     * 预约提交
     * @param orderInfo
     * @return
     */
    Result submit(Map<String, Object> orderInfo) throws MyException;

    /**
     * 查询预约后的详情
     * @param id
     * @return
     */
    Member findById(int id);
}
