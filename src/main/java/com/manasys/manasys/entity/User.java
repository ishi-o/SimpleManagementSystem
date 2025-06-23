package com.manasys.manasys.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Ishi_O
 * @since
 * @updateDate
 */
@Entity
@Table(name = "users", schema = "jhomework")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(name = "username", nullable = false, unique = true)
    private String uname;

    @Column(name = "password", nullable = false)
    private String pwd;
}
