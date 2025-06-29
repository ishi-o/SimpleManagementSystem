package com.manasys.manasys.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.manasys.manasys.entity.Client;
import com.manasys.manasys.entity.ClientRecord;
import com.manasys.manasys.repository.ClientRecordRepository;
import com.manasys.manasys.repository.ClientRepository;

import jakarta.transaction.Transactional;

/**
 * @author 刘洛松
 * @since 2025.6.28
 */
@Service
public class ClientService {

    private final ClientRepository clientRepo;
    private final ClientRecordRepository cliRecordRepo;

    public ClientService(ClientRepository clientRepo, ClientRecordRepository cliRecordRepo) {
        this.clientRepo = clientRepo;
        this.cliRecordRepo = cliRecordRepo;
    }

    @Transactional
    public void register(Long cid, String cname, String phone, String loc) {
        if (!clientRepo.existsById(cid)) {
            clientRepo.save(Client.newInstance(cid, cname, phone, loc));
        }
    }

    @Transactional
    public String viewAllClients() {
        List<Client> list = clientRepo.findAll();
        String ans = "\r\n客户身份证号\t\t客户姓名\t\t客户电话号码\t\t客户地址";
        for (Client c : list) {
            ans += "\r\n" + c.getClientName() + "\t\t" + c.getPhoneNumber() + "\t\t" + c.getLocation();
        }
        return ans;
    }

    @Transactional
    public void visit(Long cid) {
        cliRecordRepo.save(ClientRecord.newInstance(clientRepo.findById(cid).get(), LocalDate.now()));
    }

    @Transactional
    public boolean contains(Long cid) {
        return clientRepo.existsById(cid);
    }

    @Transactional
    public String getVisits() {
        List<Object[]> list = cliRecordRepo.countAllClientsVisits();
        String ans = "客户身份证号\t\t客户来访次数";
        for (Object[] tup : list) {
            ans += "\r\n" + tup[0] + "\t\t\t" + tup[1];
        }
        return ans;
    }

    @Transactional
    public String getVisits(Long cid) {
        return "身份证号为 \"" + cid + "\" 的客户总共来访 " + cliRecordRepo.countVisitsByClientId(cid) + " 次!";
    }

    @Transactional
    public String getClientInfo() {
        List<Client> list = clientRepo.findAll();
        String ans = "客户身份证号\t\t客户名\t\t客户电话号码\t\t客户收件地址";
        for (Client c : list) {
            ans += "\r\n" + c.getCid() + "\t\t\t" + c.getClientName() + "\t\t" + c.getPhoneNumber() + "\t\t\t" + c.getLocation();
        }
        return ans;
    }

    @Transactional
    public String getClientInfo(Long cid) {
        Client c = clientRepo.findById(cid).orElseThrow();
        return "客户身份证号\t\t客户名\t\t客户电话号码\t\t客户收件地址\r\n" + c.getCid() + "\t\t\t" + c.getClientName() + "\t\t" + c.getPhoneNumber() + "\t\t\t" + c.getLocation();
    }
}
