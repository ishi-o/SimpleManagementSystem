package com.mooncompany.manasys.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mooncompany.manasys.entity.User;
import com.mooncompany.manasys.exception.signin.PasswordMismatchException;
import com.mooncompany.manasys.exception.signin.UserNotFoundException;
import com.mooncompany.manasys.exception.signup.InvalidPasswordException;
import com.mooncompany.manasys.exception.signup.InvalidUsernameException;
import com.mooncompany.manasys.exception.signup.UserAlreadyExistsException;
import com.mooncompany.manasys.exception.userstate.UserAlreadyLoggedInByOthersException;
import com.mooncompany.manasys.repository.UserRepository;
import com.mooncompany.manasys.util.EntityFactory;
import com.mooncompany.manasys.util.Inspections;

import jakarta.transaction.Transactional;

/**
 * 提供针对管理员账户的服务
 *
 * @author 刘洛松
 * @author 张嘉政
 * @since 2025.6.25
 * @see CommonService
 */
@Service
public class UserService {

    private final UserRepository userRepo;
    private User user;

    /**
     * 构造方法, 由依赖注入器生成userRepo对象
     *
     * @param userRepo
     */
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
        this.user = null;
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
        } else if (!Inspections.validate("username", username)) {
            throw new InvalidUsernameException(username);
        } else if (!Inspections.validate("password", password)) {
            throw new InvalidPasswordException(password);
        } else {
            return userRepo.save(EntityFactory.newUser(username, password));
        }
    }

    /**
     * 事务: 用户登录, 若用户存在且密码正确且未在其它设备上登录, 则登录成功; 否则抛出异常
     *
     * @param username 用户名
     * @param password 用户密码
     * @throws UserNotFoundException 当用户不存在时抛出
     * @throws PasswordMismatchException 当密码不正确时抛出
     * @throws UserAlreadyLoggedInByOthersException 当用户已在其它设备上登录时抛出
     */
    @Transactional
    public void signIn(String username, String password) {
        user = userRepo.findByUsername(username).orElseThrow(() -> {
            throw new UserNotFoundException(username);
        });
        if (!user.getPassword().equals(password)) {
            user = null;
            throw new PasswordMismatchException(password);
        } else if (user.getLoginStatus()) {
            user = null;
            throw new UserAlreadyLoggedInByOthersException(username);
        } else {
            user.setLoginStatus(true);
            userRepo.save(user);
        }
    }

    /**
     * 事务: 用户登出
     */
    @Transactional
    public void signOut() {
        user.setLoginStatus(false);
        userRepo.save(user);
        user = null;
    }

    /**
     * 事务: 用户注销
     */
    @Transactional
    public void logOut() {
        userRepo.deleteById(user.getUid());
        user = null;
    }

}
