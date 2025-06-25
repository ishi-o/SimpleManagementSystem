package com.manasys.manasys.utils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.manasys.manasys.entity.User;
import com.manasys.manasys.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * 提供用户服务
 *
 * @author 刘洛松
 * @since 2025.6.25
 */
@Service
public class UserService {

    private final UserRepository userRepo;

    /**
     * 构造方法, 由依赖注入器生成userRepo对象
     *
     * @param userRepo
     */
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * 事务: 注册用户, 若用户不存在, 用户名合法, 用户密码合法, 则成功创建并返回新建的User对象; 否则,
     * 抛出DataIntegrityViolationException异常
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 新建用户
     * @throws DataIntegrityViolationException 用户存在, 或用户名不合法, 或用户密码不合法
     */
    @Transactional
    public User signUp(String username, String password) throws DataIntegrityViolationException {
        try {
            return userRepo.save(User.newUserWithFullInfo(username, password));
        } catch (DataIntegrityViolationException e) {
            throw e;
        }
    }

}
