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

    /**
     * 统计指定员工在指定年份指定月份的打卡次数
     *
     * @param eid 指定的员工编号
     * @param year 指定的年份
     * @param month 指定的月份
     * @return 员工在 {@code year} 年 {@code month} 月的总共打卡次数
     */
    @Query(value
            = """
            SELECT COUNT(1) 
            FROM jhomework.emp_records AS er 
            WHERE er.eid = ?1 AND 
                EXTRACT(YEAR FROM er.check_date) = ?2 AND 
                EXTRACT(MONTH FROM er.check_date) = ?3
            """, nativeQuery = true)
    Long countByYearAndMonth(Long eid, Integer year, Integer month);

    /**
     * 统计指定员工入职以来所有月份的打卡次数
     *
     * @param eid 指定的员工编号
     * @return (年份, 月份, 在该年该月的打卡次数)列表, 包含入职以来的所有打卡记录, 以(年份, 月份)升序排序
     */
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
            """, nativeQuery = true)
    List<Object[]> countFromJoinDate(Long eid);
}
