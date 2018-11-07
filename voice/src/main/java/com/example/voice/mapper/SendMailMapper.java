package com.example.voice.mapper;

import com.example.voice.entities.MailReceiver;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author jianhui.Yang
 * @version $Id SendMailMapper.java, v 0.1 2018-11-07 17:17 jianhui.Yang Exp $$
 */
@Mapper
public interface SendMailMapper {

    @Select("select id,email,remark,gmt_create gmtcreate,gmt_create gmtcreate from mail_receiver")
    List<MailReceiver> findAllMailReceivers();

}
