package com.mooncompany.manasys.entity;

import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 客户实体类
 *
 * @author 刘洛松
 * @author 赵庆显
 * @since 2025.6.28
 */
@Entity
@Table(name = "clients", schema = "jhomework")
public class Client {

    @Id
    private Long cid;

    @Column(name = "cname", nullable = false)
    private String cname;

    @Column(name = "phonenum", nullable = false)
    private String phonenum;

    @Column(name = "loc", nullable = false)
    private String loc;

    protected Client() {
    }

    /**
     * 用于持久化操作的构造函数
     *
     * @param cid 客户ID
     * @param cname 客户名称
     * @param phonenum 客户电话号码
     * @param loc 客户地址
     */
    @PersistenceCreator
    protected Client(Long cid, String cname, String phonenum, String loc) {
        this.cid = cid;
        this.cname = cname;
        this.phonenum = phonenum;
        this.loc = loc;
    }

    /**
     * 创建新的客户实例
     *
     * @param cid 客户ID
     * @param cname 客户名称
     * @param phonenum 客户电话号码
     * @param loc 客户地址
     * @return 新创建的客户对象
     */
    public static Client newInstance(Long cid, String cname, String phonenum, String loc) {
        return new Client(cid, cname, phonenum, loc);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == Client.class) {
            Client c = (Client) obj;
            return cid.equals(c.cid);
        } else {
            return false;
        }
    }

    /**
     * 获取哈希值
     */
    @Override
    public int hashCode() {
        return cid.hashCode();
    }

    /**
     * 获取客户ID
     *
     * @return 客户ID
     */
    public Long getCid() {
        return cid;
    }

    /**
     * 获取客户名称
     *
     * @return 客户名称
     */
    public String getClientName() {
        return cname;
    }

    /**
     * 获取客户电话号码
     *
     * @return 客户电话号码
     */
    public String getPhoneNumber() {
        return phonenum;
    }

    /**
     * 获取客户地址
     *
     * @return 客户地址
     */
    public String getLocation() {
        return loc;
    }
}
