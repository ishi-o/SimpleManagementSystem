package com.manasys.manasys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.manasys.manasys.entity.EmpRecord;

/**
 * 员工打卡记录领域仓库接口
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@Repository
public interface EmpRecordRepository extends JpaRepository<EmpRecord, EmpRecord.EmpRecordPK> {

    @Query(value
            = """
            SELECT COUNT(1) 
            FROM jhomework.emp_records AS er 
            WHERE er.eid = ?1 AND 
                EXTRACT(YEAR FROM er.check_date) = ?2 AND 
                EXTRACT(MONTH FROM er.check_date) = ?3
            """,
            nativeQuery = true)
    Long countByYearAndMonth(Long eid, Integer year, Integer month);

    @Query(value
            = """
            WITH emp_joindate AS (
                SELECT joindate
                FROM jhomework.employees
                WHERE eid = ?1
            ), month_rg AS (
                SELECT 
                    EXTRACT(YEAR FROM month_date)::INT AS year,
                    EXTRACT(MONTH FROM month_date)::INT AS month
                FROM GENERATE_SERIES (
                    DATE_TRUNC('month', (SELECT joindate FROM emp_joindate)),
                    DATE_TRUNC('month', CURRENT_DATE),
                    INTERVAL '1 month'
                ) AS month_date
            )
            SELECT 
                mr.year,
                mr.month,
                COUNT(er.eid) AS cnt
            FROM month_rg mr LEFT JOIN jhomework.emp_records er ON
                er.eid = ?1 AND
                EXTRACT(YEAR FROM er.check_date) = mr.year AND
                EXTRACT(MONTH FROM er.check_date) = mr.month
            GROUP BY mr.year, mr.month
            ORDER BY mr.year, mr.month
            """,
            nativeQuery = true)
    List<Object[]> countFromJoinDate(Long eid);
}
