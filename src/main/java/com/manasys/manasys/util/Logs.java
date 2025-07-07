package com.manasys.manasys.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * 日志工具类，提供系统日志和错误日志记录功能
 *
 * @author 赵庆显
 * @since 2025.7.2
 * @see java.util.logging.Logger
 */
public final class Logs {
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static Logger systemLogger;
    private static Logger errorLogger;
    private static String logDir = "logs";  // 默认日志目录

    static {
        initDefaultLoggers();
    }

    private Logs() {}

    /**
     * 初始化默认日志记录器
     *
     * @throws SecurityException 如果没有权限创建日志文件
     */
    private static void initDefaultLoggers() {
        try {
          
            File dir = new File(logDir);
            if (!dir.exists()) dir.mkdirs();

            systemLogger = Logger.getLogger("SystemLog");
            setupFileHandler(systemLogger, logDir + "/system.log", "SYSTEM LOG");
            
            
            errorLogger = Logger.getLogger("ErrorLog");
            setupFileHandler(errorLogger, logDir + "/error.log", "ERROR LOG");

        } catch (IOException e) {
            System.err.println("Failed to initialize loggers: " + e.getMessage());
        }
    }

    /**
     * 配置日志文件处理器
     *
     * @param logger 要配置的日志记录器
     * @param filePath 日志文件路径
     * @param title 日志文件标题
     * @throws IOException 文件操作异常时抛出
     */
    private static void setupFileHandler(Logger logger, String filePath, String title) throws IOException {
        FileHandler fileHandler = new FileHandler(filePath, true);
        fileHandler.setFormatter(new CustomFormatter(title));
        logger.addHandler(fileHandler);
        logger.setUseParentHandlers(false); 
    }
    /**
     * 自定义日志格式化类
     */
    private static class CustomFormatter extends Formatter {
        private final String title;
        /**
         * 构造方法
         * @param title 日志标题
         */
        public CustomFormatter(String title) {
            this.title = title;
        }
        /**
         * 格式化日志记录
         * @param record 日志记录对象
         * @return 格式化后的日志字符串
         */
        @Override
        public String format(LogRecord record) {
            return String.format("[%s] [%s] [%s] %s: %s%n",
                    LocalDateTime.now().format(TIMESTAMP_FORMATTER),
                    title,
                    record.getLevel(),
                    record.getSourceClassName(),
                    record.getMessage());
        }
    }

    /**
     * 设置日志目录
     * 
     * @param directory 新的日志目录路径
     */
    public static void setLogDirectory(String directory) {
        logDir = directory;
        initDefaultLoggers(); 
    }

     /**
     * 添加文件处理器到指定日志记录器
     *
     * @param loggerName 日志记录器名称
     * @param filePath 日志文件路径
     * @param title 日志标题
     * @throws IOException 文件操作异常时抛出
     */
    public static void addFileHandler(String loggerName, String filePath, String title) throws IOException {
        Logger logger = Logger.getLogger(loggerName);
        setupFileHandler(logger, filePath, title);
    }
 
    /**
     * 记录INFO级别日志
     *
     * @param message 日志消息
     */
    public static void info(String message) {
        systemLogger.info(message);
    }

    /**
     * 记录WARNING级别日志
     *
     * @param message 日志消息
     */
    public static void warning(String message) {
        systemLogger.warning(message);
    }

    /**
     * 记录ERROR级别日志
     *
     * @param message 日志消息
     * @param throwable 异常对象
     */
    public static void error(String message, Throwable throwable) {
        errorLogger.log(Level.SEVERE, message, throwable);
    }

    /**
     * 设置日志级别
     *
     * @param level 要设置的日志级别
     */
    public static void setLogLevel(Level level) {
        systemLogger.setLevel(level);
        errorLogger.setLevel(level);
    }
}

