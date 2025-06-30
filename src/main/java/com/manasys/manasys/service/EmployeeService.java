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
public class EmployeeService {

    private final EmployeeRepository empRepo;
    private final EmpRecordRepository empRecordRepo;

    public EmployeeService(EmployeeRepository empRepo, EmpRecordRepository empRecordRepo) {
        this.empRepo = empRepo;
        this.empRecordRepo = empRecordRepo;
    }

    /**
     * 事务: 注册新员工
     *
     * @param ename 员工姓名
     */
    @Transactional
    public void register(String ename) {
        empRepo.save(Employee.newInstance(ename, LocalDate.now()));
    }

    /**
     * 事务: 获取所有员工的信息
     *
     * @return 员工信息列表
     */
    @Transactional
    public String getEmployeeInfo() {
        List<Employee> emps = empRepo.findAll(Sort.by("eid"));
        String ans = "员工号\t\t员工姓名\t\t入职日期";
        for (Employee e : emps) {
            ans += "\r\n" + e.getEid() + "\t\t" + e.getEname() + "\t\t\t" + e.getJoindate();
        }
        return ans;
    }

    /**
     * 事务: 员工打卡
     *
     * @param eid 员工编号
     */
    @Transactional
    public void punchIn(Long eid) {
        Employee emp = empRepo.findById(eid).orElseThrow();
        if (!empRecordRepo.existsById(new EmpRecordPK(emp, LocalDate.now()))) {
            empRecordRepo.save(EmpRecord.newInstance(emp, LocalDate.now()));
        }
    }

    /**
     * 事务: 获取员工在指定年份指定月份的打卡情况
     *
     * @param eid 员工编号
     * @param year 指定年份
     * @param month 指定月份
     * @return 员工在指定年份指定月份的打卡情况
     */
    @Transactional
    public String getCountOfPunch(Long eid, Integer year, Integer month) {
        if (empRepo.existsById(eid)) {
            return "员工号为 \"" + eid + "\" 的员工于 " + year + " 年 " + month + " 月 共打卡 " + empRecordRepo.countByYearAndMonth(eid, year, month) + " 次!";
        } else {
            return "该员工不存在!";
        }
    }

    /**
     * 事务: 获取员工入职以来的所有月份的打卡情况
     *
     * @param eid 员工编号
     * @return 员工入职以来的打卡情况, 以格式化的列表展示
     */
    @Transactional
    public String getCountOfPunch(Long eid) {
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
