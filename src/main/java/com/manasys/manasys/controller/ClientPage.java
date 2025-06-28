package com.manasys.manasys.controller;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

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

}
