package com.mooncompany.manasys.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.mooncompany.manasys.entity.SendPackage;
import com.mooncompany.manasys.repository.ClientRecordRepository;
import com.mooncompany.manasys.repository.EmployeeRepository;
import com.mooncompany.manasys.repository.ReceivePackageRepository;
import com.mooncompany.manasys.repository.SendPackageRepository;
import com.mooncompany.manasys.util.EntityFactory;

import jakarta.transaction.Transactional;

/**
 * 针对快递的服务
 *
 * @author 刘洛松
 * @author 杨帆
 * @since 2025.6.30
 */
@Service
public class PackageService {

    private final SendPackageRepository spRepo;
    private final ReceivePackageRepository rpRepo;
    private final EmployeeRepository empRepo;
    private final ClientRecordRepository crRepo;

    /**
     * 构造函数注入依赖
     *
     * @param spRepo 发送快递记录仓库
     * @param rpRepo 接收快递记录仓库
     * @param empRepo 员工信息仓库
     * @param crRepo 客户记录仓库
     */
    public PackageService(SendPackageRepository spRepo, ReceivePackageRepository rpRepo, EmployeeRepository empRepo, ClientRecordRepository crRepo) {
        this.spRepo = spRepo;
        this.rpRepo = rpRepo;
        this.empRepo = empRepo;
        this.crRepo = crRepo;
    }

    /**
     * 登记接收快递记录
     *
     * @param eid 员工编号
     * @throws NoSuchElementException 当指定员工不存在时抛出
     */
    @Transactional
    public void register(Long eid) {
        rpRepo.save(EntityFactory.newReceivePackage(empRepo.findById(eid).orElseThrow()));
    }

    /**
     * 登记发送快递记录
     *
     * @param crid 客户记录编号
     * @param fee 快递费用
     * @throws NoSuchElementException 当指定客户记录不存在时抛出
     */
    @Transactional
    public void register(Long crid, Integer fee) {
        spRepo.save(SendPackage.newInstance(LocalDateTime.now(), crRepo.findById(crid).orElseThrow(), fee));
    }

    /**
     * 获取指定年月的快递费用统计
     *
     * @param year 年份
     * @param month 月份
     * @return 格式化后的费用统计字符串
     */
    @Transactional
    public String getFee(Integer year, Integer month) {
        return year + " 年 " + month + " 月发送快递总共消耗 " + spRepo.countByYearAndMonth(year, month) + " 元!";
    }

}
