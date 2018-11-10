package com.example.voice.service;

import java.util.Map;

/**
 * @author jianhui.Yang
 * @version $Id ApiExecuteEngineService.java, v 0.1 2018-11-08 15:50 jianhui.Yang Exp $$
 */
public interface ApiExecuteEngineService {

    /**
     *  执行api调用全过程
     * @param appId apiId
     * @param externalParam 外部参数
     * @return
     * @throws Exception
     */
    Object execute(String appId, Map<String,Object> externalParam) throws Exception;

}
