package com.mooncompany.manasys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mooncompany.manasys.entity.SendPackage;

/**
 * 接口: 发送快递的领域仓库
 *
 * @author 刘洛松
 * @author 杨帆
 * @since 2025.6.30
 */
public interface SendPackageRepository extends JpaRepository<SendPackage, Long> {

    @Query(value = """
            SELECT COALESCE(SUM(fee), 0)
            FROM jhomework.send_packages
            WHERE EXTRACT(YEAR FROM proc_date) = ?1 AND
                  EXTRACT(MONTH FROM proc_date) = ?2
            """, nativeQuery = true)
    Long countByYearAndMonth(Integer year, Integer month);

}
