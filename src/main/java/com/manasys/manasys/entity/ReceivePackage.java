package com.manasys.manasys.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 接收的快递记录
 *
 * @author 刘洛松
 * @since 2025.6.28
 * @see Package
 */
@Entity
@Table(name = "recv_packages", schema = "jhomework")
public class ReceivePackage extends Package {

    @ManyToOne
    @JoinColumn(name = "recv_eid", referencedColumnName = "eid")
    private Employee emp;

    protected ReceivePackage() {
    }

    @PersistenceCreator
    protected ReceivePackage(LocalDate procDate, Employee emp) {
        super(procDate);
        this.emp = emp;
    }

    public static ReceivePackage newInstance(LocalDate procDate, Employee emp) {
        return new ReceivePackage(procDate, emp);
    }

    public Employee getEmployee() {
        return emp;
    }

}
