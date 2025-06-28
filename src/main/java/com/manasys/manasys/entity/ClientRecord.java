package com.manasys.manasys.entity;

import java.time.LocalDate;

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
 * @since 2025.6.28
 */
@Entity
@Table(name = "client_record", schema = "jhomework")
public class ClientRecord {

    @Id
    @GeneratedValue
    private Long crid;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "cid")
    private Client client;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    protected ClientRecord() {
    }

    @PersistenceCreator
    protected ClientRecord(Client client, LocalDate visitDate) {
        this.client = client;
        this.visitDate = visitDate;
    }

    public static ClientRecord newInstance(Client client, LocalDate visitDate) {
        return new ClientRecord(client, visitDate);
    }

    public Long getCid() {
        return crid;
    }

    public Client getClient() {
        return client;
    }

    public LocalDate getDate() {
        return visitDate;
    }

}
