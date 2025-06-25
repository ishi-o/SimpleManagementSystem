package com.manasys.manasys.entity;

import org.hibernate.annotations.Check;
import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * 用户实体类
 *
 * @author 刘洛松
 * @since 2025.6.25
 */
@Entity
@Table(name = "users", schema = "jhomework")    // 表名为users, 所属模式为jhomework
@Check(constraints
        = "password ~ '^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).+$' AND "
        + "length(password) BETWEEN 8 AND 20",
        name = "ck_password_users")

public class User {

    @Id // 注解为主键
    @GeneratedValue // 主键生成策略为默认的GenerationType.AUTO, 也可以显式地注解@GeneratedValue(strategy = ...)
    private Long uid;

    @Column(name = "username", nullable = false)    // 非空约束
    @NotBlank(message = "用户名不能为空")   // 不能为空字符串
    private String uname;

    @Column(name = "password", nullable = false)
    private String pwd;

    @PersistenceCreator
    private User(String uname, String pwd) {
        this.uname = uname;
        this.pwd = pwd;
    }

    /**
     * 创建一个指定了所有信息(即包括用户名和用户密码)的User实例
     *
     * @param uname 用户名
     * @param pwd 用户密码
     * @return 如果用户名和用户密码合法, 将返回新的实例
     */
    public static User newUserWithFullInfo(String uname, String pwd) {
        return new User(uname, pwd);
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
