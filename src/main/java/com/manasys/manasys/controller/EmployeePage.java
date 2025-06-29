package com.manasys.manasys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.manasys.manasys.service.EmployeeService;
import com.manasys.manasys.service.InterfaceService;
import com.manasys.manasys.service.InterfaceService.InterfaceMode;

/**
 * 员工管理界面, 提供员工的打卡、查询交互功能
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@ShellComponent
public class EmployeePage {

    @Autowired
    private EmployeeService empServ;

    @Autowired
    private InterfaceService interfaceServ;

    @ShellMethod(key = "reg-emp", value = "登记新员工")
    public String registerEmployee(String ename) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            empServ.register(ename);
            return "登记成功!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ShellMethod(key = "view-emp", value = "查询所有员工的信息")
    public String viewAllEmployees() {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            return empServ.viewAllEmployees();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ShellMethod(key = "punch-in", value = "员工打卡")
    public String punchIn(Long eid) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            empServ.punchIn(eid);
            return "打卡成功!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ShellMethod(key = "get-punches", value = "查询员工的打卡情况")
    public String getPunches(@ShellOption(help = "员工号") Long eid, @ShellOption(help = "年份") Integer year, @ShellOption(help = "月份") Integer month) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            if (year == null && month == null) {
                return empServ.getPunches(eid);
            } else if (year != null && month != null) {
                return empServ.getPunches(eid, year, month);
            } else {
                return "没有这样的命令!";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
