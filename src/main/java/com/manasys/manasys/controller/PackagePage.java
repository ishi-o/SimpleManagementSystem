package com.manasys.manasys.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.manasys.manasys.service.InterfaceService;
import com.manasys.manasys.service.InterfaceService.InterfaceMode;
import com.manasys.manasys.service.PackageService;

/**
 * 命令行界面类: 快递相关命令
 *
 * @author 刘洛松
 * @since 2025.6.30
 */
@ShellComponent
public class PackagePage {

    private final InterfaceService interfaceServ;

    private final PackageService pkgServ;

    public PackagePage(InterfaceService interfaceServ, PackageService pkgServ) {
        this.interfaceServ = interfaceServ;
        this.pkgServ = pkgServ;
    }

    @ShellMethod(key = "send-package", value = "登记发送快递")
    public String sendPackage(@ShellOption(help = "和订单关联的客户来访记录编号", defaultValue = "") Long crid, @ShellOption(help = "订单的费用", defaultValue = "") Integer fee) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            if (crid == null || fee == null) {
                return "ERROR: 请附带上发送快递的费用!\r\n       例如: send-package 1 10 或 send-package --crid 1 --fee 10";
            }
            pkgServ.register(crid, fee);
            return "OK: 登记发送的快递成功!";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    @ShellMethod(key = "receive-package", value = "登记接收快递")
    public String receivePackage(@ShellOption(help = "接收快递的员工", defaultValue = "") Long eid) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            if (eid == null) {
                return "ERROR: 请提供接收快递的员工编号!\r\n       例如: receive-package 1 或 receive-package --eid 1";
            }
            pkgServ.register(eid);
            return "OK: 登记接收的快递成功!";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    @ShellMethod(key = "get-fee", value = "统计发件费用")
    public String getPackageFee(@ShellOption(help = "指定的年份", defaultValue = "") Integer year, @ShellOption(help = "指定的月份", defaultValue = "") Integer month) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            if (year == null || month == null) {
                return "ERROR: 请提供接收快递的员工编号!\r\n       例如: get-fee 2025 6 或 get-fee --year 2025 --month 6";
            }
            return "OK: \r\n" + pkgServ.getFee(year, month);
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

}
