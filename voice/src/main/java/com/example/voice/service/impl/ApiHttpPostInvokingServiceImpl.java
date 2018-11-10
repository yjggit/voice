package com.example.voice.service.impl;

import com.example.voice.exception.ApiException;
import com.example.voice.service.ApiAddressParamAssemblyInterface;
import com.example.voice.service.ApiInvokingService;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author jianhui.Yang
 * @version $Id ApiHttpPostInvokingServiceImpl.java, v 0.1 2018-11-09 17:07 jianhui.Yang Exp $$
 */
@Service("apiHttpPostInvoking")
public class ApiHttpPostInvokingServiceImpl implements ApiInvokingService, ApiAddressParamAssemblyInterface {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object invoking(String address, Map<String, Object> params) throws Exception {


        StringBuilder paramsStr = new StringBuilder();

        try {

            List<BasicNameValuePair> nvps = new ArrayList<>();
            if (!CollectionUtils.isEmpty(params)) {
                Iterator<Map.Entry<String,Object>> it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = it.next();
                    nvps.add(new BasicNameValuePair(entry.getKey().toString(),entry.getValue().toString() ));
                    paramsStr.append(paramsStr.length() == 0 ? "?" : "&").append(entry.getKey())
                            .append("=").append(entry.getValue());
                }
            }

            logger.warn( assembly(address,params) );

            Request request = Request.Post(address)
                    .addHeader("Content-Type","application/json;charset=UTF-8")
                    .bodyForm(nvps, Charset.forName("utf-8"));

            return request.execute().returnContent().asString();

        }catch (Exception e) {

            throw new ApiException( assembly(address, params, e), e );
        }

    }
}
