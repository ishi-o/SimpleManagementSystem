package com.manasys.manasys.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/*
 * 字段验证工具类
 */
public final class Inspections {
    
    //  私有构造方法防止实例化
    private Inspections(){}

    /**
     * 验证用户名（1-20位字母、数字或下划线）
     * @param username 用户名
     * @return 是否有效
     */

    public static boolean isValidUsername(String username){
        return username != null && username.matches("^[a-zA-Z0-9]{1,20}$");
    }

     /**
     * 验证密码（8-20位字母、数字或特殊字符@#$%^&+=）
     * @param password 密码
     * @return 是否有效
     */

    public static boolean isValidPassword(String password){
        return password != null && password.matches("^(?=[^a-z]*[a-z])(?=[^A-Z]*[A-Z])(?=[^0-9]*[0-9])(?=[^@#$%&+=]*[@#$%&+=])[a-zA-Z0-9@#$%&+=]{8,20}$");
    }

    /**
     * 验证电话号码（11位数字）
     * @param phone 电话号码
     * @return 是否有效
     */

    public static boolean isValidPhone(String phone){
        return phone != null && phone.matches("^\\d{11}$");
    }

    /**
     * 验证日期格式（YYYY-MM-DD）
     * @param date 日期字符串
     * @return 是否有效格式
     */

    public static boolean isValidDateFormat(String date){
        if( date == null || !date.matches("^\\d{4}-\\d{2}-\\d{2}$")){
            return false;
        }
        
        try {
            LocalDate.parse(date,DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * 验证日期是否在2000年后今天之前
     * @param date 日期字符串
     * @return 是否有效
     */

    public static boolean isValidDateRange(String date){
        if(isValidDateRange(date)){
            return false;
        }

        LocalDate inputDate = LocalDate.parse(date,DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate today = LocalDate.now();
        LocalDate year2000 = LocalDate.of(2000,1,1);

        return !inputDate.isBefore(year2000) && !inputDate.isAfter(today);
    }

    /**
     * 验证时间格式(HH:mm:ss)
     * @param time 时间字符串
     * @return 是否有效
     */

    public static boolean isValidTime(String time) {
        if (time == null || !time.matches("^\\d{2}:\\d{2}:\\d{2}$")) {
            return false;
        }
        
        try {
            String[] parts = time.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            int second = Integer.parseInt(parts[2]);
            
            return hour >= 0 && hour < 24 && 
                   minute >= 0 && minute < 60 && 
                   second >= 0 && second < 60;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

// 工具类说明
// Inspections 工具类
// 用户名验证：只允许字母、数字和下划线，长度1-20个字符
// 密码验证：允许字母、数字和特殊字符(@#$%^&+=)，长度8-20个字符
// 电话号码验证：必须是11位数字
// 日期验证：验证格式为YYYY-MM-DD，并检查是否在2000年之后今天之前
// 时间验证：验证格式为HH:mm:ss，并检查时间是否有效