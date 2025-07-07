package com.mooncompany.manasys.config;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.shell.jline.PromptProvider;

/**
 * 配置类
 *
 * @author 刘洛松
 * @since 2025.7.7
 */
@Configuration
public class ManasysApplicationConfiguration {

    private final EncodingProperties encodingProperties;

    public ManasysApplicationConfiguration(EncodingProperties ep) {
        this.encodingProperties = ep;
    }

    @Bean
    public Terminal terminal() throws IOException {
        return TerminalBuilder.builder().system(true).encoding(encodingProperties.getProperty("terminal")).jna(true).type("xterm-256color").dumb(true).build();
    }

    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationStarted() throws IOException {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            new ProcessBuilder("cmd.exe", "/c", "chcp", "65001", ">", "nul").inheritIO().start();
        }

        System.setProperty("file.encoding", encodingProperties.getProperty("file"));
        System.setProperty("sun.jnu.encoding", encodingProperties.getProperty("jnu"));
        System.setProperty("org.jline.terminal.encoding", encodingProperties.getProperty("jline"));
        System.setProperty("org.jline.terminal.jna.encoding", encodingProperties.getProperty("jline"));
        System.setOut(new PrintStream(System.out, true, Charset.forName(encodingProperties.getProperty("file"))));
        System.setErr(new PrintStream(System.err, true, Charset.forName(encodingProperties.getProperty("file"))));

        System.out.println("*********************************************************\n");
        System.out.println("\t欢迎使用 XXX 公司 前台管理系统 Manasys!");
        System.out.println("\t键入 \"help\" 命令以获取帮助!\n");
        System.out.println("*********************************************************\n");
    }

    @Bean
    public PromptProvider customProvider() {
        return () -> new AttributedString("manasys:> ",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN));
    }
}
