package com.manasys.manasys.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 员工实体
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@Entity
@Table(name = "employees", schema = "jhomework")
public class Employee {

    @Id
    @GeneratedValue
    private Long eid;

    @Column(name = "ename", nullable = false)
    private String ename;

    @Column(name = "joindate", nullable = false)
    private LocalDate joinDate;

    protected Employee() {
    }

    @PersistenceCreator
    protected Employee(String ename, LocalDate joinDate) {
        this.ename = ename;
        this.joinDate = joinDate;
    }

    public static Employee newInstance(String ename, LocalDate joinDate) {
        return new Employee(ename, joinDate);
    }

    @Override
    public boolean equals(Object o) {
        return Employee.class == o.getClass() && eid.equals(((Employee) o).eid);
    }

    public long getEid() {
        return eid;
    }

    public String getEname() {
        return ename;
    }

    public LocalDate getJoindate() {
        return joinDate;
    }

}
