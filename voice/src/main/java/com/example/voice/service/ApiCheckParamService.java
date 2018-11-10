package com.example.voice.service;

import com.example.voice.exception.ApiException;

import java.util.Map;

/**
 * @author jianhui.Yang
 * @version $Id ApiCheckParamService.java, v 0.1 2018-11-09 14:14 jianhui.Yang Exp $$
 */
public interface ApiCheckParamService {


    /**
     * 参数校验
     * @param sourceParam  参数来源，必须包含的参数
     * @param includeCheckJson
     * @return
     */
    Map<String,Object> checkParam( Map<String,Object> sourceParam,String includeCheckJson ) throws ApiException;

}
