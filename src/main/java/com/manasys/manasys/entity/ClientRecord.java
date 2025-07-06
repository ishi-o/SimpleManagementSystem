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

    @PersistenceCreator
    protected ClientRecord(Client client, LocalDateTime visitDateTime) {
        this.client = client;
        this.visitDateTime = visitDateTime;
    }

    public static ClientRecord newInstance(Client client, LocalDateTime visitDateTime) {
        return new ClientRecord(client, visitDateTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == Client.class) {
            ClientRecord cr = (ClientRecord) obj;
            return crid.equals(cr.crid);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return crid.hashCode();
    }

    public Long getCrid() {
        return crid;
    }

    public Client getClient() {
        return client;
    }

    public LocalDateTime getDateTime() {
        return visitDateTime;
    }

}
