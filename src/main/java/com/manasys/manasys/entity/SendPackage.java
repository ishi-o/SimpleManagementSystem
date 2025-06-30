package com.manasys.manasys.entity;

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

    @PersistenceCreator
    protected SendPackage(LocalDateTime procDateTime, ClientRecord clientRecord, Integer fee) {
        super(procDateTime);
        this.clientRecord = clientRecord;
        this.fee = fee;
    }

    public static SendPackage newInstance(LocalDateTime procDateTime, ClientRecord clientRecord, Integer fee) {
        return new SendPackage(procDateTime, clientRecord, fee);
    }

    public ClientRecord getClientRecord() {
        return clientRecord;
    }

    public Integer getFee() {
        return fee;
    }

}
