package com.example.voice.service.impl;

import com.example.voice.entities.LoggingEvent;
import com.example.voice.mapper.SystemLogMapper;
import com.example.voice.service.SystemLogService;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author jianhui.Yang
 * @version $Id SystemLogServiceImpl.java, v 0.1 2018-11-10 9:31 jianhui.Yang Exp $$
 */
@SuppressWarnings("ALL")
@Service
public class SystemLogServiceImpl implements SystemLogService {

    @Autowired
    private SystemLogMapper logMapper;

    @Override
    public List<Map<String, Object>> queryLogEvent(Long start, Long end, Long day, Integer limitStart, Integer limitSize) {

        String [] p = {"yyyyMMddHHmmss","yyyyMMddHHmm","yyyyMMddHH","yyyyMMdd"};

        if (start != null) {
            start = DateUtils.parseDate(start+"",p).getTime();
        }

        if (end != null) {
            end = DateUtils.parseDate(end+"",p).getTime();
        }

        if (start != null && end != null && day == null) {
            start = 0L;
            end = 999999999999999L;
        }

        if (day != null) {
            start = DateUtils.parseDate(day+"000000",p).getTime();
            end = DateUtils.parseDate((day + 1)+ "000000").getTime();
        }

        return logMapper.queryLogEvent(start, end,limitStart,limitSize);
    }

    @Override
    public Map<String, Object> logDetail(long event_id) {
        return logMapper.logDetail(event_id);
    }

    @Override
    public List<Map<String, Object>> logExceptionDetaill(long event_id) {
        return logMapper.logExceptionDetail(event_id);
    }

    @Override
    public List<LoggingEvent> findApiErrorEventPeriod(long minute) {
        Long second = System.currentTimeMillis() - (minute * 1000 * 60);
        return logMapper.findApiErrorEventAfter(second);
    }
}
