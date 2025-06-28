package com.manasys.manasys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.manasys.manasys.service.UserService;

/**
 * 用户未登入界面, 提供注册、登录、退出、注销功能
 *
 * @author 刘洛松
 * @since 2025.6.25
 */
@ShellComponent
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoginPage {

    @Autowired
    private UserService userServ;

    /**
     * 注册用户方法的Shell交互
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 提示信息
     */
    @ShellMethod(key = "sign-up", value = "注册用户")
    public String signUp(@ShellOption(help = "用户名") String username, @ShellOption(help = "用户密码") String password) {
        try {
            userServ.signUp(username, password);
            return "注册成功!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 登录用户方法的Shell交互
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 提示信息
     */
    @ShellMethod(key = "sign-in", value = "登录用户")
    public String signIn(@ShellOption(help = "用户名") String username, @ShellOption(help = "用户密码") String password) {
        try {
            userServ.signIn(username, password);
            return "登录成功!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ShellMethod(key = {"exit", "quit", "q"}, value = "退出程序")
    public void signOut() {
        try {
            userServ.signOut();
        } finally {
            throw new ExitRequest();
        }
    }

}
