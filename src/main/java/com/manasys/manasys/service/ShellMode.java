package com.manasys.manasys.service;

import org.springframework.stereotype.Component;

/**
 * 命令行模式
 *
 * @author 刘洛松
 * @since 2025.6.28
 */
@Component
public class ShellMode {

    private InteractModeEnum currMode = InteractModeEnum.LOGIN;

    public InteractModeEnum getCurrMode() {
        return currMode;
    }

    public void setCurrMode(InteractModeEnum mode) {
        this.currMode = mode;
    }
}
