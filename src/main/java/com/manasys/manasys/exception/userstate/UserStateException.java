package com.manasys.manasys.exception.userstate;

/**
 * 用户状态异常时, 抛出该异常
 *
 * @author 刘洛松
 * @since 2025.6.28
 * @see RuntimeException
 */
public class UserStateException extends RuntimeException {

    public UserStateException(String msg) {
        super("用户状态异常: " + msg);
    }

}
