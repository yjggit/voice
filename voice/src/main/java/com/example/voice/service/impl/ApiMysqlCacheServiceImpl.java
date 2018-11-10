package com.example.voice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.voice.dao.CompCacheDao;
import com.example.voice.entities.CompCache;
import com.example.voice.mapper.CompCacheMapper;
import com.example.voice.service.ApiCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author jianhui.Yang
 * @version $Id ApiMysqlCacheServiceImpl.java, v 0.1 2018-11-09 10:45 jianhui.Yang Exp $$
 */
@SuppressWarnings("ALL")
@Service("mysqlCacheService")
public class ApiMysqlCacheServiceImpl implements ApiCacheService {


    @Autowired
    private CompCacheDao compCacheDao;

    @Autowired
    private CompCacheMapper compCacheMapper;

    @Override
    public CompCache getCompCache(String key) {
        return compCacheDao.findById(key).orElse(null);
    }

    @Override
    public Object get(String key) {
        String val = compCacheMapper.geVal(key);

        if (!StringUtils.hasText(val)) {
            return null;
        }

        return JSONObject.parse(val);
    }

    @Override
    public void set(String key, Date validate, Object value) {

        if (value == null) {
            return;
        }

        CompCache compCache = new CompCache();
        compCache.setId(key);
        compCache.setValidDate(validate());
        compCache.setVal(value.toString());

        compCacheDao.save(compCache);
    }
}
