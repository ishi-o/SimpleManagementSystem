package com.manasys.manasys.entity;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
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
@Table(name = "emp_records", schema = "jhomework")
public class EmpRecord {

    @Embeddable
    public static class EmpRecordPK implements Serializable, Comparable<EmpRecordPK> {

        @ManyToOne
        @JoinColumn(name = "eid", referencedColumnName = "eid")
        private Employee employee;

        @Column(name = "check_date", nullable = false)
        private LocalDate checkDate;

        public EmpRecordPK() {
        }

        public EmpRecordPK(Employee emp, LocalDate checkDate) {
            this.employee = emp;
            this.checkDate = checkDate;
        }

        @Override
        public boolean equals(Object o) {
            if (EmpRecordPK.class == o.getClass()) {
                EmpRecordPK pk = (EmpRecordPK) o;
                return employee.equals(pk.employee) && checkDate.equals(pk.checkDate);
            } else {
                return false;
            }
        }

        /**
         * 返回该类的哈希值
         */
        @Override
        public int hashCode() {
            return employee.hashCode() + checkDate.hashCode();
        }

        @Override
        public int compareTo(EmpRecordPK o) {
            if (employee.getEid().equals(o.getEmployee().getEid())) {
                return checkDate.compareTo(o.checkDate);
            } else {
                return employee.getEid().compareTo(o.employee.getEid());
            }
        }

        Employee getEmployee() {
            return employee;
        }

        LocalDate getDate() {
            return checkDate;
        }
    }

    @EmbeddedId
    private EmpRecordPK emprecord;

    protected EmpRecord() {
    }

    @PersistenceCreator
    protected EmpRecord(Employee emp, LocalDate checkDate) {
        this.emprecord = new EmpRecordPK(emp, checkDate);
    }

    @Override
    public int hashCode() {
        return emprecord.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return EmpRecord.class == obj.getClass() && emprecord.equals(((EmpRecord) obj).emprecord);
    }

    public static EmpRecord newInstance(Employee emp, LocalDate checkDate) {
        return new EmpRecord(emp, checkDate);
    }

    public Employee getEmployee() {
        return emprecord.getEmployee();
    }

    public LocalDate getDate() {
        return emprecord.getDate();
    }

}
