package com.xjt.exception;

/**
 *
 * @user: Eric
 * @date: 2019/11/10
 * @description:  自定义异常：终止已知不符合业务逻辑的继续执行
 */
public class MyException extends RuntimeException {
    public MyException(String message){
        super(message);
    }
}
