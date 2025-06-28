package com.manasys.manasys.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * 快递类的基类
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@MappedSuperclass
public abstract class Package {

    @Id
    @GeneratedValue
    private Long pid;

    @Column(name = "proc_date", nullable = false)
    private LocalDate procDate;

    protected Package() {
    }

    @PersistenceCreator
    protected Package(LocalDate procDate) {
        this.procDate = procDate;
    }

    public LocalDate getDate() {
        return procDate;
    }

}
