package com.manasys.manasys.entity;

import java.time.LocalDate;

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
    protected SendPackage(LocalDate procDate, ClientRecord clientRecord, Integer fee) {
        super(procDate);
        this.clientRecord = clientRecord;
        this.fee = fee;
    }

    public SendPackage newInstance(LocalDate procDate, ClientRecord clientRecord, Integer fee) {
        return new SendPackage(procDate, clientRecord, fee);
    }

    public ClientRecord getClientRecord() {
        return clientRecord;
    }

    public Integer getFee() {
        return fee;
    }

}
