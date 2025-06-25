package com.manasys.manasys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manasys.manasys.entity.User;

/**
 * 用户领域仓库的接口
 *
 * @author 刘洛松
 * @since 2025.6.25
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
