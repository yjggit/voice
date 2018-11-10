package com.example.voice.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.voice.service.ApiParamCombineService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author jianhui.Yang
 * @version $Id ApiParamCombineServiceImpl.java, v 0.1 2018-11-09 16:09 jianhui.Yang Exp $$
 */

@Service("defaultApiParamCombineService")
public class ApiParamCombineServiceImpl implements ApiParamCombineService {


    @Override
    public Map<String, Object> combine(Map<String, Object> externalParam, String fixParamJson, String externalParamRestrictJson) {

        Map<String,Object> fixMap = new HashMap<>();
        if (StringUtils.hasText(fixParamJson)) {
            fixMap = JSONObject.parseObject(fixParamJson,Map.class);
        }

        //外部参数为空，直接返回固定参数
        if (CollectionUtils.isEmpty(externalParam)) {
            return fixMap;
        }

        //没有条件限制 则直接返回组合参数
        if (!StringUtils.hasText(externalParamRestrictJson)) {
            externalParam.putAll(fixMap);
            return externalParam;
        }

        JSONObject restrict = JSONObject.parseObject(externalParamRestrictJson);

        Map<String,Object> returnMap = new HashMap<>();

        //筛选只需要传递的参数
        if (restrict.containsKey("include")) {
            JSONArray include = (JSONArray) restrict.get("include");
            Iterator it = include.iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if (externalParam.containsKey(key)) {
                    returnMap.put(key.toString(),externalParam.get(key));
                }
            }
        }
        //排除不需要的 参数，包含和不包含二选一
        else  if (restrict.containsKey("exclude")) {

            JSONArray exclude = (JSONArray) restrict.get("exclude");
            Iterator it = exclude.iterator();
            while (it.hasNext()) {
                Object key = it.next();
                externalParam.remove(key);
            }

            returnMap = externalParam;
        }

        returnMap.putAll(fixMap);
        return returnMap;

    }
}
