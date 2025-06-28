package com.manasys.manasys.exception.userstate;

/**
 * @author Ishi_O
 * @since
 */
public class UserNotLoggedInException extends UserStateException {

    public UserNotLoggedInException() {
        super("用户尚未登录!");
    }

}
