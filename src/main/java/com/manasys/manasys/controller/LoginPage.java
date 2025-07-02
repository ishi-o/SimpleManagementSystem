package com.manasys.manasys.controller;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.standard.commands.Quit;

import com.manasys.manasys.service.InterfaceService;
import com.manasys.manasys.service.InterfaceService.InterfaceMode;
import com.manasys.manasys.service.UserService;

/**
 * 用户未登入界面, 提供注册、登录、退出、注销功能
 *
 * @author 刘洛松
 * @since 2025.6.25
 */
@ShellComponent
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoginPage implements Quit.Command {

    private final UserService userServ;

    private final InterfaceService interfaceServ;

    public LoginPage(UserService userServ, InterfaceService interfaceServ) {
        this.userServ = userServ;
        this.interfaceServ = interfaceServ;
    }

    /**
     * 注册用户方法的Shell交互
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 提示信息
     */
    @ShellMethod(key = "sign-up", value = "注册用户")
    public String signUp(@ShellOption(help = "用户名", defaultValue = "") String username, @ShellOption(help = "用户密码", defaultValue = "") String password) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.LOGIN);
            if (username.isEmpty() || password.isEmpty()) {
                return "ERROR: 请提供用户名和密码!\r\n       例如: sign-up Mary Password@123 或 sign-up --username Mary --password Password@1234";
            }
            userServ.signUp(username, password);
            return "OK: 注册成功!";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
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
    public String signIn(@ShellOption(help = "用户名", defaultValue = "") String username, @ShellOption(help = "用户密码", defaultValue = "") String password) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.LOGIN);
            if (username.isEmpty() || password.isEmpty()) {
                return "ERROR: 请提供用户名和密码!\r\n       例如: sign-in Mary Password@123 或 sign-in --username Mary --password Password@123";
            }
            userServ.signIn(username, password);
            interfaceServ.setCurrMode(InterfaceMode.HOME);
            return "OK: 登录成功!";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * 命令: 退出程序
     */
    @ShellMethod(key = {"exit", "quit", "q"}, value = "退出程序")
    public void quit() {
        try {
            userServ.signOut();
            System.out.println("OK: 退出成功! 感谢使用!");
        } finally {
            throw new ExitRequest();
        }
    }

}
