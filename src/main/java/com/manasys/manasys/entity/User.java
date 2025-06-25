package com.manasys.manasys.entity;

import org.hibernate.annotations.Check;
import org.springframework.data.annotation.PersistenceCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 用户实体类
 *
 * @author 刘洛松
 * @since 2025.6.25
 */
@Entity
@Table(name = "users", schema = "jhomework")    // 表名为users, 所属模式为jhomework
public class User {

    @Id // 注解为主键
    @GeneratedValue // 主键生成策略为默认的GenerationType.AUTO, 也可以显式地注解@GeneratedValue(strategy = ...)
    private Long uid;

    @Column(name = "username", nullable = false, unique = true)    // 非空约束, 唯一性约束
    @Check(constraints = "username ~ '^[a-zA-Z0-9]{1,20}$'", // username属性满足约束: 必须包含且只包含大写字母, 小写字母, 数字, 长度为1~20
            name = "ck_username_users")
    private String username;

    @Column(name = "password", nullable = false)    // 非空约束
    @Check(constraints // password属性满足约束: 必须包含且只包含大写字母, 小写字母, 数字, 特殊字符@#$%&+=, 长度为8~20
            = "password ~ '^(?=[^a-z]*[a-z])(?=[^A-Z]*[A-Z])(?=[^0-9]*[0-9])(?=[^@#$%&+=]*[@#$%&+=])[a-zA-Z0-9@#$%&+=]{8,20}$'",
            name = "ck_password_users")
    private String password;

    protected User() {
    }

    @PersistenceCreator
    protected User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * 创建一个指定了所有信息(即包括用户名和用户密码)的User实例
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 如果用户名和用户密码合法, 将返回新的实例
     */
    public static User newUserWithFullInfo(String username, String password) {
        return new User(username, password);
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == User.class) {
            User u = (User) o;
            return username.equals(u.username);
        } else {
            return false;
        }
    }

    /**
     * 获取用户密码
     *
     * @return 用户密码
     */
    public String getPassword() {
        return password;
    }

}
