package com.mooncompany.manasys.service;

import org.springframework.stereotype.Service;

import com.mooncompany.manasys.exception.userstate.UserAlreadyLoggedInException;
import com.mooncompany.manasys.exception.userstate.UserNotLoggedInException;

/**
 * 提供命令行程序在不同模式下切换的服务
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@Service
public class InterfaceService {

    /**
     * 模式枚举, 表示不同模式, 例如未登录模式, 已登录模式
     *
     * @author 刘洛松
     * @since 2025.6.29
     */
    public static enum InterfaceMode {
        LOGIN,
        HOME
    }

    private InterfaceMode currMode = InterfaceMode.LOGIN;

    /**
     * 用于检查当前的模式是否和目标模式相同, 若当前模式和目标模式不同, 会根据当前模式抛出异常
     *
     * @param mode 待检查模式
     * @throws UserNotLoggedInException 当前模式未登录时
     * @throws UserAlreadyLoggedInException 当前模式已登录时
     */
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

    /**
     * 设置当前模式为目标模式
     *
     * @param mode 目标模式
     */
    public void setCurrMode(InterfaceMode mode) {
        this.currMode = mode;
    }
}
