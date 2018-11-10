package com.example.voice.service;

import java.util.Map;

/**
 * @author jianhui.Yang
 * @version $Id ApiParamCombineService.java, v 0.1 2018-11-09 16:07 jianhui.Yang Exp $$
 */
public interface ApiParamCombineService {

    /**
     * 合并外部参数与固定参数
     * @param externalParam
     * @param fixParamJson
     * @param externalParamRestrictJson
     * @return
     */
    Map<String,Object> combine(Map<String,Object> externalParam, String fixParamJson,String externalParamRestrictJson);
}
