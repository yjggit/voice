package com.example.voice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.voice.entities.CompApi;
import com.example.voice.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jianhui.Yang
 * @version $Id ApiTestEngineServiceImpl.java, v 0.1 2018-11-10 14:10 jianhui.Yang Exp $$
 */
@SuppressWarnings("ALL")
@Service("testApiExecEngine")
public class ApiTestEngineServiceImpl extends ApiExecuteEngineServiceImpl {
    @Override
    public Object execute(String appId, Map<String, Object> externalParam) throws Exception {

        if (externalParam == null ) {
            externalParam = new HashMap<>();
        }

        //根据ID获取接口设置
        CompApi compApi = compApiMapper.get(appId);

        if (compApi == null) {
            throw new ApiException("ID为: "+ appId + "的接口配置不存在");
        }

        //外部参数覆盖测试参数
        externalParam = externalOverTest(externalParam, compApi.getTestParam());

        //参数处理
        Map<String,Object> combineParams = paramCombinAndCheck(compApi, externalParam);

        //执行请求
        Object value = invoking(compApi,combineParams);

        //缓存数据
        cacheData(compApi,combineParams,value);


        return value;
    }


    /**
     * 外部参数覆盖测试参数
     * @param externalParam
     * @param testParam
     * @return
     */
    protected Map<String,Object> externalOverTest(Map<String ,Object> externalParam, String testParam){
        if (!StringUtils.hasText(testParam)) {
            return externalParam;
        }

        Map<String,Object> testMap = JSONObject.parseObject(testParam,Map.class);

        testMap.putAll(externalParam);

        return testMap;
    }

}
