package com.mooncompany.manasys.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mooncompany.manasys.entity.EmpRecord;
import com.mooncompany.manasys.entity.EmpRecord.EmpRecordPK;
import com.mooncompany.manasys.entity.Employee;
import com.mooncompany.manasys.repository.EmpRecordRepository;
import com.mooncompany.manasys.repository.EmployeeRepository;
import com.mooncompany.manasys.util.EntityFactory;

import jakarta.transaction.Transactional;

/**
 * 关于公司员工的服务
 *
 * @author 刘洛松
 * @author 魏汉启
 * @since 2025.6.28
 * @see CommonService
 */
@Service
public class EmployeeService implements CommonRecordService {

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
    @Override
    @Transactional
    public void registerEntity(Map<String, Object> map) {
        empRepo.save(EntityFactory.newEmployeeAtNow((String) map.get("name")));
    }

    /**
     * 事务: 员工打卡
     *
     * @param eid 员工编号
     */
    @Override
    @Transactional
    public void record(Long eid) {
        Employee emp = empRepo.findById(eid).orElseThrow();
        if (!empRecordRepo.existsById(new EmpRecordPK(emp, LocalDate.now()))) {
            empRecordRepo.save(EntityFactory.newEmployeeRecordAtNow(emp));
        }
    }

    /**
     * 检查员工是否存在
     *
     * @param id 员工编号
     * @return 存在返回true，否则返回false
     */
    @Override
    @Transactional
    public boolean containsEntity(Long id) {
        return empRepo.existsById(id);
    }

    /**
     * 事务: 获取所有员工的信息
     *
     * @return 员工信息列表
     */
    @Override
    @Transactional
    public String getEntityInfo() {
        List<Employee> emps = empRepo.findAll(Sort.by("eid"));
        String ans = "员工号\t\t员工姓名\t\t入职日期";
        for (Employee e : emps) {
            ans += "\r\n" + e.getEid() + "\t\t" + e.getEname() + "\t\t\t" + e.getJoindate();
        }
        return ans;
    }

    /**
     * 获取指定员工信息
     *
     * @param eid 员工编号
     * @return 格式化后的员工信息字符串
     * @throws NoSuchElementException 当指定员工不存在时抛出
     */
    @Override
    @Transactional
    public String getEntityInfo(Long eid) {
        Employee emp = empRepo.findById(eid).orElseThrow();
        return "员工号\t\t员工姓名\t\t入职日期\r\n" + eid + "\t\t" + emp.getEname() + "\t\t" + emp.getJoindate();
    }

    /**
     * 获取所有打卡记录
     *
     * @return 格式化后的打卡记录字符串
     */
    @Override
    @Transactional
    public String getRecordInfo() {
        String ans = "员工编号\t\t员工打卡日";
        List<EmpRecord> list = empRecordRepo.findAll(Sort.by("emprecord"));
        for (EmpRecord er : list) {
            ans += "\r\n" + er.getEmployee().getEid() + "\t\t" + er.getDate();
        }
        return ans;
    }

    /**
     * 获取指定员工的所有打卡记录
     *
     * @param eid 员工编号
     * @return 格式化后的打卡记录字符串
     */
    @Override
    @Transactional
    public String getRecordInfo(Long eid) {
        String ans = "员工打卡日";
        List<LocalDate> list = empRecordRepo.findByEid(eid);
        for (LocalDate dt : list) {
            ans += "\r\n" + dt;
        }
        return ans;
    }

    /**
     * 获取指定员工在特定年月的打卡记录
     *
     * @param eid 员工编号
     * @param year 年份
     * @param month 月份
     * @return 格式化后的打卡记录字符串
     */
    @Override
    @Transactional
    public String getRecordInfo(Long eid, Integer year, Integer month) {
        String ans = "员工打卡日";
        List<LocalDate> list = empRecordRepo.findByEidAndYearAndMonth(eid, year, month);
        for (LocalDate dt : list) {
            ans += "\r\n" + dt;
        }
        return ans;
    }

    /**
     * 获取所有员工的打卡统计
     *
     * @return 格式化后的打卡统计字符串
     */
    @Override
    @Transactional
    public String getRecordCount() {
        String ans = "年份\t\t月份\t\t打卡次数";
        List<Object[]> list = empRecordRepo.countFromJoinDate();
        for (Object[] record : list) {
            ans += "\r\n" + record[0] + "\t\t" + record[1] + "\t\t" + record[2];
        }
        return ans;
    }

    /**
     * 事务: 获取员工入职以来的所有月份的打卡情况
     *
     * @param eid 员工编号
     * @return 员工入职以来的打卡情况, 以格式化的列表展示
     */
    @Override
    @Transactional
    public String getRecordCount(Long eid) {
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

    /**
     * 事务: 获取员工在指定年份指定月份的打卡情况
     *
     * @param eid 员工编号
     * @param year 指定年份
     * @param month 指定月份
     * @return 员工在指定年份指定月份的打卡情况
     */
    @Override
    @Transactional
    public String getRecordCount(Long eid, Integer year, Integer month) {
        if (empRepo.existsById(eid)) {
            return "员工号为 \"" + eid + "\" 的员工于 " + year + " 年 " + month + " 月 共打卡 " + empRecordRepo.countByYearAndMonth(eid, year, month) + " 次!";
        } else {
            return "该员工不存在!";
        }
    }

}
