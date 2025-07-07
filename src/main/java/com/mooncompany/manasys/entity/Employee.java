package com.mooncompany.manasys.entity;

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
 * @author 赵庆显
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

    /**
     * 用于持久化操作的构造函数
     *
     * @param ename 员工姓名
     * @param joinDate 入职日期
     */
    @PersistenceCreator
    protected Employee(String ename, LocalDate joinDate) {
        this.ename = ename;
        this.joinDate = joinDate;
    }

    /**
     * 创建新的员工实例
     *
     * @param ename 员工姓名
     * @param joinDate 入职日期
     * @return 新创建的员工对象
     */
    public static Employee newInstance(String ename, LocalDate joinDate) {
        return new Employee(ename, joinDate);
    }

    /**
     * 比较两个员工对象是否相同
     *
     * @param o 要比较的对象
     * @return 如果对象类型相同且ID相同返回true，否则返回false
     */
    @Override
    public int hashCode() {
        return eid.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return Employee.class == o.getClass() && eid.equals(((Employee) o).eid);
    }

    /**
     * 获取员工ID
     *
     * @return 员工ID
     */
    public Long getEid() {
        return eid;
    }

    /**
     * 获取员工姓名
     *
     * @return 员工姓名
     */
    public String getEname() {
        return ename;
    }

    /**
     * 获取入职日期
     *
     * @return 入职日期
     */
    public LocalDate getJoindate() {
        return joinDate;
    }

}
