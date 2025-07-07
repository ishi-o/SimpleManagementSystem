package com.mooncompany.manasys.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mooncompany.manasys.entity.Client;
import com.mooncompany.manasys.entity.ClientRecord;
import com.mooncompany.manasys.entity.EmpRecord;
import com.mooncompany.manasys.entity.Employee;
import com.mooncompany.manasys.entity.ReceivePackage;
import com.mooncompany.manasys.entity.SendPackage;
import com.mooncompany.manasys.entity.User;

/**
 * 实体的工厂类, 用于创建实体
 *
 * @author 刘洛松
 * @since 2025.7.7
 */
public class EntityFactory {

    /**
     * 私有构造方法
     */
    private EntityFactory() {
    }

    /**
     * 创建员工实例
     *
     * @param ename 员工姓名
     * @return 员工实例, 其中日期为调用该方法时的日期
     */
    public static Employee newEmployeeAtNow(String ename) {
        return Employee.newInstance(ename, LocalDate.now());
    }

    /**
     * 创建员工打卡记录实例
     *
     * @param emp 关联的员工
     * @return 员工打卡实例, 其中打卡日期为调用该方法时的日期
     */
    public static EmpRecord newEmployeeRecordAtNow(Employee emp) {
        return EmpRecord.newInstance(emp, LocalDate.now());
    }

    /**
     * 创建客户实例
     *
     * @param cid 客户身份证号
     * @param cname 客户姓名
     * @param phone 客户电话号码
     * @param loc 客户收件地址
     * @return 客户实例
     */
    public static Client newClient(Long cid, String cname, String phone, String loc) {
        return Client.newInstance(cid, cname, phone, loc);
    }

    /**
     * 创建客户访问记录实例
     *
     * @param client 关联的客户
     * @return 客户访问记录, 其中记录的时间戳为调用该方法时的时间
     */
    public static ClientRecord newClientRecordAtNow(Client client) {
        return ClientRecord.newInstance(client, LocalDateTime.now());
    }

    /**
     * 创建接收快递的实例
     *
     * @param emp 接收快递的员工
     * @return 接收快递的实例, 其中快递的处理时间为调用该方法时的时间
     */
    public static ReceivePackage newReceivePackage(Employee emp) {
        return ReceivePackage.newInstance(LocalDateTime.now(), emp);
    }

    /**
     * 创建发送快递的实例
     *
     * @param clientRecord 发送快递关联的订单号
     * @param fee 发送快递的费用
     * @return 发送快递的实例, 其中快递的处理时间为调用该方法时的时间
     */
    public static SendPackage newSendPackage(ClientRecord clientRecord, Integer fee) {
        return SendPackage.newInstance(LocalDateTime.now(), clientRecord, fee);
    }

    /**
     * 创建管理员账户实例
     *
     * @param username 管理员名称
     * @param password 管理员密码
     * @return 管理员实例
     */
    public static User newUser(String username, String password) {
        return User.newInstance(username, password);
    }

}
