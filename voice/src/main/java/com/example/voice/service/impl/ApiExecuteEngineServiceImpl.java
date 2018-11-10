package com.example.voice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.voice.dao.CompApiDao;
import com.example.voice.entities.CompApi;
import com.example.voice.entities.CompCache;
import com.example.voice.exception.ApiException;
import com.example.voice.mapper.CompApiMapper;
import com.example.voice.service.*;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jianhui.Yang
 * @version $Id ApiExecuteEngineServiceImpl.java, v 0.1 2018-11-08 15:52 jianhui.Yang Exp $$
 */
@Service("defaultApiExecEngine")
public class ApiExecuteEngineServiceImpl implements ApiExecuteEngineService, ApiAddressParamAssemblyInterface {

    private Logger logger = LoggerFactory.getLogger( getClass() );

    public final static ExecutorService cacheThread = Executors.newSingleThreadExecutor();

    @Autowired
    protected CompApiDao compApiDao;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    protected CompApiMapper compApiMapper;

//    @Value("${components.api.re-invok.times}")
    private int apiInvokTimes = 4;


    @Override
    public Object execute(String appId, Map<String, Object> externalParam) throws Exception {

        CompApi compApi = compApiMapper.get(appId);

        if (compApi == null) {
            throw new ApiException("ID为" + appId + "的接口配置不存在 ");
        }

        Object msg = null;

        for (int k = 1; k == 1 || k <= apiInvokTimes; k ++) {

            if (k != 1) {
                synchronized (Thread.currentThread()){
                    Thread.currentThread().wait(500 * k );
                }
            }

            Object value = run(k, compApi, externalParam);

            if (value != null &&  !(value instanceof ErrorMsg)) {
                return value;
            }

            msg = value;
        }

        throw new ApiException( msg.toString() );
    }


    public Object run (int counter, CompApi compApi, Map<String, Object> externalParam) throws Exception {


        //处理参数
        Map<String,Object> combineParams = paramCombinAndCheck(compApi,externalParam);

        CompCache compCache = loadCacheDataCompCache(compApi,combineParams);

        if (compCache != null &&  new Date().before(compCache.getValidDate())) {
            return JSONObject.parseObject(compCache.getVal());
        }

        Object rawData = null;
        Exception e = null;

        try {
             //执行请求
            rawData = invoking(compApi,combineParams);

            // 返回数据校验和处理
            Object value = dataCheckAndProcessing(compApi,externalParam,combineParams,rawData);

            //缓存数据
            cacheData(compApi, combineParams, value);

            return value;
        }catch (Exception ex) {
            e = ex;
        }

        if (compCache != null) {
            return JSONObject.parseObject(compCache.getVal());
        }


      return new ErrorMsg(e.getMessage());
    }


    /**
     * 多线程缓存数据
     * @param compApi
     * @param combineParams
     * @param value
     */
    protected void cacheData(CompApi compApi, Map<String,Object> combineParams, Object value) {

        if (!StringUtils.hasText(compApi.getCacheInterface())) {
            return;
        }

        cacheThread.execute(new Runnable() {

            @Override
            public void run() {

                ApiCacheService cacheService = applicationContext.getBean(compApi.getCacheInterface() ,ApiCacheService.class);

                String key = cacheService.encryptKey(compApi.getId(),combineParams);

                cacheService.set(key,cacheService.validate(),value);
            }
        });
    }

    /**
     * 执行数据请求
     * @param compApi
     * @param combineParams
     * @return
     */
    protected Object loadCacheData(CompApi compApi,Map<String,Object> combineParams) {

        if (!StringUtils.hasText(compApi.getCacheInterface())) {
            return null;
        }

        try {

            ApiCacheService cacheService = applicationContext.getBean(compApi.getCacheInterface(),ApiCacheService.class);

            String key = cacheService.encryptKey(compApi.getId(),combineParams.toString());

            return cacheService.get(key);

        }catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }


    /**
     * 执行数据请求
     * @param compApi
     * @param combineParams
     * @return
     */
    protected CompCache loadCacheDataCompCache(CompApi compApi, Map<String,Object> combineParams) {

        if (!StringUtils.hasText(compApi.getCacheInterface())) {
            return null;
        }

        try {
            ApiCacheService cacheService = applicationContext.getBean(compApi.getCacheInterface(),ApiCacheService.class);

            String key = cacheService.encryptKey(compApi.getId(),combineParams.toString());

            return cacheService.getCompCache(key);

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 执行数据请求
     * @param compApi
     * @param combineParams
     * @return
     * @throws Exception
     */
    protected  Object invoking(CompApi compApi, Map<String,Object> combineParams ) throws Exception {

        ApiInvokingService invokingService = applicationContext.getBean(compApi.getInvokingInterface(),ApiInvokingService.class);
        return invokingService.invoking(compApi.getApiUrl(),combineParams);

    }


    /**
     * 返回数据 校验和处理
     * @param compApi
     * @param externalParam
     * @param combineParams
     * @param value
     * @return
     * @throws Exception
     */
    protected Object dataCheckAndProcessing(CompApi compApi, Map<String, Object> externalParam,Map<String,Object> combineParams, Object value) throws Exception {

        //校验返回值
        if (StringUtils.hasText(compApi.getDataCheckInterface())) {
            ApiDataCheckService dataCheck = applicationContext.getBean(compApi.getDataCheckInterface(),ApiDataCheckService.class);
            dataCheck.checkData( compApi.getApiUrl(), combineParams, value );
        }

        //数据处理
        if (StringUtils.hasText(compApi.getDataProcessingInterface())) {
            ApiDataProcessingService dataProcessing = applicationContext.getBean(compApi.getDataProcessingInterface(),ApiDataProcessingService.class);
            value = dataProcessing.processing( compApi.getApiUrl(), externalParam, combineParams,value );
        }

        return value;

    }


    /**
     * 参数合并/校验
     * @param compApi
     * @param externalParam
     * @return
     * @throws Exception
     */
    protected Map<String,Object> paramCombinAndCheck(CompApi compApi,Map<String,Object> externalParam ) throws Exception {
        //组合参数
        ApiParamCombineService paraComb = applicationContext.getBean(compApi.getCombineParamInterface(),ApiParamCombineService.class);
        Map<String,Object> combineParams = paraComb.combine(externalParam,compApi.getFixParam(),compApi.getExternalParam());

        //校验参数
        ApiCheckParamService checkParamService = applicationContext.getBean(compApi.getCheckParamInterface(),ApiCheckParamService.class);
        checkParamService.checkParam(combineParams,compApi.getExternalParam());

        return combineParams;
    }

}
