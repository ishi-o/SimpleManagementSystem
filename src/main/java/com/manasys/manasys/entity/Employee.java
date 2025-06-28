package com.manasys.manasys.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    private Date joindate;

    protected Employee() {
    }

    @PersistenceCreator
    protected Employee(String ename, Date joindate) {
        this.ename = ename;
        this.joindate = joindate;
    }

    public static Employee newInstance(String ename, Date joindate) {
        return new Employee(ename, joindate);
    }

    public long getEid() {
        return eid;
    }

    public String getEname() {
        return ename;
    }

    public Date getJoindate() {
        return joindate;
    }

}
