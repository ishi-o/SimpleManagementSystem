package com.mooncompany.manasys.exception.signin;

/**
 * 用户登录异常类, 是所有登录失败发生的异常的基类
 *
 * @author 刘洛松
 * @since 2025.6.25
 * @see RuntimeException
 */
public class SignInException extends RuntimeException {

    public SignInException(String msg) {
        super("登录失败: " + msg);
    }

}
