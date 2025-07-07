package com.mooncompany.manasys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mooncompany.manasys.entity.ReceivePackage;

/**
 * 接口: 接收快递的领域仓库
 *
 * @author 刘洛松
 * @author 杨帆
 * @since 2025.6.30
 */
public interface ReceivePackageRepository extends JpaRepository<ReceivePackage, Long> {

}
