package com.manasys.manasys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manasys.manasys.entity.ClientRecord;

/**
 * 客户来访记录的邻域仓库接口
 *
 * @author 刘洛松
 * @since 2025.6.28
 * @see JpaRepository
 */
public interface ClientRecordRepository extends JpaRepository<ClientRecord, Long> {

    /**
     * 统计所有客户的总共来访次数
     *
     * @return 客户(身份证号, 来访次数)列表, 以(身份证号, 来访次数)升序排序
     */
    @Query(value = """
            SELECT cid, COUNT(crid) cnt
            FROM jhomework.client_records
            GROUP BY cid
            ORDER BY cid, cnt
            """, nativeQuery = true)
    List<Object[]> countVisitAll();

    /**
     * 统计指定客户的来访次数
     *
     * @param cid 客户身份证号
     * @return 身份证号为 {@code cid} 的客户的来访次数
     */
    @Query(value = """
            SELECT COUNT(crid) cnt
            FROM jhomework.client_records
            WHERE cid = ?1
            """, nativeQuery = true)
    Long countVisitByClientId(Long cid);

}
