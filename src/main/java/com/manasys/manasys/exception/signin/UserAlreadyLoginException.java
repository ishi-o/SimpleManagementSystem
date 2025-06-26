package com.manasys.manasys.exception.signin;

/**
 * 当用户已经登录导致无法登录时, 抛出该异常
 *
 * @author 刘洛松
 * @since 2025.6.26
 * @see SignInException
 */
public class UserAlreadyLoginException extends SignInException {

    public UserAlreadyLoginException(String username) {
        super("用户 \"" + username + "\" 已登录!");
    }

}
