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
 * @author 赵庆显
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
/**
 * 用于持久化操作的构造函数
 *
 * @param procDateTime 处理时间
 */
    @PersistenceCreator
    protected Package(LocalDateTime procDateTime) {
        this.procDateTime = procDateTime;
    }
/**
 * 获取处理时间
 *
 * @return 处理时间
 */
    public LocalDateTime getDateTime() {
        return procDateTime;
    }

}
