package com.example.voice.service;

import java.util.Map;

/**数据处理接口
 * @author jianhui.Yang
 * @version $Id ApiDataProcessingService.java, v 0.1 2018-11-09 15:09 jianhui.Yang Exp $$
 */
public interface ApiDataProcessingService {


    /**
     * 根据实际业务情况，对返回数据进行处理
     * @param address
     * @param externalParam
     * @param combineParams
     * @param rawData
     * @return
     */
    Object processing(String address,
                      Map<String,Object> externalParam,
                      Map<String,Object> combineParams,
                      Object rawData);

}
