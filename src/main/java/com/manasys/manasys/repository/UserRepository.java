package com.manasys.manasys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manasys.manasys.entity.User;

/**
 * 用户领域仓库的接口, 继承自JpaRepository, 由编译器实现基础的CRUD方法
 *
 * @author 刘洛松
 * @since 2025.6.25
 * @see JpaRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据主键 {@code username} 查找用户
     *
     * @param username 用户名
     * @return {@code Optional<User>}
     */
    Optional<User> findByUsername(String username);

}
