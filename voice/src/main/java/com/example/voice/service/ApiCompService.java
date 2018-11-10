package com.example.voice.service;

import com.example.voice.entities.CompApi;

import java.util.List;

/**
 * @author jianhui.Yang
 * @version $Id ApiCompService.java, v 0.1 2018-11-09 10:54 jianhui.Yang Exp $$
 */
public interface ApiCompService {


    /**
     * 根据id查询api配置信息
     * @param id
     * @return
     */
    CompApi get(String id);

    /**
     * 查询所有接口文档地址
     * @return
     */
    List<CompApi> finddocumentAddress();

}
