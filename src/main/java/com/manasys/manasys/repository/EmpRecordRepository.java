package com.manasys.manasys.repository;

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
            = "SELECT COUNT(1) "
            + "FROM jhomework.emp_record AS er "
            + "WHERE er.eid = ?1 AND "
            + "EXTRACT(YEAR FROM er.check_date) = ?2 AND "
            + "EXTRACT(MONTH FROM er.check_date) = ?3",
            nativeQuery = true)
    long countByYearAndMonth(long eid, int year, int month);
}
