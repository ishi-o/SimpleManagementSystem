package com.manasys.manasys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

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

    @Autowired
    private InterfaceService interfaceServ;

    @Autowired
    private PackageService pkgServ;

    @ShellMethod(key = "send-package", value = "登记发送快递")
    public String sendPackage(Long crid, Integer fee) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            if (fee == null) {
                return "请附带上发送快递的费用!";
            }
            pkgServ.register(crid, fee);
            return "登记发送的快递成功!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ShellMethod(key = "receive-package", value = "登记接收快递")
    public String receivePackage(Long eid) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            pkgServ.register(eid);
            return "登记接收的快递成功!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ShellMethod(key = "get-fee", value = "统计发件费用")
    public String getPackageFee(Integer year, Integer month) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            return pkgServ.getFee(year, month);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
