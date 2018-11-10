package com.example.voice.service.impl;

import com.example.voice.entities.CompApi;
import com.example.voice.mapper.CompApiMapper;
import com.example.voice.service.ApiCompService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jianhui.Yang
 * @version $Id ApiCompServiceImpl.java, v 0.1 2018-11-09 10:58 jianhui.Yang Exp $$
 */
@SuppressWarnings("ALL")
@Service
public class ApiCompServiceImpl implements ApiCompService {


    @Autowired
    private CompApiMapper compApiMapper;


    @Override
    public CompApi get(String id) {
        return compApiMapper.get(id);
    }

    @Override
    public List<CompApi> finddocumentAddress() {
        return compApiMapper.finddocumentAddress();
    }
}
