package com.manasys.manasys.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 接收的快递记录
 *
 * @author 刘洛松
 * @author 赵庆显
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
 /**
 * 用于持久化操作的构造函数
 *
 * @param procDateTime 处理时间
 * @param emp 接收快递的员工对象
 */
    @PersistenceCreator
    protected ReceivePackage(LocalDateTime procDateTime, Employee emp) {
        super(procDateTime);
        this.emp = emp;
    }
 /**
 * 创建新的接收快递记录实例
 *
 * @param procDateTime 处理时间
 * @param emp 接收快递的员工对象
 * @return 新创建的接收快递记录对象
 */
    public static ReceivePackage newInstance(LocalDateTime procDateTime, Employee emp) {
        return new ReceivePackage(procDateTime, emp);
    }
 /**
 * 获取接收快递的员工信息
 *
 * @return 员工对象
 */
    public Employee getEmployee() {
        return emp;
    }

}
