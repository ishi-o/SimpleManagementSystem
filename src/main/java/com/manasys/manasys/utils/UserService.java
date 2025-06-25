package com.manasys.manasys.utils;

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
     * 事务: 注册用户
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 新建用户
     */
    @Transactional
    public User signUp(String username, String password) {
        return userRepo.save(User.newUserWithFullInfo(username, password));
    }

}
