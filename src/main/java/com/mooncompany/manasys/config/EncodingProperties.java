package com.mooncompany.manasys.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 应用编码的相关配置
 *
 * @author 刘洛松
 * @since 2025.7.7
 */
@Component
@ConfigurationProperties(prefix = "manasys.encoding")
public class EncodingProperties {

    private String file = "UTF-8";
    private String jnu = "UTF-8";
    private String jline = "UTF-8";
    private String terminal = "UTF-8";

    /**
     * 获取编码配置
     *
     * @param property 配置字段
     * @return 配置值
     */
    public String getProperty(String property) {
        switch (property) {
            case "file" -> {
                return file;
            }
            case "jnu" -> {
                return jnu;
            }
            case "jline" -> {
                return jline;
            }
            case "terminal" -> {
                return terminal;
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * 设置编码配置
     *
     * @param property 配置字段
     * @param value 配置值
     */
    public void setProperty(String property, String value) {
        switch (property) {
            case "file" -> {
                file = value;
            }
            case "jnu" -> {
                jnu = value;
            }
            case "jline" -> {
                jline = value;
            }
            case "terminal" -> {
                terminal = value;
            }
            default -> {
            }
        }
    }
}
