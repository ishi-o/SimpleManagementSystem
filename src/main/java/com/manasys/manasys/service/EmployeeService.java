package com.manasys.manasys.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.manasys.manasys.entity.EmpRecord;
import com.manasys.manasys.entity.EmpRecord.EmpRecordPK;
import com.manasys.manasys.entity.Employee;
import com.manasys.manasys.repository.EmpRecordRepository;
import com.manasys.manasys.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

/**
 * 关于公司员工的服务
 *
 * @author 刘洛松
 * @since 2025.6.28
 * @see CommonService
 */
@Service
public class EmployeeService extends CommonService {

    private final EmployeeRepository empRepo;
    private final EmpRecordRepository empRecordRepo;

    public EmployeeService(EmployeeRepository empRepo, EmpRecordRepository empRecordRepo) {
        this.empRepo = empRepo;
        this.empRecordRepo = empRecordRepo;
    }

    @Override
    @Transactional
    public void register(String ename) {
        empRepo.save(Employee.newInstance(ename, LocalDate.now()));
    }

    @Override
    @Transactional
    public String viewAllEmployees() {
        List<Employee> emps = empRepo.findAll(Sort.by("eid"));
        String ans = "员工号\t\t员工姓名\t\t入职日期";
        for (Employee e : emps) {
            ans += "\r\n" + e.getEid() + "\t\t" + e.getEname() + "\t\t\t" + e.getJoindate();
        }
        return ans;
    }

    @Override
    @Transactional
    public void punchIn(Long eid) {
        Employee emp = empRepo.findById(eid).orElseThrow();
        if (!empRecordRepo.existsById(new EmpRecordPK(emp, LocalDate.now()))) {
            empRecordRepo.save(EmpRecord.newInstance(emp, LocalDate.now()));
        }
    }

    @Override
    @Transactional
    public String getPunches(Long eid, Integer year, Integer month) {
        if (empRepo.existsById(eid)) {
            return "员工号为 \"" + eid + "\" 的员工于 " + year + " 年 " + month + " 月 共打卡 " + empRecordRepo.countByYearAndMonth(eid, year, month) + " 次!";
        } else {
            return "该员工不存在!";
        }
    }

    @Override
    @Transactional
    public String getPunches(Long eid) {
        if (empRepo.existsById(eid)) {
            String ans = "员工号为 \"" + eid + "\" 的员工入职以来的打卡情况如下: \r\n年份\t\t月份\t\t打卡次数";
            List<Object[]> list = empRecordRepo.countFromJoinDate(eid);
            for (Object[] record : list) {
                ans += "\r\n" + record[0] + "\t\t" + record[1] + "\t\t" + record[2];
            }
            return ans;
        } else {
            return "该员工不存在!";
        }
    }

}
