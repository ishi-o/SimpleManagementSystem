package com.manasys.manasys.service;

import org.springframework.stereotype.Service;

import com.manasys.manasys.exception.userstate.UserAlreadyLoggedInException;
import com.manasys.manasys.exception.userstate.UserNotLoggedInException;

/**
 * 命令行模式
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@Service
public class InterfaceService {

    public static enum InterfaceMode {
        LOGIN,
        HOME
    }

    private InterfaceMode currMode = InterfaceMode.LOGIN;

    public void checkCurrMode(InterfaceMode mode) {
        if (!currMode.equals(mode)) {
            switch (currMode) {
                case InterfaceMode.LOGIN -> {
                    throw new UserNotLoggedInException();
                }
                case InterfaceMode.HOME -> {
                    throw new UserAlreadyLoggedInException();
                }
            }
        }
    }

    public void setCurrMode(InterfaceMode mode) {
        this.currMode = mode;
    }
}
