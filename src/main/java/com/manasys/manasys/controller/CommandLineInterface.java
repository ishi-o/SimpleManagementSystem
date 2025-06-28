package com.manasys.manasys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.manasys.manasys.service.UserService;

/**
 * @author 刘洛松
 * @since 2025.6.25
 */
@ShellComponent
public class CommandLineInterface {

    @Autowired
    private UserService userServ;

    @ShellMethod(key = "signup", value = "注册用户")
    public String signUp(@ShellOption(help = "用户名") String username, @ShellOption(help = "用户密码") String password) {
        try {
            userServ.signUp(username, password);
            return "注册成功!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ShellMethod(key = "signin", value = "登录用户")
    public String signIn(@ShellOption(help = "用户名") String username, @ShellOption(help = "用户密码") String password) {
        try {
            userServ.signIn(username, password);
            return "登录成功!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
