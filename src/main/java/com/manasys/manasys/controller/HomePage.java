package com.manasys.manasys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.manasys.manasys.service.InteractModeEnum;
import com.manasys.manasys.service.ShellMode;
import com.manasys.manasys.service.UserService;

/**
 * 家界面, 提供管理员登出、注销等相关功能
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@ShellComponent
public class HomePage {

    @Autowired
    private UserService userServ;

    @Autowired
    private ShellMode shellMode;

    private boolean checkShellMode() {
        return shellMode.getCurrMode().equals(InteractModeEnum.HOME);
    }

    @ShellMethod(key = "sign-out", value = "登出用户")
    public String signOut() {
        if (checkShellMode()) {
            try {
                userServ.signOut();
                shellMode.setCurrMode(InteractModeEnum.LOGIN);
                return "登出成功!";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "用户状态异常: 您尚未登录管理员账号!";
        }
    }

    @ShellMethod(key = "log-out", value = "注销本用户")
    public String logOut() {
        if (checkShellMode()) {
            try {
                userServ.logOut();
                shellMode.setCurrMode(InteractModeEnum.LOGIN);
                return "注销成功! 请重新登录!";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "用户状态异常: 您尚未登录管理员账号!";
        }
    }

}
