package com.example.voice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.voice.service.ApiDataProcessingService;

import java.util.Map;

/**
 * @author jianhui.Yang
 * @version $Id ApiDataProcessingServiceImpl.java, v 0.1 2018-11-09 15:11 jianhui.Yang Exp $$
 */
public class ApiDataProcessingServiceImpl implements ApiDataProcessingService {
    @Override
    public Object processing(String address, Map<String, Object> externalParam, Map<String, Object> combineParams, Object rawData) {
        return JSONObject.parse((String) rawData);
    }
}
