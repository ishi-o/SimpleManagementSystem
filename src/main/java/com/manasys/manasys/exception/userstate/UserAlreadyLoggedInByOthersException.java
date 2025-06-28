package com.manasys.manasys.exception.userstate;

/**
 * @author 刘洛松
 * @since 2025.6.28
 */
public class UserAlreadyLoggedInByOthersException extends UserStateException {

    public UserAlreadyLoggedInByOthersException(String username) {
        super("用户 \"" + username + "\" 已在其它设备上登录!");
    }
}
