package com.manasys.manasys.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.manasys.manasys.entity.User;
import com.manasys.manasys.exception.signin.UserNotFoundException;
import com.manasys.manasys.exception.signup.InvalidPasswordException;
import com.manasys.manasys.exception.signup.InvalidUsernameException;
import com.manasys.manasys.exception.signup.UserAlreadyExistsException;
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
     * 事务: 注册用户, 若用户不存在, 用户名合法, 用户密码合法, 则成功创建并返回新建的User对象; 否则抛出异常
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 新建用户
     * @throws UserAlreadyExistsException 用户存在时抛出
     * @throws InvalidUsernameException 用户名不合法时抛出
     * @throws InvalidPasswordException 密码不合法时抛出
     * @throws DataIntegrityViolationException 向数据库插入数据出错时抛出
     */
    @Transactional
    public User signUp(String username, String password) {
        if (userRepo.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(username);
        } else if (!username.matches("^[a-zA-Z0-9]{1,20}$")) {
            throw new InvalidUsernameException(username);
        } else if (!password.matches("^(?=[^a-z]*[a-z])(?=[^A-Z]*[A-Z])(?=[^0-9]*[0-9])(?=[^@#$%&+=]*[@#$%&+=])[a-zA-Z0-9@#$%&+=]{8,20}$")) {
            throw new InvalidPasswordException(password);
        } else {
            return userRepo.save(User.newUserWithFullInfo(username, password));
        }
    }

    @Transactional
    public User signIn(String username, String password) {
        Optional<User> user = userRepo.findByUsername(username);
        if (!user.isPresent()) {
            throw new UserNotFoundException(username);
        } else {
            return user.get();
        }
    }

}
