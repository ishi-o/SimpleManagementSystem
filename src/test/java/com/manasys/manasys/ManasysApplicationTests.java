package com.manasys.manasys;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.manasys.manasys.entity.User;
import com.manasys.manasys.repository.UserRepository;
import com.manasys.manasys.utils.UserService;

@SpringBootTest
class ManasysApplicationTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testUserServiceSignUp() {
        User mockUser = User.newUserWithFullInfo("testuser", "TruePwd@1234");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        User user = userService.signUp("testuser", "TruePwd@1234");
        // 创建成功, 用户不为空
        assertNotNull(user);
        // 创建成功, 用户名为testuser
        assertEquals("testuser", user.getUsername());
        // 用户重复
        assertThrowsExactly(DataIntegrityViolationException.class, () -> {
            userService.signUp("testuser", "TruePwd@1234");
        });
        // 密码长度不合法
        assertThrowsExactly(DataIntegrityViolationException.class, () -> {
            userService.signUp("wrongPwd", "Ab@0");
        });
        // 密码字符不合法
        assertThrowsExactly(DataIntegrityViolationException.class, () -> {
            userService.signUp("wrongPwd", "iiiiiiii");
        });
        // 用户名字符不合法
        assertThrowsExactly(DataIntegrityViolationException.class, () -> {
            userService.signUp("wrong_User", "Jhomework@123");
        });
        // 用户名长度不合法
        assertThrowsExactly(DataIntegrityViolationException.class, () -> {
            userService.signUp("", "Jhomework@123");
        });
        // 密码为空
        assertThrowsExactly(DataIntegrityViolationException.class, () -> {
            userService.signUp("AcceptUser", null);
        });
        // 用户名为空
        assertThrowsExactly(DataIntegrityViolationException.class, () -> {
            userService.signUp(null, "Accept@123");
        });
    }

}
