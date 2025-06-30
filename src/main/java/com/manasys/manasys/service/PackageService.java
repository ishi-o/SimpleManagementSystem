package com.manasys.manasys.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.manasys.manasys.entity.ReceivePackage;
import com.manasys.manasys.entity.SendPackage;
import com.manasys.manasys.repository.ClientRecordRepository;
import com.manasys.manasys.repository.EmployeeRepository;
import com.manasys.manasys.repository.ReceivePackageRepository;
import com.manasys.manasys.repository.SendPackageRepository;

import jakarta.transaction.Transactional;

/**
 * 针对快递的服务
 *
 * @author 刘洛松
 * @since 2025.6.30
 */
@Service
public class PackageService {

    private final SendPackageRepository spRepo;
    private final ReceivePackageRepository rpRepo;
    private final EmployeeRepository empRepo;
    private final ClientRecordRepository crRepo;

    public PackageService(SendPackageRepository spRepo, ReceivePackageRepository rpRepo, EmployeeRepository empRepo, ClientRecordRepository crRepo) {
        this.spRepo = spRepo;
        this.rpRepo = rpRepo;
        this.empRepo = empRepo;
        this.crRepo = crRepo;
    }

    @Transactional
    public void register(Long eid) {
        rpRepo.save(ReceivePackage.newInstance(LocalDateTime.now(), empRepo.findById(eid).orElseThrow()));
    }

    @Transactional
    public void register(Long crid, Integer fee) {
        spRepo.save(SendPackage.newInstance(LocalDateTime.now(), crRepo.findById(crid).orElseThrow(), fee));
    }

}
