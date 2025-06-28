package com.manasys.manasys.entity;

import java.util.Date;

import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author 刘洛松
 * @since 2025.6.28
 */
@Entity
@Table(name = "emp_record", schema = "jhomework")
public class EmpRecord {

    @OneToOne
    @JoinColumn(name = "eid", referencedColumnName = "eid")
    private Employee employee;

    @Column(name = "check_date", nullable = false)
    private Date checkdate;

    protected EmpRecord() {
    }

    @PersistenceCreator
    protected EmpRecord(Employee emp, Date checkdate) {
        this.employee = emp;
        this.checkdate = checkdate;
    }

    public static EmpRecord newInstance(Employee emp, Date checkdate) {
        return new EmpRecord(emp, checkdate);
    }

    public Employee getEmployee() {
        return employee;
    }

    public Date getDate() {
        return checkdate;
    }

}
