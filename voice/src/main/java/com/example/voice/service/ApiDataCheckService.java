package com.example.voice.service;

import com.example.voice.exception.ApiException;

import java.util.Map;

/**
 * @author jianhui.Yang
 * @version $Id ApiDataCheckService.java, v 0.1 2018-11-09 15:00 jianhui.Yang Exp $$
 */
public interface ApiDataCheckService {


    /**
     * 返回值监测，返回值检测不通则抛出异常
     * @param address 接口地址
     * @param params 请求接口参数
     * @param value 接口获取到的返回值
     * @return
     */
    boolean checkData (String address, Map<String,Object> params, Object value) throws ApiException;

}
