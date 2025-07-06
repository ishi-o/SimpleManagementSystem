package com.manasys.manasys.entity;

import java.time.LocalDateTime;

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
    private LocalDateTime procDateTime;

    protected Package() {
    }

    @PersistenceCreator
    protected Package(LocalDateTime procDateTime) {
        this.procDateTime = procDateTime;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Package && pid.equals(((Package) obj).pid);
    }

    @Override
    public int hashCode() {
        return pid.hashCode();
    }

    public LocalDateTime getDateTime() {
        return procDateTime;
    }

}
