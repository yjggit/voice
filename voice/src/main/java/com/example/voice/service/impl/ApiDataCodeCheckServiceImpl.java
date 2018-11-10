package com.example.voice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.voice.exception.ApiException;
import com.example.voice.service.ApiAddressParamAssemblyInterface;
import com.example.voice.service.ApiDataCheckService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author jianhui.Yang
 * @version $Id ApiDataCodeCheckServiceImpl.java, v 0.1 2018-11-09 15:02 jianhui.Yang Exp $$
 */
@Service("apiDataCodeCheckService")
public class ApiDataCodeCheckServiceImpl implements ApiDataCheckService, ApiAddressParamAssemblyInterface {
    @Override
    public boolean checkData(String address, Map<String, Object> params, Object value) throws ApiException {

        JSONObject jsonObject = (JSONObject) JSONObject.parse(value.toString());

        if ("0000".equals(jsonObject.getString("code"))) {
            return true;
        }

        String msg = assembly(address,params,value);

        throw new  ApiException(msg);
    }
}
