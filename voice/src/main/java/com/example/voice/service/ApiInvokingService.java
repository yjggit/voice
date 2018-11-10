package com.example.voice.service;

import java.util.Map;

/**
 * @author jianhui.Yang
 * @version $Id ApiInvokingService.java, v 0.1 2018-11-09 16:37 jianhui.Yang Exp $$
 */
public interface ApiInvokingService {

    Object invoking (String address, Map<String,Object> params) throws  Exception;

}
