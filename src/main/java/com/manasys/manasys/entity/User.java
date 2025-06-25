package com.manasys.manasys.entity;

import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 用户实体类
 *
 * @author 刘洛松
 * @since 1.0
 * @updateDate 2025.6.25
 */
@Entity
@Table(name = "users", schema = "jhomework")    // 表名为users, 所属模式为jhomework
public class User {

    @Id // 注解为主键
    @GeneratedValue // 主键生成策略为默认的GenerationType.AUTO, 也可以显式地注解@GeneratedValue(strategy = ...)
    private Long uid;

    @Column(name = "username", nullable = false)    // 非空约束
    @NotBlank(message = "用户名不能为空")   // 不能为空字符串
    private String uname;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "用户密码必须不为空")
    @Size(min = 8, max = 20, message = "密码长度必须在8~20之间")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).+$",
            message = "用户密码必须包含大写字母, 小写字母, 数字以及一个特殊字符")
    private String pwd;

    @PersistenceCreator
    public User(String uname, String pwd) {
        this.uname = uname;
        this.pwd = pwd;
    }

    /**
     * 获取用户id
     *
     * @return 用户id
     */
    public Long getUid() {
        return uid;
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public String getUsername() {
        return uname;
    }

    /**
     * 设置用户名
     *
     * @param username 用户的新名
     */
    public void setUsername(String username) {
        uname = username;
    }

    /**
     * 设置用户密码
     *
     * @param password 用户的新密码
     */
    public void setPassword(String password) {
        pwd = password;
    }

}
