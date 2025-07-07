package com.mooncompany.manasys.exception.userstate;

/**
 * 用户已经在异地设备登录时, 抛出该异常
 *
 * @author 刘洛松
 * @since 2025.6.28
 * @see UserStateException
 */
public class UserAlreadyLoggedInByOthersException extends UserStateException {

    public UserAlreadyLoggedInByOthersException(String username) {
        super("用户 \"" + username + "\" 已在其它设备上登录!");
    }
}
