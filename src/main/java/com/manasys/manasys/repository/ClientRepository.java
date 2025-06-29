package com.manasys.manasys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manasys.manasys.entity.Client;

/**
 * @author 刘洛松
 * @since 2025.6.28
 */
public interface ClientRepository extends JpaRepository<Client, Long> {

}
