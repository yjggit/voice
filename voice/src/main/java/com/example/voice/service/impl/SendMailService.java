package com.example.voice.service.impl;

import com.example.voice.entities.MailReceiver;
import com.example.voice.mapper.SendMailMapper;
import com.example.voice.util.MailSendHandler;
import com.example.voice.vo.SendMailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianhui.Yang
 * @version $Id SendMailService.java, v 0.1 2018-11-07 17:20 jianhui.Yang Exp $$
 */
@Component
public class SendMailService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SendMailMapper sendMailMapper;

    @Autowired
    private MailSendHandler mailSendHandler;

    @Scheduled(cron = "1 0/20 * * * ?") //每20分钟发送一次
    public void checkEventError() {
        logger.info("开始发送测试邮件");
        String[] to = getEmailTo();
        mailSendHandler.sendSimpleMail(to,"邮件测试","邮件测试");
    }

    private String[] getEmailTo () {
        List<MailReceiver> mailReceiverList = sendMailMapper.findAllMailReceivers();
        List<String> toList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(mailReceiverList)) {
            for (MailReceiver mailReceiver : mailReceiverList) {
                toList.add(mailReceiver.getEmail());
            }
        }
        String[] to = new String[toList.size()];
        return toList.toArray(to);
    }

}
