package com.manasys.manasys.exception.signup;

/**
 * 密码不合法导致注册失败时, 抛出该异常
 *
 * @author 刘洛松
 * @since 2025.6.25
 * @see SignUpException
 */
public class InvalidPasswordException extends SignUpException {

    public InvalidPasswordException(String pwd) {
        super("密码 \"" + pwd + "\" 不合法! 必须包含且只能由大写字母、小写字母、数字及特殊字符(@#$%&+=)构成且长度在8~20之间!");
    }

}
