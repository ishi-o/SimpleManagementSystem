package com.manasys.manasys.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 员工打卡记录实体类
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@Entity
@Table(name = "emp_record", schema = "jhomework")
public class EmpRecord {

    @ManyToOne
    @JoinColumn(name = "eid", referencedColumnName = "eid")
    private Employee employee;

    @Column(name = "check_date", nullable = false)
    private LocalDate checkDate;

    protected EmpRecord() {
    }

    @PersistenceCreator
    protected EmpRecord(Employee emp, LocalDate checkDate) {
        this.employee = emp;
        this.checkDate = checkDate;
    }

    public static EmpRecord newInstance(Employee emp, LocalDate checkDate) {
        return new EmpRecord(emp, checkDate);
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getDate() {
        return checkDate;
    }

}
