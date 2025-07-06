package com.manasys.manasys.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.manasys.manasys.entity.Client;
import com.manasys.manasys.entity.ClientRecord;
import com.manasys.manasys.exception.client.ClientAlreadyExistException;
import com.manasys.manasys.exception.client.ClientNotFoundException;
import com.manasys.manasys.repository.ClientRecordRepository;
import com.manasys.manasys.repository.ClientRepository;

import jakarta.transaction.Transactional;

/**
 * 提供客户业务功能的服务类
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@Service
public class ClientService implements CommonRecordService {

    private final ClientRepository clientRepo;
    private final ClientRecordRepository cliRecordRepo;

    public ClientService(ClientRepository clientRepo, ClientRecordRepository cliRecordRepo) {
        this.clientRepo = clientRepo;
        this.cliRecordRepo = cliRecordRepo;
    }

    /**
     * 事务: 登记一个新客户
     *
     * @param map 配置信息表
     * @throws ClientAlreadyExistException 当客户已经存在时
     */
    @Override
    @Transactional
    public void registerEntity(Map<String, Object> map) {
        Long cid = (Long) map.get("id");
        String cname = (String) map.get("name"),
                phone = (String) map.get("phone"),
                loc = (String) map.get("location");
        if (!clientRepo.existsById(cid)) {
            clientRepo.save(Client.newInstance(cid, cname, phone, loc));
        } else {
            throw new ClientAlreadyExistException();
        }
    }

    /**
     * 事务: 登记客户来访, 自动获取现在作为来访的日期及时间
     *
     * @param cid 客户的身份证号
     */
    @Override
    @Transactional
    public void record(Long cid) {
        cliRecordRepo.save(ClientRecord.newInstance(clientRepo.findById(cid).get(), LocalDateTime.now()));
    }

    /**
     * 事务: 查询是否存在指定客户的信息
     *
     * @param cid 客户身份证号
     * @return true 若存在; false 若不存在
     */
    @Override
    @Transactional
    public boolean containsEntity(Long cid) {
        return clientRepo.existsById(cid);
    }

    /**
     * 事务: 查看所有客户的信息
     *
     * @return 客户信息列表
     */
    @Override
    @Transactional
    public String getEntityInfo() {
        List<Client> list = clientRepo.findAll();
        String ans = "客户身份证号\t\t客户名\t\t客户电话号码\t\t客户收件地址";
        for (Client c : list) {
            ans += "\r\n" + c.getCid() + "\t\t\t" + c.getClientName() + "\t\t" + c.getPhoneNumber() + "\t\t\t" + c.getLocation();
        }
        return ans;
    }

    /**
     * 事务: 查看指定客户的信息
     *
     * @param cid 客户的身份证号
     * @return 身份证号为 {@code cid} 的客户的个人信息
     * @throws ClientNotFoundException 当客户不存在时
     */
    @Override
    @Transactional
    public String getEntityInfo(Long cid) {
        Client c = clientRepo.findById(cid).orElseThrow(() -> {
            throw new ClientNotFoundException();
        });
        return "客户身份证号\t\t客户名\t\t客户电话号码\t\t客户收件地址\r\n" + c.getCid() + "\t\t\t" + c.getClientName() + "\t\t" + c.getPhoneNumber() + "\t\t\t" + c.getLocation();
    }
    /**
     * 事务: 获取所有客户来访记录信息
     *
     * @return 格式化后的来访记录字符串，包含来访编号、客户身份证号和来访时间
     */
    @Override
    @Transactional
    public String getRecordInfo() {
        String ans = "客户来访编号\t\t客户身份证号\t\t客户来访时间";
        List<ClientRecord> list = cliRecordRepo.findAll();
        for (ClientRecord cr : list) {
            ans += "\r\n" + cr.getCrid() + "\t\t\t" + cr.getClient().getCid() + "\t\t\t" + cr.getDateTime();
        }
        return ans;
    }
    /**
     * 事务: 获取指定客户的所有来访记录
     *
     * @param cid 客户身份证号
     * @return 格式化后的来访记录字符串，包含来访编号和来访时间
     * @throws ClientNotFoundException 当客户不存在时
     */
    @Override
    @Transactional
    public String getRecordInfo(Long cid) {
        String ans = "客户来访编号\t\t客户来访时间";
        List<Object[]> list = cliRecordRepo.findByCid(cid);
        for (Object[] oarr : list) {
            ans += "\r\n" + oarr[0] + "\t\t\t" + oarr[1];
        }
        return ans;
    }
     /**
     * 事务: 获取指定客户在特定年月内的来访记录
     *
     * @param cid 客户身份证号
     * @param year 查询年份
     * @param month 查询月份
     * @return 格式化后的来访记录字符串，包含来访编号和来访时间
     * @throws ClientNotFoundException 当客户不存在时
     */
    @Override
    @Transactional
    public String getRecordInfo(Long cid, Integer year, Integer month) {
        String ans = "客户来访编号\t\t客户来访时间";
        List<Object[]> list = cliRecordRepo.findByCidAndYearAndMonth(cid, year, month);
        for (Object[] oarr : list) {
            ans += "\r\n" + oarr[0] + "\t\t\t" + oarr[1];
        }
        return ans;
    }

    /**
     * 事务: 查询所有客户的来访次数
     *
     * @return 所有客户来访次数列表, 以客户身份证号升序排序
     */
    @Override
    @Transactional
    public String getRecordCount() {
        List<Object[]> list = cliRecordRepo.countRecordGroupByCid();
        String ans = "客户身份证号\t\t客户来访次数";
        for (Object[] tup : list) {
            ans += "\r\n" + tup[0] + "\t\t\t" + tup[1];
        }
        return ans;
    }

    /**
     * 事务: 查询指定客户的来访次数
     *
     * @param cid 客户身份证号
     * @return 身份证号为 {@code cid} 的客户的来访次数记录
     * @throws ClientNotFoundException 当客户不存在时
     */
    @Override
    @Transactional
    public String getRecordCount(Long cid) {
        if (clientRepo.existsById(cid)) {
            return "身份证号为 \"" + cid + "\" 的客户总共来访 " + cliRecordRepo.countRecordByCid(cid) + " 次!";
        } else {
            throw new ClientNotFoundException();
        }
    }
    /**
     * 获取指定客户在特定年月内的来访次数统计
     * 
     * @param cid 客户身份证号，唯一标识客户
     * @param year 要统计的年份
     * @param month 要统计的月份(1-12)
     * @return 格式化后的统计结果字符串
     * @throws ClientNotFoundException 当指定客户不存在时抛出
     */
    @Override
    @Transactional
    public String getRecordCount(Long cid, Integer year, Integer month) {
        if (clientRepo.existsById(cid)) {
            return "身份证号为 \"" + cid + "\" 的客户于 " + year + " 年 " + month + " 月 " + " 总共来访 " + cliRecordRepo.countRecordByCidAndYearAndMonth(cid, year, month) + " 次!";
        } else {
            throw new ClientNotFoundException();
        }
    }

}
