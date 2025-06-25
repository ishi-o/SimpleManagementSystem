package com.manasys.manasys.exception.signup;

/**
 * 用户名不合法导致注册失败时, 抛出该异常
 *
 * @author 刘洛松
 * @since 2025.6.25
 * @see SignUpException
 */
public class InvalidUsernameException extends SignUpException {

    public InvalidUsernameException(String username) {
        super("用户名 \"" + username + "\" 不合法! 用户名应只包含大写字母、小写字母、数字, 且长度不能超过20字符!");
    }

}
