package com.manasys.manasys;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import com.manasys.manasys.entity.User;
import com.manasys.manasys.exception.signin.PasswordMismatchException;
import com.manasys.manasys.exception.signin.UserNotFoundException;
import com.manasys.manasys.exception.userstate.UserAlreadyLoggedInException;
import com.manasys.manasys.repository.UserRepository;
import com.manasys.manasys.service.UserService;

/**
 * 用户登录测试
 *
 * @author 刘洛松
 * @since 2025.6.26
 */
@SpringBootTest
public class UserServiceSignInTests {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserService userServ;

    private final String VALID_USERNAME = "username";
    private final String VALID_PASSWORD = "ValidPwd@1234";

    @Test
    void testValidSignin() {
        User user = User.newInstance(VALID_USERNAME, VALID_PASSWORD);
        when(userRepo.findByUsername(VALID_USERNAME)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> {
            userServ.signIn(VALID_USERNAME, VALID_PASSWORD);
        });
    }

    @Test
    void testUserNotFound() {
        when(userRepo.findByUsername(VALID_USERNAME)).thenReturn(Optional.empty());
        assertThrowsExactly(UserNotFoundException.class, () -> {
            userServ.signIn(VALID_USERNAME, VALID_PASSWORD);
        });
    }

    @Test
    void testPasswordMismatch() {
        User user = User.newInstance(VALID_USERNAME, VALID_PASSWORD);
        when(userRepo.findByUsername(VALID_USERNAME)).thenReturn(Optional.of(user));
        assertThrowsExactly(PasswordMismatchException.class, () -> {
            userServ.signIn(VALID_USERNAME, "Mismatch@1234");
        });
    }

    @Test
    void testUserAlreadyLogin() {
        User user = User.newInstance(VALID_USERNAME, VALID_PASSWORD);
        user.setLoginStatus(true);
        when(userRepo.findByUsername(VALID_USERNAME)).thenReturn(Optional.of(user));
        assertThrowsExactly(UserAlreadyLoggedInException.class, () -> {
            userServ.signIn(VALID_USERNAME, VALID_PASSWORD);
        });
    }

}
