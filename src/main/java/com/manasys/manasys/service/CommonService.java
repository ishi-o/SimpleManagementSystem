package com.manasys.manasys.service;

import javax.naming.OperationNotSupportedException;

import org.springframework.stereotype.Service;

import com.manasys.manasys.entity.User;

import jakarta.transaction.Transactional;

/**
 * 服务抽象类, 用于多态
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@Service
public abstract class CommonService {

    /// EmployeeService

    @Transactional
    public void register(String ename) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    @Transactional
    public String viewAllEmployees() throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    @Transactional
    public void punchIn(Long eid) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    @Transactional
    public String getPunches(Long eid, Integer year, Integer month) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    @Transactional
    public String getPunches(Long eid) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    /// UserService

    @Transactional
    public User signUp(String username, String password) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    @Transactional
    public void signOut() throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    @Transactional
    public void signIn(String username, String password) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    @Transactional
    public void logOut() throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

}
