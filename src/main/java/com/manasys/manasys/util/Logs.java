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
 * 日志工具类，支持动态配置日志输出位置、名称、标题和详细信息
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

    // 初始化默认日志记录器（系统日志和错误日志）
    private static void initDefaultLoggers() {
        try {
            // 创建日志目录
            File dir = new File(logDir);
            if (!dir.exists()) dir.mkdirs();

            // 系统日志配置
            systemLogger = Logger.getLogger("SystemLog");
            setupFileHandler(systemLogger, logDir + "/system.log", "SYSTEM LOG");
            
            // 错误日志配置
            errorLogger = Logger.getLogger("ErrorLog");
            setupFileHandler(errorLogger, logDir + "/error.log", "ERROR LOG");

        } catch (IOException e) {
            System.err.println("Failed to initialize loggers: " + e.getMessage());
        }
    }

    // 配置日志处理器（文件输出）
    private static void setupFileHandler(Logger logger, String filePath, String title) throws IOException {
        FileHandler fileHandler = new FileHandler(filePath, true);
        fileHandler.setFormatter(new CustomFormatter(title));
        logger.addHandler(fileHandler);
        logger.setUseParentHandlers(false); // 禁止输出到父处理器（如控制台）
    }

    // 自定义日志格式（支持动态标题）
    private static class CustomFormatter extends Formatter {
        private final String title;

        public CustomFormatter(String title) {
            this.title = title;
        }

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

    // 剩下的是公共API

    /**
     * 设置日志输出目录（需在首次调用日志方法前设置）
     */
    public static void setLogDirectory(String directory) {
        logDir = directory;
        initDefaultLoggers(); // 重新初始化日志器
    }

    /**
     * 动态添加一个新的日志处理器（例如输出到另一个文件）
     * @param loggerName 日志器名称（如"SystemLog"或自定义名称）
     * @param filePath   日志文件路径（如"logs/custom.log"）
     * @param title      日志标题
     */
    public static void addFileHandler(String loggerName, String filePath, String title) throws IOException {
        Logger logger = Logger.getLogger(loggerName);
        setupFileHandler(logger, filePath, title);
    }

    /**
     * 记录系统信息日志
     * @param message 日志信息
     */
    public static void info(String message) {
        systemLogger.info(message);
    }

    /**
     * 记录警告日志
     * @param message 日志信息
     */
    public static void warning(String message) {
        systemLogger.warning(message);
    }

    /**
     * 记录错误日志（带异常堆栈）
     * @param message 错误信息
     * @param throwable 异常对象
     */
    public static void error(String message, Throwable throwable) {
        errorLogger.log(Level.SEVERE, message, throwable);
    }

    /**
     * 设置日志级别（如Level.INFO、Level.DEBUG等）
     */
    public static void setLogLevel(Level level) {
        systemLogger.setLevel(level);
        errorLogger.setLevel(level);
    }
}

// Logs 工具类
// 日志分类：系统日志和错误日志分开存储
// 日志格式：包含时间戳、日志级别、日志名称和消息
// 日志文件：自动创建logs目录，日志文件自动滚动
// 日志级别：可以动态设置日志级别
// 扩展性：可以动态添加新的日志处理器
// 使用示例
// public class Main {
//     public static void main(String[] args) {
//         // 1. 动态设置日志目录
//         Logs.setLogDirectory("my_logs");

//         // 2. 添加一个自定义日志处理器（例如审计日志）
//         try {
//             Logs.addFileHandler("AuditLog", "my_logs/audit.log", "AUDIT LOG");
//         } catch (IOException e) {
//             e.printStackTrace();
//         }

//         // 3. 记录日志
//         Logs.info("系统启动完成");  // 输出到 my_logs/system.log
//         Logs.error("数据库连接失败", new RuntimeException("Connection timeout"));

//         // 4. 动态调整日志级别
//         Logs.setLogLevel(Level.FINE);  // 启用DEBUG级别日志
//     }
// }