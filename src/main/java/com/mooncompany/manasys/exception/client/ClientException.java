package com.mooncompany.manasys.exception.client;

/**
 * 客户异常类, 所有因客户状态发生的异常均继承该类
 *
 * @author 刘洛松
 * @since 2025.6.29
 * @see RuntimeException
 */
public class ClientException extends RuntimeException {

    public ClientException(String msg) {
        super("客户异常: " + msg);
    }

}
