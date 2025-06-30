package com.manasys.manasys.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.manasys.manasys.service.InterfaceService;
import com.manasys.manasys.service.InterfaceService.InterfaceMode;
import com.manasys.manasys.service.UserService;

/**
 * 家界面, 提供管理员登出、注销等相关功能
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@ShellComponent
public class HomePage {

    private final UserService userServ;

    private final InterfaceService interfaceServ;

    public HomePage(UserService userServ, InterfaceService interfaceServ) {
        this.userServ = userServ;
        this.interfaceServ = interfaceServ;
    }

    /**
     * 命令: 管理员登出
     *
     * @return 命令执行成功或失败的提示
     */
    @ShellMethod(key = "sign-out", value = "登出用户")
    public String signOut() {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            userServ.signOut();
            interfaceServ.setCurrMode(InterfaceMode.LOGIN);
            return "登出成功!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 命令: 管理员账号注销
     *
     * @return 命令执行成功或失败的提示
     */
    @ShellMethod(key = "log-out", value = "注销本用户")
    public String logOut() {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            userServ.logOut();
            interfaceServ.setCurrMode(InterfaceMode.LOGIN);
            return "注销成功! 请重新登录!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
