package com.manasys.manasys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manasys.manasys.entity.EmpRecord;

/**
 * 员工打卡记录领域仓库接口
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
public interface EmpRecordRepository extends JpaRepository<EmpRecord, EmpRecord.EmpRecordPK> {

}
