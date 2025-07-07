package com.manasys.manasys.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 客户访问记录实体类
 *
 * @author 刘洛松
 * @author 赵庆显
 * @since 2025.6.28
 */
@Entity
@Table(name = "client_records", schema = "jhomework")
public class ClientRecord {

    @Id
    @GeneratedValue
    private Long crid;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "cid")
    private Client client;

    @Column(name = "visit_date", nullable = false)
    private LocalDateTime visitDateTime;

    protected ClientRecord() {
    }
/**
 * 用于持久化操作的构造函数
 *
 * @param client 关联的客户对象
 * @param visitDateTime 访问时间
 */
    @PersistenceCreator
    protected ClientRecord(Client client, LocalDateTime visitDateTime) {
        this.client = client;
        this.visitDateTime = visitDateTime;
    }
/**
 * 创建新的客户访问记录实例
 *
 * @param client 关联的客户对象
 * @param visitDateTime 访问时间
 * @return 新创建的客户访问记录对象
 */
    public static ClientRecord newInstance(Client client, LocalDateTime visitDateTime) {
        return new ClientRecord(client, visitDateTime);
    }
 /**
 * 获取访问记录ID
 *
 * @return 访问记录ID
 */
    public Long getCrid() {
        return crid;
    }
 /**
 * 获取关联的客户对象
 *
 * @return 客户对象
 */
    public Client getClient() {
        return client;
    }
 /**
 * 获取访问时间
 *
 * @return 访问时间
 */
    public LocalDateTime getDateTime() {
        return visitDateTime;
    }

}
