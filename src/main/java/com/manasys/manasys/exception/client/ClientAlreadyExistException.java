package com.manasys.manasys.exception.client;

/**
 * 创建客户但客户信息已经存在时, 抛出该异常
 *
 * @author 刘洛松
 * @since 2025.6.29
 * @see ClientException
 */
public class ClientAlreadyExistException extends ClientException {

    public ClientAlreadyExistException() {
        super("该客户已经存在!");
    }

}
