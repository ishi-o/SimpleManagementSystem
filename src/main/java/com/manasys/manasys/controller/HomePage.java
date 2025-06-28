package com.manasys.manasys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.manasys.manasys.service.InteractModeEnum;
import com.manasys.manasys.service.ServiceManager;
import com.manasys.manasys.service.ShellMode;

/**
 * 家界面, 提供管理员登出、注销等相关功能
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@ShellComponent
public class HomePage {

    @Autowired
    private ServiceManager services;

    @Autowired
    private ShellMode shellMode;

    private boolean checkShellMode() {
        return shellMode.getCurrMode().equals(InteractModeEnum.HOME);
    }

    @ShellMethod(key = "reg-emp", value = "登记新员工")
    public String registerEmployee(String ename) {
        if (checkShellMode()) {
            try {
                services.getService(ServiceType.EMPLOYEE_SERVICE).register(ename);
                return "登记成功!";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "用户状态异常: 您尚未登录管理员账号!";
        }
    }

    @ShellMethod(key = "view-emp", value = "查询所有员工的信息")
    public String viewAllEmployees() {
        if (checkShellMode()) {
            try {
                return services.getService(ServiceType.EMPLOYEE_SERVICE).viewAllEmployees();
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "用户状态异常: 您尚未登录管理员账号!";
        }
    }

    @ShellMethod(key = "sign-out", value = "登出用户")
    public String signOut() {
        if (checkShellMode()) {
            try {
                services.getService(ServiceType.USER_SERVICE).signOut();
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
                services.getService(ServiceType.USER_SERVICE).logOut();
                shellMode.setCurrMode(InteractModeEnum.LOGIN);
                return "注销成功! 请重新登录!";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "用户状态异常: 您尚未登录管理员账号!";
        }
    }

    @ShellMethod(key = "punch-in", value = "员工打卡")
    public String punchIn(Long eid) {
        if (checkShellMode()) {
            try {
                services.getService(ServiceType.EMPLOYEE_SERVICE).punchIn(eid);
                return "打卡成功!";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "用户状态异常: 您尚未登录管理员账号!";
        }
    }

    @ShellMethod(key = "get-punches", value = "查询员工的打卡情况")
    public String getPunches(@ShellOption(help = "员工号") Long eid, @ShellOption(help = "年份") Integer year, @ShellOption(help = "月份") Integer month) {
        try {
            if (checkShellMode()) {
                if (year == null && month == null) {
                    return services.getService(ServiceType.EMPLOYEE_SERVICE).getPunches(eid);
                } else if (year != null && month != null) {
                    return services.getService(ServiceType.EMPLOYEE_SERVICE).getPunches(eid, year, month);
                } else {
                    return "没有这样的命令!";
                }
            } else {
                return "用户状态异常: 您尚未登录管理员账号!";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
