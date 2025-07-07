package com.mooncompany.manasys.exception.userstate;

/**
 * 用户未登录时, 抛出该异常
 *
 * @author 刘洛松
 * @since 2025.6.29
 * @see UserStateException
 */
public class UserNotLoggedInException extends UserStateException {

    public UserNotLoggedInException() {
        super("用户尚未登录!");
    }

}
