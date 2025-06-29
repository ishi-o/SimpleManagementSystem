package com.manasys.manasys.controller;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.manasys.manasys.service.ClientService;

/**
 * @author 刘洛松
 * @since 2025.6.28
 */
@ShellComponent
public class ClientPage {

    private final LineReader sin;

    @Autowired
    private ClientService clientServ;

    public ClientPage(@Lazy LineReader sin) {
        this.sin = sin;
    }

    @ShellMethod(key = "reg-client", value = "登记新客户")
    public String registerClient(Long cid, String cname, String phone, String loc) {
        clientServ.register(cid, cname, phone, loc);
        return "登记成功!";
    }

    @ShellMethod(key = "visit", value = "记录客户来访")
    public String visit(Long cid) {
        if (!clientServ.contains(cid)) {
            String cname = sin.readLine("客户首次来访, 需要登记信息: \r\n请输入客户姓名: ");
            String phonenum = sin.readLine("请输入客户电话号码: ");
            String loc = sin.readLine("请输入客户收件地址: ");
            clientServ.register(cid, cname, phonenum, loc);
            System.out.println("登记成功!");
        }
        clientServ.visit(cid);
        return "记录客户来访成功!";
    }

    @ShellMethod(key = "get-visits", value = "查询所有客户的来访次数")
    public String getVisits(@ShellOption(help = "指定客户身份证号", defaultValue = "") Long cid) {
        if (cid == null) {
            return clientServ.getVisits();
        } else {
            return clientServ.getVisits(cid);
        }
    }

    @ShellMethod(key = "get-client-info", value = "查询客户信息")
    public String getClientInfo(@ShellOption(help = "指定客户身份证号", defaultValue = "") Long cid) {
        if (cid == null) {
            return clientServ.getClientInfo();
        } else {
            return clientServ.getClientInfo(cid);
        }
    }

}
