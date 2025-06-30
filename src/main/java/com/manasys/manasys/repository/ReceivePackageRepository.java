package com.manasys.manasys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manasys.manasys.entity.ReceivePackage;

/**
 * 接口: 接收快递的领域仓库
 *
 * @author 刘洛松
 * @since 2025.6.30
 */
public interface ReceivePackageRepository extends JpaRepository<ReceivePackage, Long> {

}
