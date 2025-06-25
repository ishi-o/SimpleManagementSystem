package com.manasys.manasys;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.manasys.manasys.entity.User;
import com.manasys.manasys.exception.signup.InvalidPasswordException;
import com.manasys.manasys.exception.signup.InvalidUsernameException;
import com.manasys.manasys.exception.signup.UserAlreadyExistsException;
import com.manasys.manasys.repository.UserRepository;
import com.manasys.manasys.utils.UserService;

@SpringBootTest
class UserServiceSignUpTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final String VALID_USERNAME = "validuser";
    private final String VALID_PASSWORD = "ValidPwd@123";

    @Test
    void signUpValidUser() {
        User user = User.newUserWithFullInfo(VALID_USERNAME, VALID_PASSWORD);
        // 模拟VALID_USERNAME为合法的未注册用户
        when(userRepository.findByUsername(VALID_USERNAME)).thenReturn(Optional.empty());
        // 模拟调用Repo的save()时返回user
        when(userRepository.save(any(User.class))).thenReturn(user);
        assertEquals(user, userService.signUp(VALID_USERNAME, VALID_PASSWORD)); // 用户合法
    }

    @Test
    void signUpUserAlreadyExists() {
        User user = User.newUserWithFullInfo(VALID_USERNAME, VALID_PASSWORD);
        when(userRepository.findByUsername(VALID_USERNAME)).thenReturn(Optional.of(user));
        assertThrowsExactly(UserAlreadyExistsException.class, () -> {   // 用户已存在
            userService.signUp(VALID_USERNAME, VALID_PASSWORD);
        });
    }

    @Test
    void signUpInvalidUsername() {
        when(userRepository.findByUsername(VALID_USERNAME)).thenReturn(Optional.empty());
        assertThrowsExactly(InvalidUsernameException.class, () -> { // 用户名过短
            userService.signUp("", VALID_PASSWORD);
        });
        assertThrowsExactly(InvalidUsernameException.class, () -> { // 用户名含非法字符
            userService.signUp("user name", VALID_PASSWORD);
        });
        assertThrowsExactly(InvalidUsernameException.class, () -> { // 用户名过长
            userService.signUp("abcdefghijklmnopqrstuvwxyz", VALID_PASSWORD);
        });
    }

    @Test
    void signUpInvalidPassword() {
        when(userRepository.findByUsername(VALID_USERNAME)).thenReturn(Optional.empty());
        assertThrowsExactly(InvalidPasswordException.class, () -> { // 密码过短
            userService.signUp(VALID_USERNAME, "Sh0+t");
        });
        assertThrowsExactly(InvalidPasswordException.class, () -> { // 密码过长
            userService.signUp(VALID_USERNAME, "Abcdefghijklmnopqrst1234@=-");
        });
        assertThrowsExactly(InvalidPasswordException.class, () -> { // 密码强度不足
            userService.signUp(VALID_USERNAME, "abcdefghijk");
        });
        assertThrowsExactly(InvalidPasswordException.class, () -> { // 密码包含非法字符
            userService.signUp(VALID_USERNAME, "Abcd123@09 ");
        });
    }

    @Test
    void signUpRepoException() {
        when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);
        assertThrowsExactly(DataIntegrityViolationException.class, () -> {
            userService.signUp(VALID_USERNAME, VALID_PASSWORD);
        });
    }
}
