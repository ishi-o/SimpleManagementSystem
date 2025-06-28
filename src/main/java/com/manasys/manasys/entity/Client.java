package com.manasys.manasys.entity;

import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 客户实体类
 *
 * @author 刘洛松
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

    @PersistenceCreator
    protected Client(String cname, String phonenum, String loc) {
        this.cname = cname;
        this.phonenum = phonenum;
        this.loc = loc;
    }

    public static Client newInstance(String cname, String phonenum, String loc) {
        return new Client(cname, phonenum, loc);
    }

    public Long getCid() {
        return cid;
    }

    public String getClientName() {
        return cname;
    }

    public String getPhoneNumber() {
        return phonenum;
    }

    public String getLocation() {
        return loc;
    }
}
