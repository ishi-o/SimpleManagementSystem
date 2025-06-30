package com.manasys.manasys.service;

import java.time.LocalDateTime;
import java.util.List;

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
public class ClientService {

    private final ClientRepository clientRepo;
    private final ClientRecordRepository cliRecordRepo;

    public ClientService(ClientRepository clientRepo, ClientRecordRepository cliRecordRepo) {
        this.clientRepo = clientRepo;
        this.cliRecordRepo = cliRecordRepo;
    }

    /**
     * 事务: 登记一个新客户
     *
     * @param cid 客户身份证号
     * @param cname 客户姓名
     * @param phone 客户手机号码
     * @param loc 客户收件地址
     * @throws ClientAlreadyExistException 当客户已经存在时
     */
    @Transactional
    public void register(Long cid, String cname, String phone, String loc) {
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
    @Transactional
    public void visit(Long cid) {
        cliRecordRepo.save(ClientRecord.newInstance(clientRepo.findById(cid).get(), LocalDateTime.now()));
    }

    /**
     * 事务: 查询是否存在指定客户的信息
     *
     * @param cid 客户身份证号
     * @return true 若存在; false 若不存在
     */
    @Transactional
    public boolean contains(Long cid) {
        return clientRepo.existsById(cid);
    }

    /**
     * 事务: 查询所有客户的来访次数
     *
     * @return 所有客户来访次数列表, 以客户身份证号升序排序
     */
    @Transactional
    public String getCountOfVisit() {
        List<Object[]> list = cliRecordRepo.countVisitAll();
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
    @Transactional
    public String getCountOfVisit(Long cid) {
        if (clientRepo.existsById(cid)) {
            return "身份证号为 \"" + cid + "\" 的客户总共来访 " + cliRecordRepo.countVisitByClientId(cid) + " 次!";
        } else {
            throw new ClientNotFoundException();
        }
    }

    /**
     * 事务: 查看所有客户的信息
     *
     * @return 客户信息列表
     */
    @Transactional
    public String getClientInfo() {
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
    @Transactional
    public String getClientInfo(Long cid) {
        Client c = clientRepo.findById(cid).orElseThrow(() -> {
            throw new ClientNotFoundException();
        });
        return "客户身份证号\t\t客户名\t\t客户电话号码\t\t客户收件地址\r\n" + c.getCid() + "\t\t\t" + c.getClientName() + "\t\t" + c.getPhoneNumber() + "\t\t\t" + c.getLocation();
    }

    @Transactional
    public String getClientVisitInfo() {
        String ans = "客户来访编号\t\t客户身份证号\t\t客户来访时间";
        List<ClientRecord> list = cliRecordRepo.findAll();
        for (ClientRecord cr : list) {
            ans += "\r\n" + cr.getCrid() + "\t\t\t" + cr.getClient().getCid() + "\t\t\t" + cr.getDateTime();
        }
        return ans;
    }

}
