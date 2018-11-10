package com.example.voice.service;

import com.example.voice.entities.MailReceiver;

import java.util.List;

/**
 * @author jianhui.Yang
 * @version $Id MailReceiverService.java, v 0.1 2018-11-10 11:12 jianhui.Yang Exp $$
 */
public interface MailReceiverService {

    /**
     * 查询所有的邮件接收邮箱
     * @return
     */
    List<MailReceiver> findAllMailReceivers();

}
