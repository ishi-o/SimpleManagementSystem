package com.manasys.manasys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manasys.manasys.entity.User;

public interface UserDao extends JpaRepository<User, Long> {

}
