package com.example.voice.service;

import com.example.voice.entities.LoggingEvent;

import java.util.List;
import java.util.Map;

/**
 * @author jianhui.Yang
 * @version $Id SystemLogService.java, v 0.1 2018-11-10 9:25 jianhui.Yang Exp $$
 */
public interface SystemLogService {

    List<Map<String,Object>> queryLogEvent(Long start, Long end,Long day,Integer limitStart,Integer limitSize);

    /**
     * 根据event_id查询日志基本描述
     * @param event_id event_id
     * @return Map<String,Object> 基本描述
     */
    Map<String,Object> logDetail(long event_id);

    /**
     * 根据event_id 查询异常日志详情
     * @param event_id
     * @return List<Map<String,Object>> 日志详情
     */
    List<Map<String,Object>> logExceptionDetaill(long event_id);

    /**
     * 查询指定时间内的报错日志
     * @param minute 分钟
     * @return
     */
    List<LoggingEvent> findApiErrorEventPeriod(long minute);

}
