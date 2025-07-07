package com.mooncompany.manasys.controller;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.mooncompany.manasys.service.InterfaceService;
import com.mooncompany.manasys.service.InterfaceService.InterfaceMode;
import com.mooncompany.manasys.service.PackageService;

/**
 * 命令行界面类: 快递相关命令
 *
 * @author 刘洛松
 * @author 杨帆
 * @since 2025.6.30
 */
@ShellComponent
@ShellCommandGroup("快递收发操作")
public class PackagePage {

    private final InterfaceService interfaceServ;

    private final PackageService pkgServ;

    public PackagePage(InterfaceService interfaceServ, PackageService pkgServ) {
        this.interfaceServ = interfaceServ;
        this.pkgServ = pkgServ;
    }

    /**
     * 登记发送快递命令
     * <p>
     * 该命令用于在系统中登记发送的快递信息， 需要关联客户来访记录并指定快递费用。
     * </p>
     *
     * @param crid 和订单关联的客户来访记录编号，必须提供有效值
     * @param fee 订单的费用（单位：元），必须为正整数
     * @return 操作结果消息，成功时返回"OK:..."，失败时返回"ERROR:..."
     * @throws IllegalArgumentException 当参数格式错误时抛出
     * @throws IllegalStateException 当系统不在HOME模式时抛出
     * @see #receivePackage(Long) 接收快递命令
     */
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

    /**
     * 登记接收快递命令
     * <p>
     * 该命令用于在系统中登记接收的快递信息， 需要指定接收快递的员工编号。
     * </p>
     *
     * @param eid 接收快递的员工编号，必须提供有效值
     * @return 操作结果消息，成功时返回"OK:..."，失败时返回"ERROR:..."
     * @throws IllegalArgumentException 当参数格式错误时抛出
     * @throws IllegalStateException 当系统不在HOME模式时抛出
     * @see #sendPackage(Long, Integer) 发送快递命令
     */
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

    /**
     * 统计发件费用命令
     * <p>
     * 该命令用于统计指定年月内的快递发送费用总和， 返回格式化的统计结果。
     * </p>
     *
     * @param year 指定的年份（如2025），必须提供有效值
     * @param month 指定的月份（1-12），必须提供有效值
     * @return 统计结果消息，成功时返回费用明细，失败时返回错误信息
     * @throws IllegalArgumentException 当参数格式错误时抛出
     * @throws IllegalStateException 当系统不在HOME模式时抛出
     */
    @ShellMethod(key = "get-fee", value = "统计发件费用")
    public String getPackageFee(@ShellOption(help = "指定的年份", defaultValue = "") Integer year, @ShellOption(help = "指定的月份", defaultValue = "") Integer month) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            if (year == null || month == null) {
                return "ERROR: 请提供指定的年份及月份!\r\n       例如: get-fee 2025 6 或 get-fee --year 2025 --month 6";
            }
            return "OK: \r\n" + pkgServ.getFee(year, month);
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

}
