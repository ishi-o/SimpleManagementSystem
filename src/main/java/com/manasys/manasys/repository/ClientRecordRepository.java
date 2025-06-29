package com.manasys.manasys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manasys.manasys.entity.ClientRecord;

/**
 * @author 刘洛松
 * @since 2025.6.28
 */
public interface ClientRecordRepository extends JpaRepository<ClientRecord, Long> {

    @Query(value = """
            SELECT cid, COUNT(crid) cnt
            FROM jhomework.client_record
            GROUP BY cid
            ORDER BY cid, cnt
            """, nativeQuery = true)
    List<Object[]> countAllClientsVisits();

}
