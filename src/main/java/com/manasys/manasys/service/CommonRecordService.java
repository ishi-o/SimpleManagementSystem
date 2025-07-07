package com.manasys.manasys.service;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 记录类型服务的接口, 提供登记、查询信息、统计信息的方法
 *
 * @author 刘洛松
 * @author 魏汉启
 * @since 2025.6.30
 */
@Service
public interface CommonRecordService {

    /**
     * 使用提供的配置表进行登记
     *
     * @param map 配置表
     */
    void registerEntity(Map<String, Object> map);

    /**
     * 登记访问记录
     *
     * @param id 主键标识
     */
    void record(Long id);

    /**
     * 查询是否登记了某个主体的信息
     *
     * @param id 标识主体的主键
     * @return true 若包含; false 若不包含
     */
    boolean containsEntity(Long id);

    /**
     * 获取所有主体的信息
     *
     * @return 格式化的信息
     */
    String getEntityInfo();

    /**
     * 获取特定主体的信息
     *
     * @param id 标识主体的主键
     * @return 格式化的信息
     */
    String getEntityInfo(Long id);

    /**
     * 获取主体访问记录的信息
     *
     * @return 格式化的信息
     */
    String getRecordInfo();

    /**
     * 获取主体访问记录的信息
     *
     * @param id 标识主体的主键
     * @return 格式化的信息
     */
    String getRecordInfo(Long id);

    /**
     * 获取特定主体在特定年份及月份的访问信息
     *
     * @param id 标识主体的主键
     * @param year 指定的年份
     * @param month 指定的月份
     * @return 格式化的信息
     */
    String getRecordInfo(Long id, Integer year, Integer month);

    /**
     * 获取所有主体的所有记录数量
     *
     * @return 格式化的信息
     */
    String getRecordCount();

    /**
     * 获取指定主体的所有记录的数量
     *
     * @param id 指定的主体
     * @return 格式化的信息
     */
    String getRecordCount(Long id);

    /**
     * 获取指定主体指定年份指定月份的记录数量
     *
     * @param id 标识主体的主键
     * @param year 指定年份
     * @param month 指定月份
     * @return 格式化的信息
     */
    String getRecordCount(Long id, Integer year, Integer month);

}
