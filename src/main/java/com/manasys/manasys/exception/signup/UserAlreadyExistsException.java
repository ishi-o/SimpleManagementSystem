package com.manasys.manasys.exception.signup;

/**
 * 若用户已存在, 注册失败时, 抛出该异常
 *
 * @author 刘洛松
 * @since 2025.6.25
 * @see SignUpException
 */
public class UserAlreadyExistsException extends SignUpException {

    public UserAlreadyExistsException(String username) {
        super("用户 \"" + username + "\" 已存在!");
    }

}
