package com.manasys.manasys.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.manasys.manasys.controller.ServiceType;

/**
 * 服务管理器, 用于统一管理不同类型的服务
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@Service
public class ServiceManager {

    private final Map<String, CommonService> SERVICES;

    public ServiceManager(Map<String, CommonService> map) {
        this.SERVICES = map;
    }

    public CommonService getService(ServiceType type) {
        switch (type) {
            case USER_SERVICE -> {
                return SERVICES.get("userService");
            }
            case EMPLOYEE_SERVICE -> {
                return SERVICES.get("employeeService");
            }
            default -> {
                return null;
            }
        }
    }
}
