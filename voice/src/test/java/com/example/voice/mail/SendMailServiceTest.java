package com.example.voice.mail;

import com.example.voice.entities.MailReceiver;
import com.example.voice.mapper.SendMailMapper;
import com.example.voice.util.MailSendHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianhui.Yang
 * @version $Id SendMail.java, v 0.1 2018-11-10 15:48 jianhui.Yang Exp $$
 */
@Component
public class SendMailServiceTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SendMailMapper sendMailMapper;

    @Autowired
    private MailSendHandler mailSendHandler;

//    @Scheduled(cron = "1 0/01 * * * ?") //每20分钟发送一次
    public void checkEventError() {
        logger.info("开始发送测试邮件");
        String[] to = {};
        mailSendHandler.sendSimpleMail(to,"邮件测试","邮件测试");
    }

//    private String[] getEmailTo () {
//        List<MailReceiver> mailReceiverList = sendMailMapper.findAllMailReceivers();
//        List<String> toList = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(mailReceiverList)) {
//            for (MailReceiver mailReceiver : mailReceiverList) {
//                toList.add(mailReceiver.getEmail());
//            }
//        }
//        String[] to = new String[toList.size()];
//        return toList.toArray(to);
//    }


    public static void main(String[] args) {
        SendMailServiceTest sendMailServiceTest = new SendMailServiceTest();
        sendMailServiceTest.checkEventError();
    }
}
