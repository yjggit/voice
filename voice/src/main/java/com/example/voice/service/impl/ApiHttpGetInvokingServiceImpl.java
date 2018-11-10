package com.example.voice.service.impl;

import com.example.voice.exception.ApiException;
import com.example.voice.service.ApiAddressParamAssemblyInterface;
import com.example.voice.service.ApiInvokingService;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Map;

/**
 * @author jianhui.Yang
 * @version $Id ApiHttpGetInvokingServiceImpl.java, v 0.1 2018-11-09 16:44 jianhui.Yang Exp $$
 */
@Service("apiHttpGetInvoking")
public class ApiHttpGetInvokingServiceImpl implements ApiInvokingService, ApiAddressParamAssemblyInterface {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object invoking(String address, Map<String, Object> params) throws Exception {

        StringBuilder urlParams = new StringBuilder();

        try {

            for (Map.Entry<String,Object> entry : params.entrySet()) {

                urlParams.append(urlParams.length() == 0 ? "?" : "&")
                        .append( entry.getKey() ) .append("=")
                        .append(URLEncoder.encode(entry.getValue().toString(),"UTF-8"));
            }

            urlParams.insert(0,address);
            logger.warn(urlParams.toString());

            Request request = Request.Get(urlParams.toString())
                    .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            return request.execute().returnContent().asString();

        }catch (Exception e) {
            throw new ApiException( assembly(address,params,e) ,e );
        }

    }
}
