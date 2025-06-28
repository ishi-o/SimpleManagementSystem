package com.manasys.manasys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.manasys.manasys.exception.userstate.UserNotLoggedInException;
import com.manasys.manasys.service.EmployeeService;
import com.manasys.manasys.service.InteractModeEnum;
import com.manasys.manasys.service.ShellMode;

/**
 * 家界面, 提供公司员工相关操作、公司客户相关操作、快递相关操作
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@ShellComponent
public class HomePage {

    @Autowired
    private EmployeeService empServ;

    @Autowired
    private ShellMode shellMode;

    private void checkUserState() {
        if (!shellMode.getCurrMode().equals(InteractModeEnum.HOME)) {
            throw new UserNotLoggedInException();
        }
    }

    @ShellMethod(key = "reg-emp", value = "登记新员工")
    public String registerEmployee(String ename) {
        try {
            checkUserState();
            empServ.register(ename);
            return "登记成功!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ShellMethod(key = "view-emp", value = "查询所有员工的信息")
    public String viewAllEmployees() {
        try {
            checkUserState();
            return empServ.viewAllEmployees();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
