package com.manasys.manasys.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 数据验证工具类，提供常用字段验证功能
 *
 * <p>
 * 该类包含用户名、密码、电话号码、日期格式等多种验证方法， 支持通过注册机制扩展自定义验证规则</p>
 *
 * @author 赵庆显
 * @since 2025.7.2
 */
public final class Inspections {

    // 验证方法注册表
    private static final Map<String, Function<String, Boolean>> validationMap = new HashMap<>();

    static {
        // 注册内置验证方法
        registerDefaultValidators();
    }

    // 私有构造方法防止实例化
    private Inspections() {
    }

    private static void registerDefaultValidators() {
        registerValidator("username", Inspections::validateUsername);
        registerValidator("password", Inspections::validatePassword);
        registerValidator("phone", Inspections::validatePhone);
        registerValidator("dateFormat", Inspections::validateDateFormat);
        registerValidator("dateRange", Inspections::validateDateRange);
        registerValidator("time", Inspections::validateTime);
    }

    /**
     * 注册自定义验证方法
     *
     * @param fieldName 字段名称（不区分大小写）
     * @param validator 验证逻辑函数（输入字符串，返回布尔值）
     * @throws IllegalArgumentException 当参数为null时抛出
     */
    public static void registerValidator(String fieldName, Function<String, Boolean> validator) {
        if (fieldName == null || validator == null) {
            throw new IllegalArgumentException("Field name and validator cannot be null");
        }
        validationMap.put(fieldName.toLowerCase(), validator);
    }

    /**
     * 执行字段验证
     *
     * @param fieldName 已注册的字段名
     * @param value 待验证字符串
     * @return 验证通过返回true，否则false
     * @throws IllegalArgumentException 当字段未注册时抛出
     */
    public static boolean validate(String fieldName, String value) {
        Function<String, Boolean> validator = validationMap.get(fieldName.toLowerCase());
        if (validator == null) {
            throw new IllegalArgumentException("No validator registered for field: " + fieldName);
        }
        return validator.apply(value);
    }

    /**
     * 用户名格式验证（1-20位字母数字下划线）
     *
     * @param username 待验证字符串
     * @return 符合格式返回true
     */
    private static boolean validateUsername(String username) {
        return username != null && username.matches("^[a-zA-Z0-9_]{1,20}$");
    }

    /**
     * 密码强度验证（8-20位，需包含大小写字母、数字和特殊字符）
     *
     * @param password 待验证字符串
     * @return 符合强度要求返回true
     */
    private static boolean validatePassword(String password) {
        return password != null
                && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{8,20}$");
    }

    /**
     * 手机号格式验证（11位纯数字）
     *
     * @param phone 待验证字符串
     * @return 符合格式返回true
     */
    private static boolean validatePhone(String phone) {
        return phone != null && phone.matches("^\\d{11}$");
    }

    /**
     * 日期格式验证（ISO格式：yyyy-MM-dd）
     *
     * @param date 待验证字符串
     * @return 格式正确返回true
     */
    private static boolean validateDateFormat(String date) {
        if (date == null || !date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            return false;
        }

        try {
            LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * 日期范围验证（2000年至今）
     *
     * @param date 待验证字符串
     * @return 在有效范围内返回true
     */
    private static boolean validateDateRange(String date) {
        if (!validateDateFormat(date)) {
            return false;
        }

        LocalDate inputDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate today = LocalDate.now();
        LocalDate year2000 = LocalDate.of(2000, 1, 1);

        return !inputDate.isBefore(year2000) && !inputDate.isAfter(today);
    }

    /**
     * 时间格式验证（HH:mm:ss）
     *
     * @param time 待验证字符串
     * @return 格式正确返回true
     */
    private static boolean validateTime(String time) {
        if (time == null || !time.matches("^\\d{2}:\\d{2}:\\d{2}$")) {
            return false;
        }

        try {
            String[] parts = time.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            int second = Integer.parseInt(parts[2]);

            return hour >= 0 && hour < 24
                    && minute >= 0 && minute < 60
                    && second >= 0 && second < 60;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
