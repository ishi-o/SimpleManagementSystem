package com.mooncompany.manasys.exception.signin;

/**
 * 密码错误导致登录失败时, 抛出该异常
 *
 * @author 刘洛松
 * @since 2025.5.25
 * @see SignInException
 */
public class PasswordMismatchException extends SignInException {

    public PasswordMismatchException(String password) {
        super("用户密码 \"" + password + "\" 输入错误!");
    }

}
