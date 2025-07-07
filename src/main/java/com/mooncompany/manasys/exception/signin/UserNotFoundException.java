package com.mooncompany.manasys.exception.signin;

/**
 * 用户不存在导致登录失败时, 抛出该异常
 *
 * @author 刘洛松
 * @since 2025.6.25
 * @see SignInException
 */
public class UserNotFoundException extends SignInException {

    public UserNotFoundException(String username) {
        super("用户 \"" + username + "\" 不存在!");
    }

}
