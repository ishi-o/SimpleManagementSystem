package com.mooncompany.manasys.exception.client;

/**
 * 客户不存在时, 抛出该异常
 *
 * @author 刘洛松
 * @since 2025.6.29
 * @see ClientException
 */
public class ClientNotFoundException extends ClientException {

    public ClientNotFoundException() {
        super("不存在该客户的信息! 请先登记!");
    }

}
