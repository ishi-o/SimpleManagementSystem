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

    /**
     * 命令: 登记新员工
     *
     * @param ename 员工姓名
     * @return 命令执行成功或失败的提示
     */
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

    /**
     * 命令: 查询员工信息
     *
     * @return 命令执行成功或失败的提示
     */
    @ShellMethod(key = "get-emp-info", value = "查询所有员工的信息")
    public String viewAllEmployees() {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            return empServ.getEmployeeInfo();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 命令: 员工打卡
     *
     * @param eid 员工编号
     * @return 命令执行成功或失败的提示
     */
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

    /**
     * 命令: 查询员工的打卡情况
     *
     * @param eid 员工编号
     * @param year 可选项, 指定年份
     * @param month 可选项, 指定月份
     * @return 若不指定年份和月份, 则返回员工入职以来所有月份的打卡情况; 否则返回员工在指定月份的打卡情况
     */
    @ShellMethod(key = "get-punch", value = "查询员工的打卡情况")
    public String getPunches(@ShellOption(help = "员工号") Long eid, @ShellOption(help = "年份") Integer year, @ShellOption(help = "月份") Integer month) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            if (year == null && month == null) {
                return empServ.getCountOfPunch(eid);
            } else if (year != null && month != null) {
                return empServ.getCountOfPunch(eid, year, month);
            } else {
                return "没有这样的命令!";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
