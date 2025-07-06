package com.manasys.manasys.controller;

import java.util.HashMap;
import java.util.Map;

import org.jline.reader.LineReader;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.manasys.manasys.service.CommonRecordService;
import com.manasys.manasys.service.InterfaceService;
import com.manasys.manasys.service.InterfaceService.InterfaceMode;

/**
 * 客户命令类
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@ShellComponent
public class ClientPage {

    private final LineReader sin;

    private final Map<String, CommonRecordService> services;

    private final InterfaceService interfaceServ;

    public ClientPage(@Lazy LineReader sin, Map<String, CommonRecordService> map, InterfaceService interfaceServ) {
        this.sin = sin;
        this.services = map;
        this.interfaceServ = interfaceServ;
    }

    /**
     * 命令: 登记客户, 通过调用reg-client命令执行
     *
     * @param cid 客户身份证号
     * @param cname 客户姓名
     * @param phone 客户电话号码
     * @param loc 客户收件地址
     * @return 执行成功或失败的消息
     */
    @ShellMethod(key = "reg-client", value = "登记新客户")
    public String registerClient(@ShellOption(help = "客户身份证号", defaultValue = "") Long cid, @ShellOption(help = "客户姓名", defaultValue = "") String cname, @ShellOption(help = "客户电话号码", defaultValue = "") String phone, @ShellOption(help = "客户收件地址", defaultValue = "") String loc) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            if (cid == null || cname.isEmpty() || phone.isEmpty() || loc.isEmpty()) {
                return "ERROR: 请提供足够的信息!\r\n       例如: reg-client 1 Mary 15535 \"New York\" 或\r\n       reg-client --cid 1 --cname Mary --phone 15535 --loc \"New York\"";
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", cid);
            map.put("name", cname);
            map.put("phone", phone);
            map.put("location", loc);
            services.get("clientService").registerEntity(map);
            return "OK: 登记成功!";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * 命令: 登记客户来访记录, 若不存在该客户信息, 会要求管理员进行登记
     *
     * @param cid 客户身份证号
     * @return 执行成功或失败的消息
     */
    @ShellMethod(key = "visit", value = "记录客户来访")
    public String visit(@ShellOption(help = "客户身份证号", defaultValue = "") Long cid) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            if (cid == null) {
                return "ERROR: 请提供客户的身份证号!\r\n       例如: visit 1 或 visit --cid 1";
            } else if (!services.get("clientService").containsEntity(cid)) {
                String cname = sin.readLine("WARNING: 客户首次来访, 需要登记信息: \r\n          请输入客户姓名: ");
                String phonenum = sin.readLine("          请输入客户电话号码: ");
                String loc = sin.readLine("          请输入客户收件地址: ");
                Map<String, Object> map = new HashMap<>();
                map.put("id", cid);
                map.put("name", cname);
                map.put("phone", phonenum);
                map.put("location", loc);
                services.get("clientService").registerEntity(map);
                System.out.println("OK: 登记成功!");
            }
            services.get("clientService").record(cid);
            return "OK: 记录客户来访成功!";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * 命令: 获取客户的来访次数
     *
     * @param cid 可选参数, 表示指定客户的身份证号
     * @return 执行成功或失败的消息
     */
    @ShellMethod(key = "get-visit", value = "查询所有客户的来访次数")
    public String getCountOfVisit(@ShellOption(help = "指定客户身份证号", defaultValue = "") Long cid) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            if (cid == null) {
                return services.get("clientService").getRecordCount();
            } else {
                return services.get("clientService").getRecordCount(cid);
            }
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * 命令: 获取客户的个人信息
     *
     * @param cid 可选参数, 表示指定客户的身份证号
     * @return 执行成功或失败的消息
     */
    @ShellMethod(key = "get-client-info", value = "查询客户信息")
    public String getClientInfo(@ShellOption(help = "指定客户身份证号", defaultValue = "") Long cid) {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            if (cid == null) {
                return services.get("clientService").getEntityInfo();
            } else {
                return services.get("clientService").getEntityInfo(cid);
            }
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    @ShellMethod(key = "get-visit-info", value = "获取所有客户的来访记录")
    public String getClientVisitInfo() {
        try {
            interfaceServ.checkCurrMode(InterfaceMode.HOME);
            return services.get("clientService").getRecordInfo();
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
            //111test
        }
    }

}
