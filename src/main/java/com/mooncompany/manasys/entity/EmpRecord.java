package com.mooncompany.manasys.entity;

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
 * @author 赵庆显
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

        /**
         * 创建新的打卡记录主键实例
         *
         * @param emp 关联的员工对象
         * @param checkDate 打卡日期
         */
        public EmpRecordPK(Employee emp, LocalDate checkDate) {
            this.employee = emp;
            this.checkDate = checkDate;
        }

        /**
         * 比较两个主键对象是否相等
         *
         * @param o 要比较的对象
         * @return 如果对象类型相同且员工和日期相同返回true，否则返回false
         */
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

        /**
         * 比较两个主键对象的顺序
         *
         * @param o 要比较的对象
         * @return 比较结果
         */
        @Override
        public int compareTo(EmpRecordPK o) {
            if (employee.getEid().equals(o.getEmployee().getEid())) {
                return checkDate.compareTo(o.checkDate);
            } else {
                return employee.getEid().compareTo(o.employee.getEid());
            }
        }

        /**
         * 获取关联的员工对象
         *
         * @return 员工对象
         */
        Employee getEmployee() {
            return employee;
        }

        /**
         * 获取打卡日期
         *
         * @return 打卡日期
         */
        LocalDate getDate() {
            return checkDate;
        }
    }

    @EmbeddedId
    private EmpRecordPK emprecord;

    protected EmpRecord() {
    }

    /**
     * 用于持久化操作的构造函数
     *
     * @param emp 关联的员工对象
     * @param checkDate 打卡日期
     */
    @PersistenceCreator
    protected EmpRecord(Employee emp, LocalDate checkDate) {
        this.emprecord = new EmpRecordPK(emp, checkDate);
    }

    /**
     * 获取哈希值
     */
    @Override
    public int hashCode() {
        return emprecord.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return EmpRecord.class == obj.getClass() && emprecord.equals(((EmpRecord) obj).emprecord);
    }

    /**
     * 创建新的打卡记录实例
     *
     * @param emp 关联的员工对象
     * @param checkDate 打卡日期
     * @return 新创建的打卡记录对象
     */
    public static EmpRecord newInstance(Employee emp, LocalDate checkDate) {
        return new EmpRecord(emp, checkDate);
    }

    /**
     * 获取关联的员工对象
     *
     * @return 员工对象
     */
    public Employee getEmployee() {
        return emprecord.getEmployee();
    }

    /**
     * 获取打卡日期
     *
     * @return 打卡日期
     */
    public LocalDate getDate() {
        return emprecord.getDate();
    }

}
