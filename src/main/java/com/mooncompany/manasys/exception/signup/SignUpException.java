package com.mooncompany.manasys.exception.signup;

/**
 * 注册异常类, 是所有注册失败发生的异常的基类
 *
 * @author 刘洛松
 * @since 2025.6.25
 * @see RuntimeException
 */
public class SignUpException extends RuntimeException {

    public SignUpException(String message) {
        super("注册失败: " + message);
    }

}
