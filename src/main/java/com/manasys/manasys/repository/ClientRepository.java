package com.manasys.manasys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manasys.manasys.entity.Client;

/**
 * 客户实体的邻域仓库接口
 *
 * @author 刘洛松
 * @author 杨恒宇
 * @since 2025.6.28
 * @see JpaRepository
 */
public interface ClientRepository extends JpaRepository<Client, Long> {

}
