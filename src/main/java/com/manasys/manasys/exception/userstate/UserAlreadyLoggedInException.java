package com.manasys.manasys.exception.userstate;

/**
 * 当用户已经登录导致无法登录时, 抛出该异常
 *
 * @author 刘洛松
 * @since 2025.6.26
 * @see UserStateException
 */
public class UserAlreadyLoggedInException extends UserStateException {

    public UserAlreadyLoggedInException(String username) {
        super("已有用户 \"" + username + "\" 登录! 请先登出!");
    }

}
