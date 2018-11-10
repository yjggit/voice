package com.example.voice.service;

import com.example.voice.entities.CompCache;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

/**
 * @author jianhui.Yang
 * @version $Id ApiCacheService.java, v 0.1 2018-11-09 10:12 jianhui.Yang Exp $$
 */
public interface ApiCacheService {

    /**
     * 根据key获得缓存中的数据
      * @param key 缓存唯一值
     * @return Object 缓存 中的数据
     */
    CompCache getCompCache(String key);


    /**
     * 根绝key获得缓存中的数据
     * @param key 缓存唯一值
     * @return Object 缓存中的数据
     */
    Object get (String key);

    /**
     * 根据Key获得缓存中的数据
     * @param key
     * @param validate
     * @param value
     */
    void set (String key, Date validate, Object value);

    /**
     * 生成有效截止时间，默认为当天23:59:59
     * @return
     */
    default Date validate() {
        Calendar today = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY,23);
        today.set(Calendar.MINUTE,59);
        today.set(Calendar.SECOND,59);

        return today.getTime();
    }

    /**
     * 生成唯一key值
     * @param preffix key前缀
     * @param content 要加密的数据
     * @param contents 可以是多个content
     * @return String 将数据组合后进行加密
     */
    default String encryptKey(String preffix , @NotNull Object content , Object ... contents) {

        StringBuilder val = new StringBuilder(content.toString());

        if (contents != null) {
            for (Object o : contents) {
                val.append("_").append(null == o ? "" : o.toString());
            }
        }

        val.insert(0,"-");
        val.insert(0,preffix);

        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        String pwd = encoder.encodePassword(val.toString(),preffix);

        return preffix + "_" + pwd;
    }

}
