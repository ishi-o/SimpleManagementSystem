package com.manasys.manasys.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.manasys.manasys.service.CommonRecordService;
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

    private final Map<String, CommonRecordService> services;

    private final InterfaceService interfaceServ;

    public EmployeePage(Map<String, CommonRecordService> services, InterfaceService interfaceServ) {
        this.services = services;
        this.interfaceServ = interfaceServ;
    }

    /**
     * 命令: 登记新员工
     *
     * @param ename 员工姓名
     * @return 命令执行成功或失败的提示
     */
    @ShellMethod(key = "reg-emp", value = "登记新员工")
    public String registerEmployee(@ShellOption(help = "员工姓名", defaultValue = "") String ename) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            if (ename.isEmpty()) {
                return "ERROR: 请提供员工姓名!\r\n       例如: reg-emp Mary 或 reg-emp --ename Mary";
            }
            Map<String, Object> map = new HashMap<>();
            map.put("name", ename);
            services.get("employeeService").registerEntity(map);
            return "OK: 登记成功!";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
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
            return "OK: \r\n" + services.get("employeeService").getEntityInfo();
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * 命令: 员工打卡
     *
     * @param eid 员工编号
     * @return 命令执行成功或失败的提示
     */
    @ShellMethod(key = "punch-in", value = "员工打卡")
    public String punchIn(@ShellOption(help = "员工编号", defaultValue = "") Long eid) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            if (eid == null) {
                return "ERROR: 请提供员工编号!\r\n       例如: punch-in 1 或 punch-in --eid 1";
            }
            services.get("employeeService").record(eid);
            return "OK: 打卡成功!";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
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
    public String getPunches(@ShellOption(help = "员工号", defaultValue = "") Long eid, @ShellOption(help = "年份", defaultValue = "") Integer year, @ShellOption(help = "月份", defaultValue = "") Integer month) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            if (eid == null) {
                return "ERROR: 必须提供员工编号!\r\n       例如: get-punch 1 [2025 6]";
            } else if (year == null && month == null) {
                return "OK: " + services.get("employeeService").getRecordCount(eid);
            } else if (year != null && month != null) {
                return "OK: " + services.get("employeeService").getRecordCount(eid, year, month);
            } else {
                return "ERROR: 没有这样的命令! 年份与月份可选但必须同时提供!\r\n       例如: get-punch --eid 1 --year 2025 --month 6";
            }
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}
