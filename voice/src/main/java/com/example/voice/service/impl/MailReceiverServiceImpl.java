package com.example.voice.service.impl;

import com.example.voice.entities.MailReceiver;
import com.example.voice.mapper.SendMailMapper;
import com.example.voice.service.MailReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jianhui.Yang
 * @version $Id MailReceiverServiceImpl.java, v 0.1 2018-11-10 11:13 jianhui.Yang Exp $$
 */
@SuppressWarnings("ALL")
@Service
public class MailReceiverServiceImpl implements MailReceiverService {

    @Autowired
    private SendMailMapper sendMailMapper;

    @Override
    public List<MailReceiver> findAllMailReceivers() {
        return sendMailMapper.findAllMailReceivers();
    }
}
