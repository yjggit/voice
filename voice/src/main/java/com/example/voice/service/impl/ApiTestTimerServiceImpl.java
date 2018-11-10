package com.example.voice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.voice.dao.CompApiDao;
import com.example.voice.entities.CompApi;
import com.example.voice.service.ApiExecuteEngineService;
import com.example.voice.service.ApiTestTimerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author jianhui.Yang
 * @version $Id ApiTestTimerServiceImpl.java, v 0.1 2018-11-10 9:14 jianhui.Yang Exp $$
 */

@SuppressWarnings("ALL")
@Component
public class ApiTestTimerServiceImpl implements ApiTestTimerService {


    private static Logger logger = LoggerFactory.getLogger(ApiTestTimerServiceImpl.class);

    private ScheduledExecutorService scheduledThreadPool = Executors.newSingleThreadScheduledExecutor();

    @Resource(name = "testApiExecEngine")
    private ApiExecuteEngineService testEngineService;

    @Autowired
    private CompApiDao compApiDao;

    @PostConstruct
    @Override
    public void timerExec() {

        Iterable<CompApi> it = compApiDao.findAll();
        it.forEach(api ->{

            if (api.getTimerPoll() == null || api.getTimerPoll() < 1) {
                return;
            }

            scheduledThreadPool.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        logger.debug("执行接口测试: {}" , JSONObject.toJSONString(api));
                        testEngineService.execute(api.getId(),null);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },10,api.getTimerPoll(), TimeUnit.SECONDS);
        });

    }
}
