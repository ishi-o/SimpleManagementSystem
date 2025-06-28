package com.manasys.manasys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manasys.manasys.entity.Employee;

/**
 * 公司员工领域仓库接口, 继承自JpaRepository
 *
 * @author 刘洛松
 * @since 2025.6.28
 * @see JpaRepository
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
