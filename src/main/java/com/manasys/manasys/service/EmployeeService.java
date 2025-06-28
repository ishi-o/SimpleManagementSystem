package com.manasys.manasys.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.manasys.manasys.entity.Employee;
import com.manasys.manasys.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

/**
 * 关于公司员工的服务
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@Service
public class EmployeeService {

    private final EmployeeRepository empRepo;

    public EmployeeService(EmployeeRepository empRepo) {
        this.empRepo = empRepo;
    }

    @Transactional
    public void register(String ename) {
        empRepo.save(Employee.newInstance(ename, LocalDate.now()));
    }

    @Transactional
    public String viewAllEmployees() {
        List<Employee> emps = empRepo.findAll(Sort.by("eid"));
        String ans = "员工号\t\t员工姓名\t\t入职日期";
        for (Employee e : emps) {
            ans += "\r\n" + e.getEid() + "\t\t" + e.getEname() + "\t\t\t" + e.getJoindate();
        }
        return ans;
    }

}
