package com.manasys.manasys.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 字段验证工具类（支持动态验证方法注册）
 *
 * 功能： 1. 内置常用字段验证方法 2. 支持动态注册新的验证规则 3. 通过统一接口调用验证方法
 *
 *
 * 动态验证方式 // 使用统一验证接口 boolean isValid = Inspections.validate("username",
 * "user123");
 *
 * 注册新验证规则 // 注册邮箱验证规则 Inspections.registerValidator("email", email -> email !=
 * null && email.matches("^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$"));
 *
 * // 使用新注册的验证规则 boolean isEmailValid = Inspections.validate("email",
 * "test@example.com");
 *
 * 覆盖现有验证规则 // 覆盖现有的用户名验证规则（要求首字母必须大写） Inspections.registerValidator("username",
 * username -> username != null &&
 * username.matches("^[A-Z][a-zA-Z0-9_]{0,19}$"));
 *
 * // 使用新规则验证 boolean isValid = Inspections.validate("username", "User123"); //
 * 现在要求首字母大写
 *
 * 完整使用示例和测试 public class Main { public static void main(String[] args) { //
 * 动态注册员工号验证方法（仅检查 6 位数字） Inspections.registerValidator("employeeNumber", input
 * -> { if (input == null || input.length() != 6) { return false; } try { //
 * 尝试转换为数字，确保全部是数字 Integer.parseInt(input); return true; } catch
 * (NumberFormatException e) { return false; } });
 *
 *       // 使用验证方法 boolean isValid = Inspections.validate("employeeNumber",
 * "123456"); System.out.println("员工号是否有效: " + isValid); // 输出: 员工号是否有效: true
 *
 *       // 测试无效员工号（非数字或长度不对） isValid = Inspections.validate("employeeNumber",
 * "12a456"); System.out.println("员工号是否有效: " + isValid); // 输出: 员工号是否有效: false
 *
 * isValid = Inspections.validate("employeeNumber", "12345");
 * System.out.println("员工号是否有效: " + isValid); // 输出: 员工号是否有效: false } }
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

    // 公开API
    /**
     * 注册新的验证方法
     *
     * @param fieldName 字段名称（如"email"）
     * @param validator 验证方法（接受字符串参数，返回布尔值）
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
     * @param fieldName 字段名称（如"username"）
     * @param value 待验证的值
     * @return 验证结果
     * @throws IllegalArgumentException 如果字段名称未注册
     */
    public static boolean validate(String fieldName, String value) {
        Function<String, Boolean> validator = validationMap.get(fieldName.toLowerCase());
        if (validator == null) {
            throw new IllegalArgumentException("No validator registered for field: " + fieldName);
        }
        return validator.apply(value);
    }

    // 内置验证方法
    /**
     * 验证用户名（1-20位字母、数字或下划线）
     */
    private static boolean validateUsername(String username) {
        return username != null && username.matches("^[a-zA-Z0-9_]{1,20}$");
    }

    /**
     * 验证密码（8-20位，至少包含大小写字母、数字和特殊字符）
     */
    private static boolean validatePassword(String password) {
        return password != null
                && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{8,20}$");
    }

    /**
     * 验证电话号码（11位数字）
     */
    private static boolean validatePhone(String phone) {
        return phone != null && phone.matches("^\\d{11}$");
    }

    /**
     * 验证日期格式（YYYY-MM-DD）
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
     * 验证日期是否在2000年后今天之前
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
     * 验证时间格式(HH:mm:ss)
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
