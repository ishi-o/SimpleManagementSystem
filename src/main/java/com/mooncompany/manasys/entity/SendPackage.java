package com.mooncompany.manasys.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * 发送快递记录
 *
 * @author 刘洛松
 * @author 赵庆显
 * @since 2025.6.28
 * @see Package
 */
@Entity
@Table(name = "send_packages", schema = "jhomework")
public class SendPackage extends Package {

    @OneToOne
    @JoinColumn(name = "crid", referencedColumnName = "crid")
    private ClientRecord clientRecord;

    @Column(name = "fee", nullable = false)
    private Integer fee;

    protected SendPackage() {
    }

    /**
     * 用于持久化操作的构造函数
     *
     * @param procDateTime 处理时间
     * @param clientRecord 关联的客户记录
     * @param fee 快递费用
     */
    @PersistenceCreator
    protected SendPackage(LocalDateTime procDateTime, ClientRecord clientRecord, Integer fee) {
        super(procDateTime);
        this.clientRecord = clientRecord;
        this.fee = fee;
    }

    /**
     * 创建新的发送快递记录实例
     *
     * @param procDateTime 处理时间
     * @param clientRecord 关联的客户记录
     * @param fee 快递费用
     * @return 新创建的发送快递记录对象
     */
    public static SendPackage newInstance(LocalDateTime procDateTime, ClientRecord clientRecord, Integer fee) {
        return new SendPackage(procDateTime, clientRecord, fee);
    }

    /**
     * 获取关联的客户记录
     *
     * @return 客户记录对象
     */
    public ClientRecord getClientRecord() {
        return clientRecord;
    }

    /**
     * 获取快递费用
     *
     * @return 快递费用
     */
    public Integer getFee() {
        return fee;
    }

}
